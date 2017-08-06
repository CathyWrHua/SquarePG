package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import gameLogic.MapCollisionDetection;

public class FireballProjectile extends Projectile {

	private static int OFFSET_X = 75;
	private static int OFFSET_Y = 15;
	private static int FIREBALL_WIDTH = 45;

	public FireballProjectile(Entity entity, MapCollisionDetection mapCollision) {
		super(entity,
				mapCollision,
				new Animation(entity.getPosX() + (entity.getFacingEast()? OFFSET_X : entity.getImageIcon().getIconWidth() - OFFSET_X-FIREBALL_WIDTH),
						entity.getPosY() + OFFSET_Y, 0, 0, FILEPATH_EFFECTS + "fireball", 3, 100),
				new Animation(0, 0, 0, 0,FILEPATH_EFFECTS + "fireballExplosion", 4, 1),
				5,
				0);
	}

	@Override
	public void update() {
		super.update();
		if (isCollide()) {
			setHasCollided(true);
			//Regular animation kill is a class specific thing:
			//Should not be moved into super class(s)
			regularAnimation.killAnimation();
			int x = facingEast? regularAnimation.getSize().x+FIREBALL_WIDTH : regularAnimation.getSize().x;
			int y = regularAnimation.getSize().y+regularAnimation.getSize().height/2;
			collisionAnimation.setPosition(x, y);
		}
	}
}
