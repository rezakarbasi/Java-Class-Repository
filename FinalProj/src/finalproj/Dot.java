/**
 * @author Reza Karbasi
 *
 * This class is indicates an object that have 2 parameters includes X & Y.
 */
package finalproj;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import jdk.nashorn.internal.ir.BreakNode;

public class Dot {

    private double x;
    private double y;

    /**
     * This is the constructor of this class that get the input of this object.
     *
     * @param a indicates x of this object
     * @param b indicates y of this object
     */
    public Dot(double a, double b) {
        x = a;
        y = b;
    }

    /**
     * This method is say distance between two dots
     *
     * @param i the way to this dot.
     * @return distance between this two dots
     */
    public double distance(Dot i) {
        return (Math.pow(Math.pow(x - i.x, 2) + Math.pow(y - i.y, 2), 0.5));
    }

    /**
     * convert data of this object to string to be show.
     *
     * @return the string that indicates data of this object.
     */
    @Override
    public String toString() {
        return (" ( " + getX() + " , " + getY() + " ) ");
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

}
