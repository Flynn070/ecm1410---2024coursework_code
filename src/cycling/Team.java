package cycling;

import java.util.ArrayList;

public class Team {
    private String TeamName;
    private Integer TeamID;
    private ArrayList<Integer> Riders;//stores ID of each rider

    // Name functions
    public String getName(){
        return TeamName;
    }
    public void setName(String NewName){
        this.TeamName = NewName;
    }
    //ID functions
    public Integer getID(){
        return TeamID;
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
        return null;
    }
}
 