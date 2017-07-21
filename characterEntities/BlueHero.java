package characterEntities;

public class BlueHero extends Hero {
	public BlueHero (String name) {
		super(name, 100, 80, 70, 100, 100);
		colour = "blue";
		playerClass = PlayerClass.BLUE;
		setAvatar("src/assets/hero/blueNeutral.png");
	}
	
	public boolean evolve(int path) {
		if (numberEvolutions != 0) {
			return false;
		} else {
			//Need to also set imageIcon
			switch(path) {
			case Hero.PATH_RED:
				playerClass = PlayerClass.VIOLET;
				evolutionIncrease(Hero.PATH_RED);
				break;
			case Hero.PATH_YELLOW:
				playerClass = PlayerClass.TURQUOISE;
				evolutionIncrease(Hero.PATH_YELLOW);
				break;
			case Hero.PATH_BLUE:
				playerClass = PlayerClass.ULTRAMARINE;
				evolutionIncrease(Hero.PATH_BLUE);
				break;
			default:
				return false;
			}
			return true;
		}
	}

	public void attack (Ability ability) {
		if (getEntityState() == EntityState.DEFAULT) {
			setEntityState(EntityState.ATTACKING);
			switch (ability) {
				case DEFAULT:
					playAnimation(0);
					break;
				case FIRST:
					playAnimation(1);
					break;
				case SECOND:
					playAnimation(2);
					break;
				case THIRD:
					playAnimation(3);
					break;
				case ULTIMATE:
					playAnimation(4);
					break;
				default:
					break;
			}
		}
	}
}
