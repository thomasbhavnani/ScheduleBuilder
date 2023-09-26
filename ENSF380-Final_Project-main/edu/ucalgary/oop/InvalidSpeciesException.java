/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

public class InvalidSpeciesException extends Exception{
    public InvalidSpeciesException() {
        super("Species is not treated at this shelter");
    }
}
