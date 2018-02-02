package gameLogic;

import characterEntities.enemyEntities.BasicArcherEnemy;
import characterEntities.enemyEntities.BasicMagicEnemy;
import characterEntities.enemyEntities.BasicMeleeEnemy;
import characterEntities.enemyEntities.Grunt;

import java.awt.*;

public class EnemyGenInfo {
	//Type presets
	public enum EnemyType {
		GRUNT,
		BASIC_MELEE,
		BASIC_ARCHER,
		BASIC_MAGE
	}

	private EnemyType enemyType;
	private Point spawnLocation;

	//Spawn delay represents the delay of spawn from previous enemy spawn
	//Alternatively, the delay from when the obj becomes tos and when it should spawn

	public EnemyGenInfo (EnemyType type, Point location) {
		enemyType = type;
		spawnLocation = location;
	}

	public Class getEnemyClass() {
		switch (enemyType) {
			case GRUNT:
				return Grunt.class;
			case BASIC_MELEE:
				return BasicMeleeEnemy.class;
			case BASIC_ARCHER:
				return BasicArcherEnemy.class;
			case BASIC_MAGE:
				return BasicMagicEnemy.class;
			default:
				return Grunt.class;
		}
	}

	public Point getSpawnLocation() {
		return spawnLocation;
	}

	public int getMaxDamage() {
		switch (enemyType) {
			case GRUNT:
				return 5;
			case BASIC_MELEE:
				return 8;
			case BASIC_ARCHER:
				return 7;
			case BASIC_MAGE:
				return 10;
			default:
				return 5;
		}
	}

	public int getMinDamage() {
		switch (enemyType) {
			case GRUNT:
				return 3;
			case BASIC_MELEE:
				return 5;
			case BASIC_ARCHER:
				return 6;
			case BASIC_MAGE:
				return 8;
			default:
				return 3;
		}
	}

	public int getMaxHealth() {
		switch (enemyType) {
			case GRUNT:
				return 20;
			case BASIC_MELEE:
				return 30;
			case BASIC_ARCHER:
				return 20;
			case BASIC_MAGE:
				return 15;
			default:
				return 20;
		}
	}

	public double getVelocity() {
		switch (enemyType) {
			case GRUNT:
				return 1.5;
			case BASIC_MELEE:
				return 1.5;
			case BASIC_ARCHER:
				return 2.0;
			case BASIC_MAGE:
				return 1.5;
			default:
				return 1.5;
		}
	}
}
