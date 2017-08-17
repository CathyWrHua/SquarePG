package characterEntities.characterEffects;

import characterEntities.Entity;
import screens.Drawable;

public interface CharacterEffect extends Drawable {

	void update();
	void applyEffect();
	void removeEffect();
	boolean effectHasExpired();
}
