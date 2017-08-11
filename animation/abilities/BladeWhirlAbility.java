package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import javafx.scene.shape.Circle;

public class BladeWhirlAbility extends Ability {

	public final int WINDMILL_RADIUS = 112;

	public BladeWhirlAbility(Entity entity) {
		super(entity, 2, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), -75, -75, FILEPATH_ABILITY+"bladeWhirl", 3, 2);
	}

	@Override
	public void didTrigger() {}

	@Override
	public boolean didHitTarget(Entity target) {
		Circle hitArea = new Circle(entity.getCenterX(), entity.getCenterY(), WINDMILL_RADIUS);
		boolean hit = HitDetectionHelper.detectHit(hitArea, target.getEntitySize());
		return hit;
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}

	@Override
	public String getAbilityName() {
		return "bladeWhirl";
	}
}
