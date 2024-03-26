package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CyclingPortalImpl implements MiniCyclingPortal{

	public ArrayList<Race> RaceArray = new ArrayList<Race>();	//Array of all race ids that exist
	public ArrayList<Stage> StageArray = new ArrayList<Stage>();
	public ArrayList<Checkpoint> CheckpointArray = new ArrayList<Checkpoint>();
	public ArrayList<Rider> RiderArray = new ArrayList<Rider>();
	public ArrayList<Team> TeamArray = new ArrayList<Team>();


	public int getRaceIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the race arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = RaceArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); // ID not found
		}
		int mid = left + ((right - left) / 2);

		if (RaceArray.get(mid).getID() == target){
			return mid; //target found
		} else if (RaceArray.get(mid).getID() < target){
			return getRaceIndex(target, mid + 1, right); //search to the right of mid
		} else if (RaceArray.get(mid).getID() > target){
			return getRaceIndex(target, left, mid - 1); // search to the left of mid
		}
		return 0;
	}
	public int getStageIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the stage arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = StageArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); // ID not found
		}
		int mid = left + ((right - left) / 2);

		if (StageArray.get(mid).getID() == target){
			return mid; //target found
		} else if (StageArray.get(mid).getID() < target){
			return getStageIndex(target, mid + 1, right); //search to the right of mid
		} else if (StageArray.get(mid).getID() > target){
			return getStageIndex(target, left, mid - 1); // search to the left of mid
		}
		return 0;
	}
	public int getCheckpointIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the checkpoint arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = CheckpointArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); // ID not found
		}
		int mid = left + ((right - left) / 2);

		if (CheckpointArray.get(mid).getID() == target){
			return mid; //target found
		} else if (CheckpointArray.get(mid).getID() < target){
			return getCheckpointIndex(target, mid + 1, right); //search to the right of mid
		} else if (CheckpointArray.get(mid).getID() > target){
			return getCheckpointIndex(target, left, mid - 1); // search to the left of mid
		}
		return 0;
	}
	public int getRiderIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the rider arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = RiderArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); // ID not found
		}
		int mid = left + ((right - left) / 2);

		if (RiderArray.get(mid).getID() == target){
			return mid; //target found
		} else if (RiderArray.get(mid).getID() < target){
			return getRiderIndex(target, mid + 1, right); //search to the right of mid
		} else if (RiderArray.get(mid).getID() > target){
			return getRiderIndex(target, left, mid - 1); // search to the left of mid
		}
		return 0;
	}
	public int getTeamIndex(int target, int left, int right) throws IDNotRecognisedException{
		// recursive binary sort on the team arraylist, left and right int should be -1 when called from other function
		if (left == -1){		// if this is the first call of the function, set left to 0 and right to max value
			left = 0;
			right = TeamArray.size();
		}

		if (left > right){
			throw new IDNotRecognisedException(); // ID not found
		}
		int mid = left + ((right - left) / 2);

		if (TeamArray.get(mid).getID() == target){
			return mid; //target found
		} else if (TeamArray.get(mid).getID() < target){
			return getTeamIndex(target, mid + 1, right); //search to the right of mid
		} else if (TeamArray.get(mid).getID() > target){
			return getTeamIndex(target, left, mid - 1); // search to the left of mid
		}
		return 0;
	}

	public void nameValidCheck(String name)  throws InvalidNameException{
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
	public int[] intArrayListToList(ArrayList<Integer> ArrayL){
		int[] ReturnList = new int[ArrayL.size()];
		int loopCount = 0;
		for (Integer n : ArrayL){
			ReturnList[loopCount++] = n;
		}
		return ReturnList;
	}

	@Override
	public int[] getRaceIds() {
		int[] ReturnArray = new int[RaceArray.size()];
		if (RaceArray.size() > 0){ //if arraylist is populated
			int i = 0;
			for (Race n : RaceArray){	//iterate through arraylist assigning to array
				ReturnArray[i++] = n.getID();
			}
		}
		return ReturnArray;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        nameValidCheck(name);	//checks if name is valid
		boolean NameExists = false; //checks if name already exists
		for (Race n : RaceArray){	//iterates through races
			if (n.getName().equals(name)){
				NameExists = true;
			}
		}
		if (NameExists){
			throw new IllegalNameException("This race name already exists");
		}

		Race NewRace = new Race(); //Instantiate new race class
		NewRace.setName(name);
		NewRace.setDesc(description);
		NewRace.setID(RaceArray.get(RaceArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		RaceArray.add(NewRace);

		return NewRace.getID();	//return id of race
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		int RaceIndex = getRaceIndex(raceId, -1, -1);
		Race CurrentRace = RaceArray.get(RaceIndex);	//get current race to avoid repeatedly accessing array
		int TotalLength = 0; 
		for (int n : CurrentRace.getStages()){ //gets each stage ID in race
			TotalLength += StageArray.get(getStageIndex(n, -1, -1)).getLength();	//uses stage id to get stage and get length 
		}

		return CurrentRace.toString() + String.format("\n%d km in total", TotalLength);
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		int RaceIndex = getRaceIndex(raceId, -1, -1);
		Race CurrentRace = RaceArray.get(RaceIndex);	//get current race to avoid repeatedly accessing array
		// removal of race from riders
		for (int n : CurrentRace.getRiders()){ //gets ID of each rider in race
			int RiderIndex = getRiderIndex(n, -1, -1);
			ArrayList<Integer> RiderRaces = RiderArray.get(RiderIndex).getRaces();	//gets list of riders races
			int RaceCount = 0;	//int used to interate along the list
			while (RiderRaces.get(RaceCount) != raceId && RaceCount <= RiderRaces.size()){
				RaceCount += 1;	//iterates through the list until index of id is found (or end of list found - shouldnt happen but is here in case to prevent crashes)
			}
			if (RiderRaces.get(RaceCount) == raceId){
				RiderRaces.remove(RaceCount); //Removes the deleted race from rider's data
			}
			RiderArray.get(RiderIndex).setRaces(RiderRaces);	//replaces the rider's races with new list with deleted race
		}	//iterates through all riders that have data of the race to be deleted
		// removal of stages
		for (int n : CurrentRace.getStages()){
			removeStageById(n);	//removes each stage in race, this will cascade to delete each of their checkpoints too
		}
		RaceArray.remove(RaceIndex);	//removes the race from the race index
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO check this actually works
		return RaceArray.get(getRaceIndex(raceId, -1, -1)).getStages().size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		nameValidCheck(stageName);	//checks if name is valid
		boolean NameExists = false; //checks if name already exists
		for (Race n : RaceArray){	//iterates through races
			if (n.getName().equals(stageName)){
				NameExists = true;
			}
		}
		if (NameExists){
			throw new IllegalNameException("This stage name already exists");
		}
		int RaceIndex = getRaceIndex(raceId, -1, -1);
		Race CurrentRace = RaceArray.get(RaceIndex); //Gets desired race
		Stage NewStage = new Stage();	//Creates new stage
		NewStage.setName(stageName);
		NewStage.setType(type);
		NewStage.setDesc(description);
		NewStage.setLength(length);
		NewStage.setRace(raceId);
		NewStage.setID(StageArray.get(StageArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		
		CurrentRace.addStage(NewStage.getID());	//adds stage to race
		RaceArray.set(RaceIndex, CurrentRace); //Replaces race in array with race with added stage
		
		return NewStage.getID();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int RaceIndex = getRaceIndex(raceId, -1, -1);
		ArrayList<Integer> RaceStages = RaceArray.get(RaceIndex).getStages();	//creates an arraylist to avoid repeatedly accessing main arraylist
		int[] ReturnArray = new int[RaceStages.size()];	//array to return values of stage IDs
		if (RaceStages.size() > 0){	//checks if race has any stages
			ReturnArray = intArrayListToList(RaceStages);
		}
		return ReturnArray;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		return StageArray.get(getStageIndex(stageId, -1, -1)).getLength();	//gets stage index and uses that to access it on the stage arraylist
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		int RaceIndex = getRaceIndex(StageArray.get(StageIndex).getRace(), -1, -1);
		// removing stages checkpoints
		for (int n : StageArray.get(RaceIndex).getCheckpoints()){	//for each checkpoint in a stage
			CheckpointArray.remove(getCheckpointIndex(n, -1, -1));	//gets each checkpoint and removes them
		}
		// removing stage from its race
		RaceArray.get(RaceIndex).removeStage(stageId);
		
		//removing stage from stage arraylist
		StageArray.remove(StageIndex);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		Stage CurrentStage = StageArray.get(StageIndex);	//stores stage in variable to avoid repeatedly accessing arraylist
		if (CurrentStage.getType() == StageType.TT){		//checks if stage is a time trial
			throw new InvalidStageTypeException();
		}
		if (CurrentStage.getState()){						//checks if stage is waiting for results
			throw new InvalidStageStateException();
		}
		if (CurrentStage.getLength() < location){			//checks if checkpoint is within stage
			throw new InvalidLocationException();
		}
		
		Checkpoint NewClimbCheckpoint = new Checkpoint();	//creates a new checkpoint and assigns
		NewClimbCheckpoint.setGradient(averageGradient);
		NewClimbCheckpoint.setLength(length);
		NewClimbCheckpoint.setLocation(location);
		NewClimbCheckpoint.setType(type);
		NewClimbCheckpoint.setStage(stageId);
		NewClimbCheckpoint.setID(CheckpointArray.get(CheckpointArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		
		CheckpointArray.add(NewClimbCheckpoint);
		StageArray.get(StageIndex).addCheckpoint(NewClimbCheckpoint.getID());	//adds checkpoint to stage

		return NewClimbCheckpoint.getID();	//return ID
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
		InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		Stage CurrentStage = StageArray.get(StageIndex);	//stores stage in variable to avoid repeatedly accessing arraylist
		if (CurrentStage.getType() == StageType.TT){		//checks if stage is a time trial
			throw new InvalidStageTypeException();
		}
		if (CurrentStage.getState()){						//checks if stage is waiting for results
			throw new InvalidStageStateException();
		}
		if (CurrentStage.getLength() < location){			//checks if checkpoint is within stage
			throw new InvalidLocationException();
		}
			
		Checkpoint NewClimbCheckpoint = new Checkpoint();	//creates a new checkpoint and assigns
		NewClimbCheckpoint.setLocation(location);
		NewClimbCheckpoint.setType(CheckpointType.SPRINT);	//assigns sprint type
		NewClimbCheckpoint.setID(CheckpointArray.get(CheckpointArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		NewClimbCheckpoint.setStage(stageId);

		CheckpointArray.add(NewClimbCheckpoint);
		StageArray.get(StageIndex).addCheckpoint(NewClimbCheckpoint.getID());	//adds checkpoint to stage
	
		return NewClimbCheckpoint.getID();	//return ID

	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		int CheckpointIndex = getCheckpointIndex(checkpointId, -1, -1);
		int CurrentStageIndex = getStageIndex(CheckpointArray.get(CheckpointIndex).getStage(), -1, -1);	//gets the index of the stage that checkpoint is in
		if (StageArray.get(CurrentStageIndex).getState()){
			throw new InvalidStageStateException("Can't delete checkpoint from stage that is already concluded");
		}
		// removing checkpoint from its stage
		StageArray.get(CurrentStageIndex).removeCheckpoint(checkpointId);

		//removing checkpoint from checkpoint arraylist
		CheckpointArray.remove(CheckpointIndex);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		if (StageArray.get(StageIndex).getState()){		//checks if stage is already waiting for results
			throw new InvalidStageStateException();
		}
		else{
			StageArray.get(StageIndex).setState(true);	//sets state of stage to be waiting for results
		}
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		ArrayList<Integer> StageCheckpoints = StageArray.get(StageIndex).getCheckpoints();	//creates an arraylist to avoid repeatedly accessing main arraylist
		int[] ReturnArray = new int[StageCheckpoints.size()];	//array to return values of checkpoint IDs
		if (StageCheckpoints.size() > 0){	//checks if race has any stages
			ReturnArray = intArrayListToList(StageCheckpoints);
		}
		return ReturnArray;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		//variable checks
		nameValidCheck(name);	//checks if name is valid
		boolean NameExists = false; //checks if name already exists
		for (Team n : TeamArray){	//iterates through teams
			if (n.getName().equals(name)){
				NameExists = true;
			}
		}
		if (NameExists){
			throw new IllegalNameException("This team name already exists");
		}
		//team creation
		Team NewTeam = new Team(); //Instantiate new team class
		NewTeam.setName(name);
		NewTeam.setDesc(description);
		NewTeam.setID(TeamArray.get(TeamArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		TeamArray.add(NewTeam);

		return NewTeam.getID();	//return id of race
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		int TeamIndex = getTeamIndex(teamId, -1, -1);
		// removing team from riders
		for (int n : TeamArray.get(TeamIndex).getRiders()){
			RiderArray.get(getRiderIndex(n, -1, -1)).setTeam(-1);	//sets each rider to be teamless
		}

		//removing team from arraylist
		TeamArray.remove(TeamIndex);
	}

	@Override
	public int[] getTeams() {
		int[] ReturnArray = new int[TeamArray.size()];
		if (TeamArray.size() > 0){ //if arraylist is populated
			int i = 0;
			for (Team n : TeamArray){	//iterate through arraylist assigning to array
				ReturnArray[i++] = n.getID();
			}
		}
		return ReturnArray;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		if (TeamArray.get(getTeamIndex(teamId, -1, -1)).getRiders().size() > 0){	//if there are riders in a team
			return intArrayListToList(TeamArray.get(getTeamIndex(teamId, -1, -1)).getRiders());	//convert the arraylist to list and return
		}
		else{
			return new int[0];	//else return empty array
		}
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}
}
