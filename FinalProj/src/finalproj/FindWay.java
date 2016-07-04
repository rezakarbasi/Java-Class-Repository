package finalproj;

/**
 * <h1>Thread Class</h1>
 * This class Find some random ways based on some useful data.
 *
 * @author Reza Karbasi
 * @see courses.kntu.ac.ir
 * @version 1.0
 *
 */
import java.util.ArrayList;

/**
 *
 * @author Reza
 *
 * This class Find some random ways based on some useful data.
 *
 */
public class FindWay extends Thread {

    private int[] way;
    private ArrayList<Dot> dots;
    private Way out;
    private static int[][] priority;

    /**
     * This method evaluate the basic inputs to start this thread .
     *
     * @param O This parameter is for returning the output data to the main
     * class.
     * @param D This parameter indicates Dots .
     * @throws EnterInputsException this will throw when priority length and
     * array list doesn't in the same length
     */
    public FindWay(Way O, ArrayList<Dot> D) throws EnterInputsException {
        dots = D;

        if (D.size() != priority.length || priority.length != priority[0].length) {
            throw new EnterInputsException();
        }
        way = new int[dots.size()];
        way[0] = 0;

        for (int i = 0; i < way.length; i++) {
            way[i] = 0;
        }

        out = O;
    }

    /**
     * This method is called when this thread is going to start .
     */
    @Override
    public void run() {
        int[] k = new int[priority.length];
        way[0] = 0;
        for (int i = 1; i < dots.size(); i++) {
            k = MakeCopyOfPriority(way[i - 1]);
            for (int j = 0; j < i; j++) {
                k[way[j]] = 0;
            }
            int c = 0;
            for (int j = 0; j < k.length; j++) {
                c += k[j];
            }
            int random = (int) (Math.random() * c);
            if (random == 0) {
                random = 1;
            }
            c = 0;
            while (random > k[c]) {
                random -= k[c];
                c++;
            }
            way[i] = c;
        }
        try {
            out.setWay(way);
            return;
        } catch (Exception e) {
            return;
        }

    }

    /**
     *
     * @param dots the dots to set
     * @throws EnterInputsException this will throw when priority length and
     * array list doesn't in the same length
     */
    public void setDots(ArrayList<Dot> dots) throws EnterInputsException {
        if (dots.size() != priority.length) {
            throw new EnterInputsException();
        } else {
            this.dots = dots;
        }
    }

    /**
     * @return the output of this thread.
     */
    public Way getOut() {
        return out;
    }

    /**
     * @param aPriotity the priority of dots to set
     */
    public static void setPriotity(int[][] aPriotity) {
        priority = aPriotity;
    }

    /**
     * This method updates the coefficients for random selection.
     *
     * @param in is new input for updating coefficients.
     * @throws finalproj.EnterInputsException this will throw when priority
     * length and array list doesn't in the same length
     *
     */
    public static void Refresh_priority(int[][] in) throws EnterInputsException {
        if (in.length != priority.length) {
            throw new EnterInputsException();
        } else {
            for (int i = 0; i < priority.length; i++) {
                for (int j = 0; j < priority.length; j++) {
                    priority[i][j] += (in[i][j] - priority[i][j]) / 8;
                    if (!(priority[i][j] > 3)) {
                        priority[i][j] = 5;
                    }
                }
            }
        }
    }

    /**
     * This method is make a copy of row of a 2D array .
     *
     * @param n indicates the number of row that we want to be copied.
     * @return Copied row.
     */
    private int[] MakeCopyOfPriority(int n) {
        int[] copy = new int[priority.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = priority[i][n];
        }
        return copy;
    }

}
