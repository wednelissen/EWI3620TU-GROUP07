package MazeRunner;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import LevelEditor.Button;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

public class StatePauseMenu implements GLEventListener, KeyListener, MouseListener{
	
	private static GLCanvas canvas;
	private boolean startup = false;
	
	private int screenWidth, screenHeight;
	
	//layout van het hoofdmenu
	private float[] buttonResumeGameCoords = new float[] { 600, 100, 400, 100};
	
	//define buttons
	private Button buttonResumeGame = new Button(buttonResumeGameCoords, screenWidth, screenHeight);
	
	
	/**
	 * Constructor
	 * Also calls init(), initializing the main menu on the given canvas (when first = false).
	 * @param canvas
	 * @param first: only set true if StateMainMenu is the first state to be called,
	 *  directly after the canvas is created
	 */
	public StatePauseMenu(GLCanvas canvas){
		StatePauseMenu.canvas = canvas;
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		canvas.addKeyListener(this);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		System.out.println("Pause menu loaded");
		
		startup = true;
		
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
        if(startup){
        	init(drawable);
        	startup = false;
        }
		
		GL gl = drawable.getGL();

		// Set the clear color and clear the screen.
		gl.glClearColor(0.5f, 0.2f, 0.5f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Draw the buttons.
		gl.glColor3f(0, 0.5f, 0f);
		buttonResumeGame.draw(gl); 
		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();
		
	}



	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("Pause menu init");
		// Retrieve the OpenGL handle, this allows us to use OpenGL calls.
		GL gl = drawable.getGL();

		// Set the matrix mode to GL_PROJECTION, allowing us to manipulate the
		// projection matrix
		gl.glMatrixMode(GL.GL_PROJECTION);

		// Always reset the matrix before performing transformations, otherwise
		// those transformations will stack with previous transformations!
		gl.glLoadIdentity();

		/*
		 * glOrtho performs an "orthogonal projection" transformation on the
		 * active matrix. In this case, a simple 2D projection is performed,
		 * matching the viewing frustum to the screen size.
		 */
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

		// Set the matrix mode to GL_MODELVIEW, allowing us to manipulate the
		// model-view matrix.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// We leave the model view matrix as the identity matrix. As a result,
		// we view the world 'looking forward' from the origin.
		gl.glLoadIdentity();

		// We have a simple 2D application, so we do not need to check for depth
		// when rendering.
		gl.glDisable(GL.GL_DEPTH_TEST);
		startup = false;
		display(drawable);
		
	}



	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		gl.glViewport(0, 0, screenWidth, screenHeight);
		
		buttonResumeGame.update(width, height);
		
		// Update the projection to an orthogonal projection using the new
		// screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int code = event.getKeyCode();
		
		switch(code){
		case KeyEvent.VK_1:
			startGame();
			
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		int xclick = me.getX();
		int yclick = me.getY();
		if(buttonResumeGame.clickedOnIt(xclick, yclick)){
			startGame();
		}
		
	}

	private void startGame() {
		canvas.removeGLEventListener(this);
		canvas.removeKeyListener(this);
		canvas.removeMouseListener(this);
		
		//Set mouse position to center of screen
		try {
			Robot robot = new Robot();
			robot.mouseMove(100,100);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MazeRunner mazerunner = new MazeRunner(canvas);
		
		System.out.println("Game started");
	}

}
