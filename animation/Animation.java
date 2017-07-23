package animation;

import javax.swing.*;
import java.awt.*;

public abstract class Animation {
    int totalFrames;
    int counter = 0;
    String animationName;
    ImageIcon imageIcon = null;
    private boolean done = false;

    static final int ANIMATION_SPEED = 5;

    public boolean isDone() {
        return done;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void update() {
        done = false;
        String filePath = "src/assets/animations/" + animationName;
        filePath += (counter/ANIMATION_SPEED) + ".png";
        this.imageIcon = new ImageIcon(filePath);
        if (counter/ANIMATION_SPEED >= totalFrames) {
            resetCounter();
            done = true;
            return;
        }
        counter++;
    }

    public abstract void draw(Graphics g);
}
