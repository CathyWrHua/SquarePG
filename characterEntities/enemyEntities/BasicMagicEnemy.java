package characterEntities.enemyEntities;

import animation.abilities.enemyAbilities.EnemySingleFireballAbility;
import characterEntities.Hero;
import gameLogic.MapCollisionDetection;

public class BasicMagicEnemy extends Enemy {
	public BasicMagicEnemy(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/circleNeutral.png");

		//Temp code until enemy attack is determined
		setAbility(0, new EnemySingleFireballAbility(this));
	}

	@Override
	public String getShapePath() {
		return "circle";
	}
}
