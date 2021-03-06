package HandacondaBattle.Scenes;

import HandacondaBattle.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

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

    public void mouseMoved(MouseEvent e) {
        System.out.println(e.getX() + ", " + e.getY());
    }
}
