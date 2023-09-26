/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ItemTest {
    private Item item1;
    private Item item2;
    private Item item3;

    @Before 
    public void initializeItems() {
        this.item1 = new Item(1, "Cleaning", 10, "Aim", 2, 9);
        this.item2 = new Item(2, "Feeding", 30, "Arrow",3, 23);
        this.item3 = new Item(3, "Medical", 20, "Bow", 1, 14);
    }

    @Test
    public void testGetTreatmentID() {
        assertEquals(1, item1.getTreatmentID());
        assertEquals(2, item2.getTreatmentID());
        assertEquals(3, item3.getTreatmentID());
    }

    public void testGetTaskName() {
        assertEquals("Cleaning", item1.getTaskName());
        assertEquals("Feeding", item2.getTaskName());
        assertEquals("Medical", item3.getTaskName());
    }

    public void testGetTaskDuration() {
        assertEquals(10, item1.getTaskDuration());
        assertEquals(30, item2.getTaskDuration());
        assertEquals(20, item3.getTaskDuration());
    }

    public void testGetAnimalName() {
        assertEquals("Aim", item1.getAnimalName());
        assertEquals("Arrow", item2.getAnimalName());
        assertEquals("Bow", item3.getAnimalName());
    }

    public void testGetMaxWindow() {
        assertEquals(2, item1.getMaxWindow());
        assertEquals(3, item2.getMaxWindow());
        assertEquals(1, item3.getMaxWindow());
    }

    public void testGetStartHour() {
        assertEquals(9, item1.getStartHour());
        assertEquals(23, item2.getStartHour());
        assertEquals(14, item3.getStartHour());
    }
}
