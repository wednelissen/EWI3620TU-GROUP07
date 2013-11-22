package LevelEditor;

public class SaveInput {
	private MapMenu map;
	private String floorPlan = "";
	
	public SaveInput(MapMenu tempMap){
		map = tempMap;
	}
	
	public void floorPlanMaze(){
		int TotalBuildingBlockX = map.getWidth();
		int TotalBuildingBlockY = map.getHeight();
		BuildingBlock[][] BuildingBlocks = map.getAllBuildingBlocks();
	
		for(int j=0; j < TotalBuildingBlockY; j++){
			for(int i=0; i < TotalBuildingBlockX; i++){
				if(BuildingBlocks[i][j].getFloor()){
					floorPlan = floorPlan + "0";
					if(i == TotalBuildingBlockX-1){
						floorPlan = floorPlan + ";";
					}
				}
				else if(BuildingBlocks[i][j].getWall()){
						floorPlan = floorPlan + "1";
						if(i == TotalBuildingBlockX-1){
							floorPlan = floorPlan + ";";
						}
					}
				else
					System.out.println("Er is geen wall en geen floor geset??? niet mogelijk");
				
				
			}
		}
		System.out.println(floorPlan);
		
	}
	

	
}
