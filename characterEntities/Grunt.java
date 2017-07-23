package characterEntities;

import animation.AbilityAnimation;
import screens.GameMap;

public class Grunt extends Enemy {

    public Grunt(Hero hero, GameMap map, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(hero, map, maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setImageIcon("src/assets/enemies/circleNeutral.png");
        setEnemyType(EnemyType.CIRCLE);

        //Temp code until enemy attack is determined
        setAnimation(0, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.DEFAULT, this));
    }

     public void attack() {
        if (getEntityState() == EntityState.NEUTRAL) {
            playAnimation(0);
            setEntityState(EntityState.ATTACKING);
        }
     }

     public boolean isHit() {
        return true;
     }
}
