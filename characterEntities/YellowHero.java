package characterEntities;

import gui.DamageMarker;

import java.util.ArrayList;

public class YellowHero extends Hero {
	public YellowHero(ArrayList<Entity> targets) {
		super(targets,200, 70, 60, 100, 100, 10);
		setPlayerClass(PlayerClass.YELLOW);
		setImageIcon("src/assets/hero/yellowNeutral.png");
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

	protected boolean isHit(Ability ability, Entity target) {
		boolean hit = false;
		int targetPosX = target.getPosX();
		int targetPosY = target.getPosY();
		switch (ability) {
			case DEFAULT:
				if (((getFacingEast() && targetPosX > posX && targetPosX < posX+SQUARE_LENGTH+DEFAULT_RANGE) ||
						(!getFacingEast() && targetPosX < posX && targetPosX > posX-SQUARE_LENGTH-DEFAULT_RANGE)) &&
						targetPosY > posY-DEFAULT_RANGE && targetPosY < posY+DEFAULT_RANGE)
					hit = true;
				break;
			case FIRST:
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

		if (entityState != EntityState.ATTACKING) {
			return;
		}
		for (Entity target : targets) {
			switch (currentAbilityAnimation.getAbilityAnimationType()) {
				case DEFAULT:
					if (!target.immuneTo.get(this) && isHit(Ability.DEFAULT, target)) {
						marker = target.inflict(getDamage(), this);
						if (marker != null) {
							enemyMarkers.add(marker);
						}
					}
					break;
				default:
					break;
			}
		}
	}
}
