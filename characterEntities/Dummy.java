package characterEntities;

import screens.GameScreen;

import java.awt.*;

public class Dummy extends Entity {
    private int stunCounter = STUN_TIME;

    private static final int STUN_TIME = 15;

    public Dummy(GameScreen game, int posX, int posY, boolean facingEast) {
        super(game, 1, 0, 0, posX, posY, 0);
        setImageIcon("src/assets/enemies/dummyNeutral.png");
        setFacingEast(facingEast);
    }

    @Override
    public void inflict(int damageTaken, boolean attackerFacingEast) {
        getGame().createDamageMarker(damageTaken, getPosX(), getPosY());
        setAttackerFacingEast(attackerFacingEast);
        setEntityState(EntityState.DAMAGED);
    }

    @Override
    public void setEntityState(EntityState entityState) {
        super.setEntityState(entityState);
        String filepath = "src/assets/enemies/dummy";
        switch (this.getEntityState()) {
            case DEFAULT:
                filepath += "Neutral";
                break;
            case DAMAGED:
                filepath += "Damaged";
                if (!getAttackerFacingEast() && !getFacingEast() || getAttackerFacingEast() && getFacingEast()) {
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
        if (getEntityState() == EntityState.DAMAGED && stunCounter > 0) {
        stunCounter--;
        } else if (stunCounter <= 0) {
            setEntityState(EntityState.DEFAULT);
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
        int x = getPosX();
        int width = image.getWidth(null);

        if (!getFacingEast()) {
            x += width;
            width = -width;
        }

        g2d.drawImage(image, x, getPosY(), width, image.getHeight(null), null);
    }
}
