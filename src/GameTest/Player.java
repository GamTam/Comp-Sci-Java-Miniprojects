package GameTest;

import java.awt.*;

public class Player extends GameObject{

    public int size = 32;

    public Player(int x, int y, ID id, Game game) {
        super(x, y, id, game);
    }

    public void tick() {
        x += xSpeed;
        y += ySpeed;
    }

    public void render(Graphics g) {
        if (id == ID.Player) g.setColor(Color.CYAN);
        else if (id == ID.Player2) g.setColor(Color.green);
        g.fillRect(x, y, size, size);
    }
}
