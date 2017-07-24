package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import animation.Effect;
import animation.EffectAnimation;
import characterEntities.*;
import gui.DamageMarker;

public class GameScreen extends Screen implements KeyListener{
    public enum MapLayer {
        BACKGROUND(0),
        ENTITY_LAYER(1),
        ENTITY_EFFECTS_LAYER(2),
		MAP_EFFECTS_LAYER(3),
        DAMAGE_LAYER(4);

        private int value;

        MapLayer(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public final int TOTAL_MAP_LAYERS = 5;

	private GameMap map;
	private int level = 3;
	private Hero player;
	private ArrayList<Entity> targets;
	private ArrayList<Effect> effects;
	private ArrayList<ArrayList<Drawable>> layerRenderMap;
	private boolean isDoneRendering;

	private HashSet<Integer> motionKeys;
	
	public GameScreen(Hero.PlayerClass playerClass) {
		super();
		setPreferredSize(new Dimension(1000, 1000));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		targets = new ArrayList<>();
		effects = new ArrayList<>();

		motionKeys = new LinkedHashSet<>();
		map = new GameMap(level);
		createLayerRenderMap();
		isDoneRendering = true;

		createPlayer(playerClass);
		createDummy(400, 100, true);
		createDummy(600, 100, false);
		createEnemy(100, 10, 5, 500, 500 ,3);
	}

	@Override
	public void init() {
		requestFocus(true);
	}

	private void createLayerRenderMap() {
		layerRenderMap = new ArrayList<>(TOTAL_MAP_LAYERS);

		ArrayList<Drawable> background = new ArrayList<>();
		background.add(map);

		layerRenderMap.add(MapLayer.BACKGROUND.getValue(), background);
		layerRenderMap.add(MapLayer.ENTITY_LAYER.getValue(), new ArrayList<>());
		layerRenderMap.add(MapLayer.ENTITY_EFFECTS_LAYER.getValue(), new ArrayList<>());
		layerRenderMap.add(MapLayer.MAP_EFFECTS_LAYER.getValue(), new ArrayList<>());
		layerRenderMap.add(MapLayer.DAMAGE_LAYER.getValue(), new ArrayList<>());
	}

	private void createPlayer(Hero.PlayerClass playerClass) {
		switch (playerClass) {
		case RED:
			player = new RedHero(targets, map);
			break;
		case BLUE:
			player = new BlueHero(targets, map);
			break;
		case YELLOW:
			player = new YellowHero(targets, map);
			break;
		default:
		}
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(player);
	}

	private void createEnemy(int health, int maxDamage, int minDamage, int posX, int posY, int velocity) {
		Grunt grunt = new Grunt(player, map, health, maxDamage, minDamage, posX, posY, velocity);
		targets.add(grunt);
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(grunt);
	}

	private void createDummy(int posX, int posY, boolean facingEast) {
		Dummy dummy = new Dummy(posX, posY, facingEast);
		targets.add(dummy);
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(dummy);
	}

	private void createEffectAnimation(EffectAnimation.EffectAnimationType animationType, int posX, int posY) {
		EffectAnimation animation = new EffectAnimation(animationType, posX, posY);
		layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).add(animation);
		effects.add(animation);
	}

	private void createProjectile() {}

    @Override
    public void update() {

		if (!isDoneRendering) return;
		//Effects controlled by entities are cleared
		layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).clear();

		player.update();
		layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(player.getTargetMarkers());
		effects.addAll(player.getTargetMarkers());
		player.emptyTargetMarkers();

		Drawable playerAbility = player.getCurrentAbilityAnimation();
		if (playerAbility != null) {
			layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).add(playerAbility);
		}

		for (Iterator<Entity> iterator = targets.iterator(); iterator.hasNext();) {
			Entity target = iterator.next();
			if (target.getEntityType() == Entity.EntityType.ENEMY) {
				Enemy enemy = (Enemy)target;
				enemy.update();

				Drawable enemyAbility = enemy.getCurrentAbilityAnimation();
				if (enemyAbility != null) {
					layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).add(enemyAbility);
				}

				layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(target.getTargetMarkers());
				effects.addAll(target.getTargetMarkers());
				target.emptyTargetMarkers();

				if (enemy.isDone()) {
					layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).remove(enemy);
					createEffectAnimation(EffectAnimation.EffectAnimationType.ENEMY_DEATH, enemy.getPosX(), enemy.getPosY());
					iterator.remove();
				}
			} else if (target.getEntityType() == Entity.EntityType.DUMMY) {
				target.update();
			}
		}

		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect effect = iterator.next();
			effect.update();
			if (effect.isDone()) {
				layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).remove(effect);
				iterator.remove();
			}
		}

		//TEMPORARY TEST CODE TO REGENERATE ENEMY
		if (targets.size() == 2) {
			createEnemy(100, 10, 7, 100, 100, 2);
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
			level = ((level > 1) ? (level-1) : 1);
			map.setMap(level);
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			level = ((level < 3) ? (level+1) : 3);
			map.setMap(level);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.attack(Hero.Ability.DEFAULT);
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			//HACK: this is just so that we can damage the player in testing, there will be bugs with this (ignore them)
			DamageMarker marker = player.inflict(25, new Dummy(-100, -100, true));
			effects.add(marker);
			layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).add(marker);
		} else if (e.getKeyCode() == KeyEvent.VK_X) {
			player.heal(25);
		} else if (e.getKeyCode() == KeyEvent.VK_C) {
			System.out.println("Player: ("+player.getPosX()+", "+player.getPosY()+")");
			System.out.println("Enemy: ("+targets.get(0).getPosX()+", "+targets.get(2).getPosY()+")");
			System.out.println();
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

		isDoneRendering = false;
		for (int layer = 0; layer < TOTAL_MAP_LAYERS; layer++) {
			ArrayList<Drawable> drawables = layerRenderMap.get(layer);
			if (drawables != null && drawables.size() > 0) {
				for (Drawable drawable : drawables) {
					drawable.draw(g);
				}
			}
		}
		isDoneRendering = true;
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
