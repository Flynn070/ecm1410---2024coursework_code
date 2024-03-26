package cycling;

import java.util.ArrayList;

public class Stage {
    
    private int StageID;
    private String StageName;
    private String StageDesc;
    private double StageLength;
    private int StageRace;  //id of race stage is in
    private ArrayList<Integer> Checkpoints;//stores ID of each checkpoint, unused if time-trial
    private StageType StageType;
    private boolean WaitingForResults = false;  //true if waiting for results, false if not

    // Name
    public String getName(){
        return StageName;
    }
    public void setName(String NewName){
        this.StageName = NewName;
    }
    //ID
    public int getID(){
        return StageID;
    }
    public void setID(int NewID){
        this.StageID = NewID;
    }
    //Desc
    public String getDesc(){
        return StageDesc;
    }
    public void setDesc(String NewDesc){
        this.StageDesc = NewDesc;
    }
    //Length
    public double getLength(){
        return StageLength;
    }
    public void setLength(double NewLength){
        this.StageLength = NewLength;
    }
    //Race
    public int getRace(){
        return StageRace;
    }
    public void setRace(int NewRace){
        this.StageRace = NewRace;
    }
    //Type
    public StageType getType(){
        return StageType;
    }
    public void setType(StageType NewType){
        this.StageType = NewType;
    }
    //State
    public boolean getState(){
        return WaitingForResults;
    }
    public void setState(boolean NewState){
        this.WaitingForResults = NewState;
    }
    //Checkpoint 
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
    public void removeCheckpoint(int checkpointId){
        int CheckpointCount = 0;
		while (this.Checkpoints.get(CheckpointCount) != checkpointId && CheckpointCount <= this.Checkpoints.size()){
			CheckpointCount += 1;	//iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
		if (this.Checkpoints.get(CheckpointCount) == checkpointId){
			this.Checkpoints.remove(CheckpointCount); //Removes the checkpoint from the checkpoint array
		}
    }

    public String toString(){
        return null;// TODO add tostring
    }
}
