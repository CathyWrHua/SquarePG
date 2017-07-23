package animation;

import java.awt.*;

public class EffectAnimation extends Animation {
    public enum EffectAnimationType {
        ENEMY_DEATH(-25, -25);
        int offset_x, offset_y;

        EffectAnimationType(int offset_x, int offset_y) {
            this.offset_x = offset_x;
            this.offset_y = offset_y;
        }

        public int getOffset_x() {
            return offset_x;
        }

        public int getOffset_y() {
            return offset_y;
        }
    }

    private int posX, posY;
    private EffectAnimationType animationType;

    public EffectAnimation(EffectAnimationType animationType, int posX, int posY) {
        this.animationType = animationType;
        this.posX = posX;
        this.posY = posY;
        switch(animationType) {
            case ENEMY_DEATH:
                this.animationName = "enemyDeath";
                this.totalFrames = 4;
                break;
            default:
                break;
        }
    }

    public void draw (Graphics g) {
        if (imageIcon == null)
            return;
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, posX+animationType.getOffset_x(), posY+animationType.getOffset_y(), null);
        }
    }
}
