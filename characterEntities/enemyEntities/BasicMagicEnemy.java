package characterEntities.enemyEntities;

import animation.abilities.enemyAbilities.EnemySingleFireballAbility;
import characterEntities.Hero;
import gameLogic.MapCollisionDetection;

public class BasicMagicEnemy extends Enemy {
	public BasicMagicEnemy(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/basicMageNeutral.png");

		//Temp code until enemy attack is determined
		setAbility(0, new EnemySingleFireballAbility(this));
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
		return "basicMage";
	}
}
