package game2;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static game2.Constants.DELAY;

/**
 * Created by akatke on 20/01/2017.
 */
public class Game {
    public Keys control;
    public List<GameObject> gameObjects; //for game objects
    public List<GameObject> aliveGameObjects; // for storing alive objects
    public PlayerShip ship;
    public static Saucer saucer;
    private static int currentLevel = 1;
    private static int gameScore = 0;
    static boolean saucerSpawned = false; // status of saucer spawned or not

    public Game() {
        // initialising array lists
        gameObjects = new ArrayList<>();
        aliveGameObjects = new ArrayList<>();
        createAsteroid(); // initialising asteroids
        control = new Keys(); // creating controller for player
        ship = new PlayerShip(control); // sending it to the player ship and initialising it as well
        gameObjects.add(ship); // adding to current game objects list
    }

    public static void main(String[] args) { // initialising frame and game update
        Game game = new Game();
        View view = new View(game);
        JEasyFrame mainFrame = new JEasyFrame(view, "Asteroids");
        mainFrame.addKeyListener(game.control);
        while (PlayerShip.shipLives >= -1) {
            game.update();
            view.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void createAsteroid() { // creating asteroids depending on the level
        for (int i = 0; i < currentLevel; i++) {
            gameObjects.add(Asteroid.makeRandomAsteroid());
        }
        PlayerShip.lastTimeSpawned = System.currentTimeMillis(); // after each round giving player spawn protection
    }

    public static void addScore() {  // adding score, each 10k points give player a life
        gameScore += 100;
        if (gameScore % 10000 == 0) {
            PlayerShip.shipLives++;
            SoundManager.play(SoundManager.extraShip);
        }
        System.out.println(gameScore);
    }

    public int getScore() {
        return gameScore;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void update() { // updating all objects state, checking for new bullets, adding alive objects to alivegameobjects arraylist
        // collision detection / creating saucers, power-ups
        synchronized (Game.class) {
            int asteroids = 0;
            aliveGameObjects.clear();
            for (GameObject x : gameObjects) { // if asteroid was hit, divide it to 2 parts
                if (x.getClass() == Asteroid.class) {
                    if (x.dead && x.radius > 20) {
                        gameObjects.add(Asteroid.splitAsteroid(x.radius, x.position.x, x.position.y, x.velocity.x * -1, x.velocity.y * -1));
                        gameObjects.add(Asteroid.splitAsteroid(x.radius, x.position.x, x.position.y, x.velocity.x, x.velocity.y));
                    }
                    if (x.dead) break;
                }
                if (x.dead) break;
            }
            if (ship.bullet != null) { // player bullets
                gameObjects.add(ship.bullet);
                ship.bullet = null;
            }
            if (saucerSpawned) { // saucer bullets
                if (saucer.bullet != null) {
                    gameObjects.add(saucer.bullet);
                    saucer.bullet = null;
                }
            }
            Iterator<GameObject> go = gameObjects.iterator();
            while (go.hasNext()) {  // checking if any asteroids left, if saucer destroyed
                GameObject temp = go.next();
                temp.update();
                if (!temp.dead) {
                    if (temp.getClass() == Asteroid.class) asteroids++;
                    aliveGameObjects.add(temp);
                } else if (temp.dead) {
                    if (temp.getClass() == Saucer.class) {
                        saucerSpawned = false;
                    }
                }
            }
            gameObjects.clear();
            gameObjects.addAll(aliveGameObjects);
            if (asteroids == 0) { // increment of level, creating new asteroids according to it
                currentLevel++;
                createAsteroid();
            }
            if (getScore() > 0) {
                if (getScore() > 10000) { // stronger AI
                    if (getScore() % 2500 == 0) {
                        if (!saucerSpawned) {
                            saucer = new Saucer(new AimNShoot(this));
                            gameObjects.add(saucer);
                            saucerSpawned = true;
                        }
                    }
                } else if (getScore() % 2500 == 0) { // weaker AI
                    if (!saucerSpawned) {
                        saucer = new Saucer(new FirstSaucer());
                        gameObjects.add(saucer);
                        saucerSpawned = true;
                    }
                }
                if (getScore() % 1500 == 0) { // spawning power-ups each 1500 points
                    gameObjects.add(new Items((int) (Math.random() * 4)));
                    addScore();
                }
            }
            try { // collision detection
                for (int i = 0; i < gameObjects.size(); i++) {
                    for (int j = 1; j < gameObjects.size(); j++) {
                        if(!gameObjects.get(i).dead && !gameObjects.get(j).dead) {
                            gameObjects.get(i).collisionHandling(gameObjects.get(j));
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
