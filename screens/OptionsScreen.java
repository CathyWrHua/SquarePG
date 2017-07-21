package screens;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import SquarePG.*;

public class OptionsScreen extends Screen implements ActionListener{
	
	private JButton back;
	private JLabel placeHolder = new JLabel ("Options will go here");
	
	public OptionsScreen () {
		super();
		
		back = new JButton ("Back");
		back.addActionListener(this);
		
		add(placeHolder);
		add(back);
		
	}
	
	@Override
	public void update() {
		validate();
		repaint();
	}
	
	@Override
	public void actionPerformed (ActionEvent event) {
		if (event.getSource()== back) {
			SquarePG.screenState = ScreenState.HOME;
		}
	}
	
	
	
}
