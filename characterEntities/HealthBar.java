package characterEntities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HealthBar {
    private final int BAR_WIDTH = 120;
    private final int BAR_HEIGHT = 30;

    private int posX, posY;
    private int maxHealth, currentHealth;

    public HealthBar(int posX, int posY, int maxHealth) {
        this.posX = posX;
        this.posY = posY;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int inflict(int value) {
        currentHealth -= value;
        if (currentHealth < 0) {
            currentHealth = 0;
        } else if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
        return currentHealth;
    }

    public int heal(int value) {
        return inflict(-value);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.fill(new Rectangle2D.Double(posX, posY, BAR_WIDTH, BAR_HEIGHT));
    }
}
