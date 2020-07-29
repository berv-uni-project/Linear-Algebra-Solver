package linear.algebra.solver;

/**
 * Point class
 * Data structure
 */
public class Point {
    private double x, y;

    /**
     * Default constructor, create (0.0,0.0)
     */
    Point() {

    }

    /**
     * Alternate constructor defined by (x,y)
     * @param x x point in double
     * @param y y point in double
     */
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X getter
     * @return return X
     */
    public double getX() {
        return this.x;
    }

    /**
     * Y Getter
     * @return returning Y in double
     */
    public double getY() {
        return this.y;
    }

}