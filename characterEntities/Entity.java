package characterEntities;

import animation.AbilityAnimation;
import gui.DamageMarker;
import gui.HealthBar;
import screens.GameMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public abstract class Entity {
    protected int posX, posY;
    protected int newPosX, newPosY;
	protected int maxHealth, currentHealth;
	protected int maxDamage, minDamage;
    protected int stunCounter = STUN_TIME;
	protected int damageTaken;
	protected int velocity;
	protected boolean attackerFacingEast;
	protected boolean facingEast;
	protected GameMap map;
	protected AbilityAnimation currentAbilityAnimation;
    protected AbilityAnimation[] abilityAnimations;
    protected HashMap<Entity, Boolean> immuneTo;
	protected ImageIcon imageIcon;
    protected HealthBar healthBar;
	protected Random random;

    public enum EntityType {HERO, ENEMY, DUMMY}
    public enum EntityState {NEUTRAL, ATTACKING, DAMAGED, DEAD}
    public enum MotionStateUpDown {IDLE, UP, DOWN}
    public enum MotionStateLeftRight {IDLE, LEFT, RIGHT}

    protected EntityType entityType;
    protected EntityState entityState;
	protected MotionStateLeftRight lrMotionState;
	protected MotionStateUpDown udMotionState;

    protected static final int NUM_ANIMATIONS = 5;
	protected static final int STUN_TIME = 15;
	protected static final int KNOCK_BACK_DUR = 1;
	
	public Entity(GameMap map, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
	    this.map = map;
		this.currentHealth = this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		this.damageTaken = 0;

        this.posX = posX;
        this.posY = posY;
        this.facingEast = true;
        this.velocity = velocity;

        this.abilityAnimations = new AbilityAnimation[NUM_ANIMATIONS];
        this.currentAbilityAnimation = null;
		this.healthBar = new HealthBar(this);
		this.random = new Random();
		this.immuneTo = new HashMap<>();

        entityState = EntityState.NEUTRAL;
        lrMotionState = MotionStateLeftRight.IDLE;
        udMotionState = MotionStateUpDown.IDLE;
	}
	
	public DamageMarker inflict(int damageTaken, Entity attacker) {
	    DamageMarker damageMarker;
	    this.damageTaken = damageTaken;
	    this.attackerFacingEast = attacker.getFacingEast();
	    immuneTo.put(attacker, true);

	    damageMarker = (currentHealth <= 0) ? null : (new DamageMarker(damageTaken, posX, posY));
	    return damageMarker;
	}
	
	public void heal(int amountHealed) {
	    if (entityState != EntityState.DEAD) {
            currentHealth += amountHealed;
            currentHealth = (currentHealth > maxHealth) ? maxHealth : currentHealth;
        }
	}

	void playAnimation(int index) {
	    if (index >= 0 && index < abilityAnimations.length)
	        currentAbilityAnimation = abilityAnimations[index];
    }

    private void calculateEntityDirection() {
        if (lrMotionState == MotionStateLeftRight.LEFT) {
            facingEast = false;
        } else if (lrMotionState == MotionStateLeftRight.RIGHT) {
            facingEast = true;
        }
    }

	public ImageIcon getImageIcon() {
	    return imageIcon;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public EntityType getEntityType() {
        return entityType;
    }
	
    public int getDamage() {
        return (minDamage+random.nextInt(maxDamage-minDamage));
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean getFacingEast() {
        return facingEast;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setImageIcon(String filename) {
        imageIcon = new ImageIcon(filename);
    }

    public void setFacingEast(boolean facingEast) {
        this.facingEast = facingEast;
    }

    public void setAttackerFacingEast(boolean attackerFacingEast) {
        this.attackerFacingEast = attackerFacingEast;
    }

    public void setAnimation(int index, AbilityAnimation abilityAnimation) {
        abilityAnimations[index] = abilityAnimation;
    }

    public abstract Rectangle getEntitySize();
    
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public void setDamageMax(int damageMax) {
        this.maxDamage = damageMax;
    }
    
    public void setDamageMin(int damageMin) {
        this.minDamage = damageMin;
    }

    public void setPoint(Point newPoint) {
	    if (newPoint != null) {
            posX = newPoint.x;
            posY = newPoint.y;
        }
    }

    public void setEntityType(EntityType entityType) {
	    this.entityType = entityType;
    }

    public void setLRMotionState(MotionStateLeftRight state) {
    	lrMotionState = state;

    	if (entityState != EntityState.NEUTRAL) return;

    	if (state == MotionStateLeftRight.RIGHT) {
    	    facingEast = true;
        } else if (state == MotionStateLeftRight.LEFT) {
    	    facingEast = false;
        }
    }
    
    public void setUDMotionState(MotionStateUpDown state) {
    	udMotionState = state;
    }

    public void setEntityState(EntityState state) {
	    entityState = state;
    }

    public void update() {
        newPosX = posX;
        newPosY = posY;

        if (damageTaken > 0) {
            currentHealth -= damageTaken;
            currentHealth = (currentHealth < 0) ? 0 : currentHealth;
            if (currentAbilityAnimation != null) {
                currentAbilityAnimation.resetCounter();
                currentAbilityAnimation = null;
            }
            if (currentHealth > 0) {
                setEntityState(EntityState.DAMAGED);
            } else {
                setEntityState(EntityState.DEAD);
            }
            damageTaken = 0;
        }

        if ((entityState == EntityState.DAMAGED || entityState == EntityState.DEAD) && stunCounter > 0) {
            if (attackerFacingEast) {
                newPosX += KNOCK_BACK_DUR;
            } else {
                newPosX -= KNOCK_BACK_DUR;
            }
            stunCounter--;
        } else if (stunCounter <= 0 && entityState == EntityState.DAMAGED) {
            setEntityState(EntityState.NEUTRAL);
            stunCounter = STUN_TIME;
        }

        if (currentAbilityAnimation != null) {
            currentAbilityAnimation.update();
            if (currentAbilityAnimation.isDone()) {
                setEntityState(EntityState.NEUTRAL);
                calculateEntityDirection();
                currentAbilityAnimation = null;
            }
        }
    }
   
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Image image = imageIcon.getImage();
        int x = posX;
        int width = image.getWidth(null);

        if (currentAbilityAnimation != null)
            currentAbilityAnimation.draw(g);
        healthBar.draw(g);

        if (!facingEast) {
            x += width;
            width = -width;
        }

        g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
    }
}
