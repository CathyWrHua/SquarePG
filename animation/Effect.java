package animation;

import screens.Drawable;

public abstract class Effect extends Drawable {
	public abstract boolean isDone();
	public abstract void update();
}
