package characterEntities;

import javafx.scene.shape.Circle;

import java.awt.*;

public class HitDetectionHelper {
	public static boolean detectSemicircleHit(Circle hitArea, Rectangle targetRect, boolean rightHalfOfCircle) {
		if (!detectHit(hitArea, targetRect))
			return false;

		return (rightHalfOfCircle && targetRect.x >= hitArea.getCenterX() ||
				!rightHalfOfCircle && targetRect.x+targetRect.width <= hitArea.getCenterX());
	}

	public static boolean detectHit(Circle hitArea, Rectangle targetRect) {
		if (hitArea == null || targetRect == null) return false;

		int hitRadius = (int)hitArea.getRadius();
		int targetWidth = targetRect.width;
		int targetHeight = targetRect.height;

		int deltaX = Math.abs((int)hitArea.getCenterX() - (targetRect.x+targetWidth/2));
		int deltaY = Math.abs((int)hitArea.getCenterY() - (targetRect.y+targetHeight/2));

		if (deltaX > (targetWidth/2 + hitRadius)) { return false; }
		if (deltaY > (targetHeight/2 + hitRadius)) { return false; }

		if (deltaX <= (targetWidth/2)) { return true; }
		if (deltaY <= (targetHeight/2)) { return true; }

		int cornerDistance = (deltaX - targetWidth/2)^2 + (deltaY - targetHeight/2)^2;

		return (cornerDistance <= (hitRadius^2));
	}

	public static boolean detectHit(Rectangle rect1, Rectangle rect2) {
		if (rect1 == null || rect2 == null) return false;

		int leftRect1 = rect1.x;
		int rightRect1 = rect1.x + rect1.width;
		int topRect1 = rect1.y;
		int botRect1 = rect1.y + rect1.height;
		int leftRect2 = rect2.x;
		int rightRect2 = rect2.x + rect2.width;
		int topRect2 = rect2.y;
		int botRect2 = rect2.y + rect2.height;

		return (leftRect1 < rightRect2 &&
				rightRect1 > leftRect2 &&
				topRect1 < botRect2 &&
				botRect1 > topRect2);
	}

	public static double calculateDistance(Rectangle rect1, Rectangle rect2) {
		if (rect1 == null || rect2 == null) return 0.0;

		Point rect1Center = new Point(rect1.x+rect1.width/2, rect1.y+rect1.height/2);
		Point rect2Center = new Point(rect2.x+rect2.width/2, rect2.y+rect2.height/2);

		return Math.hypot(rect1Center.x-rect2Center.x, rect1Center.y-rect2Center.y);
	}
}
