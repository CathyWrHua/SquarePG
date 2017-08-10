package animation.abilities;

import animation.Animation;
import animation.effects.FireballProjectile;
import characterEntities.Entity;

public class FireballAbility extends Ability {

	public FireballAbility(Entity entity) {
		super(entity, 0.5, Entity.EntityAbility.FIRST);
		initializeAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+"fireball", 3, 2);
		setHasProjectiles(true);
	}

	@Override
	public void update() {
		if (state != AbilityState.INITIALIZING && initializeAnimation.isDone()) {
			projectiles.add(new FireballProjectile(entity, entity.getMapCollisionDetection()));
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
		return "fireball";
	}
}