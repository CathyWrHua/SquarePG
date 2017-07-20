package screens;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class GameMap {
	//arrays
	private int currentLevel;

	private HashMap<Integer, ImageIcon[][]> levelMapping = new HashMap<Integer, ImageIcon[][]>();

	public GameMap() {
		createLevels();
	}
	
	public GameMap(int level) {
		createLevels();
		currentLevel = level;
	}
	
	public void setMap(int level) {
		currentLevel = level;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		ImageIcon[][] currentMap = levelMapping.get(new Integer(currentLevel));

		for (int h = 0; h < currentMap.length; h++) {
			for (int w = 0; w < currentMap[h].length; w++) {
				g2d.drawImage(currentMap[h][w].getImage(), 100*h, 100*w, null);
			}
		}
	}

	//TODO:(cathy) thread this creation method, guard against race conditions
	private void createLevels() {
		ImageIcon[][] gameMapLevelOne = new ImageIcon[10][10];
		ImageIcon[][] gameMapLevelTwo = new ImageIcon[10][10];
		for (int h = 0; h < 10; h++) {
			for (int w = 0; w < 10; w++) {
				if (((h > 1 && h < 4) || (h > 5 && h < 8)) && ((w > 1 && w < 4) || (w > 5 && w < 8))) {
					gameMapLevelOne[h][w] = new ImageIcon("src/assets/background2.png");
				} else {
					gameMapLevelOne[h][w] = new ImageIcon("src/assets/background1.png");
				}

				if ((h > 2 && h < 7) && (w > 2 && w < 7)) {
					gameMapLevelTwo[h][w] = new ImageIcon("src/assets/background2.png");
				} else {
					gameMapLevelTwo[h][w] = new ImageIcon("src/assets/background1.png");
				}
			}
		}
		levelMapping.put(new Integer(1), gameMapLevelOne);
		levelMapping.put(new Integer(2), gameMapLevelTwo);
	}
}
