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
	private String StartPosition;
	private String EndPosition;
	private String[] spotPlan;
	private int spotSize;	
	private String[] cameraPlan;
	private int cameraSize;
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
			//de begin en eind positie worden ingelezen
			sc.next(); sc.next(); // laad het kopje met 'begin punt:'
			//System.out.println(sc.nextLine());
			StartPosition = sc.next();
			//System.out.println(StartPosition);
			sc.next(); sc.next(); // laad het kopje met 'eind punt:'
			EndPosition = sc.next();
			
			//de spots worden ingelezen als cijfers die de punten voorstellen waar een spot staat.
			sc.next(); //laad het kopje met tekst spots:
			String spotSizePrint = sc.next();
			spotSize = Integer.parseInt(spotSizePrint);
			spotPlan = new String[spotSize];
			for(int i = 0; i < spotSize; i++){
				spotPlan[i] = sc.next();		
			}	
			
			//de camera's worden ingelezen als cijfers die de punten voorstellen waar een camera staat.
			sc.next(); //laad het kopje met tekst cameras:
			String cameraSizePrint = sc.next();
			cameraSize = Integer.parseInt(cameraSizePrint);
			cameraPlan = new String[cameraSize];
			for(int i = 0; i < cameraSize; i++){
				cameraPlan[i] = sc.next();		
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
	
	public Point getStartPosition(){
		String[] a = StartPosition.split(";");
		String[] b = a[0].split(",");
		int x = Integer.parseInt(b[0]);
		int y = Integer.parseInt(b[1]);
		
		Point result = new Point(x,y);
		System.out.println(result);
		return result;
	}
	
	public Point getEndPosition(){
		String[] a = EndPosition.split(";");
		String[] b = a[0].split(",");
		int x = Integer.parseInt(b[0]);
		int y = Integer.parseInt(b[1]);
		
		Point result = new Point(x,y);
		System.out.println(result);
		return result;
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
	
	public ArrayList<Point> getSpots(){
		ArrayList<Point> result = new ArrayList<Point>();
		for(int i=0; i<spotSize; i++){
			String[] a = spotPlan[i].split(";");
			String[] b = a[0].split(",");
			int x = Integer.parseInt(b[0]);
			int y = Integer.parseInt(b[1]);
			Point temp = new Point(x,y);
			result.add(temp);	
		}
		return result;
	}
	
	public ArrayList<Point> getCameras(){
		ArrayList<Point> result = new ArrayList<Point>();
		for(int i=0; i<cameraSize; i++){
			String[] a = cameraPlan[i].split(";");
			String[] b = a[0].split(",");
			int x = Integer.parseInt(b[0]);
			int y = Integer.parseInt(b[1]);
			Point temp = new Point(x,y);
			result.add(temp);	
		}
		return result;
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
