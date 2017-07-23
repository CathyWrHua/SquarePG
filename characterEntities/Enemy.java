package characterEntities;

import java.util.HashMap;

public class Enemy extends Entity {
	public enum EnemyType {
		CIRCLE(0);
		private int value;

		EnemyType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private int deletionCounter = DELETION_TIME;
	private boolean done;
	private EnemyType enemyType;
	private HashMap<Integer, String> shapePath;

	private static final int DELETION_TIME = 90;
	
	Enemy(int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
		super(maxHealth, maxDamage, minDamage, posX, posY, velocity);
		shapePath = new HashMap<>();
		shapePath.put(0, "circle");
		done = false;
	}

	public boolean isDone() {
		return done;
	}

	void setEnemyType(EnemyType enemyType) {
		this.enemyType = enemyType;
	}

	@Override
	public void setEntityState(EntityState entityState) {
		super.setEntityState(entityState);
		String filepath = "src/assets/enemies/";
		filepath += shapePath.get(enemyType.getValue());
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

	public void update() {
		super.update();
		if (getEntityState() == EntityState.DEAD && deletionCounter-- <= 0) {
			done = true;
		}
	}

}
