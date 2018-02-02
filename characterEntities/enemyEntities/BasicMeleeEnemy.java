package characterEntities.enemyEntities;

import animation.abilities.EggplantAbility;
import characterEntities.Hero;
import gameLogic.MapCollisionDetection;

public class BasicMeleeEnemy extends Enemy {
	public BasicMeleeEnemy(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		//Temp icon
		setImageIcon("src/assets/enemies/basicMeleeNeutral.png");

		//temp, make another ability
		setAbility(0, new EggplantAbility(this));
	}

	@Override
	public String getShapePath() {
		return "basicMelee";
	}
}
