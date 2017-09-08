package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;

import java.util.LinkedList;

public class HuntersTrapEffect extends Projectile {

	private LinkedList<Entity> targetList;
	private int damage;

	public HuntersTrapEffect(Entity player) {
		super(player,
				null,
				new Animation(player.getPosX(), player.getPosY(), 0, 0, FILEPATH_EFFECTS+"bearTrap", 1, 5),
				new Animation(player.getPosX(), player.getPosY(), 0, 0, FILEPATH_EFFECTS + "trapTriggered", 1, 5),
				0,
				0
		);

		effectType = EffectType.BACKGROUND_EFFECT;
		damage = player.getDamage();
		targetList = player.getTargets();
		hasCollided = false;
	}

	@Override
	public void update() {
		super.update();

		hasCollided = hasCollided || findTrapTriggered();
		if (hasCollided) {
			regularAnimation.killAnimation();
		} else {
			regularAnimation.setNumLoops(5);
		}
	}

	private boolean findTrapTriggered() {
		for (Entity target : targetList) {
			if (HitDetectionHelper.detectHit(target.getEntitySize(), regularAnimation.getSize())) {
				//consider another way to do knockback
				targetMarkers.add(target.inflict(damage, false));

				//..some sort of immobilize (debuff?)..
				return true;
			}
		}
		return false;
	}
}
