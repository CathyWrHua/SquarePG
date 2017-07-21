package characterEntities;

import java.awt.*;

public class Projectile {
    int posX, posY;
    int speedX, speedY;

    public Projectile (int posX, int posY, int speedX, int speedY) {
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void destroy () {}

    public void update () {
        this.posX += speedX;
        this.posY += speedY;
    }

    public void draw (Graphics g) {
    }
}
