package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * Represents a set of results a rider can get on a stage
 * Stores the times of start, finish, and each checkpoint pass in a stage
 */
public class Result implements Serializable{
    private int riderId;
    private LocalTime[] checkpointTimes;
    private long totalTime;                 //total elapsed time (finish - start) in milliseconds
    //care to be taken with this value as it is a long - cannot be cast down to int


    /**
     * Gets the ID of the rider who got the results, each result should have a unique ID
     * @return int ID of result
     */
    public int getID(){
        return riderId;
    }
    /**
     * Sets ID of rider, should be unique and only be set at result creation to avoid issues
     * @param newId unique int ID number to identify the rider who got the results
     */
    public void setID(int newId){
        this.riderId = newId;
    }

    /**
     * Gets all of the times the rider got from start time, each checkpoint and finish
     * @return LocalTime array of times of start, checkpoint crosses, finish
     */
    public LocalTime[] getCheckpointTimes(){
        return checkpointTimes;
    }
    /**
     * Sets the times of the rider. Should have entries = num of stage checkpoints + 2
     * To set a single time, see {@link #setCheckpointTime}
     * @param newTimes Array of LocalTime values
     */
    public void setCheckpointTimes(LocalTime[] newTimes){
        this.checkpointTimes = newTimes;
        this.totalTime = MILLIS.between(newTimes[0], newTimes[newTimes.length - 1]);
    }
    /**
     * Sets the time of a single checkpoint
     * @param newTime LocalTime of the time to set
     * @param checkpointReplaced which checkpoint the time is being updated for, in order of start, checkpoints, finish
     * To change the finish time, pass in the size of the results - 1
     */
    public void setCheckpointTime(LocalTime newTime, int checkpointReplaced){
        this.checkpointTimes[checkpointReplaced] = newTime;                                 //insert the time in the correct checkpoint location
        if (checkpointReplaced == 0 || checkpointReplaced == checkpointTimes.length-1){     //if start or end time edited
            this.totalTime = MILLIS.between(checkpointTimes[0], checkpointTimes[checkpointTimes.length - 1]);  //recalc the elapsed time
        }
    }

    /**
     * Gets the total time in milliseconds a rider took on a stage from start to finish
     * @return  long of the total time in milliseconds
     * No setter for this value, it should be automatically calculated when checkpoint times are assigned
     */
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