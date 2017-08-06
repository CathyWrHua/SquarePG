package animation.abilities;

import animation.Animation;
import characterEntities.Entity;

public class FireballAbility extends Ability {

	public FireballAbility(Entity entity) {
		super(entity, 0.5, Entity.EntityAbility.FIRST);
		initializeAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+"blueFirst", 3, 2);
		setHasProjectiles(true);
	}

	@Override
	public void didTrigger() {

	}

	@Override
	public boolean didHitTarget(Entity target) {
		return false;
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}
}
