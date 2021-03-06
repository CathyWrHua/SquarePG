package gui;

import animation.effects.Effect;

import java.awt.*;

public class DamageMarker extends Effect {
	private int currentLifetime = LIFETIME;
	private int damage;
	private float speed = INITIAL_SPEED;
	private boolean done;

	private static final int LIFETIME = 30;
	private static final int OFFSET_X_1_DIG = 30;
	private static final int OFFSET_X_2_DIG = 21;
	private static final int OFFSET_Y = 15;
	private static final float SLOWING_MODIFIER = 0.95f;
	private static final float INITIAL_SPEED = 3.0f;

	public DamageMarker(int damage, int posX, int posY) {
		super(posX, posY, null, null, EffectType.DAMAGE);
		this.damage = damage;
		this.done = false;
	}

	public boolean isDone() {
		return done;
	}

	public void update() {
		posY -= Math.round(speed);
		speed *= SLOWING_MODIFIER;
		currentLifetime--;
		if (currentLifetime <= 0) {
			done = true;
		}
	}

	public void draw(Graphics g) {
		if (done) return;
		Graphics2D g2d = (Graphics2D)g;
		int x = posX + ((damage >= 10) ? OFFSET_X_2_DIG : OFFSET_X_1_DIG);
		g2d.setPaint(Color.RED);
		g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		g2d.drawString(Integer.toString(damage), x, posY+OFFSET_Y);
	}
}
