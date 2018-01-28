package characterEntities.characterEffects;

import characterEntities.Entity;

public class InvisibilityBuff extends Buff {

	public InvisibilityBuff(double seconds, Entity player) {
		super(seconds, player, null);
	}

	public void applyEffect() {
		entity.setTransparent(true);
	}

	public void removeEffect() {
		entity.setTransparent(false);
	}
}
