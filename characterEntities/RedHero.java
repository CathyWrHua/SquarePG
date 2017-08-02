package characterEntities;

import animation.AbilityAnimation;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class RedHero extends Hero {
	private static final int ABILITY_1_RADIUS = 112;

	public RedHero(ArrayList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 30, 15, 5, 100, 100, 5);
		setPlayerClass(PlayerClass.RED);
		setAnimation(1, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.RED_FIRST, this));
		setImageIcon("src/assets/hero/redNeutral.png");
		path[0] = CharacterProfile.Path.RED;
	}

	public boolean evolve (int pathIndex, CharacterProfile.Path path) {
		if (!super.evolve(pathIndex, path)) return false;

		//temp hack test code, will change in the future
		if (pathIndex == 1) {
			setAnimation(pathIndex + 1, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.RED_SECOND, this));
		} else if (pathIndex == 2) {
			setAnimation(pathIndex + 1, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.RED_THIRD, this));
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

	@Override
	protected boolean isHit(Ability ability, Entity target) {
		if (super.isHit(ability, target)) {
			return true;
		}
		boolean hit = false;
		switch (ability) {
			case FIRST:
				Circle hitArea = new Circle(posX + (SQUARE_LENGTH/2), posY + (SQUARE_LENGTH/2), ABILITY_1_RADIUS);
				hit = HitDetectionHelper.detectHit(hitArea, target.getEntitySize());
				break;
			case SECOND:
			case THIRD:
			case ULTIMATE:
				break;
		}
		return hit;
	}

	@Override
	public void update() {
		super.update();
		DamageMarker marker;

		if (entityState != EntityState.ATTACKING) return;
		for (Entity target : targets) {
			switch (currentAbilityAnimation.getAbility()) {
				case DEFAULT:
				case FIRST:
					if (!target.immuneTo.get(this) && isHit(currentAbilityAnimation.getAbility(), target) && target.getEntityState() != EntityState.DEAD) {
						marker = target.inflict(getDamage(), this);
						if (marker != null) {
							targetMarkers.add(marker);
						}
					}
					break;
				case SECOND:
				case THIRD:
				case ULTIMATE:
				default:
					break;
			}
		}
	}
}
