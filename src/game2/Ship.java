package game2;

import utilities.Vector2D;

import java.awt.*;

import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

/**
 * Created by Arnoldas on 19/03/2017.
 */
public abstract class Ship extends GameObject {
    // rotation velocity in radians per second
    static final double STEER_RATE = 2 * Math.PI;
    // acceleration when thrust is applied
    // constant speed loss factor
    static final double DRAG = 0.01;
    static final double DRAWING_SCALE = 5;
    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    Vector2D direction;

    Controller ctrl;
    Action action1;
    Color color;
    Bullet bullet = null;
    long lastBulletShot;
    int bulletInterval;
    Sprite shipImg;

    public void update() { // update for player ship and saucer
        action1 = ctrl.action();
        direction.rotate(action1.turn * STEER_RATE * DT);
        // scaling speed
        if(this.getClass() == PlayerShip.class){
            velocity.addScaled(direction, PlayerShip.MAG_ACC * DT * action1.thrust);
        }
        if(this.getClass() == Saucer.class){
                velocity.addScaled(direction, Saucer.SMAG_ACC * DT * action1.thrust);
        }
        position.addScaled(velocity, Constants.DT);
        velocity.mult(1 - DRAG);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT); // wrapping ship in bounds
        if (action1.shoot && System.currentTimeMillis() - lastBulletShot > bulletInterval) {
            mkBullet();
            lastBulletShot = System.currentTimeMillis(); // getting last bullet time for limited bullets
            action1.shoot = false;
        }
    }

    // bullets
    public abstract void mkBullet() ;

}
