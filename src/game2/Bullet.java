package game2;

import utilities.Vector2D;

import java.awt.*;

import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

/**
 * Created by akatke on 24/02/2017.
 */
public class Bullet extends GameObject {
    Ship whichShip; // storing which ship shot the bullet, for collision detection
    Bullet(Vector2D pos, Vector2D vel, Ship ship, int radius) { // constructor
        this.position = pos;
        this.velocity = vel;
        this.radius = radius;
        this.dead = false;
        this.whichShip = ship;
    }

    @Override
    public void update() { // updates position of bullet
        position.addScaled(velocity, DT);
        isOutOfBounds(); // if out of bounds, destroys
    }

    @Override
    public void draw(Graphics2D g) { // drawing method, for saucer and player ship bullets
        if (whichShip.getClass() == PlayerShip.class) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.RED);
        }
        g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));
    }

    private void isOutOfBounds() { // if out of bounds, destroys the bullet
        if (position.y > FRAME_HEIGHT || position.x < 0) {
            this.dead = true;
        } else if (position.x > FRAME_WIDTH || position.x < 0) {
            this.dead = true;
        }
    }

    public boolean canHit(GameObject other) { // checking if can collide with given object
        if (Asteroid.class.equals(other.getClass())) {
            if (this.whichShip.getClass().equals(PlayerShip.class)) {
                Game.addScore();
                this.dead = true;
            }
            return true;
        }
        if (Saucer.class.equals(other.getClass()) && this.whichShip.getClass().equals(PlayerShip.class)) {
            Game.addScore();
            return true;
        }
        if (PlayerShip.class.equals(other.getClass()) && this.whichShip.getClass().equals(Saucer.class))
            return true;
        return false;
    }
}
