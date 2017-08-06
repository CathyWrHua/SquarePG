package characterEntities;

import animation.abilities.SingleArrowAbility;
import gameLogic.MapCollisionDetection;
import animation.effects.ProjectileAnimation;
import gui.DamageMarker;

import java.util.ArrayList;

public class YellowHero extends Hero {
	public YellowHero(ArrayList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 20, 12, 6, 100, 100, 5);
		setPlayerClass(PlayerClass.YELLOW);
		setAbility(1, new SingleArrowAbility(this));
		setImageIcon("src/assets/hero/yellowNeutral.png");
		path[0] = CharacterProfile.Path.YELLOW;
	}

	public boolean evolve (int pathIndex, CharacterProfile.Path path) {
		if (!super.evolve(pathIndex, path)) return false;

		//temp hack test code, will change in the future
		if (pathIndex == 1) {
			setAbility(2, new SingleArrowAbility(this));
		} else if (pathIndex == 2) {
			setAbility(3, new SingleArrowAbility(this));
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
//
//	@Override
//	protected boolean isHit(Ability ability, Entity target) {
//		if (super.isHit(ability, target)) {
//			return true;
//		}
//		boolean hit = false;
//		int targetPosX = target.getPosX();
//		int targetPosY = target.getPosY();
//		switch (ability) {
//			case SECOND:
//			case THIRD:
//			case ULTIMATE:
//			default:
//				break;
//		}
//		return hit;
//	}
//
//	@Override
//	public void update() {
//		super.update();
//		DamageMarker marker;
//		Ability ability;
//
//		if (entityState != EntityState.ATTACKING || currentAbility == null) return;
//		ability = currentAbility.getAbility();
//		switch (ability) {
//			case DEFAULT:
//				for (Entity target : targets) {
//					if (!target.immuneTo.get(this) && isHit(Entity.Ability.DEFAULT, target) && target.getEntityState() != EntityState.DEAD) {
//						marker = target.inflict(getDamage(), this);
//						if (marker != null) {
//							targetMarkers.add(marker);
//						}
//					}
//				}
//				break;
//			case FIRST:
//				if (currentAbility.isDamageStartFrame()) {
//					ProjectileAnimation newProjectile = new ProjectileAnimation(ProjectileAnimation.ProjectileAnimationType.YELLOW_FIRST,
//							mapCollisionDetection, this);
//					projectileAnimations.add(newProjectile);
//				}
//				break;
//			case SECOND:
//			case THIRD:
//			case ULTIMATE:
//			default:
//				break;
//		}
//	}
}
