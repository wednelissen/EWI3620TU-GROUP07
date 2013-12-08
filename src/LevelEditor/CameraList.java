package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class CameraList {
	
	private ArrayList<Camera> cameras;
	
	public CameraList(){
		cameras = new ArrayList<Camera>();
	}
	
	/**
	 * voegt een camera aan de arraylist van camera's toe.
	 * een camera die al in de arraylist zit wordt niet toegevoegt.
	 * @param s
	 */
	public void addCamera(Camera s){
		boolean duplicated = false;
		Point a = s.getPosition();
		for(Camera temp: cameras){
			if(temp.getPosition().equals(a)){
				duplicated = true;
				break;
			}
		}		
		if(!duplicated)
			cameras.add(s);
		else{
			System.out.println("deze camera bestaat al");
		}
	}
	
	/**
	 * genereerd een arraylist van camera's en vervangt de oude arraylist
	 * @param loadedCameraPoints
	 */
	public void loadCameras(ArrayList<Point> loadedCameraPoints){
		
		for(Point a: loadedCameraPoints){
			Camera temp = new Camera();
			temp.setCamera(a);
			this.addCamera(temp);
		}
	}
	
	/**
	 * aantal camera's
	 * @return
	 */
	public int size(){
		return cameras.size();
	}
	
	/**
	 * verwijderd een camera indien deze in de lijst zit.
	 * @param s
	 */
	public void removeCamera(Camera s){
		Point a = s.getPosition();
		for(Camera temp: cameras){
			if(temp.getPosition().equals(a)){
				cameras.remove(temp);	
				break;
			}
		}
	}
	
	public ArrayList<Camera> getCameras(){
		return cameras;
	}

}
