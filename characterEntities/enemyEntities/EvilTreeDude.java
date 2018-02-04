package characterEntities.enemyEntities;

import animation.abilities.enemyAbilities.EnemyBladeSwingAbility;
import characterEntities.Hero;
import gameLogic.MapCollisionDetection;

public class EvilTreeDude extends Enemy {
	public EvilTreeDude(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		//Temp icon
		setImageIcon("src/assets/enemies/basicMeleeNeutral.png");

		//temp, make another ability
		setAbility(0, new EnemyBladeSwingAbility(this));
	}

	@Override
	protected void calculateNextMove() {
		//TODO: no motion logic, only attack logic
	}

	@Override
	public String getShapePath() {
		return "basicMelee";
	}
}
