package characterEntities;

import gameLogic.MapCollisionDetection;
import animation.effects.ProjectileAnimation;
import gui.DamageMarker;
import gui.HealthBar;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Entity implements Drawable {
	protected int posX, posY;
	protected int newPosX, newPosY;
	protected int maxHealth, currentHealth;
	protected int maxDamage, minDamage;
	protected int stunCounter = 0;
	protected int damageTaken;
	protected double velocity;
	protected boolean knockBackRight;
	protected boolean facingEast;
	protected MapCollisionDetection mapCollisionDetection;
	protected animation.abilities.Ability currentAbility;
	protected ArrayList<animation.abilities.Ability> abilities;
	protected ArrayList<ProjectileAnimation> projectileAnimations;
	protected ArrayList<DamageMarker> targetMarkers;
	protected HashMap<Entity, Boolean> immuneTo;
	protected ImageIcon imageIcon;
	protected HealthBar healthBar;
	protected Random random;

	public enum EntityType {HERO, ENEMY, DUMMY}
	public enum EntityState {NEUTRAL, ATTACKING, DAMAGED, DEAD}
	public enum MotionStateUpDown {IDLE, UP, DOWN}
	public enum MotionStateLeftRight {IDLE, LEFT, RIGHT}
	public enum Ability {
		DEFAULT(0), FIRST(1), SECOND(2), THIRD(3), ULTIMATE(4);
		private int value;

		Ability(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	protected EntityType entityType;
	protected EntityState entityState;
	protected MotionStateLeftRight lrMotionState;
	protected MotionStateUpDown udMotionState;

	protected static final int NUM_ANIMATIONS = 5;
	protected static final int STUN_TIME = 40;
	protected static final int KNOCK_BACK_TIME = 15;
	protected static final int KNOCK_BACK_DUR = 2;
	
	public Entity(MapCollisionDetection mapCollisionDetection, int maxHealth, int maxDamage, int minDamage, int posX, int posY, double velocity) {
		this.mapCollisionDetection = mapCollisionDetection;
		this.currentHealth = this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		this.damageTaken = 0;

		this.posX = posX;
		this.posY = posY;
		this.facingEast = true;
		this.velocity = velocity;

		this.abilities = new ArrayList<>(NUM_ANIMATIONS);
		for (int i = 0; i < NUM_ANIMATIONS; i++) {
			this.abilities.add(i, null);
		}

		this.currentAbility = null;
		this.healthBar = new HealthBar(this);
		this.random = new Random();
		this.immuneTo = new HashMap<>();

		entityState = EntityState.NEUTRAL;
		lrMotionState = MotionStateLeftRight.IDLE;
		udMotionState = MotionStateUpDown.IDLE;

		targetMarkers = new ArrayList<>();
		this.projectileAnimations = new ArrayList<>();
	}

	public void attack(Ability ability) {
		animation.abilities.Ability attemptedAbility = abilities.get(ability.getValue());
		if (entityState == EntityState.NEUTRAL && attemptedAbility != null && attemptedAbility.isOffCooldown()) {
			playAnimation(ability.getValue());
			if (attemptedAbility.isInstantCast()) attemptedAbility.resetCooldown();
			setEntityState(EntityState.ATTACKING);
		}
	}
	
	public DamageMarker inflict(int damageTaken, Entity attacker) {
		DamageMarker damageMarker;
		this.damageTaken = damageTaken;
		this.knockBackRight = attacker.getPosX() < posX;
		immuneTo.put(attacker, true);

		damageMarker = (currentHealth <= 0) ? null : (new DamageMarker(damageTaken, posX, posY));
		return damageMarker;
	}

	public DamageMarker inflict(int damageTaken, boolean knockBackRight) {
		DamageMarker damageMarker;
		this.damageTaken = damageTaken;
		this.knockBackRight = knockBackRight;

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
		if (index >= 0 && index < abilities.size()) {
			currentAbility = abilities.get(index);
			currentAbility.reset();
		}
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

	public ArrayList<DamageMarker> getTargetMarkers() {
		return targetMarkers;
	}

	public ArrayList<ProjectileAnimation> getProjectileAnimations() {
		return projectileAnimations;
	}

	public ArrayList<animation.abilities.Ability> getAbilities() {
		return abilities;
	}

	public abstract ArrayList<Entity> getTargets();

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

	public int getCenterX() {
		return posX + imageIcon.getIconWidth()/2;
	}

	public int getCenterY() {
		return posY + imageIcon.getIconHeight()/2;
	}

	public void setImageIcon(String filename) {
		imageIcon = new ImageIcon(filename);
	}

	public void setFacingEast(boolean facingEast) {
		this.facingEast = facingEast;
	}

	public void setAnimation(int index, animation.abilities.Ability ability) {
		if (abilities.size() > index) {
			abilities.remove(index);
		}
		abilities.add(index, ability);
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

	public animation.abilities.Ability getCurrentAbility() {
		return currentAbility;
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
			if (currentAbility != null) {
				currentAbility.kill();
				currentAbility = null;
			}
			if (currentHealth > 0) {
				setEntityState(EntityState.DAMAGED);
			} else {
				setEntityState(EntityState.DEAD);
			}
			stunCounter = 0;
			damageTaken = 0;
		}

		if ((entityState == EntityState.DAMAGED || entityState == EntityState.DEAD) && stunCounter < STUN_TIME) {
			if (stunCounter < KNOCK_BACK_TIME) {
				if (knockBackRight) {
					newPosX += KNOCK_BACK_DUR;
				} else {
					newPosX -= KNOCK_BACK_DUR;
				}
			}
			stunCounter++;
		} else if (stunCounter >= STUN_TIME && entityState == EntityState.DAMAGED) {
			setEntityState(EntityState.NEUTRAL);
			stunCounter = 0;
		}

		if (currentAbility != null) {
			currentAbility.update();
			if (currentAbility.isDone()) {
				setEntityState(EntityState.NEUTRAL);
				calculateEntityDirection();
				currentAbility.resetDone();
				currentAbility = null;
			}
		}
		for (animation.abilities.Ability ability : abilities) {
			if (ability != null) ability.decrementCooldownCounter();
		}
	}

	public void emptyTargetMarkers() {
		targetMarkers.clear();
	}

	public void emptyProjectileAnimations() {
		projectileAnimations.clear();
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Image image = imageIcon.getImage();
		int x = posX;
		int width = image.getWidth(null);

		healthBar.draw(g);

		if (!facingEast) {
			x += width;
			width = -width;
		}

		g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
	}
}
