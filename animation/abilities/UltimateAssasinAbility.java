package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.Hero;
import characterEntities.HitDetectionHelper;
import gameLogic.GameEngine;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class UltimateAssasinAbility extends Ability{

	static final float DAMAGE_MULTIPLIER = 1.5f;
	static final String ABILITY_NAME = "assassinate";
	static final int TOTAL_TARGETS = 10;
	static final int DAGGER_WIDTH = 75;

	private LinkedList<Entity> targets;
	private LinkedList<Entity> markedTargets;

	private Point entityOriginalPoint;

	public UltimateAssasinAbility(Hero player) {
		super(player, 3, Entity.EntityAbility.ULTIMATE);
		this.targets = player.getTargets();
		markedTargets = new LinkedList<>();

		initializeAnimation = new Animation(player.getPosX(), player.getPosY(), -62, -62, FILEPATH_ABILITY+"darkAura", 1, 10);
		canDamageAnimation = new Animation(player.getPosX(), player.getPosY(), 75, 0, FILEPATH_ABILITY+ABILITY_NAME, 4, 1);
	}

	public void update() {
		if (state == AbilityState.INITIALIZING && initializeAnimation.isDone()) {
			damageNextTarget();
		}

		super.update();

		if ((state == AbilityState.HAS_EXPIRED || state == AbilityState.IS_DONE)
				&& canDamageAnimation.isDone()) {
			if (markedTargets.size() > 1) {
				damageNextTarget();
			} else {
				state = AbilityState.IS_DONE;
				markedTargets.clear();
				entity.setPoint(entityOriginalPoint);
			}
		}
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
		markedTargets = (targets.clone() instanceof LinkedList)? (LinkedList<Entity>)targets.clone() : null;
		markTargets();
		entityOriginalPoint = new Point(entity.getPosX(), entity.getPosY());
	}

	@Override
	public void reset() {
		super.reset();
		markedTargets.clear();
	}

	@Override
	public GameEngine.GameEnemyUpdateState gameUpdateStateEffect() {
		if (state == AbilityState.INITIALIZING || state == AbilityState.IS_DONE) {
			return GameEngine.GameEnemyUpdateState.REGULAR;
		} else {
			return GameEngine.GameEnemyUpdateState.STOPPED;
		}
	}

	@Override
	public boolean isRestrictingMovement() {
		return true;
	}

	@Override
	public void didTrigger() {
		//Not a trigger ability
	}

	@Override
	public boolean didHitTarget(Entity target) {
		return (markedTargets.size() > 0 && markedTargets.getFirst() == target);
	}

	@Override
	public int dealDamage(int baseDamage) {
		//return Math.round(baseDamage*DAMAGE_MULTIPLIER);
		return baseDamage;
	}

	@Override
	public String getAbilityName() {
		return ABILITY_NAME;
	}

	private void damageNextTarget() {
		if (markedTargets == null || markedTargets.size() == 1) return;

		state = AbilityState.CAN_DAMAGE;
		canDamageAnimation.reset();
		markedTargets.removeFirst();
		entity.resetImmuneTo();

		int newX = 0;
		int newY = markedTargets.getFirst().getPosY();

		if (entity.getPosX() > markedTargets.getFirst().getPosX()) {
			newX = markedTargets.getFirst().getPosX()+markedTargets.getFirst().getEntitySize().width+DAGGER_WIDTH;
			entity.setFacingEast(false);
		} else {
			newX = markedTargets.getFirst().getPosX()-entity.getEntitySize().width-DAGGER_WIDTH;
			entity.setFacingEast(true);
		}

		entity.setPoint(new Point(newX, newY));
		canDamageAnimation.setPosition(newX, newY);
	}

	private void markTargets() {
		Collections.sort(markedTargets, new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				//Unsafe casting, but we should not be going over int_max or under int_min.
				return (int)Math.round(HitDetectionHelper.calculateDistance(entity.getEntitySize(), o2.getEntitySize()) - HitDetectionHelper.calculateDistance(entity.getEntitySize(), o1.getEntitySize()));
			}
		});

		//decided which 10 targets to attack
		if (markedTargets.size() < TOTAL_TARGETS && markedTargets.size() != 0) {
			//loop around
			for (int i = 0; i < markedTargets.size(); i++) {
				if (markedTargets.size() >= 10) {
					break;
				}
				markedTargets.add(markedTargets.get(i));
			}
		} else if (markedTargets.size() > TOTAL_TARGETS) {
			markedTargets.subList(10, markedTargets.size()).clear();
		}
	}

}
