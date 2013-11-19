package LevelEditor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import MazeRunner.Control;
import MazeRunner.MazeRunner;

import com.sun.opengl.util.Animator;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * A frame for us to draw on using OpenGL.
 * 
 * @author Kang extends JFrame implements ActionListener{
 */
public class JOGLFrame extends Frame implements GLEventListener, MouseListener, KeyListener {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth = 800, screenHeight = 600;
	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;
	
	private boolean mapCreated = false;

	private static final byte doNothing = 0;
	private static final byte mapClick = 1;
	private static final byte itemsClick = 2;
	private static final byte placedItemsClick = 3;
	private static final byte placedItemsPropertiesClick = 4;
	private static final byte setStartClick = 5;
	private static final byte setEndClick = 6;
	private static final byte setHeightClick = 7;
	private static final byte setWidthClick = 8;
	private static final byte saveClick = 9;
	private static final byte loadClick = 10;
	private byte Mode = doNothing;

	//layout van de level editor
	private float[] mapCoords = new float[] { 205, 5, 590, 550 };
	private float[] itemCoords = new float[] { 5, 5, 195, 200 };
	private float[] placedItemsCoords = new float[] { 5, 235, 195, 200 };
	private float[] placedItemsPropertiesCoords = new float[] { 5, 440, 195, 155 };
	private float[] setStartCoords = new float[] { 5, 210, 75, 20 };
	private float[] setEndCoords = new float[] { 125, 210, 75, 20 };
	private float[] setHeightCoords = new float[] { 205, 565, 75, 20 };
	private float[] setWidthCoords = new float[] { 290, 565, 75, 20 };
	private float[] saveCoords = new float[] { 640, 565, 75, 20 };
	private float[] loadCoords = new float[] { 720, 565, 75, 20 };
	
	//define the windows
	private MapMenu map = new MapMenu(mapCoords, screenWidth, screenHeight);
	private Window items = new Window(itemCoords, screenWidth, screenHeight);
	private Window placedItems = new Window(placedItemsCoords, screenWidth, screenHeight);
	private Window placedItemsProperties = new Window(placedItemsPropertiesCoords, screenWidth, screenHeight);
	
	//define the buttons
	private Button setStart = new Button(setStartCoords, screenWidth, screenHeight);
	private Button setEnd = new Button(setEndCoords, screenWidth, screenHeight);
	private Button setHeight = new Button(setHeightCoords, screenWidth, screenHeight);
	private Button setWidth = new Button(setWidthCoords, screenWidth, screenHeight);
	private Button save = new Button(saveCoords, screenWidth, screenHeight);
	private Button load = new Button(loadCoords, screenWidth, screenHeight);

	private MazePlan newMaze = new MazePlan();


	/**
	 * When instantiating, a GLCanvas is added for us to play with. An animator
	 * is created to continuously render the canvas.
	 */
	public JOGLFrame() {
		super("Level Editor MazeRunner");


		// Set the desired size and background color of the frame
		setSize(screenWidth, screenHeight);
		// setBackground(Color.white);
		setBackground(new Color(0.95f, 0.95f, 0.95f));

		// When the "X" close button is called, the application should exit.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// The OpenGL capabilities should be set before initializing the
		// GLCanvas. We use double buffering and hardware acceleration.
		GLCapabilities caps = new GLCapabilities();
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		// Create a GLCanvas with the specified capabilities and add it to this
		// frame. Now, we have a canvas to draw on using JOGL.
		canvas = new GLCanvas(caps);
		add(canvas);

		// Set the canvas' GL event listener to be this class. Doing so gives
		// this class control over what is rendered on the GL canvas.
		canvas.addGLEventListener(this);

		// Also add this class as mouse listener, allowing this class to react
		// to mouse events that happen inside the GLCanvas.
		canvas.addMouseListener(this);
		//key listener
		canvas.addKeyListener(this);
		// An Animator is a JOGL help class that can be used to make sure our
		// GLCanvas is continuously being re-rendered. The animator is run on a
		// separate thread from the main thread.
		Animator anim = new Animator(canvas);
		anim.start();

		// With everything set up, the frame can now be displayed to the user.
		setVisible(true);

	}

	@Override
	/**
	 * A function defined in GLEventListener. It is called once, when the frame containing the GLCanvas 
	 * becomes visible. In this assignment, there is no moving ´camera´, so the view and projection can 
	 * be set at initialization. 
	 */
	public void init(GLAutoDrawable drawable) {
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
	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called many times per second and should 
	 * contain the rendering code.
	 */
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		// Set the clear color and clear the screen.
		gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Draw the buttons.
		drawWindows(gl);

		// Draw a figure based on the current draw mode and user input
		
		// check if map can be drawed
		mapCreated = map.hasHeightAndWidth();

		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();
	}

	/**
	 * A method that draws the layout on the screen.
	 * 
	 * @param gl
	 */
	private void drawWindows(GL gl) {
		// Draw the background boxes

		if(mapCreated){
		gl.glColor3f(0, 0.5f, 0f);
		map.drawBlocks(gl);
		}
		else{
			map.drawLines(gl);
		}
		
		gl.glColor3f(0, 0.5f, 0f);
		items.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		placedItems.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		placedItemsProperties.draw(gl);
		
		
		//draw the clickable boxes
		gl.glColor3f(0, 0.5f, 0f);
		setStart.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		setEnd.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		setHeight.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		setWidth.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		save.draw(gl);
		
		gl.glColor3f(0, 0.5f, 0f);
		load.draw(gl);
		
		

		

	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called when there is a change in certain 
	 * external display settings. 
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// Not needed.
	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called when the GLCanvas is resized or moved. 
	 * Since the canvas fills the frame, this event also triggers whenever the frame is resized or moved.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		gl.glViewport(0, 0, screenWidth, screenHeight);
		
		//update the windows and button sizes
		map.update(screenWidth, screenHeight);
		map.updateBlocks(screenWidth, screenHeight);
		items.update(screenWidth, screenHeight);
		placedItems.update(screenWidth, screenHeight);
		placedItemsProperties.update(screenWidth, screenHeight);
		setStart.update(screenWidth, screenHeight);
		setEnd.update(screenWidth, screenHeight);
		setHeight.update(screenWidth, screenHeight);
		setWidth.update(screenWidth, screenHeight);
		save.update(screenWidth, screenHeight);
		load.update(screenWidth, screenHeight);
		
		//debugging
		

		// Update the projection to an orthogonal projection using the new
		// screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

	}

	@Override
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 */
	public void mouseReleased(MouseEvent me) {
	

		// check of op 1 van de buttons is gedrukt.
		
		if(map.clickedOnIt(me.getX(), me.getY())){
			Mode = mapClick;
			System.out.println("er is in de map geklikt op een item");
			
			//de wall wordt geset.
			map.getClickedBuildingBlock(me.getX(), me.getY()).setWall();
			
			//hier word de posite van de opgevragen buildingBlock getoont.
			BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
			int[] tempPositie = temp.getPosition();
			System.out.println(tempPositie[0]+", "+tempPositie[1]);
			System.out.println("wall = "+temp.getWall() + " floor = "+ temp.getFloor());
			
			
		}else if(items.clickedOnIt(me.getX(), me.getY())){
			Mode = itemsClick;
		}else if(placedItems.clickedOnIt(me.getX(), me.getY())){
			Mode = placedItemsClick;
		}else if(placedItemsProperties.clickedOnIt(me.getX(), me.getY())){
			Mode = placedItemsPropertiesClick;
		}else if(setStart.clickedOnIt(me.getX(), me.getY())){
			Mode = setStartClick;
		}else if(setEnd.clickedOnIt(me.getX(), me.getY())){
			Mode = setEndClick;
		}else if(setHeight.clickedOnIt(me.getX(), me.getY())){
			Mode = setHeightClick;
		}else if(setWidth.clickedOnIt(me.getX(), me.getY())){
			Mode = setWidthClick;
		}else if(save.clickedOnIt(me.getX(), me.getY())){
			Mode = saveClick;
		}else if(load.clickedOnIt(me.getX(), me.getY())){
			Mode = loadClick;
		}else{
			Mode = doNothing;
			int a = newMaze.getWidth();
			int b = newMaze.getHeight();
			System.out.println("Breedte: "+a+" Hoogte: "+b);
		}


	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Not needed.
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Not needed.

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Not needed.

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		// Not needed.

	}
	
	@Override
	public void keyPressed(KeyEvent event){  

		// Not needed.
		
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		
		int keycode = event.getKeyCode();
		char key = event.getKeyChar();
		switch (Mode) {
			case doNothing:
				break;
			case mapClick:
				break;
			case itemsClick:	
				break;
			case placedItemsClick:
				break;
			case placedItemsPropertiesClick:
				break;
			case setStartClick:
				break;
			case setEndClick:
				break;
				
			case setHeightClick:
				if(keycode > 95 && keycode < 106){
					newMaze.setHeight(key);	
				}else if(keycode == 10){ //er is op 'enter' gedrukt
					Mode = doNothing;
					int a = newMaze.getWidth();
					int b = newMaze.getHeight();
					System.out.println("Breedte: "+a+" Hoogte: "+b);
					map.setTotalBuildingBlocks(newMaze.getWidth(),newMaze.getHeight());
				}else if(keycode == 8){ //er is op 'backspace' gedrukt
					newMaze.removeHeight();
					//nu moet ook de hele maze gereset worden!!!!!! dit moet nog gemaakt worden
				}
				break;
				
			case setWidthClick:
				if(keycode > 95 && keycode < 106){
					newMaze.setWidth(key);	
				}else if(keycode == 10){ //er is op 'enter' gedrukt
					Mode = doNothing;
					int a = newMaze.getWidth();
					int b = newMaze.getHeight();
					System.out.println("Breedte: "+a+" Hoogte: "+b);
					map.setTotalBuildingBlocks(newMaze.getWidth(),newMaze.getHeight());
				}else if(keycode == 8){ //er is op 'backspace' gedrukt
					newMaze.removeWidth();
					//nu moet ook de hele maze gereset worden!!!!!! dit moet nog gemaakt worden
				}
				break;
					
			case saveClick:
				break;
			case loadClick:
				break;
		
		}

	}

	@Override
	public void keyTyped(KeyEvent event) {
		// Not needed.
		
	}
}

