package gui;

import characterEntities.Entity;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HealthBar implements Drawable {
	private ImageIcon border;
	private Entity entity;

	private static final int BAR_WIDTH = 95;
	private static final int BAR_HEIGHT = 17;
	private int OFFSET_X = -46;
	private int OFFSET_Y = 10;
	private int BORDER_OFFSET_X = -6;
	private int BORDER_OFFSET_Y = -5;

	public HealthBar(Entity entity) {
		this.entity = entity;
		this.border = new ImageIcon("src/assets/gui/healthBar.png");
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(Color.BLACK);

		int healthBarX = entity.getPosX()+OFFSET_X+entity.getImageIcon().getIconWidth()/2;
		int healthBarY = entity.getPosY()+OFFSET_Y+entity.getImageIcon().getIconHeight();

		g2d.fill(new Rectangle2D.Double(healthBarX, healthBarY, BAR_WIDTH, BAR_HEIGHT));
		g2d.setPaint(Color.GREEN);
		g2d.fill(new Rectangle2D.Double(healthBarX, healthBarY,
				(int)((1.0*entity.getCurrentHealth()/entity.getMaxHealth())*BAR_WIDTH), BAR_HEIGHT));
		g2d.drawImage(border.getImage(), healthBarX+BORDER_OFFSET_X, healthBarY+BORDER_OFFSET_Y, null);
	}
}
