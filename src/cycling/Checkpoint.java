package cycling;

import java.util.ArrayList;

public class Checkpoint {
    
    private Integer CheckpointID;
    private CheckpointType CheckpointType;
    private Double Location;
    private Double AverageGradient; //used for climbs
    private Double Length;
    private Integer Stage;

    //ID
    public Integer getID(){
        return CheckpointID;
    }
    public void setID(int newId){
        this.CheckpointID = newId;
    }
    //type
    public CheckpointType getType(){
        return CheckpointType;
    }
    public void setType(CheckpointType newType){
        this.CheckpointType = newType;
    }
    //location
    public double getLocation(){
        return Location;
    }
    public void setLocation(double newLocation){
        this.Location = newLocation;
    }
    //average gradient
    public double getGradient(){
        return AverageGradient;
    }
    public void setGradient(double newGradient){
        this.Location = newGradient;
    }
    //length
    public double getLength(){
        return Length;
    }
    public void setLength(double newLength){
        this.Location = newLength;
    }
    //stage
    public Integer getStage(){
        return Stage;
    }
    public void setStage(Integer NewStage){
        this.Stage = NewStage;
    }

    public String toString(){
        return null;//TODO add tostring
    }
}
