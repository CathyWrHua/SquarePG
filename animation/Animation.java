package animation;

import javax.swing.*;
import java.awt.*;

public abstract class Animation {
    int totalFrames;
    int counter = 0;
    String animationName;
    ImageIcon imageIcon = null;
    private boolean done = false;
    private int numLoops, currentLoop;

    static final int ANIMATION_SPEED = 5;

    public boolean isDone() {
        return done;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void setNumLoops(int numLoops) {
        this.numLoops = numLoops;
        this.currentLoop = numLoops;
    }

    private void resetLoops() {
        this.currentLoop = numLoops;
    }

    public void update() {
        done = false;
        int currentFrame = counter++/ANIMATION_SPEED;
        String filePath = "src/assets/animations/" + animationName;
        filePath += currentFrame + ".png";
        this.imageIcon = new ImageIcon(filePath);
        if (currentFrame >= totalFrames) {
            resetCounter();
            if (--currentLoop < 1) {
                done = true;
                resetLoops();
            }
        }
    }

    public abstract void draw(Graphics g);
}
