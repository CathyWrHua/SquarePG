package characterEntities;

import animation.abilities.Ability;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;
import java.util.Collections;
import java.util.HashMap;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public abstract class Enemy extends Entity {
	public enum EnemyType {
		CIRCLE(0);
		private int value;

		EnemyType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	protected int deletionCounter = DELETION_TIME;
	protected boolean done;
	protected EnemyType enemyType;
	protected HashMap<Integer, String> shapePath;
	private Entity targetEntity;

	//for when the enemy AI is targetting random target
	private Point randomTargetPoint;

	private static final int DELETION_TIME = 40;

	Enemy(Entity targetEntity, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		this.targetEntity = targetEntity;

		immuneTo.put(targetEntity, false);
		createEnemyHashMap();

		done = false;
		entityType = EntityType.ENEMY;
		randomTargetPoint = new Point(random.nextInt(900), random.nextInt(700));
	}

	public boolean isDone() {
		return done;
	}

	void setEnemyType(EnemyType enemyType) {
		this.enemyType = enemyType;
	}

	@Override
	public LinkedList<Entity> getTargets() {
		return (new LinkedList<>(Collections.singletonList(targetEntity)));
	}

	@Override
	public Rectangle getEntitySize() {
		return new Rectangle(posX, posY, getImageIcon().getIconWidth(), getImageIcon().getIconHeight());
	}

	@Override
	public void setEntityState(EntityState entityState) {
		super.setEntityState(entityState);
		String filepath = "src/assets/enemies/";
		filepath += shapePath.get(enemyType.getValue());
		switch (entityState) {
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

	//Simple motion detection (pythagorean locating)
	//Default attack pattern, override in child classes for custom moves
	private void calculateNextMove() {
		if (targetEntity == null) return;

		Point selfCenter = new Point(posX + getEntitySize().width / 2, posY + getEntitySize().height / 2);

		Point targetCenter = (targetEntity.isInvisible())? randomTargetPoint : new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width / 2, targetEntity.getPosY() + targetEntity.getEntitySize().height / 2);
		Point motionVector = new Point(targetCenter.x - selfCenter.x, targetCenter.y - selfCenter.y);

		double hypotenuse = Math.sqrt(motionVector.x * motionVector.x + motionVector.y * motionVector.y);

		if (hypotenuse != 0) {
			double scaleFactor = velocity / hypotenuse;
			int deltaX = (int)Math.round(motionVector.x * scaleFactor);
			int deltaY = (int)Math.round(motionVector.y * scaleFactor);

			newPosX += (deltaX == 0 && targetCenter.x != selfCenter.x) ?
					((targetCenter.x > selfCenter.x) ? 1 : -1) : deltaX;
			newPosY += (deltaY == 0 && targetCenter.y != selfCenter.y) ?
					((targetCenter.y > selfCenter.y) ? 1 : -1) : deltaY;
		}

		if (entityState == EntityState.NEUTRAL) {
			setFacingEast(motionVector.x > 0);
		}

		if (Math.abs(motionVector.x) < 100 && Math.abs(motionVector.y) < 100) {
			if (targetEntity.getEntityState() != EntityState.DEAD) {
				attack(EntityAbility.DEFAULT);
			}
			if (targetEntity.isInvisible()) {
				randomTargetPoint = new Point(random.nextInt(900), random.nextInt(700));
			}
		}


	}

	public void update() {
		super.update();

		if (currentAbility == null) {
			resetImmuneTo();
		}

		if (entityState == EntityState.DEAD && deletionCounter-- <= 0) {
			done = true;
		} else if (entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) {
			calculateNextMove();

			if (entityState == EntityState.ATTACKING && currentAbility.getState() != Ability.AbilityState.IS_DONE) {
				calculateTargetsDamage(currentAbility);
			}
		}
		setPoint(mapCollisionDetection.determineMotion(newPosX, newPosY, getEntitySize(), new LinkedList<>(Collections.singletonList(targetEntity))));
	}

	@Override
	public void calculateTargetsDamage(Ability ability) {
		DamageMarker marker;
		if (!targetEntity.immuneTo.get(this) && ability.didHitTarget(targetEntity) && targetEntity.getEntityState() != EntityState.DEAD) {
			marker = targetEntity.inflict(getDamage(), this);
			if (marker != null) {
				targetMarkers.add(marker);
			}
		}
	}

	@Override
	public void resetImmuneTo() {
		targetEntity.immuneTo.put(this, false);
	}

	private void createEnemyHashMap() {
		shapePath = new HashMap<>();
		shapePath.put(0, "circle");
	}
}
