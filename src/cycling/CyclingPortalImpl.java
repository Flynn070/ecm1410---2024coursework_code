package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.MILLIS;
import java.util.Arrays;
import java.util.ArrayList;

public class CyclingPortalImpl implements MiniCyclingPortal{
	//ArrayLists of each type of object
	private ArrayList<Race> raceArray = new ArrayList<Race>();
	private ArrayList<Stage> stageArray = new ArrayList<Stage>();
	private ArrayList<Checkpoint> checkpointArray = new ArrayList<Checkpoint>();
	private ArrayList<Rider> riderArray = new ArrayList<Rider>();
	private ArrayList<Team> teamArray = new ArrayList<Team>();


	private int getRaceIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the race arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = raceArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); 						// ID not found
		}
		int mid = left + ((right - left) / 2);

		if (raceArray.get(mid).getID() == target){
			return mid; 												//target found
		} else if (raceArray.get(mid).getID() < target){
			return getRaceIndex(target, mid + 1, right); 				//search to the right of mid
		} else {														//if (raceArray.get(mid).getID() > target)
			return getRaceIndex(target, left, mid - 1); 				// search to the left of mid
		}
	}
	private int getStageIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the stage arraylist, left and right int should be -1 when called from other function
		if (left == -1){			// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = stageArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); 						// ID not found
		}
		int mid = left + ((right - left) / 2);

		if (stageArray.get(mid).getID() == target){
			return mid; 												//target found
		} else if (stageArray.get(mid).getID() < target){
			return getStageIndex(target, mid + 1, right); 				//search to the right of mid
		} else {														//if (stageArray.get(mid).getID() > target)
			return getStageIndex(target, left, mid - 1); 				// search to the left of mid
		}
	}
	private int getCheckpointIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the checkpoint arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = checkpointArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); 						// ID not found
		}
		int mid = left + ((right - left) / 2);

		if (checkpointArray.get(mid).getID() == target){
			return mid; 												//target found
		} else if (checkpointArray.get(mid).getID() < target){
			return getCheckpointIndex(target, mid + 1, right); 			//search to the right of mid
		} else {														//if (checkpointArray.get(mid).getID() > target)
			return getCheckpointIndex(target, left, mid - 1); 			// search to the left of mid
		}
	}
	private int getRiderIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
		if (left == -1){						// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = riderArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); 					// ID not found
		}
		int mid = left + ((right - left) / 2);

		if (riderArray.get(mid).getID() == target){
			return mid; //target found
		} else if (riderArray.get(mid).getID() < target){
			return getRiderIndex(target, mid + 1, right); 			//search to the right of mid
		} else {													//if (riderArray.get(mid).getID() > target)
			return getRiderIndex(target, left, mid - 1); 			// search to the left of mid
		}
	}
	private int getTeamIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the team arraylist, left and right int should be -1 when called from other function
		if (left == -1){						// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = teamArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); 					// ID not found
		}
		int mid = left + ((right - left) / 2);

		if (teamArray.get(mid).getID() == target){
			return mid; 											//target found
		} else if (teamArray.get(mid).getID() < target){
			return getTeamIndex(target, mid + 1, right); 			//search to the right of mid
		} else if (teamArray.get(mid).getID() > target){
			return getTeamIndex(target, left, mid - 1); 			// search to the left of mid
		}
		return 0;
	}

	private void nameValidCheck(String name)  throws InvalidNameException{
		if(name == null){ //checks for invalid names
            throw new InvalidNameException("Name must not be null");
        }
        else if(name == ""){
            throw new InvalidNameException("Name must not be empty");
        }
        else if(name.length() > 30){
            throw new InvalidNameException("Name must not be over 30 characters");
        }
        else if(name.contains(" ")){
            throw new InvalidNameException("Name must not contain whitespace");
        }
	}
	private int[] intArrayListToArray(ArrayList<Integer> arrayL){
		int[] returnList = new int[arrayL.size()];			//creates array
		int loopCount = 0;									//loop variable
		for (Integer n : arrayL){							//loops throough arraylist assigning each value to array
			returnList[loopCount++] = n;
		}
		return returnList;
	}
	private ArrayList<Integer> getOrderedCheckpoints(int stageId) throws IDNotRecognisedException{
		//ordering checkpoints by location in race
		ArrayList<Integer> checkpoints = stageArray.get(getStageIndex(stageId, -1, -1)).getCheckpoints();	//ids of checkpoints in stage
		int i, j;		//loop variables
		for (i=1; i<checkpoints.size(); i++){    									//loops from second element of list to end
			Integer removed = checkpoints.remove(i);								//removes current checkpoint from list
			j = i - 1;
			Double removedLocation = checkpointArray.get(getCheckpointIndex(removed, -1, -1)).getLocation();	//gets location of removed checkpoint
			while (j >= 0 && removedLocation < checkpointArray.get(getCheckpointIndex(checkpoints.get(j), -1, -1)).getLocation()){  //loops until location before current is found
				j -= 1;
			}
			checkpoints.add(j+1, removed);   										//adds the result into place after the faster time
		}																			//checkpoints are now sorted in order in stage
		return checkpoints;
	}

	@Override
	public int[] getRaceIds() {
		int[] returnArray = new int[raceArray.size()];
		if (raceArray.size() > 0){ 													//if arraylist is populated
			int i = 0;
			for (Race n : raceArray){												//iterate through arraylist assigning to array
				returnArray[i++] = n.getID();
			}
		}
		return returnArray;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        nameValidCheck(name);														//checks if name is valid
		boolean nameExists = false; 												//checks if name already exists
		for (Race n : raceArray){													//iterates through races
			if (n.getName().equals(name)){
				nameExists = true;
			}
		}
		if (nameExists){
			throw new IllegalNameException("This race name already exists");
		}

		Race newRace = new Race(); 													//Instantiate new race class
		newRace.setName(name);														//set attributes
		newRace.setDesc(description);
		newRace.setID(raceArray.get(raceArray.size() - 1).getID() + 1); 			//largest existing id must be at end of arraylist, so add 1
		raceArray.add(newRace);

		return newRace.getID();														//return id of race
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race currentRace = raceArray.get(getRaceIndex(raceId, -1, -1));				//get current race to avoid repeatedly accessing array
		int totalLength = 0; 
		for (int n : currentRace.getStages()){ 										//gets each stage ID in race
			totalLength += stageArray.get(getStageIndex(n, -1, -1)).getLength();	//uses stage id to get stage and get length 
		}

		return currentRace.toString() + String.format("\n%d km in total", totalLength);
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		int raceIndex = getRaceIndex(raceId, -1, -1);
		Race currentRace = raceArray.get(raceIndex);								//get current race to avoid repeatedly accessing array
		// removal of race from riders
		for (int n : currentRace.getRiders()){ 										//gets ID of each rider in race
			int riderIndex = getRiderIndex(n, -1, -1);
			ArrayList<Integer> riderRaces = riderArray.get(riderIndex).getRaces();	//gets list of riders races
			int raceCount = 0;														//int used to interate along the list
			while (raceCount <= riderRaces.size() && riderRaces.get(raceCount) != raceId){
				raceCount += 1;							//iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
			}
			if (riderRaces.get(raceCount) == raceId){
				riderRaces.remove(raceCount); 			//Removes the deleted race from rider's data
			}
			riderArray.get(riderIndex).setRaces(riderRaces);	//replaces the rider's races with new list with deleted race
		}												//iterates through all riders that have data of the race to be deleted
		// removal of stages
		for (int n : currentRace.getStages()){
			removeStageById(n);							//removes each stage in race, this will cascade to delete each of their checkpoints too
		}
		raceArray.remove(raceIndex);					//removes the race from the race index
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		//gets race index, gets race, gets size of stages arraylist
		return raceArray.get(getRaceIndex(raceId, -1, -1)).getStages().size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		nameValidCheck(stageName);											//checks if name is valid
		boolean nameExists = false; 										//checks if name already exists
		for (Race n : raceArray){											//iterates through races
			if (n.getName().equals(stageName)){
				nameExists = true;
			}
		}
		if (nameExists){													//if name already exists throw error
			throw new IllegalNameException("This stage name already exists");
		}
		int raceIndex = getRaceIndex(raceId, -1, -1);
		Race currentRace = raceArray.get(raceIndex); 						//Gets desired race
		Stage newStage = new Stage();										//Creates new stage
		newStage.setName(stageName);										//assigns attributes through setters
		newStage.setType(type);
		newStage.setDesc(description);
		newStage.setLength(length);
		newStage.setRace(raceId);
		newStage.setID(stageArray.get(stageArray.size() - 1).getID() + 1); 	//largest existing id must be at end of arraylist, so add 1
		
		currentRace.addStage(newStage.getID());								//adds stage to race
		raceArray.set(raceIndex, currentRace); 								//Replaces race in array with race with added stage
		
		return newStage.getID();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		ArrayList<Integer> raceStages = raceArray.get(getRaceIndex(raceId, -1, -1)).getStages();	//creates an arraylist to avoid repeatedly accessing main arraylist
		int[] returnArray = new int[raceStages.size()];						//array to return values of stage IDs
		if (raceStages.size() > 0){											//checks if race has any stages
			returnArray = intArrayListToArray(raceStages);					//converts arraylist to array
		}
		return returnArray;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		return stageArray.get(getStageIndex(stageId, -1, -1)).getLength();	//gets stage index and uses that to access it on the stage arraylist
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		int stageIndex = getStageIndex(stageId, -1, -1);
		int raceIndex = getRaceIndex(stageArray.get(stageIndex).getRace(), -1, -1);
		// removing stages checkpoints
		for (int n : stageArray.get(raceIndex).getCheckpoints()){	//for each checkpoint in a stage
			checkpointArray.remove(getCheckpointIndex(n, -1, -1));	//gets each checkpoint and removes them
		}
		// removing stage from its race
		raceArray.get(raceIndex).removeStage(stageId);
		
		//removing stage from stage arraylist
		stageArray.remove(stageIndex);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		int stageIndex = getStageIndex(stageId, -1, -1);
		Stage currentStage = stageArray.get(stageIndex);	//stores stage in variable to avoid repeatedly accessing arraylist
		if (currentStage.getType() == StageType.TT){		//checks if stage is a time trial
			throw new InvalidStageTypeException();
		}
		if (currentStage.getState()){						//checks if stage is waiting for results
			throw new InvalidStageStateException();
		}
		if (currentStage.getLength() < location){			//checks if checkpoint is within stage
			throw new InvalidLocationException();
		}
		
		Checkpoint newClimbCheckpoint = new Checkpoint();	//creates a new checkpoint and assigns
		newClimbCheckpoint.setGradient(averageGradient);	//assigns attributes via setters
		newClimbCheckpoint.setLength(length);
		newClimbCheckpoint.setLocation(location);
		newClimbCheckpoint.setType(type);
		newClimbCheckpoint.setStage(stageId);
		newClimbCheckpoint.setID(checkpointArray.get(checkpointArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		
		checkpointArray.add(newClimbCheckpoint);
		stageArray.get(stageIndex).addCheckpoint(newClimbCheckpoint.getID());	//adds checkpoint to stage

		return newClimbCheckpoint.getID();	//return ID
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
		InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		int stageIndex = getStageIndex(stageId, -1, -1);
		Stage currentStage = stageArray.get(stageIndex);				//stores stage in variable to avoid repeatedly accessing arraylist
		if (currentStage.getType() == StageType.TT){					//checks if stage is a time trial
			throw new InvalidStageTypeException();
		}
		if (currentStage.getState()){									//checks if stage is waiting for results
			throw new InvalidStageStateException();
		}
		if (currentStage.getLength() < location){						//checks if checkpoint is within stage
			throw new InvalidLocationException();
		}
			
		Checkpoint newClimbCheckpoint = new Checkpoint();				//creates a new checkpoint and assigns
		newClimbCheckpoint.setLocation(location);
		newClimbCheckpoint.setType(CheckpointType.SPRINT);				//assigns sprint type
		newClimbCheckpoint.setID(checkpointArray.get(checkpointArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		newClimbCheckpoint.setStage(stageId);

		checkpointArray.add(newClimbCheckpoint);
		stageArray.get(stageIndex).addCheckpoint(newClimbCheckpoint.getID());	//adds checkpoint to stage
	
		return newClimbCheckpoint.getID();										//return ID

	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		int checkpointIndex = getCheckpointIndex(checkpointId, -1, -1);										//gets checkpoint location in arraylist
		int currentStageIndex = getStageIndex(checkpointArray.get(checkpointIndex).getStage(), -1, -1);		//gets the index of the stage that checkpoint is in
		if (stageArray.get(currentStageIndex).getState()){													//if stage is waiting for results
			throw new InvalidStageStateException("Can't delete checkpoint from stage that is already concluded");
		}
		// removing checkpoint from its stage
		stageArray.get(currentStageIndex).removeCheckpoint(checkpointId);

		//removing checkpoint from checkpoint arraylist
		checkpointArray.remove(checkpointIndex);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		int stageIndex = getStageIndex(stageId, -1, -1);
		if (stageArray.get(stageIndex).getState()){						//checks if stage is already waiting for results
			throw new InvalidStageStateException();
		}
		else{
			stageArray.get(stageIndex).setState(true);			//sets state of stage to be waiting for results
		}
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		ArrayList<Integer> stageCheckpoints = stageArray.get(getStageIndex(stageId, -1, -1)).getCheckpoints();	//creates an arraylist to avoid repeatedly accessing main arraylist
		int[] returnArray = new int[stageCheckpoints.size()];						//array to return values of checkpoint IDs
		if (stageCheckpoints.size() > 0){											//checks if race has any stages
			returnArray = intArrayListToArray(stageCheckpoints);
		}
		return returnArray;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		//variable checks
		nameValidCheck(name);											//checks if name is valid
		boolean nameExists = false; 									//checks if name already exists
		for (Team n : teamArray){										//iterates through teams
			if (n.getName().equals(name)){								//checks each existing team if they match new name
				nameExists = true;
			}
		}
		if (nameExists){												//throws error if name already exists
			throw new IllegalNameException("This team name already exists");
		}
		//team creation
		Team newTeam = new Team(); 										//Instantiate new team class
		newTeam.setName(name);											//assign attributes
		newTeam.setDesc(description);
		newTeam.setID(teamArray.get(teamArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		teamArray.add(newTeam);											//add to arraylist

		return newTeam.getID();											//return id of race
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		int teamIndex = getTeamIndex(teamId, -1, -1);
		// removing team from riders
		for (int n : teamArray.get(teamIndex).getRiders()){				//gets each rider thats in the team
			riderArray.get(getRiderIndex(n, -1, -1)).setTeam(-1);		//sets each rider to be teamless
		}

		//removing team from arraylist
		teamArray.remove(teamIndex);
	}

	@Override
	public int[] getTeams() {
		int[] returnArray = new int[teamArray.size()];
		if (teamArray.size() > 0){ 				//if arraylist is populated
			int i = 0;
			for (Team n : teamArray){			//iterate through arraylist assigning to array
				returnArray[i++] = n.getID();
			}
		}
		return returnArray;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		if (teamArray.get(getTeamIndex(teamId, -1, -1)).getRiders().size() > 0){					//if there are riders in a team
			return intArrayListToArray(teamArray.get(getTeamIndex(teamId, -1, -1)).getRiders());	//convert the arraylist to list and return
		}
		else{
			return new int[0];	//else return empty array
		}
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		//variable checks
		if (name.equals(null) || name.equals("")){
			throw new IllegalArgumentException("Name is null or empty");
		}
		if (yearOfBirth < 1900){
			throw new IllegalArgumentException("Year of birth must be after 1900");
		}
		getTeamIndex(teamID, -1, -1);						//unused return, just checks that team exists
		//rider creation
		Rider newRider = new Rider(); 										//Instantiate new rider class
		newRider.setTeam(teamID);
		newRider.setName(name);
		newRider.setBirth(yearOfBirth);
		newRider.setID(riderArray.get(riderArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		
		teamArray.get(getTeamIndex(teamID, -1, -1)).addRider(newRider.getID());	//adds rider to team
		
		riderArray.add(newRider);

		return newRider.getID();											//return id of race
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		Rider currentRider = riderArray.get(getRiderIndex(riderId, -1, -1));	//gets rider from arraylist
		for (int n : currentRider.getRaces()){									//gets every race that the rider is in
			int raceIndex = getRaceIndex(n, -1, -1);
			for (int i : raceArray.get(raceIndex).getStages()){					//for every stage the rider is in
				stageArray.get(getStageIndex(i, -1, -1)).removeRider(riderId);	//remove the rider and results from stage
			}
			if (currentRider.getTeam() != -1){									//checks if rider is in a team
				teamArray.get(getTeamIndex(currentRider.getTeam(), -1, -1));	//gets riders team and removes them
			}
			raceArray.get(getRaceIndex(n, -1, -1)).removeRider(riderId);		//removes the rider from the main race
		}
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		int stageIndex = getStageIndex(stageId, -1, -1);			//get index of stage to register in
		if (checkpoints.length != stageArray.get(stageIndex).getCheckpoints().size() + 2){
			throw new InvalidCheckpointTimesException();			//if rider has a different amount of times to the stages checkpoints and start and end
		}															//throw error
		if (!stageArray.get(stageIndex).getState()){				//if stage is not waiting for results
			throw new InvalidStageStateException();
		}
		Result newResult = new Result();							//create new result variable for rider
		newResult.setID(riderId);									//assigns attributes
		newResult.setCheckpointTimes(checkpoints);
		stageArray.get(stageIndex).addRiderResults(newResult);
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		return stageArray.get(getStageIndex(stageId, -1, -1)).getRiderResults(riderId);
		//gets stage, gets riders results from stage
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		ArrayList<Result> adjustedTimes = stageArray.get(getStageIndex(stageId, -1, -1)).getAdjustedRiderResults();	//gets the desired stage, and gets riders results from it
		int i = 0;																		//looping variable
		while (i < adjustedTimes.size() && adjustedTimes.get(i).getID() != riderId){
			i += 1;																		//iterates through the arraylist of adjusted times until rider is found
		}
		if (adjustedTimes.get(i).getID() != riderId){									//checks if rider was found
			throw new IDNotRecognisedException();
		}
		LocalTime startTime = adjustedTimes.get(i).getCheckpointTimes()[0];				//get start and finish time for rider in stage
		LocalTime endTime = adjustedTimes.get(i).getCheckpointTimes()[adjustedTimes.get(i).getCheckpointTimes().length-1];
		return Instant.ofEpochMilli(MILLIS.between(startTime, endTime)).atZone(ZoneId.systemDefault()).toLocalTime();	//converts difference of times from millis back to localtime, giving total elapsed
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		stageArray.get(getStageIndex(stageId, -1, -1)).removeRider(riderId);		//gets correct stage and removes rider's results from stage
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		ArrayList<Result> SortedResults = stageArray.get(getStageIndex(stageId, -1, -1)).getSortedElapsedResults();	//gets list of all results ordered by elapsed time
		int[] sortedIDs = new int[SortedResults.size()];							//creates array of same size
		int i = 0;	//loop variable
		for (Result n : SortedResults){
			sortedIDs[i] = n.getID();												//iterate through sorted results and add IDs in order
		}
		return sortedIDs;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		ArrayList<Result> sortedAdjustedResults = stageArray.get(getStageIndex(stageId, -1, -1)).getAdjustedRiderResults();	//gets arraylist of all riders adjusted results from specified stage
		LocalTime[] sortedTimes = new LocalTime[sortedAdjustedResults.size()];										//localtime array to return
		int i = 0;	//loop variable
		for (Result n : sortedAdjustedResults){
			sortedTimes[i] = Instant.ofEpochMilli(n.getTotalTime()).atZone(ZoneId.systemDefault()).toLocalTime();	//iterate through sorted results and add times in order, converting from milliseconds to localtime
		}
		return sortedTimes;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		Stage currentStage = stageArray.get(getStageIndex(stageId, -1, -1));		//gets the stage to get points from
		ArrayList<Result> stageRiders = currentStage.getSortedElapsedResults();		//gets arraylist of riders and results in order of finishing times
		int[] points = new int[stageRiders.size()];									//array to be returned, will be ordered same as arraylist
		Arrays.fill(points, 0);														//ensures all array values are 0
		if (currentStage.getCheckpoints().size() < 2){								//if stage does not have at least a start and finish
			return points;															//return empty array
		}
		ArrayList<Integer> checkpoints = getOrderedCheckpoints(stageId);			//gets checkpoints in stage order
		
		// adding points for intermediate sprints rank
		int i = 1;																	//starts on 1 to skip over start time
		int j = 0;
		for (Integer n : checkpoints){
			if (checkpointArray.get(getCheckpointIndex(n, -1, -1)).getType() == CheckpointType.SPRINT){	//checks if any sprint checkpoints are in stage
				ArrayList<Result> checkpointFinishers = currentStage.getSortedCheckpointResults(i);	//gets results sorted by cross of sprint checkpoint
				j = 0;
				for (j=0;j<15;j++){
					int finishingRiderID = checkpointFinishers.get(j).getID();		//get the ID of the rider in each rank
					int k = 0;	//yet another loop variable
					while (stageRiders.get(k).getID() != finishingRiderID){			//finds rider's location in stageriders and points
						k += 1;
					}
					switch(j){														//assigns points based on rider's rank
						case 0:														//if rider came first
							points[k] += 20;
							break;
						case 1:														//if rider came second
							points[k] += 17;
							break;
						case 2:														//third
							points[k] += 15;
							break;
						case 3:														//fourth
							points[k] += 13;
							break;
						default:
							points[k] += 15-j;										//calcs points based on formula for 5th place and under
					}
				}
			}
			i += 1;
		}

		// adding points for stage finishing rank
		//points array is already sorted in order of rider finish times, can assign immediately
		//assigning without a loop to reduce operations
		if (currentStage.getType() == StageType.TT || currentStage.getType() == StageType.HIGH_MOUNTAIN){	//tt and high mountain give same points
			points[0] += 20;	
			points[1] += 17;	
			points[2] += 15;
			points[3] += 13;
			points[4] += 11;
			points[5] += 10;
			points[6] += 9;
			points[7] += 8;
			points[8] += 7;
			points[9] += 6;
			points[10] += 5;
			points[11] += 4;
			points[12] += 3;
			points[13] += 2;
			points[14] += 1;
		}
		else if(currentStage.getType() == StageType.MEDIUM_MOUNTAIN){
			points[0] += 30;	
			points[1] += 25;	
			points[2] += 22;
			points[3] += 19;
			points[4] += 17;
			points[5] += 15;
			points[6] += 13;
			points[7] += 11;
			points[8] += 9;
			points[9] += 7;
			points[10] += 6;
			points[11] += 5;
			points[12] += 4;
			points[13] += 3;
			points[14] += 2;
		}
		else{	//stagetype must be flat
			points[0] += 50;	
			points[1] += 30;	
			points[2] += 20;
			points[3] += 18;
			points[4] += 16;
			points[5] += 14;
			points[6] += 12;
			points[7] += 10;
			points[8] += 8;
			points[9] += 7;
			points[10] += 6;
			points[11] += 5;
			points[12] += 4;
			points[13] += 3;
			points[14] += 2;
		}
		return points;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		Stage currentStage = stageArray.get(getStageIndex(stageId, -1, -1));			//gets the stage to get points from
		ArrayList<Result> stageRiders = currentStage.getSortedElapsedResults();			//gets arraylist of riders and resultsin order of finishing times
		int[] mountainPoints = new int[stageRiders.size()];								//array to be returned, will be ordered same as arraylist
		Arrays.fill(mountainPoints, 0);													//ensures all array values are 0
		
		if (currentStage.getType() == StageType.TT || currentStage.getCheckpoints().size() < 3){	//if stage is TT or has no checkpoints then no need to search for mountain checkpoints
			return mountainPoints;
		}
		ArrayList<Integer> checkpoints = getOrderedCheckpoints(stageId);				//gets checkpoints in stage order

		int i = 1;
		int j = 0;
		int k = 0;	//loop variables
		for (Integer n: checkpoints){
			Checkpoint currentCheckpoint = checkpointArray.get(getCheckpointIndex(n, -1, -1));
			if (currentCheckpoint.getType() != CheckpointType.SPRINT){
				ArrayList<Result> checkpointFinishers = currentStage.getSortedCheckpointResults(i);	//gets results sorted by cross of sprint checkpoint
				j = 0;
				k = 0;
				switch (currentCheckpoint.getType()){									//assign points based on checkpoint type
					//HC checkpoint
					case HC:
						for (j=0;j<8;j++){												//get first 8 riders
							int finishingRiderID = checkpointFinishers.get(j).getID();	//get the ID of the rider in each rank
							while (stageRiders.get(k).getID() != finishingRiderID){		//finds rider's location in stageriders and points
								k += 1;
							}
							switch (j){													//assign points based on rank in checkpoint
								case 0:													//first
									mountainPoints[k] += 20;
									break;
								case 1:													//second
									mountainPoints[k] += 15;
									break;
								default:
									mountainPoints[k] += 18 - ((j+1)*2);				//calc avoids writing out every case
									break;
							}
						}
						break;
					//1C checkpoint
					case C1:
						for (j=0;j<6;j++){												//get first 6 riders
							int finishingRiderID = checkpointFinishers.get(j).getID();	//get the ID of the rider in each rank
							while (stageRiders.get(k).getID() != finishingRiderID){		//finds rider's location in stageriders and points
								k += 1;
							}
							switch (j){													//assign points based on rank in checkpoint
								case 0:													//first
									mountainPoints[k] += 10;
									break;
								case 1:													//second
									mountainPoints[k] += 8;
									break;
								case 2:													//third.. etc
									mountainPoints[k] += 6;
									break;
								case 3:
									mountainPoints[k] += 4;
									break;
								case 4:
									mountainPoints[k] += 2;
									break;
								case 5:
									mountainPoints[k] += 1;
									break;
							}
						}
						break;
					//2C checkpoint
					case C2:
						for (j=0;j<4;j++){												//get first 4 riders
							int finishingRiderID = checkpointFinishers.get(j).getID();	//get the ID of the rider in each rank
							while (stageRiders.get(k).getID() != finishingRiderID){		//finds rider's location in stageriders and points
								k += 1;
							}
							switch (j){													//assign points based on rank in checkpoint
								case 0:													//first
									mountainPoints[k] += 5;
									break;
								case 1:													//second
									mountainPoints[k] += 3;
									break;
								case 2:													//third.. etc
									mountainPoints[k] += 2;
									break;
								case 3:
									mountainPoints[k] += 1;
									break;
							}
						}
						break;
					//3C checkpoint
					case C3:
						for (j=0;j<2;j++){												//get first 2 riders
							int finishingRiderID = checkpointFinishers.get(j).getID();	//get the ID of the rider in each rank
							while (stageRiders.get(k).getID() != finishingRiderID){		//finds rider's location in stageriders and points
								k += 1;
							}
							switch (j){													//assign points based on rank in checkpoint
								case 0:													//first
									mountainPoints[k] += 2;
									break;
								case 1:													//second
									mountainPoints[k] += 1;
									break;
							}
						}
						break;
					//4C checkpoint
					case C4:
					int finishingRiderID = checkpointFinishers.get(j).getID();			//get the ID of the rider that gets first
					while (stageRiders.get(k).getID() != finishingRiderID){				//finds rider's location in stageriders and points
						k += 1;
					}
					mountainPoints[k] += 1;
					default:
						break;															// /should/ never run
				}
			}
		}
		return mountainPoints;
	}

	@Override
	public void eraseCyclingPortal() {
		raceArray = new ArrayList<Race>();
		stageArray = new ArrayList<Stage>();
		checkpointArray = new ArrayList<Checkpoint>();
		riderArray = new ArrayList<Rider>();
		teamArray = new ArrayList<Team>();
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
			out.writeObject(raceArray);
			out.writeObject(stageArray);
			out.writeObject(checkpointArray);
			out.writeObject(riderArray);
			out.writeObject(teamArray);
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		eraseCyclingPortal();	//resets current objects so that objects from file dont load over old objects, mixing data
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
			//Read in raceArray
			Object obj = in.readObject();				//read first object from file
			if (obj instanceof ArrayList<?>){				//check loaded object is arraylist (arraylist type cannot be checked)
				ArrayList<?> testAL = (ArrayList<?>)obj;	//assign the read object to a test arraylist variable
				if (testAL.size() > 0){						//check if arraylist actually holds any data
					for (int i=0; i<testAL.size(); i++){	//ArrayList cannot be cast to safely so instead loop through loaded ArrayList
						Object testObj = testAL.get(i);		//get each entry in the loaded arraylist
						if (testObj instanceof Race){		//check loaded arraylist is of the right type (in this case Race)
							raceArray.add((Race)testObj);	//add each entry, casting safely now that Race type has been checked
						}
					}
				}
				else{
					raceArray = new ArrayList<Race>();		//if loaded arraylist is empty, no need to read it
				}
			}												//repeat for all other arraylists
			//Read in stageArray
			obj = in.readObject();
			if (obj instanceof ArrayList<?>){
				ArrayList<?> testAL = (ArrayList<?>)obj;
				if (testAL.size() > 0){	
					for (int i=0; i<testAL.size(); i++){
						Object testObj = testAL.get(i);
						if (testObj instanceof Stage){
							stageArray.add((Stage)testObj);
						}
					}
				}
				else{
					stageArray = new ArrayList<Stage>();
				}
			}
			//Read in checkpointArray
			obj = in.readObject();
			if (obj instanceof ArrayList<?>){
				ArrayList<?> testAL = (ArrayList<?>)obj;
				if (testAL.size() > 0){	
					for (int i=0; i<testAL.size(); i++){
						Object testObj = testAL.get(i);
						if (testObj instanceof Checkpoint){
							checkpointArray.add((Checkpoint)testObj);
						}
					}
				}
				else{
					checkpointArray = new ArrayList<Checkpoint>();
				}
			}
			//Read in riderArray
			obj = in.readObject();
			if (obj instanceof ArrayList<?>){
				ArrayList<?> testAL = (ArrayList<?>)obj;
				if (testAL.size() > 0){	
					for (int i=0; i<testAL.size(); i++){
						Object testObj = testAL.get(i);
						if (testObj instanceof Rider){
							riderArray.add((Rider)testObj);
						}
					}
				}
				else{
					riderArray = new ArrayList<Rider>();
				}
			}
			//Read in teamArray
			obj = in.readObject();
			if (obj instanceof ArrayList<?>){
				ArrayList<?> testAL = (ArrayList<?>)obj;
				if (testAL.size() > 0){	
					for (int i=0; i<testAL.size(); i++){
						Object testObj = testAL.get(i);
						if (testObj instanceof Team){
							teamArray.add((Team)testObj);
						}
					}
				}
				else{
					teamArray = new ArrayList<Team>();
				}
			}
		}
	}
}
