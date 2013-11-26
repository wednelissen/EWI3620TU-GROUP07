package MazeRunner;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;

import javax.media.opengl.GLCanvas;

import com.sun.opengl.util.Animator;

/**
 * The UserInput class is an extension of the Control class. It also implements three 
 * interfaces, each providing handler methods for the different kinds of user input.
 * <p>
 * For making the assignment, only some of these handler methods are needed for the 
 * desired functionality. The rest can effectively be left empty (i.e. the methods 
 * under 'Unused event handlers').  
 * <p>
 * Note: because of how java is designed, it is not possible for the game window to
 * react to user input if it does not have focus. The user must first click the window 
 * (or alt-tab or something) before further events, such as keyboard presses, will 
 * function.
 * 
 * @author Mattijs Driel
 *
 */
public class UserInput extends Control 
		implements MouseListener, MouseMotionListener, KeyListener
{
	// Fields to help calculate mouse movement
	private int Xbegin;
	private int Ybegin;
	private int Xdragged;
	private int Ydragged;
	private int dx;
	private int dy;
	private GLCanvas canvas;
	boolean gamepaused = false; 			//used for game pausing
	private MazeRunner mazerunner;
	
	
	/**
	 * UserInput constructor.
	 * <p>
	 * To make the new UserInput instance able to receive input, listeners 
	 * need to be added to a GLCanvas.
	 * 
	 * @param canvas The GLCanvas to which to add the listeners.
	 */
	public UserInput(GLCanvas canvas)
	{
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);
		this.canvas = canvas;
	}
	
	/*
	 * **********************************************
	 * *				Updating					*
	 * **********************************************
	 */

	@Override
	public void update()
	{
		// TODO: Set dX and dY to values corresponding to mouse movement
		Control.dX = dx;
		Control.dY = dy;
		dx=0;
		dy=0;
		
	}

	/*
	 * **********************************************
	 * *		Input event handlers				*
	 * **********************************************
	 */

	@Override
	public void mousePressed(MouseEvent event)
	{
		// TODO: Detect the location where the mouse has been pressed
//		Xbegin = event.getX();
//		Ybegin = event.getY();
		//System.out.println("start punt: " + Xbegin + " " + Ybegin);
		//System.out.println("god mode aan: " + MazeRunner.GOD_MODE);
	}

	@Override
	public void mouseDragged(MouseEvent event)
	{		
		// NIET MEER NODIG
//		Xdragged = event.getX();
//		Ydragged = event.getY();
//		
//		dx = Xbegin - Xdragged;
//		dy = Ybegin - Ydragged;
//		
//		Xbegin = Xdragged;
//		Ybegin = Ydragged;
//		
		//System.out.println("dx: " + dx + " dy: " + dy);
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		// Set forward, back, left and right to corresponding key presses
		
		char key = event.getKeyChar();
//		System.out.println("toets " + key);
		
		if(key == 'w'){
			Control.forward = true;
		}
		if(key == 's'){
			Control.back = true;
		}
		if(key == 'a'){
			Control.left = true;
		}
		if(key == 'd'){
			Control.right = true;
		}
		
		
		//pause the game
		if(key == 'p'){
			mazerunner.pauseSwitch();
		}
		
		//turn on or of GOD mode
		if(key == 'g'){
			if(MazeRunner.GOD_MODE == false)
				MazeRunner.GOD_MODE = true;
			else
				MazeRunner.GOD_MODE = false;
		}
		
		//open main menu
		if(key == (KeyEvent.VK_ESCAPE)){
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			canvas.removeKeyListener(this);
			new StatePauseMenu(canvas);
			canvas.setCursor(null);
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		// Set forward, back, left and right to corresponding key presses
		char key = event.getKeyChar();
		
		if(key == 'w'){
			Control.forward = false;
		}
		if(key == 's'){
			Control.back = false;
		}
		if(key == 'a'){
			Control.left = false;
		}
		if(key == 'd'){
			Control.right = false;
		}
	}

	/*
	 * **********************************************
	 * *		Unused event handlers				*
	 * **********************************************
	 */
	
	@Override
	public void mouseMoved(MouseEvent event)
	{
				
		boolean roboMouse = false;
			Xbegin = 100;
			Ybegin = 100;
			//System.out.println(Xbegin + "," + Ybegin);
			
			Xdragged = event.getX();
			Ydragged = event.getY();
			if(!roboMouse){
			dx = Xbegin - Xdragged;
			dy = Ybegin - Ydragged;
			
			System.out.println("Xbegin: " + Xbegin + " Ybegin: " + Ybegin + " Xdragged,Ydragged: " +  Xdragged + "," + Ydragged);
			}
			try {
				Robot robot = new Robot();
				roboMouse = true;
				robot.mouseMove(Xbegin,Ybegin);
				roboMouse = false;
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Xbegin = Xdragged;
			//Ybegin = Ydragged;
		
		
	}
	
	public void setMazeRunner(MazeRunner mazerunner){
		this.mazerunner = mazerunner;
		
	}

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
