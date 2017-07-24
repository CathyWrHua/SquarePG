package characterEntities;

import GameMaps.MapCollisionDetection;
import animation.AbilityAnimation;

public class Grunt extends Enemy {

    public Grunt(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setImageIcon("src/assets/enemies/circleNeutral.png");
        setEnemyType(EnemyType.CIRCLE);

        attackRange = 50;

        //Temp code until enemy attack is determined
        setAnimation(0, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.DEFAULT, this));
    }

     public void attack() {
        if (getEntityState() == EntityState.NEUTRAL) {
            playAnimation(0);
            setEntityState(EntityState.ATTACKING);
        }
     }
}
