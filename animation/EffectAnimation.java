package animation;

import java.awt.*;

public class EffectAnimation extends Animation {
    int posX, posY;

    public enum EffectAnimationType {ENEMY_DEATH}

    public EffectAnimation(EffectAnimationType animationType, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        switch(animationType) {
            case ENEMY_DEATH:
                this.animationName = "enemyDeath";
                this.totalFrames = 3;
                break;
            default:
                break;
        }
    }

    public void draw (Graphics g) {
        if (imageIcon == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, posX, posY, null);
        }
    }
}
