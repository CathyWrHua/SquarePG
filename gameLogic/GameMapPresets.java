package gameLogic;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class GameMapPresets {

	private static Queue<WaveGenInfo>[][] waveGenInfo;
	private static final int NUMBER_LEVELS = 1;
	private static final int MAX_NUMBER_SUBLEVELS = 4;

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
					{7, 0, 9, 0, 0, 0, 0, 9, 0, 8},
					{7, 9, 9, 0, 0, 0, 0, 9, 9, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 0, 0, 0, 0, 0, 0, 0, 0, 8},
					{7, 9, 9, 0, 0, 0, 0, 9, 9, 8},
					{7, 0, 9, 0, 0, 0, 0, 9, 0, 8},
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
			}
	};

	public static Queue<WaveGenInfo>[][] getEnemyWaveInfo() {
		if (waveGenInfo == null) {
			makeEnemyGenInfo();
		}

		return waveGenInfo;
	}

	private static void makeEnemyGenInfo() {
		waveGenInfo = new LinkedList[NUMBER_LEVELS][MAX_NUMBER_SUBLEVELS];

		//Sample map 1.1 enemy generation information
		Queue<EnemyGenInfo> mapOneOneFirst = new LinkedList<>();
		mapOneOneFirst.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(70, 70)));
		mapOneOneFirst.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(500, 500)));
		mapOneOneFirst.add(new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, new Point(300, 300)));

		LinkedList<WaveGenInfo> mapOneOne = new LinkedList<>();
		mapOneOne.add(new WaveGenInfo(mapOneOneFirst));

		waveGenInfo[0][0] = mapOneOne;
	}

	public static final int[][][][] LEVEL_MAPS = {
			LEVEL_ONE_MAPS
	};
}
