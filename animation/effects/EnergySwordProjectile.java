package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.Hero;

public class EnergySwordProjectile extends Projectile {

	private static int OFFSET_X = -10;
	private static int OFFSET_Y = -50;

	private static final String ABILITY_NAME = "energySword";
	private final float DAMAGE_MULTIPLIER = 1.5f;

	public EnergySwordProjectile(Hero hero, Entity target) {
		super(hero,
				hero.getMapCollisionDetection(),
				new Animation(target.getPosX(), target.getPosY(), OFFSET_X, OFFSET_Y,FILEPATH_EFFECTS+ABILITY_NAME+"Charge", 4, 1),
				new Animation(target.getPosX(), target.getPosY(), OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS+ABILITY_NAME, 3, 1),
				0,
				0);

		damage *= DAMAGE_MULTIPLIER;
	}

	public void update() {
		super.update();
		if (regularAnimation.isDone()) {
			hasCollided = true;
		}
	}
}