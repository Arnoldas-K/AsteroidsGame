package game2;
import java.awt.event.KeyEvent;
import static game2.Game.saucer;

/**
 * Created by Arnoldas on 19/03/2017.
 */
public class AimNShoot implements Controller {
    Action action = new Action();
    Game game;
    PlayerShip playerShip;

    //Saucer saucer;
    public AimNShoot(Game game) { // constructor
        this.playerShip = game.ship;
        this.game = game;
    }

    @Override
    public Action action() { // gets the distance, sets angle and speed, if target close enough, starts to shoot
        action.shoot = false;
        try {
            double ydist = (playerShip.position.y - saucer.position.y);
            double xdist = (playerShip.position.x - saucer.position.x);
            double angle = Math.atan2(ydist, xdist);
            if (Math.abs(ydist) > 200 || Math.abs(xdist) > 200) {
                saucer.velocity.y = Math.sin(angle) * 100;
                saucer.velocity.x = Math.cos(angle) * 100;
            } else {
                action.shoot = true;
            }
            saucer.direction.y = Math.sin(angle);
            saucer.direction.x = Math.cos(angle);
        } catch (Exception e) {
        }
        return action;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
