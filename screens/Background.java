package screens;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.ImageIcon;

public class Background {
	private ImageIcon background;
	
	public Background() {
		background = new ImageIcon("src/assets/map1.png");
	}
	
	public Background(String imageName) {
		background = new ImageIcon("src/assets/" + imageName);
	}
	
	public void setBackground(String imageName) {
		background = new ImageIcon("src/assets/" + imageName);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(background.getImage(), 0, 0, null);
	}
}
