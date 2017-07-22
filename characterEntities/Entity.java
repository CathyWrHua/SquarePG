package characterEntities;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Entity {
    private int posX, posY;
	private int maxHealth, currentHealth;
	private int maxDamage, minDamage;
	private int stunCounter = STUN_TIME;
	private int damageTaken;
	private boolean facingEast;
	private Animation currentAnimation;
    private Animation[] animations;
	private ImageIcon avatar;
    private HealthBar healthBar;
	private Random random;

    public enum EntityState {DEFAULT, ATTACKING, DAMAGED, DEAD}
    public enum MotionStateUpDown {IDLE, UP, DOWN}
    public enum MotionStateLeftRight {IDLE, LEFT, RIGHT}

    private EntityState entityState;
	private MotionStateLeftRight lrMotionState;
	private MotionStateUpDown udMotionState;

    private static final int NUM_ANIMATIONS = 5;
	private static final int VELOCITY = 2;
	private static final int STUN_TIME = 15;
	
	public Entity (int maxHealth, int maxDamage, int minDamage, int posX, int posY) {
		this.currentHealth = this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		this.damageTaken = 0;

        this.posX = posX;
        this.posY = posY;
        this.facingEast = true;

        this.animations = new Animation[NUM_ANIMATIONS];
        this.currentAnimation = null;
		this.healthBar = new HealthBar(this);
		this.random = new Random();

        setEntityState(EntityState.DEFAULT);
		setLRMotionState(MotionStateLeftRight.IDLE);
		setUDMotionState(MotionStateUpDown.IDLE);
	}
	
	public void inflict (int damageTaken) {
	    this.damageTaken = damageTaken;
	}
	
	public void heal (int amountHealed) {
		currentHealth += amountHealed;
		currentHealth = (currentHealth > maxHealth) ? maxHealth : currentHealth;
	}

	void playAnimation(int index) {
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

	void setAvatar (String filename) {
        avatar = new ImageIcon(filename);
    }
	
    int getDamage () {
        return (minDamage+random.nextInt(maxDamage-minDamage));
    }
    
    int getCurrentHealth () {
        return currentHealth;
    }
    
    int getMaxHealth () {
        return maxHealth;
    }

    public int getPosX () {
        return posX;
    }

    public int getPosY () {
        return posY;
    }

    boolean getFacingEast () {
	    return facingEast;
    }

    EntityState getEntityState () {
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

    public void setPoint (Point newPoint) {
	    if (newPoint != null) {
            posX = newPoint.x;
            posY = newPoint.y;
        }
    }

    private void setEntityDirection() {
        if (lrMotionState == MotionStateLeftRight.LEFT) {
            facingEast = false;
        } else if (lrMotionState == MotionStateLeftRight.RIGHT) {
            facingEast = true;
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

    void setAnimation (int index, Animation animation) {
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
}
