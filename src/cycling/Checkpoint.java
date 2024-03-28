package cycling;

import java.io.Serializable;
/**
 * Represents a checkpoint of a stage
 * Uses CheckpointType enum
 */
public class Checkpoint implements Serializable{
    
    private int checkpointId;
    private CheckpointType checkpointCheckpointType;
    private Double location;
    private Double averageGradient; //used for climbs
    private Double length;
    private int stage;

    /**
     * Each checkpoint has its own unique ID for identification
	 * @return The ID value of the checkpoint
	 */
    public int getID(){
        return checkpointId;
    }
    /**
     * Assigns an ID to checkpoint
     * Should only be called during checkpoint creation, reassigning after creation could cause issues with other checkpoints
	 * @param newId the ID to set onto the checkpoint
	 */
    public void setID(int newId){
        this.checkpointId = newId;
    }

    /**
     * Get type of checkpoint, an enum used to identify different checkpoint classes
	 * @return The CheckpointType of checkpoint
	 */
    public CheckpointType getType(){
        return checkpointCheckpointType;
    }
    /**
     * Sets type of checkpoint
	 * @param newType the type of checkpoint, should be CheckpointType enum
	 */
    public void setType(CheckpointType newType){
        this.checkpointCheckpointType = newType;
    }

    /**
     * Gets the location of a checkpoint - how far along the stage in km the checkpoint is placed
	 * @return double, measured in km
	 */
    public double getLocation(){
        return location;
    }
    /**
     * Sets location of checkpoint
	 * @param newLocation the distance in km along the stage the checkpoint is placed
	 */
    public void setLocation(double newLocation){
        this.location = newLocation;
    }

    /**
     * Gets the average gradient value along the checkpoint - only used for climb checkpoints, left empty for others
     * @return double representation of average gradient
     */
    public double getGradient(){
        return averageGradient;
    }
    /**
     * Sets the average gradient value for the checkpoint, should only be used for climb checkpoints
     * @param newGradient
     */
    public void setGradient(double newGradient){
        this.averageGradient = newGradient;
    }


    /**
     * Gets the length of a climb - only for climb stages
     * @return double representation of the climb in km
     */
    public double getLength(){
        return length;
    }
    /**
     * Sets the length of a climb in km - only for climb stages
     * @param newLength length of climb
     */
    public void setLength(double newLength){
        this.length = newLength;
    }

    /**
     * Gets the ID of the stage the checkpoint belongs to. Each stage has a unique ID
     * @return int ID vale
     */
    public int getStage(){
        return stage;
    }
    /**
     * Sets the ID of the associated stage - should only be set upon creation
     * @param newStage int ID value
     */
    public void setStage(int newStage){
        this.stage = newStage;
    }
    
    public String toString(){
        String type = "";
        switch(checkpointCheckpointType){
            case SPRINT:
                type = "Intermediate Sprint";
                break;
            case HC:
                type = "Hors Categorie";
                break;
            case C1:
                type = "Category 1";
                break;
            case C2:
                type = "Category 2";
                break;
            case C3:
                type = "Category 3";
                break;
            case C4:
                type = "Category 4";
                break;
        }
        return String.format("Category: %s \nCheckpoint ID: %s \nLocation: %s \nLength: %skm \nStage ID: %d ", type, checkpointId, location, length, stage);
    }
}
