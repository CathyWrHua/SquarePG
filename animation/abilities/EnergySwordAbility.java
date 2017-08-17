package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.Hero;
import characterEntities.HitDetectionHelper;

import java.util.LinkedList;

public class EnergySwordAbility extends Ability {

	static final float DAMAGE_MULTIPLIER = 1.5f;
	static final String ABILITY_NAME = "energySword";

	private LinkedList<Entity> targets;
	private Entity currentTarget; //or multiple

	public EnergySwordAbility(Hero player) {
		super(player, 2, Entity.EntityAbility.FIRST);
		targets = player.getTargets();

		canDamageAnimation = new Animation(0, 0, -10, -50, FILEPATH_ABILITY+"energySword", 1, 3);
	}

	public void setupAbility() {
		super.setupAbility();

		if (targets != null) {
			findTarget();
		}

		if (currentTarget != null) {
			canDamageAnimation.setPosition(currentTarget.getPosX(), currentTarget.getPosY());
		}
	}

	public void update() {
		super.update();

		if (currentTarget != null) {
			canDamageAnimation.setPosition(currentTarget.getPosX(), currentTarget.getPosY());
		}
	}

	public void reset() {
		super.reset();
		currentTarget = null;
	}

	public void didTrigger() {}

	public boolean didHitTarget(Entity target) {
		return (target != null && currentTarget != null && target == currentTarget);
	}

	public int dealDamage(int baseDamage) {
		return Math.round(baseDamage*DAMAGE_MULTIPLIER);
	}

	public String getAbilityName() {
		return ABILITY_NAME;
	}

	private void findTarget() {
		double minDistance = HitDetectionHelper.calculateDistance(targets.getFirst().getEntitySize(), entity.getEntitySize());
		currentTarget = targets.getFirst();

		for (Entity target:targets) {
			double distance = HitDetectionHelper.calculateDistance(target.getEntitySize(), entity.getEntitySize());
			if (distance < minDistance) {
				currentTarget = target;
				minDistance = distance;
			}
		}
	}
}
