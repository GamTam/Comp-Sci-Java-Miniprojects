package HandacondaBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class WinText extends GameObject{

    BufferedImage sprite;

    float scale = 0;

    public WinText(Game game, String sprite) throws IOException {
        super(game.getWidth() / 2, 150, ID.BUTTON, game);

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        this.sprite = ImageIO.read(new File(path + "/sprites/" + sprite + ".png"));

        center();
        game.handler.addObject(this);
    }

    public void center() {
        x = x - sprite.getWidth() / 2;
        y = y - sprite.getHeight() / 2;
    }

    public  void unCenter() {
        x = x + sprite.getWidth() / 2;
        y = y + sprite.getHeight() / 2;
    }

    public void tick() {
        if (scale < 1) {
            scale += 0.2;
        } else {
            scale = 1;
        }
    }

    public void render(Graphics g) {
        int tempX = x;
        int tempY = y;

        unCenter();
        x -= sprite.getWidth() * scale / 2;
        y -= sprite.getHeight() * scale / 2;

        g.drawImage(sprite, x, y, (int) (sprite.getWidth() * scale), (int) (sprite.getHeight() * scale), null);

        x = tempX;
        y = tempY;
    }
}
