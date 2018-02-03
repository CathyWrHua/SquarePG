package gameLogic;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class GameMapPresets {

	private static Queue<WaveGenInfo>[][] waveGenInfo;

	private static final int[][][] LEVEL_ONE_MAPS = {
			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 0, 9, 9, 0, 0, 9, 9, 0, 8},
					{7, 0, 9, 9, 0, 0, 9, 9, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 9, 9, 0, 0, 9, 9, 0, 8},
					{7, 0, 9, 9, 0, 0, 9, 9, 0, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 0, 0, 0, 9, 9, 0, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 9, 0, 0, 0, 0, 9, 0, 8},
					{7, 0, 9, 0, 0, 0, 0, 9, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 0, 9, 9, 0, 0, 0, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 9, 9, 9, 0, 0, 9, 9, 0, 8},
					{7, 9, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 9, 0, 9, 0, 9, 0, 0, 9, 8},
					{7, 9, 0, 0, 9, 0, 9, 0, 9, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 9, 8},
					{7, 0, 9, 9, 0, 0, 9, 9, 9, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 9, 0, 0, 0, 0, 0, 0, 9, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 0, 9, 9, 0, 0, 0, 8},
					{7, 0, 0, 0, 9, 9, 0, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 9, 0, 0, 0, 0, 0, 0, 9, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 9, 9, 9, 9, 0, 9, 9, 9, 8},
					{7, 9, 0, 0, 9, 0, 9, 0, 0, 8},
					{7, 9, 0, 0, 9, 0, 9, 0, 9, 8},
					{7, 9, 0, 9, 9, 0, 9, 0, 9, 8},
					{7, 9, 0, 0, 0, 0, 9, 0, 9, 8},
					{7, 9, 9, 9, 9, 9, 9, 0, 9, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 9, 0, 9, 0, 0, 0, 9, 0, 8},
					{7, 0, 0, 9, 0, 9, 0, 9, 9, 8},
					{7, 9, 9, 9, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 9, 9, 9, 8},
					{7, 9, 9, 0, 9, 0, 9, 0, 0, 8},
					{7, 0, 9, 0, 0, 0, 9, 0, 9, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 9, 9, 9, 9, 0, 0, 8},
					{7, 0, 0, 9, 0, 0, 9, 0, 0, 8},
					{7, 0, 0, 9, 0, 0, 9, 0, 0, 8},
					{7, 0, 0, 9, 9, 9, 9, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 9, 9, 0, 9, 0, 9, 9, 9, 8},
					{7, 9, 0, 0, 9, 0, 0, 0, 9, 8},
					{7, 0, 0, 0, 0, 0, 9, 0, 9, 8},
					{7, 9, 0, 9, 0, 0, 0, 0, 0, 8},
					{7, 9, 0, 0, 0, 9, 0, 0, 9, 8},
					{7, 9, 9, 9, 0, 9, 0, 9, 9, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 9, 0, 0, 0, 0, 0, 0, 9, 8},
					{7, 0, 9, 9, 0, 0, 9, 9, 0, 8},
					{7, 9, 0, 0, 0, 0, 0, 0, 9, 8},
					{7, 0, 9, 9, 0, 0, 9, 9, 0, 8},
					{7, 9, 0, 0, 0, 0, 0, 0, 9, 8},
					{7, 0, 9, 0, 9, 9, 0, 9, 0, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			},

			{
					{1, 5, 5, 5, 5, 5, 5, 5, 5, 2},
					{7, 0, 9, 0, 0, 0, 0, 9, 0, 8},
					{7, 9, 9, 0, 0, 0, 0, 9, 9, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 9, 9, 0, 0, 0, 0, 9, 9, 8},
					{7, 0, 9, 0, 0, 0, 0, 9, 0, 8},
					{3, 6, 6, 6, 6, 6, 6, 6, 6, 4}
			}
	};

	public static Queue<WaveGenInfo>[][] getEnemyWaveInfo() {
		if (waveGenInfo == null) {
			makeEnemyGenInfo();
		}

		return waveGenInfo;
	}

	private static void makeEnemyGenInfo() {
		waveGenInfo = new LinkedList[GameMap.TOTAL_LEVELS][GameMap.MAPS_PER_LEVEL];

//		waveGenInfo[0][0] = generateEnemyInfoMapOneOne();
//		waveGenInfo[0][1] = generateEnemyInfoMapOneTwo();
//		waveGenInfo[0][2] = generateEnemyInfoMapOneThree();
//		waveGenInfo[0][3] = generateEnemyInfoMapOneFour();
	}

	public static final int[][][][] LEVEL_MAPS = {
			LEVEL_ONE_MAPS
	};

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneOne() {
		//Sample map 1.1 enemy generation information
		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(70, 70)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(300, 300)));

		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}
	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneTwo() {
		//Sample map 1.2 enemy generation information
		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(300, 300)));

		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneThree() {
		//Sample map 1.3 enemy generation information
		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(300, 300)));

		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneFour() {
		//Sample map 1.4 enemy generation information
		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(300, 300)));

		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}
}
