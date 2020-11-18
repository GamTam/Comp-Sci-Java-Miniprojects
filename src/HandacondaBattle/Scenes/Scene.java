package HandacondaBattle.Scenes;

import HandacondaBattle.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Scene extends MouseAdapter {

    protected Game game;
    protected SCENE scene;
    protected Handler handler;

    public Scene(Game game, Handler handler, SCENE scene) {
        this.game = game;
        this.scene = scene;
        this.handler = handler;
    }

    public abstract void mousePressed(MouseEvent e);
}
