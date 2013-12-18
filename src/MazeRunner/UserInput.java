package MazeRunner;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;

import javax.media.opengl.GLCanvas;

/**
 * The UserInput class is an extension of the Control class. It also implements
 * three interfaces, each providing handler methods for the different kinds of
 * user input.
 * <p>
 * For making the assignment, only some of these handler methods are needed for
 * the desired functionality. The rest can effectively be left empty (i.e. the
 * methods under 'Unused event handlers').
 * <p>
 * Note: because of how java is designed, it is not possible for the game window
 * to react to user input if it does not have focus. The user must first click
 * the window (or alt-tab or something) before further events, such as keyboard
 * presses, will function.
 * 
 * @author Mattijs Driel
 * 
 */
public class UserInput extends Control implements MouseListener,
		MouseMotionListener, KeyListener {
	// Fields to help calculate mouse movement
	private int Xbegin = 100;
	private int Ybegin = 100;
	private int Xdragged;
	private int Ydragged;
	private int dx;
	private int dy;
	private GLCanvas canvas;
	private MazeRunner mazerunner;

	/**
	 * UserInput constructor.
	 * <p>
	 * To make the new UserInput instance able to receive input, listeners need
	 * to be added to a GLCanvas.
	 * 
	 * @param canvas The GLCanvas to which to add the listeners.
	 */
	public UserInput(GLCanvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);
		this.canvas = canvas;
	}
	
	
	public void setMazeRunner(MazeRunner mazerunner) {
		this.mazerunner = mazerunner;

	}

	/*
	 * **********************************************
	 * *				Updating					*
	 * **********************************************
	 */

	@Override
	public void update() {
		// DONE: Set dX and dY to values corresponding to mouse movement
		Control.dX = dx;
		Control.dY = dy;
		dx = 0;
		dy = 0;

	}

	/*
	 * **********************************************
	 * *		Input event handlers				*
	 * **********************************************
	 */

	@Override
	public void mousePressed(MouseEvent event) {
		//TODO: Implement shooting
	}

	@Override
	public void keyPressed(KeyEvent event) {

		int key = event.getKeyCode();

		if (key == KeyEvent.VK_W) {
			Control.forward = true;
		}
		if (key == KeyEvent.VK_S) {
			Control.back = true;
		}
		if (key == KeyEvent.VK_A) {
			Control.left = true;
		}
		if (key == KeyEvent.VK_D) {
			Control.right = true;
		}
		if (key == KeyEvent.VK_E) {
			mazerunner.openDoor();
		}

		//Sprinting
		if (key == KeyEvent.VK_SHIFT){
			mazerunner.setWalkingSpeed(0.02);
		}
		// turn on or of GOD mode
		if (key == KeyEvent.VK_G) {
			if (MazeRunner.GOD_MODE == false)
				MazeRunner.GOD_MODE = true;
			else
				MazeRunner.GOD_MODE = false;
		}

		// open pause menu
		if (key == KeyEvent.VK_ESCAPE) {

			mazerunner.pauseSwitch();
			System.out.println("Open Pause menu");
			canvas.removeKeyListener(this);

		}

	}

	@Override
	public void keyReleased(KeyEvent event) {
		// Set forward, back, left and right to corresponding key presses
		int key = event.getKeyCode();

		if (key == KeyEvent.VK_W) {
			Control.forward = false;
		}
		if (key == KeyEvent.VK_S) {
			Control.back = false;
		}
		if (key == KeyEvent.VK_A) {
			Control.left = false;
		}
		if (key == KeyEvent.VK_D) {
			Control.right = false;
		}
		
		//Stop sprinting
		if (key == KeyEvent.VK_SHIFT){
			mazerunner.setWalkingSpeed(0.01);
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {

		boolean roboMouse = false;
		// System.out.println(Xbegin + "," + Ybegin);

		Xdragged = event.getX();
		Ydragged = event.getY();
		if (!roboMouse) {
			dx = Xbegin - Xdragged;
			dy = Ybegin - Ydragged;

		}
		try {
			Robot robot = new Robot();
			roboMouse = true;
			robot.mouseMove(Xbegin, Ybegin);
			roboMouse = false;
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	
	@Override
	public void mouseDragged(MouseEvent event){
		mouseMoved(event);
	}
	
	/*
	 * **********************************************
	 * *		Unused event handlers				*
	 * **********************************************
	 */

	@Override
	public void keyTyped(KeyEvent event)
	{
	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
	}

	@Override
	public void mouseEntered(MouseEvent event)
	{
	}

	@Override
	public void mouseExited(MouseEvent event)
	{
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{
	}

}
