package animation.abilities;

import animation.Animation;
import animation.effects.ArrowProjectile;
import characterEntities.Entity;

public class SingleArrowAbility extends Ability {

	private final String ABILITY_NAME = "singleArrow";

	public SingleArrowAbility(Entity entity) {
		super(entity, 1, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+ABILITY_NAME, 3, 1);
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
		return ABILITY_NAME;
	}
}
