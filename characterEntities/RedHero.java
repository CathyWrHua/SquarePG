package characterEntities;

import animation.AbilityAnimation;
import gui.DamageMarker;
import screens.GameMap;

import java.util.ArrayList;

public class RedHero extends Hero {
    private static final int ABILITY_1_RADIUS = 112;
    
	public RedHero(ArrayList<Entity> targets, GameMap map) {
		super(targets, map, 30, 15, 5, 100, 100, 5);
		setPlayerClass(PlayerClass.RED);
        setAnimation(1, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.RED_FIRST, this));
		setImageIcon("src/assets/hero/redNeutral.png");
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

    private boolean intersects(int circleX, int circleY, int rectX, int rectY, int rectWidth, int rectHeight, int circleRadius) {
        int deltaX = Math.abs(circleX - rectX);
        int deltaY = Math.abs(circleY - rectY);

        if (deltaX > (rectWidth/2 + circleRadius)) { return false; }
        if (deltaY > (rectHeight/2 + circleRadius)) { return false; }

        if (deltaX <= (rectWidth/2)) { return true; }
        if (deltaY <= (rectHeight/2)) { return true; }

        int cornerDistance = (deltaX - rectWidth/2)^2 + (deltaY - rectHeight/2)^2;

        return (cornerDistance <= (circleRadius^2));
    }

    @Override
	protected boolean isHit(Ability ability, Entity target) {
	    if (super.isHit(ability, target)) {
	        return true;
        }
		boolean hit = false;
        int targetCenterX = target.getPosX() + (target.getImageIcon().getIconWidth()/2);
        int targetCenterY = target.getPosY() + (target.getImageIcon().getIconHeight()/2);
        int centerX = posX + (SQUARE_LENGTH/2);
        int centerY = posY + (SQUARE_LENGTH/2);
		switch (ability) {
			case FIRST:
			    hit = intersects(centerX, centerY, targetCenterX, targetCenterY,
                        target.getImageIcon().getIconWidth(), target.getImageIcon().getIconHeight(),
                        ABILITY_1_RADIUS);
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
                    if (!target.immuneTo.get(this) && isHit(currentAbilityAnimation.getAbility(), target)) {
                        marker = target.inflict(getDamage(), this);
                        if (marker != null) {
                            enemyMarkers.add(marker);
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
