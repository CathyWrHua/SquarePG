package animation.effects.enemyEffects.bossEffects;

import animation.Animation;
import animation.effects.Projectile;
import characterEntities.Entity;
import gameLogic.MapCollisionDetection;

public class BulletSeedProjectile extends Projectile{

	private static int OFFSET_X = 75;
	private static int OFFSET_Y = 15;

	public BulletSeedProjectile(Entity entity, MapCollisionDetection mapCollisionDetection) {
		//TODO: determine how to do velocity calculations (allow x and y?)
		//TODO: change animations
		super(entity,
				mapCollisionDetection,
				new Animation(entity.getPosX(),
						entity.getPosY(), OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS + "fireball", 3, 1000),
				new Animation(0, 0, 0, 0,FILEPATH_EFFECTS + "fireballExplosion", 4, 1),
				5,
				0);
	}

	@Override
	public void update() {
		super.update();
		if (!hasCollided && isCollide()) {
			setHasCollided(true);

			regularAnimation.killAnimation();

			//TODO: Calculate this properly once animations are created
			collisionAnimation.setPosition(posX, posY);
		} else if (!hasCollided) {
			regularAnimation.setPosition(posX, posY);
		}
	}
}
