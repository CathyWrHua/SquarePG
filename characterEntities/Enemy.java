package characterEntities;

public class Enemy extends Entity {
	
	public Enemy (int maxHealth, int maxDamage, int minDamage) {
		super(maxHealth, maxDamage, minDamage, 200, 100);
		setAvatar("src/assets/hero/yellowNeutral.png");
	}

	@Override
	public void setEntityState(EntityState entityState) {
		super.setEntityState(entityState);
		String filepath = "src/assets/hero/yellow";
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
		}
		filepath += ".png";
		this.setAvatar(filepath);
	}

}
