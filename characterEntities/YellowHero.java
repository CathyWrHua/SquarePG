package characterEntities;

import javax.swing.ImageIcon;

public class YellowHero extends Hero {
	
	public YellowHero (String name){
		super(name, 200, 70, 60, 100, 100);
		playerClass = PlayerClass.YELLOW;
		setAvatar("src/Assets/yellow.png");
	}
	
	public boolean evolve(int path){
		if (numberEvolutions != 0){
			return false;
		}
		else{
			//Need to also set imageIcon
			switch(path){
			case Hero.PATH_RED:
				playerClass= PlayerClass.AMBER;
				evolutionIncrease(Hero.PATH_RED);
				break;
			case Hero.PATH_YELLOW:
				playerClass = PlayerClass.GOLD;
				evolutionIncrease(Hero.PATH_YELLOW);
				break;
			case Hero.PATH_BLUE:
				playerClass = PlayerClass.LIME;
				evolutionIncrease(Hero.PATH_BLUE);
				break;
			default:
				return false;
			}
			return true;
		}
	}
}
