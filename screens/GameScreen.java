package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import characterEntities.*;

public class GameScreen extends Screen implements KeyListener{
	private Set<Integer> motionKeys = new HashSet<Integer>();
	
	//TODO:depending on map design, make background class for collision detection
	private GameMap map;
	private int level = 1;
	private Hero player;
	private ArrayList<Entity> enemies = new ArrayList<>();
	
	public GameScreen(String playerName, PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(1000, 1000));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		map = new GameMap(level);
		createPlayer(playerName, playerClass);
		createEnemy("dummy", 1000);
	}

	@Override
	public void update() {
		//HACK: have something that requests focus not so frequently
		requestFocus(true);
		Rectangle originalPlayer = new Rectangle(player.getPosX(), player.getPosY(),75,  75);
		for (Entity enemy: enemies) {
			enemy.update();
		}
		player.update();
		player.setPoint(map.determineMotion(player.getPosX(), player.getPosY(), originalPlayer));
	}
	
	private void createPlayer(String playerName, PlayerClass playerClass) {
		switch (playerClass) {
		case RED:
			player = new RedHero(playerName);
			break;
		case BLUE:
			player = new BlueHero(playerName);
			break;
		case YELLOW:
			player = new YellowHero(playerName);
			break;
		default:
		}
	}

	private void createEnemy(String name, int health) {
		Enemy dummy = new Enemy(name, health, 0, 0);
		enemies.add(dummy);
	}

	private void createProjectile() {}

	@Override
	public void keyPressed(KeyEvent e) {

		motionKeys.add(e.getKeyCode());

		for (Integer key : motionKeys) {
			if (key == KeyEvent.VK_UP) {
				up();
			} else if (key == KeyEvent.VK_DOWN) {
				down();
			} else if (key == KeyEvent.VK_LEFT) {
				left();
			} else if (key == KeyEvent.VK_RIGHT) {
				right();
			} else if (key == KeyEvent.VK_A) {
				player.attack(Hero.Ability.DEFAULT);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_J) {
			map.setMap(1);
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			map.setMap(2);
		} else if (code == KeyEvent.VK_A) {
			player.attack(Hero.Ability.DEFAULT, enemies);
		} else if (code == KeyEvent.VK_Z) {
			player.inflict(25);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		Integer code = e.getKeyCode();
		
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP) {
			player.setUDMotionState(MotionStateUpDown.IDLE);
		} else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
			player.setLRMotionState(MotionStateLeftRight.IDLE);
		}
		
		motionKeys.remove(code);
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		map.draw(g);
		for (Entity enemy : enemies) {
			enemy.draw(g);
		}
		player.draw(g);
	}
	
	private void up() {
		player.setUDMotionState(MotionStateUpDown.UP);
	}
	
	private void down() {
		player.setUDMotionState(MotionStateUpDown.DOWN);
	}
	
	private void left() {
		if (player.getEntityState() != Entity.EntityState.DAMAGED && player.getEntityState() != Entity.EntityState.DEAD) {
			player.setLRMotionState(MotionStateLeftRight.LEFT);
			if (player.getEntityState() == Entity.EntityState.DEFAULT) {
				player.faceWest();
			}
		}
	}
	
	private void right() {
		if (player.getEntityState() != Entity.EntityState.DAMAGED && player.getEntityState() != Entity.EntityState.DEAD) {
			player.setLRMotionState(MotionStateLeftRight.RIGHT);
			if (player.getEntityState() == Entity.EntityState.DEFAULT) {
				player.faceEast();
			}
		}
	}
}
