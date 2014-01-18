package MazeRunner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import LevelEditor.Button;
import LevelEditor.Window;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

public class StateBusted implements GLEventListener, KeyListener, MouseListener {

	private GLCanvas canvas;
	private boolean startup = false;
	private int screenWidth, screenHeight;

	// layout van het hoofdmenu
	private float[] textWindowCoords = new float[] { 200, 25, 400, 75 };
	private float[] buttonMainMenuCoords = new float[] { 200, 125, 400, 75 };
	private float[] buttonQuitCoords = new float[] { 200, 225, 400, 75 };
	// define buttons
	private Window textWindow = new Window(textWindowCoords, screenWidth,
			screenHeight);
	private Button buttonMainMenu = new Button(buttonMainMenuCoords,
			screenWidth, screenHeight);
	private Button buttonQuit = new Button(buttonQuitCoords, screenWidth,
			screenHeight);
	private Button[] buttonList = new Button[] { buttonMainMenu, buttonQuit };
	private String bustedString = "HA FEUTMANS JE BENT GEPAKT DOOR EEN GUARD";

	/**
	 * Loads the Busted State on the given Canvas. Switches to the default
	 * cursor and adds the Busted State as KeyListener etc.
	 * 
	 * @param canvas
	 *            : The Canvas on which the State will be drawn
	 */
	public StateBusted(GLCanvas canvas) {
		this.canvas = canvas;
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		this.resume();
	}

	public void resume() {
		if (canvas != null) {
			this.canvas.setCursor(null);
			this.canvas.addKeyListener(this);
			this.canvas.addGLEventListener(this);
			this.canvas.addMouseListener(this);
			startup = true;
			System.out.println("Busted state loaded");
		}
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		if (startup) {
			init(drawable);
			startup = false;
		}

		GL gl = drawable.getGL();
		buttonMainMenu.draw(gl,
				LoadTexturesMaze.getTexture("buttonMainMenu"));
		buttonQuit.draw(gl,
				LoadTexturesMaze.getTexture("buttonQuit"));

		textWindow.renderString(gl, bustedString);
		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();

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
		
		for (int i = 0; i < MazeRunner.amountofSpots(); i++) {
			gl.glDisable(GL.GL_LIGHTING) ;
		}
		
		for (int i = 0; i < buttonList.length; i++) {
			buttonList[i].update(screenWidth, screenHeight);
		}
		;
		textWindow.update(screenWidth, screenHeight);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		gl.glViewport(0, 0, screenWidth, screenHeight);

		for (int i = 0; i < buttonList.length; i++) {
			buttonList[i].update(screenWidth, screenHeight);
		}
		

		// Update the projection to an orthogonal projection using the new
		// screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

	}

	@Override
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();

		switch (code) {

		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_2:
			returnToMainMenu();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		int xclick = me.getX();
		int yclick = me.getY();

		if (buttonMainMenu.clickedOnIt(xclick, yclick)) {
			returnToMainMenu();
		}
		if (buttonQuit.clickedOnIt(xclick, yclick)) {
			System.exit(0);
		}
	}

	private void returnToMainMenu() {
		System.out.println("Return to main menu");
		canvas.removeGLEventListener(this);
		canvas.removeMouseListener(this);
		canvas.removeKeyListener(this);
		GameDriver.mainMenu.returnTo();
	}

	// /////////////////////////NOT USED////////////////////////////////////

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// NOT USED

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// NOT USED

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// NOT USED

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// NOT USED

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// NOT USED

	}

}
