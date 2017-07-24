package animation;

import GameMaps.MapCollisionDetection;
import characterEntities.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectileAnimation extends Animation {
    public enum ProjectileAnimationType {
        YELLOW_FIRST(100, 30);
        private int offsetX, offsetY;

        ProjectileAnimationType(int offsetX, int offsetY) {
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
    private int velocityX, velocityY;
    private boolean facingEast;
    private ProjectileAnimationType animationType;
    private EffectType effectType;
    private ArrayList<Entity> targets;
    private MapCollisionDetection collisionMap;

    public ProjectileAnimation(ProjectileAnimationType animationType, Entity entity) {
        this.effectType = EffectType.ENTITY_EFFECT;
        this.targets = entity.getTargets();
        this.animationType = animationType;
        this.posX = entity.getPosX();
        this.posY = entity.getPosY();
        this.facingEast = entity.getFacingEast();
        this.collisionMap = entity.getMapCollisionDetection();
        switch (animationType) {
            case YELLOW_FIRST:
                setValues("arrow", 2);
                velocityX = 5;
                velocityY = 0;
                break;
            default:
                break;
        }
        velocityX *= facingEast ? 1 : -1;
    }

    private void setValues(String animationName, int totalFrames) {
        this.animationName = animationName;
        this.totalFrames = totalFrames;
    }

    private boolean isHit() {
        boolean hit;
        int newX = posX + velocityX;
        int newY = posY + velocityY;
        Rectangle projectileSize = new Rectangle(posX, posY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        Point newPoint = collisionMap.determineMotion(newX, newY, projectileSize, targets);
        hit = (newX != newPoint.x || newY != newPoint.y);
        posX = newX;
        posY = newY;
        return false;
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public void update () {
        int currentFrame = counter++/ANIMATION_SPEED;
        if (currentFrame >= totalFrames) {
            resetCounter();
            currentFrame = 0;
        }
        String filePath = "src/assets/animations/" + animationName;
        filePath += currentFrame + ".png";
        this.imageIcon = new ImageIcon(filePath);
        done = isHit();
    }

    public void draw(Graphics g) {
        if (imageIcon == null) return;
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();
        int x = posX;
        int width = image.getWidth(null);
        int offsetX = facingEast ? animationType.getOffsetX() : -animationType.getOffsetX();
        int offsetY = animationType.getOffsetY();

        if (!facingEast) {
            x += width;
            width = -width;
        }

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, x+offsetX, posY+offsetY, width, image.getHeight(null), null);
        }
    }
}
