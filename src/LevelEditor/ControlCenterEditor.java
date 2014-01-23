package LevelEditor;

import java.awt.Point;

public class ControlCenterEditor {
	Point position;
	boolean setControlCenter;

	/**
	 * Creëert een ControlCenter met plek voor een positie (x, z)
	 */
	public ControlCenterEditor() {
		position = new Point();
		setControlCenter = false;
	}

	/**
	 * set de positie van een ControlCenter en maakt 'setControlCenter' true
	 * 
	 * @param a
	 *            Point De positie van de control center (x, z)
	 */
	public void setControlCenter(Point a) {
		position = a;
		setControlCenter = true;
	}

	/**
	 * Bekijkt of er een control center is returnt true als ControlCenter op een
	 * positie is geset. dit moet altijd worden bekeken voordat de positie wordt
	 * opgevraagd anders is de standaard positie 0,0.
	 * 
	 * @return True wanneer de positie een control center heeft
	 */
	public boolean hasControlCenter() {
		return setControlCenter;
	}

	/**
	 * Verwijderd de positie van de ControlCenter en maakt 'setControlCenter'
	 * false.
	 */
	public void removeControlCenter() {
		position = null;
		setControlCenter = false;
	}

	/**
	 * De get functie van de camera
	 * 
	 * @return de positie van het ControlCenter.
	 */
	public Point getPosition() {
		return position;
	}
}