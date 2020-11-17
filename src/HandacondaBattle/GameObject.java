package HandacondaBattle;

import java.awt.*;

public abstract class GameObject {

    protected int x, y;
    protected ID id;
    protected int xSpeed, ySpeed;
    protected Game game;

    public GameObject(int x, int y, ID id, Game game) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return  x;
    }

    public  int getY() {
        return y;
    }

    public void setID(ID id) {
        this.id = id;
    }

    public ID getID() {
        return id;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return  ySpeed;
    }
}
