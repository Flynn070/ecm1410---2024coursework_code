package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a single race
 * stores a list of ID's of associated riders and stages which should be kept accurate
 */
public class Race implements Serializable{
    private String raceName;
    private String raceDesc;
    private int raceId;
    private ArrayList<Integer> stages;  //stores ID of each stage in race
    private ArrayList<Integer> riders;  //stores ID of each rider in race

    /**
     * Gets the name of the Race - should be unique
     * @return String of the name of the race
     */
    public String getName(){
        return raceName;
    }
    /**
     * Sets the name of the Race - should not be null, empty, over 30 chars or contain whitespace
     * @param newName String new name that abides by rules of name
     */
    public void setName(String newName){
        this.raceName = newName;
    }

    /**
     * Gets the description of race
     * @return String, may be much longer than name
     */
    public String getDesc(){
        return raceDesc;
    }
    /**
     * Sets the description of the race
     * @param newDesc String, not restricted by size or contents
     */
    public void setDesc(String newDesc){
        this.raceDesc = newDesc;
    }

    /**
     * Gets the ID of race, each race should have a unique ID
     * @return int unique ID of race
     */
    public int getID(){
        return raceId;
    }
    /**
     * Sets ID of race, should be unique and only be set at race creation to avoid issues
     * @param newId unique int ID number to identify race
     */
    public void setID(int newId){
        this.raceId = newId;
    }

    /**
     * Gets ArrayList of all the stages currently contained within the race
     * ArrayList should be sorted from lowest to highest so that binary search functions correctly
     * @return ArrayList of Integers, each of which are IDs of stages
     */
    public ArrayList<Integer> getStages(){
        return stages;
    }
    /**
     * Adds a stage to a race
     * @param stageId int ID of the stage to be added to the race, the same ID should not be set multiple times
     */
    public void addStage(Integer stageId){
        if (stages.size() > 0){
            int i = stages.size()-1;    //loop variable
            while (stages.get(i) > stageId){                    //iterates down list until an ID value lower than stageId is found
                i -= 1;
            }
            stages.add(i+1, stageId);                           //insert stageId above lower value
        }
        else{
            stages.add(stageId);                                //if no stages are registered then add the first
        }
    }
    /**
     * Removes a stage from a race - checkpoints and results of stage should also be removed if stage is being removed from system
     * @param stageId int unique ID of stage
     */
    public void removeStage(int stageId){
        int StageCount = 0;
		while (this.stages.get(StageCount) != stageId && StageCount <= this.stages.size()){
			StageCount += 1;	            //iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
		if (this.stages.get(StageCount) == stageId){
			this.stages.remove(StageCount);                     //Removes the stage from the stage array
		}
    }

    /**
     * Gets list of all riders that have taken part in a race
     * @return ArrayList of Integers, each are a unique ID of a rider
     */
    public ArrayList<Integer> getRiders(){
        return riders;
    }
    /**
     * Replaces the entire list of riders - should only be used when resetting entire rider list, list should be sorted from lowest to highest ID value
     * To just add or remove a single rider, use {@link #addRider} and {@link #removeRider respectively
     * @param NewRiders
     */
    public void setRiders(ArrayList<Integer> NewRiders){
        this.riders = NewRiders;
    }
    /**
     * Adds a single rider to the race, automatically sorts, should not get duplicates
     * @param riderId int unique ID value of a rider to add
     */
    public void addRider(int riderId){
        if (riders.size() == 0){
            riders.add(riderId);
        }
        else{
            int[] InsertLocation = getRiderRaceIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in race
            if (InsertLocation[1] == 0){                                //if not yet added to race
                if (riders.size() == InsertLocation[0]){                //if id should be added at the end of the arraylist
                    riders.add(riderId);
                }
                else{
                    riders.add(InsertLocation[0], riderId);             //adds rider id in correct location
                }
            }
        }
    }
    /**
     * Removes a single rider from the race. Race should also be removed from Rider
     * @param riderId unique ID value of rider to remove from race
     */
    public void removeRider(int riderId){
        if (riders.size() > 0){                                         //checks arraylist not empty
            int[] DeleteLocation = getRiderRaceIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in race
            if (DeleteLocation[1] == 1){                                //checks rider exists in race
                riders.remove(DeleteLocation[0]);
            }
        }
    }

    /**
     * Gets the index of a rider in the rider ArrayList
     * Recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
     * @param riderId int ID of rider to find in the arraylist
     * @param left  used to recurse, should be set as -1 when calling function
     * @param right used to recurse, should be set as -1 when calling function
     * @return array of 2 values - array[0] is the index the item is either found at, or needs to be inserted at, array[1] == 0 if not found, and == 1 if found
     */
    private int[] getRiderRaceIndex(int riderId, int left, int right){

		if (left == -1){		                    // if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = riders.size();
		}

		if (left > right){
			int[] returnArray = new int[1];
            returnArray[0] = left;                                      //where id should be inserted
            returnArray[1] = 0;                                         // ID not found, needs to be inserted
            return returnArray;
		}
		int mid = left + ((right - left) / 2);

		if (riders.get(mid) == riderId){
            int[] returnArray = new int[1];
            returnArray[0] = mid;
            returnArray[1] = 1;                                         //target found, shouldnt be added again
			return returnArray;
		} else if (riders.get(mid) < riderId){
			return getRiderRaceIndex(riderId, mid + 1, right);          //search to the right of mid
		} else {    //if (riders.get(mid) > riderId)
			return getRiderRaceIndex(riderId, left, mid - 1);           // search to the left of mid
		}
    }

    //
    public String toString(){
        return String.format("%s, : \n Race ID: %s \n %s \n %d stages \n %d riders", raceName, raceId, raceDesc, stages.size(), riders.size());
    }
}
 