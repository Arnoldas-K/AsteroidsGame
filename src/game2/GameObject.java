package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

/**
 * Created by akatke on 24/02/2017.
 */
public abstract class GameObject {
    public Vector2D position, velocity;
    public boolean dead;
    public double radius;

    public void hit() { // collision action according to a object type
        if(this.getClass() == PlayerShip.class){ // reducing lives, when out of lives - game over
            if(System.currentTimeMillis() - PlayerShip.lastTimeSpawned > 5000) { // Spawn protection
                if (PlayerShip.shipLives >= 1) {
                    PlayerShip.shipLives--;
                    PlayerShip.lastTimeSpawned = System.currentTimeMillis();
                    this.position = new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2);
                    this.velocity = new Vector2D(0, 0);
                    System.out.println(PlayerShip.shipLives + " - lives left");
                    SoundManager.stopThrust();
                    SoundManager.ship();
                } else {
                    SoundManager.stopThrust();
                    SoundManager.ship();
                    PlayerShip.shipLives--;
                    this.dead = true;
                }
            }
        }
        if(this.getClass() == Asteroid.class) {
            this.dead = true;
            if(this.radius == Asteroid.large) {
                SoundManager.play(SoundManager.bangLarge);
            } else if(this.radius == Asteroid.medium){
                SoundManager.play(SoundManager.bangMedium);
            } else {
                SoundManager.play(SoundManager.bangSmall);
            }
        }
        if(this.getClass() == Bullet.class){
            this.dead = true;
        }
        if(this.getClass() == Saucer.class){
            this.dead = true;
            SoundManager.play(SoundManager.bangMedium);
        }
        if(this.getClass() == Items.class){
            this.dead = true;
            SoundManager.play(SoundManager.extraShip);
        }
    }

    public boolean overlap(GameObject other) { // if objects collides
        Ellipse2D.Double first = new Ellipse2D.Double((this.position.x - this.radius), (this.position.y - this.radius), (this.radius * 2), (this.radius * 2));
        Ellipse2D.Double second = new Ellipse2D.Double((other.position.x - other.radius), (other.position.y - other.radius), (other.radius * 2), (other.radius * 2));
        return first.getBounds2D().intersects(second.getBounds2D());
    }

    public void collisionHandling(GameObject other) { // collision checking
        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if(this.canHit(other)) other.hit();
            if(other.canHit(this)) this.hit();
        }
    }

    public void update() { //
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public abstract void draw(Graphics2D g); // abstract draw method

    public abstract boolean canHit(GameObject other); // abstract canHit method, for checking if given objects can collide
}
