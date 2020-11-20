package HandacondaBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class BG extends GameObject  {

    BufferedImage image;

    public BG(Game game, String image) throws IOException {
        super(0, 0, ID.BG, game);

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        File file = new File(path + "/sprites/backgrounds/" + image + ".png");
        this.image = ImageIO.read(file);
        game.handler.addObject(this);
    }

    public void tick() {
        //
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }
}
