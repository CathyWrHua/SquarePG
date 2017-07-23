package characterEntities;

import animation.AbilityAnimation;
import gui.DamageMarker;

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

	private HashMap<Integer, String> colourPath;
	private PlayerClass playerClass;
	int numberEvolutions;

	static final int PATH_RED = 0;
	static final int PATH_YELLOW = 1;
	static final int PATH_BLUE = 2;
	static final int SQUARE_LENGTH = 75;
	static final int DEFAULT_RANGE = SQUARE_LENGTH;

	void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}
	
	Hero(int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
		super(maxHealth, maxDamage, minDamage, posX, posY, velocity);
		colourPath = new HashMap<>();
		colourPath.put(0, "red");
		colourPath.put(1, "blue");
		colourPath.put(2, "yellow");
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
			case DEFAULT:
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

//	public abstract boolean evolve(int path);

	public abstract ArrayList<DamageMarker> attack(Ability ability, ArrayList<Entity> targets);

	protected abstract boolean isHit(Ability ability, Entity target);
	
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
}
