package game2;

/**
 * Created by akatke on 20/01/2017.
 */

import utilities.Vector2D;
import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Asteroid extends GameObject {
    public static final double MAX_SPEED = 100;

    public static final int large = 70;
    public static final int medium = 40;
    public static final int small = 20;

    private Sprite asteroidImg;

    public Asteroid(double x, double y, double vx, double vy){ // constructor
        this.position = new Vector2D(x, y);
        this.velocity = new Vector2D(vx,vy);
        this.dead = false;
        this.radius = large;
        this.asteroidImg = new Sprite(Constants.ASTEROID1, this.position, this.velocity, this.radius*2, this.radius*2);
    }


	public static Asteroid makeRandomAsteroid() { // for initialising asteroid
        double x = (FRAME_HEIGHT + large + Math.random() * (FRAME_HEIGHT - 2 * large));
        double y = (FRAME_WIDTH + large + Math.random() * (FRAME_WIDTH - 2 * large));
        double vx = (Math.random()*MAX_SPEED);
        double vy = (Math.random()*MAX_SPEED);

        //get random number between max speed and minimum speed

        Asteroid asteroid = new Asteroid(x, y, vx, vy);
        return asteroid;
    }

    public static Asteroid splitAsteroid(double currentSize, double xThis, double yThis, double vxThis, double vyThis) { // after asteroid collision, creating a new, smaller, asteroid
        Asteroid asteroid = new Asteroid(xThis, yThis, vxThis, vyThis);
        if(currentSize == large) { asteroid.radius = medium; }
        else if(currentSize == medium){ asteroid.radius = small; }
        asteroid.asteroidImg = new Sprite(Constants.ASTEROID1, asteroid.position, asteroid.velocity, asteroid.radius*2, asteroid.radius*2);
        return asteroid;
    }

     public void update(){ // according to velocity calculates new position
         position.x += velocity.x * DT;
         position.y += velocity.y * DT;
         position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
     }

     public void draw(Graphics2D g){ // drawing method, draw sprite, otherwise just a circle
         g.setColor(Color.red);
         try {
             asteroidImg.draw(g);
         } catch (Exception e){
             g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int)(2 * radius),(int) (2 * radius));
         }
     }

    public boolean canHit(GameObject other){ // for checking if it can collide with given object
        if(System.currentTimeMillis() - PlayerShip.lastTimeSpawned > 5000) { if(other.getClass().equals(PlayerShip.class)) return true; }
        if(other.getClass().equals(Bullet.class)){return true;}
        return false;
    }
}
