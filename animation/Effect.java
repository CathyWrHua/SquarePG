package animation;

import screens.Drawable;

public interface Effect extends Drawable {
	enum EffectType {
		ENTITY_EFFECT(2), MAP_EFFECT(3), PROJECTILE_EFFECT(3),DAMAGE(4);
		private int value;

		EffectType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	EffectType getEffectType();
	boolean isDone();
	void update();
}
