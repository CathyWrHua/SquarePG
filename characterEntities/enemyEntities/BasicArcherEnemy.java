package characterEntities.enemyEntities;

import animation.abilities.enemyAbilities.EnemySingleArrowAbility;
import characterEntities.Hero;
import gameLogic.MapCollisionDetection;

public class BasicArcherEnemy extends Enemy{
	public BasicArcherEnemy(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/basicArcherNeutral.png");

		//Temp code until enemy attack is determined
		setAbility(0, new EnemySingleArrowAbility(this));
	}

	@Override
	protected void calculateNextMove() {
		super.calculateNextMove();

		if (Math.abs(targetEntity.getPosY()-getPosY()) <= 75 &&
				targetEntity.getEntityState() != EntityState.DEAD) {
				attack(EntityAbility.DEFAULT);
		}
	}

	@Override
	public String getShapePath() {
		return "basicArcher";
	}
}
