package screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import SquarePG.GameState;
import characterEntities.*;

public class GameScreen extends Screen implements KeyListener{
	private GameState gameState = GameState.WORLDMAP;
	private Set<Integer> motionKeys = new HashSet<Integer>();
	
	//TODO:depending on map design, make background class for collision detection
	private GameMap map;
	private int level = 1;
	private Hero player;
	
	public GameScreen(String playerName, PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(1000, 1000));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		map = new GameMap(level);
		createPlayer(playerName, playerClass);
	}

	@Override
	public void update() {
		//HACK: have something that requests focus not so frequently
		requestFocus(true);
		player.update();
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

	private void createEnemy() {}

	private void createProjectile() {}

	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = new Integer(e.getKeyCode());
		motionKeys.add(code);

		for (Integer key : motionKeys) {
			if (key.intValue() == KeyEvent.VK_UP) {
				up();
			} else if (key.intValue() == KeyEvent.VK_DOWN) {
				down();
			} else if (key.intValue() == KeyEvent.VK_LEFT) {
				left();
			} else if (key.intValue() == KeyEvent.VK_RIGHT) {
				right();
			} else if (key.intValue() == KeyEvent.VK_A) {
				player.attack(Hero.Ability.DEFAULT);
			}
		}

		if (code == KeyEvent.VK_J) {
			map.setMap(1);
		} else if (code == KeyEvent.VK_K) {
			map.setMap(2);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		Integer code = new Integer(e.getKeyCode());
		
		if (code.intValue() == KeyEvent.VK_DOWN || code.intValue() == KeyEvent.VK_UP) {
			player.setUDMotionState(MotionStateUpDown.IDLE);
		} else if (code.intValue() == KeyEvent.VK_LEFT || code.intValue() == KeyEvent.VK_RIGHT) {
			player.setLRMotionState(MotionStateLeftRight.IDLE);
		}
		
		motionKeys.remove(code);
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		map.draw(g);
		player.draw(g);
	}
	
	public void up() {
		player.setUDMotionState(MotionStateUpDown.UP);
	}
	
	public void down() {
		player.setUDMotionState(MotionStateUpDown.DOWN);
	}
	
	public void left() {
		player.setLRMotionState(MotionStateLeftRight.LEFT);
		if (player.getEntityState() == Entity.EntityState.DEFAULT) {
			player.faceWest();
		}
	}
	
	public void right() {
		player.setLRMotionState(MotionStateLeftRight.RIGHT);
		if (player.getEntityState() == Entity.EntityState.DEFAULT) {
			player.faceEast();
		}
	}
}
