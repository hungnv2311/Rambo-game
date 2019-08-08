package game.Physic;

import game.GameObject;
import game.Vector2D;

public class NewBoxCollider {
    public Vector2D position;
    public int width;
    public int height;
    public Vector2D anchor;

    public NewBoxCollider(Vector2D position, Vector2D anchor, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.anchor = anchor;
    }

    public void set(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public NewBoxCollider shift(double dx, double dy) {
        Vector2D newposition = this.position.clone();
        newposition.add(dx, dy);
        NewBoxCollider shiftedBoxCollider = new NewBoxCollider(newposition, this.anchor, this.width, this.height);
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
