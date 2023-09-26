/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TaskTesting {
    private Task task1;
    private Task task2;
    private Task task3;

    /*
     * Test Task Constructor with a valid duration argument
     */
    @Test
    public void testValidDuration() {
        boolean passed = false;
        try{
            Task t = new Task(1, "Feeding", 20, 3); 
            passed = true;
        }
        catch (InvalidDurationException e) {
            passed = false;
        }
        assertTrue(passed);
    }

    /*
     * Test Task Constructor with an invalid duration arguments
     */
    @Test
    public void testInvalidDuration() throws InvalidStartHourException {
        boolean passedGreater = false;
        boolean passedLesser = false;
        // test for duration greater than 60
        try{
            Task taskG = new Task(2, "Cleaning", 65, 1);
            passedGreater = true;
        }
        catch (InvalidDurationException e) {
            passedGreater = false;
        }

        // test for start hour less than 0
        try{
            Task taskL = new Task(2, "Cleaning", -5, 1);
            passedLesser = true;
        }
        catch (InvalidDurationException e) {
            passedLesser = false;
        }
        assertFalse(passedGreater);
        assertFalse(passedLesser);
    }

    // initiaize tasks
    @Before 
    public void initailizeTasks() {
        try {
            this.task1 = new Task(1, "Feeding", 20, 3);
            this.task2 = new Task(2, "Cleaning", 45, 1);
            this.task3 = new Task(3, "Treatment", 10, 5);
        }
        catch (InvalidDurationException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetID() {
        assertEquals(1, task1.getId());
        assertEquals(2, task2.getId());
        assertEquals(3, task3.getId());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Feeding", task1.getDescription());
        assertEquals("Cleaning", task2.getDescription());
        assertEquals("Treatment", task3.getDescription());
    }

    @Test
    public void testGetDuration() {
        assertEquals(20, task1.getDuration());
        assertEquals(45, task2.getDuration());
        assertEquals(10, task3.getDuration());
    }

    @Test
    public void testGetMaxWindow() {
        assertEquals(3, task1.getMaxWindow());
        assertEquals(1, task2.getMaxWindow());
        assertEquals(5, task3.getMaxWindow());
    }
}