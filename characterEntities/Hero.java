package characterEntities;

import animation.abilities.Ability;
import animation.abilities.EggplantAbility;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

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
	protected LinkedList<Entity> targets;
	protected PlayerClass playerClass;
	protected CharacterProfile.Path[] path;

	Hero(LinkedList<Entity> targets, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		this.targets = targets;
		for (Entity target : targets) {
			immuneTo.put(target, false);
		}

		createHeroHashMap();
		//setAbility(0, new EggplantAbility(this));
		path = new CharacterProfile.Path[3];
		setEntityType(EntityType.HERO);
	}

	@Override
	public void update () {
		super.update();

		if (currentAbility == null) {
			resetImmuneTo();
		}

		if ((entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) &&
				(currentAbility == null || (currentAbility != null && !currentAbility.isRestrictingMovement()))) {
			newPosX = posX;
			newPosY = posY;
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

			if (newPosX != posX || newPosY != posY) {
				setPoint(mapCollisionDetection.determineMotion(newPosX, newPosY, getEntitySize(), targets));
			}
		}

		if (entityState != EntityState.ATTACKING || currentAbility == null) return;
		if (currentAbility.getState() == Ability.AbilityState.CAN_DAMAGE) {
			calculateTargetsDamage(currentAbility);
		}
	}

	@Override
	public void calculateTargetsDamage(Ability ability) {
		DamageMarker marker;
		for (Entity target : targets) {
			if (!target.immuneTo.get(this) &&
					ability.didHitTarget(target) &&
					target.getEntityState() != EntityState.DEAD) {
				marker = target.inflict(ability.dealDamage(getDamage()), this);
				if (marker != null) {
					targetMarkers.add(marker);
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
		return new Rectangle(posX, posY, DEFAULT_ENTITY_LENGTH, DEFAULT_ENTITY_LENGTH);
	}

	public boolean evolve(int pathIndex, CharacterProfile.Path path) {
		//Temporary hack code
		if (pathIndex > 2) return false;
		this.path[pathIndex] = path;

		return true;
	}

	@Override
	public void resetImmuneTo() {
		for (Entity target : targets) {
			target.immuneTo.put(this, false);
		}
	}

	@Override
	public int getCenterX() {
		return posX + DEFAULT_ENTITY_LENGTH/2;
	}

	@Override
	public int getCenterY() {
		return posY + DEFAULT_ENTITY_LENGTH/2;
	}

//	protected boolean isHit(EntityAbility ability, Entity target) {
//		int targetPosX = target.getPosX();
//		int targetPosY = target.getPosY();
//		return (((getFacingEast() && targetPosX > posX && targetPosX < posX+DEFAULT_ENTITY_LENGTH+DEFAULT_RANGE) ||
//				(!getFacingEast() && targetPosX < posX && targetPosX > posX-DEFAULT_ENTITY_LENGTH-DEFAULT_RANGE)) &&
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
	public LinkedList<Entity> getTargets() {
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
