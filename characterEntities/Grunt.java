package characterEntities;

public class Grunt extends Enemy {

    public Grunt (int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setShape("circle");
        setAvatar("src/assets/enemies/circleNeutral.png");
    }
}
