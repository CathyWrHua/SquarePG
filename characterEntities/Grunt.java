package characterEntities;

import gameLogic.MapCollisionDetection;
import animation.AbilityAnimation;

public class Grunt extends Enemy {

	public Grunt(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/circleNeutral.png");
		setEnemyType(EnemyType.CIRCLE);

		attackRange = 50;

		//Temp code until enemy attack is determined
		setAnimation(0, new AbilityAnimation(AbilityAnimation.AbilityAnimationType.CIRCLE_DEFAULT, this));
	}
}
