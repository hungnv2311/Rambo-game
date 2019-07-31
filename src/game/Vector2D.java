package game;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Phuong thuc | Method
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public void scale(double rate) {
        this.x = this.x*rate;
        this.y += this.y*rate;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D clone() {
        return new Vector2D(this.x, this.y);
    }


    // Still Method | Phuong thuc
    public double getLength() {
        return Math.sqrt(x*x + y*y);
    }

    public void setLength(double length) {
        if(this.getLength() != 0) {
            this.scale(length/ this.getLength());
        }
    }

    public double distanceTo(Vector2D other) {
        return Math.sqrt((Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2)));
    }

    public double getAngle() {
        return Math.atan2(y, x);      // Ham atan2 giup tra ve car nhung goc >90
    }

    public void setAngle(double angle) {

        double l = this.getLength();
        if (l != 0) {
            this.x = l*Math.cos(angle);
            this.y = l*Math.sin(angle);
        }
    }

    public static void main(String[] args) {
        Vector2D v1 = new Vector2D(3, 3);
        System.out.println(v1.getLength() + " " + (3 * Math.sqrt(2)));
        System.out.println(v1.getAngle() + " " + (Math.PI /4));

        v1.setLength(10);
        System.out.println(v1.getLength());
        v1.setAngle(Math.toRadians(60));
        System.out.println(v1.getAngle() + " " + (Math.toRadians(60)));
    }
}
