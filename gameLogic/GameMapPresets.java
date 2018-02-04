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

		waveGenInfo[0][0] = generateEnemyInfoMapOneOne();
		waveGenInfo[0][1] = generateEnemyInfoMapOneTwo();
		waveGenInfo[0][2] = generateEnemyInfoMapOneThree();
		waveGenInfo[0][3] = generateEnemyInfoMapOneFour();
		waveGenInfo[0][4] = generateEnemyInfoMapOneFive();
		waveGenInfo[0][5] = generateEnemyInfoMapOneSix();
		waveGenInfo[0][6] = generateEnemyInfoMapOneSeven();
		waveGenInfo[0][7] = generateEnemyInfoMapOneEight();
		waveGenInfo[0][8] = generateEnemyInfoMapOneNine();
		waveGenInfo[0][9] = generateEnemyInfoMapOneBoss();
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
