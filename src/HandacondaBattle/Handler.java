package HandacondaBattle;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList<GameObject> object = new LinkedList<>();

    public void getObjects() {
        for (GameObject obj: object) {
            System.out.println(obj);
        }
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        GameObject trans = null;

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            if (tempObject.getID() != ID.TRANS) {
                tempObject.render(g);
            } else {
                trans = tempObject;
            }
        }

        if (trans != null) trans.render(g);
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void clearAll() {
        GameObject sound = null;
        GameObject trans = null;

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            
            if (tempObject.getID() == ID.SOUNDTRACK) {
                sound = tempObject;
            } else if (tempObject.getID() == ID.TRANS) {
                trans = tempObject;
            }
        }

        object = new LinkedList<>();

        addObject(sound);
        if (trans != null) addObject(trans);
    }
}
