package gameLogic;

import animation.Effect;
import animation.MapAnimation;
import animation.ProjectileAnimation;
import characterEntities.*;
import gui.AbilityBar;
import gui.DamageMarker;
import screens.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class GameEngine {
	public enum MapLayer {
		BACKGROUND(0),
		ENTITY_LAYER(1),
		ENTITY_EFFECTS_LAYER(2),
		MAP_EFFECTS_LAYER(3),
		DAMAGE_LAYER(4),
		GUI_LAYER(5);

		private int value;

		MapLayer(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private final int TOTAL_MAP_LAYERS = 6;

	private GameMap map;
	private MapCollisionDetection collisionMap;

	private int level = 3;
	private boolean isDoneRendering = true;
	private Hero player;
	private AbilityBar playerAbilityBar;
	private ArrayList<Entity> targets;
	private ArrayList<Effect> effects;
	private ArrayList<ArrayList<Drawable>> layerRenderMap;

	public GameEngine(Hero.PlayerClass playerClass) {
		targets = new ArrayList<>();
		effects = new ArrayList<>();

		map = new GameMap(level);
		collisionMap = new MapCollisionDetection(map.getCurrentCollisionMap());
		createLayerRenderMap();
		isDoneRendering = true;

		createPlayer(playerClass);
		createAbilityBar(player);
		createDummy(400, 100, true);
		createDummy(600, 100, false);
		createEnemy(20, 2, 1, 500, 500 ,2);
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
		layerRenderMap.add(MapLayer.GUI_LAYER.getValue(), new ArrayList<>());
	}

	public void update() {
		if (!isDoneRendering) return;

		//TEMPORARY REMOVE WHEN WE HAVE LEGIT MAPS
		//This is to avoid asynchronous heisenbugs of overwriting collision maps while a calculation is going on
		//Can be removed once tests keys are removed (J and K)
		collisionMap.setHitRectArray(map.getCurrentCollisionMap());
		layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).clear();
		player.update();

		Effect playerAbility = player.getCurrentAbilityAnimation();
		if (playerAbility != null) layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).add(playerAbility);

		layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(player.getTargetMarkers());
		effects.addAll(player.getTargetMarkers());
		player.emptyTargetMarkers();

		layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).addAll(player.getProjectileAnimations());
		effects.addAll(player.getProjectileAnimations());
		player.emptyProjectileAnimations();

		for (Iterator<Entity> iterator = targets.iterator(); iterator.hasNext();) {
			Entity target = iterator.next();
			if (target.getEntityType() == Entity.EntityType.ENEMY) {
				Enemy enemy = (Enemy)target;
				enemy.update();

				Effect enemyAbility = enemy.getCurrentAbilityAnimation();
				if (enemyAbility != null) {
					layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).add(enemyAbility);
				}

				layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(enemy.getTargetMarkers());
				effects.addAll(enemy.getTargetMarkers());
				target.emptyTargetMarkers();

				layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).addAll(enemy.getProjectileAnimations());
				effects.addAll(enemy.getProjectileAnimations());
				target.emptyProjectileAnimations();

				if (enemy.isDone()) {
					layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).remove(enemy);
					createMapAnimation(MapAnimation.MapAnimationType.ENEMY_DEATH, enemy.getCenterX(), enemy.getCenterY());
					iterator.remove();
				}
			} else if (target.getEntityType() == Entity.EntityType.DUMMY) {
				target.update();
			}
		}

		ArrayList<MapAnimation> projectileEndAnimations = new ArrayList<>();
		ArrayList<DamageMarker> damageMarkers = new ArrayList<>();
		MapAnimation currentEndAnimation;
		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect effect = iterator.next();
			effect.update();

			if (effect.isDone()) {
				if (effect.getEffectType() == Effect.EffectType.PROJECTILE_EFFECT) {
					ProjectileAnimation animation = (ProjectileAnimation) effect;
					damageMarkers.addAll(animation.getTargetMarkers());
					currentEndAnimation = determineEndAnimation(animation);
					if (currentEndAnimation != null) {
						projectileEndAnimations.add(currentEndAnimation);
					}
					animation.clearTargetMarkers();
				}
				layerRenderMap.get(effect.getEffectType().getValue()).remove(effect);
				iterator.remove();
			}
		}
		effects.addAll(damageMarkers);
		effects.addAll(projectileEndAnimations);
		layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(damageMarkers);
		layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).addAll(projectileEndAnimations);

		//TEMPORARY TEST CODE TO REGENERATE ENEMY
		if (targets.size() == 2) {
			createEnemy(20, 2, 1, 100, 100, 2);
		}
	}

	public void paint(Graphics g) {
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

	public void playerDidMoveUp() {
		player.setUDMotionState(Entity.MotionStateUpDown.UP);
	}

	public void playerDidStopMovingUp(boolean containsDown) {
		player.setUDMotionState(containsDown? Entity.MotionStateUpDown.DOWN : Entity.MotionStateUpDown.IDLE);
	}

	public void playerDidMoveDown() {
		player.setUDMotionState(Entity.MotionStateUpDown.DOWN);
	}

	public void playerDidStopMovingDown(boolean containsUp) {
		player.setUDMotionState(containsUp? Entity.MotionStateUpDown.UP : Entity.MotionStateUpDown.IDLE);
	}

	public void playerDidMoveLeft() {
		player.setLRMotionState(Entity.MotionStateLeftRight.LEFT);
	}

	public void playerDidStopMovingLeft(boolean containsRight) {
		player.setLRMotionState(containsRight? Entity.MotionStateLeftRight.RIGHT : Entity.MotionStateLeftRight.IDLE);
	}

	public void playerDidMoveRight() {
		player.setLRMotionState(Entity.MotionStateLeftRight.RIGHT);
	}

	public void playerDidStopMovingRight(boolean containsLeft) {
		player.setLRMotionState(containsLeft? Entity.MotionStateLeftRight.LEFT : Entity.MotionStateLeftRight.IDLE);
	}

	public void toggleMapLevel(int change) {
		this.level += change;
		if (level > 3) {
			level = 3;
		} else if (level < 1) {
			level = 1;
		}
		map.setMap(level);
	}

	public void playerDidAttack(Entity.Ability ability) {
		player.attack(ability);
	}

	//Temporary hack to damage the player in testing
	public void playerWasAttacked() {
		DamageMarker marker = player.inflict(3, new Dummy(-100, -100, true));
		effects.add(marker);
		layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).add(marker);
	}

	//Temporary hack to test healing
	public void playerDidHeal(int heal) {
		player.heal(heal);
	}

	public Hero getPlayer() {
		return player;
	}

	// Private helper functions
	//
	//Remove temporary test code where required

	private void createAbilityBar(Hero player) {
		this.playerAbilityBar = new AbilityBar(player);
		layerRenderMap.get(MapLayer.GUI_LAYER.getValue()).add(playerAbilityBar);
	}

	private void createPlayer(Hero.PlayerClass playerClass) {
		switch (playerClass) {
			case RED:
				player = new RedHero(targets, collisionMap);
				break;
			case BLUE:
				player = new BlueHero(targets, collisionMap);
				break;
			case YELLOW:
				player = new YellowHero(targets, collisionMap);
				break;
			default:
		}
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(player);
	}

	private void createEnemy(int health, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		Grunt grunt = new Grunt(player, collisionMap, health, maxDamage, minDamage, posX, posY, velocity);
		targets.add(grunt);
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(grunt);
	}

	private void createDummy(int posX, int posY, boolean facingEast) {
		Dummy dummy = new Dummy(posX, posY, facingEast);
		targets.add(dummy);
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(dummy);
	}

	private void createMapAnimation(MapAnimation.MapAnimationType animationType, int posX, int posY) {
		MapAnimation animation = new MapAnimation(animationType, posX, posY);
		layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).add(animation);
		effects.add(animation);
	}

	private MapAnimation determineEndAnimation(ProjectileAnimation projectile) {
		MapAnimation endAnimation;
		switch (projectile.getAnimationType()) {
			case BLUE_FIRST:
				endAnimation = new MapAnimation(MapAnimation.MapAnimationType.FIREBALL, projectile.getEndX(), projectile.getEndY());
				break;
			default:
				return null;
		}
		return endAnimation;
	}
}
