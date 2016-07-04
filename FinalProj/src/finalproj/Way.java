/**
 * @author Reza Karbasi
 *
 * This class indicates an array that show a way in screen.
 *
 */
package finalproj;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Way {

    private int[] way;
    private ArrayList<Dot> points;
    private double distance;
    private boolean enterd;
    private Line[] lines;

    /**
     * This is constructor of this class
     *
     * @param d indicates the array of path that this object shows
     * @param inputDots this array list indicates dots that we want find the
     * best way within them
     * @throws finalproj.EnterInputsException this will throw when priority
     * length and array list doesn't in the same length
     */
    public Way(int[] d, ArrayList<Dot> inputDots) throws EnterInputsException {
        if (d.length != inputDots.size()) {
            throw new EnterInputsException();
        } else {
            way = d;
            points = inputDots;
            distance = 0.0;
            enterd = true;
            set_distance();
            Create_Path();
        }
    }

    /**
     * This is constructor of this class
     *
     * @param inputDots this array list indicates dots that we want find the
     * best way within them
     */
    public Way(ArrayList<Dot> inputDots) {
        points = inputDots;
        enterd = false;
        distance = 9999999.9;
    }

    /**
     * This method finds the distance that this array shows
     */
    private void set_distance() {
        int[] k = new int[getQueue().length];
        distance = 0.0;
        for (int i = 1; i < getQueue().length; i++) {
            distance += getPoints().get(way[i]).distance(getPoints().get(way[i - 1]));
        }
        distance += getPoints().get(getQueue()[getPoints().size() - 1]).distance(getPoints().get(0));
        enterd = true;
    }

    /**
     * convert data of this object to string to be show.
     *
     * @return the string that indicates data of this object.
     */
    @Override
    public String toString() {
        String out = "Distance =" + distance + "   Path : " + getPoints().get(0);
        for (int i = 1; i < getPoints().size(); i++) {
            out += " -> " + getPoints().get(way[i]);
        }
        return out;
    }

    /**
     * This method create the lines of object's path
     */
    private void Create_Path() {
        lines = new Line[getPoints().size()];
        for (int i = 1; i < getLines().length; i++) {
            lines[i] = new Line(getPoints().get(way[i]).getX(), getPoints().get(way[i]).getY(), getPoints().get(way[i - 1]).getX(), getPoints().get(way[i - 1]).getY());
        }
        lines[0] = new Line(getPoints().get(way[getLines().length - 1]).getX(), getPoints().get(way[getLines().length - 1]).getY(), getPoints().get(way[0]).getX(), getPoints().get(way[0]).getY());

    }

    /**
     * This method compare two ways be their distance
     *
     * @param in the path that will be compare
     * @return the result of the compare
     */
    public int compare2(Way in) {
        if (in.isEnterd() && isEnterd() && in.distance > distance) {
            return -1;
        } else if (in.isEnterd() && isEnterd() && in.distance < distance) {
            return 1;
        }
        return 0;
    }

    /**
     * @return the way
     */
    public int[] getQueue() {
        return way;
    }

    /**
     * @param way the way to set
     * @throws finalproj.EnterInputsException this will throw when priority
     * length and array list doesn't in the same length
     */
    public void setWay(int[] way) throws EnterInputsException {
        if (way.length != points.size()) {
            throw new EnterInputsException();
        } else {
            this.way = way;
            set_distance();;
            enterd = true;
            Create_Path();
        }
    }

    /**
     * @param points the points to set
     * @throws finalproj.EnterInputsException this will throw when priority
     * length and array list doesn't in the same length
     */
    public void setPoints(ArrayList<Dot> points) throws EnterInputsException {
        if (points.size() != way.length) {
            throw new EnterInputsException();
        } else {
            this.points = points;
            if (isEnterd()) {
                set_distance();
            }
        }

    }

    /**
     * @return the distance
     */
    public double getDistance() {
        if (isEnterd()) {
            return distance;
        }
        return 999999;
    }

    /**
     * @return say way of this object entered so far?
     */
    public boolean isEnterd() {
        return enterd;
    }

    /**
     * @return the lines of path
     */
    public Line[] getLines() {
        return lines;
    }

    /**
     * @return the points
     */
    public ArrayList<Dot> getPoints() {
        return points;
    }

    /**
     * show the path on the screen
     *
     * @param root root that objects have to add to that
     * @throws IllegalStateException this exception may be occurs but handled
     */
    public void Show(Pane root) throws IllegalStateException {
        Platform.runLater(() -> {
            for (int i = 0; i < lines.length; i++) {
                root.getChildren().add(lines[i]);
            }
        });
    }

    /**
     * Delete the lines of the path from the screen
     *
     * @param root root that objects have to delete from that
     * @throws IllegalStateException this exception may be occurs but handled
     */
    public void Delete(Pane root) throws IllegalStateException {
        Platform.runLater(() -> {
            for (int i = 0; i < lines.length; i++) {
                root.getChildren().remove(lines[i]);
            }
        });
    }

}
