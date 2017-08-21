package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.Hero;
import characterEntities.HitDetectionHelper;
import gui.DamageMarker;
import javafx.scene.shape.Circle;

import java.awt.*;

public class ExplodingKnifeProjectile extends Projectile {

	static final int KNIFE_VELOCITY = 8;
	private static int KNIFE_WIDTH = 100;
	private static int EXPLOSION_WIDTH = 75;
	private static int EXPLOSION_RADIUS = 100;

	private Animation detonationAnimation;
	private boolean hasDetonated = false;
	private Entity collidedTarget;

	public ExplodingKnifeProjectile(Hero entity) {
		super(entity,
				entity.getMapCollisionDetection(),
				null,
				null,
				KNIFE_VELOCITY,
				0);
		this.detonationAnimation = new Animation(0, 0, 0, 0,FILEPATH_EFFECTS + "fireballExplosion", 4, 1);
	}

	public void detonate() {
		hasDetonated = true;
		posX = facingEast? regularAnimation.getSize().x+KNIFE_WIDTH/2 : regularAnimation.getSize().x-EXPLOSION_WIDTH/2;
		posY = regularAnimation.getSize().y+regularAnimation.getSize().height/2 - EXPLOSION_WIDTH/2;
		this.detonationAnimation.setPosition(posX, posY);

		findDetonationDamage();
	}

	public void update() {
		if (!hasDetonated) {
			super.update();
			if (!hasCollided && isCollide()) {
				setHasCollided(true);

				regularAnimation.killAnimation();
				collisionAnimation.setPosition(posX, posY);
			} else if (hasCollided) {
				if (collidedTarget != null) {
					posX = collidedTarget.getPosX();
					posY = collidedTarget.getPosY();
				}
				collisionAnimation.setPosition(posX, posY);
			} else if (!hasCollided){
				regularAnimation.setPosition(posX, posY);
			}
		} else {
			if (!detonationAnimation.isDone()) {
				detonationAnimation.update();
			}
		}
	}

	public boolean isDone() {
		return (detonationAnimation.isDone() || (hasCollided && collidedTarget != null && collidedTarget.getEntityState() == Entity.EntityState.DEAD));
	}

	@Override
	protected boolean isCollide() {
		posX += velocityX;
		posY += velocityY;

		regularAnimation.setPosition(posX, posY);

		boolean collision = mapCollision.detectCollision(regularAnimation.getSize(), targets);
		if (collision) findCollision();
		return collision;
	}

	private void findCollision() {
		Entity targetHit = null;
		Rectangle projectileSize = regularAnimation.getSize();

		for (Entity target : targets) {
			if (HitDetectionHelper.detectHit(projectileSize, target.getEntitySize()) && (targetHit == null ||
					(target.getPosX() < targetHit.getPosX()))) {
				targetHit = target;
			}
		}

		this.collidedTarget = targetHit;
	}

	private void findDetonationDamage() {
		if (targets == null) return;

		Circle hitArea = new Circle(posX+EXPLOSION_WIDTH/2, posY+EXPLOSION_WIDTH/2, EXPLOSION_RADIUS);
		for (Entity target : targets) {
			if (HitDetectionHelper.detectHit(hitArea, target.getEntitySize())) {
				dealDamage(damage, target);
			}
		}
	}

	private void dealDamage(int damage, Entity entity) {
		DamageMarker damageMarker = null;
		damageMarker = entity.inflict(damage, posX < entity.getPosX());

		if (damageMarker != null) {
			targetMarkers.add(damageMarker);
		}
	}

}
