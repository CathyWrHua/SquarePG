package animation.abilities;

import animation.Animation;
import characterEntities.Entity;

public class EggplantAbility extends Ability {

	public EggplantAbility(Entity entity) {
		super(entity, 0.5, Entity.EntityAbility.DEFAULT);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+"heroDefault", 4, 1);
	}

	@Override
	public void didTrigger() { }

	@Override
	public boolean didHitTarget(Entity target) {
		return false;
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}
}
