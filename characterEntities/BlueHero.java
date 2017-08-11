package characterEntities;

import animation.abilities.FireballAbility;
import gameLogic.MapCollisionDetection;

import java.util.LinkedList;

public class BlueHero extends Hero {
	public BlueHero(LinkedList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 10, 8, 7, 100, 100, 5);
		setPlayerClass(PlayerClass.BLUE);
		setAbility(1, new FireballAbility(this));
		setImageIcon("src/assets/hero/blueNeutral.png");
		path[0] = CharacterProfile.Path.BLUE;
	}

	public boolean evolve (int pathIndex, CharacterProfile.Path path) {
		if (!super.evolve(pathIndex, path)) return false;

		//temp hack test code, will change in the future
		if (pathIndex == 1) {
			setAbility(2, new FireballAbility(this));
		} else if (pathIndex == 2) {
			setAbility(3, new FireballAbility(this));
		}

		return true;
	}
	
//	public boolean evolve(int path) {
//		if (numberEvolutions != 0) {
//			return false;
//		} else {
//			//Need to also set imageIcon
//			switch(path) {
//			case Hero.PATH_RED:
//				setPlayerClass(PlayerClass.VIOLET);
//				evolutionIncrease(Hero.PATH_RED);
//				break;
//			case Hero.PATH_YELLOW:
//				setPlayerClass(PlayerClass.TURQUOISE);
//				evolutionIncrease(Hero.PATH_YELLOW);
//				break;
//			case Hero.PATH_BLUE:
//				setPlayerClass(PlayerClass.ULTRAMARINE);
//				evolutionIncrease(Hero.PATH_BLUE);
//				break;
//			default:
//				return false;
//			}
//			return true;
//		}
//	}
}
