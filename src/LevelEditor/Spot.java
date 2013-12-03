package LevelEditor;

import java.awt.Point;

public class Spot {
	
	Point position;
	boolean setSpot;
	
	public Spot(){
		position = new Point();
		setSpot = false;
	}
		
	public void setSpot(Point a){
		position = a;
		setSpot = true;
	}
	
	public boolean hasSpot(){
		return setSpot;
	}
	
	public void removeSpot(){
		position = null;
		setSpot =  false;
	}
	
	public Point getPosition(){
		return position;
	}
}
