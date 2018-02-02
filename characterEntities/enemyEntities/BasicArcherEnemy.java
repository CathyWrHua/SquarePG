package characterEntities.enemyEntities;

import animation.abilities.EggplantAbility;
import characterEntities.Hero;
import gameLogic.MapCollisionDetection;

public class BasicArcherEnemy extends Enemy{
	public BasicArcherEnemy(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/circleNeutral.png");

		//Temp code until enemy attack is determined
		setAbility(0, new EggplantAbility(this));
	}

	@Override
	public String getShapePath() {
		return "circle";
	}
}
