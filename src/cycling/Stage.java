package cycling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;;

public class Stage {
    
    private int StageID;
    private String StageName;
    private String StageDesc;
    private double StageLength;
    private int StageRace;  //id of race stage is in
    private ArrayList<Integer> Checkpoints;//stores ID of each checkpoint, unused if time-trial
    private ArrayList<Result> RiderResults;  //stores each riders times, index is found by parent race's riders arraylist
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
        Checkpoints.add(ID);    //checkpoints added are unsorted but can be sorted when called upon
    }
    public void removeCheckpoint(int checkpointId){
        int CheckpointCount = 0;
		while (CheckpointCount <= this.Checkpoints.size() && this.Checkpoints.get(CheckpointCount) != checkpointId){
			CheckpointCount += 1;	//iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
		if (this.Checkpoints.get(CheckpointCount) == checkpointId){
			this.Checkpoints.remove(CheckpointCount); //Removes the checkpoint from the checkpoint array
		}
    }
    //results
    public ArrayList<Result> getAllRiderResults(){
        return RiderResults;
    }
    public void setAllRiderResults(ArrayList<Result> NewResults){
        this.RiderResults = NewResults;
    }
    public LocalTime[] getRiderResults(int riderId) throws IDNotRecognisedException{
        int[] RiderResultIndex = getRiderResultIndex(riderId, -1, -1);
        if (RiderResultIndex[1] == 0 ){ //if rider results not found
            throw new IDNotRecognisedException();
        }
        return RiderResults.get(RiderResultIndex[0]).getCheckpointTimes();  //returns riders times
    }
    public void addRiderResults(Result newResult) throws DuplicatedResultException{
        if (RiderResults.size() == 0){
            RiderResults.add(newResult);
        }
        else{
            int[] InsertLocation = getRiderResultIndex(newResult.getID(), -1, -1);  //gets index of where rider should be inserted and whether rider already exists in stage
            if (InsertLocation[1] == 0){  //if not yet added to stage
                if (RiderResults.size() == InsertLocation[0]){   //if id should be added at the end of the arraylist
                    RiderResults.add(newResult);
                }
                else{
                    RiderResults.add(InsertLocation[0], newResult);    //adds rider id in correct location
                }
            }
            else{
                throw new DuplicatedResultException();
            }
        }
    }
    public void removeRider(int riderId) throws IDNotRecognisedException{
        if (RiderResults.size() > 0){ //checks arraylist not empty
            int[] DeleteLocation = getRiderResultIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in stage
            if (DeleteLocation[1] == 1){    //checks rider exists in stage
                RiderResults.remove(DeleteLocation[0]);
            }
            else{   //if rider not found
                throw new IDNotRecognisedException();
            }
        }
    }

    public ArrayList<Result> getAdjustedRiderResults(){ //this uses the finishing times - assumes riders all start at the same time, wouldnt work for TT but not needed
        // returns arraylist of results ordered by finish times with times adjusted
        ArrayList<Result> AdjustedTimes = RiderResults;
        AdjustedTimes = sortResult(AdjustedTimes, Checkpoints.size() + 1);  //sorts the results by time of finish line cross
        int finalCheckpoint = Checkpoints.size() + 1;   //gets the finish line times
        int i, j;   //looping variables
        int FirstAdjustmentIndex = -1;
        for (i=0;i<AdjustedTimes.size()-1;i++){  //from start to end of list
            while (i < AdjustedTimes.size()-1 && MILLIS.between(AdjustedTimes.get(i).getCheckpointTimes()[finalCheckpoint], AdjustedTimes.get(i+1).getCheckpointTimes()[finalCheckpoint]) < 1000){
                //checks if next time is within a second of currently checked time
                if (FirstAdjustmentIndex == -1){ //if this is the first times found under 1 second, record the fastest time
                    FirstAdjustmentIndex = i;
                }
                i += 1; //advances to next item 
            }
            if (FirstAdjustmentIndex != -1){    //if any adjustments need to be made
                LocalTime AdjustedTime = AdjustedTimes.get(FirstAdjustmentIndex).getCheckpointTimes()[finalCheckpoint]; //gets fastest time within adjustment
                for (j=FirstAdjustmentIndex+1;j<=i;j++){  //starts replacing values at the first result within a second
                    AdjustedTimes.get(j).setCheckpointTime(AdjustedTime, finalCheckpoint);  //replaces the final checkpoint time
                }
                FirstAdjustmentIndex = -1; //resets the adjustment
            }
        }

        return AdjustedTimes;
    }
    public ArrayList<Result> getSortedElapsedResults(){ 
        //returns arraylist of all results ordered by elapsed time
        if (RiderResults.size() > 0){
            return sortElapsedTime(RiderResults);
        }
        else{   //returns empty list if no results are in stage
            return new ArrayList<Result>();
        }
    }
    public ArrayList<Result> getSortedCheckpointResults(int sortCheckpoint){
        //returns results sorted by specified checkpoint time
        return sortResult(RiderResults, sortCheckpoint);
    }
    private ArrayList<Result> sortResult(ArrayList<Result> resultList, int sortCheckpoint){
        if (resultList.size() > 1){    //checking if list is too small to be sorted
            int i, j;
            for (i=1; i<resultList.size(); i++){    //loops from second element of list to end
                Result removed = resultList.remove(i);
                j = i - 1;
                while (j >= 0 && removed.getCheckpointTimes()[sortCheckpoint].isBefore(resultList.get(j).getCheckpointTimes()[sortCheckpoint])){  //loops until time faster than current item is found or end of list is found
                    j -= 1;
                }
                resultList.add(j+1, removed);   //adds the result into place after the faster time
            }
        }
        return resultList;
    }
    private ArrayList<Result> sortElapsedTime(ArrayList<Result> resultList){
        if (resultList.size() > 1){    //checking if list is too small to be sorted
            int i, j;
            for (i=1; i<resultList.size(); i++){    //loops from second element of list to end
                Result removed = resultList.remove(i);
                j = i - 1;
                while (j >= 0 && removed.getTotalTime() < resultList.get(j).getTotalTime()){  //loops until time faster than current item is found or end of list is found
                    j -= 1;
                }
                resultList.add(j+1, removed);   //adds the result into place after the faster time
            }
        }
        return resultList;
    }
    private int[] getRiderResultIndex(int riderId, int left, int right){
        // recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
        //returns array - array[0] is the index the id is found at / needs to be inserted at
        //array[1] == 0 if not found, and == 1 if found
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = RiderResults.size();
		}

		if (left > right){
			int[] returnArray = new int[1];
            returnArray[0] = left;  //where id should be inserted
            returnArray[1] = 0;     // ID not found, needs to be inserted
            return returnArray;
		}
		int mid = left + ((right - left) / 2);

		if (RiderResults.get(mid).getID() == riderId){
            int[] returnArray = new int[1];
            returnArray[0] = mid;
            returnArray[1] = 1; //target found, shouldnt be added again
			return returnArray;
		} else if (RiderResults.get(mid).getID() < riderId){
			return getRiderResultIndex(riderId, mid + 1, right); //search to the right of mid
		} else {    //if (RiderResults.get(mid).getID > riderId)
			return getRiderResultIndex(riderId, left, mid - 1); // search to the left of mid
		}
    }
    
    public String toString(){
        return null;// TODO add tostring
    }
}

