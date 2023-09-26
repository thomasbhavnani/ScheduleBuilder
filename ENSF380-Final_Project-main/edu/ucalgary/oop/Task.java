/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

public class Task {
    private int taskID;
    private String description;
    private int duration;
    private int maxWindow;


    /**
     * Constructs a new Task object with the given taskID, description, duration and maxWindow.
     * 
     * @param id the task ID for this task
     * @param description the description for this task
     * @param duration the duration of this task in minutes
     * @param maxWindow the maximum time window in which this task can be performed
     * @throws InvalidDurationException if the duration is not between 0 and 60 minutes
     */
    public Task(int id, String description, int duration, int maxWindow) throws InvalidDurationException{
        this.taskID = id;
        this.description = description;
        this.maxWindow = maxWindow;
        if(duration > 60 || duration < 0) {
            throw new InvalidDurationException();
        }
        else {
            this.duration = duration;
        }
    }
    
    //getters

    /**
     * Returns the task ID for this task.
     * 
     * @return the task ID for this task
     */
    public int getId() {
        return this.taskID;
    }

    /**
     * Returns the description for this task.
     * 
     * @return the description for this task
     */

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the duration of this task in minutes.
     * 
     * @return the duration of this task in minutes
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Returns the maximum time window in which this task can be performed.
     * 
     * @return the maximum time window in which this task can be performed
     */
    public int getMaxWindow() {
        return this.maxWindow;
    }

}