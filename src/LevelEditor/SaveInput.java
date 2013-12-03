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
	
	private StartAndEndPosition StartAndEnd;
	private String StartPosition;
	private String EndPosition;
	
	private SpotList spotList;
	private String[] spots;
	
	private CameraList cameraList;
	private String[] cameras;
	
	
	
	
	public SaveInput(MapMenu tempMap, PlacedItemsMenu tempPlacedItems, StartAndEndPosition tempStartAndEnd, SpotList tempSpotList, CameraList tempCameraList){
		map = tempMap;
		placedItems = tempPlacedItems;
		StartAndEnd = tempStartAndEnd;
		spotList = tempSpotList;
		cameraList = tempCameraList;
		
		floorPlan = new String[map.getHeight()];
		guardsPlan = new String[placedItems.guardSize()];
		keysPlan = new String[placedItems.keySize()];
		StartPosition = new String();
		EndPosition = new String();
		spots = new String[spotList.size()];
		cameras = new String[cameraList.size()];
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
	
	public void StartAndEndPosition(){
		Point start =StartAndEnd.getStart();
		Point end = StartAndEnd.getEnd();

		StartPosition = StartPosition+(int)start.getX() + "," +(int)start.getY()+";"; 
		EndPosition = EndPosition+(int)end.getX() + "," +(int)end.getY()+";"; 
		
		//testen van de output
		System.out.println("start: "+StartPosition+" eind: " + EndPosition);
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
	
	public void SpotsPlan(){
		int i = 0;
		for(Spot s: spotList.getSpots()){
			Point a = s.getPosition();
			int x = (int)a.getX();
			int y = (int)a.getY();
			spots[i] = x + "," +y+";"; 
			i++;
		}
		
		//DEBUG VOOR DE SPOTS
		for(int j=0; j<spots.length; j++){
			System.out.println(spots[j]);
		}
		
	}
	
	public void CamerasPlan(){
		int i = 0;
		for(Camera s: cameraList.getCameras()){
			Point a = s.getPosition();
			int x = (int)a.getX();
			int y = (int)a.getY();
			cameras[i] = x + "," +y+";"; 
			i++;
		}
		
		//DEBUG VOOR DE CAMERA'S
		for(int j=0; j<cameras.length; j++){
			System.out.println(cameras[j]);
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
			pw.println("Begin punt:");
			pw.println(StartPosition);
			pw.println("Eind punt:");
			pw.println(EndPosition);
			
			pw.println("spots:");
			pw.println(spots.length);
			for(int i=0; i<spots.length; i++){
				pw.println(spots[i]);
			}
			
			pw.println("cameras:");
			pw.println(cameras.length);
			for(int i=0; i<cameras.length; i++){
				pw.println(cameras[i]);
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

