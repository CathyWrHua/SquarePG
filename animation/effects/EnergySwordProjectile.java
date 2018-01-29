package animation.effects;

import animation.Animation;
import characterEntities.Entity;
import characterEntities.Hero;
import gui.DamageMarker;

public class EnergySwordProjectile extends Projectile {

	private static int OFFSET_X = -10;
	private static int OFFSET_Y = -120;

	private static final String ABILITY_NAME = "energySword";
	private final float DAMAGE_MULTIPLIER = 1.5f;
	private Entity target;

	public EnergySwordProjectile(Hero hero, Entity target) {
		super(hero,
				hero.getMapCollisionDetection(),
				new Animation(target.getPosX(), target.getPosY(), OFFSET_X, OFFSET_Y,FILEPATH_EFFECTS+ABILITY_NAME+"Charge", 4, 1),
				new Animation(target.getPosX(), target.getPosY(), OFFSET_X, OFFSET_Y, FILEPATH_EFFECTS+ABILITY_NAME, 3, 1),
				0,
				0);
		this.target = target;
		damage *= DAMAGE_MULTIPLIER;
	}

	public void update() {
		super.update();


		if (regularAnimation.isDone()) {
			if (!hasCollided) {
				dealDamage();
				setHasCollided(true);
			}
		} else {
			regularAnimation.setPosition(target.getPosX(), target.getPosY());
		}
	}

	@Override
	public void dealDamage() {
		DamageMarker marker = target.inflict(damage, false);
		if (marker != null) {
			targetMarkers.add(marker);
		}
	}
}