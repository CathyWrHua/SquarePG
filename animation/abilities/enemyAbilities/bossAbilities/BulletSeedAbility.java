package animation.abilities.enemyAbilities.bossAbilities;

import SquarePG.SquarePG;
import animation.Animation;
import animation.abilities.Ability;
import animation.effects.enemyEffects.bossEffects.BulletSeedProjectile;
import characterEntities.Entity;

public class BulletSeedAbility extends Ability {

	//TODO:Change when we have actual asset
	private final String ABILITY_NAME = "fireball";
	private final int BULLET_SEED_TIMEFRAME = SquarePG.FPS/2;
	private final int NUMBER_SEEDS = 3;

	private int abilityCounter = BULLET_SEED_TIMEFRAME;

	public BulletSeedAbility(Entity entity) {
		super(entity, 2, Entity.EntityAbility.FIRST);

		//TODO: create initialization+canDamage animation for entire duration of BULLET_SEED_TIMEFRAME
		//initializeAnimation = new Animation();
		//canDamageAnimation = new Animation();

		//Temporary test animation
		initializeAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+ABILITY_NAME, 3, 20);

		setHasEffects(true);
	}

	@Override
	public void update() {
		super.update();

		if (abilityCounter > 0) {
			abilityCounter--;
			if (abilityCounter % (BULLET_SEED_TIMEFRAME/NUMBER_SEEDS) == 0) generateBulletSeed();
		} else {
			setState(AbilityState.IS_DONE);
		}
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
		abilityCounter = BULLET_SEED_TIMEFRAME;
	}

	private void generateBulletSeed() {
		effects.add(new BulletSeedProjectile(entity, entity.getMapCollisionDetection()));
	}

	@Override
	public void didTrigger() {
		//Do nothing, is not a player triggered ability
	}

	@Override
	public boolean didHitTarget(Entity target) {
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
}
