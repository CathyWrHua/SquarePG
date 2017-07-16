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
	private GameState state = GameState.WORLDMAP;
	
	//TODO:depending on map design, make background class for collision detection
	private Background background;
	private int mapNumber = 9;
	private Hero player;
	
	
	
	public GameScreen(String playerName, PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(450, 300));
		
		//background = new Background();
		background = new Background("map5.png");
		createPlayer(playerName, playerClass);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background.draw(g);
	}
}
