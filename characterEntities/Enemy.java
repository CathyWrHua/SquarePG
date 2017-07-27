package characterEntities;

import gameLogic.MapCollisionDetection;
import gui.DamageMarker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.awt.*;

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
	protected int attackRange;
	private Entity targetEntity;

	private static final int DELETION_TIME = 40;
	private static final int DEFAULT_COOLDOWN = 10;
	private int coolDown = 0;

	Enemy(Entity targetEntity, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		this.targetEntity = targetEntity;

		immuneTo.put(targetEntity, false);
		createEnemyHashMap();

		done = false;
		targetMarkers = new ArrayList<>();
		entityType = EntityType.ENEMY;
	}

	public boolean isDone() {
		return done;
	}

	void setEnemyType(EnemyType enemyType) {
		this.enemyType = enemyType;
	}

	@Override
	public ArrayList<Entity> getTargets() {
		return (new ArrayList<>(Collections.singletonList(targetEntity)));
	}

	@Override
	public Rectangle getEntitySize() {
		return new Rectangle(posX, posY, getImageIcon().getIconWidth(), getImageIcon().getIconHeight());
	}

	public void setTargetEntity(Entity targetEntity) {
		this.targetEntity = targetEntity;
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
		Point targetCenter = new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width / 2, targetEntity.getPosY() + targetEntity.getEntitySize().height / 2);
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
				attack(Ability.DEFAULT);
			}
		}
	}

	public void update() {
		super.update();

		if (currentAbilityAnimation == null) {
			targetEntity.immuneTo.put(this, false);
		}

		if (entityState == EntityState.DEAD && deletionCounter-- <= 0) {
			done = true;
		} else if (entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) {
			calculateNextMove();

			if (entityState == EntityState.ATTACKING) {
				DamageMarker marker;
				if (!targetEntity.immuneTo.get(this) && isHit() && targetEntity.getEntityState() != EntityState.DEAD) {
					marker = targetEntity.inflict(getDamage(), this);
					if (marker != null) {
						targetMarkers.add(marker);
					}
				}
			}
		}

		setPoint(mapCollisionDetection.determineMotion(newPosX, newPosY, getEntitySize(), new ArrayList<>(Collections.singletonList(targetEntity))));
	}

	public boolean isHit() {
		boolean hit = false;
		int targetPosX = targetEntity.getPosX();
		int targetPosY = targetEntity.getPosY();
		if (((getFacingEast() && targetPosX > posX && targetPosX < posX+getEntitySize().width+attackRange) ||
				(!getFacingEast() && targetPosX < posX && targetPosX > posX-getEntitySize().width-attackRange)) &&
				targetPosY > posY-attackRange && targetPosY < posY+attackRange) {
			hit = true;
		}
		return hit;
	}

	private void createEnemyHashMap() {
		shapePath = new HashMap<>();
		shapePath.put(0, "circle");
	}
}
