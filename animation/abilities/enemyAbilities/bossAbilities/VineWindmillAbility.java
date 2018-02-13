package animation.abilities.enemyAbilities.bossAbilities;

import SquarePG.SquarePG;
import animation.Animation;
import animation.abilities.Ability;
import characterEntities.Entity;

import java.awt.*;

public class VineWindmillAbility extends Ability{

	//TODO: replae with actual resource name
	private final String ABILITY_NAME = "bladeSwing";
	private final int ADDITIONAL_DAMAGE = 5;
	private final double ROTATIONS_PER_SECOND = 1;
	private final double DEGREES_FULL_CIRCLE = 360;
	private double rotationDegrees;

	public VineWindmillAbility(Entity entity) {
		//TODO:change FIRST to THIRD
		super(entity, 5, Entity.EntityAbility.FIRST);

		//TODO:make initialization animation of extending vines
		//initializeAnimation = new Animation();
		//TODO:one instance of canDamage with one/two frames that get rotated (num loops should be infinite)
		//canDamageAnimation = new Animation();
		//TODO:expiration animation once rotation stops
		//expirationAnimation = new Animation();
	}

	@Override
	public void update() {
		super.update();

		if (state == AbilityState.CAN_DAMAGE) {
			rotationDegrees += DEGREES_FULL_CIRCLE*ROTATIONS_PER_SECOND/SquarePG.FPS;
			if (rotationDegrees >= DEGREES_FULL_CIRCLE) {
				//Full rotations has occureed, end attack
				if (canDamageAnimation != null) canDamageAnimation.killAnimation();
			}
		}
	}

	@Override
	public void setupAbility() {
		super.setupAbility();
		rotationDegrees = 0;
	}

	@Override
	public void didTrigger() {
		//Do nothing, not a trigger ability
	}

	@Override
	public boolean didHitTarget(Entity target) {
		//TODO: Calculate with rotating rectangles

		return false;
	}

	@Override
	public int dealDamage(int baseDamage) {
		return baseDamage+ADDITIONAL_DAMAGE;
	}

	@Override
	public String getAbilityName() {
		return ABILITY_NAME;
	}

	@Override
	public void draw(Graphics g) {
		//when can damage, draw with a rotation, then get rid of rotation.
		Graphics2D graphics = (Graphics2D) g;
		graphics.rotate(Math.toRadians(rotationDegrees));
		super.draw(g);
		graphics.rotate(Math.toRadians(rotationDegrees));
	}

}
