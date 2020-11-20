package HandacondaBattle.Scenes;

import HandacondaBattle.*;
import HandacondaBattle.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainMenu extends Scene {
    TitleScreenChars luigi;
    TitleScreenChars mario;
    TitleScreenChars shyGuy;
    TitleScreenChars fawful;
    TitleScreenChars sans;
    TitleScreenChars toadette;

    Button titleScreen;
    Button playButton;

    public MainMenu(Game game, Handler handler, SCENE scene) throws IOException, FontFormatException {
        super(game, handler, scene);

        game.soundtrack.play("title");

        game.addMouseListener(this);
        game.loaded = false;

        new BG(this.game, "Title Screen bg");

        luigi = new TitleScreenChars(((game.width + 100) / 5), game.height / 2, 2, ID.SLIDE, game,"luigi/title screen", 60, true);
        mario = new TitleScreenChars(((game.width + 100) / 5) * 2, game.height / 2, 2, ID.SLIDE, game,"mario/title screen", 60, true);
        shyGuy = new TitleScreenChars(((game.width + 100) / 5) * 3, game.height / 2, 2, ID.SLIDE, game,"shy guy/title screen", 60, true);
        fawful = new TitleScreenChars((((game.width + 100) / 5) * 4), game.height / 2, 2, ID.SLIDE, game,"fawful/title screen", 60, true);
        sans = new TitleScreenChars(0, game.height / 2, 2, ID.SLIDE, game,"sans/title screen", 60, true);
        toadette = new TitleScreenChars((((game.width + 100) / 5) * 5), game.height / 2, 2, ID.SLIDE, game,"toadette/title screen", 100, true);

        titleScreen = new Button(game.width / 2, 75, 2, ID.BUTTON, game,"Title Screen", false);
        playButton = new Button(game.width / 2, game.getHeight() - 125, 2, ID.BUTTON, game,"Play Game", true);

        game.loaded = true;
    }

    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        int x = e.getX();
        int y = e.getY();

        if (key == MouseEvent.BUTTON1) {
            if (playButton.mouseOver(x, y)) {
                this.game.removeMouseListener(this);
                game.handler.addObject(new SceneTransition(SCENE.CharSelect, game));
            } else if (titleScreen.mouseOver(x, y)) {
                game.soundtrack.play("secret");
            } else if (mario.mouseOver(x, y)) {
                game.soundtrack.play("mario");
            } else if (luigi.mouseOver(x, y)) {
                game.soundtrack.play("luigi");
            } else if (shyGuy.mouseOver(x, y)) {
                game.soundtrack.play("shy guy");
            } else if (fawful.mouseOver(x, y)) {
                game.soundtrack.play("fawful");
            } else if (sans.mouseOver(x, y)) {
                game.soundtrack.play("sans");
            } else if (toadette.mouseOver(x, y)) {
                game.soundtrack.play("toadette");
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        game.mouse.x = -100;
        game.mouse.y = -100;
    }
}
