package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import gameLogic.GameEngine;
import characterEntities.*;

import javax.swing.*;

public class GameScreen extends Screen implements KeyListener, MouseListener {
	public enum GameState {
		GAME_STATE_MAP,
		GAME_STATE_PAUSED,
		GAME_STATE_HELP,
		GAME_STATE_PROFILE //For now
	}

	private GameEngine gameEngine;
	private CharacterProfile profilePage;
	private ImageIcon helpPage;
	private HashSet<Integer> motionKeys;
	private GameState gameState = GameState.GAME_STATE_MAP;
	
	public GameScreen(Hero.PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(1000, 1000));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		addMouseListener(this);

		gameEngine = new GameEngine(playerClass);
		profilePage = new CharacterProfile(gameEngine.getPlayer());
		helpPage = new ImageIcon("src/assets/maps/helpPage.png");
		motionKeys = new LinkedHashSet<>();
	}

	@Override
	public void init() {
		requestFocus(true);
	}

    @Override
    public void update() {
		switch (gameState) {
			case GAME_STATE_MAP:
				gameEngine.update();
				break;
			case GAME_STATE_HELP:
				//something
				break;
			case GAME_STATE_PROFILE:
				profilePage.update();
				break;
			default:
				//error?
				break;
		}
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

		if (e.getKeyCode() == KeyEvent.VK_J) {
			gameEngine.toggleMapLevel(-1);
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			gameEngine.toggleMapLevel(1);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			gameEngine.playerDidAttack(Entity.Ability.DEFAULT);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			gameEngine.playerDidAttack(Entity.Ability.FIRST);
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			gameEngine.playerWasAttacked();
		} else if (e.getKeyCode() == KeyEvent.VK_X) {
			gameEngine.playerDidHeal(3);
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			gameState = (gameState == GameState.GAME_STATE_PAUSED)? GameState.GAME_STATE_MAP : GameState.GAME_STATE_PAUSED;
		} else if (e.getKeyCode() == KeyEvent.VK_M) {
			gameState = GameState.GAME_STATE_MAP;
		} else if (e.getKeyCode() == KeyEvent.VK_H) {
			gameState = GameState.GAME_STATE_HELP;
		} else if (e.getKeyCode() == KeyEvent.VK_C) {
			gameState = GameState.GAME_STATE_PROFILE;
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
	public void mousePressed(MouseEvent e) {
		if (gameState == GameState.GAME_STATE_PROFILE) {

		}
	}

	public void mouseReleased(MouseEvent e) {
		if (gameState == GameState.GAME_STATE_PROFILE) {
			findPath(e.getX(), e.getY());
		}
	}

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }

	public void mouseClicked(MouseEvent e) {
		if (gameState == GameState.GAME_STATE_PROFILE) {
			findPath(e.getX(), e.getY());
		}
	}

	private void findPath(int mouseX, int mouseY) {
		
	}

	private void evolvePlayer(int index, CharacterProfile.Path path) {
		profilePage.attemptEvolution(index, path);
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
			default:
				break;
		}
	}
}
