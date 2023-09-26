/**
 * @author  380-PROJ 23
 * @version 1.5
 * @since 1.0
 */

package edu.ucalgary.oop;
import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Schedule implements Format{
    // instance variables
    private HashMap<Integer, ArrayList<Item>> schedule = new HashMap<Integer, ArrayList<Item>>();
    private HashMap<Integer, ArrayList<Item>> unscheduled = new HashMap<Integer, ArrayList<Item>>();
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ArrayList<Treatment> treatments = new ArrayList<Treatment>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private TreeSet<Integer> hoursForVolunteer = new TreeSet<Integer>();
    private TreeSet<Integer> hoursOfFailure = new TreeSet<Integer>();
    private HashMap<Integer, ArrayList<Item>> groupByStartHour = new HashMap<Integer, ArrayList<Item>>();
    private boolean needVolunteer = false;
    private ArrayList<Item> itemFailures = new ArrayList<Item>();



    // constructor

     /**
     * Constructs a Schedule object using the provided lists of animals, tasks, and treatments.
     *
     * @param animals    a list of arrays containing animal data
     * @param tasks      a list of arrays containing task data
     * @param treatments a list of arrays containing treatment data
     */
    public Schedule(ArrayList<String[]> animals, ArrayList<String[]> tasks,
            ArrayList<String[]> treatments) {
        // initalize animal arraylist

        for (String[] animal : animals) {
            try{
                Animal newAnimal = new Animal(Integer.parseInt(animal[0]),
                animal[1], animal[2]);
                this.animals.add(newAnimal); 
            }
            catch (InvalidSpeciesException e) {

            }
        }

        // initalize task arraylist
        for (String[] task : tasks) {
            try{
                Task newTask = new Task(Integer.parseInt(task[0]), task[1],
                Integer.parseInt(task[2]),
                Integer.parseInt(task[3]));
                this.tasks.add(newTask);
            }
            catch (InvalidDurationException e) {
                
            }
        }

        // initalize treatment arraylist
        for (String[] treatment : treatments) {
            try {
                Treatment newTreatment = new Treatment(Integer.parseInt(treatment[0]),
                        Integer.parseInt(treatment[1]),
                        Integer.parseInt(treatment[2]),
                        Integer.parseInt(treatment[3]));
                this.treatments.add(newTreatment);
            } catch (InvalidStartHourException e) {
                System.out.println("Treatment does not contain a valid start date");
            }
        }

        createItems(this.treatments, this.animals, this.tasks);
    }

    // helper function for constructor
    private void createItems(ArrayList<Treatment> treatments, ArrayList<Animal> animals, ArrayList<Task> tasks) {
        for (Treatment t: treatments) {
            for (Task tas: tasks) {
                for (Animal a: animals) {
                    if(t.getAnimalID() == a.getID() && t.getTaskID() == tas.getId()) {
                        Item newItem = new Item(t.getTreatmentID(), tas.getDescription(), tas.getDuration(), 
                                                a.getAnimalNickname(), tas.getMaxWindow(), t.getStartHour());
                        this.items.add(newItem);
                    }
                }
            }
        }
    }

    // schedule generation functions

    /**
     * Generates a schedule for the given data.
     * @throws ScheduleGenerationException if the schedule generation fails
     */
    public void generateSchedule() throws ScheduleGenerationException{
        groupByStartHour();
        for (int i = 0; i < 24; i++) {
            this.schedule.put(i, new ArrayList<Item>());
        }

        int timeAllowed = 60;
        for (int key : this.groupByStartHour.keySet()) {

            int timeUsed = 0;

            ArrayList<Item> temp = sortByMaxWindow(this.groupByStartHour.get(key));
            ArrayList<Item> toInsert = new ArrayList<Item>();
            ArrayList<Item> tempUnscheduled = new ArrayList<Item>();

            for (Item item: temp) {
                if((timeUsed + item.getTaskDuration()) <= timeAllowed) {

                    timeUsed = timeUsed + item.getTaskDuration();
                    toInsert.add(item);
                } else {
                    tempUnscheduled.add(item);
                }
            }
            schedule.put(key, toInsert);
            unscheduled.put(key, tempUnscheduled);
        }
        feedingSchedule();
        cleaningSchedule();
        scheduleUnscheduled();
    }

    
    /**
    Generates feeding schedule for each animal species based on their feeding windows and preparation time.
    @throws ScheduleGenerationException if there is an error in the schedule generation process
    */
    private void feedingSchedule() throws ScheduleGenerationException{

        HashMap<String, Animal> species = new HashMap<String, Animal>();
        for (Animal animal: this.animals) {
            species.put(animal.getAnimalSpecies(), animal);
        }
        
        for (String s: species.keySet()) {
            scheduleFeedingBySpecies(s, species.get(s).getfeedWindows(), species.get(s).getPrepTime());
        }
    }


    /**

    Schedules feeding for a given animal species based on their feeding windows and preparation time.
    @param species the animal species to schedule feeding for
    @param startHours the feeding windows for the animal species
    @param prepTime the preparation time for feeding
    @throws ScheduleGenerationException if there is an error in the schedule generation process
    */
    private void scheduleFeedingBySpecies(String species, int[] startHours, int prepTime) throws ScheduleGenerationException{
        ArrayList<Animal> bySpecies = groupBySpecies(species, "feeding");
        String animalString = "";
        HashMap<String, int[]> animalsAndTimes = new HashMap<String, int[]>();
        int animalCount = animalCount(species, "feeding");
        int addedAnimalCount = 0;
        for (Animal animal : animals) {

            animalsAndTimes.put(animal.getAnimalNickname(), animal.getfeedWindows());
            for (int hour : schedule.keySet()) {
                int timeRemaining = findTimeRemaining(schedule.get(hour));

                for (int number : animal.getfeedWindows()) {

                    if (hour == number) {
                        if (animal.getAnimalSpecies().equals(species)) {
                            int animalsInHour;
                            if (addedAnimalCount + Math.floorDiv(timeRemaining - prepTime, 5) < animalCount) {
                                animalsInHour = Math.floorDiv(timeRemaining - prepTime, 5);
                                addedAnimalCount += animalsInHour;
                            } else {
                                animalsInHour = animalCount - addedAnimalCount;
                                addedAnimalCount += animalsInHour;
                            }
                            if (animalsInHour > 0) {
                                for (int i = 0; i < animalsInHour; i++) {
                                    animalString += bySpecies.get(0).getAnimalNickname() + ", ";
                                    bySpecies.remove(0);
                                }
                                animalString = animalString.substring(0, animalString.length() - 2);
                                Item newItem = new Item(-2, "Feeding " + animalsInHour + " " + species, 
                                prepTime + animalsInHour * 5, animalString, 1, 99);
                                schedule.get(hour).add(newItem);
                                animalString = "";
                                break;  
                            }
                        }
                    }
                }
                if (addedAnimalCount == animalCount) {
                    break;
                }
            }
        }
        if (addedAnimalCount != animalCount) {
            throw new ScheduleGenerationException();
        }
    }

    /**

    Generates cleaning schedule for each animal species based on their cleaning time.
    @throws ScheduleGenerationException if there is an error in the schedule generation process
    */
    private void cleaningSchedule() throws ScheduleGenerationException{
        HashMap<String, Animal> species = new HashMap<String, Animal>();
        for (Animal animal: this.animals) {
            species.put(animal.getAnimalSpecies(), animal);
        }

        for (String s: species.keySet()) {
            scheduleCleaningBySpecies(s, species.get(s).getCleanTime());
        }
    }

    /**
     * Schedules cleaning for a specific species based on clean time.
     *
     * @param species the species of animal to schedule cleaning for
     * @param cleanTime the time needed to clean the species' cages
     * @throws ScheduleGenerationException if cleaning could not be scheduled for the given species
     */
    private void scheduleCleaningBySpecies(String species, int cleanTime) throws ScheduleGenerationException{
        ArrayList<Animal> bySpecies = groupBySpecies(species, "cleaning");
        String animals = "";
        int animalCount = animalCount(species, "cleaning");
        int addedAnimalCount = 0;
        for (int hour: this.schedule.keySet()) {
            int timeRemaining = findTimeRemaining(schedule.get(hour));
            int animalsInHour;
            if (addedAnimalCount + Math.floorDiv(timeRemaining, cleanTime) <= animalCount) {
                animalsInHour = Math.floorDiv(timeRemaining, cleanTime);
                addedAnimalCount += animalsInHour;
            } else {
                animalsInHour = animalCount - addedAnimalCount;
                addedAnimalCount += animalsInHour;
            }
            
            if (animalsInHour > 0) {
                for (int i = 0; i < animalsInHour; i++) {
                    animals += bySpecies.get(0).getAnimalNickname() + ", ";
                    bySpecies.remove(0);
                }
                animals = animals.substring(0, animals.length() - 2);
                Item newItem = new Item(-1, "Cleaning " + animalsInHour + " " + species + " cages", 
                                        animalsInHour * cleanTime, animals, 1, 99);
                schedule.get(hour).add(newItem);  
                animals = "";
            }
            if(addedAnimalCount == animalCount) {
                break;
            }
        }
        if (addedAnimalCount != animalCount) {
            throw new ScheduleGenerationException();
        }
    }

    /**
     * Schedules any unscheduled tasks into the existing schedule.
     * Tasks are scheduled based on their maximum time window and duration.
     *
     * @throws ScheduleGenerationException if unscheduled tasks could not be scheduled
     */
    private void scheduleUnscheduled() throws ScheduleGenerationException{
        for (int key : this.unscheduled.keySet()) {
            ArrayList<Item> temp = this.unscheduled.get(key);
            if (temp.size() != 0) {
                Iterator<Item> taskIterator = temp.iterator();
                while (taskIterator.hasNext()) {
                    Item item = taskIterator.next();
                    int maxWindow = item.getMaxWindow();
                    int duration = item.getTaskDuration();
                    int openHour = findOpenHour(key, maxWindow, duration);
                    if (openHour != -1) {
                        
                        this.schedule.get(openHour).add(item);
                        taskIterator.remove();
                    } else {

                        int currTime = 0;
                        ArrayList<Item> itemsInHour = this.schedule.get(key);
                        for (Item t: itemsInHour) {
                            currTime+=t.getTaskDuration();
                        }
                        if((currTime + item.getTaskDuration()) <= 120) {
                            this.schedule.get(key).add(item);
                            this.hoursForVolunteer.add(key);
                            taskIterator.remove();
        
                        }
                        else {
                            this.hoursOfFailure.add(key);
                            this.itemFailures.add(item);
                            
                        }
                        this.needVolunteer = true;
                    }
                }
            }
        }
        if (itemFailures.size() > 0) {
            throw new ScheduleGenerationException();
        }
    }

    /**
     * Adds a task to the schedule at the specified start hour.
     *
     * @param startHour the start hour for the task
     * @param toAdd     the Item object representing the task to be added
     */
    public void addTask(int startHour, Item toAdd) {
        this.schedule.get(startHour).add(toAdd);
    }

    // getters
    public ArrayList<Item> getItems() {
        return this.items;
    }

    public HashMap<Integer, ArrayList<Item>> getSchedule() {
        return this.schedule;
    }

    public HashMap<Integer, ArrayList<Item>> getUnscheduled() {
        return this.unscheduled;
    }

    public ArrayList<Animal> getAnimals() {
        return this.animals;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public ArrayList<Treatment> getTreatments() {
        return this.treatments;
    }

    public HashMap<Integer, ArrayList<Item>> getGroupByStartHour() {
        return this.groupByStartHour;
    }

    public TreeSet<Integer> getVolunteerHours() {
        return this.hoursForVolunteer;
    }

    public boolean getNeedVolunter() {
        return this.needVolunteer;
    }

    public TreeSet<Integer> getFailureHours() {
        return this.hoursOfFailure;
    }

    public ArrayList<Item> getItemFailures() {
        return this.itemFailures;
    }

    // output functions

    /**
     * Formats the schedule and returns it as a string. Also saves the schedule to a file.
     *
     * @return A formatted schedule string.
     */
    public String format() {
        String toReturn = "Schedule for: " + LocalDate.now().toString() + "\n\n";
        for (int key : this.schedule.keySet()) {
            if(hoursForVolunteer.contains(key)) {
                toReturn += "Hour " + key + " - Call Volunteer \n";
            }
            else {
                toReturn += "Hour " + key + "\n";  
            }
            for (Item item : this.schedule.get(key)) {
                toReturn += "- " + item.getTaskName() + " --> " + item.getAnimalName() +"\n";
            }
            toReturn += "\n";
        }
        toFile(toReturn);
        return toReturn;
    }

    /**
     * Writes the given text to a file named "schedule.txt".
     *
     * @param text The text to be written to the file.
     */
    private void toFile(String text) {
        try {
            FileWriter out = new FileWriter("schedule.txt");
            out.write(text);
            out.close();
        }
        catch (IOException e) {
            System.out.println("could not write to file");
        }
    }

    // helper functions

    /**
     * Finds an open hour within a specified time window and duration for a task.
     *
     * @param startHour The starting hour for the search.
     * @param maxWindow The maximum time window for the search.
     * @param duration The duration of the task.
     * @return An integer representing the open hour, or -1 if none found.
     */
    private int findOpenHour(int startHour, int maxWindow, int duration) {
        TreeSet<Integer> availableStartHours = new TreeSet<Integer>();
        ArrayList<Item> unscheduledTasks = unscheduled.get(startHour);
        for (int i = 0; i < maxWindow; i++) {
            availableStartHours.add(startHour + i);
        }
        availableStartHours.add(startHour);
        for (int i : availableStartHours) {
            ArrayList<Item> tasksInHour = schedule.get(i);
            if (tasksInHour.isEmpty()) {
                return i;
            } else {
                int remainingTime = findTimeRemaining(tasksInHour);
                for (Item item : unscheduledTasks) {
                    if (item.getTaskDuration() < remainingTime) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Finds open hours for a specified task.
     *
     * @param item The task item to search for open hours.
     * @return An ArrayList of integers representing the open hours.
     */

    public ArrayList<Integer> findOpenHours(Item item) {
        ArrayList<Integer> openHours = new ArrayList<Integer>();
        for (int key: this.schedule.keySet()) {
            if(findTimeRemaining(this.schedule.get(key)) >= item.getTaskDuration()) {
                openHours.add(key);
            }
        }
        return openHours;
    }


    /**
     * Groups tasks by start hour.
     */
    private void groupByStartHour() {
        for (int i = 0; i < 24; i++) {
            this.groupByStartHour.put(i, new ArrayList<Item>());
        }

        // check
        for (int key: this.groupByStartHour.keySet()) {
            for(Item item: this.items) {
                if(item.getStartHour() == key) {
                    this.groupByStartHour.get(key).add(item);
                }
            }
        }

    }

        /**
     * Sorts an ArrayList of Item objects based on their maximum time window.
     *
     * @param arr The ArrayList of Item objects to be sorted.
     * @return A sorted ArrayList of Item objects.
     */
    private ArrayList<Item> sortByMaxWindow(ArrayList<Item> arr) {
        ArrayList<Item> sortedArr = new ArrayList<Item>(arr.size());

        for (int i = 0; i < arr.size(); i++) {
          int minIndex = i;
    
          for (int j = i + 1; j < arr.size(); j++) {
            if (arr.get(j).getMaxWindow() < arr.get(minIndex).getMaxWindow()) {
              minIndex = j;
            }
          }
    
          Item temp = arr.get(i);
          arr.set(i, arr.get(minIndex));
          arr.set(minIndex, temp);
          sortedArr.add(arr.get(i));
        }
    
        return sortedArr;
    }

    /**
     * Calculates the remaining time available in a given list of tasks within an hour.
     *
     * @param itemsInHour An ArrayList of Item objects representing tasks within an hour.
     * @return An integer representing the remaining time in minutes.
     */
    private int findTimeRemaining(ArrayList<Item> itemsInHour) {
        int timeUsed = 0;
        for (Item item : itemsInHour) {
            timeUsed += item.getTaskDuration();
        }
        return 60 - timeUsed;
    }

        /**
     * Counts the number of animals of a specified species for a specified task.
     *
     * @param species The animal species.
     * @param task The task (feeding or cleaning).
     * @return The count of animals of the specified species for the specified task.
     */
    private int animalCount(String species, String task) {
        int count = 0;
        Treatment orphanFeeding = null;
        for (Animal animal : this.animals) {
            if(task.equals("feeding")) {
                for(Treatment treatment: this.treatments) {
                    if (treatment.getTreatmentID() == 1) {
                        orphanFeeding = treatment;
                        break;
                    }
                }
                if (orphanFeeding != null) {
                    if (animal.getAnimalSpecies().equals(species) && animal.getID() != orphanFeeding.getAnimalID()) {
                        count++;
                    }
                }
            }
            else if(task.equals("cleaning")) {
                if (animal.getAnimalSpecies().equals(species)) {
                    count++;
                } 
            }
        }
        return count;
    }

    /**
     * Groups animals by species for a specified task.
     *
     * @param species The animal species.
     * @param task The task (feeding or cleaning).
     * @return An ArrayList of Animal objects grouped by the specified species and task.
     */
    private ArrayList<Animal> groupBySpecies(String species, String task) {
        ArrayList<Animal> bySpecies = new ArrayList<Animal>();
        Treatment orphanFeeding = null;
        for (Animal animal : this.animals) {
            if(task.equals("feeding")) {
                for(Treatment treatment: this.treatments) {
                    if (treatment.getTreatmentID() == 1) {
                        orphanFeeding = treatment;
                        break;
                    }
                }
                if (orphanFeeding != null) {
                    if (animal.getAnimalSpecies().equals(species) && animal.getID() != orphanFeeding.getAnimalID()) {
                        bySpecies.add(animal);
                    }
                }
            }
            else if(task.equals("cleaning")) {
                if (animal.getAnimalSpecies().equals(species)) {
                    bySpecies.add(animal);
                } 
            }
        }
        return bySpecies;
    }
}
