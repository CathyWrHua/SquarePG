package animation.effects;

import animation.Animation;

public class SmokeBombEffect extends Effect {

	public SmokeBombEffect(int posX, int posY) {
		super(posX, posY, new Animation(posX, posY, -12, -12, FILEPATH_EFFECTS + "smokeBomb", 5, 1), null, EffectType.MAP_EFFECT);
	}

}
