package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;

import java.util.ArrayList;

public class ArrowProjectile extends Projectile {

	private static int OFFSET_X = 30;
	private static int OFFSET_Y = 30;
	private static int ARROW_WIDTH = 75;

	public ArrowProjectile(Entity entity, MapCollisionDetection mapCollision, int velocityX, int velocityY) {
		super(entity,
				mapCollision,
				new Animation(entity.getPosX(), entity.getPosY(), OFFSET_X, OFFSET_Y,FILEPATH_EFFECTS +"arrow",2, 1000),
				null,
				velocityX,
				velocityY);

		posX += (entity.getFacingEast())? OFFSET_X : -1*(ARROW_WIDTH - OFFSET_X);
	}

	public ArrowProjectile(Entity entity, MapCollisionDetection mapCollision) {
		super(entity,
				mapCollision,
				new Animation(entity.getPosX(), entity.getPosY(),
						 OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS +"arrow",2, 1000),
				null,
				10,
				0);

		posX += (entity.getFacingEast())? 0 : -1*(ARROW_WIDTH - OFFSET_X);
	}

	public void update() {
		super.update();
		if (isCollide()) {
			setHasCollided(true);

			//Regular animation kill is a class specific thing:
			//Should not be moved into super class(s)
			regularAnimation.killAnimation();
		} else {
			regularAnimation.setPosition(posX, posY);
		}
	}
}
