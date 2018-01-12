package gameLogic;

import characterEntities.Enemy;
import characterEntities.Hero;
import screens.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class GameMap implements Drawable {
	private int currentLevel;
	private int currentMap;
	private HashMap<String, ImageIcon[][]> levelMapping = new HashMap<>();
	private HashMap<String, Rectangle[]> hitRectangleMapping = new HashMap<>();

	public static final int TOTAL_LEVELS = 1;
	public static final int MAPS_PER_LEVEL = 4;
	private static final int MAP_WIDTH_TILES = 10;
	private static final int MAP_HEIGHT_TILES = 8;
	private static final int NUM_TILE_TYPES = 10;
	private static final int TILE_LENGTH = 100;

	private static final String FILEPATH_ROOT = "src/assets/maps/";
	private static final String FILEPATH_BACKGROUND = "/background";
	private static final String FILEPATH_PNG = ".png";
	
	public GameMap(int level, int map) {
		createLevels();
		setStage(level, map);
	}
	
	public void setStage(int level, int map) {
		if (level > 0 && level <= TOTAL_LEVELS && map > 0 && map <= MAPS_PER_LEVEL) {
			currentLevel = level;
			currentMap = map;
		} else {
			currentLevel = 1;
			currentMap = 1;
		}
	}

	public Rectangle[] getCurrentCollisionMap () {
		return hitRectangleMapping.get(currentLevel+"-"+currentMap);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		ImageIcon[][] currentStage = levelMapping.get(currentLevel+"-"+currentMap);

		for (int h = 0; h < currentStage.length; h++) {
			for (int w = 0; w < currentStage[h].length; w++) {
				g2d.drawImage(currentStage[h][w].getImage(), TILE_LENGTH*w, TILE_LENGTH*h, null);
			}
		}
	}

	//TODO:(cathy) thread this creation method, guard against race conditions
	private void createLevels() {
		ImageIcon[][][][] maps = new ImageIcon[TOTAL_LEVELS][MAPS_PER_LEVEL][MAP_HEIGHT_TILES][MAP_WIDTH_TILES];
		ArrayList<Rectangle>[][] hitRects = new ArrayList[TOTAL_LEVELS][MAPS_PER_LEVEL];
		ImageIcon[] mapTiles = new ImageIcon[NUM_TILE_TYPES];

		for (int i = 0; i < TOTAL_LEVELS; i++) {
			for (int j = 0; j < MAPS_PER_LEVEL; j++) {
				hitRects[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < TOTAL_LEVELS; i++) {
			for (int m = 0; m < NUM_TILE_TYPES; m++) {
				mapTiles[m] = new ImageIcon(FILEPATH_ROOT + (i + 1) + FILEPATH_BACKGROUND + m + FILEPATH_PNG);
			}
			for (int j = 0; j < MAPS_PER_LEVEL; j++) {
				for (int h = 0; h < MAP_HEIGHT_TILES; h++) {
					for (int w = 0; w < MAP_WIDTH_TILES; w++) {
						maps[i][j][h][w] = mapTiles[GameMapPresets.LEVEL_MAPS[i][j][h][w]];
						if (GameMapPresets.LEVEL_MAPS[i][j][h][w] == 9)
							hitRects[i][j].add(new Rectangle(w*TILE_LENGTH, h*TILE_LENGTH, TILE_LENGTH, TILE_LENGTH));
					}
				}
			}
		}

		for (int i = 0; i < TOTAL_LEVELS; i++) {
			for (int j = 0; j < MAPS_PER_LEVEL; j++) {
				ArrayList<Rectangle> currentHitRect = hitRects[i][j];
				levelMapping.put((i+1)+"-"+(j+1), maps[i][j]);
				hitRects[i][j] = addBorder(currentHitRect);
				hitRectangleMapping.put((i+1)+"-"+(j+1), hitRects[i][j].toArray(new Rectangle[currentHitRect.size()]));
			}
		}
	}

	private ArrayList<Rectangle> addBorder(ArrayList<Rectangle> hitRectMap) {
		if (hitRectMap != null) {
			hitRectMap.add(new Rectangle(0, 0, 25, 800));
			hitRectMap.add(new Rectangle(0, 0, 1000, 25));
			hitRectMap.add(new Rectangle(975, 0, 25, 800));
			hitRectMap.add(new Rectangle(0, 775, 1000, 25));
		}
		return hitRectMap;
	}

	//Example of how to instantiate an enemy from preset
	public Enemy creatNewEnemy(Hero hero, MapCollisionDetection map) {
		EnemyGenInfo info = new EnemyGenInfo(EnemyGenInfo.EnemyType.GRUNT, 0);

		Enemy grunt = null;

		try {
			grunt = (Enemy) info.getEnemyClass().getConstructor(Hero.class, MapCollisionDetection.class, int.class, int.class, int.class, int.class, int.class, double.class)
					.newInstance(hero, map, 10, 6, 2, 100, 100, 1.2d);
		} catch (Exception e) {

		}

		return grunt;

	}
}
