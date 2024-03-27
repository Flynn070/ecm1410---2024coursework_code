package cycling;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

public class Result {
    private int RiderID;
    private LocalTime[] CheckpointTimes;
    private long TotalTime;  //total elapsed time (finish - start) in milliseconds
    //care to be taken with this value as it is a long - cannot be cast down to int


    //id
    public int getID(){
        return RiderID;
    }
    public void setID(int newId){
        this.RiderID = newId;
    }

    //checkpoint times
    public LocalTime[] getCheckpointTimes(){
        return CheckpointTimes;
    }
    public void setCheckpointTimes(LocalTime[] newTimes){
        this.CheckpointTimes = newTimes;
        this.TotalTime = MILLIS.between(newTimes[0], newTimes[newTimes.length - 1]);
    }
    public void setCheckpointTime(LocalTime newTime, int CheckpointReplaced){
        this.CheckpointTimes[CheckpointReplaced] = newTime;
        if (CheckpointReplaced == 0 || CheckpointReplaced == CheckpointTimes.length-1){ //if start or end time edited
            this.TotalTime = MILLIS.between(CheckpointTimes[0], CheckpointTimes[CheckpointTimes.length - 1]);  //recalc the elapsed time
        }
    }

    //totaltime
    public long getTotalTime(){
        return TotalTime;
    }
}