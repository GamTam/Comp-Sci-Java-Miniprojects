package HandacondaBattle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    Handler handler;

    public MouseListener (Handler handler) {
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int key = e.getButton();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getID() == ID.BUTTON) {
                int y = e.getY();
                int x = e.getX();
                System.out.println(x + ", " + y);

                Button button = (Button) tempObject;
                if (key == MouseEvent.BUTTON1 && x >= button.getX() && x <= button.getX() + button.getWidth() && y >= button.getY() && y <= button.getY() + button.getHeight()) button.press();
            }
        }
    }
}
