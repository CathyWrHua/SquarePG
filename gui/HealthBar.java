package gui;

import characterEntities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HealthBar {
    private ImageIcon border;
    private Entity entity;

    private static final int BAR_WIDTH = 95;
    private static final int BAR_HEIGHT = 17;
    private static final int OFFSET_Y = 85;
    private static final int OFFSET_X = -9;
    private static final int BORDER_OFFSET_Y = 80;
    private static final int BORDER_OFFSET_X = -15;

    public HealthBar(Entity entity) {
        this.entity = entity;
        this.border = new ImageIcon("src/assets/gui/healthbar.png");
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(Color.BLACK);
        g2d.fill(new Rectangle2D.Double(entity.getPosX()+OFFSET_X, entity.getPosY()+OFFSET_Y, BAR_WIDTH, BAR_HEIGHT));
        g2d.setPaint(Color.GREEN);
        g2d.fill(new Rectangle2D.Double(entity.getPosX()+OFFSET_X, entity.getPosY()+OFFSET_Y,
                (int)((1.0*entity.getCurrentHealth()/entity.getMaxHealth())*BAR_WIDTH), BAR_HEIGHT));
        g2d.drawImage(border.getImage(), entity.getPosX()+BORDER_OFFSET_X, entity.getPosY()+BORDER_OFFSET_Y, null);
    }
}
