package characterEntities;

import javax.swing.*;
import java.awt.*;

public class Animation {
    private int totalFrames;
    private int currentFrame = 0;
    private Entity entity;
    private String animationName;
    private ImageIcon image = null;
    private boolean done = false;

    public Animation (String animationName, Entity entity) {
        this.animationName = animationName;
        this.entity = entity;
        switch(animationName) {
            case "default":
                this.totalFrames = 3;
                break;
            default:
                break;
        }
    }

    public boolean isDone () {
        return done;
    }

    public void update () {
        if (currentFrame < totalFrames) {
            String filePath = "src/assets/" + animationName + entity.getDirection() + (currentFrame++) + ".png";
            this.image = new ImageIcon(filePath);
        } else {
            this.done = true;
        }
    }

    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        if (!done && image != null) {
            // do drawing
        }
    }
}
