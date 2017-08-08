package animation.effects;

import animation.Animation;

public class EnemyDeathEffect extends Effect {

	public EnemyDeathEffect(int posX, int posY) {
		super(posX, posY, new Animation(posX, posY, -62, -62, FILEPATH_EFFECTS + "enemyDeath", 4, 1), null, EffectType.MAP_EFFECT);
	}

}
