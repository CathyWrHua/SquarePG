package animation.abilities;

import animation.Animation;
import characterEntities.Entity;

public class WindmillSwordsAbility extends Ability {

	public WindmillSwordsAbility (Entity entity) {
		super(entity, 2, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), -75, -75, FILEPATH_ABILITY+"redFirst", 3, 2);
	}

	@Override
	public void didTrigger() {}

	@Override
	public boolean didHitTarget(Entity target) {
		return false;
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}
}
