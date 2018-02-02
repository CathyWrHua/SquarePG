package animation.abilities.enemyAbilities;

import animation.Animation;
import animation.abilities.Ability;
import animation.effects.ArrowProjectile;
import characterEntities.Entity;

public class EnemySingleArrowAbility extends Ability{

	private final String ABILITY_NAME = "singleArrow";

	public EnemySingleArrowAbility(Entity entity) {
		super(entity, 1, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+ABILITY_NAME, 3, 1);
		setHasEffects(true);
	}

	@Override
	public void update() {
		if (state == AbilityState.INITIALIZING && (initializeAnimation == null || initializeAnimation.isDone())) {
			effects.add(new ArrowProjectile(entity, entity.getMapCollisionDetection()));
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
