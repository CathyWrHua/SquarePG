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
		//initializeAnimation = new Animation(0, 0, -10, -50, FILEPATH_ABILITY+ABILITY_NAME+"Charge", 4, 1);
		//canDamageAnimation = new Animation(0, 0, -10, -50, FILEPATH_ABILITY+ABILITY_NAME, 3, 1);
	}

	@Override
	public void setupAbility() {
		super.setupAbility();

		if (targets != null) {
			findTargets();
		}

//		if (currentTarget != null) {
//			initializeAnimation.setPosition(currentTarget.getPosX(), currentTarget.getPosY());
//			canDamageAnimation.setPosition(currentTarget.getPosX(), currentTarget.getPosY());
//		}
	}

	@Override
	public void update() {
		super.update();
	}



	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}

//	public void update() {
//		super.update();
//
//		if (currentTarget != null) {
//			initializeAnimation.setPosition(currentTarget.getPosX(), currentTarget.getPosY());
//			canDamageAnimation.setPosition(currentTarget.getPosX(), currentTarget.getPosY());
//		}
//	}
//
//	public void reset() {
//		super.reset();
//	}

	@Override
	public void didTrigger() {}

	public boolean hasEffects() {
		return super.hasEffects();
	}


//	public boolean didHitTarget(Entity target) {
//		return (target != null && currentTarget != null && target == currentTarget);
//	}

//	public int dealDamage(int baseDamage) {
//		return Math.round(baseDamage*DAMAGE_MULTIPLIER);
//	}

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


//		double minDistance = HitDetectionHelper.calculateDistance(targets.getFirst().getEntitySize(), entity.getEntitySize());
//		currentTarget = targets.getFirst();
//
//		for (Entity target:targets) {
//			double distance = HitDetectionHelper.calculateDistance(target.getEntitySize(), entity.getEntitySize());
//			if (distance < minDistance) {
//				currentTarget = target;
//				minDistance = distance;
//			}
//		}
	}

	@Override
	public boolean didHitTarget(Entity target) {
		return false;
	}

}
