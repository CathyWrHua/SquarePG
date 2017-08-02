package gui;

import animation.AbilityAnimation;
import characterEntities.Hero;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class AbilityBar extends Drawable {
	private ImageIcon iconBar;
	private ArrayList<ImageIcon> abilityIcons;
	private ArrayList<AbilityAnimation> abilities;

	private static final int NUM_ABILITIES = 4;
	private static final int ICON_GAP = 90;
	private static final int ICON_LENGTH = 85;
	private static final int ICON_BAR_OFFSET_X = 313;
	private static final int ICON_BAR_OFFSET_Y = 870;
	private static final int ICONS_OFFSET_X = 323;
	private static final int ICONS_OFFSET_Y = 880;

	public AbilityBar(Hero player) {
		iconBar = new ImageIcon("src/assets/gui/iconBar.png");
		abilityIcons = new ArrayList<>(NUM_ABILITIES);
		abilities = new ArrayList<>(NUM_ABILITIES);
		drawableType = DrawableType.GUI;
		updateAbilityIcons(player);
	}

	public void updateAbilityIcons(Hero player) {
		String filepath;
		ArrayList<AbilityAnimation> abilityAnimations = player.getAbilityAnimations();

		for (int i = 0; i < NUM_ABILITIES; i++) {
			if (i+1 < abilityAnimations.size()) {
				abilities.add(i, abilityAnimations.get(i+1));
			} else {
				abilities.add(i, null);
			}
		}
		for (int i = 0; i < NUM_ABILITIES; i++) {
			filepath = "src/assets/gui/";
			if (abilities.get(i) != null) {
				filepath += abilities.get(i).getAnimationName() + "Icon.png";
			} else {
				filepath += "unknownIcon.png";
			}
			abilityIcons.add(i, new ImageIcon(filepath));
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g2d.setPaint(new Color(255, 255, 255, 127));
		g2d.drawImage(iconBar.getImage(), ICON_BAR_OFFSET_X, ICON_BAR_OFFSET_Y, null);
		for (int i = 0; i < NUM_ABILITIES; i++) {
			g2d.drawImage(abilityIcons.get(i).getImage(), ICONS_OFFSET_X+ICON_GAP*i, ICONS_OFFSET_Y, null);
			if (abilities.get(i) != null && abilities.get(i).getCooldownCounter() > 0) {
				double percentageCooldown = abilities.get(i).getCooldownCounter()/(1.0*abilities.get(i).getCooldownTotal());
				int blurHeight = (int)Math.ceil(ICON_LENGTH*percentageCooldown);
				g2d.fill(new Rectangle2D.Double(ICONS_OFFSET_X, ICONS_OFFSET_Y+ICON_LENGTH-blurHeight, ICON_LENGTH, blurHeight));
			}
		}
	}
}
