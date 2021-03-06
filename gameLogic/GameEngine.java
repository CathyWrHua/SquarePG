package gameLogic;

import SquarePG.SquarePG;
import animation.abilities.Ability;
import animation.effects.Effect;
import animation.effects.enemyEffects.EnemyDeathEffect;
import animation.effects.Projectile;
import characterEntities.*;
import characterEntities.enemyEntities.Dummy;
import characterEntities.enemyEntities.Enemy;
import gui.AbilityBar;
import gui.DamageMarker;
import javafx.util.Pair;
import screens.Drawable;
import screens.GameScreen;

import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameEngine {
	public enum MapLayer {
		BACKGROUND(0),
		BACKGROUND_EFFECTS_LAYER(1),
		ENTITY_LAYER(2),
		ENTITY_EFFECTS_LAYER(3),
		MAP_EFFECTS_LAYER(4),
		DAMAGE_LAYER(5),
		GUI_LAYER(6);

		private int value;

		MapLayer(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum GameEnemyUpdateState {
		REGULAR,
		STOPPED
	}

	private final int TOTAL_MAP_LAYERS = 7;
	private final int FINISHED_MAP_COUNDOWN = SquarePG.FPS;

	private GameMap gameMap;

	//collisionMap should be a static singleton
	//TODO: make this static and a singleton
	private MapCollisionDetection collisionMap;

	private int level = 1;
	private int map = 1;
	private boolean doneRendering = true;
	private boolean currentWaveFinished = false;
	private boolean currentMapFinished = false;
	private boolean failCurrentMap = false;
	private int mapFinishedCounter = FINISHED_MAP_COUNDOWN;
	private Hero player;
	private AbilityBar playerAbilityBar;
	private LinkedList<Entity> targets;
	private LinkedList<Effect> effects;
	private ArrayList<LinkedList<Drawable>> layerRenderMap;

	//For testing with dummies on the map, remove when dummies are removed
	private final int NUMBER_DUMMIES = 0;

	public GameEngine(Hero.PlayerClass playerClass) {
		targets = new LinkedList<>();
		effects = new LinkedList<>();

		gameMap = new GameMap(level, map);
		collisionMap = new MapCollisionDetection(gameMap.getCurrentCollisionMap());
		createLayerRenderMap();
		doneRendering = true;

		createPlayer(playerClass);
		createAbilityBar(player);
	}

	private void createLayerRenderMap() {
		layerRenderMap = new ArrayList<>(TOTAL_MAP_LAYERS);

		LinkedList<Drawable> background = new LinkedList<>();
		background.add(gameMap);

		layerRenderMap.add(MapLayer.BACKGROUND.getValue(), background);
		layerRenderMap.add(MapLayer.BACKGROUND_EFFECTS_LAYER.getValue(), new LinkedList<>());
		layerRenderMap.add(MapLayer.ENTITY_LAYER.getValue(), new LinkedList<>());
		layerRenderMap.add(MapLayer.ENTITY_EFFECTS_LAYER.getValue(), new LinkedList<>());
		layerRenderMap.add(MapLayer.MAP_EFFECTS_LAYER.getValue(), new LinkedList<>());
		layerRenderMap.add(MapLayer.DAMAGE_LAYER.getValue(), new LinkedList<>());
		layerRenderMap.add(MapLayer.GUI_LAYER.getValue(), new LinkedList<>());
	}

	public void update() {
		if (!doneRendering) return;

		//TEMPORARY: REMOVE WHEN WE HAVE LEGIT MAPS
		//This is to avoid asynchronous heisenbugs of overwriting collision maps while a calculation is going on
		//Can be removed once tests keys are removed (J and K)
		collisionMap.setHitRectArray(gameMap.getCurrentCollisionMap());

		layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).clear();
		player.update();

		Ability playerAbility = player.getCurrentAbility();
		if (playerAbility != null) layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).add(playerAbility);

		if (playerAbility != null && playerAbility.gameUpdateStateEffect() == GameEnemyUpdateState.STOPPED) {
			//do nothing for now (?)
		} else {
			layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(player.getTargetMarkers());
			effects.addAll(player.getTargetMarkers());
			player.emptyTargetMarkers();

			LinkedList<Effect> playerEffects = player.getEffects();
			effects.addAll(playerEffects);
			if (playerEffects != null && playerEffects.size() > 0) {
				for (Effect effect : playerEffects) {
					if (effect.getEffectType() == Effect.EffectType.BACKGROUND_EFFECT) {
						layerRenderMap.get(MapLayer.BACKGROUND_EFFECTS_LAYER.getValue()).add(effect);
					} else {
						layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).add(effect);
					}
				}
			}
			player.emptyEffects();

			updateEnemies();
			updateEffects();
		}

		if (player.getEntityState() == Entity.EntityState.DEAD) {
			failCurrentMap = true;
		}
	}

	public void paint(Graphics g) {
		doneRendering = false;
		for (int layer = 0; layer < TOTAL_MAP_LAYERS; layer++) {
			LinkedList<Drawable> drawables = layerRenderMap.get(layer);
			if (drawables != null && drawables.size() > 0) {
				for (Drawable drawable : drawables) {
					drawable.draw(g);
				}
			}
		}
		doneRendering = true;
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

	//Debug mode only for changing maps
	public void toggleMap(int change) {
		map--;
		map = Math.floorMod(map+change, GameMap.MAPS_PER_LEVEL);
		map++;
		gameMap.setStage(level, map);
	}

	public void playerDidAttack(Entity.EntityAbility ability) {
		player.attack(ability);
	}

	//Debug mode only for taking damage
	public void playerWasAttacked() {
		DamageMarker marker = player.inflict(3, new Dummy(-100, -100, true));
		effects.add(marker);
		layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).add(marker);
	}

	//Debug mode only for healing
	public void playerDidHeal(int heal) {
		player.heal(heal);
	}

	public Hero getPlayer() {
		return player;
	}

	public AbilityBar getPlayerAbilityBar() {
		return playerAbilityBar;
	}

	public boolean isCurrentMapFinished() {
		return currentMapFinished;
	}

	public void setCurrentMapFinished(boolean mapFinished) {
		currentMapFinished = mapFinished;
	}

	public void reset() {
		if (layerRenderMap != null) {
			layerRenderMap.get(MapLayer.BACKGROUND_EFFECTS_LAYER.getValue()).clear();
			layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).clear();
			layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).clear();
			layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).clear();
			layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).clear();

			targets.clear();
			effects.clear();

			if (player != null) {
				player.resetEntity();
				player.setPoint(new Point(GameScreen.GAME_SCREEN_LEFT_BOUNDARY, GameScreen.GAME_SCREEN_TOP_BOUNDARY));
				layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(player);
			}
		}
	}

	public void setMap(int map) {
		if (map > 0 && map <= GameMap.MAPS_PER_LEVEL) {
			this.map = map;
			gameMap.setStage(level, this.map);
		}
	}

	public Pair<Integer, Integer> unlockNext() {
		int nextLevel = map == 10 ? level + 1: level;
		int nextMap = map == 10 ? 1 : map + 1;
		return new Pair<>(nextLevel, nextMap);
	}

	public Pair<Integer, Integer> getCurrentLevel() {
		return new Pair<>(level, map);
	}

	public boolean getFailCurrentMap() {
		return failCurrentMap;
	}

	public void setFailCurrentMap(boolean currentMap) {
		this.failCurrentMap = currentMap;
	}

	// Debug method to quickly clear level
	public void killAllEnemies() {
		for (Entity target: targets) {
			DamageMarker marker = target.inflict(99999, true);
			effects.add(marker);
			layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).add(marker);
		}
	}

	// Private helper functions
	private void updateEnemies() {
		for (Iterator<Entity> iterator = targets.iterator(); iterator.hasNext();) {
			Entity target = iterator.next();
			if (target.getEntityType() == Entity.EntityType.ENEMY) {
				Enemy enemy = (Enemy)target;
				enemy.update();

				Ability enemyAbility = enemy.getCurrentAbility();
				if (enemyAbility != null) {
					layerRenderMap.get(MapLayer.ENTITY_EFFECTS_LAYER.getValue()).add(enemyAbility);
				}

				layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(enemy.getTargetMarkers());
				effects.addAll(enemy.getTargetMarkers());
				target.emptyTargetMarkers();

				LinkedList<Effect> enemyEffects = enemy.getEffects();
				effects.addAll(enemyEffects);
				if (enemyEffects != null && enemyEffects.size() > 0) {
					for (Effect effect : enemyEffects) {
						if (effect.getEffectType() == Effect.EffectType.BACKGROUND_EFFECT) {
							layerRenderMap.get(MapLayer.BACKGROUND_EFFECTS_LAYER.getValue()).add(effect);
						} else {
							layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).add(effect);
						}
					}
				}
				enemy.emptyEffects();

				if (enemy.isDone()) {
					player.notifyEnemyDeath(enemy);
					layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).remove(enemy);
					Effect deathEffect = new EnemyDeathEffect(enemy.getCenterX(), enemy.getCenterY());
					layerRenderMap.get(MapLayer.MAP_EFFECTS_LAYER.getValue()).add(deathEffect);
					effects.add(deathEffect);
					iterator.remove();
				}
			} else if (target.getEntityType() == Entity.EntityType.DUMMY) {
				target.update();
			}
		}

		//Generate enemies
		Queue<WaveGenInfo> queue = GameMapPresets.getEnemyWaveInfo()[level-1][map-1];
		WaveGenInfo currentWave = (queue != null)? queue.peek(): null;
		if (currentWave != null) {
			if (currentWave.waveIsComplete()) {
				if (targets.size() <= NUMBER_DUMMIES) {
					GameMapPresets.getEnemyWaveInfo()[level-1][map-1].remove();
				}
			} else if (currentWave.shouldGenerateNextEnemy()){
				Enemy enemy = createEnemyFromInfo(currentWave.getNextEnemyInfo());
				targets.add(enemy);
				player.notifyEnemyCreation(enemy);
				layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(enemy);
			} else {
				currentWave.decrementCounter();
			}
		} else {
			if (SquarePG.gameMode == GameMode.PLAY) {
				if (mapFinishedCounter > 0) {
					mapFinishedCounter--;
				} else {
					currentMapFinished = true;
				}
			} else if (SquarePG.gameMode == GameMode.DEBUG){
				//Nothing?
			}
		}
	}

	private void updateEffects() {
		LinkedList<DamageMarker> damageMarkers = new LinkedList<>();

		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect effect = iterator.next();
			effect.update();

			Projectile projectile = (effect instanceof Projectile ? (Projectile)effect : null);

			if (projectile != null) {
				damageMarkers.addAll(projectile.getTargetMarkers());
				projectile.clearTargetMarkers();
			}

			if (effect.isDone()) {
				layerRenderMap.get(effect.getEffectType().getValue()).remove(effect);
				iterator.remove();
			}
		}
		effects.addAll(damageMarkers);
		layerRenderMap.get(MapLayer.DAMAGE_LAYER.getValue()).addAll(damageMarkers);
	}

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

	private Enemy createEnemyFromInfo(EnemyGenInfo info) {
		Enemy enemy = null;

		try {
			enemy = (Enemy) info.getEnemyClass().getConstructor(Hero.class, MapCollisionDetection.class, int.class, int.class, int.class, int.class, int.class, double.class)
					.newInstance(player, collisionMap, info.getMaxHealth(), info.getMaxDamage(), info.getMinDamage(), info.getSpawnLocation().x, info.getSpawnLocation().y, info.getVelocity());
		} catch (Exception e) {
			System.out.print(e);
			//todo lots of error catching
		}

		return enemy;
	}

	private void createDummy(int posX, int posY, boolean facingEast) {
		Dummy dummy = new Dummy(posX, posY, facingEast);
		targets.add(dummy);
		player.notifyEnemyCreation(dummy);
		layerRenderMap.get(MapLayer.ENTITY_LAYER.getValue()).add(dummy);
	}
}
