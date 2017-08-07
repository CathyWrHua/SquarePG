package animation.abilities;

import animation.Animation;
import animation.effects.ArrowProjectile;
import characterEntities.Entity;

public class SingleArrowAbility extends Ability {

	public SingleArrowAbility(Entity entity) {
		super(entity, 1, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+"yellowFirst", 3, 1);
		setHasProjectiles(true);
	}

	@Override
	public void update() {
		if (state == AbilityState.INITIALIZING && (initializeAnimation == null || initializeAnimation.isDone())) {
			projectiles.add(new ArrowProjectile(entity, entity.getMapCollisionDetection()));
		}

		super.update();
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

	@Override
	public String getAbilityName() {
		return "yellowFirst";
	}
}
