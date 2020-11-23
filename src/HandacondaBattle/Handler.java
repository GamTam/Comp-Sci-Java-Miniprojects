package HandacondaBattle;

import HandacondaBattle.BattleBackgrounds.BattleBack;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Handler {
    LinkedList<GameObject> object = new LinkedList<>();
    Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public void getObjects() {
        for (GameObject obj: object) {
            System.out.println(obj);
        }
    }

    public void tick() throws IOException {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }

        if (game.scene == SCENE.Game && game.battle != null) {
            game.battle.tick();
        }
    }

    public void render(Graphics g) {
        List<GameObject> trans = new ArrayList<>();
        List<BattleBack> bgs = new ArrayList<>();
        List<CardReveal> cr = new ArrayList<>();
        List<BattleChars> bc = new ArrayList<>();

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            if (tempObject.getID() != ID.TRANS) {
                tempObject.render(g);
            } else {
                trans.add(tempObject);
            }

            if (tempObject.getID() == ID.BATTLEBACK) {
                bgs.add((BattleBack) tempObject);
            }

            if (tempObject.getID() == ID.BATTLECHAR) {
                assert tempObject instanceof BattleChars;
                bc.add((BattleChars) tempObject);
            }

            if (tempObject.getID() == ID.CARDREVEAL) {
                assert tempObject instanceof CardReveal;
                cr.add((CardReveal) tempObject);
            }
        }

        for (BattleBack obj : bgs) {
            obj.renderFront(g);
        }

        for (BattleChars b : bc) {
            b.renderFront(g);
        }

        for (CardReveal b : cr) {
            b.render(g);
        }

        for (GameObject obj : trans) {
            obj.render(g);
        }
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

    public void clearTrans() {
        GameObject trans = null;

        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            if (tempObject.getID() == ID.TRANS) {
                trans = tempObject;
            }
        }

        if (trans != null) removeObject(trans);
    }
}
