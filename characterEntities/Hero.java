package characterEntities;

import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.geom.Rectangle2D;
import SquarePG.GameState;

public abstract class Hero extends Entity {
	protected int posX, posY;
	protected Equipment equipment;
	protected Consumables consumables;
	protected GameState gameState = GameState.WORLDMAP;
	protected int numberEvolutions;
	protected ImageIcon heroAvatar;
	protected PlayerClass playerClass;
	
	public static final int PATH_RED = 0;
	public static final int PATH_YELLOW = 1;
	public static final int PATH_BLUE = 2;
	
	
	public Hero (String name, int maxHealth, int maxMana, int maxDamage, int minDamage, int posX, int posY){
		super(name, maxHealth, maxMana, maxDamage, minDamage);
		this.posX = posX;
		this.posY = posY;
		numberEvolutions = 0;
		equipment = new Equipment();
		consumables = new Consumables();
	}
	
	public abstract boolean evolve(int path);
	
	protected boolean evolutionIncrease(int path){
		switch(path){
		case PATH_RED: 
			maxHealth += 210;
			maxMana += 30;
			maxDamage += 30;
			minDamage += 30;
			break;
		case PATH_YELLOW:
			maxHealth += 150;
			maxMana += 50;
			maxDamage += 50;
			minDamage += 50;
			break;
		case PATH_BLUE: 
			maxHealth += 60;
			maxMana += 100;
			maxDamage += 70;
			maxDamage += 70;
			break;
		default:
			return false;
		}
		return true;
	}
	
	//Function checks for sufficient gold and not previously purchased, then purchases the equipment specified
	//Buying equipment will automatically be equipped
    public boolean buyEquipment (int equipNum, int numBought){
        if (equipment.getItem(equipNum).getBuyPrice() > gold || equipment.getItem(equipNum).getStock() > 0 || numBought != 1){
            return false;
        } else {
            equipment.getItem(equipNum).changeStock(numBought);
            gold -= equipment.getItem(equipNum).getBuyPrice();
            maxHealth += equipment.getItem(equipNum).getHealthIncrease();
            maxDamage += equipment.getItem(equipNum).getDamageIncrease();
            minDamage += equipment.getItem(equipNum).getDamageIncrease();
            maxMana += equipment.getItem(equipNum).getManaIncrease();
        }
        return true;
    }
    
    //Function that checks for having equipment, then selling equipment
    public boolean sellEquipment(int equipNum){
        if (equipment.getItem(equipNum).getStock() < 1){
            System.out.println("How do you sell something you don't have?");
            return false;
        } else {
            maxHealth -= equipment.getItem(equipNum).getHealthIncrease();
            maxDamage -= equipment.getItem(equipNum).getDamageIncrease();
            minDamage -= equipment.getItem(equipNum).getDamageIncrease();
            maxMana -= equipment.getItem(equipNum).getManaIncrease();
            equipment.getItem(equipNum).changeStock(-1);
            gold += equipment.getItem(equipNum).getSellPrice();
            return true;
        }

    }
    
    //Function that checks for sufficient funds and increases stock for consumables
    //Returns true for successful purchase
    public boolean buyConsumables(int itemNum, int numBought){
        if (numBought*consumables.getItem(itemNum).getBuyPrice() > gold){
            System.out.println("Sorry, debt does not exist in this game");
            return false;
        }
        else {
            consumables.getItem(itemNum).changeStock(numBought);
            gold -= consumables.getItem(itemNum).getBuyPrice()*numBought;
        }
        return true;
    }
    
    //Function that checks for sufficient stock, and decreases stock accordingly
    public boolean sellConsumables(int itemNum, int numSell){
        if (numSell > consumables.getItem(itemNum).getStock()){
            System.out.println("Can't sell something you don't have");
            return false;
        }
        else {
            consumables.getItem(itemNum).changeStock(-1*numSell);
            gold += consumables.getItem(itemNum).getSellPrice()*numSell;
        }
        return true;
    }
    
    public boolean gainGold (int income){
        if (gold + income >= 0){
            gold += income;
        }
        else {
            gold = 0;
            return false;
        }
        return true;
    }
    
    public Consumables getConsumables(){
        return consumables;
    }
    
    public Item getConsumables(int itemNum){
        return consumables.getItem(itemNum);
    }
    
    public Equipment getEquipment(){
        return equipment;
    }
    
    public Item getEquipment(int itemNum){
        return equipment.getItem(itemNum);
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public int getPosX() {
        return posX;
    }
    
    public int getPosY() {
        return posY;
    }
    
    //Renders to screen
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        switch (gameState) {
            case COMBAT:
                g2d.setPaint(Color.WHITE);
                g2d.fill(new Rectangle2D.Double(300, 40, 175, 45));
                g2d.setPaint(Color.GRAY);
                g2d.fill(new Rectangle2D.Double(310, 60, 155, 15));
                g2d.setPaint(Color.RED);
                g2d.fill(new Rectangle2D.Double(310, 60, (currentHealth*1.0/maxHealth*155), 15));
                g2d.setPaint(Color.BLACK);
                g2d.drawString(name, 310, 55);
                g2d.drawString((currentHealth+"/"+maxHealth+" HP"), 312, 72);
                g2d.drawImage(heroAvatar.getImage(), 350, 250, null);
                break;
            case WORLDMAP:
                g2d.drawImage(heroAvatar.getImage(), posX, posY, null);
                break;
        }
    }
}
