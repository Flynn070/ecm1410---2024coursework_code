package cycling;

import java.util.ArrayList;

public class Team {
    private String TeamName;
    private String TeamDesc;
    private Integer TeamID;
    private ArrayList<Integer> Riders;//stores ID of each rider

    // Name
    public String getName(){
        return TeamName;
    }
    public void setName(String NewName){
        this.TeamName = NewName;
    }
    //ID
    public Integer getID(){
        return TeamID;
    }
    public void setID(int newId){
        this.TeamID = newId;
    }
    //description
    public String getDesc(){
        return TeamDesc;
    }
    public void setDesc(String newDesc){
        this.TeamDesc = newDesc;
    }
    //Checkpoint functions
    public ArrayList<Integer> getRiders(){
        return Riders;
    }
    public void addRider(Integer ID){

        if (this.Riders.size() > 0){
            Riders.add(ID);//TODO add sorting :)
        }
        else{
            Riders.add(ID);
        }
    }

    public String toString(){
        return null;//TODO add tostring
    }
}
 