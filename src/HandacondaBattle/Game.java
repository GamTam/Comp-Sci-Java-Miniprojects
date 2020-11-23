package HandacondaBattle;

import HandacondaBattle.Scenes.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int width = 815, height = (int) (width / 12 * 9.5);

    private Thread thread;
    private boolean running = false;

    public Handler handler;
    public Random random;
    public Soundtrack soundtrack = new Soundtrack("snif city", "origami king boss", "victory SS", "darkness falls", "stabby stabby souls", this);

    public SCENE scene = SCENE.MainMenu;
    public SCENE prevScene = SCENE.MainMenu;
    public boolean loaded = false;

    public MouseListener mouse;

    public Window window = new Window(width, height, "Super Roshambo", this);

    public Battle battle;

    public String playerChar;
    public String opponentChar;
    public String difficulty;

    public SCENE getID() {
        return scene;
    }

    public Game() throws IOException, LineUnavailableException, UnsupportedAudioFileException, FontFormatException {
        handler = new Handler(this);
        random = new Random();

        handler.addObject(soundtrack);
        mouse = new MouseListener(this);
        SceneTransition t = new SceneTransition(SCENE.MainMenu, this);
        t.setX(-60);
        handler.addObject(t);
        soundtrack.play("title");
        new MainMenu(this, handler, SCENE.MainMenu);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1 && delta <= 50) {
                try {
                    tick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                delta--;
            }

            while (delta >= 1) {
                delta --;
            }

            if (running) {
                render();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }

        stop();
    }

    private void tick() throws IOException, FontFormatException, LineUnavailableException, UnsupportedAudioFileException {
        if (scene != prevScene) {
            prevScene = scene;
            handler.clearAll();

            if (scene == SCENE.MainMenu) {
                new MainMenu(this, handler, SCENE.MainMenu);
            } else if (scene == SCENE.CharSelect) {
                new CharSelect(this, handler, SCENE.CharSelect);
            } else if (scene == SCENE.Game) {
                do {
                    int num = random.nextInt(6);

                    if (num == 1) {
                        opponentChar = "Mario";
                    } else if (num == 2) {
                        opponentChar = "Luigi";
                    } else if (num == 3) {
                        opponentChar = "Fawful";
                    } else if (num == 4) {
                        opponentChar = "Shy Guy";
                    } else if (num == 5) {
                        opponentChar = "Sans";
                    } else {
                        opponentChar = "Toadette";
                    }
                } while (opponentChar.equalsIgnoreCase(playerChar));

                battle = new Battle(this, handler, SCENE.Game);
            }
        }

        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);

        g.fillRect(0, 0, width, height);
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, FontFormatException {
        new Game();
    }
}
