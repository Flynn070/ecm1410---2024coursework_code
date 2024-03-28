package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

public class Result implements Serializable{
    private int riderId;
    private LocalTime[] checkpointTimes;
    private long totalTime;                 //total elapsed time (finish - start) in milliseconds
    //care to be taken with this value as it is a long - cannot be cast down to int


    //id
    public int getID(){
        return riderId;
    }
    public void setID(int newId){
        this.riderId = newId;
    }

    //checkpoint times
    public LocalTime[] getCheckpointTimes(){
        return checkpointTimes;
    }
    public void setCheckpointTimes(LocalTime[] newTimes){
        this.checkpointTimes = newTimes;
        this.totalTime = MILLIS.between(newTimes[0], newTimes[newTimes.length - 1]);
    }
    public void setCheckpointTime(LocalTime newTime, int checkpointReplaced){
        this.checkpointTimes[checkpointReplaced] = newTime;                                 //insert the time in the correct checkpoint location
        if (checkpointReplaced == 0 || checkpointReplaced == checkpointTimes.length-1){     //if start or end time edited
            this.totalTime = MILLIS.between(checkpointTimes[0], checkpointTimes[checkpointTimes.length - 1]);  //recalc the elapsed time
        }
    }

    //totaltime - no setter, should automatically be updated 
    public long getTotalTime(){
        return totalTime;
    }

    public String toString(){
        String checkpointTimesString = "";
        for (LocalTime n : checkpointTimes){
            checkpointTimesString += n.toString() + "\n";
        }
        return String.format("Rider ID: %s \n Total Time: %d \nIndividual Times:\n%s", riderId, totalTime, checkpointTimesString);
    }
}