package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import gameLogic.MapCollisionDetection;

public class FireballProjectile extends Projectile {

	private static int OFFSET_X = 75;
	private static int OFFSET_Y = 15;
	private static int FIREBALL_WIDTH = 45;
	private static int FIREBALL_EXPLOSION_WIDTH = 75;

	public FireballProjectile(Entity entity, MapCollisionDetection mapCollision) {
		super(entity,
				mapCollision,
				new Animation(entity.getPosX(),
						entity.getPosY(), OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS + "fireball", 3, 1000),
				new Animation(0, 0, 0, 0,FILEPATH_EFFECTS + "fireballExplosion", 4, 1),
				5,
				0);
		posX += (entity.getFacingEast())? 0 : entity.getImageIcon().getIconWidth()-2*OFFSET_X-FIREBALL_WIDTH;
	}

	@Override
	public void update() {
		super.update();
		if (!hasCollided && isCollide()) {
			setHasCollided(true);

			//Regular animation kill is a class specific thing:
			//Should not be moved into super class(s)
			regularAnimation.killAnimation();
			posX = facingEast? regularAnimation.getSize().x+FIREBALL_WIDTH/2 : regularAnimation.getSize().x-FIREBALL_EXPLOSION_WIDTH/2;
			posY = regularAnimation.getSize().y+regularAnimation.getSize().height/2 - FIREBALL_EXPLOSION_WIDTH/2;
			collisionAnimation.setPosition(posX, posY);
		} else if (!hasCollided){
			regularAnimation.setPosition(posX, posY);
		}
	}
}
