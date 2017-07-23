package animation;

import java.awt.*;

public class Projectile extends Animation {
    private int posX, posY;
    private int velocityX, velocityY;

    public Projectile(int posX, int posY, int velocityX, int velocityY) {
        this.posX = posX;
        this.posY = posY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public void update () {
        this.posX += velocityX;
        this.posY += velocityY;
    }

    public void draw(Graphics g) {
    }
}
