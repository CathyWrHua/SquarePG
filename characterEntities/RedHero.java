package characterEntities;

import animation.abilities.BladeWhirlAbility;
import animation.abilities.LightningDrawAbility;
import animation.abilities.UltimateAssasinAbility;
import gameLogic.MapCollisionDetection;

import java.util.LinkedList;

public class RedHero extends Hero {

	public RedHero(LinkedList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 30, 15, 5, 100, 100, 5);
		setPlayerClass(PlayerClass.RED);
		setAbility(1, new BladeWhirlAbility(this));
		setImageIcon("src/assets/hero/redNeutral.png");
	}

	public boolean evolve(int pathIndex, CharacterProfile.Path path) {
		if (!super.evolve(pathIndex, path)) return false;

		//temp hack test code, will change in the future
		if (pathIndex == 0 && path == CharacterProfile.Path.RED) {
			setAbility(2, new LightningDrawAbility(this)); //temp
		} else if (pathIndex == 1 && path == CharacterProfile.Path.RED) {
			setAbility(3, new BladeWhirlAbility(this)); //temp
		} else if (pathIndex == 2 && path == CharacterProfile.Path.RED) {
			setAbility(4, new UltimateAssasinAbility(this)); //temp
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
//				setPlayerClass(PlayerClass.SCARLET);
//				evolutionIncrease(Hero.PATH_RED);
//				break;
//			case Hero.PATH_YELLOW:
//				setPlayerClass(PlayerClass.VERMILLION);
//				evolutionIncrease(Hero.PATH_YELLOW);
//				break;
//			case Hero.PATH_BLUE:
//				setPlayerClass(PlayerClass.MAGENTA);
//				evolutionIncrease(Hero.PATH_BLUE);
//				break;
//			default:
//				return false;
//			}
//			return true;
//		}
//	}
}
