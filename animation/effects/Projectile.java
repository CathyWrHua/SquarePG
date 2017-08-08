package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;

import java.awt.*;
import java.util.ArrayList;

public abstract class Projectile extends Effect {
	protected int velocityX, velocityY;
	private int damage;
	protected boolean facingEast;
	protected boolean done;
	private MapCollisionDetection mapCollision;
	private ArrayList<Entity> targets;
	private ArrayList<DamageMarker> targetMarkers;

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
		this.targetMarkers = new ArrayList<>();

		if (regularAnimation != null) {
			regularAnimation.shouldMirror(!this.facingEast);
		}
		if (collisionAnimation != null) {
			collisionAnimation.shouldMirror(!this.facingEast);
		}
	}

	@Override
	public void update() {
		super.update();
	}

	public ArrayList<DamageMarker> getTargetMarkers() {
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
					(Math.abs(target.getPosX()-posX) < Math.abs(targetHit.getPosX()-posX)))) {
				targetHit = target;
			}
		}
		if (targetHit == null) return;
		marker = targetHit.inflict(damage, posX < targetHit.getPosX());
		if (marker != null) {
			targetMarkers.add(marker);
		}
	}
}
