package characterEntities;

import javax.swing.*;
import java.awt.*;

public class DamageMarker {
    private int currentLifetime = LIFETIME;
    private int damage;
    private int posX, posY;
    private float speed = INITIAL_SPEED;
    private boolean done;

    private static final int LIFETIME = 30;
    private static final int OFFSET_X = 21;
    private static final int OFFSET_Y = 15;
    private static final float SLOWING_MODIFIER = 0.95f;
    private static final float INITIAL_SPEED = 3.0f;

    public DamageMarker(int damage, int posX, int posY) {
        this.damage = damage;
        this.posX = posX;
        this.posY = posY;
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public void update() {
        posY -= Math.round(speed);
        speed *= SLOWING_MODIFIER;
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
        g2d.setPaint(Color.RED);
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        g2d.drawString(Integer.toString(damage), posX+OFFSET_X, posY+OFFSET_Y);
    }
}
