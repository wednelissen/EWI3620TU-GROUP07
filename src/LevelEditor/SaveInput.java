package LevelEditor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public class SaveInput {
	private MapMenu map;
	private String[] floorPlan;
	
	public SaveInput(MapMenu tempMap){
		map = tempMap;
		floorPlan = new String[map.getHeight()];
	}
	
	public void floorPlanMaze(){
		int TotalBuildingBlockX = map.getWidth();
		int TotalBuildingBlockY = map.getHeight();
		BuildingBlock[][] BuildingBlocks = map.getAllBuildingBlocks();
	
		for(int j=0; j < TotalBuildingBlockY; j++){
			floorPlan[j]="";
			for(int i=0; i < TotalBuildingBlockX; i++){
				if(BuildingBlocks[i][j].getFloor()){
					floorPlan[j] = floorPlan[j] + "0";
//					if(i == TotalBuildingBlockX-1){
//						floorPlan[j] = floorPlan[j] + ";";
//					}
				}
				else if(BuildingBlocks[i][j].getWall()){
					floorPlan[j] = floorPlan[j] + "1";
//					if(i == TotalBuildingBlockX-1){
//						floorPlan[j] = floorPlan[j] + ";";
//					}
					}
				else
					System.out.println("Er is geen wall en geen floor geset??? niet mogelijk");
				
				
			}
		}		
	}
	
	public void write(String name) {
		try{			
			// build files
			//File NNOut = new File(NNOutS);
			File outputF = new File(name);
			
			// make scanner
			//Scanner sc = new Scanner(NNOut);
			
			// make file and printwriter
			FileWriter fw = new FileWriter(outputF);
			PrintWriter pw = new PrintWriter(fw);
			
			//build solution
			//aantal regels om uit te lezen
			pw.println(map.getHeight()+2); //geeft aantal te schrijven regels aan.
			pw.println(map.getWidth());
			pw.println(map.getHeight());
			//de maze word weggeschreven
			for(int i=0; i<map.getHeight(); i++){
				pw.println(floorPlan[i]);
			}
			
			
			// close all
			pw.close();
			fw.close();
			//sc.close();
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
}

