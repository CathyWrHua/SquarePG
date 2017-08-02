package animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapAnimation extends Animation {
	public enum MapAnimationType {
		ENEMY_DEATH(-50, -50);
		private int offsetX, offsetY;

		MapAnimationType(int offsetX, int offsetY) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}
	}

	private int posX, posY;
	private EffectType effectType;

	public MapAnimation(MapAnimationType animationType, int posX, int posY) {
		this.effectType = EffectType.MAP_EFFECT;
		this.posX = posX + animationType.getOffsetX();
		this.posY = posY + animationType.getOffsetY();
		switch(animationType) {
			case ENEMY_DEATH:
				setValues("enemyDeath", 4, 1);
				break;
			default:
				break;
		}
		this.imageIcons = new ArrayList<>(totalFrames);
		for (int i = 0; i < totalFrames; i++) {
			imageIcons.add(i, new ImageIcon("src/assets/animations/"+animationName+i+".png"));
		}
	}

	@Override
	public EffectType getEffectType() {
		return effectType;
	}

	private void setValues(String animationName, int totalFrames, int numLoops) {
		this.animationName = animationName;
		this.totalFrames = totalFrames;
		this.setNumLoops(numLoops);
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
