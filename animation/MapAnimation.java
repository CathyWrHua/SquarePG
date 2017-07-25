package animation;

import java.awt.*;

public class MapAnimation extends Animation {
    public enum MapAnimationType {
        ENEMY_DEATH(-25, -25);
        private int offsetX, offsetY;

        MapAnimationType(int offsetX, int offsetY) {
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
    private EffectType effectType;

    public MapAnimation(MapAnimationType animationType, int posX, int posY) {
        this.effectType = EffectType.MAP_EFFECT;
        this.posX = posX + animationType.getOffsetX();
        this.posY = posY + animationType.getOffsetY();
        switch(animationType) {
            case ENEMY_DEATH:
                setValues("enemyDeath", 4, 1);
                break;
            default:
                break;
        }
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    private void setValues(String animationName, int totalFrames, int numLoops) {
        this.animationName = animationName;
        this.totalFrames = totalFrames;
        this.setNumLoops(numLoops);
    }

    public void draw (Graphics g) {
        if (imageIcon == null) return;
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, posX, posY, null);
        }
    }
}
