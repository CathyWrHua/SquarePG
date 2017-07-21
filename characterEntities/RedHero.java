package characterEntities;

public class RedHero extends Hero {
	public RedHero (String name) {
		super(name, 300, 55, 45, 100, 100);
		colour = "red";
		playerClass = PlayerClass.RED;
		setAvatar("src/assets/hero/redEastNeutral.png");
	}
	
	public boolean evolve (int path) {
		if (numberEvolutions != 0) {
			return false;
		}
		else {
			//Need to also set imageIcon
			switch (path) {
			case Hero.PATH_RED:
				playerClass = PlayerClass.SCARLET;
				evolutionIncrease(Hero.PATH_RED);
				break;
			case Hero.PATH_YELLOW:
				playerClass = PlayerClass.VERMILLION;
				evolutionIncrease(Hero.PATH_YELLOW);
				break;
			case Hero.PATH_BLUE:
				playerClass = PlayerClass.MAGENTA;
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
