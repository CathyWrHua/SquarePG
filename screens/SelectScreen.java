package screens;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import SquarePG.*;
import characterEntities.PlayerClass;

public class SelectScreen extends Screen implements ActionListener {
	private JLabel selectionLabel = new JLabel("Let's get started!");
    private JLabel nameLabel = new JLabel("Square's Name:");
    private JTextField nameTextField = new JTextField(15);
    private JToggleButton redButton = new JToggleButton("Red");
    private JToggleButton yellowButton = new JToggleButton("Yellow");
    private JToggleButton blueButton = new JToggleButton("Blue");
    private JButton startButton = new JButton("Start!");
	
	public SelectScreen(){
		super();
		
		redButton.addActionListener(this);
        yellowButton.addActionListener(this);
        blueButton.addActionListener(this);
        startButton.addActionListener(this);
        nameTextField.addActionListener(this);
		
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
        c.gridy = 2;
        c.gridx = 0;
        c.ipadx = 5;
        add(nameLabel, c);
        c.gridx = 1;
        c.gridwidth = 2;
        add(nameTextField, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        add(startButton, c);
        startButton.setEnabled(false);
        nameTextField.setEditable(false);
        nameTextField.setMaximumSize(nameTextField.getPreferredSize());
	}

	@Override
	public void update() {
		validate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//TODO: (cathy.hua) fix form validation logic
		if (event.getSource() == redButton) {
			SquarePG.heroClass= PlayerClass.RED;
            nameTextField.setEditable(true);
            yellowButton.setSelected(false);
            blueButton.setSelected(false);
        } 
		if (event.getSource() == yellowButton) {
        	SquarePG.heroClass = PlayerClass.YELLOW;
            nameTextField.setEditable(true);
            redButton.setSelected(false);
            blueButton.setSelected(false);
        }
		if (event.getSource() == blueButton) {
        	SquarePG.heroClass = PlayerClass.BLUE;
            nameTextField.setEditable(true);
            redButton.setSelected(false);
            yellowButton.setSelected(false);
        } 
		if (event.getSource() == nameTextField && nameTextField.getText().length() > 0) {
			//TODO: (cathy.hua) add document listener to listen for text changed, setenable() that way
            SquarePG.playerName = event.getActionCommand(); 
            startButton.setEnabled(true);
        } 
		if (event.getSource() == startButton) {
            SquarePG.screenState = ScreenState.GAME;
        }
	}
	
}
