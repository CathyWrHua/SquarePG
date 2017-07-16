package SquarePG;

import javax.swing.ImageIcon;

public class BlueHero extends Hero {
	
	public BlueHero (String name){
		super(name, 100, 200, 80, 70, 100, 100);
		playerClass = PlayerClass.BLUE;
		heroAvatar = new ImageIcon(this.getClass().getResource("blue.png"));
	}
	
	public boolean evolve(int path){
		if (numberEvolutions != 0){
			return false;
		}
		else{
			//Need to also set imageIcon
			switch(path){
			case Hero.PATH_RED:
				playerClass= PlayerClass.VIOLET;
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
}
