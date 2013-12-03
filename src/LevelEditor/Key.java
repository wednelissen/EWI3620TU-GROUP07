package LevelEditor;

import java.awt.Point;

public class Key extends Window{
	
	private Point keyPosition;
	private Point doorPosition;

	public Key(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		keyPosition = new Point();
		doorPosition = new Point();
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
	}
	
	public void removeKey(){
		keyPosition = null;
	}
	
	public Point getKey(){
		return keyPosition;
	}
	
	public boolean hasKey(){
		if(keyPosition.equals(null)){
			return false;
		}
		else
			return true;
	}
	
	public void setDoor(Point a){
		//hier moet nog wel worden gecontrolleerd of punt a een 'door' is.
		doorPosition = a;
	}
	
	public void removeDoor(){
		doorPosition = null;
	}
	
	public Point getDoor(){
		return doorPosition;
	}
	
	public boolean hasDoor(){
		if(doorPosition.equals(null)){
			return false;
		}
		else
			return true;
	}
	


	
}
