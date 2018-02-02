package gameLogic;

import characterEntities.enemyEntities.Grunt;

import java.awt.*;

public class EnemyGenInfo {
	//Type presets
	public enum EnemyType {
		GRUNT
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
				return 10;
			default:
				return 10;
		}
	}

	public int getMinDamage() {
		switch (enemyType) {
			case GRUNT:
				return 8;
			default:
				return 8;
		}
	}

	public int getMaxHealth() {
		switch (enemyType) {
			case GRUNT:
				return 20;
			default:
				return 100;
		}
	}

	public double getVelocity() {
		switch (enemyType) {
			case GRUNT:
				return 1.5;
			default:
				return 1.5;
		}
	}
}
