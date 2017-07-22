package characterEntities;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Entity {
	private String name;
    private int posX, posY;
	private int maxHealth, currentHealth;
	private int maxDamage, minDamage;
	private int stunCounter = STUN_TIME;
	private int damageTaken = 0;
	private boolean facingEast = true;
	private Animation currentAnimation = null;
    private Animation[] animations = new Animation[5];
	private ImageIcon avatar;
    private HealthBar healthBar;
	private Random random = new Random();
	
	private MotionStateLeftRight lrMotionState = MotionStateLeftRight.IDLE;
	private MotionStateUpDown udMotionState = MotionStateUpDown.IDLE;

	public enum EntityState {DEFAULT, ATTACKING, DAMAGED, DEAD}
	private EntityState entityState = EntityState.DEFAULT;
	
	private static final int VELOCITY = 2;
	private static final int STUN_TIME = 10;
	
	public Entity (String name, int maxHealth, int maxDamage, int minDamage, int posX, int posY) {
		this.name = name;
		currentHealth = this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
        this.posX = posX;
        this.posY = posY;
		this.healthBar = new HealthBar(this, maxHealth);
	}
	
	public void inflict (int damageTaken) {
	    this.damageTaken = damageTaken;
	}
	
	public void heal (int amountHealed) {
		currentHealth += amountHealed;
		currentHealth = (currentHealth > maxHealth) ? maxHealth : currentHealth;
	}

	public void playAnimation(int index) {
	    if (index >= 0 && index < animations.length)
	        currentAnimation = animations[index];
    }

	public void update () {
	    if (entityState == EntityState.DEFAULT || entityState == EntityState.ATTACKING) {
            switch (lrMotionState) {
                case LEFT:
                    posX -= VELOCITY;
                    break;
                case RIGHT:
                    posX += VELOCITY;
                    break;
            }
            switch (udMotionState) {
                case UP:
                    posY -= VELOCITY;
                    break;
                case DOWN:
                    posY += VELOCITY;
                    break;
            }
        }

        if (damageTaken > 0) {
            currentHealth -= damageTaken;
            currentHealth = (currentHealth < 0) ? 0 : currentHealth;
            if (currentAnimation != null) {
                currentAnimation.resetCounter();
                currentAnimation = null;
            }
            if (currentHealth > 0) {
                setEntityState(EntityState.DAMAGED);
            } else {
                setEntityState(EntityState.DEAD);
            }
            damageTaken = 0;
        }

		if (entityState == EntityState.DAMAGED && stunCounter > 0) {
	        //TODO: KNOCKBACK
		    stunCounter--;
        } else if (stunCounter <= 0) {
		    setEntityState(EntityState.DEFAULT);
		    stunCounter = STUN_TIME;
        }

		if (currentAnimation != null) {
            currentAnimation.update();
            if (currentAnimation.isDone()) {
                setEntityState(EntityState.DEFAULT);
                setEntityDirection();
                currentAnimation = null;
            }
        }
	}

	public void setAvatar (String filename) {
        avatar = new ImageIcon(filename);
    }

    public void faceEast () {
	    facingEast = true;
    }

    public void faceWest () {
	    facingEast = false;
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

    public boolean getFacingEast () {
	    return facingEast;
    }

    public EntityState getEntityState () {
	    return entityState;
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

    public void setPoint (Point newPoint) {
	    if (newPoint != null) {
            posX = newPoint.x;
            posY = newPoint.y;
        }
    }

    public void setLRMotionState (MotionStateLeftRight state) {
    	lrMotionState = state;

    	if (entityState != EntityState.DEFAULT) return;

    	if (state == MotionStateLeftRight.RIGHT) {
    	    facingEast = true;
        } else if (state == MotionStateLeftRight.LEFT) {
    	    facingEast = false;
        }
    }
    
    public void setUDMotionState (MotionStateUpDown state) {
    	udMotionState = state;
    }

    public void setEntityState (EntityState state) {
	    entityState = state;
    }

    public void setAnimation (int index, Animation animation) {
	    animations[index] = animation;
    }
   
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Image image = avatar.getImage();
        int x = posX;
        int width = image.getWidth(null);

        if (currentAnimation != null)
            currentAnimation.draw(g);
        healthBar.draw(g);

        if (!facingEast) {
            x += width;
            width = -width;
        }

        g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
    }

    private void setEntityDirection() {
        if (lrMotionState == MotionStateLeftRight.LEFT) {
            facingEast = false;
        } else if (lrMotionState == MotionStateLeftRight.RIGHT) {
            facingEast = true;
        }
    }
}
