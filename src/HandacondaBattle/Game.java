package HandacondaBattle;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int width = 800, height = width / 12 * 9;

    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private Random random;

    public Game() throws IOException {
        handler = new Handler();
        random = new Random();

        this.addKeyListener(new KeyInput(handler));
        new Window(width, height, "Rock Paper Scissors", this);
        handler.addObject(new Button(width / 2, height / 2, ID.Button, this,"luigi"));
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
                tick();
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

    private void tick() {
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

    public static void main(String[] args) throws IOException {
        new Game();
    }
}
