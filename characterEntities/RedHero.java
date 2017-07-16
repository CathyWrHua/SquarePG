package characterEntities;

import javax.swing.ImageIcon;

public class RedHero extends Hero {
	
	public RedHero(String name){
		super(name, 300, 50, 55, 45, 100, 100);
		playerClass = PlayerClass.RED;
		heroAvatar = new ImageIcon(this.getClass().getResource("red.png"));
	}
	
	public boolean evolve(int path){
		if (numberEvolutions != 0){
			return false;
		}
		else{
			//Need to also set imageIcon
			switch(path){
			case Hero.PATH_RED:
				playerClass= PlayerClass.SCARLET;
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
}
