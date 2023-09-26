/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.*;

public class ScheduleTest {

    private ArrayList<String[]> goodAnimals = new ArrayList<String[]>();
    private ArrayList<String[]> goodTreatments = new ArrayList<String[]>();
    private ArrayList<String[]> goodTasks = new ArrayList<String[]>();

    private ArrayList<String[]> badAnimals = new ArrayList<String[]>();
    private ArrayList<String[]> badTreatments = new ArrayList<String[]>();
    private ArrayList<String[]> badTasks = new ArrayList<String[]>();

    @Before
    public void initializeItems() {

        // initialize goodAnimals
        goodAnimals.add(new String[] { "1", "Loner", "coyote" });
        goodAnimals.add(new String[] { "2", "Biter", "beaver" });
        goodAnimals.add(new String[] { "3", "Bitter", "fox" });
        goodAnimals.add(new String[] { "4", "Pencil", "porcupine" });
        goodAnimals.add(new String[] { "5", "Eraser", "raccoon" });

        // initialize goodTreatments
        goodTreatments.add(new String[] { "0", "1", "1", "0" });
        goodTreatments.add(new String[] { "1", "2", "2", "2" });
        goodTreatments.add(new String[] { "2", "3", "3", "4" });
        goodTreatments.add(new String[] { "3", "4", "4", "6" });
        goodTreatments.add(new String[] { "4", "5", "5", "8" });

        // initialize goodTasks
        goodTasks.add(new String[] { "1", "Kit feeding", "30", "2" });
        goodTasks.add(new String[] { "2", "Rebandage leg wound", "20", "1" });
        goodTasks.add(new String[] { "3", "Apply burn ointment back", "10", "3" });
        goodTasks.add(new String[] { "4", "Administer antibiotics", "5", "1" });
        goodTasks.add(new String[] { "5", "Flush neck wound", "25", "1" });

        // initialize badAnimals
        badAnimals.add(new String[] { "1", "Loner", "coyote" });
        badAnimals.add(new String[] { "2", "Biter", "beaver" });
        badAnimals.add(new String[] { "3", "Bitter", "whale" });
        badAnimals.add(new String[] { "4", "Pencil", "porcupine" });
        badAnimals.add(new String[] { "5", "Eraser", "raccoon" });

        // initialize badTreatments
        badTreatments.add(new String[] { "0", "1", "1", "0" });
        badTreatments.add(new String[] { "1", "2", "2", "2" });
        badTreatments.add(new String[] { "2", "3", "3", "4" });
        badTreatments.add(new String[] { "3", "4", "4", "6" });
        badTreatments.add(new String[] { "4", "5", "5", "24" });

        // initialize badTasks
        badTasks.add(new String[] { "1", "Kit feeding", "30", "2" });
        badTasks.add(new String[] { "2", "Rebandage leg wound", "20", "1" });
        badTasks.add(new String[] { "3", "Apply burn ointment back", "10", "3" });
        badTasks.add(new String[] { "4", "Administer antibiotics", "5", "1" });
        badTasks.add(new String[] { "5", "Flush neck wound", "61", "1" });

    }

    @Test
    public void testGenerateSchedule() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        boolean passed = false;
        try {
            schedule.generateSchedule();
            passed = true;
        } catch (ScheduleGenerationException g) {
            passed = false;
        }
        assertTrue(passed);
    }

    @Test
    public void testPrintSchedule() throws ScheduleGenerationException {
        boolean passed = false;
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        schedule.generateSchedule();
        int stringSize = schedule.format().length();
        if (stringSize > 0) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Before
    public void initializeData() {

    }

    /*
     * Test Schedule constructor with good and bad data
     */
    @Test
    public void testScheduleConstructorGoodData() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        assertEquals(5, schedule.getAnimals().size());
        assertEquals(5, schedule.getTasks().size());
        assertEquals(5, schedule.getTreatments().size());
        assertEquals(5, schedule.getItems().size());
    }

    @Test
    public void testScheduleConstructorBadData() {
        Schedule schedule = new Schedule(badAnimals, badTasks, badTreatments);
        assertEquals(4, schedule.getAnimals().size());
        assertEquals(4, schedule.getTasks().size());
        assertEquals(4, schedule.getTreatments().size());
        assertEquals(3, schedule.getItems().size());
    }

    /*
     * Tests for all getters using good data
     */
    @Test
    public void testGetItems() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        ArrayList<Integer> treatmentIDs = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            treatmentIDs.add(i);
        }
        boolean passed = true;
        for (Item i : schedule.getItems()) {
            if (!treatmentIDs.contains(i.getTreatmentID())) {
                passed = false;
                break;
            } else {
                passed = true;
            }
        }
        assertTrue(passed);
    }

    @Test
    public void testGetSchedule() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        ArrayList<Integer> hours = new ArrayList<Integer>();
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        boolean passed = true;
        for (int hour : schedule.getSchedule().keySet()) {
            if (!hours.contains(hour)) {
                passed = false;
                break;
            } else {
                passed = true;
            }
        }
        assertTrue(passed);
    }

    @Test
    public void testGetUnscheduled() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        ArrayList<Integer> hours = new ArrayList<Integer>();
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        boolean passed = true;
        for (int hour : schedule.getUnscheduled().keySet()) {
            if (!hours.contains(hour)) {
                passed = false;
                break;
            } else {
                passed = true;
            }
        }
        assertTrue(passed);
    }

    @Test
    public void testGetAnimals() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        ArrayList<Integer> animalIDs = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            animalIDs.add(i);
        }
        boolean passed = true;
        for (Animal animal : schedule.getAnimals()) {
            if (!animalIDs.contains(animal.getID())) {
                passed = false;
                break;
            } else {
                passed = true;
            }
        }
        assertTrue(passed);
    }

    @Test
    public void testGetTasks() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        ArrayList<Integer> taskIDs = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            taskIDs.add(i);
        }
        boolean passed = true;
        for (Task task : schedule.getTasks()) {
            if (!taskIDs.contains(task.getId())) {
                passed = false;
                break;
            } else {
                passed = true;
            }
        }
        assertTrue(passed);
    }

    @Test
    public void testGetTreatments() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        ArrayList<Integer> treatmentIDs = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            treatmentIDs.add(i);
        }
        boolean passed = true;
        for (Treatment treatment : schedule.getTreatments()) {
            if (!treatmentIDs.contains(treatment.getTreatmentID())) {
                passed = false;
                break;
            } else {
                passed = true;
            }
        }
        assertTrue(passed);
    }

    @Test
    public void testGetGroupByStartHour() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        try {
            schedule.generateSchedule();
            ArrayList<Integer> hours = new ArrayList<Integer>();
            for (int i = 0; i < 24; i++) {
                hours.add(i);
            }
            boolean passed = true;
            for (int hour : schedule.getGroupByStartHour().keySet()) {
                if (!hours.contains(hour)) {
                    passed = false;
                    break;
                } else {
                    passed = true;
                }
            }
            assertTrue(passed);
        } catch (ScheduleGenerationException e) {

        }
    }

    @Test
    public void testGetVolunteerHours() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        try {
            schedule.generateSchedule();
            boolean passed = true;
            if (schedule.getVolunteerHours().size() != 0) {
                passed = false;
            }
            assertTrue(passed);
        } catch (ScheduleGenerationException e) {

        }
    }

    @Test
    public void testGetNeedVolunteer() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        try {
            schedule.generateSchedule();
            assertFalse(schedule.getNeedVolunter());
        } catch (ScheduleGenerationException e) {

        }
    }

    @Test
    public void testGetFailureHours() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        try {
            schedule.generateSchedule();
            boolean passed = true;
            if (schedule.getFailureHours().size() != 0) {
                passed = false;
            }
            assertTrue(passed);
        } catch (ScheduleGenerationException e) {

        }
    }

    @Test
    public void testGetItemFailure() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        try {
            schedule.generateSchedule();
            boolean passed = true;
            if (schedule.getItemFailures().size() != 0) {
                passed = false;
            }
            assertTrue(passed);
        } catch (ScheduleGenerationException e) {

        }
    }

    @Test
    public void testFindOpenHours() {
        Schedule schedule = new Schedule(goodAnimals, goodTasks, goodTreatments);
        try {
            schedule.generateSchedule();

            Item testItem = new Item(72, "cleaning nick", 10, "nick", 3, 0);
            ArrayList<Integer> openHours = schedule.findOpenHours(testItem);
            assertEquals(23, openHours.size());
        } catch (ScheduleGenerationException e) {

        }
    }

    @Test
    public void testAddTask() {
        ArrayList<String[]> initialTasks = new ArrayList<String[]>();

        initialTasks.add(new String[] { "1", "Kit feeding", "30", "2" });
        initialTasks.add(new String[] { "2", "Rebandage leg wound", "20", "1" });
        initialTasks.add(new String[] { "3", "Apply burn ointment back", "10", "3" });
        initialTasks.add(new String[] { "4", "Administer antibiotics", "5", "1" });
        initialTasks.add(new String[] { "5", "Flush neck wound", "25", "1" });

        Schedule schedule = new Schedule(goodAnimals, initialTasks, goodTreatments);

        boolean passed = false;
        try {
            schedule.generateSchedule();
            int beforeInt = schedule.format().length();
            Item testItem = new Item(72, "cleaning nick", 10, "nick", 3, 0);
            schedule.addTask(7, testItem);
            int afterInt = schedule.format().length();
            if (afterInt > beforeInt) {
                passed = true;
            }
            assertTrue(passed);
        } catch (ScheduleGenerationException e) {

        }
    }

}