package gui;

import animation.AbilityAnimation;
import characterEntities.Hero;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AbilityBar implements Drawable {
    private ArrayList<AbilityAnimation> abilities;
    private ImageIcon iconBar;
    private ArrayList<ImageIcon> abilityIcons;

    private static final int NUM_ABILITIES = 4;

    public AbilityBar(Hero player) {
        iconBar = new ImageIcon();
        abilities = new ArrayList<>(NUM_ABILITIES);
        abilityIcons = new ArrayList<>(NUM_ABILITIES);

        ArrayList<AbilityAnimation> playerAbilities = player.getAbilityAnimations();
        for (int i = 0; i < NUM_ABILITIES; i++) {
            if (playerAbilities.get(i+1) != null) {
                abilities.add(i, playerAbilities.get(i+1));
                abilityIcons.add(i, new ImageIcon());
            } else {
                abilityIcons.add(i, new ImageIcon());
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
    }
}
