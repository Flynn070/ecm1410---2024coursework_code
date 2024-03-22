package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CyclingPortalImpl implements MiniCyclingPortal{

	public ArrayList<Race> RaceArray = new ArrayList<Race>();	//Array of all race ids that exist
	public ArrayList<Stage> StageArray = new ArrayList<Stage>();
	public ArrayList<Rider> RiderArray = new ArrayList<Rider>();
	public ArrayList<Team> TeamArray = new ArrayList<Team>();
	
	public int getRaceIndex(int raceId){
		return 0; //TODO make a get index
	}
	public int getStageIndex(int stageId){
		return 0;
	}
	public int getRiderIndex(int riderId){
		return 0;
	}
	public int getTeamIndex(int teamId){
		return 0;
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
        if(name == null){
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

		Race NewRace = new Race(); //Instantiate new race class
		NewRace.setName(name);// TODO create race id 
		NewRace.setDesc(description);
		RaceArray.add(NewRace);

		return NewRace.getID();	//return id of race
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race CurrentRace = RaceArray.get(getRaceIndex(raceId));
		int TotalLength = 0; //TODO add up lengths of all stages
		int StageCount = getNumberOfStages(raceId); //TODO also add amount of stages

		return String.format("%s, : \n Race ID: %s \n %s \n %d Stages, %dm in total", CurrentRace.getName(), CurrentRace.getID(), CurrentRace.getDesc(), StageCount, TotalLength);
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		int RaceIndex = getRaceIndex(raceId);
		RaceArray.remove(RaceIndex);

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO check this actually works
		return RaceArray.get(getRaceIndex(raceId)).getStages().size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
				if(stageName == null){
					throw new InvalidNameException("Name must not be null");
				}
				else if(stageName == ""){
					throw new InvalidNameException("Name must not be empty");
				}
				else if(stageName.length() > 30){
					throw new InvalidNameException("Name must not be over 30 characters");
				}
				else if(stageName.contains(" ")){
					throw new InvalidNameException("Name must not contain whitespace");
				}
		// TODO add id not recognised
		int RaceIndex = getRaceIndex(raceId);
		Race CurrentRace = RaceArray.get(RaceIndex); //Gets desired race
		Stage NewStage = new Stage();	//Creates new stage
		NewStage.setName(stageName);
		NewStage.setType(type);
		NewStage.setDesc(description);
		NewStage.setLength(length);
		CurrentRace.addStage(NewStage.getID());	//adds stage to race
		RaceArray.set(RaceIndex, CurrentRace); //Replaces race in array with race with added stage
		//TODO return stage id
		return 0;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int RaceIndex = getRaceIndex(raceId);
		ArrayList<Integer> RaceStages = new ArrayList<Integer>();
		RaceStages = RaceArray.get(RaceIndex).getStages();
		int[] ReturnArray = new int[RaceStages.size()];
		if (RaceStages.size() > 0){
			int i = 0;
			for (int n : RaceArray.get(RaceIndex).getStages()){ //gets each stage in race
				ReturnArray[i++] = n;
			}
		}
		
		return ReturnArray;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		StageIndex = getStageIndex(stageId);
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
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
