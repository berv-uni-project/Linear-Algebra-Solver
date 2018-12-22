package linear.algebra.solver;
/*
	ADT Point
*/

public class Point {
    public double x, y;

    // Konstruktor
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Selector
    public double getX() {
        return this.x;
    }

    // Setter
    public void setX(double newX) {
        this.x = newX;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double newY) {
        this.y = newY;
    }

}