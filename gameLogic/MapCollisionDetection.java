package gameLogic;

import characterEntities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MapCollisionDetection {
	private Rectangle[] hitRectArray;

	public MapCollisionDetection(Rectangle[] hitRectArray) {
		this.hitRectArray = hitRectArray;
	}

	public void setHitRectArray(Rectangle[] hitRectArray) {
		this.hitRectArray = hitRectArray;
	}

	//Determine motion works for almost all cases, except for two edge cases
	//Edge cases occur due to the order of the arrayList: (corner with two motion directions)
	//HACK: reverse the arraylist in these two cases to correctly calculate the new x and y
	public Point determineMotion(int newX, int newY, Rectangle objectSize, ArrayList<Entity> currentEntityList){
		if (objectSize == null) {
			return null;
		}

		int objectRight = newX + objectSize.width;
		int objectLeft = newX;
		int objectTop = newY;
		int objectBottom = newY + objectSize.height;

		int displacementX = newX - objectSize.x;
		int displacementY = newY - objectSize.y;

		if (displacementX == 0 && displacementY == 0) {
			return new Point(newX, newY);
		}

		hitRectArray = addEnemiesToHitArray(hitRectArray, currentEntityList);

		if (displacementX < 0 && displacementY < 0) {
			ArrayList<Rectangle> tempList = new ArrayList<>(Arrays.asList(hitRectArray));
			Collections.reverse(tempList);
			hitRectArray = tempList.toArray(new Rectangle[tempList.size()]);
		}

		for (Rectangle rect:hitRectArray) {
			int hitRectRight = rect.x + rect.width;
			int hitRectLeft = rect.x;
			int hitRectBottom = rect.y + rect.height;
			int hitRectTop = rect.y;

			if ((displacementX != 0 || displacementY != 0) &&
					hitRectLeft < objectRight &&
					hitRectRight > objectLeft &&
					hitRectTop < objectBottom &&
					hitRectBottom > objectTop) {
				//collision was detected

				if (displacementX > 0) {
					if (objectLeft < hitRectLeft) {
						if (displacementY == 0) {
							newX = rect.x - objectSize.width;
						} else if (displacementY > 0) {
							int collisionWidth = objectRight - hitRectLeft;
							int collisionHeight = objectBottom - hitRectTop;
							if (collisionHeight > collisionWidth) {
								newX = rect.x - objectSize.width;
							} else {
								newY = rect.y - objectSize.height;
							}
						} else {
							int collisionWidth = objectRight - hitRectLeft;
							int collisionHeight = hitRectBottom - objectTop;
							if (collisionHeight > collisionWidth) {
								newX = rect.x - objectSize.width;
							} else if (collisionHeight == collisionWidth) {
								newX = rect.x - objectSize.width;
							} else {
								newY = hitRectBottom;
							}
						}

						objectRight = newX + objectSize.width;
						objectLeft = newX;
						objectTop = newY;
						objectBottom = newY + objectSize.height;

						continue;
					}
				} else if (displacementX < 0) {
					if (objectRight > hitRectRight) {
						if (displacementY == 0) {
							newX = hitRectRight;
						} else if (displacementY > 0) {
							int collisionWidth = hitRectRight - objectLeft;
							int collisionHeight = objectBottom - hitRectTop;
							if (collisionHeight > collisionWidth) {
								newX = hitRectRight;
							} else {
								newY = rect.y - objectSize.height;
							}
						} else {
							int collisionWidth = hitRectRight - objectLeft;
							int collisionHeight = hitRectBottom - objectTop;
							if (collisionHeight > collisionWidth) {
								newX = hitRectRight;
							} else {
								newY = hitRectBottom;
							}
						}

						objectRight = newX + objectSize.width;
						objectLeft = newX;
						objectTop = newY;
						objectBottom = newY + objectSize.height;
						continue;
					}
				}

				if (displacementY > 0) {
					if (objectTop < hitRectTop) {
						newY = rect.y - objectSize.height;
						objectTop = newY;
						objectBottom = newY + objectSize.height;
						continue;
					}
				} else if (displacementY < 0) {
					if (objectBottom > hitRectBottom) {
						newY = hitRectBottom;
						objectTop = newY;
						objectBottom = newY + objectSize.height;
						continue;
					}
				}
			}
		}
		return new Point(newX, newY);
	}

	// Private helper functions
	private Rectangle[] addEnemiesToHitArray(Rectangle[] currentMap, ArrayList<Entity> entityArrayList) {
		if (entityArrayList != null && entityArrayList.size() > 0) {
			ArrayList<Rectangle> mapList = new ArrayList<Rectangle>(Arrays.asList(currentMap));
			for (Entity entity : entityArrayList) {
				mapList.add(entity.getEntitySize());
			}
			return mapList.toArray(new Rectangle[mapList.size()]);
		}
		return currentMap;
	}
}
