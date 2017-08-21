package characterEntities.characterEffects;

import SquarePG.SquarePG;
import animation.Animation;
import characterEntities.Entity;

import java.awt.*;

public abstract class Buff implements CharacterEffect {

	protected long countdown;
	protected Entity entity;
	protected Animation buffAnimation;

	public Buff(double seconds, Entity entity, Animation buffAnimation) {
		countdown = Math.round(seconds*SquarePG.FPS);
		this.entity = entity;
		this.buffAnimation = buffAnimation;
	}

	@Override
	public void update() {
		if (buffAnimation != null) {
			buffAnimation.update();
		}

		if (countdown > 0) {
			countdown--;
		}
	}

	public abstract void applyEffect();
	public abstract void removeEffect();

	@Override
	public boolean effectHasExpired() {
		return (countdown <= 0);
	}

	@Override
	public void draw(Graphics g) {
		if (buffAnimation != null) {
			buffAnimation.draw(g);
		}
	}
}
