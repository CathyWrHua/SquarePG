package characterEntities;

import screens.GameScreen;

import java.awt.*;

public class Enemy extends Entity {
	private int deletionCounter = DELETION_TIME;
	private boolean done;
	private Entity targetEntity;
	private String shape;

	private static final int DELETION_TIME = 90;
	
	Enemy(GameScreen game, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
		super(game, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		done = false;
	}

	void setShape(String shape) {
		this.shape = shape;
	}

	public String getShape() {
		return shape;
	}

	public boolean isDone() {
		return done;
	}

	public void update() {
		super.update();
		if (getEntityState() == EntityState.DEAD && deletionCounter-- <= 0) {
			done = true;
		} else if (getEntityState() == EntityState.DEFAULT){
			calculateNextMove();
		}
	}

	@Override
	public Rectangle getEntitySize() {
		return new Rectangle(getPosX(), getPosY(), getImageIcon().getIconWidth(), getImageIcon().getIconHeight());
	}

	//TODO:add target entity to constructor
	public void setTargetEntity(Entity targetEntity) {
		this.targetEntity = targetEntity;
	}

	@Override
	public void setEntityState(EntityState entityState) {
		super.setEntityState(entityState);
		String filepath = "src/assets/enemies/";
		filepath += shape;
		switch (this.getEntityState()) {
			case DEFAULT:
				filepath += "Neutral";
				break;
			case ATTACKING:
				filepath += "Attacking";
				break;
			case DAMAGED:
				filepath += "Damaged";
				break;
			case DEAD:
				filepath += "Dead";
				break;
			default:
				break;
		}
		filepath += ".png";
		this.setImageIcon(filepath);
	}

	//Simple motion detection (pythagorean locating)
	private void calculateNextMove() {
		if (targetEntity == null) return;

		Point selfCenter = new Point(posX + getEntitySize().width/2, posY + getEntitySize().height/2);
		Point targetCenter = new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width/2, targetEntity.getPosY() + targetEntity.getEntitySize().height/2);

		Point motionVector = new Point(targetCenter.x - selfCenter.x, targetCenter.y - selfCenter.y);

		System.out.println(motionVector);
		double hypotenuse = Math.sqrt(motionVector.x * motionVector.x + motionVector.y * motionVector.y);

		if (hypotenuse != 0) {
			double scaleFactor = velocity/hypotenuse;

			posX += motionVector.x * scaleFactor;
			posY += motionVector.y * scaleFactor;
		}

		if (Math.abs(motionVector.x) < 75 && Math.abs(motionVector.y) < 75) {
			setEntityState(EntityState.ATTACKING);
			targetEntity.inflict(2, true);
		}
	}
}
