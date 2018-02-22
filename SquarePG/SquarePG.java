package SquarePG;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.*;

import gameLogic.GameMode;
import screens.*;
import characterEntities.*;

public class SquarePG extends JFrame {
	public static ScreenState screenState = ScreenState.WAIT;
	public static GameMode gameMode = GameMode.PLAY;
	public static Hero.PlayerClass heroClass;
	private Screen currentScreen; 
	private Stack<Screen> screenStack = new Stack<>();
	public static final int FPS = 60;
	
	public SquarePG() {
		super("SquarePG");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1000, 960);
		setResizable(false);
		setVisible(true);
		setLayout(new BorderLayout());
		try {
			this.setIconImage(ImageIO.read(new File("src/assets/gui/icon.png")));
		}
		catch (IOException exc) {
			exc.printStackTrace();
		}
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
						if (screenState == ScreenState.HOME) {
							currentScreen = screenStack.pop();
						} else {
							screenStack.push(currentScreen);
							switch(screenState) {
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
								currentScreen = new GameScreen(heroClass);
								break;
							default:
								currentScreen = screenStack.pop();
							}
						}
						add(currentScreen);
						currentScreen.init();
						screenState = ScreenState.WAIT;
					}

					validate();
					repaint();

					// Delay for each frame
					time = (1000/FPS) - (System.currentTimeMillis()-time);

					if (time > 0) {
						try {
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
