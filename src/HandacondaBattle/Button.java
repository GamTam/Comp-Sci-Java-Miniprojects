package HandacondaBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Button extends GameObject {

    private BufferedImage sprite;
    private BufferedImage selectSprite;
    private BufferedImage activeSprite;
    private File spritePath;

    int xSpeed;
    boolean selectedFrame;

    public Button(int x, int y, int xSpeed, ID id, Game game, String image, boolean selectedFrame) throws IOException {
        super(x, y, id, game);
        this.xSpeed = xSpeed;
        this.selectedFrame = selectedFrame;

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        if (selectedFrame) {
            spritePath = new File(path + "/sprites/" + image + " Unselected.png");
            sprite = ImageIO.read(spritePath);
            activeSprite = ImageIO.read(spritePath);
            spritePath = new File(path + "/sprites/" + image + " selected.png");
            selectSprite = ImageIO.read(spritePath);
        } else {
            spritePath = new File(path + "/sprites/" + image + ".png");
            activeSprite = ImageIO.read(spritePath);
        }

        center();
        game.handler.addObject(this);
    }

    public  void center() {
        x = x - activeSprite.getWidth() / 2;
        y = y - activeSprite.getHeight() / 2;
    }

    public void tick() {
        if (id == ID.SLIDE) {
            x += xSpeed;

            if (x >= game.width + activeSprite.getWidth()) {
                x = 0 - activeSprite.getWidth();
                /*y += 5;
                if (y >= game.height + (sprite.getHeight() / 2)) {
                    y = -sprite.getHeight() / 2 + 10;
                }*/
            }
        }

        if (selectedFrame) {
            if (!mouseOver(game.mouse.getX(), game.mouse.getY())) {
                activeSprite = sprite;
            } else {
                activeSprite = selectSprite;
            }
        }
    }


    public void render(Graphics g) {
        g.drawImage(activeSprite, x, y, activeSprite.getWidth(), activeSprite.getHeight(), null);
    }

    public boolean mouseOver(int x, int y) {
        return  x >= this.x && x <= this.x + getWidth() && y >= this.y && y <= this.y + getHeight();
    }

    public int getWidth() {
        return activeSprite.getWidth();
    }

    public int getHeight() {
        return activeSprite.getHeight();
    }
}
