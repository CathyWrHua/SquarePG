package animation;

import characterEntities.Entity;

import java.awt.*;

public class AbilityAnimation extends Animation {
    public enum AbilityAnimationType {
        HERO_DEFAULT(75, 0), RED_FIRST(-75, -75), YELLOW_FIRST(75, 0);
        private int offsetX, offsetY;

        AbilityAnimationType(int offsetX, int offsetY) {
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
    private Entity entity;
    private boolean hasDirection;
    private Entity.Ability ability;
    private AbilityAnimationType animationType;
    private EffectType effectType;

    public AbilityAnimation(AbilityAnimationType animationType, Entity entity) {
        this.effectType = EffectType.ENTITY_EFFECT;
        this.entity = entity;
        this.animationType = animationType;
        switch(animationType) {
            case HERO_DEFAULT:
            setValues("default", 4, Entity.Ability.DEFAULT, true, 1);
                break;
            case RED_FIRST:
                setValues("redFirst", 3, Entity.Ability.FIRST, false, 3);
                break;
            case YELLOW_FIRST:
                setValues("yellowFirst", 3, Entity.Ability.FIRST, true, 1);
                break;
            default:
                break;
        }
    }

    private void setValues(String animationName, int totalFrames, Entity.Ability ability, boolean hasDirection, int numLoops) {
        this.animationName = animationName;
        this.totalFrames = totalFrames;
        this.ability = ability;
        this.hasDirection = hasDirection;
        setNumLoops(numLoops);
    }

    public void resetDone() {
        done = false;
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    public Entity.Ability getAbility() {
        return ability;
    }

    public void draw(Graphics g) {
        if (imageIcon == null) return;
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();
        int x = entity.getPosX();
        int width = image.getWidth(null);
        int offsetX = ((entity.getFacingEast() || !hasDirection) ? animationType.getOffsetX() : -animationType.getOffsetX());
        int offsetY = animationType.getOffsetY();

        if (hasDirection && !entity.getFacingEast()) {
            x += width;
            width = -width;
        }

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, x+offsetX, entity.getPosY()+offsetY, width, image.getHeight(null), null);
        }
    }
}
