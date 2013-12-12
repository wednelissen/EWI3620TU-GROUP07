package LevelEditor;

import java.awt.Point;

public class Spot {
	
	Point position;
	boolean setSpot;
	
	/**
	 * maakt een spot die een positie kan bevatten.
	 */
	public Spot(){
		position = new Point();
		setSpot = false;
	}
		
	/**
	 * set de positie van een spot. setSpot wordt op true geset.
	 * 
	 * @param a Point
	 */
	public void setSpot(Point a){
		position = a;
		setSpot = true;
	}
	
	/**
	 * 
	 * @return true als er een spot is geset.
	 */
	public boolean hasSpot(){
		return setSpot;
	}
	
	/**
	 * verwijderd de positie van de spot en maakt setSpot op fasle.
	 */
	public void removeSpot(){
		position = null;
		setSpot =  false;
	}
	
	/**
	 * 
	 * @return positie van de geplaatste spot.
	 */
	public Point getPosition(){
		return position;
	}
}
