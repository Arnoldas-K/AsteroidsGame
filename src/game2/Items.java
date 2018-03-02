package game2;

import utilities.Vector2D;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

/**
 * Created by Arnoldas on 19/03/2017.
 */
public class Items extends GameObject {

    public static final int health = 0, boost = 1, beam = 2;
    Sprite healthImg;
    Sprite boostImg;
    Sprite beamImg;
    Sprite shieldImg;

    int drawing_scale = 3;

    public static int option; // which kind of power up
    Items(int option){ // constructor, random position, schedule this = dead after 5 sec
        this.position = new Vector2D(Math.random()*FRAME_WIDTH, Math.random()*FRAME_HEIGHT);
        this.velocity = new Vector2D(0,0);
        this.dead = false;
        this.radius = 10;
        this.option = option;
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                dead = true;
            }
        }, 5000);
    }

    @Override
    public void draw(Graphics2D g) { // draw according to its type
        g.setColor(Color.white);
        g.fillOval((int) (position.x- (drawing_scale*radius/2)), (int) (position.y - (drawing_scale*radius/2)), (int) (drawing_scale * radius), (int) (drawing_scale * radius));
        switch (option){
            case 0:
                this.healthImg = new Sprite(Sprite.HEALTH, position, velocity, radius*drawing_scale, radius*drawing_scale);
                healthImg.draw(g);
                break;
            case 1:
                this.boostImg = new Sprite(Sprite.BOOST, position, velocity,  radius*drawing_scale, radius*drawing_scale);
                boostImg.draw(g);
                break;
            case 2:
                this.beamImg = new Sprite(Sprite.BEAM, position, velocity,  radius*drawing_scale, radius*drawing_scale);
                beamImg.draw(g);
                break;
            case 3:
                this.shieldImg = new Sprite(Sprite.SHIELD, position, velocity,  radius*drawing_scale, radius*drawing_scale);
                shieldImg.draw(g);
                break;
        }
    }

    @Override
    public boolean canHit(GameObject other) {
        return false;
    }
}
