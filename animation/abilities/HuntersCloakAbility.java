package animation.abilities;

import characterEntities.Entity;
import characterEntities.characterEffects.InvisibilityBuff;
import characterEntities.characterEffects.SpeedBuff;

public class HuntersCloakAbility extends Ability {

	static final String ABILITY_NAME = "huntersCloak";
	static final double ABILITY_DURATION = 2.5;

	public HuntersCloakAbility(Entity entity) {
		super(entity, 3, Entity.EntityAbility.SECOND);
		// ..add initialization animation ..
	}

	public void update() {
		super.update();
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
		entity.addCharacterEffect(new InvisibilityBuff(ABILITY_DURATION, entity));
		entity.addCharacterEffect(new SpeedBuff(ABILITY_DURATION, entity));
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
