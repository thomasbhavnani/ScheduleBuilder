/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

public class InvalidDurationException extends Exception{
    public InvalidDurationException() {
        super("Duration cannot exceed 60 or be below 0");
    }
}
