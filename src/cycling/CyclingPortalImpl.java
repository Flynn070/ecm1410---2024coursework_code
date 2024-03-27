package cycling;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.MILLIS;
import java.util.Arrays;
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
		} else {	//if (RaceArray.get(mid).getID() > target)
			return getRaceIndex(target, left, mid - 1); // search to the left of mid
		}
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
		} else {	//if (StageArray.get(mid).getID() > target)
			return getStageIndex(target, left, mid - 1); // search to the left of mid
		}
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
		} else {	//if (CheckpointArray.get(mid).getID() > target)
			return getCheckpointIndex(target, left, mid - 1); // search to the left of mid
		}
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
		} else {	//if (RiderArray.get(mid).getID() > target)
			return getRiderIndex(target, left, mid - 1); // search to the left of mid
		}
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
			while (RaceCount <= RiderRaces.size() && RiderRaces.get(RaceCount) != raceId){
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
		//variable checks
		if (name.equals(null) || name.equals("")){
			throw new IllegalArgumentException("Name is null or empty");
		}
		if (yearOfBirth < 1900){
			throw new IllegalArgumentException("Year of birth must be after 1900");
		}
		int TeamIndex = getTeamIndex(teamID, -1, -1);	//unused, just checks that team exists
		//rider creation
		Rider NewRider = new Rider(); //Instantiate new rider class
		NewRider.setTeam(teamID);
		NewRider.setName(name);
		NewRider.setBirth(yearOfBirth);
		NewRider.setID(RiderArray.get(RiderArray.size() - 1).getID() + 1); //largest existing id must be at end of arraylist, so add 1
		RiderArray.add(NewRider);

		return NewRider.getID();	//return id of race
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		int RiderIndex = getRiderIndex(riderId, -1, -1);
		Rider CurrentRider = RiderArray.get(RiderIndex);
		for (int n : CurrentRider.getRaces()){	//gets every race that the rider is in
			int RaceIndex = getRaceIndex(n, -1, -1);
			for (int i : RaceArray.get(RaceIndex).getStages()){	//for every stage the rider is in
				StageArray.get(getStageIndex(i, -1, -1)).removeRider(riderId);	//remove the rider and results from stage
			}
			if (CurrentRider.getTeam() != -1){	//checks if rider is in a team
				TeamArray.get(getTeamIndex(CurrentRider.getTeam(), -1, -1));	//gets riders team and removes them
			}
			RaceArray.get(getRaceIndex(n, -1, -1)).removeRider(riderId);	//removes the rider from the main race
		}
		
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		if (checkpoints.length != StageArray.get(StageIndex).getCheckpoints().size() + 2){
			throw new InvalidCheckpointTimesException();
		}
		if (!StageArray.get(StageIndex).getState()){	//if stage is not waiting for results
			throw new InvalidStageStateException();
		}
		Result NewResult = new Result();
		NewResult.setID(riderId);
		NewResult.setCheckpointTimes(checkpoints);
		StageArray.get(StageIndex).addRiderResults(NewResult);
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		return StageArray.get(getStageIndex(stageId, -1, -1)).getRiderResults(riderId);
		//gets stage, gets riders results from stage
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		ArrayList<Result> AdjustedTimes = StageArray.get(StageIndex).getAdjustedRiderResults();
		int i = 0;	//looping variable
		while (i < AdjustedTimes.size() && AdjustedTimes.get(i).getID() != riderId){
			i += 1;	//iterates through the arraylist of adjusted times until rider is found
		}
		if (AdjustedTimes.get(i).getID() != riderId){	//checks if rider was found
			throw new IDNotRecognisedException();
		}
		LocalTime StartTime = AdjustedTimes.get(i).getCheckpointTimes()[0];
		LocalTime EndTime = AdjustedTimes.get(i).getCheckpointTimes()[AdjustedTimes.get(i).getCheckpointTimes().length-1];
		return Instant.ofEpochMilli(MILLIS.between(StartTime, EndTime)).atZone(ZoneId.systemDefault()).toLocalTime();	//converts difference of times from millis back to localtime, giving total elapsed
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);	//gets correct stage
		StageArray.get(StageIndex).removeRider(riderId);	//removes rider's results from stage
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);	//getss stage
		ArrayList<Result> SortedResults = StageArray.get(StageIndex).getSortedElapsedResults();	//gets list of all results ordered by elapsed time
		int[] SortedIDs = new int[SortedResults.size()];
		int i = 0;	//loop variable
		for (Result n : SortedResults){
			SortedIDs[i] = n.getID();	//iterate through sorted results and add IDs in order
		}
		return SortedIDs;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);
		ArrayList<Result> SortedAdjustedResults = StageArray.get(StageIndex).getAdjustedRiderResults();
		LocalTime[] SortedTimes = new LocalTime[SortedAdjustedResults.size()];
		int i = 0;	//loop variable
		for (Result n : SortedAdjustedResults){
			SortedTimes[i] = Instant.ofEpochMilli(n.getTotalTime()).atZone(ZoneId.systemDefault()).toLocalTime();	//iterate through sorted results and add times in order, converting from milliseconds to localtime
		}
		return SortedTimes;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		int StageIndex = getStageIndex(stageId, -1, -1);	
		Stage CurrentStage = StageArray.get(StageIndex);	//gets the stage to get points from
		ArrayList<Result> StageRiders = CurrentStage.getSortedElapsedResults();	//gets arraylist of riders and resultsin order of finishing times
		int[] Points = new int[StageRiders.size()];	//array to be returned, will be ordered same as arraylist
		Arrays.fill(Points, 0);	//ensures all array values are 0
		if (CurrentStage.getCheckpoints().size() < 2){	//if stage does not have at least a start and finish
			return Points;	//return empty array
		}

		if (CurrentStage.getType() != StageType.TT || CurrentStage.getCheckpoints().size() > 2){	//if stage is TT or has no checkpoints then no need to search for intermediate sprint
			//ordering checkpoints by location in race
			ArrayList<Integer> Checkpoints = StageArray.get(StageIndex).getCheckpoints();	//ids of checkpoints in stage
			int i, j;	//loop variables
			for (i=1; i<Checkpoints.size(); i++){    //loops from second element of list to end
				Integer removed = Checkpoints.remove(i);	//removes current checkpoint from list
				j = i - 1;
				Double RemovedLocation = CheckpointArray.get(getCheckpointIndex(removed, -1, -1)).getLocation();	//gets location of removed checkpoint
				while (j >= 0 && RemovedLocation < CheckpointArray.get(getCheckpointIndex(Checkpoints.get(j), -1, -1)).getLocation()){  //loops until location before current is found
					j -= 1;
				}
				Checkpoints.add(j+1, removed);   //adds the result into place after the faster time
			}	//checkpoints are now sorted in order in stage
			i = 0;
			
			// adding points for intermediate sprints rank
			for (Integer n : Checkpoints){
				if (CheckpointArray.get(getCheckpointIndex(n, -1, -1)).getType() == CheckpointType.SPRINT){	//checks if any sprint checkpoints are in stage
					ArrayList<Result> CheckpointFinishers = CurrentStage.getSortedCheckpointResults(i);	//gets results sorted by cross of sprint checkpoint
					j = 0;
					for (j=0;j<15;j++){
						int FinishingRiderID = CheckpointFinishers.get(j).getID();
						int k = 0;	//yet another loop variable
						while (StageRiders.get(k).getID() != FinishingRiderID){		//finds rider's location in stageriders and Points
							k += 1;
						}
						switch(j){	//assigns points based on rider's rank
							case 0:	//if rider came first
								Points[k] += 20;
								break;
							case 1:	//if rider came second... etc
								Points[k] += 17;
								break;
							case 2:
								Points[k] += 15;
								break;
							case 3:
								Points[k] += 13;
								break;
							default:
								Points[k] += 15-j;	//calcs points based on formula for 5th place and under
						}
					}
				}
				i += 1;
			}

			// adding points for stage finishing rank
			//points array is already sorted in order of rider finish times, can assign immediately
			//assigning without a loop to reduce operations
			if (CurrentStage.getType() == StageType.TT || CurrentStage.getType() == StageType.HIGH_MOUNTAIN){	//tt and high mountain give same points
				Points[0] += 20;	
				Points[1] += 17;	
				Points[2] += 15;
				Points[3] += 13;
				Points[4] += 11;
				Points[5] += 10;
				Points[6] += 9;
				Points[7] += 8;
				Points[8] += 7;
				Points[9] += 6;
				Points[10] += 5;
				Points[11] += 4;
				Points[12] += 3;
				Points[13] += 2;
				Points[14] += 1;
			}
			else if(CurrentStage.getType() == StageType.MEDIUM_MOUNTAIN){
				Points[0] += 30;	
				Points[1] += 25;	
				Points[2] += 22;
				Points[3] += 19;
				Points[4] += 17;
				Points[5] += 15;
				Points[6] += 13;
				Points[7] += 11;
				Points[8] += 9;
				Points[9] += 7;
				Points[10] += 6;
				Points[11] += 5;
				Points[12] += 4;
				Points[13] += 3;
				Points[14] += 2;
			}
			else{	//stagetype must be flat
				Points[0] += 50;	
				Points[1] += 30;	
				Points[2] += 20;
				Points[3] += 18;
				Points[4] += 16;
				Points[5] += 14;
				Points[6] += 12;
				Points[7] += 10;
				Points[8] += 8;
				Points[9] += 7;
				Points[10] += 6;
				Points[11] += 5;
				Points[12] += 4;
				Points[13] += 3;
				Points[14] += 2;
			}
		}
		return Points;
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
