package cycling;

import java.util.ArrayList;

public class Race {
    private String RaceName;
    private String RaceDesc;
    private int RaceID;
    private ArrayList<Integer> Stages;  //stores ID of each stage in race
    private ArrayList<Integer> Riders;  //stores ID of each rider in race

    // Name
    public String getName(){
        return RaceName;
    }
    public void setName(String NewName){
        this.RaceName = NewName;
    }
    //Desc
    public String getDesc(){
        return RaceDesc;
    }
    public void setDesc(String NewDesc){
        this.RaceDesc = NewDesc;
    }
    //ID 
    public int getID(){
        return RaceID;
    }
    public void setID(int NewID){
        this.RaceID = NewID;
    }
    //Stage
    public ArrayList<Integer> getStages(){
        return Stages;
    }
    public void addStage(Integer ID){

        if (this.Stages.size() > 0){
            Stages.add(ID);//TODO add sorting :)
        }
        else{
            Stages.add(ID);
        }
    }
    public void removeStage(int stageId){
        int StageCount = 0;
		while (this.Stages.get(StageCount) != stageId && StageCount <= this.Stages.size()){
			StageCount += 1;	//iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
		}
		if (this.Stages.get(StageCount) == stageId){
			this.Stages.remove(StageCount); //Removes the stage from the stage array
		}
    }
    //riders
    public ArrayList<Integer> getRiders(){
        return Riders;
    }
    public void setRiders(ArrayList<Integer> NewRiders){
        this.Riders = NewRiders;
    }
    public void addRider(int riderId){
        if (Riders.size() == 0){
            Riders.add(riderId);
        }
        else{
            int[] InsertLocation = getRiderRaceIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in race
            if (InsertLocation[1] == 0){  //if not yet added to race
                if (Riders.size() == InsertLocation[0]){   //if id should be added at the end of the arraylist
                    Riders.add(riderId);
                }
                else{
                    Riders.add(InsertLocation[0], riderId);    //adds rider id in correct location
                }
            }
        }
    }
    public void removeRider(int riderId){
        if (Riders.size() > 0){ //checks arraylist not empty
            int[] DeleteLocation = getRiderRaceIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in race
            if (DeleteLocation[1] == 1){    //checks rider exists in race
                Riders.remove(DeleteLocation[0]);
            }
        }
    }

    private int[] getRiderRaceIndex(int riderId, int left, int right){
        // recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
        //returns array - array[0] is the index the id is found at / needs to be inserted at
        //array[1] == 0 if not found, and == 1 if found
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = Riders.size();
		}

		if (left > right){
			int[] returnArray = new int[1];
            returnArray[0] = left;  //where id should be inserted
            returnArray[1] = 0;     // ID not found, needs to be inserted
            return returnArray;
		}
		int mid = left + ((right - left) / 2);

		if (Riders.get(mid) == riderId){
            int[] returnArray = new int[1];
            returnArray[0] = mid;
            returnArray[1] = 1; //target found, shouldnt be added again
			return returnArray;
		} else if (Riders.get(mid) < riderId){
			return getRiderRaceIndex(riderId, mid + 1, right); //search to the right of mid
		} else {    //if (Riders.get(mid) > riderId)
			return getRiderRaceIndex(riderId, left, mid - 1); // search to the left of mid
		}
    }

    //
    public String toString(){
        return String.format("%s, : \n Race ID: %s \n %s \n %d Stages", this.getName(), this.getID(), this.getDesc(), this.Stages.size());
    }
}
 