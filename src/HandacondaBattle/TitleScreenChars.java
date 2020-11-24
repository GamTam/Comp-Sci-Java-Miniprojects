package HandacondaBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class TitleScreenChars extends GameObject {

    private LinkedList<BufferedImage> sprite = new LinkedList<>();
    private File spritePath;

    int xSpeed;
    int animSpeed;
    int currentFrame = 0;
    long lastUpdate = 0;
    boolean bottom;

    String name;
    Font font;

    int maxWidth = 0;
    int maxHeight = 0;

    public TitleScreenChars(int x, int y, int xSpeed, ID id, Game game, String image, int animSpeed, boolean bottom) throws IOException, FontFormatException {
        super(x, y, id, game);
        this.xSpeed = xSpeed;
        this.animSpeed = animSpeed;
        this.bottom = bottom;
        this.name = image.replace("/title screen", "");

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        font = Font.createFont(Font.TRUETYPE_FONT, new File(path + "/fonts/text.ttf")).deriveFont(40f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        spritePath = new File(path + "/sprites/" + image + "/");
        int i = 1;
        for (File file : spritePath.listFiles()) {
            file = new File(path + "/sprites/" + image + "/" + i + ".png");
            sprite.add(ImageIO.read(file));

            if (sprite.get(i - 1).getHeight() > maxHeight) {
                maxHeight = sprite.get(i - 1).getHeight();
            }

            if (sprite.get(i - 1).getWidth() > maxWidth) {
                maxWidth = sprite.get(i - 1).getWidth();
            }

            i += 1;
        }

        center();
        game.handler.addObject(this);
    }

    public  void center() {
        x = x - sprite.get(currentFrame).getWidth() / 2;
        y = y - sprite.get(currentFrame).getHeight();
    }

    public void tick() {
        animate();

        x += xSpeed;

        if (x >= game.width + 150) {
            x -= game.width + 300;
        }
    }

    public void animate() {
        long now = System.currentTimeMillis();
        int tempX = x + sprite.get(currentFrame).getWidth() / 2;
        int tempY = y + sprite.get(currentFrame).getHeight();

        if (now - lastUpdate > animSpeed) {
            lastUpdate = now;

            if (currentFrame < sprite.size() - 1) {
                currentFrame += 1;
            } else {
                currentFrame = 0;
            }

            if (bottom) {
                x = tempX - sprite.get(currentFrame).getWidth() / 2;
                y = tempY - sprite.get(currentFrame).getHeight();
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(sprite.get(currentFrame), x, y, sprite.get(currentFrame).getWidth(), sprite.get(currentFrame).getHeight(), null);

        if (mouseOver(game.mouse.getX(), game.mouse.getY())) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, sprite.get(currentFrame).getWidth(), sprite.get(currentFrame).getHeight());

            g.setColor(new Color(252, 255, 0));
            g.setFont(font);
            drawCenteredString(g, name, new Rectangle(x + sprite.get(currentFrame).getWidth() / 2, game.height / 2 + 30, 0, 0), font);
        }
    }

    public boolean mouseOver(int x, int y) {
        return x >= this.x && x <= this.x + getWidth() && y >= this.y && y <= this.y + getHeight();
    }

    public int getCenterX() {
        return x - sprite.get(currentFrame).getWidth() / 2;
    }

    public int getCenterY() {
        return y - sprite.get(currentFrame).getHeight() / 2;
    }

    public int getWidth() {
        return sprite.get(currentFrame).getWidth();
    }

    public int getHeight() {
        return sprite.get(currentFrame).getHeight();
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(capital(text), x, y);
    }

    public String capital(String str){
        String[] word = str.split(" ");

        String capital = "";
        for(String w:word){
            String first = w.substring(0,1);
            String rest = w.substring(1);
            capital += first.toUpperCase() + rest + " ";
        }
        return capital.trim();
    }
}
