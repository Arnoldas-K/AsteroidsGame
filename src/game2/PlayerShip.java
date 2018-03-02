package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

/**
 * Created by akatke on 03/02/2017.
 */
public class PlayerShip extends Ship {

    public static int shipLives;
    static long lastTimeSpawned;

    // controller which provides an Action object in each frame
    int bulletRadius = 5;
    static double MAG_ACC = 500;

    public PlayerShip(Controller ctrl) { // constructor, tuning settings, gettime lastTimeSpawned for protection of ship
        this.ctrl = ctrl;
        this.position = new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2);
        this.velocity = new Vector2D(0, 0);
        this.direction = new Vector2D(0, -1);
        this.color = Color.blue;
        this.radius = 10;
        this.bulletInterval = 300;
        this.dead = false;
        this.shipLives = 3;
        lastTimeSpawned = System.currentTimeMillis();

    }

    public void draw(Graphics2D g) {
        if(System.currentTimeMillis() - PlayerShip.lastTimeSpawned < 5000) {
            g.setColor(Color.YELLOW);
            g.fillOval((int)(position.x - (radius*DRAWING_SCALE/2)),(int)(position.y - (radius*DRAWING_SCALE/2)),(int) (radius*DRAWING_SCALE),(int) (radius*DRAWING_SCALE));
        }
        this.shipImg = new Sprite(Sprite.SHIP1, this.position, this.direction, this.radius*DRAWING_SCALE, this.radius*DRAWING_SCALE);
        shipImg.draw(g);
        if (action1.thrust == 1) { SoundManager.startThrust(); } else { SoundManager.stopThrust(); }
    }

    public void mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity).normalise().addScaled(direction, 500), this, bulletRadius);
        SoundManager.fire();
    }

    public boolean canHit(GameObject other){ // if can collide with other objects
        if(System.currentTimeMillis() - PlayerShip.lastTimeSpawned > 5000) { // if not under protection
            if (other.getClass().equals(Asteroid.class)) return true;
            if (other.getClass().equals(Saucer.class)) return true;
        }
        if(other.getClass().equals(Items.class)){
            Timer time = new Timer();
            switch (Items.option){
                case 0: // +1 HP
                    shipLives++;
                    break;
                case 1: // BOOST
                    PlayerShip.MAG_ACC += 150;
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            PlayerShip.MAG_ACC -= 150;
                        }
                    }, 5000);
                    break;
                case 2: // bigger bullet radius
                    this.bulletRadius = 40;
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            bulletRadius = 5;
                        }
                    }, 5000);
                    break;
                case 3: // SHIELD
                    lastTimeSpawned = System.currentTimeMillis();
                    break;
            }
            return true;
        }
        return false;
    }
}