package gameLogic;

import screens.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class GameMap extends Drawable {
	private int currentLevel;
	private HashMap<Integer, ImageIcon[][]> levelMapping = new HashMap<>();
	private HashMap<Integer, Rectangle[]> hitRectangleMapping = new HashMap<>();

	public GameMap() {
		this(1);
	}
	
	public GameMap(int level) {
		drawableType = DrawableType.BACKGROUND;
		createLevels();
		currentLevel = level;
	}


	
	public void setMap(int level) {
		if (level > 0 && level < 4) {
			currentLevel = level;
		}
	}

	public Rectangle[] getCurrentCollisionMap () {
		return hitRectangleMapping.get(currentLevel);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		ImageIcon[][] currentMap = levelMapping.get(new Integer(currentLevel));

		for (int h = 0; h < currentMap.length; h++) {
			for (int w = 0; w < currentMap[h].length; w++) {
				g2d.drawImage(currentMap[h][w].getImage(), 100*w, 100*h, null);
			}
		}
	}

	//Private helper functions

	//TODO:(cathy) thread this creation method, guard against race conditions
	private void createLevels() {
		ImageIcon[][] mapOne = new ImageIcon[10][10];
		ImageIcon[][] mapTwo = new ImageIcon[10][10];
		ImageIcon[][] mapThree = new ImageIcon[10][10];

		ArrayList<Rectangle> hitRectOne = new ArrayList<>();
		ArrayList<Rectangle> hitRectTwo = new ArrayList<>();
		ArrayList<Rectangle> hitRectThree = new ArrayList<>();

		for (int h = 0; h < 10; h++) {
			for (int w = 0; w < 10; w++) {

				if (h == 0) {
					if (w == 0) {
						mapOne[h][w] = new ImageIcon("src/assets/maps/background3.png");
						mapTwo[h][w] = new ImageIcon("src/assets/maps/background3.png");
						mapThree[h][w] = new ImageIcon("src/assets/maps/background3.png");
					} else if (w == 9){
						mapOne[h][w] = new ImageIcon("src/assets/maps/background8.png");
						mapTwo[h][w] = new ImageIcon("src/assets/maps/background8.png");
						mapThree[h][w] = new ImageIcon("src/assets/maps/background8.png");
					} else {
						mapOne[h][w] = new ImageIcon("src/assets/maps/background5.png");
						mapTwo[h][w] = new ImageIcon("src/assets/maps/background5.png");
						mapThree[h][w] = new ImageIcon("src/assets/maps/background5.png");
					}
				} else if (h == 9) {
					if (w == 0) {
						mapOne[h][w] = new ImageIcon("src/assets/maps/background9.png");
						mapTwo[h][w] = new ImageIcon("src/assets/maps/background9.png");
						mapThree[h][w] = new ImageIcon("src/assets/maps/background9.png");
					} else if (w == 9){
						mapOne[h][w] = new ImageIcon("src/assets/maps/background10.png");
						mapTwo[h][w] = new ImageIcon("src/assets/maps/background10.png");
						mapThree[h][w] = new ImageIcon("src/assets/maps/background10.png");
					} else {
						mapOne[h][w] = new ImageIcon("src/assets/maps/background6.png");
						mapTwo[h][w] = new ImageIcon("src/assets/maps/background6.png");
						mapThree[h][w] = new ImageIcon("src/assets/maps/background6.png");
					}
				} else if (w == 0) {
					mapOne[h][w] = new ImageIcon("src/assets/maps/background4.png");
					mapTwo[h][w] = new ImageIcon("src/assets/maps/background4.png");
					mapThree[h][w] = new ImageIcon("src/assets/maps/background4.png");
				} else if (w == 9) {
					mapOne[h][w] = new ImageIcon("src/assets/maps/background7.png");
					mapTwo[h][w] = new ImageIcon("src/assets/maps/background7.png");
					mapThree[h][w] = new ImageIcon("src/assets/maps/background7.png");
				}

				if (((h > 1 && h < 4) || (h > 5 && h < 8)) && ((w > 1 && w < 4) || (w > 5 && w < 8))) {
					mapOne[h][w] = new ImageIcon("src/assets/maps/background2.png");
					hitRectOne.add(new Rectangle(w * 100, h * 100, 100, 100));
				} else if (w != 0 && w != 9 && h != 9 && h != 0) {
					mapOne[h][w] = new ImageIcon("src/assets/maps/background1.png");
				}

				if ((h > 2 && h < 7) && (w > 2 && w < 7)) {
					mapTwo[h][w] = new ImageIcon("src/assets/maps/background2.png");
					hitRectTwo.add(new Rectangle(w * 100, h * 100, 100, 100));
				} else if (w != 0 && w != 9 && h != 9 && h != 0) {
					mapTwo[h][w] = new ImageIcon("src/assets/maps/background1.png");
				}

				if (w != 0 && w != 9 && h != 9 && h != 0) {
					mapThree[h][w] = new ImageIcon("src/assets/maps/background1.png");
				}
			}
		}

		levelMapping.put(1, mapOne);
		levelMapping.put(2, mapTwo);
		levelMapping.put(3, mapThree);

		hitRectOne = addBorder(hitRectOne);
		hitRectTwo = addBorder(hitRectTwo);
		hitRectThree = addBorder(hitRectThree);

		hitRectangleMapping.put(1, hitRectOne.toArray(new Rectangle[hitRectOne.size()]));
		hitRectangleMapping.put(2, hitRectTwo.toArray(new Rectangle[hitRectTwo.size()]));
		hitRectangleMapping.put(3, hitRectThree.toArray(new Rectangle[hitRectThree.size()]));
	}

	private ArrayList<Rectangle> addBorder(ArrayList<Rectangle> hitRectMap) {
		if (hitRectMap != null) {
			hitRectMap.add(new Rectangle(0, 0, 25, 1000));
			hitRectMap.add(new Rectangle(0, 0, 1000, 25));
			hitRectMap.add(new Rectangle(975, 0, 25, 1000));
			hitRectMap.add(new Rectangle(0, 975, 1000, 25));
		}
		return hitRectMap;
	}
}
