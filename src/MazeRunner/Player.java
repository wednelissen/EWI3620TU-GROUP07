package MazeRunner;

/**
 * Player represents the actual player in MazeRunner.
 * <p>
 * This class extends GameObject to take advantage of the already implemented location 
 * functionality. Furthermore, it also contains the orientation of the Player, ie. 
 * where it is looking at and the player's speed. 
 * <p>
 * For the player to move, a reference to a Control object can be set, which can then
 * be polled directly for the most recent input. 
 * <p>
 * All these variables can be adjusted freely by MazeRunner. They could be accessed
 * by other classes if you pass a reference to them, but this should be done with 
 * caution.
 * 
 * @author Bruno Scheele
 *
 */
public class Player extends GameObject {	
	private double horAngle, verAngle;
	private boolean canMoveForward,canMoveBack,canMoveLeft,canMoveRight;
	private double speed;
	
	private Control control = null;
	
	/**
	 * The Player constructor.
	 * <p>
	 * This is the constructor that should be used when creating a Player. It sets
	 * the starting location and orientation.
	 * <p>
	 * Note that the starting location should be somewhere within the maze of 
	 * MazeRunner, though this is not enforced by any means.
	 * 
	 * @param x		the x-coordinate of the location
	 * @param y		the y-coordinate of the location
	 * @param z		the z-coordinate of the location
	 * @param h		the horizontal angle of the orientation in degrees
	 * @param v		the vertical angle of the orientation in degrees
	 */
	public Player( double x, double y, double z, double h, double v ) {
		// Set the initial position and viewing direction of the player.
		super( x, y, z );
		horAngle = h;
		verAngle = v;
		speed = 0.01;
	}
	
	/**
	 * Sets the Control object that will control the player's motion
	 * <p>
	 * The control must be set if the object should be moved.
	 * @param input
	 */
	public void setControl(Control control)
	{
		this.control = control;
	}
	
	/**
	 * Gets the Control object currently controlling the player
	 * @return
	 */
	public Control getControl()
	{
		return control;
	}

	/**
	 * Returns the horizontal angle of the orientation.
	 * @return the horAngle
	 */
	public double getHorAngle() {
		return horAngle;
	}

	/**
	 * Sets the horizontal angle of the orientation.
	 * @param horAngle the horAngle to set
	 */
	public void setHorAngle(double horAngle) {
		this.horAngle = horAngle;
	}

	/**
	 * Returns the vertical angle of the orientation.
	 * @return the verAngle
	 */
	public double getVerAngle() {
		return verAngle;
	}

	/**
	 * Sets the vertical angle of the orientation.
	 * @param verAngle the verAngle to set
	 */
	public void setVerAngle(double verAngle) {
		this.verAngle = verAngle;
	}
	
	/**
	 * Returns the speed.
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * Sets the possibility to move forward in next update, to avoid collisions.
	 * @param true/false
	 */
	public void setCanMoveForward(boolean cmf){
		this.canMoveForward = cmf;
	}
	
	/**
	 * Sets the possibility to move back in next update, to avoid collisions.
	 * @param true/false
	 */
	public void setCanMoveBack(boolean cmb){
		this.canMoveBack = cmb;
	}
	
	/**
	 * Sets the possibility to move left in next update, to avoid collisions.
	 * @param true/false
	 */
	public void setCanMoveLeft(boolean cml){
		this.canMoveLeft = cml;
	}
	
	/**
	 * Sets the possibility to move right in next update, to avoid collisions.
	 * @param true/false
	 */
	public void setCanMoveRight(boolean cmr){
		this.canMoveRight = cmr;
	}

	/**
	 * Updates the physical location and orientation of the player
	 * @param deltaTime The time in milliseconds since the last update.
	 */
	public void update(int deltaTime)
	{
		if (control != null)
		{
			control.update();
			
			// TODO: Rotate the player, according to control
			double dx = control.getdX();
			double dy = control.getdY();
			horAngle = horAngle + dx;
			verAngle = verAngle + dy;
			
			if(verAngle >= 90){
				verAngle = 89.99;
			}
			if(verAngle <= -90){
				verAngle = -89.99;
			}
			//System.out.println("horizon "+ horAngle + " vertical: " + verAngle);			
			
			
			if(canMoveForward && control.getForward()){
				locationX = locationX - speed * deltaTime * Math.sin(Math.PI * horAngle / 180);
				locationZ = locationZ - speed * deltaTime * Math.cos(Math.PI * horAngle / 180);
				if(MazeRunner.GOD_MODE){
					locationY = locationY + speed * deltaTime * Math.sin(Math.PI * verAngle / 180);
				}
			}
			
			if(canMoveBack && control.getBack()){
				locationX = locationX + speed * deltaTime * Math.sin(Math.PI * horAngle / 180);
				locationZ = locationZ + speed * deltaTime * Math.cos(Math.PI * horAngle / 180);
				if(MazeRunner.GOD_MODE){
					locationY = locationY - speed * deltaTime * Math.sin(Math.PI * verAngle / 180);
				}
			}
			
			if(canMoveLeft && control.getLeft()){
				locationX = locationX - speed * deltaTime * Math.sin(Math.PI * horAngle / 180 + Math.PI * 0.5);
				locationZ = locationZ - speed * deltaTime * Math.cos(Math.PI * horAngle / 180 + Math.PI * 0.5);
			}
			
			if(canMoveRight && control.getRight()){
				locationX = locationX - speed * deltaTime * Math.sin(Math.PI * horAngle / 180 - Math.PI * 0.5);
				locationZ = locationZ - speed * deltaTime * Math.cos(Math.PI * horAngle / 180 - Math.PI * 0.5);
			}
			//Reset collision detectors
			canMoveForward = true;
			canMoveBack = true;
			canMoveLeft = true;
			canMoveRight = true;
		}
	}
}
