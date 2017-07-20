package characterEntities;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Entity {
	private String name;
    private int posX, posY;
	private int maxHealth, currentHealth;
	private int maxDamage, minDamage;
	private String direction = "East";
	private Animation currentAnimation = null;
    private Animation[] animations = new Animation[5];
	private ImageIcon avatar;
    private HealthBar healthBar;
	private Random random = new Random();
	
	private MotionStateLeftRight lrMotionState = MotionStateLeftRight.IDLE;
	private MotionStateUpDown udMotionState = MotionStateUpDown.IDLE;
	
	private final int velocity = 2;
	
	public Entity () {
		name = "Unknown";
		maxHealth = currentHealth = 100;
		maxDamage = minDamage = 50;
	}
	
	public Entity (String name, int maxHealth, int maxDamage, int minDamage, int posX, int posY) {
		this.name = name;
		currentHealth = this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
        this.posX = posX;
        this.posY = posY;
		this.healthBar = new HealthBar(posX, posY, maxHealth);
	}
	
	public boolean inflict (int damageTaken) {
		currentHealth -= damageTaken;
        currentHealth = (currentHealth < 0) ? 0 : currentHealth;
        healthBar.inflict(damageTaken);
        return !(currentHealth == 0);
	}
	
	public boolean heal (int amountHealed) {
		currentHealth += amountHealed;
		currentHealth = (currentHealth > maxHealth)? maxHealth : currentHealth;
		return true;
	}
	
	public void update () {
		switch (lrMotionState) {
		case LEFT:
			posX -= velocity;
			break;
		case RIGHT:
			posX += velocity;
		}
		
		switch (udMotionState) {
		case UP:
			posY -= velocity;
			break;
		case DOWN:
			posY += velocity;
		}
		if (currentAnimation != null)
		    currentAnimation.update();
		else if (currentAnimation.isDone())
		    currentAnimation = null;
	}

	public void setAvatar (String filename) {
        avatar = new ImageIcon(filename);
    }
	
    public int getDamage () {
        return (minDamage+random.nextInt(maxDamage-minDamage));
    }
    
    public String getName () {
        return name;
    }
    
    public int getCurrentHealth () {
        return currentHealth;
    }
    
    public int getMaxHealth () {
        return maxHealth;
    }

    public int getPosX () {
        return posX;
    }

    public int getPosY () {
        return posY;
    }

    public String getDirection () {
	    return direction;
    }
    
    public void setCurrentHealth (int currentHealth) {
        this.currentHealth = currentHealth;
    }
    
    public void setMaxHealth (int maxHealth) {
        this.maxHealth = maxHealth; 
    }
    
    public void setDamageMax (int damageMax) {
        this.maxDamage = damageMax;
    }
    
    public void setDamageMin (int damageMin) {
        this.minDamage = damageMin;
    }

    public void setPosX (int posX) {
        this.posX = posX;
    }

    public void setPosY (int posY) {
        this.posY = posY;
    }
    
    public void setLRMotionState (MotionStateLeftRight state) {
    	lrMotionState = state;
    }
    
    public void setUDMotionState (MotionStateUpDown state) {
    	udMotionState = state;
    }

    public void setAnimation (int index, Animation animation) {
	    animations[index] = animation;
    }
   
    public void draw (Graphics g) {
	    //TODO: draw the entity name label
        Graphics2D g2d = (Graphics2D)g;
        if (currentAnimation != null)
            currentAnimation.draw(g);
        g2d.drawImage(avatar.getImage(), posX, posY, null);
    }
}
