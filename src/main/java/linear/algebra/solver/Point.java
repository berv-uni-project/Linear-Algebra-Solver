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
    public double GetX() {
        return this.x;
    }

    public double GetY() {
        return this.y;
    }

    // Setter
    public void SetX(double newX) {
        this.x = newX;
    }

    public void SetY(double newY) {
        this.y = newY;
    }

}