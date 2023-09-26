/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

public class InvalidStartHourException extends Exception {
    public InvalidStartHourException() {
        super("Hour Does not Exist");
    }
}