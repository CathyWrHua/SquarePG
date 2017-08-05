package animation;

import screens.Drawable;

public abstract class Effect implements Drawable {
	public enum EffectType {
		ENTITY_EFFECT(2), MAP_EFFECT(3), PROJECTILE_EFFECT(3), DAMAGE(4);
		private int value;

		EffectType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	protected EffectType effectType;

	public EffectType getEffectType() {
		return effectType;
	}
	public abstract boolean isDone();
	public abstract void update();
}
