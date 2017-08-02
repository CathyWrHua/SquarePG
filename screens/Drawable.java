package screens;

import java.awt.*;

public abstract class Drawable {
	public enum DrawableType {
		BACKGROUND(0), ENTITY(1), ENTITY_EFFECT(2), MAP_EFFECT(3), PROJECTILE_EFFECT(3), DAMAGE(4), GUI(5);
		private int value;

		DrawableType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	protected DrawableType drawableType;

	public DrawableType getDrawableType() {
		return drawableType;
	}
	public abstract void draw(Graphics g);
}
