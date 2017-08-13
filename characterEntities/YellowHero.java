package characterEntities;

import animation.abilities.SingleArrowAbility;
import gameLogic.MapCollisionDetection;

import java.util.LinkedList;

public class YellowHero extends Hero {
	public YellowHero(LinkedList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 20, 12, 6, 100, 100, 5);
		setPlayerClass(PlayerClass.YELLOW);
		setAbility(1, new SingleArrowAbility(this));
		setImageIcon("src/assets/hero/yellowNeutral.png");
		path[0] = CharacterProfile.Path.YELLOW;
	}

	public boolean evolve (int pathIndex, CharacterProfile.Path path) {
		if (!super.evolve(pathIndex, path)) return false;

		//temp hack test code, will change in the future
		if (pathIndex == 1 && path == CharacterProfile.Path.YELLOW) {
			setAbility(2, new SingleArrowAbility(this));
		} else if (pathIndex == 2 && path == CharacterProfile.Path.YELLOW) {
			setAbility(3, new SingleArrowAbility(this));
		} else if (pathIndex == 3 && path == CharacterProfile.Path.YELLOW) {
			setAbility(4, new SingleArrowAbility(this));
		}

		return true;
	}

//	public boolean evolve(int path) {
//		if (numberEvolutions != 0) {
//			return false;
//		} else {
//			//Need to also set imageIcon
//			switch (path) {
//			case Hero.PATH_RED:
//				setPlayerClass(PlayerClass.AMBER);
//				evolutionIncrease(Hero.PATH_RED);
//				break;
//			case Hero.PATH_YELLOW:
//				setPlayerClass(PlayerClass.GOLD);
//				evolutionIncrease(Hero.PATH_YELLOW);
//				break;
//			case Hero.PATH_BLUE:
//				setPlayerClass(PlayerClass.LIME);
//				evolutionIncrease(Hero.PATH_BLUE);
//				break;
//			default:
//				return false;
//			}
//			return true;
//		}
//	}
}
