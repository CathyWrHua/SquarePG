package screens;


import characterEntities.Entity;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class GameMap {
	//arrays
	private int currentLevel;

	private HashMap<Integer, ImageIcon[][]> levelMapping = new HashMap<Integer, ImageIcon[][]>();
	private HashMap<Integer, Rectangle[]> hitRectangleMapping = new HashMap<Integer, Rectangle[]>();

	private ArrayList<Entity> currentEntityList;

	public GameMap() {
		createLevels();
	}
	
	public GameMap(int level) {
		createLevels();
		currentLevel = level;
	}
	
	public void setMap(int level) {
		if (level > 0 && level < 3) {
			currentLevel = level;
		}
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

	//TODO:A few bugs with detection, not sure what causes them
	public Point determineMotion(int newX, int newY, Rectangle objectSize){
		if (objectSize == null) {
			return null;
		}

		Rectangle[] hitRectArray = addEnemiesToHitArray(hitRectangleMapping.get(new Integer(currentLevel)));

		int objectRight = newX + objectSize.width;
		int objectLeft = newX;
		int objectTop = newY;
		int objectBottom = newY + objectSize.height;

		int displacementX = newX - objectSize.x;
		int displacementY = newY - objectSize.y;

		for (Rectangle rect:hitRectArray) {
			int hitRectRight = rect.x + rect.width;
			int hitRectLeft = rect.x;
			int hitRectBottom = rect.y + rect.height;
			int hitRectTop = rect.y;

			if (!(objectSize.y + objectSize.height == hitRectTop || objectSize.y == hitRectBottom)) {
				if (displacementX > 0) {
					if (objectRight > hitRectLeft && objectRight < hitRectRight && !(objectBottom <= hitRectTop || objectTop >= hitRectBottom)) {
						newX = rect.x - objectSize.width;
						objectRight = newX + objectSize.width;
						objectLeft = newX;
					}
				} else if (displacementX < 0) {
					if (objectLeft > hitRectLeft && objectLeft < hitRectRight && !(objectBottom <= hitRectTop || objectTop >= hitRectBottom)) {
						newX = hitRectRight;
						objectRight = newX + objectSize.width;
						objectLeft = newX;
					}
				}
			}

			if (displacementY > 0) {
				if (objectBottom > hitRectTop && objectBottom < hitRectBottom && !(objectRight <= hitRectLeft || objectLeft >= hitRectRight)) {
					newY = hitRectTop - objectSize.height;
					objectTop = newY;
					objectBottom = newY + objectSize.height;
				}
			} else if (displacementY < 0) {
				if (objectTop < hitRectBottom && objectTop > hitRectTop && !(objectRight <= hitRectLeft || objectLeft >= hitRectRight)) {
					newY = hitRectBottom;
					objectTop = newY;
					objectBottom = newY + objectSize.height;
				}
			}
		}
		return new Point(newX, newY);
	}

	public void setCurrentEntityList(ArrayList<Entity> entityList) {
		this.currentEntityList = entityList;
	}

	private Rectangle[] addEnemiesToHitArray(Rectangle[] currentMap) {
		if (currentEntityList != null && currentEntityList.size() > 0) {
			ArrayList<Rectangle> mapList = new ArrayList<Rectangle>(Arrays.asList(currentMap));
			for (Entity entity : currentEntityList) {
				mapList.add(new Rectangle(entity.getPosX(), entity.getPosY(), 75, 75));
			}
			return mapList.toArray(new Rectangle[mapList.size()]);
		}
		return currentMap;
	}

	//TODO:(cathy) thread this creation method, guard against race conditions
	private void createLevels() {
		ImageIcon[][] mapOne = new ImageIcon[10][10];
		ArrayList<Rectangle> hitRectOne = new ArrayList<Rectangle>();
		ImageIcon[][] mapTwo = new ImageIcon[10][10];
		ArrayList<Rectangle> hitRectTwo = new ArrayList<Rectangle>();
		for (int h = 0; h < 10; h++) {
			for (int w = 0; w < 10; w++) {
				if (((h > 1 && h < 4) || (h > 5 && h < 8)) && ((w > 1 && w < 4) || (w > 5 && w < 8))) {
					mapOne[h][w] = new ImageIcon("src/assets/maps/background2.png");
					hitRectOne.add(new Rectangle(w * 100, h * 100, 100, 100));
				} else {
					mapOne[h][w] = new ImageIcon("src/assets/maps/background1.png");
				}

				if ((h > 2 && h < 7) && (w > 2 && w < 7)) {
					mapTwo[h][w] = new ImageIcon("src/assets/maps/background2.png");
					hitRectTwo.add(new Rectangle(w * 100, h * 100, 100, 100));
				} else {
					mapTwo[h][w] = new ImageIcon("src/assets/maps/background1.png");
				}
			}
		}
		levelMapping.put(1, mapOne);
		levelMapping.put(2, mapTwo);

		hitRectOne = addBorder(hitRectOne);
		hitRectTwo = addBorder(hitRectTwo);

		hitRectangleMapping.put(1, hitRectOne.toArray(new Rectangle[hitRectOne.size()]));
		hitRectangleMapping.put(2, hitRectTwo.toArray(new Rectangle[hitRectTwo.size()]));
	}

	private ArrayList<Rectangle> addBorder(ArrayList<Rectangle> hitRectMap) {
		if (hitRectMap != null) {
			hitRectMap.add(new Rectangle(0, 0, 20, 1000));
			hitRectMap.add(new Rectangle(0, 0, 1000, 20));
			hitRectMap.add(new Rectangle(980, 0, 20, 1000));
			hitRectMap.add(new Rectangle(0, 925, 1000, 20));
		}

		return hitRectMap;
	}
}
