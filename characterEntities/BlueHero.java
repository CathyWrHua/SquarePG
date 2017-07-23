package characterEntities;

import gui.DamageMarker;

import java.util.ArrayList;

public class BlueHero extends Hero {
	public BlueHero() {
		super(100, 80, 70, 100, 100, 5);
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

	public ArrayList<DamageMarker> attack(Ability ability, ArrayList<Entity> targets) {
		ArrayList<DamageMarker> damageMarkers = new ArrayList<>();
		DamageMarker currentDamageMarker;
		int damage;

		if (getEntityState() == EntityState.NEUTRAL) {
			setEntityState(EntityState.ATTACKING);
			playAnimation(ability.getValue());
			for (Entity target : targets) {
				switch (ability) {
					case DEFAULT:
						if (isHit(ability, target)) {
							damage = getDamage();
							currentDamageMarker = target.inflict(damage, this.getFacingEast());
							if (currentDamageMarker != null) {
								damageMarkers.add(currentDamageMarker);
							}
						}
						break;
					case FIRST:
						break;
					case SECOND:
						break;
					case THIRD:
						break;
					case ULTIMATE:
						break;
					default:
						break;
				}
			}
		}
		return damageMarkers;
	}

	protected boolean isHit(Ability ability, Entity target) {
		boolean hit = false;
		int x1 = getPosX();
		int y1 = getPosY();
		int x2 = target.getPosX();
		int y2 = target.getPosY();
		switch (ability) {
			case DEFAULT:
				if (((getFacingEast() && x2 > x1 && x2 < x1+SQUARE_LENGTH+DEFAULT_RANGE) ||
						(!getFacingEast() && x2 < x1 && x2 > x1-SQUARE_LENGTH-DEFAULT_RANGE)) &&
						y2 >= y1-DEFAULT_RANGE && y2 < y1+DEFAULT_RANGE)
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
}
