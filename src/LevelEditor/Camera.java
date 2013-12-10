package LevelEditor;

import java.awt.Point;

public class Camera {
	
	Point position;
	boolean setCamera;
	
	/**
	 * maakt een camera met plek voor een positie
	 */
	public Camera(){
		position = new Point();
		setCamera = false;
	}
	
	/**
	 * set de positie van een camera en maakt 'setCamera' true
	 * @param a Point
	 */
	public void setCamera(Point a){
		position = a;
		setCamera = true;
	}
	
	/**
	 * returnt true als camera op een positie is geset.
	 * dit moet altijd worden bekeken voordat de positie wordt opgevraagd anders is de
	 * standaard positie 0,0.
	 * @return
	 */
	public boolean hasCamera(){
		return setCamera;
	}
	
	/**
	 * verwijderd de positie van de camera en maakt 'setCamera' false.
	 */
	public void removeCamera(){
		position = null;
		setCamera =  false;
	}
	
	/**
	 * 
	 * @return de positie van de Camera.
	 */
	public Point getPosition(){
		return position;
	}
}