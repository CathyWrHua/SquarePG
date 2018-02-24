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

	public static void regenerateEnemyForMap(int level, int map) {
		if (waveGenInfo == null) {
			makeEnemyGenInfo();
		} else {
			waveGenInfo[level-1][map-1] = generateEnemyInfo(level, map);
		}
	}

	private static void makeEnemyGenInfo() {
		waveGenInfo = new LinkedList[GameMap.TOTAL_LEVELS][GameMap.MAPS_PER_LEVEL];

		for (int l = 0; l < GameMap.TOTAL_LEVELS; l++) {
			for (int m = 0; m < GameMap.MAPS_PER_LEVEL; m++) {
				waveGenInfo[l][m] = generateEnemyInfo(l+1, m+1);
			}
		}
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfo(int level, int map) {
		switch(level) {
			case 1:
				switch (map) {
					case 1:
						return generateEnemyInfoMapOneOne();
					case 2:
						return generateEnemyInfoMapOneTwo();
					case 3:
						return generateEnemyInfoMapOneThree();
					case 4:
						return generateEnemyInfoMapOneFour();
					case 5:
						return generateEnemyInfoMapOneFive();
					case 6:
						return generateEnemyInfoMapOneSix();
					case 7:
						return generateEnemyInfoMapOneSeven();
					case 8:
						return generateEnemyInfoMapOneEight();
					case 9:
						return generateEnemyInfoMapOneNine();
					case 10:
						return generateEnemyInfoMapOneBoss();
					default:
				}
				break;
			default:
		}
		return null;
	}

	public static final int[][][][] LEVEL_MAPS = {
			LEVEL_ONE_MAPS
	};

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneOne() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(30, 30)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(30, 30)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(30, 30)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 400)));

		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 400)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}
	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneTwo() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 100)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(300, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(300, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(300, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(800, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneThree() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(400, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(600, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(300, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(700, 400)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(700, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(700, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneFour() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(800, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 500)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 500)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 500)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneFive() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(900, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(900, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(900, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(900, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(20, 20)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneSix() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 500)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 500)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 500)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(800, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneSeven() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(100, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(100, 100)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(800, 650)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 650)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 650)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(800, 650)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 650)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 650)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(100, 650)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(100, 650)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(800, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 100)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(800, 100)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneEight() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(400, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 300)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(400, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 300)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 300)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(400, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(400, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 400)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneNine() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(400, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 400)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 400)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(400, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(400, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(20, 20)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(400, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(400, 200)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(400, 200)));
		waveInfo.add(new WaveGenInfo(genInfos));

		genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MAGE, new Point(500, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(20, 20)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(400, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_ARCHER, new Point(400, 400)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.BASIC_MELEE, new Point(800, 700)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}

	private static LinkedList<WaveGenInfo> generateEnemyInfoMapOneBoss() {
		LinkedList<WaveGenInfo> waveInfo = new LinkedList<>();

		Queue<EnemyGenInfo> genInfos = new LinkedList<>();
		genInfos.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.LEVEL_ONE_BOSS, new Point(400, 300)));
		waveInfo.add(new WaveGenInfo(genInfos));

		return waveInfo;
	}
}
