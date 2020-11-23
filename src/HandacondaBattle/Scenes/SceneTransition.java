package HandacondaBattle.Scenes;

import HandacondaBattle.*;

import java.awt.*;

public class SceneTransition extends GameObject {

    SCENE scene;
    int speed = 30;
    public int time = 0;
    int maxTime = 150;
    int time2 = 0;
    int width;
    int height;

    public SceneTransition(SCENE scene, Game game) {
        super(0, 0, ID.TRANS, game);
        this.scene = scene;
        x = (int) -(game.width * 1.5);
        width = (int) (game.width * 1.5);
        height = game.height;
    }

    public void tick() {
        if (x <= -60) {
            x += speed;
        } else {
            time += 1;
            if (time > maxTime && game.scene != scene) {
                game.scene = scene;
            }
        }

        if (game.scene == scene && time > maxTime && game.loaded) {
            time2 += 1;
            if (time2 > 60) {
                x += speed;
                if (x >= game.width + 30) {
                    game.handler.removeObject(this);
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
    }
}
