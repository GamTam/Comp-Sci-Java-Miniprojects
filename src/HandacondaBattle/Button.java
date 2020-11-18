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
    private File spritePath;

    public Button(int x, int y, ID id, Game game, String image) throws IOException {
        super(x, y, id, game);

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        spritePath = new File(path + "/sprites/" + image + ".png");
        sprite = ImageIO.read(spritePath);

        center();
    }

    public  void center() {
        x = x - sprite.getWidth() / 2;
        y = y - sprite.getHeight() / 2;
    }

    public void tick() {
    }


    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.drawRect(x, y, sprite.getWidth(), sprite.getHeight());
        g.drawImage(sprite, x, y, sprite.getWidth(), sprite.getHeight(), null);
    }

    public void press() {
        System.out.println("I have been pressed");
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }
}
