package GameTest;

import java.awt.*;

public class Player extends GameObject{

    public int size = 32;

    public Player(int x, int y, ID id, Game game) {
        super(x, y, id, game);

        xSpeed = 2;
        ySpeed = 2;
    }

    public void tick() {
        x += xSpeed;
        y += ySpeed;

        if (x <= 0 || x >= game.WIDTH - (size * 1.5)) {
            xSpeed *= -1;
        }

        if (y <= 0 || y >= game.HEIGHT - (size * 2.25)) {
            ySpeed *= -1;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, size, size);
    }
}
