package animation;

import screens.Drawable;

public interface Effect extends Drawable {
	public boolean isDone();
	public void update();
}
