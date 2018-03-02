package game2;

import utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

/**
 * Created by akatke on 20/01/2017.
 */
public class Constants {
    public static final int FRAME_HEIGHT = 540;
    public static final int FRAME_WIDTH = 960;
    public static final Dimension FRAME_SIZE = new Dimension(
                        Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    // sleep time between two frames
    public static final int DELAY = 20;  // in milliseconds
    public static final double DT = DELAY / 1000.0;  // in seconds

    public static Image ASTEROID1, MILKYWAY1;
    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
        } catch (IOException e) { e.printStackTrace(); }
    }

}
