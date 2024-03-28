package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a team of riders
 */
public class Team implements Serializable{
    private String teamName;
    private String teamDesc;
    private int teamId;
    private ArrayList<Integer> riders;//stores ID of each rider

    /**
     * Gets the name of the ream, should not be null or empty
     * @return String of the name of the team
     */
    public String getName(){
        return teamName;
    }
    /**
     * Sets the name of the team - should not be null or empty
     * @param newName String new name that abides by rules of name
     */
    public void setName(String newName){
        this.teamName = newName;
    }

    /**
     * Gets the ID of team, each team should have a unique ID
     * @return int unique ID of team
     */
    public int getID(){
        return teamId;
    }
    /**
     * Sets ID of team, should be unique and only be set at team creation to avoid issues
     * @param newId unique int ID number to identify team
     */
    public void setID(int newId){
        this.teamId = newId;
    }

    /**
     * Gets the description of team
     * @return String, may be much longer than name
     */
    public String getDesc(){
        return teamDesc;
    }
    /**
     * Sets the description of the team
     * @param newDesc String, not restricted by size or contents
     */
    public void setDesc(String newDesc){
        this.teamDesc = newDesc;
    }

    /**
     * Gets a list of rider IDs that are in the team
     * @return ArrayList of Integers, each an ID of a rider in the team
     */
    public ArrayList<Integer> getRiders(){
        return riders;
    }
    /**
     * Adds a rider to the team, rider should not already be in team
     * @param riderId Integer ID of rider to be added
     */
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
    /**
     * Removes a rider from the team, rider should already be in team
     * @param riderId Integer ID of rider to be removed
     */
    public void removeRider(Integer riderId){
        if (riders.size() > 0){                                 //checks if team has any riders to remove
            int[] riderIndex = getRiderIndex(riderId, -1, -1);  //searches for rider id
            if (riderIndex[1] == 1){                            //if rider exists in team   (see getRiderIndex, [1] == 1 -> id exists in riders)
                assert riders.get(riderIndex[0]) == riderId;    //asserts correct rider was found
                riders.remove(riderIndex[0]);                   //remove rider
            }
        }
    }

    /**
     * Gets the index of a rider in the riders ArrayList
     * Recursive binary sort on the riders arraylist, left and right int should be -1 when called from other function
     * @param riderId int ID of rider to find in the arraylist
     * @param left  used to recurse, should be set as -1 when calling function
     * @param right used to recurse, should be set as -1 when calling function
     * @return array of 2 values - array[0] is the index the item is either found at, or needs to be inserted at, array[1] == 0 if not found, and == 1 if found
     */
    private int[] getRiderIndex(int riderId, int left, int right){
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
 