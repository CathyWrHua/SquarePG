package animation;

import characterEntities.Entity;

import java.awt.*;

public class AbilityAnimation extends Animation {
    private Entity entity;
    private boolean hasDirection = true;
    private AbilityAnimationType abilityAnimationType;

    private static final int OFFSET = 75;
    
    public enum AbilityAnimationType {DEFAULT}

    public AbilityAnimation(AbilityAnimationType animationType, Entity entity) {
        this.abilityAnimationType = animationType;
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

    public AbilityAnimationType getAbilityAnimationType() {
        return abilityAnimationType;
    }

    public void draw(Graphics g) {
        if (imageIcon == null)
            return;
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
