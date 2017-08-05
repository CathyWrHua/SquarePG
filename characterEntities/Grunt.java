package characterEntities;

import gameLogic.MapCollisionDetection;

public class Grunt extends Enemy {

	public Grunt(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/circleNeutral.png");
		setEnemyType(EnemyType.CIRCLE);

		attackRange = 50;

		//Temp code until enemy attack is determined
		setAnimation(0, new animation.abilities.Ability(abilities.Ability.AbilityAnimationType.CIRCLE_DEFAULT, this));
	}
}
