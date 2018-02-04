package characterEntities.enemyEntities;

import animation.abilities.Ability;
import animation.abilities.enemyAbilities.EnemyBladeSwingAbility;
import characterEntities.Hero;
import characterEntities.characterEffects.CharacterEffect;
import gameLogic.MapCollisionDetection;

import java.util.Iterator;

public class TreeDudeBoss extends Enemy {
	public TreeDudeBoss(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		//Temp icon
		setImageIcon("src/assets/enemies/treeDudeNeutral.png");

		//temp, make another ability
		setAbility(0, new EnemyBladeSwingAbility(this));
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
	}

	@Override
	protected void calculateNextMove() {
		//TODO: no motion logic, only attack logic
	}

	@Override
	public String getShapePath() {
		return "treeDude";
	}
}
