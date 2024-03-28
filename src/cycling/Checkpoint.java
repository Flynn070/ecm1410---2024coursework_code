package cycling;

import java.io.Serializable;

public class Checkpoint implements Serializable{
    
    private Integer checkpointId;
    private CheckpointType checkpointCheckpointType;
    private Double location;
    private Double averageGradient; //used for climbs
    private Double length;
    private Integer stage;

    //ID
    public Integer getID(){
        return checkpointId;
    }
    public void setID(int newId){
        this.checkpointId = newId;
    }
    //type
    public CheckpointType getType(){
        return checkpointCheckpointType;
    }
    public void setType(CheckpointType newType){
        this.checkpointCheckpointType = newType;
    }
    //location
    public double getLocation(){
        return location;
    }
    public void setLocation(double newLocation){
        this.location = newLocation;
    }
    //average gradient
    public double getGradient(){
        return averageGradient;
    }
    public void setGradient(double newGradient){
        this.averageGradient = newGradient;
    }
    //length
    public double getLength(){
        return length;
    }
    public void setLength(double newLength){
        this.length = newLength;
    }
    //stage
    public Integer getStage(){
        return stage;
    }
    public void setStage(Integer NewStage){
        this.stage = NewStage;
    }

    public String toString(){
        String type = "";
        switch(checkpointCheckpointType){
            case SPRINT:
                type = "Intermediate Sprint";
                break;
            case HC:
                type = "Hors Categorie";
                break;
            case C1:
                type = "Category 1";
                break;
            case C2:
                type = "Category 2";
                break;
            case C3:
                type = "Category 3";
                break;
            case C4:
                type = "Category 4";
                break;
        }
        return String.format("Category: %s \nCheckpoint ID: %s \nLocation: %s \nLength: %skm \nStage ID: %d ", type, checkpointId, location, length, stage);
    }
}
