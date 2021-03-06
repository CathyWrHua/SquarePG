package animation.abilities.enemyAbilities;

import animation.Animation;
import animation.abilities.Ability;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import javafx.scene.shape.Circle;

public class EnemyBladeSwingAbility extends Ability {

	//TODO: replace with actual resource name (icon and animation) with increased attack range
	private final String ABILITY_NAME = "bladeSwing";
	private final int ATTACK_RANGE = 100;

	public EnemyBladeSwingAbility(Entity entity) {
		super(entity, 2, Entity.EntityAbility.FIRST);
		canDamageAnimation = new Animation(entity.getPosX(), entity.getPosY(), Entity.DEFAULT_ENTITY_LENGTH, Entity.DEFAULT_ENTITY_LENGTH/2-ATTACK_RANGE, FILEPATH_ABILITY+ABILITY_NAME, 4, 1);
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
		int swordRectCenterX = entity.getCenterX() + (entity.getFacingEast() ? Entity.DEFAULT_ENTITY_LENGTH/2 : -Entity.DEFAULT_ENTITY_LENGTH/2 );
		Circle swordRect = new Circle(swordRectCenterX, entity.getCenterY(), ATTACK_RANGE);

		return HitDetectionHelper.detectSemicircleHit(swordRect, target.getEntitySize(), entity.getFacingEast());
	}

	//TODO: figure out a better damage calculation
	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage*2;
	}

	@Override
	public String getAbilityName() {
		return ABILITY_NAME;
	}
}
