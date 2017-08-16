package animation.effects;

import animation.Animation;

public class TeleportMapEffect extends Effect {

	public TeleportMapEffect(int posX, int posY) {
		super(posX, posY, new Animation(posX, posY, 0, 0, FILEPATH_EFFECTS+"teleportMark", 1, 1000), null, EffectType.BACKGROUND_EFFECT);
	}

	public void killEffect() {
		regularAnimation.killAnimation();
	}
}
