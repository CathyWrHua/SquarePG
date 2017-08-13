package animation.abilities;

import SquarePG.SquarePG;
import animation.Animation;
import animation.effects.Projectile;
import characterEntities.Entity;
import gameLogic.GameEngine;
import screens.Drawable;

import java.awt.*;
import java.util.LinkedList;

public abstract class Ability implements Drawable {
	public enum AbilityState {
		INITIALIZING,
		CAN_DAMAGE,
		HAS_EXPIRED,
		IS_DONE
	}

	public static final String FILEPATH_ABILITY = "abilities/";

	protected Animation initializeAnimation = null;
	protected Animation canDamageAnimation = null;
	protected Animation expirationAnimation = null;
	protected Entity entity = null;
	private Entity.EntityAbility entityAbility;
	protected AbilityState state = AbilityState.INITIALIZING;
	protected int cooldownTotal, cooldownCounter;
	protected boolean restrictingMovement = false;

	protected LinkedList<Projectile> projectiles = null;

	public Ability(Entity entity, double cooldownInSeconds, Entity.EntityAbility entityAbility) {
		this.cooldownCounter = 0;
		this.entity = entity;
		this.cooldownTotal = (int)Math.round(cooldownInSeconds * SquarePG.FPS);
		this.entityAbility = entityAbility;
	}

	public void update() {
		//Updating the state of the ability
		if (state == AbilityState.INITIALIZING && (initializeAnimation == null || initializeAnimation.isDone())) {
			resetCooldown();
			if (canDamageAnimation != null) {
				state = AbilityState.CAN_DAMAGE;
			} else if (expirationAnimation != null) {
				state = AbilityState.HAS_EXPIRED;
			} else {
				state = AbilityState.IS_DONE;
			}
		} else if (state == AbilityState.CAN_DAMAGE && (canDamageAnimation == null || canDamageAnimation.isDone())) {
			if (expirationAnimation != null) {
				state = AbilityState.HAS_EXPIRED;
			} else {
				state = AbilityState.IS_DONE;
			}
		} else if (state == AbilityState.HAS_EXPIRED && (expirationAnimation == null || expirationAnimation.isDone())) {
			state = AbilityState.IS_DONE;
		}

		Animation currentAnimation = null;
		switch(state) {
			case INITIALIZING:
				currentAnimation = initializeAnimation;
				break;
			case CAN_DAMAGE:
				currentAnimation = canDamageAnimation;
				break;
			case HAS_EXPIRED:
				currentAnimation = expirationAnimation;
				break;
			case IS_DONE:
				break;
			default:
				break;
		}

		if (currentAnimation != null) {
			currentAnimation.update();
			if (currentAnimation.isDone()) {
				if (state == AbilityState.INITIALIZING) {
					state = AbilityState.CAN_DAMAGE;
				} else if (state == AbilityState.CAN_DAMAGE) {
					state = AbilityState.HAS_EXPIRED;
				} else {
					state = AbilityState.IS_DONE;
				}
			} else {
				int additionalX = entity.getFacingEast()? 0: entity.getImageIcon().getIconWidth()-2*currentAnimation.getOffsetX()-currentAnimation.getSize().width;
				currentAnimation.setPosition(entity.getPosX()+additionalX, entity.getPosY());
				currentAnimation.shouldMirror(!entity.getFacingEast());
			}
		}
	}

	public boolean isRestrictingMovement() {
		return restrictingMovement;
	}

	public GameEngine.GameEnemyUpdateState gameUpdateStateEffect() {
		return GameEngine.GameEnemyUpdateState.REGULAR;
	}

	public AbilityState getState() {
		return state;
	}

	public void setHasProjectiles(boolean hasProjectiles) {
		if (hasProjectiles) {
			projectiles = new LinkedList<>();
		}
	}

	public boolean hasProjectiles() {
		return projectiles != null;
	}

	public LinkedList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void clearProjectiles() {
		projectiles.clear();
	}

	public void setState(AbilityState state) {
		this.state = state;
	}

	public void draw(Graphics g) {
		Animation currentAnimation = null;
		switch (state) {
			case INITIALIZING:
				currentAnimation = initializeAnimation;
				break;
			case CAN_DAMAGE:
				currentAnimation = canDamageAnimation;
				break;
			case HAS_EXPIRED:
				currentAnimation = expirationAnimation;
				break;
			case IS_DONE:
				break;
			default:
				break;
		}

		if (currentAnimation != null) {
			currentAnimation.draw(g);
		}
	}

	public void reset() {
		state = AbilityState.INITIALIZING;
		if (initializeAnimation != null) {
			initializeAnimation.reset();
		}
		if (canDamageAnimation != null) {
			canDamageAnimation.reset();
		}
		if (expirationAnimation != null) {
			expirationAnimation.reset();
		}
	}

	public void resetCooldown() {
		cooldownCounter = cooldownTotal;
	}

	public void decrementCooldownCounter() {
		if (cooldownCounter > 0) {
			cooldownCounter--;
		}
	}

	public boolean isOffCooldown() {
		return cooldownCounter <= 0;
	}

	public int getCooldownCounter() {
		return cooldownCounter;
	}

	public int getCooldownTotal() {
		return cooldownTotal;
	}

	public Entity.EntityAbility getEntityAbility() {
		return entityAbility;
	}

	public abstract void didTrigger();
	public abstract boolean didHitTarget(Entity target);
	public abstract int dealDamage(int baseDamage);
	public abstract String getAbilityName();
}
