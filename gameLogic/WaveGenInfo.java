package gameLogic;

import SquarePG.SquarePG;

import java.util.Queue;

public class WaveGenInfo {
	public static final int GENERATION_DELAY = Math.round(SquarePG.FPS*1.5f);
	private Queue<EnemyGenInfo> enemyGenInfoQueue;
	private int delayCounter;

	public WaveGenInfo(Queue<EnemyGenInfo> enemyQueue) {
		enemyGenInfoQueue = enemyQueue;
		delayCounter = GENERATION_DELAY;
	}

	public boolean shouldGenerateNextEnemy() {
		return delayCounter <= 0;
	}

	public boolean waveIsComplete() {
		return enemyGenInfoQueue.peek() == null;
	}

	public void decrementCounter() {
		if (delayCounter > 0) {
			delayCounter--;
		}
	}

	public EnemyGenInfo getNextEnemyInfo() {
		EnemyGenInfo genInfo = null;
		if (enemyGenInfoQueue.peek() != null) {
			genInfo = enemyGenInfoQueue.remove();
		}

		//For debugging
		if (delayCounter > 0) {
			System.out.println("Enemy generated before countdown complete");
		}

		delayCounter = GENERATION_DELAY;

		return genInfo;
	}
}
