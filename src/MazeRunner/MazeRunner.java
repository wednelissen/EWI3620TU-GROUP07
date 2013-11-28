package MazeRunner;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/** 
 * MazeRunner is the base class of the game, functioning as the view controller and game logic manager.
 * <p>
 * Functioning as the window containing everything, it initializes both JOGL, the 
 * game objects and the game logic needed for MazeRunner.
 * <p>
 * For more information on JOGL, visit <a href="http://jogamp.org/wiki/index.php/Main_Page">this page</a>
 * for general information, and <a href="https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/">this page</a>
 * for the specification of the API.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */


public class MazeRunner implements GLEventListener {
	static final long serialVersionUID = 7526471155622776147L;
	public static boolean GOD_MODE = false;

	/*
 * **********************************************
 * *			Local variables					*
 * **********************************************
 */
	private GLCanvas canvas;

	private int screenWidth, screenHeight;									// Screen size.
	private ArrayList<VisibleObject> visibleObjects;						// A list of objects that will be displayed on screen.
	private Player player;													// The player object.
	private Camera camera;													// The camera object.
	private UserInput input;												// The user input object that controls the player.
	private Maze maze; 														// The maze.
	private long previousTime = Calendar.getInstance().getTimeInMillis(); 	// Used to calculate elapsed time.
	
	private Animator anim;
	private boolean gameinitialized = false, gamepaused = false;
	
	private boolean startup = true;
/*
 * **********************************************
 * *		Initialization methods				*
 * **********************************************
 */
	/**
	 * Initializes the MazeRunner game.
	 * The MazeRunner is drawn on the canvas defined by GameDriver. It adds itself
	 * as a GLEventListener.
	 */
	public MazeRunner(GLCanvas canvas) {
	
		GOD_MODE = false;
		this.canvas = canvas;
		
				
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		System.out.println("Screen:" + screenHeight);
		initJOGL();							// Initialize JOGL.
		initObjects();						// Initialize all the objects!
		gameinitialized = true;
	}
	
	/**
	 * initJOGL() sets up JOGL to work properly.
	 * <p>
	 * It sets the capabilities we want for MazeRunner, and uses these to create the GLCanvas upon which 
	 * MazeRunner will actually display our screen. To indicate to OpenGL that is has to enter a 
	 * continuous loop, it uses an Animator, which is part of the JOGL api.
	 */
	private void initJOGL()	{

		/* We need to add a GLEventListener to interpret OpenGL events for us. Since MazeRunner implements
		 * GLEventListener, this means that we add the necessary init(), display(), displayChanged() and reshape()
		 * methods to this class.
		 * These will be called when we are ready to perform the OpenGL phases of MazeRunner. 
		 */
		canvas.addGLEventListener( this );
		
		/* We need to create an internal thread that instructs OpenGL to continuously repaint itself.
		 * The Animator class handles that for JOGL.
		 */
		anim = new Animator( canvas );
		anim.start();
	}
	
	/**
	 * initializeObjects() creates all the objects needed for the game to start normally.
	 * <p>
	 * This includes the following:
	 * <ul>
	 * <li> the default Maze
	 * <li> the Player
	 * <li> the Camera
	 * <li> the User input
	 * </ul>
	 * <p>
	 * Remember that every object that should be visible on the screen, should be added to the
	 * visualObjects list of MazeRunner through the add method, so it will be displayed 
	 * automatically. 
	 */
	private void initObjects()	{
		// We define an ArrayList of VisibleObjects to store all the objects that need to be
		// displayed by MazeRunner.
		visibleObjects = new ArrayList<VisibleObject>();
		
			// Add the maze that we will be using.
			maze = new Maze();
			visibleObjects.add( maze );

			// Initialize the player.
			player = new Player( 6 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2, 	// x-position
					maze.SQUARE_SIZE / 2,							// y-position
					5 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2, 	// z-position
					90, 0 );										// horizontal and vertical angle

			camera = new Camera( player.getLocationX(), player.getLocationY(), player.getLocationZ(), 
					player.getHorAngle(), player.getVerAngle() );

			input = new UserInput(canvas);
			input.setMazeRunner(this);
			player.setControl(input);
		
	}

/*
 * **********************************************
 * *		OpenGL event handlers				*
 * **********************************************
 */

	/**
	 * init(GLAutodrawable) is called to initialize the OpenGL context, giving it the proper parameters for viewing.
	 * <p>
	 * Implemented through GLEventListener. 
	 * It sets up most of the OpenGL settings for the viewing, as well as the general lighting.
	 * <p> 
	 * It is <b>very important</b> to realize that there should be no drawing at all in this method.
	 */
	public void init(GLAutoDrawable drawable) {
		System.out.println("Mazerunner init");
		drawable.setGL( new DebugGL(drawable.getGL() )); // We set the OpenGL pipeline to Debugging mode.
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        
        gl.glClearColor(0, 0, 0, 0);								// Set the background color.
        // Now we set up our viewpoint.
        gl.glMatrixMode( GL.GL_PROJECTION );						// We'll use orthogonal projection.
        gl.glLoadIdentity();										// Reset the current matrix.
        glu.gluPerspective( 60, screenWidth/screenHeight, .1, 200 );	// Set up the parameters for perspective viewing.
        gl.glMatrixMode( GL.GL_MODELVIEW );
      
        
        // Enable back-face culling.
        gl.glCullFace( GL.GL_BACK );
        gl.glEnable( GL.GL_CULL_FACE );
        
        // Enable Z-buffering.
        gl.glEnable( GL.GL_DEPTH_TEST );
        
        // Set and enable the lighting.
//        float lightPosition[] = { 0.0f, 50.0f, 0.0f, 1.0f }; 			// High up in the sky!
//        float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };				// White light!
//        gl.glLightfv( GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0 );	// Note that we're setting Light0.
//        gl.glLightfv( GL.GL_LIGHT0, GL.GL_AMBIENT, lightColour, 0);
//        gl.glEnable( GL.GL_LIGHTING );
//        gl.glEnable( GL.GL_LIGHT0 );
        
        // Set the shading model.
        gl.glShadeModel( GL.GL_SMOOTH );
        
      //Set invisible cursor
      		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
      		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
      			    cursorImg, new Point(0, 0), "blank cursor");
      				canvas.setCursor(blankCursor);
	}
	
	/**
	 * display(GLAutoDrawable) is called upon whenever OpenGL is ready to draw a new frame and handles all of the drawing.
	 * <p>
	 * Implemented through GLEventListener. 
	 * In order to draw everything needed, it iterates through MazeRunners' list of visibleObjects. 
	 * For each visibleObject, this method calls the object's display(GL) function, which specifies 
	 * how that object should be drawn. The object is passed a reference of the GL context, so it 
	 * knows where to draw.
	 */
	public void display(GLAutoDrawable drawable) {
        if(startup){
        	init(drawable);
        	startup = false;
        }
        if(!gamepaused){
		GL gl = drawable.getGL();
		GLU glu = new GLU();
		
		// Calculating time since last frame.
		Calendar now = Calendar.getInstance();		
		long currentTime = now.getTimeInMillis();
		int deltaTime = (int)(currentTime - previousTime);
		previousTime = currentTime;
		
		// Update any movement since last frame.
		updateMovement(deltaTime);
		updateCamera();
		 
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		gl.glLoadIdentity();
        glu.gluLookAt( camera.getLocationX(), camera.getLocationY(), camera.getLocationZ(), 
 			   camera.getVrpX(), camera.getVrpY(), camera.getVrpZ(),
 			   camera.getVuvX(), camera.getVuvY(), camera.getVuvZ() );

        // Display all the visible objects of MazeRunner.
        for( Iterator<VisibleObject> it = visibleObjects.iterator(); it.hasNext(); ) {
        	it.next().display(gl);
        }

        gl.glLoadIdentity();
        // Flush the OpenGL buffer.
        gl.glFlush();
        }
	}

	
	/**
	 * displayChanged(GLAutoDrawable, boolean, boolean) is called upon whenever the display mode changes.
	 * <p>
	 * Implemented through GLEventListener. 
	 * Seeing as this does not happen very often, we leave this unimplemented.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// GL gl = drawable.getGL();
	}
	
	/**
	 * reshape(GLAutoDrawable, int, int, int, int, int) is called upon whenever the viewport changes shape, to update the viewport setting accordingly.
	 * <p>
	 * Implemented through GLEventListener. 
	 * This mainly happens when the window changes size, thus changin the canvas (and the viewport 
	 * that OpenGL associates with it). It adjust the projection matrix to accomodate the new shape.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();
		
		// Setting the new screen size and adjusting the viewport.
		screenWidth = width;
		screenHeight = height;
		gl.glViewport( 0, 0, screenWidth, screenHeight );
		
		// Set the new projection matrix.
		gl.glMatrixMode( GL.GL_PROJECTION );
		gl.glLoadIdentity();
		glu.gluPerspective( 60, screenWidth/screenHeight, .1, 200 );
		gl.glMatrixMode( GL.GL_MODELVIEW );
		
		try {
			Robot robot = new Robot();
			
			robot.mouseMove(screenWidth/2,screenHeight/2);
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		canvas.requestFocus();
	}

/*
 * **********************************************
 * *				Methods						*
 * **********************************************
 */

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */
	private void updateMovement(int deltaTime)
	{
		
			//Check forward direction for obstacles if GOD mode is off
		if(!GOD_MODE){
			if(maze.isWall(player.getLocationX() + 2*-Math.sin( Math.PI * player.getHorAngle() / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 )
					,player.getLocationZ() + 2*-Math.cos( Math.PI * player.getHorAngle() / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 ))){
				player.setCanMoveForward(false);
			}
			//Check backward direction for obstacles
			if(maze.isWall(player.getLocationX() - 2*-Math.sin( Math.PI * player.getHorAngle() / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 )
					,player.getLocationZ() - 2*-Math.cos( Math.PI * player.getHorAngle() / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 ))){
				player.setCanMoveBack(false);
			}
			//Check left direction for obstacles
			if(maze.isWall(player.getLocationX() + 2*-Math.sin( Math.PI * (player.getHorAngle()+90) / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 )
					,player.getLocationZ() + 2*-Math.cos( Math.PI * (player.getHorAngle()+90) / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 ))){
				player.setCanMoveLeft(false);
			}
			//check right direction for obstacles
			if(maze.isWall(player.getLocationX() - 2*-Math.sin( Math.PI * (player.getHorAngle()+90) / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 )
					,player.getLocationZ() - 2*-Math.cos( Math.PI * (player.getHorAngle()+90) / 180 ) 
					* Math.cos( Math.PI * player.getVerAngle() / 180 ))){
				player.setCanMoveRight(false);
			}
		}
		
		//update locations
		player.update(deltaTime);

	}

	/**
	 * updateCamera() updates the camera position and orientation.
	 * <p>
	 * This is done by copying the locations from the Player, since MazeRunner runs on a first person view.
	 */
	private void updateCamera() {
		camera.setLocationX( player.getLocationX() );
		camera.setLocationY( player.getLocationY() );  
		camera.setLocationZ( player.getLocationZ() );
		camera.setHorAngle( player.getHorAngle() );
		camera.setVerAngle( player.getVerAngle() );
		camera.calculateVRP();
	}
	
	public boolean pauseSwitch(){
		if(!gamepaused && gameinitialized){
			canvas.removeMouseListener(input);
			canvas.removeMouseMotionListener(input);
			canvas.removeKeyListener(input);
			gamepaused = true;
			canvas.removeGLEventListener(this);
		return true;
		}
		else if(gamepaused && gameinitialized){
			System.out.println("Mazerunner resume called");
			canvas.addMouseListener(input);
			canvas.addMouseMotionListener(input);
			canvas.addKeyListener(input);
			gamepaused = false;
			canvas.addGLEventListener(this);
			startup = true;
			return true;
		}
		return false;
	}
}