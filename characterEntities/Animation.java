package characterEntities;

import java.awt.*;

public class Animation {
    private int totalFrames;
    private int currentFrame = 0;
    private String animationName;

    public Animation (String animationName, int totalFrames) {
        this.totalFrames = totalFrames;
        this.animationName = animationName;
    }

    public void update() {

    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
    }
}
