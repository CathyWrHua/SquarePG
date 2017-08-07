package animation;

import animation.effects.MapEffect;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Animation implements Drawable {
	private boolean done = false;
	private boolean shouldMirror = false;
	private int posX, posY, offsetX, offsetY;

	private int totalFrames;
	private int counter = 0;
	private int numLoops, currentLoop;
	private String animationName;
	private ImageIcon currentImage = null;
	private ArrayList<ImageIcon> imageIcons;

	static final int ANIMATION_SPEED = 5;
	static final String FILEPATH_ROOT = "src/assets/animations/";
	static final String FILEPATH_FILETYPE = ".png";

	public Animation (int posX,
					  int posY,
					  int offsetX,
					  int offsetY,
					  String animationName,
					  int totalFrames,
					  int numLoops) {
		this.posX = posX;
		this.posY = posY;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.animationName = FILEPATH_ROOT + animationName;
		this.totalFrames = totalFrames;
		this.numLoops = numLoops;
		this.currentLoop = numLoops;

		this.imageIcons = new ArrayList<>(totalFrames);
		for (int i = 0; i < totalFrames; i++) {
			this.imageIcons.add(new ImageIcon(this.animationName + i + FILEPATH_FILETYPE));
		}
	}

	public boolean isDone() {
		return done;
	}

	public void reset() {
		resetLoops();
		resetCounter();
		done = false;
	}

	public void setPosition (int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public Rectangle getSize() {
		return (currentImage == null)? null:
				new Rectangle(offsetX+posX, posY+offsetY, currentImage.getIconWidth(), currentImage.getIconHeight());
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void killAnimation() {
		done = true;
	}

//	public int getCurrentFrame() {
//		return counter/ANIMATION_SPEED;
//	}

	protected void resetCounter() {
		this.counter = 0;
	}

	protected void resetLoops() {
		this.currentLoop = numLoops;
	}

	public void setNumLoops(int numLoops) {
		this.numLoops = numLoops;
		this.currentLoop = numLoops;
	}

	public void update() {
		int currentFrame = counter++/ANIMATION_SPEED;
		if (counter/ANIMATION_SPEED >= totalFrames) {
			resetCounter();
			currentFrame = 0;
			done = --currentLoop < 1;
		}
		currentImage = imageIcons.get(currentFrame);
	}

	public void shouldMirror(boolean mirror) {
		shouldMirror = mirror;
	}

	public void draw(Graphics g) {
		if (currentImage == null) return;
		Graphics2D g2d = (Graphics2D)g;
		Image image = currentImage.getImage();
		int x = posX;
		int width = image.getWidth(null);

		if (shouldMirror) {
			x += width;
			width = -width;
		}

		if (counter/ANIMATION_SPEED < totalFrames) {
			g2d.drawImage(image, x+offsetX, posY+offsetY, width, image.getHeight(null), null);
		}
	}
}
