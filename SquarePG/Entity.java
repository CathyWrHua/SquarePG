package SquarePG;

import java.awt.Graphics;
import java.util.Random;

public abstract class Entity {
	protected String name;
	protected int maxHealth, currentHealth;
	protected int maxMana, currentMana;
	protected int maxDamage, minDamage;
	protected int gold;
	protected Random random = new Random();
	
	public boolean inflict (int damageTaken){
		currentHealth -= damageTaken;
        currentHealth = (currentHealth < 0)? 0: currentHealth;
        return (currentHealth == 0) ? (false) : (true);
	}
	
	public boolean heal (int amountHealed) {
		currentHealth += amountHealed;
		currentHealth = (currentHealth > maxHealth)? maxHealth : currentHealth;
		return true;
	}
	
	public boolean useMana (int manaUsed) {
		if (manaUsed > currentMana){
			return false;
		}
		else {
			currentMana -= manaUsed;
			if (currentMana > maxMana){
				currentMana = maxMana;
			}
			return true;
		}
	}
	
    public int getDamage() {
        return (minDamage+random.nextInt(maxDamage-minDamage));
    }
    
    public String getName() {
        return name;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getCurrentMana(){
        return currentMana;
    }
    
    public int getMaxMana() {
        return maxMana;
    }
    
    public int getGold(){
        return gold;
    }
    
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    
    public void setCurrentMana(int currentMana){
        this.currentMana = currentMana;
    }
    
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth; 
    }
    
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
    
    public void setDamageMax(int damageMax) {
        this.maxDamage = damageMax;
    }
    
    public void setDamageMin(int damageMin) {
        this.minDamage = damageMin;
    }
   
    public abstract void draw(Graphics g);
}
