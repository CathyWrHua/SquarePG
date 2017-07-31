package characterEntities;

import animation.AbilityAnimation;
import gameLogic.MapCollisionDetection;
import animation.ProjectileAnimation;
import gui.DamageMarker;

import java.util.ArrayList;
import java.util.HashMap;

public class YellowHero extends Hero {
	private HashMap<Ability, Boolean> newProjectileFlag;

	public YellowHero(ArrayList<Entity> targets, MapCollisionDetection mapCollisionDetection) {
		super(targets, mapCollisionDetection, 20, 12, 6, 100, 100, 5);
		newProjectileFlag = new HashMap<>();
		createNewProjectileFlagHashMap();
		setPlayerClass(PlayerClass.YELLOW);
		setAnimation(1, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.YELLOW_FIRST, this));
		setImageIcon("src/assets/hero/yellowNeutral.png");
		path[0] = CharacterProfile.Path.YELLOW;
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

	private void createNewProjectileFlagHashMap() {
		newProjectileFlag.put(Ability.FIRST, false);
	}

	@Override
	public void attack(Ability ability) {
		super.attack(ability);
		switch (ability) {
			case FIRST:
				newProjectileFlag.put(ability, true);
			default:
				break;
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
			case SECOND:
			case THIRD:
			case ULTIMATE:
			default:
				break;
		}
		return hit;
	}

	@Override
	public void update() {
		super.update();
		DamageMarker marker;
		Ability ability;

		if (entityState != EntityState.ATTACKING || currentAbilityAnimation == null) return;

		for (Entity target : targets) {
			ability = currentAbilityAnimation.getAbility();
			switch (ability) {
				case DEFAULT:
					if (!target.immuneTo.get(this) && isHit(ability, target) && target.getEntityState() != EntityState.DEAD) {
						marker = target.inflict(getDamage(), this);
						if (marker != null) {
							targetMarkers.add(marker);
						}
					}
					break;
				case FIRST:
					if (newProjectileFlag.get(ability)) {
						ProjectileAnimation newProjectile = new ProjectileAnimation(ProjectileAnimation.ProjectileAnimationType.YELLOW_FIRST, mapCollisionDetection, this);
						projectileAnimations.add(newProjectile);
						newProjectileFlag.put(ability, false);
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
