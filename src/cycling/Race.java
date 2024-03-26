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
    //
    public String toString(){
        return String.format("%s, : \n Race ID: %s \n %s \n %d Stages", this.getName(), this.getID(), this.getDesc(), this.Stages.size());
    }
}
 