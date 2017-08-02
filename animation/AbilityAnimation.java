package animation;

import SquarePG.SquarePG;
import characterEntities.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AbilityAnimation extends Animation {
	public enum AbilityAnimationType {
		HERO_DEFAULT(75, 0, "heroDefault", Entity.Ability.DEFAULT, true, 4, 1, 0.5),
		RED_FIRST(-75, -75, "redFirst", Entity.Ability.FIRST, false, 3, 2, 2),
		RED_SECOND(75, 0, "heroDefault", Entity.Ability.SECOND, true, 4, 1, 0.5),
		RED_THIRD(75, 0, "heroDefault", Entity.Ability.THIRD, true, 4, 1, 0.5),
		YELLOW_FIRST(75, 0, "yellowFirst", Entity.Ability.FIRST, true, 3, 1, 1),
		YELLOW_SECOND(75, 0,  "heroDefault", Entity.Ability.SECOND, true, 4, 1, 0.5),
		YELLOW_THIRD(75, 0, "heroDefault", Entity.Ability.THIRD, true, 4, 1, 0.5),
		BLUE_FIRST(75, 0, "blueFirst", Entity.Ability.FIRST, true, 3, 1, 1),
		BLUE_SECOND(75, 0, "heroDefault", Entity.Ability.SECOND, true, 4, 1, 0.5),
		BLUE_THIRD(75, 0, "heroDefault", Entity.Ability.THIRD, true, 4, 1, 0.5),
		CIRCLE_DEFAULT(75, 0, "heroDefault", Entity.Ability.DEFAULT, true, 4, 1, 2);
		private int offsetX, offsetY;
		private String animationName;
		private Entity.Ability ability;
		private boolean hasDirection;
		int totalFrames, numLoops;
		double cooldownInSeconds;

		AbilityAnimationType(int offsetX, int offsetY, String animationName, Entity.Ability ability,
							 boolean hasDirection, int totalFrames, int numLoops, double cooldownInSeconds) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.animationName = animationName;
			this.ability = ability;
			this.hasDirection = hasDirection;
			this.totalFrames = totalFrames;
			this.numLoops = numLoops;
			this.cooldownInSeconds = cooldownInSeconds;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetY() {
			return offsetY;
		}

		public Entity.Ability getAbility() {
			return ability;
		}

		public double getCooldownInSeconds() {
			return cooldownInSeconds;
		}

		public int getNumLoops() {
			return numLoops;
		}

		public int getTotalFrames() {
			return totalFrames;
		}

		public String getAnimationName() {
			return animationName;
		}

		public boolean getHasDirection() {
			return hasDirection;
		}
	}
	private Entity entity;
	private int cooldownTotal, cooldownCounter;
	private boolean hasDirection;
	private Entity.Ability ability;
	private AbilityAnimationType animationType;

	public AbilityAnimation(AbilityAnimationType animationType, Entity entity) {
		this.drawableType = DrawableType.ENTITY_EFFECT;
		this.animationType = animationType;
		this.entity = entity;
		this.animationName = animationType.getAnimationName();
		this.totalFrames = animationType.getTotalFrames();
		this.ability = animationType.getAbility();
		this.hasDirection = animationType.getHasDirection();
		this.cooldownTotal = (int)Math.round(animationType.getCooldownInSeconds()*SquarePG.FPS);
		this.cooldownCounter = 0;
		setNumLoops(animationType.getNumLoops());
		this.imageIcons = new ArrayList<>(totalFrames);
		for (int i = 0; i < totalFrames; i++) {
			imageIcons.add(i, new ImageIcon(FILEPATH_ROOT+animationName+i+FILEPATH_PNG));
		}
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
		int offsetX = ((entity.getFacingEast() || !hasDirection) ? animationType.getOffsetX() :
				entity.getImageIcon().getIconWidth()-animationType.getOffsetX()-width);
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
