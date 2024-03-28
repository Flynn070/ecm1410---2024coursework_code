package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a single rider
 * team value will be set as -1 when the rider is not associated with a team
 */
public class Rider implements Serializable{

    private String riderName;
    private int riderId;
    private ArrayList<Integer> competedRaces = new ArrayList<Integer>();    //arraylist of IDs of races the rider has competed in
    private int team = -1;      //ID of team rider is in, -1 when rider does not have a team
    private int yearOfBirth;

    /**
     * Gets the name of the Rider, should not be null or empty
     * @return String of the name of the race
     */
    public String getName(){
        return riderName;
    }
    /**
     * Sets the name of the Rider - should not be null or empty
     * @param newName String new name that abides by rules of name
     */
    public void setName(String newName){
        this.riderName = newName;
    }

    /**
     * Gets the ID of rider, each rider should have a unique ID
     * @return int unique ID of rider
     */
    public int getID(){
        return riderId;
    }
    /**
     * Sets ID of rider, should be unique and only be set at rider creation to avoid issues
     * @param newId unique int ID number to identify rider
     */
    public void setID(int newId){
        this.riderId = newId;
    }

    /**
     * Gets an arraylist of IDs of races that the rider has competed in
     * @return ArrayList of Integer values, each the ID of a race
     */
    public ArrayList<Integer> getRaces(){
        return competedRaces;
    }
    /**
     * Sets the list of races the rider has competed in
     * @param newRaces ArrayList of Integers, each the ID of a race
     */
    public void setRaces(ArrayList<Integer> newRaces){
        this.competedRaces = newRaces;
    }

    /**
     * Gets the ID of the team the rider is associated with
     * Value of -1 if the rider is not associated with a team
     * @return int ID of associated team or -1 if not associated with a team
     */
    public int getTeam(){
        return team;
    }
    /**
     * Sets the ID of the team the rider is associated with. Riders can only be associated with 1 team at a time
     * @param newTeam int ID value of team that the rider is associated with
     */
    public void setTeam(int newTeam){
        this.team = newTeam;
    }

    /**
     * Gets year of riders birth
     * @return int > 1900
     */
    public int getBirth(){
        return yearOfBirth;
    }
    /**
     * Sets the year of the riders birth. Should not be < 1900
     * @param newBirth int > 1900
     */
    public void setBirth(int newBirth){
        this.yearOfBirth = newBirth;
    }

    public String toString(){
        return String.format("Rider Name: %s \nID: %d \n Born %d", riderName, riderId, yearOfBirth);
    }

    
}