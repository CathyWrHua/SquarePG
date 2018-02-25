package characterEntities.enemyEntities;

import animation.abilities.Ability;
import animation.abilities.enemyAbilities.bossAbilities.BulletSeedAbility;
import animation.abilities.enemyAbilities.bossAbilities.SeedlingAbility;
import characterEntities.Hero;
import characterEntities.characterEffects.CharacterEffect;
import gameLogic.MapCollisionDetection;
import screens.GameScreen;

import java.awt.*;
import java.util.Iterator;

public class TreeDudeBoss extends Enemy {
	public TreeDudeBoss(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/treeDudeNeutral.png");

		//temp, make another ability
		setAbility(0, new BulletSeedAbility(this));
		setAbility(1, new SeedlingAbility(this));
	}

	@Override
	public void update() {
		//Entity takes damage logic
		if (damageTaken > 0) {
			currentHealth -= damageTaken;
			currentHealth = (currentHealth < 0) ? 0 : currentHealth;
			if (currentAbility != null) {
				currentAbility.setState(Ability.AbilityState.IS_DONE);
				currentAbility = null;
			}
			if (currentHealth > 0) {
				setEntityState(EntityState.DAMAGED);
			} else {
				setEntityState(EntityState.DEAD);
			}
			stunCounter = 0;
			damageTaken = 0;
		}

		//Entity stun
		if ((entityState == EntityState.DAMAGED || entityState == EntityState.DEAD) && stunCounter < STUN_TIME) {
			stunCounter++;
		} else if (stunCounter >= STUN_TIME && entityState == EntityState.DAMAGED) {
			setEntityState(EntityState.NEUTRAL);
			stunCounter = 0;
		}

		if (currentAbility != null) {
			currentAbility.update();
			if (currentAbility.hasEffects()) {
				effects.addAll(currentAbility.getEffects());
				currentAbility.clearEffects();
			}
			if (currentAbility.getState() == Ability.AbilityState.IS_DONE) {
				setEntityState(EntityState.NEUTRAL);
				currentAbility.reset();
				currentAbility = null;
			}
		}
		for (animation.abilities.Ability ability : abilities) {
			if (ability != null) ability.decrementCooldownCounter();
		}

		for (Iterator<CharacterEffect> iterator = characterEffects.iterator(); iterator.hasNext();) {
			CharacterEffect effect = iterator.next();
			effect.update();
			if (effect.effectHasExpired()) {
				effect.removeEffect();
				iterator.remove();
			}
		}

		if (currentAbility == null) {
			resetImmuneTo();
		}

		if (entityState == EntityState.DEAD && deletionCounter-- <= 0) {
			done = true;
		} else if (entityState == EntityState.NEUTRAL || entityState == EntityState.ATTACKING) {
			calculateNextMove();

			if (entityState == EntityState.ATTACKING && currentAbility.getState() != Ability.AbilityState.IS_DONE) {
				calculateTargetsDamage(currentAbility);
			}
		}

		randomTargetCounter -= (randomTargetCounter > 0)? 1:0;
	}

	@Override
	protected void calculateNextMove() {
		if (targetEntity == null) return;

		Point selfCenter = new Point(posX + getEntitySize().width / 2, posY + getEntitySize().height / 2);

		Point targetCenter = (targetEntity.isInvisible())? randomTargetPoint : new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width / 2, targetEntity.getPosY() + targetEntity.getEntitySize().height / 2);
		Point motionVector = new Point(targetCenter.x - selfCenter.x, targetCenter.y - selfCenter.y);

		if (targetEntity.getEntityState() != EntityState.DEAD) {
			attack(EntityAbility.SECOND);
		}

		if (targetEntity.isInvisible() && randomTargetCounter == 0) {
			randomTargetPoint = new Point(random.nextInt(GameScreen.GAME_SCREEN_WIDTH), random.nextInt(GameScreen.GAME_SCREEN_HEIGHT));
			randomTargetCounter = RANDOM_POINT_LIFETIME;
		}
	}

	@Override
	public String getShapePath() {
		return "treeDude";
	}
}
