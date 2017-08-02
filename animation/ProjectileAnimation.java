package animation;

import gameLogic.MapCollisionDetection;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import gui.DamageMarker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectileAnimation extends Animation {
	public enum ProjectileAnimationType {
		YELLOW_FIRST(30, 30, "arrow", 2, 10, 0),
		BLUE_FIRST(75, 15, "fireball", 3, 5, 0);
		private int offsetX, offsetY;
		private String animationName;
		private int totalFrames;
		private int velocityX, velocityY;

		ProjectileAnimationType(int offsetX, int offsetY, String animationName, int totalFrames,
								int velocityX, int velocityY) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.animationName = animationName;
			this.totalFrames = totalFrames;
			this.velocityX = velocityX;
			this.velocityY = velocityY;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}

		public int getTotalFrames() {
			return totalFrames;
		}

		public String getAnimationName() {
			return animationName;
		}

		public int getVelocityX() {
			return velocityX;
		}

		public int getVelocityY() {
			return velocityY;
		}
	}

	private int posX, posY;
	private int velocityX, velocityY;
	private int damage;
	private boolean facingEast;
	private MapCollisionDetection mapCollision;
	private ArrayList<Entity> targets;
	private ArrayList<DamageMarker> targetMarkers;

	public ProjectileAnimation(ProjectileAnimationType animationType, MapCollisionDetection collisionMap, Entity entity) {
		this.drawableType = DrawableType.PROJECTILE_EFFECT;
		this.mapCollision = collisionMap;
		this.facingEast = entity.getFacingEast();
		this.targets = entity.getTargets();
		this.damage = entity.getDamage();
		this.targetMarkers = new ArrayList<>();
		this.animationName = animationType.getAnimationName();
		this.totalFrames = animationType.getTotalFrames();
		this.velocityX = animationType.getVelocityX();
		this.velocityY = animationType.getVelocityY();
		this.imageIcons = new ArrayList<>(totalFrames);
		for (int i = 0; i < totalFrames; i++) {
			imageIcons.add(i, new ImageIcon(FILEPATH_ROOT+animationName+i+FILEPATH_PNG));
		}
		this.velocityX *= facingEast ? 1 : -1;
		this.posX = entity.getPosX() + (entity.getFacingEast() ? animationType.getOffsetX() :
				entity.getImageIcon().getIconWidth()-animationType.getOffsetX()-imageIcons.get(0).getIconWidth());
		this.posY = entity.getPosY() + animationType.getOffsetY();
	}

	private void dealDamage() {
		Entity targetHit = null;
		Rectangle projectileSize = new Rectangle(posX, posY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
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

	public ArrayList<DamageMarker> getTargetMarkers () {
		return targetMarkers;
	}

	public void clearTargetMarkers() {
		targetMarkers.clear();
	}

	private boolean isCollide() {
		boolean collision;
		int newX = posX + velocityX;
		int newY = posY + velocityY;
		Rectangle projectileSize = new Rectangle(posX, posY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		Point newPoint = mapCollision.determineMotion(newX, newY, projectileSize, targets);

		collision = (newX != newPoint.x || newY != newPoint.y);
		posX = newX;
		posY = newY;
		if (collision) dealDamage();
		return collision;
	}

	@Override
	public void update() {
		int currentFrame = counter++/ANIMATION_SPEED;
		if (counter/ANIMATION_SPEED >= totalFrames) {
			resetCounter();
			currentFrame = 0;
		}
		this.imageIcon = imageIcons.get(currentFrame);
		done = isCollide();
	}

	public void draw(Graphics g) {
		if (imageIcon == null) return;
		Graphics2D g2d = (Graphics2D)g;
		Image image = imageIcon.getImage();
		int x = posX;
		int width = image.getWidth(null);

		if (!facingEast) {
			x += width;
			width = -width;
		}

		if (counter/ANIMATION_SPEED < totalFrames) {
			g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
		}
	}
}
