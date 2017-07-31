package characterEntities;

import javax.swing.*;
import java.awt.*;

public class CharacterProfile {
	public enum  Path {
		RED(0),
		YELLOW(1),
		BLUE(2);

		private int value;

		Path(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}

	}

	private Hero player;
	private Path[] path;
	public static final int MAX_EVOLUTION = 3;

	private ImageIcon baokground;
	private ImageIcon redFilled;
	private ImageIcon redBorder;
	private ImageIcon blueFilled;
	private ImageIcon blueBorder;
	private ImageIcon yellowFilled;
	private ImageIcon yellowBorder;

	private boolean[][] isSelected;

	public CharacterProfile(Hero player) {
		this.player = player;
		this.path = player.path;
		isSelected = new boolean[3][3];

		calculateIsSelected();
		createImageAssets();
	}

	public void update() {
		calculateIsSelected();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g2d.drawImage(baokground.getImage(), 0, 0, null);
		for (int h = 0; h < MAX_EVOLUTION; h++) {
			for (int w = 0; w < MAX_EVOLUTION; w++) {
				if (h == 0) {
					if (isSelected[w][h]) {
						g2d.drawImage(redFilled.getImage(), 100*(w + 1) + 200*w, 100, null);
					} else {
						g2d.drawImage(redBorder.getImage(), 100*(w + 1) + 200*w, 100, null);
					}
				} else if (h == 1) {
					if (isSelected[w][h]) {
						g2d.drawImage(yellowFilled.getImage(), 100*(w + 1) + 200*w, 400, null);
					} else {
						g2d.drawImage(yellowBorder.getImage(), 100*(w + 1) + 200*w, 400, null);
					}
				} else if (h == 2) {
					if (isSelected[w][h]) {
						g2d.drawImage(blueFilled.getImage(), 100*(w + 1) + 200*w, 700, null);
					} else {
						g2d.drawImage(blueBorder.getImage(), 100*(w + 1) + 200*w, 700, null);
					}
				}
			}
		}
	}

	//private helper functions
	private void calculateIsSelected() {
		for (int i = 0; i < MAX_EVOLUTION; i++) {
			if (path[i] != null) {
				isSelected[i][path[i].getValue()] = true;
			}
		}
	}

	public void attemptEvolution(int index, Path path) {

	}

	private void createImageAssets() {
		baokground = new ImageIcon("src/assets/profile/layout.png");
		redFilled = new ImageIcon("src/assets/profile/redFilled.png");
		redBorder = new ImageIcon("src/assets/profile/redBorder.png");
		blueFilled = new ImageIcon("src/assets/profile/blueFilled.png");
		blueBorder = new ImageIcon("src/assets/profile/blueBorder.png");
		yellowFilled = new ImageIcon("src/assets/profile/yellowFilled.png");
		yellowBorder = new ImageIcon("src/assets/profile/yellowBorder.png");
	}
}
