package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.Hero;
import characterEntities.HitDetectionHelper;

import java.awt.*;

public class UltimateLaserAbility extends Ability {

	static final int OFFSET_Y = -25;
	static final String ABILITY_NAME = "laser";
	static final int DAMAGE_INTERVAL = 10;
	static final float DAMAGE_MULTIPLIER = 1.5f;

	private int damageCounter = 0;

	public UltimateLaserAbility(Hero player) {
		super(player, 3, Entity.EntityAbility.ULTIMATE);

		initializeAnimation = new Animation(player.getPosX(), player.getPosY(), -62, -62, FILEPATH_ABILITY+"laserCharge", 1, 10);
		canDamageAnimation = new Animation(player.getPosX(), player.getPosY(), player.getFacingEast()? 75 : -1000, OFFSET_Y, FILEPATH_ABILITY+"laserBeam", 1, 20);
		canDamageAnimation.shouldMirror(!player.getFacingEast());
	}

	public void update() {
		super.update();

		if (state == AbilityState.CAN_DAMAGE) {
			if (damageCounter%DAMAGE_INTERVAL == 0) {
				entity.resetImmuneTo();
			}
			damageCounter++;
		}
	}

	public boolean isRestrictingMovement() {
		return true;
	}

	public void setupAbility() {
		super.setupAbility();
		damageCounter = 0;
	}

	public void didTrigger() { }

	public boolean didHitTarget(Entity target) {
		if (target == null) return false;

		Rectangle hitRect = (entity.getFacingEast())? new Rectangle(entity.getPosX()+75, entity.getPosY()+OFFSET_Y, 1000, 100) :
				new Rectangle(0, entity.getPosY()+OFFSET_Y, entity.getPosX(), 100);
		return HitDetectionHelper.detectHit(hitRect, target.getEntitySize());
	}

	public int dealDamage(int baseDamage) {
		return Math.round(baseDamage*DAMAGE_MULTIPLIER);
	}

	public String getAbilityName() {
		return ABILITY_NAME;
	}

}
