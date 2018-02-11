package characterEntities;

import animation.abilities.EnergySwordAbility;
import animation.abilities.FireballAbility;
import animation.abilities.TeleportAbility;
import animation.abilities.UltimateLaserAbility;
import gameLogic.MapCollisionDetection;

import java.util.LinkedList;

public class BlueHero extends Hero {
	public BlueHero(LinkedList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 20, 10, 8, 100, 100, 5);
		setPlayerClass(PlayerClass.BLUE);
		setAbility(0, new FireballAbility(this));
		setImageIcon("src/assets/hero/blueNeutral.png");
	}

	public boolean evolve (int pathIndex, CharacterProfile.Path path) {
		if (!super.evolve(pathIndex, path)) return false;

		//temp hack test code, will change in the future
		if (pathIndex == 0 && path == CharacterProfile.Path.BLUE) {
			setAbility(1, new EnergySwordAbility(this));
		} else if (pathIndex == 1 && path == CharacterProfile.Path.BLUE) {
			setAbility(2, new TeleportAbility(this));
		} else if (pathIndex == 2 && path == CharacterProfile.Path.BLUE) {
			setAbility(3, new UltimateLaserAbility(this));
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
