package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import SquarePG.*;

public class HomeScreen extends Screen implements ActionListener{

	private JButton play, options, about, back;
	
	public HomeScreen() {
		super();
		play = new JButton("Play");
		options = new JButton("Options");
		about = new JButton("About");
		back = new JButton("Back");

		play.addActionListener(this);
		options.addActionListener(this);
		about.addActionListener(this);
		back.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(play, c);
		c.gridy = 1;
		add(options, c);
		c.gridy = 2;
		add(about, c);

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
		if (event.getSource() == play) {
			SquarePG.screenState = ScreenState.CHARACTER_SELECT;
		}
		if (event.getSource() == options) {
			SquarePG.screenState = ScreenState.OPTIONS;
		}
		if (event.getSource() == about) {
			SquarePG.screenState = ScreenState.ABOUT;
		}
		
	}

}
