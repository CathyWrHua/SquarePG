package animation;

import java.awt.*;

public class EffectAnimation extends Animation {
    public enum EffectAnimationType {
        ENEMY_DEATH(-25, -25);
        int offsetX, offsetY;

        EffectAnimationType(int offsetX, int offsetY) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
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
        if (imageIcon == null) return;
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, posX+animationType.getOffsetX(), posY+animationType.getOffsetY(), null);
        }
    }
}
