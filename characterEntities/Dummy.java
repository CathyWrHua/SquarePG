package characterEntities;

import gui.DamageMarker;

import java.awt.*;

public class Dummy extends Entity {
    private int stunCounter = STUN_TIME;

    private static final int STUN_TIME = 15;

    public Dummy(int posX, int posY, boolean facingEast) {
        super(null, 1, 0, 0, posX, posY, 0);
        setImageIcon("src/assets/enemies/dummyNeutral.png");
        setFacingEast(facingEast);
        setEntityType(EntityType.DUMMY);
    }

    @Override
    public DamageMarker inflict(int damageTaken, Entity attacker) {
        setAttackerFacingEast(attacker.getFacingEast());
        setEntityState(EntityState.DAMAGED);
        immuneTo.put(attacker, true);
        return (new DamageMarker(damageTaken, posX, posY));
    }

    @Override
    public void setEntityState(EntityState entityState) {
        super.setEntityState(entityState);
        String filepath = "src/assets/enemies/dummy";
        switch (entityState) {
            case NEUTRAL:
                filepath += "Neutral";
                break;
            case DAMAGED:
                filepath += "Damaged";
                if (!attackerFacingEast && !facingEast || attackerFacingEast && facingEast) {
                    filepath += "Right";
                } else {
                    filepath += "Left";
                }
                break;
            case ATTACKING:
            case DEAD:
            default:
                break;
        }
        filepath += ".png";
        this.setImageIcon(filepath);
    }

    @Override
    public void update() {
        if (entityState == EntityState.DAMAGED && stunCounter > 0) {
        stunCounter--;
        } else if (stunCounter <= 0) {
            setEntityState(EntityState.NEUTRAL);
            stunCounter = STUN_TIME;
        }
    }

    @Override
    public Rectangle getEntitySize() {
        return new Rectangle(posX, posY, getImageIcon().getIconWidth(), getImageIcon().getIconHeight());
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Image image = getImageIcon().getImage();
        int x = posX;
        int width = image.getWidth(null);

        if (!facingEast) {
            x += width;
            width = -width;
        }

        g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
    }
}
