package MazeRunner.GameStates;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

import LevelEditor.Button;
import LevelEditor.Window;
import MazeRunner.Fundamental.GameWindow;
import MazeRunner.Fundamental.GameDriver;
import MazeRunner.Fundamental.LoadTexturesMaze;
import MazeRunner.Fundamental.MazeRunner;

public class StateMainMenu implements GLEventListener, KeyListener,
		MouseListener {

	LoadTexturesMaze loadedTexturesMaze;
	private static GLCanvas canvas;
	private boolean startup = false;
	private int screenWidth, screenHeight;

	// layout van het hoofdmenu
	private float[] buttonStartGameCoords = new float[] { 200, 25, 400, 75 };
	private float[] buttonHighScoresCoords = new float[] { 200, 125, 400, 75 };
	private float[] buttonLevelEditorCoords = new float[] { 200, 225, 400, 75 };

	private float[] buttonHowToPlayCoords = new float[] { 200, 325, 400, 75 };
	private float[] buttonQuitCoords = new float[] { 200, 425, 400, 75 };
	
	private float[] backgroundImageCoords = new float [] {0, 0, 800, 600 };

	// define buttons
	private Button buttonStartGame = new Button(buttonStartGameCoords,
			screenWidth, screenHeight);
	private Button buttonHighScores = new Button(buttonHighScoresCoords,
			screenWidth, screenHeight);
	private Button buttonLevelEditor = new Button(buttonLevelEditorCoords,
			screenWidth, screenHeight);
	private Button buttonHowToPlay = new Button(buttonHowToPlayCoords,
			screenWidth, screenHeight);
	private Button buttonQuit = new Button(buttonQuitCoords, screenWidth,
			screenHeight);

	private Button[] buttonList = new Button[] { buttonStartGame,
			buttonHighScores, buttonLevelEditor, buttonHowToPlay, buttonQuit };
	private boolean texturesLoaded = false;
	
	private Window backgroundImage = new Window(backgroundImageCoords, screenWidth, screenHeight);
	public GameWindow driver;

	/**
	 * Constructor Also calls init(), initializing the main menu on the given
	 * canvas (when first = false).
	 * 
	 * @param canvas
	 * 
	 * @param first
	 *            : only set true if StateMainMenu is the first state to be
	 *            called, directly after the canvas is created
	 */
	public StateMainMenu(GLCanvas canvass, boolean first) {
		StateMainMenu.canvas = canvass;
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		System.out.println("screenHeight: " + screenHeight);
		canvas.addKeyListener(this);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		System.out.println("Main menu loaded");
		if (!first) {
			startup = true;
		}
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		if (!texturesLoaded) {
			init(drawable);
		}
		if (startup) {
			init(drawable);
			System.out.println("display init");
			startup = false;
		}

		GL gl = drawable.getGL();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		// Draw backgroundimage
		backgroundImage.draw(gl, LoadTexturesMaze.getTexture("backgroundImage"));

		// Draw the buttons.
		buttonStartGame.draw(gl, LoadTexturesMaze.getTexture("buttonStart"));
		buttonHighScores.draw(gl,
				LoadTexturesMaze.getTexture("buttonHighScore"));
		buttonLevelEditor.draw(gl,
				LoadTexturesMaze.getTexture("buttonLevelEditor"));
		buttonHowToPlay
				.draw(gl, LoadTexturesMaze.getTexture("buttonHowToPlay"));
		buttonQuit.draw(gl, LoadTexturesMaze.getTexture("buttonQuit"));

		// gl.glLoadIdentity(); //Wanneer je deze inschakeld begint hij te
		// knipperen bij het reshapen van het window??????

		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("Main menu init");
		if (!texturesLoaded) {
			loadedTexturesMaze = new LoadTexturesMaze();
			System.out.println("Tex loaded");
			texturesLoaded = true;
		}
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
		
		backgroundImage.update(screenWidth, screenHeight);
		for (int i = 0; i < buttonList.length; i++) {
			buttonList[i].update(screenWidth, screenHeight);
		}
		this.fullScreen();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// Set the new screen size and adjusting the viewport

		screenWidth = width;
		screenHeight = height;
		System.out.println(screenWidth + " " + screenHeight);
		gl.glViewport(0, 0, screenWidth, screenHeight);

		backgroundImage.update(screenWidth, screenHeight);
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
	public void keyReleased(KeyEvent event) {
		int code = event.getKeyCode();
		switch (code) {
		case KeyEvent.VK_SPACE:
			startGame();
			break;
		}
	}

	public void fullScreen(){
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 screenWidth = (int) screenSize.getWidth();
		 screenHeight = (int) screenSize.getHeight();
		 driver.setSizeScreen(screenWidth, screenHeight);
		 canvas.setSize(screenWidth, screenHeight);
	}
	
	public void setGameDriver(GameWindow driverr){
		driver = driverr;
	}
	
	/**
	 * Detects whether a button is clicked, and which button is clicked.
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		int xclick = me.getX();
		int yclick = me.getY();
		if (buttonStartGame.clickedOnIt(xclick, yclick)) {
			startGame();
		}

		if (buttonHighScores.clickedOnIt(xclick, yclick)) {
			canvas.removeGLEventListener(this);
			canvas.removeMouseListener(this);
			canvas.removeKeyListener(this);
			new StateHighScores(canvas, this);
		}

		if (buttonLevelEditor.clickedOnIt(xclick, yclick)) {
			canvas.setEnabled(false);
			canvas.setVisible(false);
			GameDriver.getGameWindow().dispose();
			LevelEditor.RunLevelEditor.main(new String[] {});
		}

		if (buttonHowToPlay.clickedOnIt(xclick, yclick)) {
			canvas.removeGLEventListener(this);
			canvas.removeMouseListener(this);
			canvas.removeKeyListener(this);
			new StateHowToPlay(canvas, this);
		}

		if (buttonQuit.clickedOnIt(xclick, yclick)) {
			System.exit(0);
		}

	}

	/**
	 * Starts a new MazeRunner. Removes the Main menu from the canvas.
	 */
	private void startGame() {
		canvas.removeGLEventListener(this);
		canvas.removeKeyListener(this);
		canvas.removeMouseListener(this);

		// Set mouse position to standard position
		try {
			Robot robot = new Robot();
			robot.mouseMove(100, 100);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		MazeRunner mazerunner = new MazeRunner(canvas);

		System.out.println("Game started");
	}

	/**
	 * Adds the main menu to the canvas as Listeners. startup is set to true to
	 * make sure init() is called.
	 */
	public void returnTo() {
		canvas.addKeyListener(this);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.setCursor(null);
		startup = true;
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

	@Override
	public void keyPressed(KeyEvent e) {
		
		// NOT USED

	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// NOT USED

	}
}
