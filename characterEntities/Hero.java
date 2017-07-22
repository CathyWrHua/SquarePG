package characterEntities;

import java.util.ArrayList;

public abstract class Hero extends Entity {
	public enum PlayerClass {
		RED, BLUE, YELLOW,
		VERMILLION, MAGENTA, SCARLET,
		VIOLET, TURQUOISE, ULTRAMARINE,
		LIME, AMBER, GOLD
	}
	private String colour = "";
	private PlayerClass playerClass;
	int numberEvolutions;

	static final int PATH_RED = 0;
	static final int PATH_YELLOW = 1;
	static final int PATH_BLUE = 2;
	static final int SQUARE_LENGTH = 75;
	static final int DEFAULT_RANGE = SQUARE_LENGTH;

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

	void setColour(String colour) {
		this.colour = colour;
	}

	void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}
	
	Hero(int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
		super(maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setAnimation(0, new Animation(Animation.AnimationType.DEFAULT, this));
		numberEvolutions = 0;
	}

	@Override
	public void setEntityState(EntityState entityState) {
		//TODO: SHOW GAME OVER MESSAGE IF DEAD
		super.setEntityState(entityState);
		String filepath = "src/assets/hero/";
		filepath += this.colour;
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
	
	public abstract boolean evolve(int path);

	public abstract void attack(Ability ability, ArrayList<Entity> targets);

	protected abstract boolean isHit(Ability ability, Entity target);
	
	protected boolean evolutionIncrease(int path) {
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
		return true;
	}
}
