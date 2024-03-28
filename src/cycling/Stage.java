package cycling;

import java.util.ArrayList;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

import java.io.Serializable;;

/**
 * Represents a stage in a race
 * Contains the results of riders in that stage
 * Uses the {@link cycling.Result#Result()} class
 */
public class Stage implements Serializable{
    
    private int stageId;
    private String stageName;
    private String stageDesc;
    private double stageLength;
    private int stageRace;                      //id of race stage is in
    private ArrayList<Integer> checkpoints;     //stores ID of each checkpoint, unused if time-trial
    private ArrayList<Result> riderResults;     //stores each riders times, index is found by parent race's riders arraylist
    private StageType stageStageType;
    private boolean waitingForResults = false;  //true if waiting for results, false if not

    /**
     * Gets the name of theStage - should be unique
     * @return String of the name of the stage
     */
    public String getName(){
        return stageName;
    }
    /**
     * Sets the name of the Stage - should be unique, not be null, empty, over 30 chars or contain whitespace
     * @param newName String new name that abides by rules of name
     */
    public void setName(String newName){
        this.stageName = newName;
    }

    /**
     * Gets the ID of stage, each stage should have a unique ID
     * @return int unique ID of stage
     */
    public int getID(){
        return stageId;
    }
    /**
     * Sets ID of stage, should be unique and only be set at stage creation to avoid issues
     * @param newId unique int ID number to identify stage
     */
    public void setID(int newId){
        this.stageId = newId;
    }

    /**
     * Gets the description of stage
     * @return String, may be much longer than name
     */
    public String getDesc(){
        return stageDesc;
    }
    /**
     * Sets the description of the stage
     * @param newDesc String, not restricted by size or contents
     */
    public void setDesc(String newDesc){
        this.stageDesc = newDesc;
    }

    /**
     * Gets the length of a stage
     * @return double representation of the stage in km
     */
    public double getLength(){
        return stageLength;
    }
    /**
     * Sets the length of a stage in km
     * @param newLength double length of stage
     */
    public void setLength(double newLength){
        this.stageLength = newLength;
    }

    /**
     * Gets the ID value of the race the stage belongs to
     * @return int unique ID of race
     */
    public int getRace(){
        return stageRace;
    }
    /**
     * Sets the ID value of the race the stage belongs to
     * Should typically only be set on stage creation to avoid later issues
     * @param newRace int unique ID of race
     */
    public void setRace(int newRace){
        this.stageRace = newRace;
    }

    /**
     * Gets the type of stage, uses {@link cycling.StageType#StageType} enum
     * Can be flat, medium mountain, high mountain, or time trial
     * @return StageType
     */
    public StageType getType(){
        return stageStageType;
    }
    /**
     * Sets the type of stage, uses {@link cycling.StageType#StageType} enum
     * Can be flat, medium mountain, high mountain, or time trial
     * @param newType StageType
     */
    public void setType(StageType newType){
        this.stageStageType = newType;
    }

    /**
     * Gets the state of Stage - whether setup has been concluded and it is waiting for results or not
     * Default false
     * @return boolean true if waiting for results, false if not
     */
    public boolean getState(){
        return waitingForResults;
    }
    /**
     * Gets the state of Stage. Should not be changed from true back to false
     * Default false
     * @param newState boolean true if waiting for results, false if not
     */
    public void setState(boolean newState){
        this.waitingForResults = newState;
    }

    /**
     * Gets a list of all the IDs of the checkpoints within a stage
     * @return ArrayList of Integers, each are the unique ID of a checkpoint
     */
    public ArrayList<Integer> getCheckpoints(){
        return checkpoints;
    }
    /**
     * Adds a checkpoint to the stage
     * Checkpoints are not sorted initially
     * @param checkpointId Integer ID value of checkpoint to add to stage
     */
    public void addCheckpoint(Integer checkpointId){
        checkpoints.add(checkpointId);                  //checkpoints added are unsorted but can be sorted when called upon
    }
    /**
     * Removes a checkpoint from the stage
     * ID of checkpoint not in stage should not be given
     * Checkpoint should also be removed from system after removal from stage
     * @param checkpointId int ID of checkpoint to be removed
     */
    public void removeCheckpoint(int checkpointId){
        int CheckpointCount = 0;
		while (CheckpointCount <= this.checkpoints.size() && this.checkpoints.get(CheckpointCount) != checkpointId){
			CheckpointCount += 1;	    //iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
        assert this.checkpoints.get(CheckpointCount) == checkpointId;
		this.checkpoints.remove(CheckpointCount); //Removes the checkpoint from the checkpoint array
    }

    /**
     * Gets a list of the results of all riders entered in this stage
     * For adjusted results, {@link #getAdjustedRiderResults}
     * @return ArrayList of type {@link cycling.Result#Result}
     */
    public ArrayList<Result> getAllRiderResults(){
        return riderResults;
    }
    /**
     * Overwrites all results of this stage
     * To add a single rider's results or remove a rider from a stage, use {@link #addRiderResults} and {@link #removeRider} respectively
     * @param newResults ArrayList of type {@link cycling.Result#Result}
     */
    public void setAllRiderResults(ArrayList<Result> newResults){
        this.riderResults = newResults;
    }
    /**
     * Gets the Result of a specified rider in the stage
     * @param riderId ID value of rider to get results for
     * @return {@link cycling.Result#Result} of specified rider
     * @throws IDNotRecognisedException when rider is not found in the stage
     */
    public LocalTime[] getRiderResults(int riderId) throws IDNotRecognisedException{
        int[] riderResultIndex = getRiderResultIndex(riderId, -1, -1);      //gets specufied rider
        if (riderResultIndex[1] == 0 ){                                     //if rider results not found
            throw new IDNotRecognisedException();
        }
        assert riderResults.get(riderResultIndex[0]).getID() == riderId;
        return riderResults.get(riderResultIndex[0]).getCheckpointTimes();  //returns riders times
    }
    /**
     * Adds a single rider's results to the stage. Needs to be put into a Result
     * @param newResult Results of rider in {@link cycling.Result#Result}
     * @throws DuplicatedResultException when rider's results already exist in stage
     */
    public void addRiderResults(Result newResult) throws DuplicatedResultException{
        if (riderResults.size() == 0){
            riderResults.add(newResult);
        }
        else{
            int[] insertLocation = getRiderResultIndex(newResult.getID(), -1, -1);  //gets index of where rider should be inserted and whether rider already exists in stage
            if (insertLocation[1] == 0){                                            //if not yet added to stage
                if (riderResults.size() == insertLocation[0]){                      //if id should be added at the end of the arraylist
                    riderResults.add(newResult);
                }
                else{
                    riderResults.add(insertLocation[0], newResult);                 //adds rider id in correct location
                }
            }
            else{                                                                   //if rider has already been added to stage
                throw new DuplicatedResultException();
            }
        }
    }
    /**
     * Removes a specified rider and their results from the stage
     * @param riderId ID value of rider to remove 
     * @throws IDNotRecognisedException when rider does not exist in stage
     */
    public void removeRider(int riderId) throws IDNotRecognisedException{
        if (riderResults.size() > 0){                                       //checks arraylist not empty
            int[] deleteLocation = getRiderResultIndex(riderId, -1, -1);    //gets index of where rider should be inserted and whether rider already exists in stage
            if (deleteLocation[1] == 1){                                    //checks rider exists in stage
                riderResults.remove(deleteLocation[0]);
            }
            else{                                                           //if rider not found throw exception
                throw new IDNotRecognisedException();
            }
        }
    }

    /**
     * Gets results of all riders with times adjusted for General Classification
     * For unadjusted times, see {@link #getAllRiderResults}
     * @return ArrayList of type {@link cycling.Result#Result}
     */
    public ArrayList<Result> getAdjustedRiderResults(){ //this uses the finishing times - assumes riders all start at the same time, wouldnt work for TT but not needed
        // returns arraylist of results ordered by finish times with times adjusted
        ArrayList<Result> adjustedTimes = riderResults;
        adjustedTimes = sortResult(adjustedTimes, checkpoints.size() + 1);      //sorts the results by time of finish line cross
        int finalCheckpoint = checkpoints.size() + 1;                           //gets the finish line times
        int i, j;   //looping variables
        int firstAdjustmentIndex = -1;
        for (i=0;i<adjustedTimes.size()-1;i++){                                 //from start to end of list
            while (i < adjustedTimes.size()-1 && MILLIS.between(adjustedTimes.get(i).getCheckpointTimes()[finalCheckpoint], adjustedTimes.get(i+1).getCheckpointTimes()[finalCheckpoint]) < 1000){
                //checks if next time is within a second of currently checked time
                if (firstAdjustmentIndex == -1){        //if this is the first times found under 1 second, record the fastest time
                    firstAdjustmentIndex = i;
                }
                i += 1;                                                         //advances to next item 
            }
            if (firstAdjustmentIndex != -1){                                    //if any adjustments need to be made
                LocalTime AdjustedTime = adjustedTimes.get(firstAdjustmentIndex).getCheckpointTimes()[finalCheckpoint]; //gets fastest time within adjustment
                for (j=firstAdjustmentIndex+1;j<=i;j++){                        //starts replacing values at the first result within a second
                    adjustedTimes.get(j).setCheckpointTime(AdjustedTime, finalCheckpoint);  //replaces the final checkpoint time
                }
                firstAdjustmentIndex = -1;                                      //resets the adjustment to be used again
            }
        }

        return adjustedTimes;
    }
    /**
     * Gets all results in stage like {@link #getAllRiderResults()}, except sorted by total elapsed time
     * @return ArrayList of type {@link cycling.Result#Result}, sorted by Result.getTotalTime
     */
    public ArrayList<Result> getSortedElapsedResults(){ 
        //returns arraylist of all results ordered by elapsed time
        if (riderResults.size() > 0){                                           //checks if results have been added to stage yet
            return sortElapsedTime(riderResults);                               //returns results after sorting them
        }
        else{                                                                   //returns empty list if no results are in stage
            return new ArrayList<Result>();
        }
    }
    /**
     * Gets results sorted by specified checkpoint time, in order of checkpoints
     * @param sortCheckpoint int of which checkpoint to sort by (0 is start time, last value is finish time)
     * @return ArrayList of type {@link cycling.Result#Result}
     */
    public ArrayList<Result> getSortedCheckpointResults(int sortCheckpoint){
        //returns results sorted by specified checkpoint time
        return sortResult(riderResults, sortCheckpoint);
    }
    /**
     * Sorts Results based on a specified checkpoint
     * Used for {@link #getSortedCheckpointResults}
     * @param resultList ArrayList of type {@link cycling.Result#Result} to be sorted
     * @param sortCheckpoint int of which checkpoint to sort by (0 is start time, last value is finish time)
     * @return  ArrayList of type {@link cycling.Result#Result}
     */
    private ArrayList<Result> sortResult(ArrayList<Result> resultList, int sortCheckpoint){
        if (resultList.size() > 1){                                             //checking if list is too small to be sorted
            int i, j;
            for (i=1; i<resultList.size(); i++){                                //loops from second element of list to end
                Result removed = resultList.remove(i);                          //removes selected item from arraylist to reinsert in correct location
                j = i - 1;                                                      //starts by checking previous item
                while (j >= 0 && removed.getCheckpointTimes()[sortCheckpoint].isBefore(resultList.get(j).getCheckpointTimes()[sortCheckpoint])){  //loops until time faster than current item is found or end of list is found
                    j -= 1;                                                     //moves down items until an item with lower time or end of list is found
                }
                resultList.add(j+1, removed);                                   //adds the result into place after the faster time
            }
        }
        return resultList;
    }
    private ArrayList<Result> sortElapsedTime(ArrayList<Result> resultList){
        if (resultList.size() > 1){                                             //checking if list is too small to be sorted
            int i, j;
            for (i=1; i<resultList.size(); i++){                                //loops from second element of list to end
                Result removed = resultList.remove(i);
                j = i - 1;
                while (j >= 0 && removed.getTotalTime() < resultList.get(j).getTotalTime()){  //loops until time faster than current item is found or end of list is found
                    j -= 1;
                }
                resultList.add(j+1, removed);                                   //adds the result into place after the faster time
            }
        }
        return resultList;
    }
    /**
     * Gets the index of a rider in the riderResults ArrayList
     * Recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
     * @param riderId int ID of rider to find in the arraylist
     * @param left  used to recurse, should be set as -1 when calling function
     * @param right used to recurse, should be set as -1 when calling function
     * @return array of 2 values - array[0] is the index the item is either found at, or needs to be inserted at, array[1] == 0 if not found, and == 1 if found
     */
    private int[] getRiderResultIndex(int riderId, int left, int right){

		if (left == -1){		                        // if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = riderResults.size();
		}

		if (left > right){
			int[] returnArray = new int[1];
            returnArray[0] = left;                                              //where id should be inserted
            returnArray[1] = 0;                                                 // ID not found, needs to be inserted
            return returnArray;
		}
		int mid = left + ((right - left) / 2);

		if (riderResults.get(mid).getID() == riderId){                          //checks if id is found
            int[] returnArray = new int[1];
            returnArray[0] = mid;
            returnArray[1] = 1;                                                 //target found, shouldnt be added again
			return returnArray;
		} else if (riderResults.get(mid).getID() < riderId){
			return getRiderResultIndex(riderId, mid + 1, right);                //search to the right of mid
		} else {    //if (riderResults.get(mid).getID > riderId)
			return getRiderResultIndex(riderId, left, mid - 1);                 // search to the left of mid
		}
    }
    
    public String toString(){
        String type = "";
        switch(stageStageType){
            case TT:
                type = "Time Trial";
                break;
            case FLAT:
                type = "Flat";
                break;
            case MEDIUM_MOUNTAIN:
                type = "Medium Mountain";
                break;
            case HIGH_MOUNTAIN:
                type = "High Mountain";
                break;
        }
        return String.format("%d: %s \n%s \n%s \n%dkm \n%d checkpoints", stageId, stageName, type, stageDesc, stageLength, checkpoints.size());
    }
}

