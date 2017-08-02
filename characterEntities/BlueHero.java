package characterEntities;

import animation.AbilityAnimation;
import animation.ProjectileAnimation;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;

import java.util.ArrayList;

public class BlueHero extends Hero {
	public BlueHero(ArrayList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 10, 8, 7, 100, 100, 5);
		setPlayerClass(PlayerClass.BLUE);
		setAnimation(1, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.BLUE_FIRST, this));
		setImageIcon("src/assets/hero/blueNeutral.png");
		path[0] = CharacterProfile.Path.BLUE;
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

	@Override
	public void attack(Ability ability) {
		AbilityAnimation attemptedAnimation = abilityAnimations.get(ability.getValue());
		if (entityState == EntityState.NEUTRAL && attemptedAnimation != null && attemptedAnimation.isOffCooldown()) {
			playAnimation(ability.getValue());
			attemptedAnimation.resetCooldown();
			setEntityState(EntityState.ATTACKING);
			switch (ability) {
				case FIRST:
					ProjectileAnimation newProjectile = new ProjectileAnimation(ProjectileAnimation.ProjectileAnimationType.BLUE_FIRST, mapCollisionDetection, this);
					projectileAnimations.add(newProjectile);
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected boolean isHit(Ability ability, Entity target) {
		if (super.isHit(ability, target)) {
			return true;
		}
		boolean hit = false;
		int targetPosX = target.getPosX();
		int targetPosY = target.getPosY();
		switch (ability) {
			case FIRST:
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
					if (!target.immuneTo.get(this) && isHit(Ability.DEFAULT, target) && target.getEntityState() != EntityState.DEAD) {
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
