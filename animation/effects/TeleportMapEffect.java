package animation.effects;

import animation.Animation;

public class TeleportMapEffect extends Effect {

	private boolean isKilled = false;

	public TeleportMapEffect(int posX, int posY) {
		super(posX, posY, new Animation(posX, posY, -20, -20, FILEPATH_EFFECTS+"teleportMark", 1, 2), null, EffectType.BACKGROUND_EFFECT);
	}

	public void killEffect() {
		regularAnimation.killAnimation();
		isKilled = true;
	}

	public boolean isDone() {
		return isKilled;
	}

	public void update() {
		super.update();

		regularAnimation.setNumLoops(2);
	}

}
