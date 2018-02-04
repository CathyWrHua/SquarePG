package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import javafx.scene.shape.Circle;

public class BladeWhirlAbility extends Ability {

	private final String ABILITY_NAME = "bladeWhirl";
	public final int WINDMILL_RADIUS = 112;

	public BladeWhirlAbility(Entity entity) {
		super(entity, 2, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), -Entity.DEFAULT_ENTITY_LENGTH, -Entity.DEFAULT_ENTITY_LENGTH, FILEPATH_ABILITY+ABILITY_NAME, 3, 2);
	}

	@Override
	public void didTrigger() {}

	@Override
	public boolean didHitTarget(Entity target) {
		Circle hitArea = new Circle(entity.getCenterX(), entity.getCenterY(), WINDMILL_RADIUS);
		return HitDetectionHelper.detectHit(hitArea, target.getEntitySize());
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage;
	}

	@Override
	public String getAbilityName() {
		return ABILITY_NAME;
	}
}
