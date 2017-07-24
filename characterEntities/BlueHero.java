package characterEntities;

import gui.DamageMarker;
import screens.GameMap;

import java.util.ArrayList;

public class BlueHero extends Hero {
	public BlueHero(ArrayList<Entity> targets, GameMap map) {
		super(targets, map, 10, 8, 7, 100, 100, 5);
		setPlayerClass(PlayerClass.BLUE);
		setImageIcon("src/assets/hero/blueNeutral.png");
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
