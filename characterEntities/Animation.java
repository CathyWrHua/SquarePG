package characterEntities;

import javax.swing.*;
import java.awt.*;

public class Animation {
    private int totalFrames;
    private int currentFrame = 0;
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
        if (counter++ % ANIMATION_SPEED == 0 && currentFrame < totalFrames) {
            String filePath = "src/assets/animations/" + animationName;
            filePath += (currentFrame++) + ".png";
            this.imageIcon = new ImageIcon(filePath);
        } else if (currentFrame >= totalFrames && counter++ % ANIMATION_SPEED == 0) {
            reset();
            done = true;
        }
    }

    public void reset () {
        this.counter = 0;
        this.currentFrame = 0;
    }

    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        if (imageIcon != null) {
            Image image = imageIcon.getImage();
            int x = entity.getPosX();
            int width = image.getWidth(null);

            int offset = !hasDirection ? 0 :
                    (entity.getFacingEast()) ? OFFSET : -OFFSET;

            if (hasDirection && !entity.getFacingEast()) {
                x += width;
                width = -width;
            }

            if (currentFrame < totalFrames) {
                g2d.drawImage(image, x + offset, entity.getPosY(), width, image.getHeight(null), null);
            }
        }
    }
}
