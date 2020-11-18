package HandacondaBattle;

import HandacondaBattle.Scenes.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int width = 800, height = width / 12 * 9;

    private Thread thread;
    private boolean running = false;

    public Handler handler;
    private Random random;
    public Soundtrack soundtrack = new Soundtrack("Snif City", "king bowser", "victory SS", "darkness falls", this);

    public SCENE scene = SCENE.MainMenu;
    public SCENE prevScene = SCENE.MainMenu;

    public SCENE getID() {
        return scene;
    }

    public Game() throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
        handler = new Handler();
        random = new Random();

        this.addKeyListener(new KeyInput(handler));
        new Window(width, height, "Handaconda Battle", this);
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

            while (delta >= 1) {
                try {
                    tick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                delta--;
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

    private void tick() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (scene != prevScene) {
            prevScene = scene;

            if (scene == SCENE.MainMenu) {
                new MainMenu(this, handler, SCENE.MainMenu);
            }
        }

        if (scene == SCENE.MainMenu) {
            handler.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        if (scene == SCENE.MainMenu) {
            g.setColor(Color.black);
        } else if (scene == SCENE.CharSelect) {
            g.setColor(Color.white);
        }

        g.fillRect(0, 0, width, height);
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
        new Game();
    }
}
