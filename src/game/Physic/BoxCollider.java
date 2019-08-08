package game.Physic;

import game.GameObject;
import game.Vector2D;

public class BoxCollider {
    public Vector2D position;
    public int width;
    public int height;
    public Vector2D anchor;

//    public BoxCollider(double x, double y, int width, int height) {
//        this.position = new Vector2D(x, y);
//        this.width = width;
//        this.height = height;
//    }

    public BoxCollider(GameObject master, int width, int height) {
        this.position = master.position;
        this.width = width;
        this.height = height;
        this.anchor = master.anchor;
    }

    public void set(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public BoxCollider shift(GameObject master, double dx, double dy) {
        this.position = master.position;
        this.position.add(dx, dy);
        BoxCollider shiftedBoxCollider = new BoxCollider(master, width, height);
        return shiftedBoxCollider;
    }

    public double top() {
        return (position.y - anchor.y * height);
    }

    public double bot() {
        return this.top() + height;
    }

    public double left() {
        return (position.x - anchor.x * width);
    }

    public double right() {
        return this.left() + width;
    }

    public boolean intersects(BoxCollider other) {
        return this.right() >= other.left()
                && this.left() <= other.right()
                && this.bot() >= other.top()
                && this.top() <= other.bot();
    }
}
