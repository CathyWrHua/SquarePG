package characterEntities;

import screens.GameScreen;

public class Enemy extends Entity {
	private int deletionCounter = DELETION_TIME;
	private boolean done;
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
		}
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

}
