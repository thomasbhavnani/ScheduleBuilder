/**
 * @author  380-PROJ 23
 * @version 1.0
 * @since 1.0
 */

package edu.ucalgary.oop;

public class Animal{

    private int animalID;
    private String animalNickname;
    private String animalSpecies;
    private String animalType;
    private int prepTime;
    private int cleanTime;
    private int[] feedWindows;

    // constructor

    /**
     * Constructs an Animal object with the given ID, nickname, and species.
     * Sets appropriate values for prep time, clean time, type, and feed windows based on the species.
     *
     * @param id        the ID of the animal
     * @param nickname  the nickname of the animal
     * @param species   the species of the animal
     * @throws InvalidSpeciesException if the species provided is not recognized
     */
    public Animal(int id, String nickname, String species) throws InvalidSpeciesException{
        this.animalID = id;
        this.animalNickname = nickname;
        this.animalSpecies = species;

        if (species.equals("coyote")) {
            this.prepTime = 10;
            this.cleanTime = 5;
            this.animalType = Type.CREPUSCULAR.toString();
            this.feedWindows = new int[] { 19, 20, 21 };
        }
        else if (species.equals("fox")) {
            this.prepTime = 5;
            this.cleanTime = 5;
            this.animalType = Type.NOCTURNAL.toString();
            this.feedWindows = new int[] { 0, 1, 2 };
        }
        else if (species.equals("raccoon")) {
            this.prepTime = 0;
            this.cleanTime = 5;
            this.animalType = Type.NOCTURNAL.toString();
            this.feedWindows = new int[] { 0, 1, 2 };
        }
        else if (species.equals("porcupine")) {
            this.prepTime = 0;
            this.cleanTime = 10;
            this.animalType = Type.CREPUSCULAR.toString();
            this.feedWindows = new int[] { 19, 20, 21 };
        }
        else if (species.equals("beaver")){
            this.prepTime = 0;
            this.cleanTime = 5;
            this.animalType = Type.DIURNAL.toString();
            this.feedWindows = new int[] { 8, 9, 10 };
        }
        else {
            throw new InvalidSpeciesException();
        }

    }

    // getters

     /**
     * Returns the ID of the animal.
     *
     * @return the animal ID
     */
    public int getID() {
        return this.animalID;
    }


     /**
     * Returns the nickname of the animal.
     *
     * @return the animal nickname
     */
    public String getAnimalNickname() {
        return this.animalNickname;
    }


     /**
     * Returns the species of the animal.
     *
     * @return the animal species
     */
    public String getAnimalSpecies() {
        return this.animalSpecies;
    }

     /**
     * Returns the type of the animal.
     *
     * @return the animal type
     */
    public String getAnimalType() {
        return this.animalType;
    }


     /**
     * Returns the feed windows for the animal.
     *
     * @return an array of integers representing the feed windows
     */
    public int[] getfeedWindows() {
        return this.feedWindows;
    }


    /**
     * Returns the preparation time for the animal.
     *
     * @return the prep time
     */
    public int getPrepTime() {
        return this.prepTime;
    }


    /**
     * Returns the cleaning time for the animal.
     *
     * @return the clean time
     */
    public int getCleanTime() {
        return this.cleanTime;
    }
}
