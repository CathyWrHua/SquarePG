package characterEntities;

import javax.swing.*;
import java.awt.*;

public class DamageMarker {
    private int currentLifetime = LIFETIME;
    private int posX, posY;
    private float speed = INITIAL_SPEED;
    private boolean done;
    private ImageIcon imageIcon;

    private static final int LIFETIME = 30;
    private static final int OFFSET_X = 0;
    private static final int OFFSET_Y = 0;
    private static final float SLOWING_MODIFIER = 0.95f;
    private static final float INITIAL_SPEED = 3.0f;

    public DamageMarker(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public void update() {
        posY -= Math.round(speed);
        currentLifetime--;
        if (currentLifetime <= 0) {
            done = true;
        }
    }

    public void draw(Graphics g) {
        if (done) {
            return;
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(imageIcon.getImage(), posX+OFFSET_X, posY+OFFSET_Y, null);
    }
}
