package characterEntities.enemyEntities;

import animation.abilities.EggplantAbility;
import characterEntities.Hero;
import characterEntities.enemyEntities.Enemy;
import gameLogic.MapCollisionDetection;

public class Grunt extends Enemy {

	public Grunt(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/circleNeutral.png");
		setEnemyType(EnemyType.CIRCLE);

		//Temp code until enemy attack is determined
		setAbility(0, new EggplantAbility(this));
	}
}
