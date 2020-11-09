package GameTest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;

    public KeyInput (Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getID() == ID.Player) {
                if (key == KeyEvent.VK_W) tempObject.setySpeed(-5);
                else if (key == KeyEvent.VK_S) tempObject.setySpeed(5);
                if (key == KeyEvent.VK_A) tempObject.setxSpeed(-5);
                else if (key == KeyEvent.VK_D) tempObject.setxSpeed(5);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getID() == ID.Player) {
                if (key == KeyEvent.VK_W && tempObject.getySpeed() < 0) tempObject.setySpeed(0);
                else if (key == KeyEvent.VK_S && tempObject.getySpeed() > 0) tempObject.setySpeed(0);
                if (key == KeyEvent.VK_A && tempObject.getxSpeed() < 0) tempObject.setxSpeed(0);
                else if (key == KeyEvent.VK_D && tempObject.getxSpeed() > 0) tempObject.setxSpeed(0);
            }
        }
    }
}
