package animation.abilities;

import characterEntities.Entity;

public class HunterTrapAbility extends Ability {

	static final String ABILITY_NAME = "hunterTrap";

	public HunterTrapAbility(Entity entity) {
		super(entity, 3, Entity.EntityAbility.THIRD);
		// ..add initialization animation ..
	}

	public void update() {
		if (state == AbilityState.INITIALIZING && (initializeAnimation != null && initializeAnimation.isDone())) {
			// ..add effect here ..
		}

		super.update();
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
	}

	public void didTrigger() {
		//not a trigger ability
	}

	public boolean didHitTarget(Entity target) {
		return false; //not an impact ability
	}
	public int dealDamage(int baseDamage) {
		return 0; //not a damage ability
	}

	public String getAbilityName() {
		return ABILITY_NAME;
	}
}
