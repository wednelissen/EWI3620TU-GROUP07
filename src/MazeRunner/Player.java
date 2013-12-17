package MazeRunner;

import Sound.*;

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
	private boolean leftForwardWall, rightForwardWall;
	private double speed;
	
	private Control control = null;
	private boolean overRuleLeft;
	private int deltaTimeSum = 0;
	private boolean overRuleRight;
	
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
	 * Sets whether a wall is found in a 45 degree (CCW) direction
	 */
	public void setLeftForwardWall(boolean lfw){
		this.leftForwardWall = lfw;
	}
	
	/**
	 * Sets whether a wall is found in a -45 degree (CCW) direction
	 */
	public void setRightForwardWall(boolean rfw){
		this.leftForwardWall = rfw;
	}
	
	/**
	 * Updates the physical location and orientation of the player
	 * Booleans overRuleLeft and overRuleRight are used to 'follow' a
	 * wall in stead of getting stuck.
	 * @param deltaTime The time in milliseconds since the last update.
	 */
	public void update(int deltaTime)
	{
		if (control != null)
		{
			control.update();
			
			double dx = control.getdX();
			double dy = control.getdY();
			horAngle = horAngle + dx;
			if(horAngle >= 	180){
				horAngle = horAngle - 360;
			}
			
			if(horAngle <= 	-180){
				horAngle = horAngle + 360;
			}
			
			verAngle = verAngle + dy;
			
			if(verAngle >= 70){
				verAngle = 70;
			}
			if(verAngle <= -70){
				verAngle = -70;
			}
			
			double absCos = Math.abs(Math.cos(Math.PI * horAngle / 180 - Math.PI * 0.5));
			double absSin = Math.abs(Math.sin(Math.PI * horAngle / 180 - Math.PI * 0.5));
//			System.out.println("horizon "+ horAngle + " vertical: " + verAngle);			
//			System.out.println("sin(horAngle) = "  + Math.sin(Math.PI * horAngle / 180));
//			System.out.println("cos(horAngle) = " + Math.cos(Math.PI * horAngle / 180));
			
			if(canMoveForward && control.getForward()){
				stepForward(deltaTime, speed);
				//try to dodge corners, to prevent looking inside the wall.
				if(!canMoveRight && rightForwardWall){
					overRuleLeft = true;
				}
				else if(!canMoveLeft && leftForwardWall){
					overRuleRight = true;
				}
				
			}
						
			if(!canMoveForward && !canMoveRight && control.getForward()){
				overRuleLeft = true;
			}
			
			if(!canMoveForward && !canMoveLeft && control.getForward()){
				overRuleRight = true;
			}
			
			if(canMoveBack && control.getBack()){
				stepBack(deltaTime, speed);
			}
			
			if(!canMoveBack && !canMoveRight && control.getBack()){
				overRuleLeft = true;
			}
			
			if(!canMoveBack && !canMoveLeft && control.getBack()){
				overRuleRight = true;
			}
			if(canMoveLeft && control.getLeft()){
				stepLeft(deltaTime, speed);
			}
			
			if(canMoveRight && control.getRight()){
				stepRight(deltaTime, speed);
			}
			
			if(overRuleLeft){
				stepLeft(deltaTime, Math.min(absCos,absSin) * speed);
				deltaTimeSum  = deltaTimeSum + deltaTime;
//				System.out.println(deltaTimeSum);
				if(deltaTimeSum >= 100 | !(control.getForward() | control.getBack())){
					overRuleLeft = false;
					deltaTimeSum = 0;
				}
			}
			
			if(overRuleRight){
				stepRight(deltaTime, Math.min(absCos, absSin) * speed);
				deltaTimeSum = deltaTimeSum + deltaTime;
//				System.out.println(deltaTimeSum);
				if(deltaTimeSum >= 100 | !(control.getForward() | control.getBack())){
					overRuleRight = false;
					deltaTimeSum = 0;
				}
			}
			//Reset collision detectors
			canMoveForward = true;
			canMoveBack = true;
			canMoveLeft = true;
			canMoveRight = true;
			leftForwardWall = false;
			rightForwardWall = false;
			
			//Stop walking sounds when no input is received
			if(!(control.getForward() || control.getBack() || control.getRight() || control.getLeft()) ){
				SoundEffect.WALK.stop();
			}
		}
	}

	/**
	 * Updates locationX and locantionZ according to forward movement. 
	 * Also updates locationY if god mode is enabled.
	 * @param deltaTime
	 * @param speed
	 */
	private void stepForward(int deltaTime, double speed) {
		SoundEffect.WALK.walk();
		locationX = locationX - speed * deltaTime * Math.sin(Math.PI * horAngle / 180);
		locationZ = locationZ - speed * deltaTime * Math.cos(Math.PI * horAngle / 180);
		if(MazeRunner.GOD_MODE){
			locationY = locationY + speed * deltaTime * Math.sin(Math.PI * verAngle / 180);
		}
	}

	/**
	 * Updates locationX and locantionZ according to backward movement. 
	 * Also updates locationY if god mode is enabled.
	 * @param deltaTime
	 * @param speed
	 */
	private void stepBack(int deltaTime, double speed) {
		SoundEffect.WALK.walk();
		locationX = locationX + speed * deltaTime * Math.sin(Math.PI * horAngle / 180);
		locationZ = locationZ + speed * deltaTime * Math.cos(Math.PI * horAngle / 180);
		if(MazeRunner.GOD_MODE){
			locationY = locationY - speed * deltaTime * Math.sin(Math.PI * verAngle / 180);
		}
	}

	/**
	 * Updates locationX and locantionZ according to movement. 
	 * @param deltaTime
	 * @param speed
	 */
	private void stepLeft(int deltaTime, double speed) {
		SoundEffect.WALK.walk();
		locationX = locationX - speed * deltaTime * Math.sin(Math.PI * horAngle / 180 + Math.PI * 0.5);
		locationZ = locationZ - speed * deltaTime * Math.cos(Math.PI * horAngle / 180 + Math.PI * 0.5);
	}

	/**
	 * Updates locationX and locantionZ according to movement towards the right. 
	 * @param deltaTime
	 * @param speed
	 */
	private void stepRight(int deltaTime, double speed) {
		SoundEffect.WALK.walk();
		locationX = locationX - speed * deltaTime * Math.sin(Math.PI * horAngle / 180 - Math.PI * 0.5);
		locationZ = locationZ - speed * deltaTime * Math.cos(Math.PI * horAngle / 180 - Math.PI * 0.5);
	}
}
