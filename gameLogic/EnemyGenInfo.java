package gameLogic;

import characterEntities.Grunt;

import java.awt.*;

public class EnemyGenInfo {
	//Type presets
	public enum EnemyType {
		GRUNT
	}

	private EnemyType enemyType;

	//Spawn delay represents the delay of spawn from previous enemy spawn
	//Alternatively, the delay from when the obj becomes tos and when it should spawn
	//TODO:decide where delay seconds gets converted to update cycles
	private int spawnDelayCounter;
	private Point spawnLocation;

	public EnemyGenInfo (EnemyType type, int spawnDelay, Point location) {
		enemyType = type;
		spawnDelayCounter = spawnDelay;
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

	public int getSpawnDelayCounter() {
		return spawnDelayCounter;
	}

	public void decreaseSpawnDelayCounter() {
		if (spawnDelayCounter > 0) {
			spawnDelayCounter--;
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
