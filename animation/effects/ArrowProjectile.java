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
				new Animation(entity.getPosX() + (entity.getFacingEast()? OFFSET_X :
				entity.getImageIcon().getIconWidth()-OFFSET_X-ARROW_WIDTH), entity.getPosY() + OFFSET_Y, FILEPATH_EFFECTS +"arrow",2, 100),
				null,
				velocityX,
				velocityY);
	}

	public ArrowProjectile(Entity entity, MapCollisionDetection mapCollision) {
		super(entity,
				mapCollision,
				new Animation(entity.getPosX() + (entity.getFacingEast()? OFFSET_X :
						entity.getImageIcon().getIconWidth()-OFFSET_X-ARROW_WIDTH), entity.getPosY() + OFFSET_Y, FILEPATH_EFFECTS +"arrow",2, 100),
				null,
				10,
				0);
	}

	public void update() {
		super.update();
		done = isCollide();
	}
}
