package LevelEditor;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadLevel {
	
	private String fileName;
	private int width, height;
	private String printWidth, printHeight;
	private String[] floorPlan;
	private String[] guardsPlan;
	private String[] keysPlan;
	private int guardSize;
	
	private float[] placedItemsCoords = new float[] { 5, 235, 195, 200 }; //wordt niet gebruikt
	
	public LoadLevel(String name){
		fileName = name;
		try{			
			File text = new File(fileName);
			Scanner sc = new Scanner(text);  
			
			// get the length of the total lines to read
			int fileLength = Integer.parseInt(sc.next());
			
			//breedte en hoogte worden opgevraagd en de maze wordt ingelezen als enen en nullen.
			printWidth = sc.next();
			printHeight = sc.next();
			width = Integer.parseInt(printWidth);
			height = Integer.parseInt(printHeight);
			floorPlan = new String[height];
			
			for(int i = 0; i < height; i++){
				floorPlan[i] = sc.next();				
			}
			
			//de guards worden ingelezen als cijfers die de punten voorstellen waar een bewaker loopt.
			sc.next(); //laad het kopje met tekst Guards:
			String guardSizePrint = sc.next();
			guardSize = Integer.parseInt(guardSizePrint);
			guardsPlan = new String[guardSize];
			
			for(int i = 0; i < guardSize; i++){
				guardsPlan[i] = sc.next();		
			}	

			//close scanner
			sc.close();

					
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	//geeft een tweedimensionale int array die direct gebruikt kan worden door de mazerunner om te spelen.
	public int[][] outputForMazeRunner(){
		int[][] maze = new int[width][height];
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				String temp = floorPlan[j].charAt(i) + "";
				int temp2 = Integer.parseInt(temp);
				maze[i][j] = temp2;	
			}
		}
		
		
//		DEBUG output		
//		for(int j = 0; j < height; j++){
//			System.out.println();
//			for(int i = 0; i < width; i++){
//				System.out.print(maze[i][j] + " ");
//				 	
//			}
//		}
		
		return maze;
	}
	
	//returnt een lijst met gardians en hun routes.
	public ArrayList<Guardian> getGuardians(){	
		ArrayList<Guardian> guards = new ArrayList<Guardian>();
		for(int i = 0; i < guardSize; i++){		
			String[] route = guardsPlan[i].split(";");
			Guardian tempGuard = new Guardian(placedItemsCoords, 800, 600);
			for(int j=0; j<route.length; j++){
				String[] routePoints = route[j].split(",");
				for(int k=0; k<routePoints.length; k+=2){
					int x = Integer.parseInt(routePoints[k]);
					int y = Integer.parseInt(routePoints[k+1]);
					Point a = new Point(x,y);
					tempGuard.addRoute(a);
				}
				
			}
			guards.add(tempGuard);
		}

	
		//DEBUG guardians printen:		
//		System.out.println(guards.size());
//		for(Guardian g: guards){
//			System.out.println(g.getCopyRoutes());
//		}
		return guards;
	}

}
