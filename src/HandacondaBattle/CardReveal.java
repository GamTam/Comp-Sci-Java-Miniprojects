package HandacondaBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class CardReveal extends GameObject{
    public BufferedImage back;
    public BufferedImage rock;
    public BufferedImage paper;
    public BufferedImage scissors;

    public BufferedImage activeSprite;
    public BufferedImage otherSide;

    public int xDest;
    public int yDest;

    public double deltaX;
    public double deltaY;
    double dir;

    int speed = 10;
    float scale = 1;

    public boolean small;
    
    public boolean flipping;

    public CardReveal(int x, int y, Game game) throws IOException {
        super(x, y, ID.CARDREVEAL, game);
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        xDest = x;
        yDest = y;

        back = ImageIO.read(new File(path + "/sprites/cards/back.png"));
        rock = ImageIO.read(new File(path + "/sprites/cards/rock selected.png"));
        paper = ImageIO.read(new File(path + "/sprites/cards/paper selected.png"));
        scissors = ImageIO.read(new File(path + "/sprites/cards/scissors selected.png"));

        activeSprite = back;
        otherSide = rock;
        center();

        game.handler.addObject(this);
    }

    public void center() {
        x = x - activeSprite.getWidth() / 2;
        y = y - activeSprite.getHeight() / 2;
    }

    public  void unCenter() {
        x = x + activeSprite.getWidth() / 2;
        y = y + activeSprite.getHeight() / 2;
    }

    public void goToPoint(int x, int y) {
        xDest = x;
        yDest = y;

        deltaX = xDest - this.x;
        deltaY = yDest - this.y;

        dir = Math.atan(deltaY / deltaX);
    }

    public void tick() {
        unCenter();

        if (!inRange(x, xDest)) {
            x = (int) (x + (speed * Math.cos(dir)));
        } else {
            x = xDest;
        }

        if (!inRange(y, yDest)) {
            y = (int) (y + (speed * Math.sin(dir)));
        } else {
            y = yDest;
        }

        center();
        
        if (flipping) {
            if (small) {
                scale -= 0.1;
                if (scale <= 0) {
                    scale = 0.1f;
                    activeSprite = otherSide;
                    small = false;
                }
            } else {
                scale += 0.1;
                if (scale >= 1) {
                    scale = 1;
                }
            }
        }
    }

    public void render(Graphics g) {
        int tempX = x;
        int tempY = y;

        unCenter();
        x -= activeSprite.getWidth() * scale / 2;
        y -= activeSprite.getHeight() / 2;

        g.drawImage(activeSprite, x, y, (int) (activeSprite.getWidth() * scale), activeSprite.getHeight(), null);

        x = tempX;
        y = tempY;
    }

    public boolean inRange(int current, int dest) {
        boolean t = false;

        if (current <= dest + speed && current >= dest - speed) {
            t = true;
        }

        return t;
    }
}
