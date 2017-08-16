package animation.abilities;

import animation.Animation;
import animation.effects.TeleportMapEffect;
import characterEntities.Entity;
import characterEntities.Hero;

import java.awt.*;

public class TeleportAbility extends Ability {
	static final String ABILITY_NAME = "teleport";

	private Point teleportPoint;
	private TeleportMapEffect teleportMark;

	public TeleportAbility(Hero player) {
		super(player, 3, Entity.EntityAbility.THIRD);

		initializeAnimation = new Animation(player.getPosX(), player.getPosY(), -20, -20, FILEPATH_ABILITY+"teleportInit", 1, 10);
		setHasEffects(true);
	}

	public void update() {
		if (state == AbilityState.INITIALIZING && initializeAnimation.isDone()) {
			teleportPoint = new Point(entity.getPosX(), entity.getPosY());
			teleportMark = new TeleportMapEffect(entity.getPosX()-20, entity.getPosY()-20);
			effects.add(teleportMark);
		}
		super.update();
	}

	public void setupAbility() {
		if (teleportMark != null && !teleportMark.isDone()) return;

		super.setupAbility();
		teleportMark = null;
		teleportPoint = null;
	}

	public boolean isRestrictingMovement() {
		return true;
	}

	public boolean shouldTrigger() {
		return (teleportMark != null && !teleportMark.isDone());
	}

	public void didTrigger() {
		if (teleportPoint != null) {
			if (teleportMark != null && !teleportMark.isDone()) {
				entity.setPoint(teleportPoint);
				teleportPoint = null;
				teleportMark.killEffect();
				teleportMark = null;
			}
		}
		teleportMark = null;
	}

	public boolean didHitTarget(Entity target) {
		return false;
	}

	public int dealDamage(int baseDamage) {
		return 0;
	}

	public String getAbilityName() {
		return ABILITY_NAME;
	}

}
