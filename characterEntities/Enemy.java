package characterEntities;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;


public class Enemy extends Entity {
	
	private ImageIcon enemyAvatar;
	
	public Enemy (String name, int maxHealth, int maxMana, int maxDamage, int minDamage){
		this.name = name;
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
		this.maxMana = maxMana;
		currentMana = maxMana;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		gold = random.nextInt(150);
		enemyAvatar = new ImageIcon(this.getClass().getResource("enemy.png"));
	}
	
	public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle2D.Double(25, 40, 175, 45));
        g2d.setPaint(Color.GRAY);
        g2d.fill(new Rectangle2D.Double(35, 60, 155, 15));
        g2d.setPaint(Color.RED);
        g2d.fill(new Rectangle2D.Double(35, 60, (currentHealth*1.0/maxHealth*155), 15));
        g2d.setPaint(Color.BLACK);
        g2d.drawString(name, 35, 55);
        g2d.drawString((currentHealth+"/"+maxHealth+" HP"), 37, 72);
        g2d.drawImage(enemyAvatar.getImage(), 100, 250, null);
    }
	

}
