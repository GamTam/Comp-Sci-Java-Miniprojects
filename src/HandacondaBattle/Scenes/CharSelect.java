package HandacondaBattle.Scenes;

import HandacondaBattle.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CharSelect extends Scene {
    Button luigi;

    public CharSelect(Game game, Handler handler, SCENE scene) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        super(game, handler, scene);

        this.game.addMouseListener(this);
        game.loaded = false;

        luigi = new Button(game.width / 2, game.height / 2, 5, ID.SLIDE, game,"bleck", false);
        game.handler.addObject(luigi);

        game.soundtrack.play("game heavy");
        game.loaded = true;
    }

    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        int x = e.getX();
        int y = e.getY();

        if (key == MouseEvent.BUTTON1) {
            if (luigi.mouseOver(x, y)) {
                this.game.removeMouseListener(this);
                game.handler.addObject(new SceneTransition(SCENE.MainMenu, game));
            }
        }
    }

}
