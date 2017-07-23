package characterEntities;

import animation.AbilityAnimation;
import gui.DamageMarker;
import screens.GameMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Hero extends Entity {
	public enum PlayerClass {
		RED(0), BLUE(1), YELLOW(2),
		VERMILLION(3), MAGENTA(4), SCARLET(5),
		VIOLET(6), TURQUOISE(7), ULTRAMARINE(8),
		LIME(9), AMBER(10), GOLD(11);
		private int value;

		PlayerClass(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Ability {
		DEFAULT(0), FIRST(1), SECOND(2), THIRD(3), ULTIMATE(4);
		private int value;

		Ability(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	protected HashMap<Integer, String> colourPath;
	protected ArrayList<Entity> targets;
	protected ArrayList<DamageMarker> enemyMarkers;
	protected PlayerClass playerClass;
	int numberEvolutions;

	public static final int SQUARE_LENGTH = 75;
	static final int DEFAULT_RANGE = SQUARE_LENGTH;

	
	Hero(ArrayList<Entity> targets, GameMap map, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
		super(map, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		this.targets = targets;
		for (Entity target : targets) {
			immuneTo.put(target, false);
		}

		enemyMarkers = new ArrayList<>();
		createHeroHashMap();
		setAnimation(0, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.DEFAULT, this));
		numberEvolutions = 0;
		setEntityType(EntityType.HERO);
	}

	@Override
	public void setEntityState(EntityState entityState) {
		//TODO: SHOW GAME OVER MESSAGE IF DEAD
		super.setEntityState(entityState);
		String filepath = "src/assets/hero/";
		filepath += colourPath.get(playerClass.getValue());
		switch (this.getEntityState()) {
			case NEUTRAL:
				filepath += "Neutral";
				break;
			case ATTACKING:
				filepath += "Attacking";
				break;
			case DAMAGED:
				filepath += "Damaged";
				break;
			case DEAD:
				filepath += "Dead";
				break;
			default:
				break;
		}
		filepath += ".png";
		this.setImageIcon(filepath);
	}

	@Override
	public Rectangle getEntitySize() {
		return new Rectangle(posX, posY, SQUARE_LENGTH, SQUARE_LENGTH);
	}

//	public abstract boolean evolve(int path);

	public void attack(Ability ability) {
		if (getEntityState() == EntityState.NEUTRAL) {
			playAnimation(ability.getValue());
			setEntityState(EntityState.ATTACKING);
		}
	}

	protected boolean isHit(Ability ability, Entity target) {
		int targetPosX = target.getPosX();
		int targetPosY = target.getPosY();
		return (((getFacingEast() && targetPosX > posX && targetPosX < posX+SQUARE_LENGTH+DEFAULT_RANGE) ||
				(!getFacingEast() && targetPosX < posX && targetPosX > posX-SQUARE_LENGTH-DEFAULT_RANGE)) &&
				targetPosY > posY-DEFAULT_RANGE && targetPosY < posY+DEFAULT_RANGE && ability == Ability.DEFAULT);
	}
	
//	protected boolean evolutionIncrease(int path) {
//		switch (path) {
//		case PATH_RED:
//			maxHealth += 210;
//			maxDamage += 30;
//			minDamage += 30;
//			break;
//		case PATH_YELLOW:
//			maxHealth += 150;
//			maxDamage += 50;
//			minDamage += 50;
//			break;
//		case PATH_BLUE:
//			maxHealth += 60;
//			maxDamage += 70;
//			maxDamage += 70;
//			break;
//		default:
//			return false;
//		}
//		return true;
//	}


	public ArrayList<DamageMarker> getEnemyMarkers() {
		return enemyMarkers;
	}

	public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}

	public void emptyEnemyMarkers() {
		enemyMarkers.clear();
	}

	private void createHeroHashMap() {
		colourPath = new HashMap<>();
		colourPath.put(0,"red");
		colourPath.put(1,"blue");
		colourPath.put(2,"yellow");
	}

	@Override
	public void update () {
		super.update();
		if (entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) {
			switch (lrMotionState) {
				case LEFT:
					newPosX -= velocity;
					break;
				case RIGHT:
					newPosX += velocity;
					break;
				case IDLE:
				default:
					break;
			}
			switch (udMotionState) {
				case UP:
					newPosY -= velocity;
					break;
				case DOWN:
					newPosY += velocity;
					break;
				case IDLE:
				default:
					break;
			}
		}
		if (currentAbilityAnimation == null) {
			for (Entity target : targets) {
				target.immuneTo.put(this, false);
			}
		}
		setPoint(map.determineMotion(newPosX, newPosY, getEntitySize(), targets));
	}
}
