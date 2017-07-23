package characterEntities;

import screens.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Entity {
    protected int posX, posY;
	private int maxHealth, currentHealth;
	private int maxDamage, minDamage;
    private int stunCounter = STUN_TIME;
	private int damageTaken;
	protected int velocity;
	private boolean attackerFacingEast;
	private boolean facingEast;
	private AbilityAnimation currentAbilityAnimation;
    private AbilityAnimation[] abilityAnimations;
	private ImageIcon imageIcon;
    private HealthBar healthBar;
	private Random random;
	private GameScreen game;

    public enum EntityState {DEFAULT, ATTACKING, DAMAGED, DEAD}
    public enum MotionStateUpDown {IDLE, UP, DOWN}
    public enum MotionStateLeftRight {IDLE, LEFT, RIGHT}

    private EntityState entityState;
	private MotionStateLeftRight lrMotionState;
	private MotionStateUpDown udMotionState;

    private static final int NUM_ANIMATIONS = 5;
	private static final int STUN_TIME = 15;
	private static final int KNOCK_BACK_DUR = 1;
	
	public Entity(GameScreen game, int maxHealth, int maxDamage, int minDamage, int posX, int posY, int velocity) {
	    this.game = game;
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

        setEntityState(EntityState.DEFAULT);
		setLRMotionState(MotionStateLeftRight.IDLE);
		setUDMotionState(MotionStateUpDown.IDLE);
	}
	
	public void inflict(int damageTaken, boolean attackerFacingEast) {
	    this.game.createDamageMarker(damageTaken, posX, posY);
	    this.damageTaken = damageTaken;
	    this.attackerFacingEast = attackerFacingEast;
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

	public void update() {
	    if (entityState == EntityState.DEFAULT || entityState == EntityState.ATTACKING) {
            switch (lrMotionState) {
                case LEFT:
                    posX -= velocity;
                    break;
                case RIGHT:
                    posX += velocity;
                    break;
                case IDLE:
                default:
                    break;
            }
            switch (udMotionState) {
                case UP:
                    posY -= velocity;
                    break;
                case DOWN:
                    posY += velocity;
                    break;
                case IDLE:
                default:
                    break;
            }
        }

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

		if (entityState == EntityState.DAMAGED && stunCounter > 0) {
	        if (attackerFacingEast) {
	            posX += KNOCK_BACK_DUR;
            } else {
	            posX -= KNOCK_BACK_DUR;
            }
		    stunCounter--;
        } else if (stunCounter <= 0) {
		    setEntityState(EntityState.DEFAULT);
		    stunCounter = STUN_TIME;
        }

		if (currentAbilityAnimation != null) {
            currentAbilityAnimation.update();
            if (currentAbilityAnimation.isDone()) {
                setEntityState(EntityState.DEFAULT);
                setEntityDirection();
                currentAbilityAnimation = null;
            }
        }
	}

	ImageIcon getImageIcon() {
	    return imageIcon;
    }

    GameScreen getGame() {
	    return game;
    }
	
    int getDamage() {
        return (minDamage+random.nextInt(maxDamage-minDamage));
    }
    
    int getCurrentHealth() {
        return currentHealth;
    }
    
    int getMaxHealth() {
        return maxHealth;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    boolean getFacingEast() {
	    return facingEast;
    }

    boolean getAttackerFacingEast() {
	    return attackerFacingEast;
    }

    EntityState getEntityState() {
	    return entityState;
    }

    void setImageIcon(String filename) {
        imageIcon = new ImageIcon(filename);
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

    public void setLRMotionState(MotionStateLeftRight state) {
    	lrMotionState = state;

    	if (entityState != EntityState.DEFAULT) return;

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

    private void setEntityDirection() {
        if (lrMotionState == MotionStateLeftRight.LEFT) {
            facingEast = false;
        } else if (lrMotionState == MotionStateLeftRight.RIGHT) {
            facingEast = true;
        }
    }

    void setFacingEast(boolean facingEast) {
        this.facingEast = facingEast;
    }

    void setAttackerFacingEast(boolean attackerFacingEast) {
	    this.attackerFacingEast = attackerFacingEast;
    }

    void setAnimation(int index, AbilityAnimation abilityAnimation) {
	    abilityAnimations[index] = abilityAnimation;
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
