package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;

import java.awt.*;
import java.util.LinkedList;

public abstract class Projectile extends Effect {
	protected int velocityX, velocityY;
	protected int damage;
	protected boolean facingEast;
	protected boolean done;
	protected  MapCollisionDetection mapCollision;
	protected LinkedList<Entity> targets;
	protected LinkedList<DamageMarker> targetMarkers;

	public Projectile(Entity entity, MapCollisionDetection mapCollision, Animation regular, Animation collision, int velocityX, int velocityY) {
		super(entity.getPosX(), entity.getPosY(), regular, collision, EffectType.PROJECTILE_EFFECT);
		this.mapCollision = mapCollision;
		this.facingEast = entity.getFacingEast();
		this.targets = entity.getTargets();
		this.damage = entity.getDamage();
		this.targets = entity.getTargets();

		this.velocityX = velocityX;
		this.velocityX *= (facingEast)? 1: -1;
		this.velocityY = velocityY;
		this.targetMarkers = new LinkedList<>();

		if (regularAnimation != null) {
			regularAnimation.shouldMirror(!this.facingEast);
		}
		if (collisionAnimation != null) {
			collisionAnimation.shouldMirror(!this.facingEast);
		}
	}

	public LinkedList<DamageMarker> getTargetMarkers() {
		return targetMarkers;
	}

	public void clearTargetMarkers() {
		targetMarkers.clear();
	}

	public boolean isDone() {
		return (super.isDone() || done);
	}

	protected boolean isCollide() {
		posX += velocityX;
		posY += velocityY;

		regularAnimation.setPosition(posX, posY);

		boolean collision = mapCollision.detectCollision(regularAnimation.getSize(), targets);
		if (collision) dealDamage();
		return collision;
	}

	protected void dealDamage() {
		Entity targetHit = null;
		Rectangle projectileSize = regularAnimation.getSize();
		DamageMarker marker;

		for (Entity target : targets) {
			if (HitDetectionHelper.detectHit(projectileSize, target.getEntitySize()) && (targetHit == null ||
					(target.getPosX() < targetHit.getPosX()))) {
				targetHit = target;
			}
		}
		if (targetHit == null) return;

		int x = (regularAnimation == null)? posX: posX+regularAnimation.getOffsetX();
		marker = targetHit.inflict(damage, x < targetHit.getPosX());
		if (marker != null) {
			targetMarkers.add(marker);
		}
	}
}
