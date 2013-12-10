package LevelEditor;

import java.awt.Point;

public class Key extends Window{
	
	private Point keyPosition;
	private Point doorPosition;
	private boolean setKey;
	private boolean setDoor;

	/**
	 * hier wordt een key gemaakt maar de positie die wordt opgegeven is nog niet belangrijk
	 * deze wordt geautomatiseerd goed geset doordat de functie updatePositie wordt aangeroepen,
	 * door de classe PlacedItems.
	 * @param sizes
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
	public Key(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		keyPosition = new Point();
		doorPosition = new Point();
		setKey = false;
		setDoor = false;
	}
	
	/**
	 * hier wordt de werkelijke positie van een Key geset. wanneer hij in het placedItems menu
	 * is geplaatst.
	 * @param sizes
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
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

	/**
	 * set de positie waar de sleutel staat en set 'setKey' op true
	 * @param a
	 */
	public void setKey(Point a){
		keyPosition = a;
		setKey = true;
	}
	
	/**
	 * verwijderd de positie waar de sleutel staat en set 'setKey' op false
	 */
	public void removeKey(){
		keyPosition = null;
		setKey = false;
	}
	
	/**
	 * geeft de positie van de sleutel terug indien deze is geset. returnt anders null
	 * @return
	 */
	public Point getKey(){
		if(setKey){
			return keyPosition;
		}
		else
			return null;
	}
	
	/**
	 * geeft true wanneer er een positie is geset
	 * @return
	 */
	public boolean hasPosition(){
		return setKey;
	}
	
	/**
	 * set de positie van de deur die bij de sleutel hoort en maakt 'setDoor' true
	 * @param a
	 */
	public void setDoor(Point a){
		doorPosition = a;
		setDoor = true;
	}
	
	/**
	 * verwijderd de positie van de deur die bij de sleutel hoort en maakt 'setDoor' false
	 * de posite van de deur wordt null
	 */
	public void removeDoor(){
		doorPosition = null;
		setDoor = false;
	}
	
	/**
	 * returnt de positie van de deur indien deze is geset.
	 * @return
	 */
	public Point getDoor(){
		if(setDoor){
			return doorPosition;
		}
		else
			return null;
	}

	/**
	 * return true indien de deur positie is geset
	 * @return
	 */
	boolean hasDoor(){
		return setDoor;
	}
	
}
