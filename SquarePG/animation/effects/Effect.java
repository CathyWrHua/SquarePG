package animation.effects;

import animation.Animation;
import screens.Drawable;

import java.awt.*;

public abstract class Effect implements Drawable {
	public enum EffectType {
		MAP_EFFECT(3), PROJECTILE_EFFECT(3), DAMAGE(4);
		private int value;

		EffectType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public static final String FILEPATH_EFFECTS = "Effects/";

	protected EffectType effectType;
	protected int posX;
	protected int posY;
	protected Animation regularAnimation;
	protected Animation collisionAnimation;
	protected boolean hasCollided = false;

	public Effect(int posX, int posY, Animation regularAnimation, Animation collisionAnimation, EffectType effectType) {
		this.posX = posX;
		this.posY = posY;
		this.regularAnimation = regularAnimation;
		this.collisionAnimation = collisionAnimation;
		this.effectType = effectType;
	}

	public boolean hasCollisionAnimation() {
		return collisionAnimation != null;
	}

	public EffectType getEffectType() {
		return effectType;
	}

	public void setHasCollided (boolean collided) {
		hasCollided = collided;
	}

	public boolean isDone() {
		return (regularAnimation == null || regularAnimation.isDone()) &&
				(collisionAnimation == null || collisionAnimation.isDone());
	}

	public void update() {
		if (!hasCollided && regularAnimation != null && !regularAnimation.isDone()) {
			regularAnimation.update();
		} else if (hasCollided && collisionAnimation != null && !collisionAnimation.isDone()) {
			collisionAnimation.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		if (!hasCollided && regularAnimation != null && !regularAnimation.isDone()) {
			regularAnimation.draw(g);
		} else if (hasCollided && collisionAnimation != null && !collisionAnimation.isDone()) {
			collisionAnimation.draw(g);
		}
	}
}
