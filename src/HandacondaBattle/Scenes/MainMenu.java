package HandacondaBattle.Scenes;

import HandacondaBattle.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainMenu extends Scene {
    Button luigi;

    public MainMenu(Game game, Handler handler, SCENE scene) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        super(game, handler, scene);

        game.handler.clearAll();
        this.game.addMouseListener(this);

        luigi = new Button(-50, game.height / 2, ID.BUTTON, game,"luigi");
        game.handler.addObject(luigi);
        game.handler.addObject(game.soundtrack);

        game.soundtrack.play("game light");
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (luigi.mouseOver(x, y)) {
            game.scene = SCENE.CharSelect;
            this.game.removeMouseListener(this);
        }

        System.out.println(game.scene);
    }

}
