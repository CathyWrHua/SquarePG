package animation.abilities;

import animation.Animation;
import characterEntities.Entity;

public class SingleArrowAbility extends Ability {

	public SingleArrowAbility(Entity entity) {
		super(entity, 1, Entity.EntityAbility.FIRST);
		initializeAnimation = new Animation(entity.getPosX(), entity.getPosY(), -75, -75, FILEPATH_ABILITY+"yellowFirst", 3, 1);
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
