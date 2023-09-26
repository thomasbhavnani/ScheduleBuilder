/**
 * @author  380-PROJ 23
 * @version 1.2
 * @since 1.0
 */

package edu.ucalgary.oop;
import java.sql.*;
import java.util.*;

public class DatabaseConnection {
    // instance variables
    private Connection dbConnect;
    private ResultSet results;


    /**
 * Constructs a new default DatabaseConnection object.
 */
    public DatabaseConnection() {

    }
    /**
     * Establishes a connection to the MySQL database with the specified URL, username, and password.
     */
    public void createConnection() {
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "oop", "password");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * Retrieves all animal records from the "animals" table and returns them as an ArrayList of String arrays.
     *
     * @return an ArrayList of String arrays, where each array represents an animal record
     */
    public ArrayList<String[]> selectAnimals() {
        ArrayList<String[]> animals = new ArrayList<String[]>();
        
        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM animals");
            
            while (results.next()){
                String[] aniAtrributes = new String[3];
                aniAtrributes[0] = results.getString("AnimalID");
                aniAtrributes[1] = results.getString("AnimalNickname");
                aniAtrributes[2] = results.getString("AnimalSpecies");

                animals.add(aniAtrributes);
            }
            
            myStmt.close();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return animals;
    }


    /**
     * Retrieves all task records from the "tasks" table and returns them as an ArrayList of String arrays.
     *
     * @return an ArrayList of String arrays, where each array represents a task record
     */
    public ArrayList<String[]> selectTasks() {
        ArrayList<String[]> tasks = new ArrayList<String[]>();
        
        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM tasks");
            
            while (results.next()){
                String[] taskAtrributes = new String[4];
                taskAtrributes[0] = results.getString("TaskID");
                taskAtrributes[1] = results.getString("Description");
                taskAtrributes[2] = results.getString("Duration");
                taskAtrributes[3] = results.getString("MaxWindow");

                tasks.add(taskAtrributes);
            }
            
            myStmt.close();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }


    /**
     * Retrieves all treatment records from the "treatments" table and returns them as an ArrayList of String arrays.
     *
     * @return an ArrayList of String arrays, where each array represents a treatment record
     */
    public ArrayList<String[]> selectTreatments() {
        ArrayList<String[]> treatments = new ArrayList<String[]>();
        
        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM treatments");
            
            while (results.next()){
                String[] treAttributestrributes = new String[4];
                treAttributestrributes[0] = results.getString("TreatmentID");
                treAttributestrributes[1] = results.getString("AnimalID");
                treAttributestrributes[2] = results.getString("TaskID");
                treAttributestrributes[3] = results.getString("StartHour");

                treatments.add(treAttributestrributes);
            }
            
            myStmt.close();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return treatments;
    }

    /**
     * Updates the "StartHour" column of the "treatments" table for a specific treatment record.
     *
     * @param treatmentID the ID of the treatment record to be updated
     * @param startHour the new value of the "StartHour" column
     */
    public void update(int treatmentID, int startHour) {
        String update = String.format("UPDATE TREATMENTS SET StartHour = %d WHERE TreatmentID = %d", startHour, treatmentID);
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeUpdate(update);
            myStmt.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes the result set and database connection.
     */
    public void close() {
        try {
            results.close();
            dbConnect.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
