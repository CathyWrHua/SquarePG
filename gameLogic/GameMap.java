package gameLogic;

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
	private static final int MAP_WIDTH = 10;
	private static final int MAP_HEIGHT = 8;
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
		if (level > 0 && level < TOTAL_LEVELS+1 && map > 0 && map < MAPS_PER_LEVEL+1) {
			currentLevel = level;
			currentMap = map;
		} else {
			currentLevel = 1;
			currentMap = 1;
		}
	}

	public Rectangle[] getCurrentCollisionMap () {
		return hitRectangleMapping.get(currentLevel+" "+currentMap);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		ImageIcon[][] currentStage = levelMapping.get(currentLevel+" "+currentMap);

		for (int h = 0; h < currentStage.length; h++) {
			for (int w = 0; w < currentStage[h].length; w++) {
				g2d.drawImage(currentStage[h][w].getImage(), TILE_LENGTH*w, TILE_LENGTH*h, null);
			}
		}
	}

	//TODO:(cathy) thread this creation method, guard against race conditions
	private void createLevels() {
		ImageIcon[][][][] maps = new ImageIcon[TOTAL_LEVELS][MAPS_PER_LEVEL][MAP_HEIGHT][MAP_WIDTH];
		ArrayList<Rectangle>[][] hitRects = new ArrayList[TOTAL_LEVELS][MAPS_PER_LEVEL];
		ImageIcon[] mapTiles = new ImageIcon[NUM_TILE_TYPES];

		for (int i = 0; i < TOTAL_LEVELS; i++) {
			for (int j = 0; j < MAPS_PER_LEVEL; j++) {
				hitRects[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < TOTAL_LEVELS; i++) {
			for (int m = 0; m < NUM_TILE_TYPES; m++) mapTiles[m] = new ImageIcon(FILEPATH_ROOT+
					(i+1)+FILEPATH_BACKGROUND+m+FILEPATH_PNG);
			for (int j = 0; j < MAPS_PER_LEVEL; j++) {
				maps[i][j][0][0] = mapTiles[1];
				maps[i][j][0][9] = mapTiles[2];
				maps[i][j][7][0] = mapTiles[3];
				maps[i][j][7][9] = mapTiles[4];
				for (int w = 1; w < MAP_WIDTH - 1; w++) {
					maps[i][j][0][w] = mapTiles[5];
					maps[i][j][7][w] = mapTiles[6];
				}
				for (int h = 1; h < MAP_HEIGHT - 1; h++) {
					maps[i][j][h][0] = mapTiles[7];
					maps[i][j][h][9] = mapTiles[8];
				}
				for (int h = 0; h < MAP_HEIGHT - 2; h++) {
					for (int w = 0; w < MAP_WIDTH - 2; w++) {
						if (GameMapPresets.LEVEL_MAPS[i][j][h][w] == 1) {
							maps[i][j][h + 1][w + 1] = mapTiles[9];
							hitRects[i][j].add(new Rectangle((w+1)*TILE_LENGTH, (h+1)*TILE_LENGTH, TILE_LENGTH, TILE_LENGTH));
						} else {
							maps[i][j][h + 1][w + 1] = mapTiles[0];
						}
					}
				}
			}
		}

		for (int i = 0; i < TOTAL_LEVELS; i++) {
			for (int j = 0; j < MAPS_PER_LEVEL; j++) {
				ArrayList<Rectangle> currentHitRect = hitRects[i][j];
				levelMapping.put((i+1)+" "+(j+1), maps[i][j]);
				hitRects[i][j] = addBorder(currentHitRect);
				hitRectangleMapping.put((i+1)+" "+(j+1), hitRects[i][j].toArray(new Rectangle[currentHitRect.size()]));
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
}
