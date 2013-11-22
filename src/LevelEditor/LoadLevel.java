package LevelEditor;

import java.io.File;
import java.util.Scanner;

public class LoadLevel {
	
	private String fileName;
	private int width, height;
	private String printWidth, printHeight;
	private String[] floorPlan;
	
	public LoadLevel(String name){
		fileName = name;
		try{			
			File text = new File(fileName);
			Scanner sc = new Scanner(text);  
			
			// get the length of the total lines to read
			int fileLength = Integer.parseInt(sc.next());
			printWidth = sc.next();
			printHeight = sc.next();
			width = Integer.parseInt(printWidth);
			height = Integer.parseInt(printHeight);
			floorPlan = new String[height];
			
			for(int i = 0; i < height; i++){
				floorPlan[i] = sc.next();				
			}

			//close scanner
			sc.close();

			//show what is read
			for(int i = 0; i < floorPlan.length; i++){
				System.out.println(floorPlan[i]);			
			}
					
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
	
	//deze moet nog even goed gemaakt worden zodat hij alles netjes in een multidimensionale int array laad.
	public int[][] outputForMazeRunner(){
		int[][] maze = new int[width][height];
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				String temp = floorPlan[j].charAt(i) + "";
				int temp2 = Integer.parseInt(temp);
				maze[i][j] = temp2;	
			}
		}
		
		for(int j = 0; j < height; j++){
			System.out.println();
			for(int i = 0; i < width; i++){
				System.out.print(maze[i][j] + " ");
				 	
			}
		}
		
		return maze;
	}

}
