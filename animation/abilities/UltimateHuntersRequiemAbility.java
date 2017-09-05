package animation.abilities;

import animation.Animation;
import animation.effects.ExplodingKnifeProjectile;
import animation.effects.Projectile;
import characterEntities.Entity;
import characterEntities.Hero;

import java.util.LinkedList;

public class UltimateHuntersRequiemAbility extends Ability {

	static final String ABILITY_NAME = "hunterRequiem";
	static final int SHOOT_INTERVAL = 300;

	private LinkedList<ExplodingKnifeProjectile> knives;
	private int countDown = 0;
	private Hero hero;

	public UltimateHuntersRequiemAbility(Hero entity) {
		super(entity, 3, Entity.EntityAbility.ULTIMATE);
		knives = new LinkedList<>();
		setHasEffects(true);

		hero = entity;

		initializeAnimation = new Animation(entity.getPosX(), entity.getPosY(), 60, -10, FILEPATH_ABILITY+ABILITY_NAME, 1, 400);
	}

	public void update() {
		super.update();

		if (countDown > 0) {
			countDown--;
			initializeAnimation.shouldMirror(!entity.getFacingEast());
		} else if (countDown == 0) {
			for (ExplodingKnifeProjectile knife : knives) {
				knife.detonate();
			}
			knives.clear();
			setState(AbilityState.IS_DONE);
			resetCooldown();
		}
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
		countDown = SHOOT_INTERVAL;
		knives.clear();
	}

	public void didTrigger() {
		if (countDown > 0) {
			ExplodingKnifeProjectile knife = new ExplodingKnifeProjectile(hero);
			knives.add(knife);
			effects.add(knife);
		}
	}

	@Override
	public void reset() {
		super.reset();
		countDown = 0;
	}

	@Override
	public boolean shouldTrigger() {
		return (countDown > 0);
	}

	@Override
	public boolean allowsDirectionSwitching() {
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
