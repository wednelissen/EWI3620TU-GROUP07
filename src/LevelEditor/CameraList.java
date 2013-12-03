package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class CameraList {
	
	private ArrayList<Camera> cameras;
	
	public CameraList(){
		cameras = new ArrayList<Camera>();
	}
	
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
	
	public int size(){
		return cameras.size();
	}
	
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
