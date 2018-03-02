package game2;

import java.awt.event.KeyEvent;

import static game2.Game.saucer;

/**
 * Created by Arnoldas on 19/03/2017.
 */
public class FirstSaucer implements Controller {
    Action action = new Action();
    //Game game;
    FirstSaucer() {

    }

    @Override
    public Action action() { // AI for saucer / random movement
        action.shoot = true;
        action.turn = (int) (Math.random()*2) -1;
        action.thrust = 1;
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