package MazeRunner;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import LevelEditor.Guardian;
import LevelEditor.Key;
import LevelEditor.LoadLevel;
import LevelEditor.Spot;

import com.sun.opengl.util.Animator;

/**
 * MazeRunner is the base class of the game, functioning as the view controller
 * and game logic manager.
 * <p>
 * Functioning as the window containing everything, it initializes both JOGL,
 * the game objects and the game logic needed for MazeRunner.
 * <p>
 * For more information on JOGL, visit <a
 * href="http://jogamp.org/wiki/index.php/Main_Page">this page</a> for general
 * information, and <a
 * href="https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/">this
 * page</a> for the specification of the API.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */

public class MazeRunner implements GLEventListener, MouseListener {
	static final long serialVersionUID = 7526471155622776147L;
	public static boolean GOD_MODE = false;
	private static final double SQUARE_SIZE = 5;

	/*
	 * **********************************************
	 * * Local variables 
	 * **********************************************
	 */
	private GLCanvas canvas;
	private LoadLevel newMaze = new LoadLevel("level1");
	private int screenWidth, screenHeight;
	private ArrayList<VisibleObject> visibleObjects;
	static Player player;
	private Camera camera;
	private UserInput input;
	private Maze maze;
	private long previousTime = Calendar.getInstance().getTimeInMillis();
	public int deltaTime = 0;
	private ArrayList<Guardian> tempGuard = newMaze.getGuardians();
	private ArrayList<Guard> guards = new ArrayList<Guard>();
	private ArrayList<Point> tempCamera = newMaze.getCameras();
	private ArrayList<GuardCamera> cameras = new ArrayList<GuardCamera>();
	private ArrayList<Spot> spots = new ArrayList<Spot>();
	private ArrayList<Point> tempSpot = newMaze.getSpots();

	private int checkdistance = 2;

	private ArrayList<Key> tempKey = newMaze.getKeys();
	private ArrayList<Keys> keys = new ArrayList<Keys>();
	private Inventory inventory = new Inventory();
	private Gun gun;

	private Animator anim;
	private boolean gameinitialized = false, gamepaused = false;

	private boolean startup = true;
	private boolean initialize = true;

	private LoadTexturesMaze loadedTexturesMaze;
	@SuppressWarnings("unused")
	private StatePauseMenu pausemenu;

	/*
	 * **********************************************
	 * * Initialization methods 
	 * **********************************************
	 */
	/**
	 * Initializes the MazeRunner game. The MazeRunner is drawn on the canvas
	 * defined by GameDriver. It adds itself as a GLEventListener.
	 */
	public MazeRunner(GLCanvas canvas) {

		GOD_MODE = false;
		this.canvas = canvas;

		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();

		initJOGL(); // Initialize JOGL.
		gameinitialized = true;
	}

	/**
	 * initJOGL() sets up JOGL to work properly.
	 * <p>
	 * It sets the capabilities we want for MazeRunner, and uses these to create
	 * the GLCanvas upon which MazeRunner will actually display our screen. To
	 * indicate to OpenGL that is has to enter a continuous loop, it uses an
	 * Animator, which is part of the JOGL api.
	 */
	private void initJOGL() {

		canvas.addGLEventListener(this);
		anim = new Animator(canvas);
		anim.start();
	}

	/**
	 * initializeObjects() creates all the objects needed for the game to start
	 * normally.
	 * <p>
	 * This includes the following:
	 * <ul>
	 * <li>the default Maze
	 * <li>the Player
	 * <li>the Camera
	 * <li>the User input
	 * </ul>
	 * <p>
	 * Remember that every object that should be visible on the screen, should
	 * be added to the visualObjects list of MazeRunner through the add method,
	 * so it will be displayed automatically.
	 */
	private void initObjects() {
		
		
		
		// We define an ArrayList of VisibleObjects to store all the objects
		// that need to be
		// displayed by MazeRunner.
		visibleObjects = new ArrayList<VisibleObject>();

		// Add the maze that we will be using.
		maze = new Maze(loadedTexturesMaze);
		visibleObjects.add(maze);

		// // Add the spots that we will be using
		// for (int i = 0; i < Loadlevel.getSpots.size(); i++) {
		// Spotlight tempSpot = new Spotlight(maze.SQUARE_SIZE,
		// loadedTexturesMaze.getTexture("spotlight"),
		// Loadlevel.getSpotlight.get(i));
		// visibleObjects.add(tempSpot);
		// }

		createKeys();
		maze.setKeys(keys);
		for (Keys temp : keys) {
			visibleObjects.add(temp);
		}

		gun = new Gun(2, 0, 1, 5);
		visibleObjects.add(gun);

		createGuards();
		createCameras();

		for (GuardCamera temp : cameras) {
			visibleObjects.add(temp);
		}

		for (Guard temp : guards) {
			visibleObjects.add(temp);
		}

		// Initialize the player.

		player = new Player(maze.startPoint.getX() * maze.SQUARE_SIZE
				+ maze.SQUARE_SIZE / 2, // x-position
				maze.SQUARE_SIZE*8 /2, // y-position
				maze.startPoint.getY() * maze.SQUARE_SIZE + maze.SQUARE_SIZE
						/ 2, // z-position
				90, 0); // horizontal and vertical angle

		camera = new Camera(player.getLocationX(), player.getLocationY(),
				player.getLocationZ(), player.getHorAngle(),
				player.getVerAngle());
		// guardcamera = new GuardCamera(15, 15, 15, player.locationX,
		// player.locationZ, previousTime);

		input = new UserInput(canvas);
		input.setMazeRunner(this);
		player.setControl(input);
		System.out.println("Klaar met creatie objecten");
	}

	/*
	 * **********************************************
	 * * OpenGL event handlers 
	 * **********************************************
	 */

	/**
	 * init(GLAutodrawable) is called to initialize the OpenGL context, giving
	 * it the proper parameters for viewing.
	 * <p>
	 * Implemented through GLEventListener. It sets up most of the OpenGL
	 * settings for the viewing, as well as the general lighting.
	 * <p>
	 * It is <b>very important</b> to realize that there should be no drawing at
	 * all in this method.
	 */
	public void init(GLAutoDrawable drawable) {
		if (initialize) {
			System.out.println("Maze textures init");
			initTextures();
			System.out.println("Creatie objects");
			initObjects(); // Initialize all the objects!
			initialize = false;
		}
		System.out.println("Mazerunner init");
		drawable.setGL(new DebugGL(drawable.getGL())); // We set the OpenGL
														// pipeline to Debugging
														// mode.
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		gl.glClearColor(0, 0, 0, 0); // Set the background color.
		// Now we set up our viewpoint.
		gl.glMatrixMode(GL.GL_PROJECTION); // We'll use orthogonal projection.
		gl.glLoadIdentity(); // Reset the current matrix.
		glu.gluPerspective(60, screenWidth / screenHeight, .1, 200); // Set up the parameters for perspective viewing.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// Enable back-face culling.
		gl.glCullFace(GL.GL_BACK);
		gl.glEnable(GL.GL_CULL_FACE);

		// Enable Z-buffering.
		gl.glEnable(GL.GL_DEPTH_TEST);

		// Set the shading model.
		gl.glShadeModel(GL.GL_SMOOTH);

		// Set invisible cursor
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		canvas.setCursor(blankCursor);
	}

	public void initTextures() {
		loadedTexturesMaze = new LoadTexturesMaze();
		System.out.println("Textures geladen");
	}

	/**
	 * display(GLAutoDrawable) is called upon whenever OpenGL is ready to draw a
	 * new frame and handles all of the drawing.
	 * <p>
	 * Implemented through GLEventListener. In order to draw everything needed,
	 * it iterates through MazeRunners' list of visibleObjects. For each
	 * visibleObject, this method calls the object's display(GL) function, which
	 * specifies how that object should be drawn. The object is passed a
	 * reference of the GL context, so it knows where to draw.
	 */
	public void display(GLAutoDrawable drawable) {
		if (startup) {
			init(drawable);
			startup = false;
		}
		if (!gamepaused) {
			GL gl = drawable.getGL();
			GLU glu = new GLU();

			// ALLES IS WIT HIERDOOR, TIJDELIJKE OPLOSSING
			gl.glColor3f(1, 1, 1);

			// Calculating time since last frame.
			Calendar now = Calendar.getInstance();
			long currentTime = now.getTimeInMillis();
			deltaTime = (int) (currentTime - previousTime);
			previousTime = currentTime;

			// Update any movement since last frame.
			updateMovement(deltaTime);
			updateCamera();
			updateKeys(deltaTime);

			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			gl.glLoadIdentity();
			glu.gluLookAt(camera.getLocationX(), camera.getLocationY(),
					camera.getLocationZ(), camera.getVrpX(), camera.getVrpY(),
					camera.getVrpZ(), camera.getVuvX(), camera.getVuvY(),
					camera.getVuvZ());

			// Display all the visible objects of MazeRunner.
			for (Iterator<VisibleObject> it = visibleObjects.iterator(); it
					.hasNext();) {
				it.next().display(gl);
			}

			gl.glLoadIdentity();
			// Flush the OpenGL buffer.
			gl.glFlush();
		}
	}

	/**
	 * reshape(GLAutoDrawable, int, int, int, int, int) is called upon whenever
	 * the viewport changes shape, to update the viewport setting accordingly.
	 * <p>
	 * Implemented through GLEventListener. This mainly happens when the window
	 * changes size, thus changin the canvas (and the viewport that OpenGL
	 * associates with it). It adjust the projection matrix to accomodate the
	 * new shape.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		// Setting the new screen size and adjusting the viewport.
		screenWidth = width;
		screenHeight = height;
		gl.glViewport(0, 0, screenWidth, screenHeight);

		// Set the new projection matrix.
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60, screenWidth / screenHeight, .1, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);

		try {
			Robot robot = new Robot();

			robot.mouseMove(screenWidth / 2, screenHeight / 2);

		} catch (AWTException e) {
			e.printStackTrace();
		}
		canvas.requestFocus();
	}

	/*
	 * **********************************************
	 * * Methods **********************************************
	 */

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */
	private void updateMovement(int deltaTime) {

		// update locations
		player.update(deltaTime);
		playerWallChecker(checkdistance);
		playerItemCheck();
		
		for (Guard temp : guards) {
			if (!temp.isAttack()) {
				temp.update(deltaTime);
			}
			//MENNO
//			temp.playerDetectie(player.locationX, player.locationZ);
//			temp.aanvallen(player.locationX, player.locationZ, deltaTime);
//			guardWallChecker(2, temp);
			
//			System.out.println("x: "+player.locationX + " z: "+player.locationZ+" EDITOR--- x: "
//			+(int)Math.floor( player.locationX / SQUARE_SIZE )+" z: "+(int)Math.floor( player.locationZ / SQUARE_SIZE ));

		}
		for (GuardCamera temp : cameras) {
			temp.updatePositie(player.locationX, player.locationZ);
			temp.alarm();
		}

	}

	public static Player getPlayer() {
		return player;
	}

	/**
	 * 
	 * @param checkdistance
	 */
	private void playerWallChecker(int checkdistance) {

		if (!GOD_MODE) {
			if (maze.isWall(
					player.getLocationX() + checkdistance
							* -Math.sin(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ() + checkdistance
							* -Math.cos(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveForward(false);
			}
			// Check backward direction for obstacles
			if (maze.isWall(
					player.getLocationX() - checkdistance
							* -Math.sin(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ() - checkdistance
							* -Math.cos(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveBack(false);
			}
			// Check left direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							+ checkdistance
							* -Math.sin(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							+ checkdistance
							* -Math.cos(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveLeft(false);
			}
			// check right direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							- checkdistance
							* -Math.sin(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							- checkdistance
							* -Math.cos(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveRight(false);
			}

			// Check left-forward direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							+ checkdistance// Math.sqrt(2)
							* -Math.sin(Math.PI * (player.getHorAngle() + 45)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							+ checkdistance
							/ Math.sqrt(2)
							* -Math.cos(Math.PI * (player.getHorAngle() + 45)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setLeftForwardWall(true);
			}

			// Check right-forward direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							- checkdistance// Math.sqrt(2)
							* -Math.sin(Math.PI * (player.getHorAngle() + 135)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							- checkdistance
							/ Math.sqrt(2)
							* -Math.cos(Math.PI * (player.getHorAngle() + 135)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setRightForwardWall(true);
			}
		}
	}

	private void guardWallChecker(int checkdistance, Guard res) {

		if (!GOD_MODE) {
			if (maze.isWall(
					res.getLocationX() + checkdistance
							* -Math.sin(Math.PI * res.getHorAngle() / 180),
					res.getLocationZ() + checkdistance
							* -Math.cos(Math.PI * res.getHorAngle() / 180))) {
				res.setCanMoveForward(false);
			}
			// Check backward direction for obstacles
			if (maze.isWall(
					res.getLocationX() - checkdistance
							* -Math.sin(Math.PI * res.getHorAngle() / 180),
					res.getLocationZ() - checkdistance
							* -Math.cos(Math.PI * res.getHorAngle() / 180))) {
				res.setCanMoveBack(false);
			}
			// Check left direction for obstacles
			if (maze.isWall(
					res.getLocationX()
							+ checkdistance
							* -Math.sin(Math.PI * (res.getHorAngle() + 90)
									/ 180),
					res.getLocationZ()
							+ checkdistance
							* -Math.cos(Math.PI * (res.getHorAngle() + 90)
									/ 180))) {
				res.setCanMoveLeft(false);
			}
			// check right direction for obstacles
			if (maze.isWall(
					res.getLocationX()
							- checkdistance
							* -Math.sin(Math.PI * (res.getHorAngle() + 90)
									/ 180),
					res.getLocationZ()
							- checkdistance
							* -Math.cos(Math.PI * (res.getHorAngle() + 90)
									/ 180))) {
				res.setCanMoveRight(false);
			}

			// Check left-forward direction for obstacles
			if (maze.isWall(
					res.getLocationX()
							+ checkdistance// Math.sqrt(2)
							* -Math.sin(Math.PI * (res.getHorAngle() + 45)
									/ 180),
					res.getLocationZ()
							+ checkdistance
							/ Math.sqrt(2)
							* -Math.cos(Math.PI * (res.getHorAngle() + 45)
									/ 180))) {
				res.setLeftForwardWall(true);
			}

			// Check right-forward direction for obstacles
			if (maze.isWall(
					res.getLocationX()
							- checkdistance// Math.sqrt(2)
							* -Math.sin(Math.PI * (res.getHorAngle() + 135)
									/ 180),
					res.getLocationZ()
							- checkdistance
							/ Math.sqrt(2)
							* -Math.cos(Math.PI * (res.getHorAngle() + 135)
									/ 180))) {
				res.setRightForwardWall(true);
			}
		}
	}

	/**
	 * updateCamera() updates the camera position and orientation.
	 * <p>
	 * This is done by copying the locations from the Player, since MazeRunner
	 * runs on a first person view.
	 */
	private void updateCamera() {
		camera.setLocationX(player.getLocationX());
		camera.setLocationY(player.getLocationY());
		camera.setLocationZ(player.getLocationZ());
		camera.setHorAngle(player.getHorAngle());
		camera.setVerAngle(player.getVerAngle());
		camera.calculateVRP();
	}
	
	private void updateKeys(int deltaTime) {
		for(Keys k: keys){
			k.updateDeltaTime(deltaTime);
		}
	}
	

	/**
	 * 
	 * @return
	 */
	public void pauseSwitch() {
		if (!gamepaused && gameinitialized) {
			canvas.removeMouseListener(input);
			canvas.removeMouseMotionListener(input);
			canvas.removeKeyListener(input);
			gamepaused = true;
			canvas.removeGLEventListener(this);
//			canvas.setCursor(null);
//			canvas.addKeyListener(pausemenu);
//			canvas.addGLEventListener(pausemenu);
//			canvas.addMouseListener(pausemenu);
			pausemenu = new StatePauseMenu(canvas, this);
		} else if (gamepaused && gameinitialized) {
			System.out.println("Mazerunner resume called");
			canvas.addMouseListener(input);
			canvas.addMouseMotionListener(input);
			canvas.addKeyListener(input);
			gamepaused = false;
			canvas.addGLEventListener(this);
			startup = true;
			pausemenu = null;
		}
	}

	/**
	 * Maakt een arraylist van guardobjecten
	 */

	public void createGuards() {
		for (Guardian temp : tempGuard) {
			ArrayList<Point> temproute = temp.getCopyRoutes();
			Point a = temp.getRoute(0);
			Guard res = new Guard(a.getX(), 6, a.getY(), temproute);
			guards.add(res);
		}
	}

	/**
	 * Maakt een arraylist van cameraobjecten
	 */
	public void createCameras() {
		for (Point temp : tempCamera) {
			GuardCamera res = new GuardCamera(temp.getX(), 6, temp.getY());
			cameras.add(res);
		}
	}

	public void createSpots() {
		for (Point temp : tempSpot) {
			Spot res = new Spot();
			spots.add(res);
		}
	}
	
	public void createKeys() {
		for (Key temp : tempKey) {
			Point a = temp.getKey();
			Point b = temp.getDoor();
			Keys res = new Keys(a.getX(), 0, a.getY(), b.getX(), b.getY(), maze.SQUARE_SIZE);
			keys.add(res);
		}
	}
	
	private void playerItemCheck(){
		int gebied = 1; 
		//KEYS
		for(Keys k: keys){
			if(Math.abs(player.locationX - k.locationX) < gebied  && Math.abs(player.locationZ - k.locationZ) < gebied){
				inventory.addKey(k);
				visibleObjects.remove(k);
				
				//DEBUG CHECK INVENTORY
				System.out.println("aantal Keys: "+inventory.getKeys().size());
			}
		}
		
		//GUN
		/*
		if(Math.abs(player.locationX - gun.locationX) < gebied  && Math.abs(player.locationZ - gun.locationZ) < gebied){
			//visibleObjects.remove(gun);
			gun.pickedUp();
			gun.horAngle = player.horAngle;
			gun.verAngle = player.verAngle;
			gun.locationX = player.locationX;
			gun.locationY = player.locationY;
			gun.locationZ = player.locationZ;
		}
		*/
		
	}
	
	public void openDoor(){
		for(Keys k: inventory.getKeys()){
			Point door = k.getDoor();
			double xdoor = (k.getDoor().getX()+0.5)*maze.SQUARE_SIZE;
			double zdoor = (k.getDoor().getY()+0.5)*maze.SQUARE_SIZE;
			double gebied = 0.5*maze.SQUARE_SIZE + checkdistance;
			
			if((Math.abs(player.locationX-xdoor) < gebied) && (Math.abs(player.locationZ-zdoor) < gebied)){
				maze.openDoor(door);
				inventory.removeKey(k);
				Sound.SoundEffect.DOORSLIDE.play();
				break;
			}
		}
	}
	
	/**
	 * MENNO BEGIN
	 * dit werkt niet goed, moet helemaal veranderd worden. de kogels worden wel aangemaakt maar dan hoog in de lucht.
	 */
	public void shoot(int xMouse, int yMouse){
		Kogel kogel = new Kogel(xMouse, yMouse, 0);
		kogel.updateShoot(player.getHorAngle(), player.getVerAngle(), deltaTime);
		visibleObjects.add(kogel);
		System.out.println("kom je hier wel");
		// visibleObjects.add(kogel);
	}
	
	/**
	 * 
	 * MENNO EIND
	 */

	public void setWalkingSpeed(double speed) {
		player.setSpeed(speed);
	}
	
	public double getWalkingSpeed(){
		return player.getSpeed();
	}

	// /////////////////////////////////////NOT
	// USED////////////////////////////////////

	/**
	 * displayChanged(GLAutoDrawable, boolean, boolean) is called upon whenever
	 * the display mode changes.
	 * <p>
	 * Implemented through GLEventListener. Seeing as this does not happen very
	 * often, we leave this unimplemented.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void mouseClicked(MouseEvent me) {


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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
