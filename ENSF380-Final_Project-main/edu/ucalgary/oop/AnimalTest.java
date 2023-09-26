/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class AnimalTest {
    private Animal animal1;
    private Animal animal2;
    private Animal animal3;

    /*
     * Test Animal Constructor with a valid species argument
     */
    @Test
    public void testValidSpecies() {
        boolean passed = false;
        try{
            Animal a = new Animal(1, "Nickname", "beaver"); 
            passed = true;
        }
        catch (InvalidSpeciesException e) {
            passed = false;
        }
        assertTrue(passed);
    }

    /*
     * Test Task Constructor with an invalid duration arguments
     */
    @Test
    public void testInvalidSpecies() throws InvalidStartHourException {
        boolean passed = false;
        // test for duration greater than 60
        try{
            Animal a = new Animal(1, "Nickname", "ealge"); 
            passed = true;
        }
        catch (InvalidSpeciesException e) {
            passed = false;
        }
        assertFalse(passed);
    }

    // initiaize tasks
    @Before 
    public void initializeTasks() {
        try {
            this.animal1 = new Animal(1, "Aim", "fox");
            this.animal2 = new Animal(2, "Arrow", "beaver");
            this.animal3 = new Animal(3, "Bow", "porcupine");
        }
        catch (InvalidSpeciesException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetNickname() {
        assertEquals("Aim", animal1.getAnimalNickname());
        assertEquals("Arrow", animal2.getAnimalNickname());
        assertEquals("Bow", animal3.getAnimalNickname());
    }

    @Test
    public void testGetSpecies() {
        assertEquals("fox", animal1.getAnimalSpecies());
        assertEquals("beaver", animal2.getAnimalSpecies());
        assertEquals("porcupine", animal3.getAnimalSpecies());
    }

    @Test
    public void testGetType() {
        assertEquals("Nocturnal", animal1.getAnimalType());
        assertEquals("Diurnal", animal2.getAnimalType());
        assertEquals("Crepuscular", animal3.getAnimalType());
    }

    @Test
    public void testGetFeedWindow() {
        boolean passed1 = true;
        boolean passed2 = true;
        boolean passed3 = true;
        for (int k: animal1.getfeedWindows()) {
            if(k != 0 && k != 1 && k != 2) {
                passed1 = false;
            }
        }
        for (int k: animal2.getfeedWindows()) {
            if(k != 8 && k != 9 && k != 10) {
                passed2 = false;
            }
        }
        for (int k: animal3.getfeedWindows()) {
            if(k != 19 && k != 20 && k != 21) {
                passed3 = false;
            }
        }
        assertTrue(passed1);
        assertTrue(passed2);
        assertTrue(passed3);
    }

    @Test
    public void testGetPrepTime() {
        assertEquals(5, animal1.getPrepTime());
        assertEquals(0, animal2.getPrepTime());
        assertEquals(0, animal3.getPrepTime());
    }

    @Test
    public void testGetCleanTime() {
        assertEquals(5, animal1.getCleanTime());
        assertEquals(5, animal2.getCleanTime());
        assertEquals(10, animal3.getCleanTime());
    }
}