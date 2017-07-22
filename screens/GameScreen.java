package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;
import characterEntities.*;

public class GameScreen extends Screen implements KeyListener{
	private GameMap map;
	private int level = 1;
	private Hero player;
	private ArrayList<Entity> enemies = new ArrayList<>();
	private Set<Integer> motionKeys = new LinkedHashSet<>();
	
	public GameScreen(Hero.PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(1000, 1000));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		map = new GameMap(level);
		createPlayer(playerClass);
		createEnemy(1000);
	}

	@Override
	public void update() {
		//HACK: have something that requests focus not so frequently
		requestFocus(true);
		//TODO:Remove all magic width and heights with actual ones

		Rectangle originalPlayer = new Rectangle(player.getPosX(), player.getPosY(),75,  75);
		player.update();

		for (Entity enemy: enemies) {
			Rectangle originalEnemy = new Rectangle(enemy.getPosX(), enemy.getPosY(), 75, 75);
			enemy.update();
			enemy.setPoint(map.determineMotion(enemy.getPosX(), enemy.getPosY(), originalEnemy));
		}
		map.setCurrentEntityList(enemies);
		player.setPoint(map.determineMotion(player.getPosX(), player.getPosY(), originalPlayer));
	}
	
	private void createPlayer(Hero.PlayerClass playerClass) {
		switch (playerClass) {
		case RED:
			player = new RedHero();
			break;
		case BLUE:
			player = new BlueHero();
			break;
		case YELLOW:
			player = new YellowHero();
			break;
		default:
		}
	}

	private void createEnemy(int health) {
		Enemy dummy = new Enemy(health, 0, 0);
		enemies.add(dummy);
	}

	private void createProjectile() {}

	@Override
	public void keyPressed(KeyEvent e) {

		//maintains proper order
		if (motionKeys.contains(e.getKeyCode())) {
			motionKeys.remove(e.getKeyCode());
		}
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
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_J) {
			map.setMap(1);
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			map.setMap(2);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.attack(Hero.Ability.DEFAULT, enemies);
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			player.inflict(25);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		Integer code = e.getKeyCode();
		
		if (code == KeyEvent.VK_DOWN ) {
			player.setUDMotionState(motionKeys.contains(KeyEvent.VK_UP)? Entity.MotionStateUpDown.UP : Entity.MotionStateUpDown.IDLE);
		} else if (code == KeyEvent.VK_UP){
			player.setUDMotionState(motionKeys.contains(KeyEvent.VK_DOWN)? Entity.MotionStateUpDown.DOWN : Entity.MotionStateUpDown.IDLE);
		} else if (code == KeyEvent.VK_LEFT) {
			player.setLRMotionState(motionKeys.contains(KeyEvent.VK_RIGHT)? Entity.MotionStateLeftRight.RIGHT : Entity.MotionStateLeftRight.IDLE);
		} else if (code == KeyEvent.VK_RIGHT) {
			player.setLRMotionState(motionKeys.contains(KeyEvent.VK_LEFT)? Entity.MotionStateLeftRight.LEFT : Entity.MotionStateLeftRight.IDLE);
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
		player.setUDMotionState(Entity.MotionStateUpDown.UP);
	}
	
	private void down() {
		player.setUDMotionState(Entity.MotionStateUpDown.DOWN);
	}
	
	private void left() {
		player.setLRMotionState(Entity.MotionStateLeftRight.LEFT);
	}
	
	private void right() {
		player.setLRMotionState(Entity.MotionStateLeftRight.RIGHT);
	}
}
