package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import SquarePG.SquarePG;
import gameLogic.GameEngine;
import characterEntities.*;
import gameLogic.GameMode;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameScreen extends Screen implements KeyListener, MouseListener {
	public enum GameState {
		GAME_STATE_MAP,
		GAME_STATE_PAUSED,
		GAME_STATE_HELP,
		GAME_STATE_PROFILE,
		GAME_STATE_MAP_SELECT
	}

	private GameEngine gameEngine;
	private CharacterProfile profilePage;
	private MapSelectScreen mapSelectScreen;
	private JPanel deathPanel;
	private ImageIcon helpPage;
	private HashSet<Integer> motionKeys;
	private Queue<Entity.EntityAbility> attackKeys;
	private GameState gameState = GameState.GAME_STATE_MAP_SELECT;

	private boolean shouldKillAll = false;

	public static final int GAME_SCREEN_WIDTH = 1000;
	public static final int GAME_SCREEN_HEIGHT = 925;
	public static final int GAME_MAP_HEIGHT = 800;

	public static final int GAME_SCREEN_LEFT_BOUNDARY = 25;
	public static final int GAME_SCREEN_RIGHT_BOUNDARY = 975;
	public static final int GAME_SCREEN_TOP_BOUNDARY = 25;
	public static final int GAME_SCREEN_BOTTOM_BOUNDARY = 775;

	//Member variable is to make any changes to gameMap synchronized with main thread. Delete in non-dev mode
	private int toggleMap;
	
	public GameScreen(Hero.PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		addMouseListener(this);

		gameEngine = new GameEngine(playerClass);
		profilePage = new CharacterProfile(gameEngine.getPlayer(), gameEngine.getPlayerAbilityBar());
		helpPage = new ImageIcon("src/assets/maps/helpPage.png");
		motionKeys = new LinkedHashSet<>();
		attackKeys = new LinkedList<>();
		mapSelectScreen = new MapSelectScreen();
		mapSelectScreen.setUnlockedLevel(1);
		mapSelectScreen.setUnlockedMap(1);

		mapSelectScreen.init();
		mapSelectScreen.setVisible(false);
		add(mapSelectScreen);

		createAddDeathPanel();
	}

	@Override
	public void init() {
		requestFocus(true);
	}

	@Override
	public void update() {
		switch (gameState) {
			case GAME_STATE_MAP:
				consumeToggleMap();
				consumeAttack();
				gameEngine.update();
				if (gameEngine.getFailCurrentMap()) {
					deathPanel.setVisible(true);
					gameEngine.setFailCurrentMap(false);
					gameState = GameState.GAME_STATE_PAUSED;
				}

				if (SquarePG.gameMode == GameMode.DEBUG && shouldKillAll) {
					gameEngine.killAllEnemies();
					shouldKillAll = false;
				}
				break;
			case GAME_STATE_HELP:
				//something
				break;
			case GAME_STATE_PROFILE:
				profilePage.update();
				break;
			case GAME_STATE_MAP_SELECT:
				if (!mapSelectScreen.isVisible()) {
					mapSelectScreen.setVisible(true);
				}
				if (mapSelectScreen.getMap() != -1) {
					gameEngine.reset();
					gameEngine.setMap(mapSelectScreen.getMap());
					gameState = GameState.GAME_STATE_MAP;
					mapSelectScreen.resetSelection();
					mapSelectScreen.setVisible(false);
				}
				break;
			default:
				//error?
				break;
		}

		if (gameEngine.isCurrentMapFinished() && SquarePG.gameMode == GameMode.PLAY) {
			gameState = GameState.GAME_STATE_MAP_SELECT;
			gameEngine.setCurrentMapFinished(false);

			Pair<Integer, Integer> next = gameEngine.unlockNext();
			mapSelectScreen.setUnlockedLevel(next.getKey());
			mapSelectScreen.setUnlockedMap(next.getValue());
			mapSelectScreen.update();
		}
	}

	private void createAddDeathPanel() {
		deathPanel = new JPanel();
		deathPanel.setPreferredSize(new Dimension(GAME_SCREEN_WIDTH, GAME_MAP_HEIGHT));
		deathPanel.setBorder(BorderFactory.createEmptyBorder());
		deathPanel.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.4f));
		deathPanel.setLocation(0, 0);

		FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 0, 0);
		setLayout(flow);

		JLabel failLabel = new JLabel("YOU DIED");
		failLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		failLabel.setForeground(Color.white);
		failLabel.setBackground(Color.blue);
		failLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JButton tryAgain = new JButton("Try Again");
		tryAgain.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		tryAgain.addActionListener(l -> {
			gameEngine.reset();
			gameEngine.setMap(gameEngine.getCurrentLevel().getValue());
			deathPanel.setVisible(false);
			gameState = GameState.GAME_STATE_MAP;
		});

		JButton mainMap = new JButton("Return to Main Map");
		mainMap.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		mainMap.addActionListener(l -> {
			deathPanel.setVisible(false);
			gameState = GameState.GAME_STATE_MAP_SELECT;
		});

		deathPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;

		deathPanel.add(failLabel, c);
		c.gridy = 1;
		deathPanel.add(tryAgain, c);
		c.gridy = 2;
		deathPanel.add(mainMap, c);

		deathPanel.setVisible(false);
		add(deathPanel);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//maintains proper order
		if (motionKeys.contains(e.getKeyCode())) {
			motionKeys.remove(e.getKeyCode());
		}
		motionKeys.add(e.getKeyCode());

		for (Integer key : motionKeys) {
			if (key == KeyEvent.VK_UP) {
				gameEngine.playerDidMoveUp();
			} else if (key == KeyEvent.VK_DOWN) {
				gameEngine.playerDidMoveDown();
			} else if (key == KeyEvent.VK_LEFT) {
				gameEngine.playerDidMoveLeft();
			} else if (key == KeyEvent.VK_RIGHT) {
				gameEngine.playerDidMoveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_Q) {
			attackKeys.add(Entity.EntityAbility.FIRST);
		} else if (e.getKeyCode() == KeyEvent.VK_W){
			attackKeys.add(Entity.EntityAbility.SECOND);
		} else if (e.getKeyCode() == KeyEvent.VK_E){
			attackKeys.add(Entity.EntityAbility.THIRD);
		} else if (e.getKeyCode() == KeyEvent.VK_R){
			attackKeys.add(Entity.EntityAbility.ULTIMATE);
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			gameState = (gameState == GameState.GAME_STATE_PAUSED)? GameState.GAME_STATE_MAP : GameState.GAME_STATE_PAUSED;
		} else if (e.getKeyCode() == KeyEvent.VK_H) {
			gameState = GameState.GAME_STATE_HELP;
		}

		// Debug only controls
		if (SquarePG.gameMode == GameMode.DEBUG) {
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				gameEngine.playerWasAttacked();
			} else if (e.getKeyCode() == KeyEvent.VK_X) {
				gameEngine.playerDidHeal(3);
			} else if (e.getKeyCode() == KeyEvent.VK_M) {
				gameState = GameState.GAME_STATE_MAP;
			} else if (e.getKeyCode() == KeyEvent.VK_C) {
				gameState = GameState.GAME_STATE_PROFILE;
			} else if (e.getKeyCode() == KeyEvent.VK_T) {
				shouldKillAll = true;
			} else if (e.getKeyCode() == KeyEvent.VK_J) {
				toggleMap = -1;
			} else if (e.getKeyCode() == KeyEvent.VK_K) {
				toggleMap = 1;
			} else if (e.getKeyCode() == KeyEvent.VK_L) {
				gameState = GameState.GAME_STATE_MAP_SELECT;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		Integer code = e.getKeyCode();
		
		if (code == KeyEvent.VK_DOWN ) {
			gameEngine.playerDidStopMovingDown(motionKeys.contains(KeyEvent.VK_UP));
		} else if (code == KeyEvent.VK_UP){
			gameEngine.playerDidStopMovingUp(motionKeys.contains(KeyEvent.VK_DOWN));
		} else if (code == KeyEvent.VK_LEFT) {
			gameEngine.playerDidStopMovingLeft(motionKeys.contains(KeyEvent.VK_RIGHT));
		} else if (code == KeyEvent.VK_RIGHT) {
			gameEngine.playerDidStopMovingRight(motionKeys.contains(KeyEvent.VK_LEFT));
		}
		
		motionKeys.remove(code);
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }

	//mouse events
	@Override
	public void mousePressed(MouseEvent e) {
		if (gameState == GameState.GAME_STATE_PROFILE) {

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameState == GameState.GAME_STATE_PROFILE) {
			findPath(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (gameState == GameState.GAME_STATE_PROFILE) {
			findPath(e.getX(), e.getY());
		}
	}

	//Evolution Debug method
	private void findPath(int mouseX, int mouseY) {
		if (mouseX > 100 && mouseX < 300) {
			if (mouseY > 100 && mouseY < 300) {
				profilePage.attemptEvolution(0, CharacterProfile.Path.RED);
			} else if (mouseY > 400 && mouseY < 600) {
				profilePage.attemptEvolution(0, CharacterProfile.Path.YELLOW);
			} else if (mouseY > 700 && mouseY < 900) {
				profilePage.attemptEvolution(0, CharacterProfile.Path.BLUE);
			}
		} else if (mouseX > 400 && mouseX < 600) {
			if (mouseY > 100 && mouseY < 300) {
				profilePage.attemptEvolution(1, CharacterProfile.Path.RED);
			} else if (mouseY > 400 && mouseY < 600) {
				profilePage.attemptEvolution(1, CharacterProfile.Path.YELLOW);
			} else if (mouseY > 700 && mouseY < 900) {
				profilePage.attemptEvolution(1, CharacterProfile.Path.BLUE);
			}
		} else if (mouseX > 700 && mouseX < 900) {
			if (mouseY > 100 && mouseY < 300) {
				profilePage.attemptEvolution(2, CharacterProfile.Path.RED);
			} else if (mouseY > 400 && mouseY < 600) {
				profilePage.attemptEvolution(2, CharacterProfile.Path.YELLOW);
			} else if (mouseY > 700 && mouseY < 900) {
				profilePage.attemptEvolution(2, CharacterProfile.Path.BLUE);
			}
		}
	}

	private void evolvePlayer(int index, CharacterProfile.Path path) {
		profilePage.attemptEvolution(index, path);
	}

	private void consumeAttack() {
		if (attackKeys != null && attackKeys.peek() != null) {
			gameEngine.playerDidAttack(attackKeys.remove());
		}
	}

	private void consumeToggleMap() {
		if (toggleMap != 0) {
			gameEngine.toggleMap(toggleMap);
			toggleMap = 0;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;

		switch (gameState) {
			case GAME_STATE_MAP: case GAME_STATE_PAUSED:
				gameEngine.paint(g);
				break;
			case GAME_STATE_HELP:
				g2d.drawImage(helpPage.getImage(), 0, 0, null);
				break;
			case GAME_STATE_PROFILE:
				profilePage.paint(g);
				break;
			case GAME_STATE_MAP_SELECT:
				break;
			default:
				break;
		}
	}
}