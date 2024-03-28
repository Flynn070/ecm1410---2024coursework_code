package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable{
    private String teamName;
    private String teamDesc;
    private Integer teamId;
    private ArrayList<Integer> riders;//stores ID of each rider

    // Name
    public String getName(){
        return teamName;
    }
    public void setName(String NewName){
        this.teamName = NewName;
    }
    //ID
    public Integer getID(){
        return teamId;
    }
    public void setID(int newId){
        this.teamId = newId;
    }
    //description
    public String getDesc(){
        return teamDesc;
    }
    public void setDesc(String newDesc){
        this.teamDesc = newDesc;
    }
    //riders
    public ArrayList<Integer> getRiders(){
        return riders;
    }
    public void addRider(Integer riderId){
        if (riders.size() == 0){                                    //checks riders is not empty
            riders.add(riderId);
        }
        else{
            int[] insertLocation = getRiderIndex(riderId, -1, -1);  //gets index of where rider should be inserted and whether rider already exists in stage
            if (insertLocation[1] == 0){                            //if not yet added to team, shouldnt be but prevents possible duplicates
                if (riders.size() == insertLocation[0]){            //if id should be added at the end of the arraylist
                    riders.add(riderId);
                }
                else{
                    riders.add(insertLocation[0], riderId);         //adds rider id in correct location
                }
            }
        }
    }
    public void removeRider(Integer riderId){
        if (riders.size() > 0){                                 //checks if team has any riders to remove
            int[] riderIndex = getRiderIndex(riderId, -1, -1);  //searches for rider id
            if (riderIndex[1] == 1){                            //if rider exists in team   (see getRiderIndex, [1] -> id exists in riders)
                riders.remove(riderIndex[0]);                   //remove rider
            }
        }
    }
    private int[] getRiderIndex(int riderId, int left, int right){
        // recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
        //returns array - array[0] is the index the id is found at
        //array[1] == 0 if not found, and == 1 if found
		if (left == -1){		                // if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = riders.size();
		}

		if (left > right){
			int[] returnArray = new int[1];
            returnArray[0] = left;                              //where id should be inserted
            returnArray[1] = 0;                                 // ID not found
            return returnArray;
		}
		int mid = left + ((right - left) / 2);

		if (riders.get(mid) == riderId){
            int[] returnArray = new int[1];
            returnArray[0] = mid;
            returnArray[1] = 1;                                 //target found, can be deleted
			return returnArray;
		} else if (riders.get(mid) < riderId){
			return getRiderIndex(riderId, mid + 1, right);      //search to the right of mid
		} else {    //if (riders.get(mid) > riderId)
			return getRiderIndex(riderId, left, mid - 1);       // search to the left of mid
		}
    }

    public String toString(){
        return String.format("%d: %s \n %s", teamId, teamName, teamDesc);
    }
}
 