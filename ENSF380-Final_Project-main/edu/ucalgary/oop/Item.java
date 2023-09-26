/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since   1.0
 */

package edu.ucalgary.oop;

public class Item{
    private int treatmentID;
    private String taskName;
    private int taskDuration;
    private String animalName;
    private int maxWindow;
    private int startHour;


 

    /**
     * Constructs a new Item with the specified attributes.
     *   since all arguments will be validated by the Animal, Task, 
     *   and Treatment classes, there is no need to throw exceptions
     * @param treatmentID  the ID of the treatment
     * @param taskName     the name of the task
     * @param taskDuration the duration of the task in minutes
     * @param animalName   the name of the animal
     * @param maxWindow    the maximum window for task completion in minutes
     * @param startHour    the start hour for the task (24-hour format)
     */
    public Item(int treatmentID, String taskName, int taskDuration, String animalName, int maxWindow, int startHour) {
        this.treatmentID = treatmentID;
        this.taskName = taskName;
        this.taskDuration = taskDuration;
        this.animalName = animalName;
        this.maxWindow = maxWindow;
        this.startHour = startHour;
    }

    // getters
    /**
     * Returns the treatment ID.
     * @return the treatment ID
     */
    public int getTreatmentID() {
        return this.treatmentID;
    }


    /**
     * Returns the task name.
     * @return the task name
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Returns the task duration.
     * @return the task duration in minutes
     */
    public int getTaskDuration() {
        return this.taskDuration;
    }

    /**
     * Returns the animal name.
     * @return the animal name
     */
    public String getAnimalName() {
        return this.animalName;
    }

    /**
     * Returns the maximum window for task completion.
     * @return the maximum window in minutes
     */
    public int getMaxWindow() {
        return this.maxWindow;
    }

    /**
     * Returns the start hour for the task.
     * @return the start hour in 24-hour format
     */
    public int getStartHour() {
        return this.startHour;
    }

}