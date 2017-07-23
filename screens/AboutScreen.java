package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import SquarePG.*;

public class AboutScreen extends Screen implements ActionListener {

	private JLabel aboutLabel = new JLabel("<html><center>ABOUT<br><br>Authors:<br>Tiger Dong, Cathy Hua<br><br>Last Revised:<br>DATE HERE<br><br>Compiler:<br>JDK 1.8.0_101<br><br>Version:<br>Java SE 8<br> <br></html>");
    private JButton back;
	
	public AboutScreen() {
		super();
		back = new JButton("Back");
		back.addActionListener(this);
		
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 0, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;        
        c.gridx = 0;
        c.gridy = 0;
        add(aboutLabel, c);
        c.gridy = 2;
        add(back, c);
		
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
		if (event.getSource() == back) {
			SquarePG.screenState = ScreenState.HOME;
		}
	}
}
