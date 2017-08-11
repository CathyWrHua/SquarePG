package characterEntities;

import animation.abilities.Ability;
import animation.abilities.EggplantAbility;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;

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

	protected HashMap<Integer, String> colourPath;
	protected ArrayList<Entity> targets;
	protected PlayerClass playerClass;
	protected CharacterProfile.Path[] path;

	public static final int SQUARE_LENGTH = 75;

	Hero(ArrayList<Entity> targets, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		this.targets = targets;
		for (Entity target : targets) {
			immuneTo.put(target, false);
		}

		createHeroHashMap();
		setAbility(0, new EggplantAbility(this));
		path = new CharacterProfile.Path[3];
		setEntityType(EntityType.HERO);
	}

	@Override
	public void update () {
		super.update();

		if (currentAbility == null) {
			for (Entity target : targets) {
				target.immuneTo.put(this, false);
			}
		}

		if (entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) {
			switch (lrMotionState) {
				case LEFT:
					newPosX -= Math.round(velocity);
					break;
				case RIGHT:
					newPosX += Math.round(velocity);
					break;
				case IDLE:
				default:
					break;
			}
			switch (udMotionState) {
				case UP:
					newPosY -= Math.round(velocity);
					break;
				case DOWN:
					newPosY += Math.round(velocity);
					break;
				case IDLE:
				default:
					break;
			}
		}

		setPoint(mapCollisionDetection.determineMotion(newPosX, newPosY, getEntitySize(), targets));

		if (entityState != EntityState.ATTACKING || currentAbility == null) return;
		DamageMarker marker;
		if (currentAbility.getState() == Ability.AbilityState.CAN_DAMAGE) {
			for (Entity target : targets) {
				if (!target.immuneTo.get(this) &&
						currentAbility.didHitTarget(target) &&
						target.getEntityState() != EntityState.DEAD) {
					marker = target.inflict(currentAbility.dealDamage(getDamage()), this);
					if (marker != null) {
						targetMarkers.add(marker);
					}
				}
			}
		}
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

	public boolean evolve(int pathIndex, CharacterProfile.Path path) {
		//Temporary hack code
		if (pathIndex > 2) return false;
		this.path[pathIndex] = path;

		return true;
	}

//	protected boolean isHit(EntityAbility ability, Entity target) {
//		int targetPosX = target.getPosX();
//		int targetPosY = target.getPosY();
//		return (((getFacingEast() && targetPosX > posX && targetPosX < posX+SQUARE_LENGTH+DEFAULT_RANGE) ||
//				(!getFacingEast() && targetPosX < posX && targetPosX > posX-SQUARE_LENGTH-DEFAULT_RANGE)) &&
//				targetPosY > posY-DEFAULT_RANGE && targetPosY < posY+DEFAULT_RANGE && ability == Entity.Ability.DEFAULT);
//	}
	
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

	@Override
	public ArrayList<Entity> getTargets() {
		return targets;
	}

	public CharacterProfile.Path[] getEvolutionPath() {
		return path;
	}

	public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}

	private void createHeroHashMap() {
		colourPath = new HashMap<>();
		colourPath.put(0, "red");
		colourPath.put(1, "blue");
		colourPath.put(2, "yellow");
	}
}
