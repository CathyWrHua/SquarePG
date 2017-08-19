package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.characterEffects.InvisibilityBuff;
import characterEntities.characterEffects.SpeedBuff;

public class HuntersCloakAbility extends Ability {

	static final String ABILITY_NAME = "huntersCloak";
	static final double ABILITY_DURATION = 2.5;

	public HuntersCloakAbility(Entity entity) {
		super(entity, 3, Entity.EntityAbility.SECOND);

		initializeAnimation	= new Animation(entity.getPosX(), entity.getPosY(), -12, -12, FILEPATH_ABILITY+ABILITY_NAME, 1, 3);
	}

	public void update() {
		if (state == AbilityState.INITIALIZING && initializeAnimation.isDone()) {
			entity.addCharacterEffect(new InvisibilityBuff(ABILITY_DURATION, entity));
			entity.addCharacterEffect(new SpeedBuff(ABILITY_DURATION, entity));
		}

		super.update();
	}

	@Override
	public boolean isRestrictingMovement() {
		return true;
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
