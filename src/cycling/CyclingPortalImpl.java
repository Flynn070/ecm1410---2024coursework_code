package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CyclingPortalImpl implements MiniCyclingPortal{

	public ArrayList<Race> RaceArray = new ArrayList<Race>();	//Array of all race ids that exist

	@Override
	public int[] getRaceIds() {
		if (RaceArray.size() > 0){
			int[] returnArray = new int[RaceArray.size()];
			int i = 0;
			for (Race n : RaceArray){	//iterate through arraylist assigning to array
				returnArray[i++] = n.getID();
			}
			return returnArray;
		}
		else{
			return new int[0];
		}
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
		Race NewRace;
		int NewRaceID = 
		
		RaceArray.add()
			
		)

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
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
