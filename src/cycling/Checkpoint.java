package cycling;

import java.util.ArrayList;

public class Checkpoint {
    
    private String CheckpointName;
    private Integer CheckpointID;
    private CheckpointType CheckpointType;

    // Name functions
    public String getName(){
        return CheckpointName;
    }
    public void setName(String NewName){
        this.CheckpointName = NewName;
    }
    //ID functions
    public Integer getID(){
        return CheckpointID;
    }

    public CheckpointType getType(){
        return CheckpointType;
    }

    
    public String toString(){
        return null;
    }
}
