package animation.abilities;

import animation.Animation;
import animation.effects.EnergySwordProjectile;
import characterEntities.Entity;
import characterEntities.Hero;
import characterEntities.HitDetectionHelper;

import java.util.LinkedList;

public class EnergySwordAbility extends Ability {

	private LinkedList<Entity> targets;
	private static final String ABILITY_NAME = "energySword";
	private final double ENERGY_SWORD_RADIUS = 300;

	//TODO: Animations should be projectiles
	public EnergySwordAbility(Hero player) {
		super(player, 2, Entity.EntityAbility.FIRST);
		targets = player.getTargets();
		setHasEffects(true);
	}

	@Override
	public void setupAbility() {
		super.setupAbility();

		if (targets != null) {
			findTargets();
		}
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}

	@Override
	public void didTrigger() {}

	public boolean hasEffects() {
		return super.hasEffects();
	}

	@Override
	public String getAbilityName() {
		return ABILITY_NAME;
	}

	private void findTargets() {
		for (Entity target:targets) {
			double distance = HitDetectionHelper.calculateDistance(target.getEntitySize(), entity.getEntitySize());
			if (distance < ENERGY_SWORD_RADIUS) {
				effects.add(new EnergySwordProjectile((Hero) entity, target));
			}
		}
	}

	@Override
	public boolean didHitTarget(Entity target) {
		return false;
	}
}
