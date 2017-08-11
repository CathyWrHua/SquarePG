package gameLogic;

import characterEntities.Entity;
import characterEntities.HitDetectionHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

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
	public Point determineMotion(int newX, int newY, Rectangle objectSize, LinkedList<Entity> currentEntityList){
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


		Rectangle[] detectionList = addEnemiesToHitArray(hitRectArray, currentEntityList);

		if (displacementX < 0 && displacementY < 0) {
			ArrayList<Rectangle> tempList = new ArrayList<>(Arrays.asList(detectionList));
			Collections.reverse(tempList);
			detectionList = tempList.toArray(new Rectangle[tempList.size()]);
		}

		for (Rectangle rect:detectionList) {
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

	public boolean detectCollision(Rectangle object, LinkedList<Entity> entityList) {
		if (object == null) return false;

		Rectangle[] detectionList = addEnemiesToHitArray(hitRectArray, entityList);
		boolean collision = false;

		for (Rectangle rect:detectionList) {
			collision = collision || HitDetectionHelper.detectHit(rect, object);
			if (collision) break;
		}
		return collision;
	}

	// Private helper functions
	private Rectangle[] addEnemiesToHitArray(Rectangle[] currentMap, LinkedList<Entity> entityList) {
		if (entityList != null && entityList.size() > 0) {
			ArrayList<Rectangle> mapList = new ArrayList<>(Arrays.asList(currentMap));
			for (Entity entity : entityList) {
				mapList.add(entity.getEntitySize());
			}
			return mapList.toArray(new Rectangle[mapList.size()]);
		}
		return currentMap;
	}
}
