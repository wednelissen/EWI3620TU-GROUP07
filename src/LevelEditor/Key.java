package LevelEditor;

import java.awt.Point;

public class Key extends Window{
	
	private Point keyPosition;
	private Point doorPosition;
	private boolean setKey;
	private boolean setDoor;

	public Key(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		keyPosition = new Point();
		doorPosition = new Point();
		setKey = false;
		setDoor = false;
	}
	
	//hier wordt de werkelijke positie van een guard geset.
	public void updatePosition(float[] sizes, int screenWidthFrame, int screenHeightFrame){
		if (sizes.length == 4) {

			originalSizes = sizes;
			float x_linksOnder = sizes[0];
			float y_rechtsOnder = sizes[1];
			float buttonSizeX = sizes[2];
			float buttonSizeY = sizes[3];

			screenWidth = screenWidthFrame;
			screenHeight = screenHeightFrame;

			x = x_linksOnder / 800 * screenWidth;
			y = (600 - y_rechtsOnder) / 600 * screenHeight;
			
			sizeX = buttonSizeX / 800 * screenWidth;
			sizeY = buttonSizeY / 600 * screenHeight;
		} else {
			System.out.println("geen geldige lengte voor input");
		}		
		
	}

	public void setKey(Point a){
		keyPosition = a;
		setKey = true;
	}
	
	public void removeKey(){
		keyPosition = null;
		setKey = false;
	}
	
	public Point getKey(){
		if(setKey){
			return keyPosition;
		}
		else
			return null;
	}
	
	public boolean hasPosition(){
		return setKey;
	}
	
	public void setDoor(Point a){
		doorPosition = a;
		setDoor = true;
	}
	
	public void removeDoor(){
		doorPosition = null;
		setDoor = false;
	}
	
	public Point getDoor(){
		if(setDoor){
			return doorPosition;
		}
		else
			return null;
	}
	
	public boolean hasDoor(){
		return setDoor;
	}
	
}
