package LevelEditor;

import java.awt.Point;

public class ControlCenterEditor {
	Point position;
	boolean setControlCenter;
	
	/**
	 * maakt een ControlCenter met plek voor een positie
	 */
	public ControlCenterEditor(){
		position = new Point();
		setControlCenter = false;
	}
	
	/**
	 * set de positie van een ControlCenter en maakt 'setControlCenter' true
	 * @param a Point
	 */
	public void setControlCenter(Point a){
		position = a;
		setControlCenter = true;
	}
	
	/**
	 * returnt true als ControlCenter op een positie is geset.
	 * dit moet altijd worden bekeken voordat de positie wordt opgevraagd anders is de
	 * standaard positie 0,0.
	 * @return
	 */
	public boolean hasControlCenter(){
		return setControlCenter;
	}
	
	/**
	 * verwijderd de positie van de ControlCenter en maakt 'setControlCenter' false.
	 */
	public void removeControlCenter(){
		position = null;
		setControlCenter =  false;
	}
	
	/**
	 * 
	 * @return de positie van de ControlCenter.
	 */
	public Point getPosition(){
		return position;
	}
}