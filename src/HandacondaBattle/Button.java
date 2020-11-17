package HandacondaBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Button extends GameObject{

    private BufferedImage sprite;
    private File spritePath;

    public Button(int x, int y, ID id, Game game, String image) throws IOException {
        super(x, y, id, game);

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        spritePath = new File(path + "/sprites/" + image + ".png");
        sprite = ImageIO.read(spritePath);
    }

    public void tick() {

    }


    public void render(Graphics g) {
        int realX = x - sprite.getWidth() / 2;
        int realY = y - sprite.getHeight() / 2;

        g.drawImage(sprite, realX, realY, sprite.getWidth(), sprite.getHeight(), null);
    }
}
