package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;

import java.awt.*;

public class LightningDrawAbility extends Ability {

	private final String ABILITY_NAME = "lightningDraw";
	private final int ATTACK_WIDTH = 110;
	private final int ATTACK_HEIGHT = 75;

	public LightningDrawAbility(Entity entity) {
		super(entity, 1, Entity.EntityAbility.SECOND);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), -20, 0, FILEPATH_ABILITY+ABILITY_NAME, 4, 1);;
	}

	@Override
	public void update() {
		super.update();
		int newPosX = entity.getPosX() + (15 * (entity.getFacingEast() ? 1 : -1));
		this.entity.setPoint(new Point(newPosX, entity.getPosY()));
	}

	@Override
	public void didTrigger() {}

	@Override
	public boolean didHitTarget(Entity target) {
		if (entity == null) return false;
		Rectangle lightningDrawRect = new Rectangle(entity.getFacingEast()? entity.getPosX()+entity.getEntitySize().width : entity.getPosX()-ATTACK_WIDTH,
				entity.getPosY(),
				ATTACK_WIDTH,
				ATTACK_HEIGHT);

		return HitDetectionHelper.detectHit(lightningDrawRect, target.getEntitySize());
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
