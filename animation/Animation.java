package animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Animation implements Effect {
	protected boolean done = false;
	protected int totalFrames;
	protected int counter = 0;
	protected int numLoops, currentLoop;
	protected String animationName;
	protected ImageIcon imageIcon = null;
	protected ArrayList<ImageIcon> imageIcons;

	static final int ANIMATION_SPEED = 5;
	static final String FILEPATH_ROOT = "\"src/assets/animations/\"";

	public boolean isDone() {
		return done;
	}

	protected void resetCounter() {
		this.counter = 0;
	}

	protected void resetLoops() {
		this.currentLoop = numLoops;
	}

	public void kill() {
		done = true;
	}

	public void reset() {
		resetLoops();
		resetCounter();
		done = false;
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
		this.imageIcon = imageIcons.get(currentFrame);
	}

	public abstract void draw(Graphics g);
}
