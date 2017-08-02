package characterEntities;

import gui.DamageMarker;

import java.awt.*;
import java.util.ArrayList;

public class Dummy extends Entity {
	public Dummy(int posX, int posY, boolean facingEast) {
		super(null, 1, 0, 0, posX, posY, 0);
		setImageIcon("src/assets/enemies/dummyNeutral.png");
		setFacingEast(facingEast);
		setEntityType(EntityType.DUMMY);
	}

	@Override
	public ArrayList<Entity> getTargets() {
		return null;
	}

	@Override
	public DamageMarker inflict(int damageTaken, Entity attacker) {
		knockBackRight = attacker.getPosX() < posX;
		setEntityState(EntityState.DAMAGED);
		immuneTo.put(attacker, true);
		return (new DamageMarker(damageTaken, posX, posY));
	}

	@Override
	public DamageMarker inflict(int damageTaken, boolean knockBackRight) {
		this.knockBackRight = knockBackRight;
		setEntityState(EntityState.DAMAGED);
		return (new DamageMarker(damageTaken, posX, posY));
	}

	@Override
	public void setEntityState(EntityState entityState) {
		super.setEntityState(entityState);
		String filepath = "src/assets/enemies/dummy";
		switch (entityState) {
			case NEUTRAL:
				filepath += "Neutral";
				break;
			case DAMAGED:
				filepath += "Damaged";
				if (!knockBackRight && !facingEast || knockBackRight && facingEast) {
					filepath += "Right";
				} else {
					filepath += "Left";
				}
				break;
			case ATTACKING:
			case DEAD:
			default:
				break;
		}
		filepath += ".png";
		this.setImageIcon(filepath);
	}

	@Override
	public void update() {
		if (entityState == EntityState.DAMAGED && stunCounter < KNOCK_BACK_TIME) {
			stunCounter++;
		} else if (stunCounter >= KNOCK_BACK_TIME) {
			setEntityState(EntityState.NEUTRAL);
			stunCounter = 0;
		}
	}

	@Override
	public Rectangle getEntitySize() {
		return new Rectangle(posX, posY, getImageIcon().getIconWidth(), getImageIcon().getIconHeight());
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Image image = getImageIcon().getImage();
		int x = posX;
		int width = image.getWidth(null);

		if (!facingEast) {
			x += width;
			width = -width;
		}

		g2d.drawImage(image, x, posY, width, image.getHeight(null), null);
	}
}
