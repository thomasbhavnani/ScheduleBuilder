/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class TreatmentTesting {
    /*
     * Test Treatment Constructor with a valid start hour argument
     */
    @Test
    public void testValidStartHour() {
        boolean passed = false;
        try{
            Treatment t = new Treatment(0, 1, 2, 12);  
            passed = true;
        }
        catch (InvalidStartHourException e) {
            passed = false;
        }
        assertTrue(passed);
    }

    /*
     * Test Treatment Constructor with an invalid start hour argument
     */
    @Test
    public void testInvalidStartHour() throws InvalidStartHourException {
        boolean passedGreater = false;
        boolean passedLesser = false;
        // test for start hour greater than 23
        try{
            Treatment tg = new Treatment(1, 1, 2, 25);  
            passedGreater = true;
        }
        catch (InvalidStartHourException e) {
            passedGreater = false;
        }

        // test for start hour less than 0
        try{
            Treatment tl = new Treatment(2, 1, 2, -1);
            passedLesser = true;
        }
        catch (InvalidStartHourException e) {
            passedLesser = false;
        }
        assertFalse(passedGreater);
        assertFalse(passedLesser);
    }

    /*
     * Test getAnimalID 
     */
    @Test
    public void testGetAnimalID() throws InvalidStartHourException {
        Treatment t = new Treatment(3, 3, 4, 10);
        assertEquals(3, t.getAnimalID());
    }

    /*
     * Test getTaskID
     */
    @Test
    public void testGetTaskID() throws InvalidStartHourException {
        Treatment t = new Treatment(4, 5, 6, 16);
        assertEquals(6, t.getTaskID());
    }

    /*
     * Test getStartHour
     */
    @Test
    public void testGetStartHour() throws InvalidStartHourException {
        Treatment t = new Treatment(6, 5, 6, 16);
        assertEquals(16, t.getStartHour());
    }

    /*
     * Test getTreatmentID
     */
    @Test
    public void testGetTreatMentID() throws InvalidStartHourException {
        Treatment t = new Treatment(6, 5, 6, 16);
        assertEquals(6, t.getTreatmentID());
    }
}
    
