package characterEntities;

public class Enemy extends Entity {
	
	public Enemy (String name, int maxHealth, int maxDamage, int minDamage) {
		super(name, maxHealth, maxDamage, minDamage, 200, 100);
		setAvatar("src/assets/hero/redNeutral.png");
	}

	@Override
	public void setEntityState(EntityState entityState) {
		super.setEntityState(entityState);
		String filepath = "src/assets/hero/red";
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
