package characterEntities;

import screens.GameScreen;

public class Grunt extends Enemy {

    public Grunt(GameScreen game, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(game, maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setShape("circle");
        setImageIcon("src/assets/enemies/circleNeutral.png");
    }
}
