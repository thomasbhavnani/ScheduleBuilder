/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

public class Treatment{

    private int treatmentID;
    private int animalID;
    private int taskID;
    private int startHour;
   

    /**
     * Constructs a new Treatment object with the given treatmentID, animalID, taskID and startHour.
     * 
     * @param treatmentID the treatment ID for this treatment
     * @param animalID the animal ID for this treatment
     * @param taskID the task ID for this treatment
     * @param startHour the start hour for this treatment
     * @throws InvalidStartHourException if the start hour is not between 0 and 23
     */
    public Treatment(int treatmentID, int animalID, int taskID, int startHour) throws InvalidStartHourException{
        this.treatmentID = treatmentID;
        this.animalID = animalID;
        this.taskID = taskID;
        if(startHour > 23 || startHour < 0){
            throw new InvalidStartHourException();
        }
        else{
            this.startHour = startHour;
        }
    }


    /**
     * Formats the Treatment object as a string.
     * 
     * @return a formatted string of this Treatment object
     */
    public String format() {
        String output = "Treatment ID = " + treatmentID + ", ";
        output += "Animal = " + animalID + ", ";
        output += "Task ID = " + taskID + ", ";
        output += "Start Hour = " + startHour;
        return output;
    }

    // getters
        /**
     * Returns the start hour for this treatment.
     */
    public int getStartHour() {
        return this.startHour;
    }
    /**
 * Returns the animal ID for this treatment.
 */
    public int getAnimalID() {
        return this.animalID;
    }

    /**
     * Returns the task ID for this treatment.
     */
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * Returns the treatment ID for this treatment.
     */

    public int getTreatmentID() {
        return this.treatmentID;
    }

}
