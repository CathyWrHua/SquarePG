package gameLogic;

import characterEntities.Grunt;

public class EnemyGenInfo {
	public enum EnemyType {
		GRUNT
	}

	private EnemyType enemyType;
	private int spawnDelay;

	public EnemyGenInfo (EnemyType type, int spawnDelay) {
		enemyType = type;
		this.spawnDelay = spawnDelay;
	}

	public Class getEnemyClass() {
		switch (enemyType) {
			case GRUNT:
				return Grunt.class;
			default:
				return  Grunt.class;
		}
	}

	public int getSpawnDelay() {
		return spawnDelay;
	}
}
