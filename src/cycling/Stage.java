package cycling;

import java.util.ArrayList;

public class Stage {
    
    private int StageID;
    private String StageName;
    private String StageDesc;
    private double StageLength;
    private ArrayList<Integer> Checkpoints;//stores ID of each checkpoint, unused if time-trial
    private StageType StageType;

    // Name functions
    public String getName(){
        return StageName;
    }
    public void setName(String NewName){
        this.StageName = NewName;
    }

    public int getID(){
        return StageID;
    }
    public void setID(int NewID){
        this.StageID = NewID;
    }

    public String getDesc(){
        return StageDesc;
    }
    public void setDesc(String NewDesc){
        this.StageDesc = NewDesc;
    }

    public double getLength(){
        return StageLength;
    }
    public void setLength(double NewLength){
        this.StageLength = NewLength;
    }

    public StageType getType(){
        return StageType;
    }
    public void setType(StageType NewType){
        this.StageType = NewType;
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
