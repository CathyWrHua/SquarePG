package animation;

import GameMaps.MapCollisionDetection;
import characterEntities.Entity;
import characterEntities.HitDetectionHelper;
import gui.DamageMarker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectileAnimation extends Animation {
    public enum ProjectileAnimationType {
        YELLOW_FIRST(75, 30);
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
    private EffectType effectType;
    private MapCollisionDetection mapCollision;
    private ArrayList<Entity> targets;
    private int damage;
    private ArrayList<DamageMarker> targetMarkers;

    public ProjectileAnimation(ProjectileAnimationType animationType, MapCollisionDetection collisionMap, Entity entity) {
        this.effectType = EffectType.PROJECTILE_EFFECT;
        this.mapCollision = collisionMap;
        this.facingEast = entity.getFacingEast();
        this.targets = entity.getTargets();
        this.posX = entity.getPosX() + animationType.getOffsetX() * (entity.getFacingEast() ? 1 : -1);
        this.posY = entity.getPosY() + animationType.getOffsetY();
        this.damage = entity.getDamage();
        targetMarkers = new ArrayList<>();
        switch (animationType) {
            case YELLOW_FIRST:
                setValues("arrow", 2);
                velocityX = 10;
				setNumLoops(500/velocityX);
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

    private void dealDamage() {
        Entity targetHit = null;
        Rectangle projectileSize = new Rectangle(posX, posY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        DamageMarker marker;

        for (Entity target : targets) {
            if (HitDetectionHelper.detectHit(projectileSize, target.getEntitySize()) && (targetHit == null ||
                    (Math.abs(target.getPosX()-posX) < Math.abs(targetHit.getPosX()-posX)))) {
                targetHit = target;
            }
        }
        if (targetHit == null) return;
        marker = targetHit.inflict(damage, posX < targetHit.getPosX());
        if (marker != null) {
            targetMarkers.add(marker);
        }
    }

    public ArrayList<DamageMarker> getTargetMarkers () {
        return targetMarkers;
    }

    public void clearTargetMarkers() {
        targetMarkers.clear();
    }

    private boolean isCollide() {
        boolean collision;
        int newX = posX + velocityX;
        int newY = posY + velocityY;
        Rectangle projectileSize = new Rectangle(posX, posY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        Point newPoint = mapCollision.determineMotion(newX, newY, projectileSize, targets);

        collision = (newX != newPoint.x || newY != newPoint.y);
        posX = newX;
        posY = newY;
        if (collision) dealDamage();
        return collision;
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
        done = isCollide();
    }

    public void draw(Graphics g) {
        if (imageIcon == null) return;
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();
        int x = posX;
        int width = image.getWidth(null);

        if (!facingEast) {
            x += width;
            width = -width;
        }

        if (counter/ANIMATION_SPEED < totalFrames) {
            g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
        }
    }
}
