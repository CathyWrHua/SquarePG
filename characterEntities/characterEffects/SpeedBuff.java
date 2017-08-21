package characterEntities.characterEffects;

import characterEntities.Entity;

public class SpeedBuff extends Buff{

	private static final double SPEED_INCREASE = 3.0;

	public SpeedBuff(double seconds, Entity entity) {
		super(seconds, entity, null); // we can also have an animation for this as well
	}

	public void applyEffect() {
		entity.setVelocity(entity.getVelocity()+SPEED_INCREASE);
	}

	public void removeEffect() {
		entity.setVelocity(entity.getVelocity()-SPEED_INCREASE);
	}
}
