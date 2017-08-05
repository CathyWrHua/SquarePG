package animation.effects;

import animation.Animation;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapAnimation extends MapEffect {
	public enum MapAnimationType {
		ENEMY_DEATH(-50, -50, "enemyDeath", 4, 1),
		FIREBALL(-38, -38, "fireballExplosion", 4, 1);
		private int offsetX, offsetY;
		private String animationName;
		private int totalFrames, numLoops;

		MapAnimationType(int offsetX, int offsetY, String animationName, int totalFrames, int numLoops) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.animationName = animationName;
			this.totalFrames = totalFrames;
			this.numLoops = numLoops;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}

		public String getAnimationName() {
			return animationName;
		}

		public int getTotalFrames() {
			return totalFrames;
		}

		public int getNumLoops() {
			return numLoops;
		}
	}

	private int posX, posY;

	public MapAnimation(MapAnimationType animationType, int posX, int posY) {
		this.effectType = EffectType.MAP_EFFECT;
		this.animationName = animationType.getAnimationName();
		this.totalFrames = animationType.getTotalFrames();
		this.setNumLoops(animationType.getNumLoops());
		this.imageIcons = new ArrayList<>(totalFrames);
		for (int i = 0; i < totalFrames; i++) {
			imageIcons.add(i, new ImageIcon(FILEPATH_ROOT+animationName+i+FILEPATH_PNG));
		}
		this.posX = posX + animationType.getOffsetX();
		this.posY = posY + animationType.getOffsetY();
	}

	public void draw (Graphics g) {
		if (imageIcon == null) return;
		Graphics2D g2d = (Graphics2D)g;
		Image image = imageIcon.getImage();

		if (counter/ANIMATION_SPEED < totalFrames) {
			g2d.drawImage(image, posX, posY, null);
		}
	}
}
