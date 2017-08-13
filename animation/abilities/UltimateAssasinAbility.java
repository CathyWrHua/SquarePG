package animation.abilities;

import characterEntities.Entity;
import characterEntities.Hero;
import gameLogic.GameEngine;

public class UltimateAssasinAbility extends Ability{

	static final float DAMAGE_MULTIPLIER = 1.5f;
	static final String ABILITY_NAME = "assassinate";

	public UltimateAssasinAbility(Hero player) {
		super(player, 3, Entity.EntityAbility.ULTIMATE);
	}

	public void update() {

	}

	public GameEngine.GameEnemyUpdateState gameUpdateStateEffect() {
		return GameEngine.GameEnemyUpdateState.STOPPED;
	}

	public boolean isRestrictingMovement() {
		return true;
	}

	public void didTrigger() {
		//Not a trigger ability
	}

	public boolean didHitTarget(Entity target) {
		return true; //think of how to do the logic for this one
	}

	public int dealDamage(int baseDamage) {
		return Math.round(baseDamage*DAMAGE_MULTIPLIER);
	}

	public String getAbilityName() {
		return ABILITY_NAME;
	}

}
