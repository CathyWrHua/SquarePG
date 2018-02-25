package characterEntities.enemyEntities;

import animation.abilities.Ability;
import characterEntities.Entity;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;
import screens.GameScreen;

import java.util.Collections;
import java.util.HashMap;

import java.awt.*;
import java.util.LinkedList;

public abstract class Enemy extends Entity {

	protected int deletionCounter = DELETION_TIME;
	protected boolean done;
	protected Entity targetEntity;
	private LinkedList<Entity> entityHitDetectionList;

	//for when the enemy AI is targeting random target
	protected Point randomTargetPoint;
	protected final int RANDOM_POINT_LIFETIME = 60; //updates target every 60 updates
	protected int randomTargetCounter = 0;

	private static final int DELETION_TIME = 40;

	Enemy(Entity targetEntity, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		this.targetEntity = targetEntity;
		this.entityHitDetectionList = new LinkedList<>();

		immuneTo.put(targetEntity, false);

		done = false;
		entityType = EntityType.ENEMY;
		randomTargetPoint = new Point(random.nextInt(GameScreen.GAME_SCREEN_WIDTH), random.nextInt(GameScreen.GAME_SCREEN_HEIGHT));
	}

	public boolean isDone() {
		return done;
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
		filepath += getShapePath();
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
	protected void calculateNextMove() {
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
				attack(EntityAbility.FIRST);
			}
		}

		if (targetEntity.isInvisible() && randomTargetCounter == 0) {
			randomTargetPoint = new Point(random.nextInt(GameScreen.GAME_SCREEN_WIDTH), random.nextInt(GameScreen.GAME_SCREEN_HEIGHT));
			randomTargetCounter = RANDOM_POINT_LIFETIME;
		}
	}

	protected void updateAttack() {
		if (entityState == EntityState.DEAD && deletionCounter-- <= 0) {
			done = true;
		} else if (entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) {
			calculateNextMove();

			if (entityState == EntityState.ATTACKING && currentAbility.getState() != Ability.AbilityState.IS_DONE) {
				calculateTargetsDamage(currentAbility);
			}
		}

	}

	protected void updateMovement() {
		entityHitDetectionList.clear();
		for (Entity entity : targetEntity.getTargets()) {
			if (entity != this) {
				entityHitDetectionList.add(entity);
			}
		}
		entityHitDetectionList.add(targetEntity);
		setPoint(mapCollisionDetection.determineMotion(newPosX, newPosY, getEntitySize(), entityHitDetectionList));
	}

	protected void updateEnemyMisc() {
		if (currentAbility == null) {
			resetImmuneTo();
		}
		randomTargetCounter -= (randomTargetCounter > 0)? 1:0;
	}

	public void update() {
		super.update();
		updateAttack();
		updateMovement();
		updateEnemyMisc();
	}

	@Override
	public void calculateTargetsDamage(Ability ability) {
		DamageMarker marker;
		if (!targetEntity.getImmuneTo(this) && ability.didHitTarget(targetEntity) && targetEntity.getEntityState() != EntityState.DEAD) {
			marker = targetEntity.inflict(getDamage(), this);
			if (marker != null) {
				targetMarkers.add(marker);
			}
		}
	}

	@Override
	public void resetImmuneTo() {
		targetEntity.setImmuneTo(this, false);
	}

	public abstract String getShapePath();
}
