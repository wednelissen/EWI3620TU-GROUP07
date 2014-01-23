package MazeRunner.Fundamental;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import LevelEditor.Guardian;
import LevelEditor.Key;
import LevelEditor.LoadLevel;
import MazeRunner.GameStates.StateBusted;
import MazeRunner.GameStates.StateGameEnded;
import MazeRunner.GameStates.StatePauseMenu;
import MazeRunner.Objects.ControlCenter;
import MazeRunner.Objects.FinishPoint;
import MazeRunner.Objects.Keys;
import MazeRunner.Objects.Kogel;
import MazeRunner.Objects.Maze;
import MazeRunner.Objects.Spotlight;
import MazeRunner.Objects.VisibleObject;
import MazeRunner.Opponents.Guard;
import MazeRunner.Opponents.GuardCamera;
import MazeRunner.Opponents.RouteAlgoritme;
import MazeRunner.Player.Camera;
import MazeRunner.Player.Inventory;
import MazeRunner.Player.Player;
import MazeRunner.Player.UserInput;
import MazeRunner.Player.WallChecker;
import MazeRunner.Sound.SoundEffect;

/**
 * MazeRunner is the base class of the game, functioning as the view controller
 * and game logic manager.
 * 
 * 
 */
public class MazeRunner implements GLEventListener, MouseListener {
	static final long serialVersionUID = 7526471155622776147L;
	public static boolean GOD_MODE = false;

	/*
	 * **********************************************
	 * * Local variables **********************************************
	 */
	private GLCanvas canvas;
	private Maze maze;

	private int screenWidth, screenHeight;
	private ArrayList<VisibleObject> visibleObjects;
	static Player player;
	private Camera camera;
	private UserInput input;
	private long previousTime = Calendar.getInstance().getTimeInMillis();
	private int deltaTime = 0;

	private static int amountSpots;

	private ArrayList<Guard> guards = new ArrayList<Guard>();
	private ArrayList<GuardCamera> cameras = new ArrayList<GuardCamera>();

	private int checkdistance = 2;

	private ArrayList<Keys> keys = new ArrayList<Keys>();

	private ArrayList<Spotlight> spotlights = new ArrayList<Spotlight>();
	private ArrayList<ControlCenter> controlCenters = new ArrayList<ControlCenter>();

	private WallChecker playerwallchecker;

	private Inventory inventory = new Inventory();

	private boolean gameinitialized = false, gamepaused = false;

	private boolean startup = true;
	private boolean initialize = true;

	private StatePauseMenu pausemenu;
	private HighScore score;

	private boolean watchFromCamera = false;
	private int watchCameraNumber = 0;

	/**
	 * Initializes the MazeRunner game. The MazeRunner is drawn on the canvas
	 * defined by GameDriver. It adds itself as a GLEventListener.
	 */
	public MazeRunner(GLCanvas canvas) {

		GOD_MODE = false;
		this.canvas = canvas;
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		canvas.addGLEventListener(this);
		gameinitialized = true;
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

	private void initObjects(GL gl) {

		// We define an ArrayList of VisibleObjects to store all the objects
		// that need to be displayed by MazeRunner.
		visibleObjects = new ArrayList<VisibleObject>();

		// Add the maze that we will be using.
		maze = new Maze();
		LoadLevel loadLevelMaze = maze.getLoadLevel();
		score = new HighScore(null, 0, maze.getLevelName());

		ArrayList<Guardian> tempGuard = loadLevelMaze.getGuardians();
		ArrayList<Point> tempCamera = loadLevelMaze.getCameras();
		ArrayList<Point> tempSpots = loadLevelMaze.getSpots();
		ArrayList<Point> tempControlCenter = loadLevelMaze.getControlCenters();
		ArrayList<Key> tempKey = loadLevelMaze.getKeys();

		visibleObjects.add(maze);

		createSpots(gl, tempSpots);
		for (Spotlight temp : spotlights) {
			visibleObjects.add(temp);
		}

		createKeys(tempKey);
		maze.setKeys(keys);
		for (Keys temp : keys) {
			visibleObjects.add(temp);
		}

		createCameras(tempCamera);
		for (GuardCamera temp : cameras) {
			visibleObjects.add(temp);
		}

		createGuards(tempGuard);
		for (Guard temp : guards) {
			visibleObjects.add(temp);
		}

		createControlCenter(tempControlCenter);
		for (ControlCenter temp : controlCenters) {
			visibleObjects.add(temp);
		}
		maze.setControlCenters(controlCenters);

		FinishPoint finishpoint = createFinishPoint();
		visibleObjects.add(finishpoint);

		// Initialize the player.
		double startHorAnglePlayer = calculateBestStartAngle();
		System.out.println("startHorAnglePlayer: " + startHorAnglePlayer);
		player = new Player(maze.startPoint.getX() * Maze.SQUARE_SIZE
				+ Maze.SQUARE_SIZE / 2, // x-position
				Maze.SQUARE_SIZE / 2, // y-position
				maze.startPoint.getY() * Maze.SQUARE_SIZE + Maze.SQUARE_SIZE

				/ 2, // z-position
				startHorAnglePlayer, 0); // horizontal and vertical angle

		camera = new Camera(player.getLocationX(), player.getLocationY(),
				player.getLocationZ(), player.getHorAngle(),
				player.getVerAngle());

		input = new UserInput(canvas);
		input.setMazeRunner(this);
		player.setControl(input);
		player.setEndPoint(maze.endPoint);
		input.restPLayer(); // zorgt dat de player altijd start in rust
							// toestand.

	}

	/*
	 * **********************************************
	 * * OpenGL event handlers **********************************************
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

		drawable.setGL(new DebugGL(drawable.getGL())); // We set the OpenGL
														// pipeline to Debugging
														// mode.

		GL gl = drawable.getGL();
		GLU glu = new GLU();
		if (initialize) {
			initObjects(gl); // Initialize all the objects!
			playerwallchecker = new WallChecker(player, checkdistance, maze); // must
																				// be
																				// done
																				// after
																				// the
																				// maze
																				// has
																				// been
																				// created
			initialize = false;
		}

		gl.glClearColor(0, 0, 0, 0); // Set the background color.

		// Now we set up our viewpoint.
		gl.glMatrixMode(GL.GL_PROJECTION); // We'll use orthogonal projection.
		gl.glLoadIdentity(); // Reset the current matrix.
		glu.gluPerspective(60, screenWidth / screenHeight, .1, 200); // Set up
																		// the
																		// parameters
																		// for
																		// perspective
																		// viewing.
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

			// Calculating time since last frame.
			Calendar now = Calendar.getInstance();
			long currentTime = now.getTimeInMillis();
			deltaTime = (int) (currentTime - previousTime);
			previousTime = currentTime;
			// Update any movement since last frame.
			updateMovement(deltaTime);
			updateCamera();
			updateKeys(deltaTime);
			updateHighscore(deltaTime);
			if (player.getReachedEndOfLevel()) {
				endGame();
			}
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			gl.glLoadIdentity();
			glu.gluLookAt(camera.getLocationX(), camera.getLocationY(),
					camera.getLocationZ(), camera.getVrpX(), camera.getVrpY(),
					camera.getVrpZ(), camera.getVuvX(), camera.getVuvY(),
					camera.getVuvZ());

			// Display all the spotslights of MazeRunner.
			for (VisibleObject obj : visibleObjects)
				if (obj instanceof Spotlight)
					obj.display(gl);

			// Displays the rest of the visiblObjects
			for (VisibleObject obj : visibleObjects)
				if (!(obj instanceof Spotlight))
					obj.display(gl);

			// for (VisibleObject obj : visibleObjects
			// obj.display(gl);

			gl.glLoadIdentity();
			// Flush the OpenGL buffer.
			// gl.glFlush();
		}
	}

	private void updateHighscore(int deltaTime) {
		score.update(deltaTime);
	}

	public void updateHighScoreCamera() {
		score.alarmedCameraUpdate();
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

		canvas.requestFocus();
	}

	/*
	 * **********************************************
	 * * Methods **********************************************
	 */

	/**
	 * kijkt om het begin punt waar geen muur is en zal dat de starthoek maken.
	 * een deur is ook geen muur dus de player kan ook beginnen met het zicht op
	 * een deur.
	 * 
	 * @return nieuwe hoek van de player
	 */
	private double calculateBestStartAngle() {
		Point begin = maze.startPoint;
		int x = (int) begin.getX();
		int z = (int) begin.getY();

		if (!maze.isWall(x, z - 1)) { // boven v/h startpunt
			return 0;
		} else if (!maze.isWall(x, z + 1)) { // onder v/h startpunt
			System.out.println("rare true: " + maze.isWall(x, z - 1));
			return 180;
		} else if (!maze.isWall(x + 1, z)) { // rechts v/h startpunt
			return -90;
		} else if (!maze.isWall(x - 1, z)) { // links v/h startpunt
			return 90;
		} else {
			return 0;
		}
	}

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */
	private void updateMovement(int deltaTime) {

		// update locations

		player.update(deltaTime); // Player
		if (playerwallchecker != null) {
			playerwallchecker.check();
		}
		playerItemCheck();

		boolean mazeChange = maze.mazeChanged(); // check het verschil met de
													// maze met de vorige keer.
													// alleen als de maze
													// veranderd zullen camera's
													// waar eerst geen guard bij
													// kon
													// kijken of er nu wel een
													// guard bij kan komen.

		for (GuardCamera cam : cameras) { // GuardCameras
			cam.updatePosition(player.locationX, player.locationZ);
			if (cam.alarm() || (mazeChange && cam.getNeedGuard())) {
				System.out.println("mazeChange: " + mazeChange);
				System.out.println("checken nu de maze veranderd is");
				ArrayList<Point> closestGuardRoute = new ArrayList<Point>();
				Guard closestGuard = null;
				int j = Integer.MAX_VALUE;
				for (Guard guard : guards) {
					RouteAlgoritme route = new RouteAlgoritme(maze);
					ArrayList<Point> guardRoute = route.algorithm(
							cam.getCurrentPosition(), guard.getEindPositie());
					if (guardRoute != null) {
						if (guardRoute.size() < j) {
							j = guardRoute.size();
							closestGuardRoute = guardRoute;
							closestGuard = guard;
						}
					}

				}
				// vanaf hier is de guard met de kortste pad naar de camera
				// gevonden of de closestGuard is nog null omdat geen van de
				// guards de camera kon bereiken.

				if (closestGuard != null) {

					cam.setNeedGuard(false);
					ArrayList<Point> tempRoute = new ArrayList<Point>();
					tempRoute.add(closestGuard.getHuidigepositie());
					tempRoute.addAll(closestGuardRoute);
					closestGuardRoute = tempRoute;
					// closestGuard.setPatrol(false);
					closestGuard.setAlarmed(true);
					closestGuard.setNewPatrolPath(closestGuardRoute);
					closestGuard.setGuardCamera(cam);
				} else {
					cam.setNeedGuard(true); // er moet nog steeds een guard naar
											// de camera worden gestuurd.
				}

			}
		}

		for (Guard guard : guards) { // Guards
			guard.run(deltaTime, player.locationX, player.locationZ);
			if (guard.isBusted()) {
				System.out.println("Busted");
				SoundEffect.SHOT.play();
				toStateBusted();
			}
		}

		// DEBUG:
		// System.out.println("x: "+player.locationX +
		// " z: "+player.locationZ+" EDITOR--- x: "
		// +(int)Math.floor( player.locationX / SQUARE_SIZE
		// )+" z: "+(int)Math.floor( player.locationZ / SQUARE_SIZE ));

	}

	/**
	 * Stops the game and initializes the game ended state.
	 */
	public void endGame() {
		canvas.removeMouseListener(input);
		canvas.removeMouseMotionListener(input);
		canvas.removeKeyListener(input);
		gamepaused = true;
		canvas.removeGLEventListener(this);
		new StateGameEnded(canvas, score);
	}

	public static Player getPlayer() {
		return player;
	}

	public void toStateBusted() {
		canvas.removeMouseListener(input);
		canvas.removeMouseMotionListener(input);
		canvas.removeKeyListener(input);
		gamepaused = true;
		canvas.removeGLEventListener(this);
		new StateBusted(canvas);
	}

	/**
	 * updateCamera() updates the camera position and orientation.
	 * <p>
	 * This is done by copying the locations from the Player, since MazeRunner
	 * runs on a first person view.
	 */
	private void updateCamera() {
		if (!watchFromCamera) {
			camera.setLocationX(player.getLocationX());
			camera.setLocationY(player.getLocationY());
			camera.setLocationZ(player.getLocationZ());
		} else {
			camera.setLocationX(cameras.get(watchCameraNumber).getLocationX());
			camera.setLocationY(cameras.get(watchCameraNumber).getLocationY() * 0.8);
			camera.setLocationZ(cameras.get(watchCameraNumber).getLocationZ());
		}
		camera.setHorAngle(player.getHorAngle());
		camera.setVerAngle(player.getVerAngle());
		camera.calculateVRP();
	}

	public boolean watchFromCamera() {
		//int gebied = 2;
		for (ControlCenter c : controlCenters) {
			if (Math.abs(player.locationX - c.locationX) < (c.getSizeX() + checkdistance)
					&& Math.abs(player.locationZ - c.locationZ) < (c.getSizeZ()+checkdistance)) {
				watchFromCamera = !watchFromCamera;
				if (watchFromCamera) {
					visibleObjects.add(player);
					player.lastKnownHorAngle();
				} else {
					visibleObjects.remove(player);
					player.setLastKnownHorAngle();
				}
				return watchFromCamera;
			}
		}
		return false;

	}

	public void watchFromOtherCamera(boolean plus) {
		if (plus) {
			watchCameraNumber++;
		} else {
			watchCameraNumber--;
		}
		if (watchCameraNumber < 0) {
			watchCameraNumber = cameras.size() - 1;
		}

		if (watchCameraNumber > (cameras.size() - 1)) {
			watchCameraNumber -= (cameras.size());
		}
		System.out.println(watchCameraNumber);
	}

	private void updateKeys(int deltaTime) {
		for (Keys k : keys) {
			k.updateDeltaTime(deltaTime);
		}
	}

	/**
	 * Either pauses the game and shows the pause menu, or unpauses the game and
	 * redraws it on the canvas. In case of unpausing, previousTime is set to
	 * the current instant, to prevent all visibleObjects from updating.
	 */
	public void pauseSwitch() {
		if (!gamepaused && gameinitialized) {
			canvas.removeMouseListener(input);
			canvas.removeMouseMotionListener(input);
			canvas.removeKeyListener(input);
			gamepaused = true;
			canvas.removeGLEventListener(this);

			if (pausemenu == null) {
				pausemenu = new StatePauseMenu(canvas, this);
			} else {
				pausemenu.resume();
			}

		} else if (gamepaused && gameinitialized) {
			turnLightsOn();
			System.out.println("Mazerunner resume called");
			input.resetMousePosition();
			canvas.addMouseListener(input);
			canvas.addMouseMotionListener(input);
			canvas.addKeyListener(input);
			gamepaused = false;
			canvas.addGLEventListener(this);
			startup = true;
			previousTime = Calendar.getInstance().getTimeInMillis();
		}
	}

	/**
	 * Maakt een arraylist van guardobjecten
	 * 
	 * @param tempGuard
	 */

	public void createGuards(ArrayList<Guardian> tempGuard) {
		for (Guardian temp : tempGuard) {
			ArrayList<Point> temproute = temp.getCopyRoutes();
			Point a = temp.getRoute(0);
			Guard res = new Guard(a.getX(), 6, a.getY(), temproute);
			res.maze = this.maze;
			guards.add(res);
		}
	}

	/**
	 * Maakt een arraylist van cameraobjecten
	 * 
	 * @param tempCamera
	 */
	public void createCameras(ArrayList<Point> tempCamera) {
		for (Point temp : tempCamera) {
			GuardCamera res = new GuardCamera(temp.getX(), 6, temp.getY(), this);
			cameras.add(res);
		}
	}

	public void turnLightsOn() {
		for (VisibleObject temp : visibleObjects) {
			if (temp instanceof Spotlight) {
				Spotlight spot = (Spotlight) temp;
				spot.lightsOn();
			}
		}
	}

	public void createSpots(GL gl, ArrayList<Point> tempSpots) {
		// Hoogte van de spot, moet nog veranderen
		double spotHeight = 5;
		int i = 0;
		for (Point temp : tempSpots) {
			Spotlight res = new Spotlight(gl, 5,
					LoadTexturesMaze.getTexture("spotlight"), temp.getX(),
					spotHeight, temp.getY(), i);
			spotlights.add(res);
			i++;
		}
		amountSpots = i;
	}

	public void createKeys(ArrayList<Key> tempKey) {

		for (Key temp : tempKey) {
			Point a = temp.getKey();
			Point b = temp.getDoor();

			Keys res = new Keys(a.getX(), 0, a.getY(), b.getX(), b.getY(),
					Maze.SQUARE_SIZE);

			keys.add(res);
		}
	}

	public void createControlCenter(ArrayList<Point> tempControlCenter) {
		for (Point temp : tempControlCenter) {

			ControlCenter res = new ControlCenter(temp.getX(), 0, temp.getY());

			controlCenters.add(res);
		}
	}

	private void playerItemCheck() {
		int gebied = 1;
		// KEYS
		for (Keys k : keys) {
			if (Math.abs(player.locationX - k.locationX) < gebied
					&& Math.abs(player.locationZ - k.locationZ) < gebied) {

				inventory.addKey(k);
				visibleObjects.remove(k);

				// DEBUG CHECK INVENTORY
				System.out
						.println("aantal Keys: " + inventory.getKeys().size());
			}
		}
	}

	public void openDoor() {
		for (Keys k : inventory.getKeys()) {
			Point door = k.getDoor();

			double xdoor = (k.getDoor().getX() + 0.5) * Maze.SQUARE_SIZE;
			double zdoor = (k.getDoor().getY() + 0.5) * Maze.SQUARE_SIZE;
			double gebied = 0.5 * Maze.SQUARE_SIZE + checkdistance;

			if ((Math.abs(player.locationX - xdoor) < gebied)
					&& (Math.abs(player.locationZ - zdoor) < gebied)) {

				maze.openDoor(door);
				inventory.removeKey(k);
				SoundEffect.DOORSLIDE.play();
				break;
			}
		}
	}

	public FinishPoint createFinishPoint() {
		FinishPoint finishpoint = new FinishPoint(maze.getEndPoint().getX(), 1,
				maze.getEndPoint().getY());
		return finishpoint;
	}

	public static int amountofSpots() {
		return amountSpots;
	}

	/**
	 * dit werkt niet goed, moet helemaal veranderd worden. de kogels worden wel
	 * aangemaakt maar dan hoog in de lucht.
	 */
	public void shoot(int xMouse, int yMouse) {
		Kogel kogel = new Kogel(xMouse, yMouse, 0);
		kogel.updateShoot(player.getHorAngle(), player.getVerAngle(), deltaTime);
		visibleObjects.add(kogel);

		// visibleObjects.add(kogel);
	}

	public void setWalkingSpeed(double speed) {
		player.setSpeed(speed);
	}

	public double getWalkingSpeed() {
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
	public void mouseReleased(MouseEvent arg0) {
		// NOT USED

	}

}
