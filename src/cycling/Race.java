package cycling;

import java.util.ArrayList;

public class Race {
    private String RaceName;
    private String RaceDesc;
    private int RaceID;
    private ArrayList<Integer> Stages;//stores ID of each 

    // Name functions
    public String getName(){
        return RaceName;
    }
    public void setName(String NewName){
        this.RaceName = NewName;
    }
    public String getDesc(){
        return RaceDesc;
    }
    public void setDesc(String NewDesc){
        this.RaceDesc = NewDesc;
    }
    //ID functions
    public int getID(){
        return RaceID;
    }
    //Checkpoint functions
    public ArrayList<Integer> getStages(){
        return Stages;
    }
    public void addStage(Integer ID){

        if (this.Stages.size() > 0){
            Stages.add(ID);//TODO add sorting :)
        }
        else{
            Stages.add(ID);
        }
    }

    public String toString(){
        return null;
    }
}
 