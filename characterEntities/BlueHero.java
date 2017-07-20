package characterEntities;

public class BlueHero extends Hero {
	
	public BlueHero (String name) {
		super(name, 100, 80, 70, 100, 100);
		playerClass = PlayerClass.BLUE;
		setAvatar("src/assets/blue.png");
	}
	
	public boolean evolve(int path) {
		if (numberEvolutions != 0) {
			return false;
		}
		else {
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

	public void attack (int ability) {
		switch (ability) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				break;
		}
	}
}
