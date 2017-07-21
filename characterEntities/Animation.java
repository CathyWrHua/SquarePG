package characterEntities;

import javax.swing.*;
import java.awt.*;

public class Animation {
    private int totalFrames;
    private int currentFrame = 0;
    private int counter = 0;
    private Entity entity;
    private String animationName;
    private ImageIcon image = null;
    private boolean hasDirection = false;
    private boolean done = false;

    private static final int OFFSET = 75;
    private static final int ANIMATION_SPEED = 7;
    
    public enum AnimationType {DEFAULT}

    public Animation (AnimationType animationType, Entity entity) {
        this.entity = entity;
        switch(animationType) {
            case DEFAULT:
                this.animationName = "default";
                this.totalFrames = 4;
                break;
            default:
                break;
        }
    }

    public boolean isDone () {
        return done;
    }

    public void update () {
        if (counter++ % ANIMATION_SPEED == 0 && currentFrame < totalFrames && !done) {
            String filePath = "src/assets/animations/" + animationName;
            filePath += hasDirection ? "" : entity.getDirection();
            filePath += (currentFrame++) + ".png";
            this.image = new ImageIcon(filePath);
        } else if (currentFrame >= totalFrames && counter++ % ANIMATION_SPEED == 0) {
            this.done = true;
        }
    }

    public void reset () {
        this.counter = 0;
        this.currentFrame = 0;
        this.done = false;
    }

    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int offset = hasDirection ? 0 :
                (entity.getDirection() == "East") ? OFFSET : -OFFSET;

        if (!done && image != null) {
            g2d.drawImage(image.getImage(), entity.getPosX()+offset, entity.getPosY(), null);
        }
    }
}
