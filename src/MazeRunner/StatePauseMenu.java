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
	private MazeRunner mazerunner;
	private int screenWidth, screenHeight;
	
	//layout van het hoofdmenu
	private float[] buttonResumeGameCoords = new float[] { 200, 50, 400, 100};
	private float[] buttonHighScoresCoords = new float[] { 200, 200, 400, 100};
	private float[] buttonQuitCoords = new float[] {200, 500, 400, 100};
	//define buttons
	private Button buttonResumeGame = new Button(buttonResumeGameCoords, screenWidth, screenHeight);
	private Button buttonHighScores = new Button(buttonHighScoresCoords, screenWidth, screenHeight);
	private Button buttonQuit = new Button(buttonQuitCoords,screenWidth,screenHeight);
	/**
	 * Constructor
	 * 	 
	 * @param canvas
	 * @param mazerunner
	 */
	public StatePauseMenu(GLCanvas canvas, MazeRunner mazerunner){
		StatePauseMenu.canvas = canvas;
		canvas.setCursor(null);
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		canvas.addKeyListener(this);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		System.out.println("Pause menu loaded");
		
		startup = true;
		this.mazerunner = mazerunner;
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
		buttonHighScores.draw(gl);
		buttonQuit.draw(gl);
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
		buttonResumeGame.update(screenWidth, screenHeight);
		buttonHighScores.update(screenWidth, screenHeight);
		buttonQuit.update(screenWidth, screenHeight);
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
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		
		switch(code){
		
		case KeyEvent.VK_ESCAPE:
			resumeGame();
			
			break;
		
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent me) {
		int xclick = me.getX();
		int yclick = me.getY();
		if(buttonResumeGame.clickedOnIt(xclick, yclick)){
			resumeGame();
		}
		if(buttonHighScores.clickedOnIt(xclick, yclick)){
			
		}
		if(buttonQuit.clickedOnIt(xclick,yclick)){
			System.exit(0);
		}
	}

	private void resumeGame() {
		System.out.println("Return to game");
		canvas.removeGLEventListener(this);
		canvas.removeMouseListener(this);
		canvas.removeKeyListener(this);
		mazerunner.pauseSwitch();
	}

	///////////////////////////NOT USED////////////////////////////////////
	
	@Override
	public void keyReleased(KeyEvent event) {
		
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


}
