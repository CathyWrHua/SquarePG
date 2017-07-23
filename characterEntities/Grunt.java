package characterEntities;

public class Grunt extends Enemy {

    public Grunt(Hero hero, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(hero, maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setImageIcon("src/assets/enemies/circleNeutral.png");
        setEnemyType(EnemyType.CIRCLE);
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
