package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import gameLogic.GameEngine;
import characterEntities.*;

public class GameScreen extends Screen implements KeyListener {
	public enum GameState {
		GAME_STATE_MAP,
		GAME_STATE_HELP,
		GAME_STATE_PROFILE //For now
	}

	private GameEngine gameEngine;
	private HashSet<Integer> motionKeys;
	private GameState gameState = GameState.GAME_STATE_MAP;
	
	public GameScreen(Hero.PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(1000, 1000));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		gameEngine = new GameEngine(playerClass);
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
				//something else
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (gameState == GameState.GAME_STATE_MAP) {
			gameEngine.paint(g);
		}
	}
}