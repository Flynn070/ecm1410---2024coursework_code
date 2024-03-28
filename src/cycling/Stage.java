package cycling;

import java.util.ArrayList;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

import java.io.Serializable;;

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

    // Name
    public String getName(){
        return stageName;
    }
    public void setName(String newName){
        this.stageName = newName;
    }
    //ID
    public int getID(){
        return stageId;
    }
    public void setID(int newID){
        this.stageId = newID;
    }
    //Desc
    public String getDesc(){
        return stageDesc;
    }
    public void setDesc(String newDesc){
        this.stageDesc = newDesc;
    }
    //Length
    public double getLength(){
        return stageLength;
    }
    public void setLength(double newLength){
        this.stageLength = newLength;
    }
    //Race
    public int getRace(){
        return stageRace;
    }
    public void setRace(int newRace){
        this.stageRace = newRace;
    }
    //Type
    public StageType getType(){
        return stageStageType;
    }
    public void setType(StageType newType){
        this.stageStageType = newType;
    }
    //State
    public boolean getState(){
        return waitingForResults;
    }
    public void setState(boolean newState){
        this.waitingForResults = newState;
    }
    //Checkpoint 
    public ArrayList<Integer> getCheckpoints(){
        return checkpoints;
    }
    public void addCheckpoint(Integer checkpointId){
        checkpoints.add(checkpointId);                  //checkpoints added are unsorted but can be sorted when called upon
    }
    public void removeCheckpoint(int checkpointId){
        int CheckpointCount = 0;
		while (CheckpointCount <= this.checkpoints.size() && this.checkpoints.get(CheckpointCount) != checkpointId){
			CheckpointCount += 1;	    //iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
		if (this.checkpoints.get(CheckpointCount) == checkpointId){
			this.checkpoints.remove(CheckpointCount); //Removes the checkpoint from the checkpoint array
		}
    }
    //results
    public ArrayList<Result> getAllRiderResults(){
        return riderResults;
    }
    public void setAllRiderResults(ArrayList<Result> newResults){
        this.riderResults = newResults;
    }
    public LocalTime[] getRiderResults(int riderId) throws IDNotRecognisedException{
        int[] riderResultIndex = getRiderResultIndex(riderId, -1, -1);      //gets specufied rider
        if (riderResultIndex[1] == 0 ){                                     //if rider results not found
            throw new IDNotRecognisedException();
        }
        return riderResults.get(riderResultIndex[0]).getCheckpointTimes();  //returns riders times
    }
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
    public ArrayList<Result> getSortedElapsedResults(){ 
        //returns arraylist of all results ordered by elapsed time
        if (riderResults.size() > 0){                                           //checks if results have been added to stage yet
            return sortElapsedTime(riderResults);                               //returns results after sorting them
        }
        else{                                                                   //returns empty list if no results are in stage
            return new ArrayList<Result>();
        }
    }
    public ArrayList<Result> getSortedCheckpointResults(int sortCheckpoint){
        //returns results sorted by specified checkpoint time
        return sortResult(riderResults, sortCheckpoint);
    }
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
    private int[] getRiderResultIndex(int riderId, int left, int right){
        // recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
        //returns array - array[0] is the index the id is found at / needs to be inserted at
        //array[1] == 0 if not found, and == 1 if found
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

