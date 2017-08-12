package animation.abilities;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;

import java.awt.*;

public class EggplantAbility extends Ability {

	private final String ABILITY_NAME = "eggplant";
	private final int ATTACK_RANGE = 75;

	public EggplantAbility(Entity entity) {
		super(entity, 2, Entity.EntityAbility.DEFAULT);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), 75, 0, FILEPATH_ABILITY+ABILITY_NAME, 4, 1);
	}

	@Override
	public void didTrigger() { }

	@Override
	public boolean didHitTarget(Entity target) {
//		int targetPosX = target.getPosX();
//		int targetPosY = target.getPosY();
//		if (((entity.getFacingEast() && targetPosX > entity.getPosX() && targetPosX < entity.getPosX()+entity.getEntitySize().width+ATTACK_RANGE) ||
//				(!entity.getFacingEast() && targetPosX < entity.getPosX() && targetPosX > entity.getPosX()-entity.getEntitySize().width-ATTACK_RANGE)) &&
//				targetPosY > entity.getPosY()-ATTACK_RANGE && targetPosY < entity.getPosY()+ATTACK_RANGE) {
//			hit = true;
//		}

		if (entity == null) return false;
		//Dependent on update for ability being called first
		Rectangle eggplantRect = new Rectangle(entity.getFacingEast()? entity.getPosX()+entity.getEntitySize().width : entity.getPosX()-ATTACK_RANGE,
				entity.getPosY(),
				ATTACK_RANGE,
				ATTACK_RANGE);

		return HitDetectionHelper.detectHit(eggplantRect, target.getEntitySize());
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
