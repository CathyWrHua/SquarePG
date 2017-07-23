package characterEntities;

import screens.GameMap;

public class Grunt extends Enemy {

    public Grunt(Hero hero, GameMap map, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
        super(hero, map, maxHealth, maxDamage, minDamage, posX, posY, velocity);
        setImageIcon("src/assets/enemies/circleNeutral.png");
        setEnemyType(EnemyType.CIRCLE);
    }
}
