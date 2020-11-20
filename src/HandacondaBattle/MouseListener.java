package HandacondaBattle;

import HandacondaBattle.Scenes.Scene;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    Game game;

    public int x = -100;
    public int y = -100;

    public MouseListener (Game game) {
        this.game = game;
        game.addMouseMotionListener(this);
    }

    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
