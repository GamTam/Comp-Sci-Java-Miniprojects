package HandacondaBattle.Scenes;

import HandacondaBattle.*;
import HandacondaBattle.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class StatsScene extends Scene {
    Button back;

    StatsText stats;

    public StatsScene(Game game, Handler handler) throws IOException, FontFormatException {
        super(game, handler, SCENE.Stats);

        game.loaded = false;
        if (game.soundtrack.getsongPlaying() == null) {
            game.soundtrack.play("title");
        }

        new BG(this.game, "charselect bg", true, false);

        back = new Button(127, 565, 0, ID.BUTTON, this.game, "charselect/back", true);
        stats = new StatsText(this.game);

        game.loaded = true;
        game.addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        int x = e.getX();
        int y = e.getY();

        if (key == MouseEvent.BUTTON1) {
            if (back.mouseOver(x, y)) {
                this.game.removeMouseListener(this);
                game.handler.clearTrans();
                game.handler.addObject(new SceneTransition(SCENE.MainMenu, game));
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        game.mouse.x = -100;
        game.mouse.y = -100;
    }
}
