package characterEntities;

import javax.swing.*;
import java.awt.*;

public class Animation {
    private int totalFrames;
    private int counter = 0;
    private Entity entity;
    private String animationName;
    private ImageIcon imageIcon = null;
    private boolean hasDirection = true;
    private boolean done = false;

    private static final int OFFSET = 75;
    private static final int ANIMATION_SPEED = 5;
    
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

    public boolean isDone() {
        return done;
    }

    public void update () {
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

    public void resetCounter () {
        this.counter = 0;
    }

    public void draw (Graphics g) {
        if (imageIcon == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D)g;

        Image image = imageIcon.getImage();
        int x = entity.getPosX();
        int width = image.getWidth(null);

        int offset = !hasDirection ? 0 :
                (entity.getFacingEast()) ? OFFSET : -OFFSET;

        if (hasDirection && !entity.getFacingEast()) {
            x += width;
            width = -width;
        }

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, x + offset, entity.getPosY(), width, image.getHeight(null), null);
        }
    }
}
