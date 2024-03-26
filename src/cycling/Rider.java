package cycling;

import java.util.ArrayList;

public class Rider {

    private String RiderName;
    private int RiderID;
    private ArrayList<Integer> CompetedRaces = new ArrayList<Integer>();
    private int Team = -1;   //ID of team rider is in, -1 when rider does not have a team

    // Name
    public String getName(){
        return RiderName;
    }
    public void setName(String NewName){
        this.RiderName = NewName;
    }
    //ID
    public int getID(){
        return RiderID;
    }
    public void setID(int NewID){
        this.RiderID = NewID;
    }
    // races
    public ArrayList<Integer> getRaces(){
        return CompetedRaces;
    }
    public void setRaces(ArrayList<Integer> NewRaces){
        this.CompetedRaces = NewRaces;
    }
    //team
    public int getTeam(){
        return Team;
    }
    public void setTeam(int newTeam){
        this.Team = newTeam;
    }

    public String toString(){
        return null;//TODO add tostring
    }

    
}