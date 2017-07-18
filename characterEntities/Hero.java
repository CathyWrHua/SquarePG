package characterEntities;

import java.awt.*;
import javax.swing.ImageIcon;
import SquarePG.GameState;

public abstract class Hero extends Entity {
	private GameState gameState = GameState.WORLDMAP;
	private int numberEvolutions;
	private PlayerClass playerClass;
	
	
	private static final int PATH_RED = 0;
	private static final int PATH_YELLOW = 1;
	private static final int PATH_BLUE = 2;
	
	
	public Hero(String name, int maxHealth, int maxDamage, int minDamage, int posX, int posY){
		super(name, maxHealth, maxDamage, minDamage, posX, posY);
		numberEvolutions = 0;
	}
	
	public abstract boolean evolve(int path);
	
	private boolean evolutionIncrease(int path){
//		switch(path){
//		case PATH_RED:
//			maxHealth += 210;
//			maxDamage += 30;
//			minDamage += 30;
//			break;
//		case PATH_YELLOW:
//			maxHealth += 150;
//			maxDamage += 50;
//			minDamage += 50;
//			break;
//		case PATH_BLUE:
//			maxHealth += 60;
//			maxDamage += 70;
//			maxDamage += 70;
//			break;
//		default:
//			return false;
//		}
		return true;
	}
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}