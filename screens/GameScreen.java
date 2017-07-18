package screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import SquarePG.GameState;
import characterEntities.*;

public class GameScreen extends Screen implements KeyListener{
	private GameState gameState = GameState.WORLDMAP;
	
	//TODO:motion state machine might be useful for real-time motion
	//Remove if not needed
	private MotionStateLeftRight lrMotionState = MotionStateLeftRight.IDLE;
	private MotionStateUpDown udMotionState = MotionStateUpDown.IDLE;
	
	//TODO:depending on map design, make background class for collision detection
	private Background background;
	private int mapNumber = 9;
	private Hero player;
	
	public GameScreen(String playerName, PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(450, 300));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		background = new Background("map9.png");
		createPlayer(playerName, playerClass);
	}

	@Override
	public void update() {
		//HACK: have something that requests focus not so frequently
		requestFocus(true);

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
		};
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			up();
		} 
		if (code == KeyEvent.VK_DOWN) {
			down();
		} 
		if (code == KeyEvent.VK_LEFT) {
			left();
		} 
		if (code == KeyEvent.VK_RIGHT) {
			right();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background.draw(g);
		player.draw(g);
	}
	
	public void up() {
		player.setPosY(player.getPosY() - 10);
	}
	
	public void down() {
		player.setPosY(player.getPosY() + 10);
	}
	
	public void left() {
		player.setPosX(player.getPosX() - 10);
	}
	
	public void right() {
		player.setPosX(player.getPosX() + 10);
	}
}

enum MotionStateLeftRight {
	IDLE, LEFT, RIGHT;
}

enum MotionStateUpDown {
	IDLE, UP, DOWN;
}