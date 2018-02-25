package animation.abilities;

import SquarePG.SquarePG;
import animation.Animation;
import animation.effects.Effect;
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

	protected LinkedList<Effect> effects = null;

	public Ability(Entity entity, double cooldownInSeconds, Entity.EntityAbility entityAbility) {
		clearCooldown();
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
			if (!currentAnimation.isDone()) {
				int additionalX = entity.getFacingEast()? 0: entity.getImageIcon().getIconWidth()-2*currentAnimation.getOffsetX()-currentAnimation.getSize().width;
				currentAnimation.setPosition(entity.getPosX()+additionalX, entity.getPosY());
				currentAnimation.shouldMirror(!entity.getFacingEast());
			}
		}
	}

	public boolean isRestrictingMovement() {
		return false;
	}

	public GameEngine.GameEnemyUpdateState gameUpdateStateEffect() {
		return GameEngine.GameEnemyUpdateState.REGULAR;
	}

	public AbilityState getState() {
		return state;
	}

	public void setHasEffects(boolean hasEffects) {
		if (hasEffects) {
			effects = new LinkedList<>();
		}
	}

	public boolean hasEffects() {
		return effects != null;
	}

	public LinkedList<Effect> getEffects() {
		return effects;
	}

	public void clearEffects() {
		effects.clear();
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

	public void setupAbility() {
		reset();
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

	public void clearCooldown() {
		cooldownCounter = 0;
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

	public boolean shouldTrigger() {
		return false;
	}

	public boolean allowsDirectionSwitching() {
		return false;
	}

	public abstract void didTrigger();
	public abstract boolean didHitTarget(Entity target);
	public abstract int dealDamage(int baseDamage);
	public abstract String getAbilityName();
}
