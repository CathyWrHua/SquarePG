package characterEntities;

public abstract class Hero extends Entity {
	protected int numberEvolutions;
	protected PlayerClass playerClass;
	
	protected static final int PATH_RED = 0;
	protected static final int PATH_YELLOW = 1;
	protected static final int PATH_BLUE = 2;

	public enum Ability {DEFAULT, FIRST, SECOND, THIRD, ULTIMATE}
	
	public Hero (String name, int maxHealth, int maxDamage, int minDamage, int posX, int posY) {
		super(name, maxHealth, maxDamage, minDamage, posX, posY);
		setAnimation(0, new Animation(Animation.AnimationType.DEFAULT, this));
		numberEvolutions = 0;
	}

	public void basicAttack () {}
	
	public abstract boolean evolve (int path);

	public abstract void attack (Ability ability);
	
	protected boolean evolutionIncrease (int path) {
//		switch (path) {
//		case PATH_RED:
//			maxHealth += 210;
//			maxDamage += 30;
//			minDamage += 30;
//			break;
//		case PATH_YELLOW:
//			maxHealth += 150;
//			maxDamage += 50;
//			minDamage += 50;
//			break;
//		case PATH_BLUE:
//			maxHealth += 60;
//			maxDamage += 70;
//			maxDamage += 70;
//			break;
//		default:
//			return false;
//		}
		return true;
	}
}
