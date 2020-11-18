package HandacondaBattle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class Handler {
    LinkedList<GameObject> object = new LinkedList<>();

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void clearAll() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        GameObject sound = new Soundtrack("Snif City", "origami king boss", "victory SS", "darkness falls", null);

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            
            if (tempObject.getID() == ID.SOUNDTRACK) {
                sound = tempObject;
            }
        }
        
        object = new LinkedList<>();
        
        addObject(sound);
    }
}
