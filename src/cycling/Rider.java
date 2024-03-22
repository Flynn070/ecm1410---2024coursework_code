package cycling;

import java.util.ArrayList;

public class Rider {

    private String RiderName;
    private Integer RiderID;

// Name functions
    public String getName(){
        return RiderName;
    }
    public void setName(String NewName){
        this.RiderName = NewName;
    }
    //ID functions
    public Integer getID(){
        return RiderID;
    }

    public String toString(){
        return null;
    }
}