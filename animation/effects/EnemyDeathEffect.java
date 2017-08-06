package animation.effects;

import animation.Animation;

import java.awt.*;

public class EnemyDeathEffect extends MapEffect {

	public EnemyDeathEffect(int posX, int posY) {
		super(posX, posY, new Animation(posX, posY, -62, -62, FILEPATH_EFFECTS + "enemyDeath", 4, 1), null, EffectType.MAP_EFFECT);
	}

}
