package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Race implements Serializable{
    private String raceName;
    private String raceDesc;
    private int raceId;
    private ArrayList<Integer> stages;  //stores ID of each stage in race
    private ArrayList<Integer> riders;  //stores ID of each rider in race

    // Name
    public String getName(){
        return raceName;
    }
    public void setName(String NewName){
        this.raceName = NewName;
    }
    //Desc
    public String getDesc(){
        return raceDesc;
    }
    public void setDesc(String NewDesc){
        this.raceDesc = NewDesc;
    }
    //ID 
    public int getID(){
        return raceId;
    }
    public void setID(int NewID){
        this.raceId = NewID;
    }
    //Stage
    public ArrayList<Integer> getStages(){
        return stages;
    }
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
    public void removeStage(int stageId){
        int StageCount = 0;
		while (this.stages.get(StageCount) != stageId && StageCount <= this.stages.size()){
			StageCount += 1;	            //iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
		if (this.stages.get(StageCount) == stageId){
			this.stages.remove(StageCount);                     //Removes the stage from the stage array
		}
    }
    //riders
    public ArrayList<Integer> getRiders(){
        return riders;
    }
    public void setRiders(ArrayList<Integer> NewRiders){
        this.riders = NewRiders;
    }
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
    public void removeRider(int riderId){
        if (riders.size() > 0){                                         //checks arraylist not empty
            int[] DeleteLocation = getRiderRaceIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in race
            if (DeleteLocation[1] == 1){                                //checks rider exists in race
                riders.remove(DeleteLocation[0]);
            }
        }
    }

    private int[] getRiderRaceIndex(int riderId, int left, int right){
        // recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
        //returns array - array[0] is the index the id is found at / needs to be inserted at
        //array[1] == 0 if not found, and == 1 if found
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
 