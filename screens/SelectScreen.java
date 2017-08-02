package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import SquarePG.*;
import characterEntities.Hero;

public class SelectScreen extends Screen implements ActionListener {
	private JLabel selectionLabel = new JLabel("Let's get started!");
	private JToggleButton redButton = new JToggleButton("Red");
	private JToggleButton yellowButton = new JToggleButton("Yellow");
	private JToggleButton blueButton = new JToggleButton("Blue");
	private JButton startButton = new JButton("Start!");
	
	public SelectScreen() {
		super();
		
		redButton.addActionListener(this);
		yellowButton.addActionListener(this);
		blueButton.addActionListener(this);
		startButton.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 0, 0, 0);
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.gridx = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(selectionLabel, c);
		c.gridwidth = 1;
		c.gridy = 1;
		c.gridx = 0;
		c.ipadx = 23;
		add(redButton, c);
		c.gridx = 1;
		c.ipadx = 25;
		add(yellowButton,c);
		c.gridx = 2;
		c.ipadx = 40;
		add(blueButton,c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		add(startButton, c);
		startButton.setEnabled(false);
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		validate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//TODO: (cathy.hua) fix form validation logic
		if (event.getSource() == redButton) {
			SquarePG.heroClass = Hero.PlayerClass.RED;
			yellowButton.setSelected(false);
			blueButton.setSelected(false);
			startButton.setEnabled(true);
		}
		if (event.getSource() == yellowButton) {
			SquarePG.heroClass = Hero.PlayerClass.YELLOW;
			redButton.setSelected(false);
			blueButton.setSelected(false);
			startButton.setEnabled(true);
		}
		if (event.getSource() == blueButton) {
			SquarePG.heroClass = Hero.PlayerClass.BLUE;
			redButton.setSelected(false);
			yellowButton.setSelected(false);
			startButton.setEnabled(true);
		}
		if (event.getSource() == startButton) {
			SquarePG.screenState = ScreenState.GAME;
		}
	}
}
