package characterEntities;

import animation.abilities.Ability;
import animation.effects.Effect;
import characterEntities.characterEffects.CharacterEffect;
import gameLogic.MapCollisionDetection;
import gui.DamageMarker;
import gui.HealthBar;
import screens.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
	protected Ability currentAbility;
	protected ArrayList<Ability> abilities;
	protected LinkedList<DamageMarker> targetMarkers;
	protected LinkedList<Effect> effects;
	protected HashMap<Entity, Boolean> immuneTo;
	protected ImageIcon imageIcon;
	protected HealthBar healthBar;
	protected Random random;

	//Character Effects eg. buffs, debuffs
	private boolean isTransparent = false;
	protected LinkedList<CharacterEffect> characterEffects;

	public enum EntityType {HERO, ENEMY, DUMMY}
	public enum EntityState {NEUTRAL, ATTACKING, DAMAGED, DEAD}
	public enum MotionStateUpDown {IDLE, UP, DOWN}
	public enum MotionStateLeftRight {IDLE, LEFT, RIGHT}
	public enum EntityAbility {
		FIRST(0), SECOND(1), THIRD(2), ULTIMATE(3);
		private int value;

		EntityAbility(int value) {
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
	protected static final int STUN_TIME = 20;
	protected static final int KNOCK_BACK_TIME = 15;
	protected static final int KNOCK_BACK_DUR = 2;

	public static final int DEFAULT_ENTITY_LENGTH = 75;

	/**--CONSTRUCTOR--**/
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

		targetMarkers = new LinkedList<>();
		effects = new LinkedList<>();
		characterEffects = new LinkedList<>();
	}

	/**--UPDATE HELPER FUNCTIONS--**/
	protected void updateDamageTaken() {
		//Entity takes damage logic
		if (damageTaken > 0) {
			currentHealth -= damageTaken;
			currentHealth = (currentHealth < 0) ? 0 : currentHealth;
			if (currentAbility != null) {
				currentAbility.setState(Ability.AbilityState.IS_DONE);
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
	}

	protected void updateStunAndKnockBack() {
		//Entity stagger + knockback
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
	}

	protected void updateStun() {
		//Entity stun
		if ((entityState == EntityState.DAMAGED || entityState == EntityState.DEAD) && stunCounter < STUN_TIME) {
			stunCounter++;
		} else if (stunCounter >= STUN_TIME && entityState == EntityState.DAMAGED) {
			setEntityState(EntityState.NEUTRAL);
			stunCounter = 0;
		}
	}

	protected void updateAbilities() {
		if (currentAbility != null) {
			currentAbility.update();
			if (currentAbility.hasEffects()) {
				effects.addAll(currentAbility.getEffects());
				currentAbility.clearEffects();
			}
			if (currentAbility.getState() == Ability.AbilityState.IS_DONE) {
				setEntityState(EntityState.NEUTRAL);
				calculateEntityDirection();
				currentAbility.reset();
				currentAbility = null;
			}
		}
		for (animation.abilities.Ability ability : abilities) {
			if (ability != null) ability.decrementCooldownCounter();
		}
	}

	protected void updateEffects() {
		for (Iterator<CharacterEffect> iterator = characterEffects.iterator(); iterator.hasNext();) {
			CharacterEffect effect = iterator.next();
			effect.update();
			if (effect.effectHasExpired()) {
				effect.removeEffect();
				iterator.remove();
			}
		}
	}

	/**--ENTITY ACTIONS--**/
	public void attack(EntityAbility ability) {
		Ability attemptedAbility = abilities.get(ability.getValue());
		if (attemptedAbility == null) return;

		if (attemptedAbility.shouldTrigger() && entityState != EntityState.DAMAGED && entityState != EntityState.DEAD) {
			attemptedAbility.didTrigger();
		} else if (entityState == EntityState.NEUTRAL && attemptedAbility != null && attemptedAbility.isOffCooldown()) {
			playAnimation(ability.getValue());
			setEntityState(EntityState.ATTACKING);
		}
	}

	// ..require ability for inflict, to know whether it allows knockback or not (should default to true)..
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

	private void playAnimation(int index) {
		if (index >= 0 && index < abilities.size()) {
			currentAbility = abilities.get(index);
			currentAbility.setupAbility();
		}
	}

	private void calculateEntityDirection() {
		if (lrMotionState == MotionStateLeftRight.LEFT) {
			facingEast = false;
		} else if (lrMotionState == MotionStateLeftRight.RIGHT) {
			facingEast = true;
		}
	}

	public void resetEntity() {
		for (int i = 0; i < abilities.size(); i++) {
			Ability ability = abilities.get(i);
			if (ability != null) {
				ability.reset();
				ability.clearCooldown();
			}
		}
		currentAbility = null;
		setEntityState(EntityState.NEUTRAL);

		currentHealth = maxHealth;
		immuneTo.clear();
	}

	/**--ACCESSORS AND MUTATORS--**/
	public boolean getImmuneTo(Entity entity) {
		return (immuneTo != null)? immuneTo.get(entity): false;
	}

	public void setImmuneTo(Entity entity, boolean immune) {
		if (immuneTo != null) {
			immuneTo.put(entity, immune);
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

	public ArrayList<Ability> getAbilities() {
		return abilities;
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

	public void setAbility(int index, Ability ability) {
		if (abilities.size() > index) {
			abilities.remove(index);
		}
		abilities.add(index, ability);
	}

	public MapCollisionDetection getMapCollisionDetection() {
		return mapCollisionDetection;
	}

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

		if (entityState != EntityState.NEUTRAL && !(entityState == EntityState.ATTACKING && currentAbility != null && currentAbility.allowsDirectionSwitching())) return;

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

	public LinkedList<DamageMarker> getTargetMarkers() {
		return targetMarkers;
	}

	public LinkedList<Effect> getEffects() {
		return effects;
	}

	public void emptyTargetMarkers() {
		targetMarkers.clear();
	}

	public void emptyEffects() {
		effects.clear();
	}

	public void notifyEnemyCreation(Entity enemy) {
		if (enemy != null) {
			this.immuneTo.put(enemy, false);
		}
	}

	public  void notifyEnemyDeath(Entity enemy) {
		if (enemy != null && this.immuneTo.containsKey(enemy)) {
			this.immuneTo.remove(enemy);
		}
	}

	public void addCharacterEffect(CharacterEffect effect) {
		if (effect != null) {
			for(Iterator<CharacterEffect> iterator = characterEffects.iterator(); iterator.hasNext();) {
				CharacterEffect characterEffect = iterator.next();
				if (characterEffect.getClass().getName().equals(effect.getClass().getName())) {
					characterEffect.removeEffect();
					iterator.remove();
				}
			}
			characterEffects.add(effect);
			effect.applyEffect();
		}
	}

	public void clearCharacterEffects() {
		characterEffects.clear();
	}

	public void setTransparent(boolean transparent) {
		this.isTransparent = transparent;
	}

	public boolean isInvisible() {
		return isTransparent;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	/**--UPDATE AND DRAW--**/
	public void update() {
		newPosX = posX;
		newPosY = posY;

		updateDamageTaken();
		updateStunAndKnockBack();
		updateAbilities();
		updateEffects();
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

		if (isTransparent) {
			AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2d.setComposite(alcom);
		}

		g2d.drawImage(image, x, posY, width, image.getHeight(null), null);

		for (CharacterEffect characterEffect : characterEffects) {
			characterEffect.draw(g);
		}

		if (isTransparent) {
			AlphaComposite resetAlcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			g2d.setComposite(resetAlcom);
		}
	}

	/**--ABSTRACT METHODS--**/
	public abstract void calculateTargetsDamage(Ability ability);
	public abstract LinkedList<Entity> getTargets();
	public abstract void resetImmuneTo();
	public abstract Rectangle getEntitySize();
}
