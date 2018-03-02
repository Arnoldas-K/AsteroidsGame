package game2;

import utilities.Vector2D;

import java.awt.*;
import static game2.Constants.FRAME_HEIGHT;

/**
 * Created by Arnoldas on 19/03/2017.
 */
public class Saucer extends Ship{
    static double SMAG_ACC = 150;
    Saucer (Controller ctrl){ // constructor
        this.ctrl = ctrl;
        this.position = new Vector2D(0, FRAME_HEIGHT/4);
        this.velocity = new Vector2D(0, 0);
        this.direction = new Vector2D(1, 0);
        this.color = Color.green;
        this.radius = 10;
        this.bulletInterval = 1200;
        this.dead = false;
    }

    @Override
    public void draw(Graphics2D g) { // draw method
        this.shipImg = new Sprite(Sprite.SHIP2, this.position, this.direction, this.radius*DRAWING_SCALE, this.radius*DRAWING_SCALE);
        shipImg.draw(g);
    }

    public boolean canHit(GameObject other){ // if can collide with given object
        if(other.getClass().equals(PlayerShip.class)) return true;
        return false;
    }

    public void mkBullet() { // creating a new bullet
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity).normalise().addScaled(direction, 300), this, 5);
        SoundManager.fire();
    }
}
