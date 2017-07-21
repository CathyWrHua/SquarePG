package characterEntities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HealthBar {
    private static final int BAR_WIDTH = 95;
    private static final int BAR_HEIGHT = 15;
    private static final int OFFSET_Y = 85;
    private static final int OFFSET_X = -8;
    private static final int BORDER_OFFSET_Y = 78;
    private static final int BORDER_OFFSET_X = -15;

    private ImageIcon border = new ImageIcon("src/assets/gui/healthbar.png");
    private Entity entity;
    private int maxHealth, currentHealth;

    public HealthBar(Entity entity, int maxHealth) {
        this.entity = entity;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(Color.GREEN);
        g2d.fill(new Rectangle2D.Double(entity.getPosX()+OFFSET_X, entity.getPosY()+OFFSET_Y,
                (int)((1.0*entity.getCurrentHealth()/entity.getMaxHealth())*BAR_WIDTH), BAR_HEIGHT));
        g2d.drawImage(border.getImage(), entity.getPosX()+BORDER_OFFSET_X, entity.getPosY()+BORDER_OFFSET_Y, null);
    }
}
