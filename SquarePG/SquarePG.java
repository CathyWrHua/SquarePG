package SquarePG;

import java.awt.BorderLayout;
import java.util.EmptyStackException;
import java.util.Stack;
import javax.swing.JFrame;

import screens.*;
import characterEntities.*;

public class SquarePG extends JFrame {
	public static ScreenState screenState = ScreenState.WAIT;
	public static String playerName;
	public static PlayerClass heroClass;
	private Screen currentScreen; 
	private Stack<Screen> screenStack = new Stack();
	private final int FPS = 60;
	
	public SquarePG() {
		super("SquarePG");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
		setResizable(false);
		setVisible(true);
		setLayout(new BorderLayout());
		currentScreen = new HomeScreen();
		add(currentScreen);
	}
	
	private void run() {
		Thread loop = new Thread() {
			public void run() {
				while(true) {
		            long time = System.currentTimeMillis();
		            
		            currentScreen.update();
		            
		            //Screen switching function: screenState is in Wait, unless a change screen button is pressed (eg. back, enter buttons)
		            if (screenState != ScreenState.WAIT) {
		            	remove(currentScreen);
		            	if (screenState == ScreenState.HOME){
		            		currentScreen = screenStack.pop();
		            	} else {
		            		screenStack.push(currentScreen);
		            		switch(screenState){
		            		case ABOUT:
		            			currentScreen = new AboutScreen();
		            			break;
		            		case CHARACTER_SELECT:
		            			currentScreen = new SelectScreen();
		            			break;
		            		case OPTIONS:
		            			currentScreen = new OptionsScreen();
		            			break;
		            		case GAME:
		            			currentScreen = new GameScreen(playerName, heroClass);
		            			break;
		            		default:
		            			currentScreen = screenStack.pop();
		            		}
		            	}
		            	add(currentScreen);
		            	screenState = ScreenState.WAIT;
		            }
		            
		            validate();
		            repaint();
		            
		            // Delay for each frame
		            time = (1000/FPS) - (System.currentTimeMillis()-time);
		            
		            if (time > 0){
		                try{
		                    Thread.sleep(time);
		                }
		                catch (Exception e) {}
		            }
		        }
			}
		};
		loop.start();
	}
	
	public static void main(String[] args) {
		SquarePG game = new SquarePG();
		game.run();
	}

}
