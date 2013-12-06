package MazeRunner;

/**
 * GameObject is the superclass for all the objects in the game that need a
 * location.
 * <p>
 * Used mostly as a convenience superclass, it helps to avoid tedious retyping
 * of the get and set methods for the location variables. This class should be
 * inhereted by all objects having at least a position.
 * 
 * @author Bruno Scheele
 * 
 */
public class GameObject {
	protected double locationX, locationY, locationZ;
	protected double horAngle;

	/**
	 * The default GameObject constructor.
	 */
	public GameObject() {
	}

	/**
	 * GameObject constructor with a defined starting position.
	 * 
	 * @param x
	 *            the x-coordinate of the location
	 * @param y
	 *            the y-coordinate of the location
	 * @param z
	 *            the z-coordinate of the location
	 */
	public GameObject(double x, double y, double z) {
		locationX = x;
		locationY = y;
		locationZ = z;
		horAngle = 0;
	}

	/**
	 * Gets the horizontal angle of a gameobject
	 * 
	 * @return
	 */
	public double getHorAngle() {
		return horAngle;
	}

	/**
	 * Sets the horizontal angle of a gameobject
	 * 
	 * @param horAngle
	 */
	public void setHorAngle(double horAngle) {
		this.horAngle = horAngle;
	}

	/**
	 * Sets the x-coordinate of the location.
	 * 
	 * @param locationX
	 *            the locationX to set
	 */
	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	/**
	 * Returns the y-coordinate of the location.
	 * 
	 * @return the locationX
	 */
	public double getLocationX() {
		return locationX;
	}

	/**
	 * Sets the y-coordinate of the location.
	 * 
	 * @param locationY
	 *            the locationY to set
	 */
	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	/**
	 * Returns the y-coordinate of the location.
	 * 
	 * @return the locationY
	 */
	public double getLocationY() {
		return locationY;
	}

	/**
	 * Sets the z-coordinate of the location.
	 * 
	 * @param locationZ
	 *            the locationZ to set
	 */
	public void setLocationZ(double locationZ) {
		this.locationZ = locationZ;
	}

	/**
	 * Returns the z-coordinate of the location.
	 * 
	 * @return the locationZ
	 */
	public double getLocationZ() {
		return locationZ;
	}
}
