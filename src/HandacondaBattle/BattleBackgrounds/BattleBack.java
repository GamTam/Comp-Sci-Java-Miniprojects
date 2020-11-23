package HandacondaBattle.BattleBackgrounds;

import HandacondaBattle.Game;
import HandacondaBattle.GameObject;
import HandacondaBattle.ID;

import java.awt.*;

public abstract class BattleBack extends GameObject {
    public BattleBack(Game game) {
        super(0, 0, ID.BATTLEBACK, game);
    }

    public abstract void tick();

    public abstract void render(Graphics g) ;

    public abstract void renderFront(Graphics g);
}
