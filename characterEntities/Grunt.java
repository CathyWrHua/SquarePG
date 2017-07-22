package characterEntities;

public class Grunt extends Enemy {

    public Grunt(int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setShape("circle");
        setImageIcon("src/assets/enemies/circleNeutral.png");
    }
}
