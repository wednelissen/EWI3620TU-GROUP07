package LevelEditor;

import java.awt.Point;

/**
 * 
 * 
 *
 */
public class Camera {

	Point position;
	boolean setCamera;

	/**
	 * Creëert een camera met plek voor een positie
	 */
	public Camera() {
		position = new Point();
		setCamera = false;
	}

	/**
	 * Set de positie van een camera en maakt 'setCamera' true
	 * 
	 * @param a
	 *            Point De positie van de camera (x, z)
	 */
	public void setCamera(Point a) {
		position = a;
		setCamera = true;
	}

	/**
	 * Bekijkt of er een camera is dit moet altijd worden bekeken voordat de
	 * positie wordt opgevraagd anders is de standaard positie 0,0.
	 * 
	 * @return True wanneer de positie een camera heeft
	 */
	public boolean hasCamera() {
		return setCamera;
	}

	/**
	 * Verwijderd de positie van de camera en maakt 'setCamera' false.
	 */
	public void removeCamera() {
		position = null;
		setCamera = false;
	}

	/**
	 * De get functie van de camera
	 * 
	 * @return de positie van de Camera.
	 */
	public Point getPosition() {
		return position;
	}
}