package animation.abilities;

import animation.effects.Projectile;
import characterEntities.Entity;

import java.util.LinkedList;

public class UltimateHuntersRequiemAbility extends Ability {

	static final String ABILITY_NAME = "hunterRequiem";
	static final int SHOOT_INTERVAL = 60;

	private LinkedList<Projectile> knives;
	private int countDown = SHOOT_INTERVAL;

	public UltimateHuntersRequiemAbility(Entity entity) {
		super(entity, 3, Entity.EntityAbility.ULTIMATE);
		knives = new LinkedList<>();
		setHasEffects(true);

		// ..add initialization animation or not..
	}

	public void update() {
		super.update();

		if (countDown > 0) {
			countDown--;
			setState(AbilityState.INITIALIZING);
		} else if (countDown == 0) {
			// ..detonate all in list..
		}
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
		countDown = SHOOT_INTERVAL;
		// ..create projectile here..
	}

	public void didTrigger() {
		if (countDown > 0) {
			// ..create projectile..
		}
	}

	@Override
	public boolean shouldTrigger() {
		return (countDown > 0);
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
