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

public class StateHowToPlay implements GLEventListener, KeyListener,
		MouseListener {

	private static GLCanvas canvas;
	private boolean startup = false;
	private int screenWidth, screenHeight;
	private StateMainMenu mainmenu;
	private String instructionString = "HOW TO PLAY\n\n"
			+ "You are a convicted felon facing death row. Your only chance to survive is to\n"
			+ "escape the prison you are held in, before being put in the electric chair.\n\n"
			+ "Getting out will not be easy though. You’ll have to pass guards and their security\n"
			+ "cameras, gain access to locked doors and, of course, make sure you get out\nbefore lockdown.\n\n"
			+ "If you are spotted by a guard, he will chase you and attempt to catch you.\n"
			+ "When caught, you will be returned to your cell.\n\n"
			+ "In case you are spotted by a security camera, at least one guard will be alerted\n"
			+ "and will be drawn to your location.\n\n"
			+ "Use your mouse to look around.\n"
			+ "Use W, A, S and D to move.\n" + "Press E for actions.\n"
			+ "Use Shift to sprint.";

	// layout
	private float[] buttonBackCoords = new float[] { 0, 0, 50, 50 };
	private float[] wasdImageCoords = new float[] { 570, 320, 160, 120 };
	// define buttons
	private Button buttonBack = new Button(buttonBackCoords, screenWidth,
			screenHeight);
	private Button wasdImage = new Button(wasdImageCoords, screenWidth,
			screenHeight);

	private Button[] buttonList = new Button[] { buttonBack, wasdImage };

	// define display window for instruction text
	private float[] instructionWindowCoords = new float[] { 50, 50, 700, 500 };

	private Window instructionWindow = new Window(instructionWindowCoords,
			screenWidth, screenHeight);

	/**
	 * Loads the How To Play state on the given Canvas. Switches to the default
	 * cursor and adds the High Score Menu as KeyListener etc.
	 * 
	 * @param canvas
	 *            : The Canvas on which the How To Play Menu will be drawn.
	 * @param mazerunner
	 *            : The game which will be resumed when the resume button is
	 *            clicked.
	 */
	public StateHowToPlay(GLCanvas canvas, StateMainMenu mainmenu) {
		StateHowToPlay.canvas = canvas;
		this.mainmenu = mainmenu;
		canvas.setCursor(null);
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		canvas.addKeyListener(this);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		System.out.println("How To Play loaded");
		startup = true;
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		if (startup) {
			init(drawable);
			startup = false;
		}

		GL gl = drawable.getGL();

		// Set the clear color and clear the screen.
//		gl.glClearColor(0.5f, 0.2f, 0.5f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Draw the buttons.
//		gl.glColor3f(0, 0.5f, 0f);

		for (int i = 0; i < buttonList.length; i++) {
			buttonList[i].draw(gl, null);
		}

//		instructionWindow.draw(gl, null);

		instructionWindow.renderString(gl, instructionString);

		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();

	}



	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("How To Play init");
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
		for (int i = 0; i < buttonList.length; i++) {
			buttonList[i].update(screenWidth, screenHeight);
		}

		instructionWindow.update(screenWidth, screenHeight);
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

	private void returnToMainMenu() {
		System.out.println("Return to main menu");
		canvas.removeGLEventListener(this);
		canvas.removeKeyListener(this);
		canvas.removeMouseListener(this);
		canvas.addGLEventListener(this.mainmenu);
		canvas.addKeyListener(mainmenu);
		canvas.addMouseListener(mainmenu);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();

		switch (code) {

		case KeyEvent.VK_ESCAPE:
			System.out.println("HOWTOPLAY/ESCAPE");
			returnToMainMenu();
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		int xclick = me.getX();
		int yclick = me.getY();
		if (buttonBack.clickedOnIt(xclick, yclick)) {
			returnToMainMenu();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// NOT USED

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
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
	
	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

	}
}
