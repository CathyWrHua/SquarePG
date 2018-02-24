package screens;

import com.sun.istack.internal.NotNull;
import gameLogic.GameMap;
import gameLogic.GameMapPresets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MapSelectScreen extends Screen{
	private int level = 1;
	private int map = -1;
	private int unlockedLevel = -1;
	private int unlockedMap = -1;
	private ArrayList<JButton> buttons;

	private JPanel mapPanel, levelPanel;

	public MapSelectScreen() {
		buttons = new ArrayList<>();

		// Buttons for maps within a level
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridLayout(4, 3, 10, 10));
		for (int i = 0; i < GameMap.MAPS_PER_LEVEL; i++) {
			final int buttonNumber = i+1;
			JButton button = new JButton(Integer.toString(i+1));
			button.addActionListener(e -> setMap(buttonNumber));
			button.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
			mapPanel.add(button).setLocation((int) i/3, i%3);
			buttons.add(button);
		}
		add(mapPanel);
	}

	public void setUnlockedLevel(int unlockedLevel) {
		this.unlockedLevel = unlockedLevel;
	}

	public void setUnlockedMap(int unlockedMap) {
		this.unlockedMap = unlockedMap;
	}

	private void setLevel(int level) {
		if (level <= unlockedLevel) {
			this.level = level;
		}
	}

	private void setMap(int map) {
		System.out.println(map);
		if (map <= unlockedMap) {
			this.map = map;
		}
	}

	public int getLevel() {
		return level;
	}

	public int getMap() {
		return map;
	}

	public void resetSelection() {
		map = -1;
		level = -1;
	}

	@Override
	public void init() {
		//TODO:initialize other levels

		for (int i = 0; i < GameMap.MAPS_PER_LEVEL; i++) {
			boolean enabled = i < unlockedMap;
			buttons.get(i).setEnabled(enabled);
		}
	}

	@Override
	public void update() {

		for (int i = 0; i < GameMap.MAPS_PER_LEVEL; i++) {
			boolean enabled = i < unlockedMap;
			buttons.get(i).setEnabled(enabled);
		}
	}
}
