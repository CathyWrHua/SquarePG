package animation;

import javax.swing.*;
import java.awt.*;

public abstract class Animation implements Effect {
    boolean done = false;
    int totalFrames;
    int counter = 0;
    int numLoops, currentLoop;
    String animationName;
    ImageIcon imageIcon = null;

    static final int ANIMATION_SPEED = 5;

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
        if (currentFrame >= totalFrames) {
            resetCounter();
            currentFrame = 0;
            done = --currentLoop < 1;
        }
        String filePath = "src/assets/animations/" + animationName;
        filePath += currentFrame + ".png";
        this.imageIcon = new ImageIcon(filePath);
    }

    public abstract void draw(Graphics g);
}
