package animation.effects.enemyEffects.bossEffects;

import SquarePG.SquarePG;
import animation.Animation;
import animation.effects.Projectile;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;
import javafx.scene.shape.Circle;

import java.awt.*;

public class FloatySeedlingProjectile extends Projectile {

	private final int DAMAGE_MULTIPLIER = 2;
	private final int BLAST_RADIUS = 100;
	private final int ATTACK_RANGE = 70;
	private Animation channelAnimation;

	private static int OFFSET_X = 75;
	private static int OFFSET_Y = 15;

	private double velocity = 2;
	private int confusedCounter = 0;

	public FloatySeedlingProjectile(Entity entity, MapCollisionDetection mapCollision) {
		super(entity,
				mapCollision,
				new Animation(entity.getPosX(),
						entity.getPosY(), OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS + "fireball", 3, 1000),
				new Animation(0, 0, 0, 0,FILEPATH_EFFECTS + "fireballExplosion", 4, 1),
				0,
				0);

		//TODO: create channelAnimation that channels for long enough to be fair
		//channelAnimation = new Animation();
		channelAnimation = new Animation(entity.getPosX(),
				entity.getPosY(), OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS + "fireball", 3, 5);
	}

	@Override
	public void update() {
		if (!hasCollided && regularAnimation != null && !regularAnimation.isDone()) {
			regularAnimation.update();

			//Has the side effect of setting new position as well
			findNewPosition();
			if (determineShouldExplode()) {
				setHasCollided(true);
				channelAnimation.shouldMirror(facingEast);
				channelAnimation.setPosition(posX, posY);
				collisionAnimation.shouldMirror(facingEast);
				collisionAnimation.setPosition(posX, posY);
			}
		} else if (hasCollided && channelAnimation != null && !channelAnimation.isDone()) {
			channelAnimation.update();
			if (channelAnimation.isDone()) {
				findCollisions();
			}
		} else if (hasCollided && collisionAnimation != null && !collisionAnimation.isDone()) {
			collisionAnimation.update();
		}
	}

	private void findNewPosition() {
		Entity targetEntity = targets.getFirst();

		Point selfCenter = new Point(posX + regularAnimation.getSize().width / 2, posY + regularAnimation.getSize().height / 2);

		Point targetCenter = (targetEntity.isInvisible())? selfCenter : new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width / 2, targetEntity.getPosY() + targetEntity.getEntitySize().height / 2);
		Point motionVector = new Point(targetCenter.x - selfCenter.x, targetCenter.y - selfCenter.y);

		double hypotenuse = Math.sqrt(motionVector.x * motionVector.x + motionVector.y * motionVector.y);

		if (hypotenuse != 0) {
			double scaleFactor = velocity / hypotenuse;
			int deltaX = (int)Math.round(motionVector.x * scaleFactor);
			int deltaY = (int)Math.round(motionVector.y * scaleFactor);

			posX += (deltaX == 0 && targetCenter.x != selfCenter.x) ?
					((targetCenter.x > selfCenter.x) ? 1 : -1) : deltaX;
			posY += (deltaY == 0 && targetCenter.y != selfCenter.y) ?
					((targetCenter.y > selfCenter.y) ? 1 : -1) : deltaY;
		}

		if (targetEntity.isInvisible()) {
			confusedCounter++;
			if (confusedCounter % (SquarePG.FPS/3) == 0) {
				facingEast = !facingEast;
			}
		} else {
			facingEast = motionVector.x < 0;
		}

		regularAnimation.shouldMirror(facingEast);
		regularAnimation.setPosition(posX, posY);
	}

	private boolean determineShouldExplode() {
		Entity targetEntity = targets.getFirst();
		boolean shouldExplode = false;
		if (!targetEntity.isInvisible()) {
			Point selfCenter = new Point(posX + regularAnimation.getSize().width / 2, posY + regularAnimation.getSize().height / 2);
			Point targetCenter = new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width / 2, targetEntity.getPosY() + targetEntity.getEntitySize().height / 2);
			Point motionVector = new Point(targetCenter.x - selfCenter.x, targetCenter.y - selfCenter.y);

			double hypotenuse = Math.sqrt(motionVector.x * motionVector.x + motionVector.y * motionVector.y);

			if (hypotenuse < ATTACK_RANGE) shouldExplode = true;
		}
		return shouldExplode;
	}

	private void findCollisions() {
		Circle hitArea = new Circle(regularAnimation.getSize().getCenterX(), regularAnimation.getSize().getCenterY(), BLAST_RADIUS);

		int x = (regularAnimation == null)? posX: posX+regularAnimation.getOffsetX();
		for (Entity target: targets) {
			if (HitDetectionHelper.detectHit(hitArea, target.getEntitySize())) {
				DamageMarker marker = target.inflict(damage*DAMAGE_MULTIPLIER, x < target.getPosX());
				if (marker != null) {
					targetMarkers.add(marker);
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (!hasCollided && regularAnimation != null && !regularAnimation.isDone()) {
			regularAnimation.draw(g);
		} else if (hasCollided && channelAnimation != null && !channelAnimation.isDone()) {
			channelAnimation.draw(g);
		} else if (hasCollided && collisionAnimation != null && !collisionAnimation.isDone()) {
			collisionAnimation.draw(g);
		}
	}
}
