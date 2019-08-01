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
//
//    public static void main(String[] args) {
//        BoxCollider rect1 = new BoxCollider(1, 1,1,1);
//        BoxCollider rect2 = new BoxCollider(1, 1,2,2);
//        BoxCollider rect3 = new BoxCollider(4, 4,2,2);
//
//        System.out.println(rect1.intersects(rect2));  // true
//        System.out.println(rect1.intersects(rect3));  // false
//        System.out.println(rect2.intersects(rect3));  // false
//    }
}
