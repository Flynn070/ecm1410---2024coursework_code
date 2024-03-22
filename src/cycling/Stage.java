package cycling;

import java.util.ArrayList;

public class Stage {
    
    private String StageName;
    private Integer StageID;
    private ArrayList<Integer> Checkpoints;//stores ID of each checkpoint, unused if time-trial
    private StageType StageType;

    // Name functions
    public String getName(){
        return StageName;
    }
    public void setName(String NewName){
        this.StageName = NewName;
    }
    //ID functions
    public Integer getID(){
        return StageID;
    }

    public StageType getType(){
        return StageType;
    }
    //Checkpoint functions
    public ArrayList<Integer> getCheckpoints(){
        return Checkpoints;
    }
    public void addCheckpoint(Integer ID){

        if (this.Checkpoints.size() > 0){
            Checkpoints.add(ID);//TODO add sorting :)
        }
        else{
            Checkpoints.add(ID);
        }
    }

    public String toString(){
        return null;
    }
}
