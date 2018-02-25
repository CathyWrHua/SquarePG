package characterEntities.enemyEntities;

import animation.abilities.Ability;
import animation.abilities.enemyAbilities.bossAbilities.BulletSeedAbility;
import animation.abilities.enemyAbilities.bossAbilities.SeedlingAbility;
import characterEntities.Hero;
import characterEntities.characterEffects.CharacterEffect;
import gameLogic.MapCollisionDetection;
import screens.GameScreen;

import java.awt.*;
import java.util.Iterator;

public class TreeDudeBoss extends Enemy {
	public TreeDudeBoss(Hero hero, MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		super(hero, mapCollisionDetection, maxHealth, maxDamage, minDamage, posX, posY, velocity);
		setImageIcon("src/assets/enemies/treeDudeNeutral.png");

		//temp, make another ability
		setAbility(0, new BulletSeedAbility(this));
		setAbility(1, new SeedlingAbility(this));
	}

	@Override
	public void update() {
		updateDamageTaken();
		updateStun();
		updateAbilities();
		updateEffects();
		updateAttack();
		updateEnemyMisc();
	}

	@Override
	protected void calculateNextMove() {
		if (targetEntity == null) return;

		Point selfCenter = new Point(posX + getEntitySize().width / 2, posY + getEntitySize().height / 2);

		Point targetCenter = (targetEntity.isInvisible())? randomTargetPoint : new Point(targetEntity.getPosX() + targetEntity.getEntitySize().width / 2, targetEntity.getPosY() + targetEntity.getEntitySize().height / 2);
		Point motionVector = new Point(targetCenter.x - selfCenter.x, targetCenter.y - selfCenter.y);

		if (targetEntity.getEntityState() != EntityState.DEAD) {
			attack(EntityAbility.SECOND);
		}

		if (targetEntity.isInvisible() && randomTargetCounter == 0) {
			randomTargetPoint = new Point(random.nextInt(GameScreen.GAME_SCREEN_WIDTH), random.nextInt(GameScreen.GAME_SCREEN_HEIGHT));
			randomTargetCounter = RANDOM_POINT_LIFETIME;
		}
	}

	@Override
	public String getShapePath() {
		return "treeDude";
	}
}
