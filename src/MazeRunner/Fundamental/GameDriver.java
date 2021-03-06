package MazeRunner.Fundamental;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import MazeRunner.GameStates.StateMainMenu;
import MazeRunner.Sound.SoundEffect;

import com.sun.opengl.util.Animator;

/**
 * The GameDriver class is used to start the game. It initializes a GameWindow
 * and attaches a GLCanvas to it. OpenGL is set to be hardware accelerated and
 * double buffered. Sound effects are loaded before starting the game. The main
 * menu is initialized on the full-screen canvas.
 * 
 * @author Wiebe
 * 
 */
public class GameDriver {
	public static StateMainMenu mainMenu;
	private static GLCanvas canvas;
	private static int screenWidth = 600, screenHeight = 600; // Default screen
																// size (not
																// used).
	public static LoadTexturesMaze loadedTexturesMaze;
	private static Animator anim;
	private static GameWindow window;

	public static void main(String[] args) {
		// Initialize Window
		initWindow();
		// Initialize Sounds
		SoundEffect.init();
		// Show Main Menu
		mainMenu = new StateMainMenu(canvas, true);
		mainMenu.driver = window;
		anim = new Animator(canvas);
		anim.start();
	}

	/**
	 * Initializes a window with a GLCanvas to draw on. Screen resolution is
	 * detected and the window and canvas are set to be full-screen.
	 */
	public static void initWindow() {
		// Automatically detect screen resolution
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// screenWidth = (int) screenSize.getWidth();
		// screenHeight = (int) screenSize.getHeight();

		// Initializes a window with the specified dimensions
		window = new GameWindow(screenWidth, screenHeight);
		// First, we set up JOGL. We start with the default settings.
		GLCapabilities caps = new GLCapabilities();
		// Then we make sure that JOGL is hardware accelerated and uses double
		// buffering.
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		// Now we add the canvas, where OpenGL will actually draw for us. We'll
		// use settings we've just defined.
		canvas = new GLCanvas(caps);
		canvas.setSize(screenWidth, screenHeight);
		window.add(canvas);
		canvas.requestFocus();
	}

	public static GameWindow getGameWindow() {
		return window;
	}
}
