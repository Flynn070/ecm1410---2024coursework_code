package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Rider implements Serializable{

    private String riderName;
    private int riderId;
    private ArrayList<Integer> competedRaces = new ArrayList<Integer>();    //arraylist of IDs of races the rider has competed in
    private int team = -1;      //ID of team rider is in, -1 when rider does not have a team
    private int yearOfBirth;

    // Name
    public String getName(){
        return riderName;
    }
    public void setName(String newName){
        this.riderName = newName;
    }
    //ID
    public int getID(){
        return riderId;
    }
    public void setID(int newId){
        this.riderId = newId;
    }
    // races
    public ArrayList<Integer> getRaces(){
        return competedRaces;
    }
    public void setRaces(ArrayList<Integer> newRaces){
        this.competedRaces = newRaces;
    }
    //team
    public int getTeam(){
        return team;
    }
    public void setTeam(int newTeam){
        this.team = newTeam;
    }
    //year
    public int getBirth(){
        return yearOfBirth;
    }
    public void setBirth(int newBirth){
        this.yearOfBirth = newBirth;
    }

    public String toString(){
        return String.format("Rider Name: %s \nID: %d \n Born %d", riderName, riderId, yearOfBirth);
    }

    
}