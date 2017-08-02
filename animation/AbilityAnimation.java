package animation;

import SquarePG.SquarePG;
import characterEntities.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AbilityAnimation extends Animation {
	public enum AbilityAnimationType {
		HERO_DEFAULT(75, 0),
		RED_FIRST(-75, -75),
		RED_SECOND(75, 0),
		RED_THIRD(75, 0),
		YELLOW_FIRST(75, 0),
		YELLOW_SECOND(75, 0),
		YELLOW_THIRD(75,0),
		BLUE_FIRST(75, 0),
		BLUE_SECOND(75, 0),
		BLUE_THIRD(75, 0),
		CIRCLE_DEFAULT(75, 0);
		private int offsetX, offsetY;

		AbilityAnimationType(int offsetX, int offsetY) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}
	}
	private Entity entity;
	private int cooldownTotal, cooldownCounter;
	private boolean hasDirection;
	private Entity.Ability ability;
	private AbilityAnimationType animationType;
	private EffectType effectType;

	public AbilityAnimation(AbilityAnimationType animationType, Entity entity) {
		this.effectType = EffectType.ENTITY_EFFECT;
		this.entity = entity;
		this.animationType = animationType;
		this.cooldownCounter = 0;
		switch(animationType) {
			case HERO_DEFAULT:
				setValues("heroDefault", 4, 0.5, Entity.Ability.DEFAULT, true, 1);
				break;
			case RED_FIRST:
				setValues("redFirst", 3, 2, Entity.Ability.FIRST, false, 2);
				break;
			case RED_SECOND:
				setValues("heroDefault", 4, 0.5, Entity.Ability.SECOND, true, 1);
				break;
			case RED_THIRD:
				setValues("heroDefault", 4, 0.5, Entity.Ability.THIRD, true, 1);
				break;
			case YELLOW_FIRST:
				setValues("yellowFirst", 3, 1, Entity.Ability.FIRST, true, 1);
				break;
			case YELLOW_SECOND:
				setValues("heroDefault", 4, 0.5, Entity.Ability.SECOND, true, 1);
				break;
			case YELLOW_THIRD:
				setValues("heroDefault", 4, 0.5, Entity.Ability.THIRD, true, 1);
				break;
			case BLUE_FIRST:
				setValues("blueFirst", 3, 1, Entity.Ability.FIRST, true, 1);
				break;
			case BLUE_SECOND:
				setValues("heroDefault", 4, 0.5, Entity.Ability.SECOND, true, 1);
				break;
			case BLUE_THIRD:
				setValues("heroDefault", 4, 0.5, Entity.Ability.THIRD, true, 1);
				break;
			case CIRCLE_DEFAULT:
				//temp asset
				setValues("heroDefault", 4, 2, Entity.Ability.DEFAULT, true, 1);
				break;
			default:

				break;
		}
		this.imageIcons = new ArrayList<>(totalFrames);
		for (int i = 0; i < totalFrames; i++) {
			imageIcons.add(i, new ImageIcon("src/assets/animations/"+animationName+i+".png"));
		}
	}

	private void setValues(String animationName, int totalFrames, double cooldownInSeconds, Entity.Ability ability, boolean hasDirection, int numLoops) {
		this.animationName = animationName;
		this.totalFrames = totalFrames;
		this.ability = ability;
		this.hasDirection = hasDirection;
		this.cooldownTotal = (int)Math.round(cooldownInSeconds*SquarePG.FPS);
		setNumLoops(numLoops);
	}

	public void decrementCooldownCounter() {
		if (cooldownCounter > 0) {
			cooldownCounter--;
		}
	}

	public void resetCooldown () {
		this.cooldownCounter = cooldownTotal;
	}

	public void resetDone() {
		done = false;
	}

	public boolean isOffCooldown () {
		return (cooldownCounter <= 0);
	}

	@Override
	public EffectType getEffectType() {
		return effectType;
	}

	public String getAnimationName() {
		return animationName;
	}

	public int getCooldownCounter() {
		return cooldownCounter;
	}

	public int getCooldownTotal() {
		return cooldownTotal;
	}

	public Entity.Ability getAbility() {
		return ability;
	}

	public void draw(Graphics g) {
		if (imageIcon == null) return;
		Graphics2D g2d = (Graphics2D)g;
		Image image = imageIcon.getImage();
		int x = entity.getPosX();
		int width = image.getWidth(null);
		int offsetX = ((entity.getFacingEast() || !hasDirection) ? animationType.getOffsetX() : -width);
		int offsetY = animationType.getOffsetY();

		if (hasDirection && !entity.getFacingEast()) {
			x += width;
			width = -width;
		}

		if (counter/ANIMATION_SPEED < totalFrames) {
			g2d.drawImage(image, x+offsetX, entity.getPosY()+offsetY, width, image.getHeight(null), null);
		}
	}
}
