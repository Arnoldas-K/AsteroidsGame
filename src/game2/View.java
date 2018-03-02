package game2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by akatke on 20/01/2017.
 */
public class View extends JComponent {
    public static final Color BG_COLOR = Color.black;
    private Game game;
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;

    public View(Game game){ // applying background image
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }

    @Override
    public void paintComponent(Graphics g0){ // game hud, drawing all the objects of the game
        Graphics2D g = (Graphics2D) g0;
        g.setColor(BG_COLOR);
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(im, bgTransf, null);
        g.setColor(Color.GREEN);
        g.drawString("Your score: " + String.valueOf(game.getScore()), 15,10);
        g.drawString("Current level: " + String.valueOf(game.getCurrentLevel()), 15,20);
        g.drawString("Lives left: " + String.valueOf(PlayerShip.shipLives), 15, 30);
        if(PlayerShip.shipLives == -1){ g.setFont(new Font("Tahoma", Font.BOLD, 30)); g.drawString("GAME OVER", getWidth()/2-100, getHeight()/2);}
        synchronized (Game.class) {
            for (GameObject x : game.gameObjects) {
                x.draw((Graphics2D) g0);
            }
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
