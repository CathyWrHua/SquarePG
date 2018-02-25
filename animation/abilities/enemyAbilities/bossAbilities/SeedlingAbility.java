package animation.abilities.enemyAbilities.bossAbilities;

import animation.Animation;
import animation.abilities.Ability;
import animation.effects.enemyEffects.bossEffects.FloatySeedlingProjectile;
import characterEntities.Entity;

import java.util.NoSuchElementException;

public class SeedlingAbility extends Ability {

	static final String ABILITY_NAME = "seedling";
	private Entity target;

	public SeedlingAbility(Entity entity) {
		super(entity, 5, Entity.EntityAbility.SECOND);

		initializeAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 15, FILEPATH_ABILITY+ABILITY_NAME, 7, 1);
		setHasEffects(true);
	}

	@Override
	public void update() {
		if (target == null) {
			setState(AbilityState.IS_DONE);
			return;
		}

		//Can remove the null check once initializeAnimation is not null
		if (state == AbilityState.INITIALIZING && initializeAnimation != null && initializeAnimation.isDone()) {
			createSeedling();
		}

		super.update();
	}

	@Override
	public void setupAbility() {
		super.setupAbility();

		target = null;
		try {
			target = entity.getTargets().getFirst();
		} catch (NoSuchElementException e) {
			System.out.println("There are no enemies to target");
		}

	}

	@Override
	public void didTrigger() {
		//Do nothing
	}

	@Override
	public boolean didHitTarget(Entity target) {
		//No contact ability
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

	private void createSeedling() {
		effects.add(new FloatySeedlingProjectile(entity, entity.getMapCollisionDetection()));
	}
}
