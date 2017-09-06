package animation.abilities;

import animation.Animation;
import animation.effects.HuntersTrapEffect;
import characterEntities.Entity;

public class HunterTrapAbility extends Ability {

	static final String ABILITY_NAME = "hunterTrap";

	public HunterTrapAbility(Entity entity) {
		super(entity, 3, Entity.EntityAbility.THIRD);

		setHasEffects(true);
		//because cathy's lazy she's using the hunter's cloak init
		initializeAnimation	= new Animation(entity.getPosX(), entity.getPosY(), -12, -12, FILEPATH_ABILITY+"huntersCloak", 1, 3);
	}

	public void update() {
		if (state == AbilityState.INITIALIZING && (initializeAnimation != null && initializeAnimation.isDone())) {
			effects.add(new HuntersTrapEffect(entity));
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

	@Override
	public boolean isRestrictingMovement() {
		return true;
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
