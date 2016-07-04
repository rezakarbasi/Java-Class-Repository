/**
 * @author Reza Karbasi
 * This exception indicates Error in Input the Data to some classes include FindWay.java & way.java & Dot.java
 */
package finalproj;

/**
 *
 * @author Reza
 */
public class EnterInputsException extends Exception {

    /**
     * This constructor show some useful messages on the screen
     */
    public EnterInputsException() {
        System.out.println("Exception is for wrong on inpu the data.");
    }

}
