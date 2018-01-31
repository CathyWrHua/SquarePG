package animation.abilities;

import animation.Animation;
import animation.effects.TeleportMapEffect;
import characterEntities.Entity;
import characterEntities.Hero;
import characterEntities.HitDetectionHelper;
import gui.DamageMarker;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.LinkedList;

public class TeleportAbility extends Ability {
	static final String ABILITY_NAME = "teleport";
	static final float DAMAGE_MULTIPLIER = 1.5f;
	static final int BLAST_RADIUS = 100;

	private Point teleportPoint;
	private TeleportMapEffect teleportMark;
	private boolean isTriggered = false;
	private boolean setCoolDown = false;
	private LinkedList<Entity> targets;

	public TeleportAbility(Hero player) {
		super(player, 3, Entity.EntityAbility.THIRD);

		targets = player.getTargets();

		initializeAnimation = new Animation(player.getPosX(), player.getPosY(), -20, -20, FILEPATH_ABILITY+"teleportInit", 1, 10);
		setHasEffects(true);
	}

	public void update() {
		if (state == AbilityState.INITIALIZING && initializeAnimation.isDone()) {
			teleportPoint = new Point(entity.getPosX(), entity.getPosY());
			teleportMark = new TeleportMapEffect(entity.getPosX(), entity.getPosY());
			effects.add(teleportMark);
		}

		super.update();

		if (!setCoolDown) {
			cooldownCounter = 0;
		}
	}

	public void setupAbility() {
		if (teleportMark != null && !teleportMark.isDone()) return;

		super.setupAbility();
		teleportMark = null;
		teleportPoint = null;
	}

	public void decrementCooldownCounter() {
		super.decrementCooldownCounter();
		if (cooldownCounter == 0) {
			setCoolDown = false;
		}
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

				entity.calculateTargetsDamage(this);
				setCoolDown = true;
				resetCooldown();
				//isTriggered = true;
			}
		}
		teleportMark = null;
	}

	public boolean didHitTarget(Entity target) {
		if (target == null) return false;

		Circle hitArea = new Circle(entity.getCenterX(), entity.getCenterY(), BLAST_RADIUS);
		return HitDetectionHelper.detectHit(hitArea, target.getEntitySize());
	}

	public int dealDamage(int baseDamage) {
		return Math.round(baseDamage*DAMAGE_MULTIPLIER);
	}

	public String getAbilityName() {
		return ABILITY_NAME;
	}
}
