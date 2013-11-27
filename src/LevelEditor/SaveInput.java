package LevelEditor;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class SaveInput {
	private MapMenu map;
	private String[] floorPlan;
	
	private PlacedItemsMenu placedItems;
	private String[] guardsPlan;
	private String[] keysPlan;
	
	public SaveInput(MapMenu tempMap, PlacedItemsMenu tempPlacedItems){
		map = tempMap;
		placedItems = tempPlacedItems;
		floorPlan = new String[map.getHeight()];
		guardsPlan = new String[placedItems.guardSize()];
		keysPlan = new String[placedItems.keySize()];	
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
				}
				else if(BuildingBlocks[i][j].getWall()){
					floorPlan[j] = floorPlan[j] + "1";
					}
				else
					System.out.println("Er is geen wall en geen floor geset??? niet mogelijk");
				
				
			}
		}		
	}
	
	public void GuardsPlan(){
		ArrayList<Guardian> guards = placedItems.getAllGuards();
		for(int i=0; i< placedItems.guardSize(); i++){
			Guardian g = guards.get(i);
			String temp = "";
			for(int j=0; j <g.routeSize(); j++){
				Point p = g.getRoute(j);
				int a = (int)p.getX();
				int b = (int)p.getY();
				temp = temp+a + "," +b+";"; 
			}
			guardsPlan[i] = temp;	
			
		}
		
		//hier wordt de output getest.
		for(int i=0; i<guardsPlan.length; i++){
			System.out.println(guardsPlan[i]);
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
			pw.println(map.getHeight()+placedItems.guardSize()+4); //geeft aantal te schrijven regels aan.
			pw.println(map.getWidth());
			pw.println(map.getHeight());
			//de maze word weggeschreven
			for(int i=0; i<map.getHeight(); i++){
				pw.println(floorPlan[i]);
			}
			
			pw.println("Guards:");
			pw.println(guardsPlan.length);
			for(int i=0; i<guardsPlan.length; i++){
				pw.println(guardsPlan[i]);
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

