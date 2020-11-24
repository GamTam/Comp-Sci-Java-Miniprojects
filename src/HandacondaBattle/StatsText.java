package HandacondaBattle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class StatsText extends GameObject {

    Font font;
    int increaseAmount = 40;

    public StatsText(Game game) throws IOException, FontFormatException {
        super(0, 0, ID.BUTTON, game);

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        font = Font.createFont(Font.TRUETYPE_FONT, new File(path + "/fonts/text.ttf")).deriveFont(40f);

        x = 40;
        y = 0;

        game.handler.addObject(this);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        int tempX = x;
        int tempY = y;

        g.setFont(font);

        y += increaseAmount;
        g.drawString("Games Played: " + game.games, x, y);
        y += increaseAmount;
        g.drawString("Games Won: " + game.gamesWon, x, y);
        y += increaseAmount;
        g.drawString("Games Lost: " + game.gamesLost, x, y);
        y += increaseAmount;
        g.drawString("Games Abandoned: " + (game.games - (game.gamesWon + game.gamesLost)), x, y);
        y += increaseAmount;
        g.drawString("Easy Games: " + game.easyTimes, x, y);
        y += increaseAmount;
        g.drawString("Normal Games: " + game.normalTimes, x, y);
        y += increaseAmount;
        g.drawString("Hard Games: " + game.hardTimes, x, y);

        x = game.getWidth() / 2;
        y = game.getHeight() / 2;

        g.drawString("Mario Games: " + game.marioTimes, x, y);
        y += increaseAmount;
        g.drawString("Luigi Games: " + game.luigiTimes, x, y);
        y += increaseAmount;
        g.drawString("Fawful Games: " + game.fawfulTimes, x, y);
        y += increaseAmount;
        g.drawString("Toadette Games: " + game.toadetteTimes, x, y);
        y += increaseAmount;
        g.drawString("Sans Games: " + game.sansTimes, x, y);
        y += increaseAmount;
        g.drawString("Shy Guy Games: " + game.shyGuyTimes, x, y);

        x = tempX;
        y = tempY;
    }
}
