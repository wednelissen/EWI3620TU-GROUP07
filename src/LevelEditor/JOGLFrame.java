package LevelEditor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;  
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
 *TO DO:
 *
 *PROBLEMEN:
 *omdat key nog niet is geimplementeerd is er een probleem. je kan hem wel toevoegen maar nog niet verwijderen.
 *
 *
 *
 *KEYS:
 *key moet nog helemaal worden geimplementeerd, dit lijkt erg sterk op spots maar dan met een verwijzing naar een deur.
 *
 *DOORS:
 *er moeten deuren kunnen worden geplaatst in het level waar 1 of meerdere sleutels aan is gekoppeld.
 *
 *een Deur maak je in buildingBlock wanneer er later een sleutel is aangemaakt kan deze aan een deur worden gekoppeld door
 *te kijken of de building block een door is. de key bevat dan de coordinaten van een door. wanneer er een wall of floor wordt 
 *geset wordt gecontrolleerd of er een key nodig was voor deze deur. zo ja dan wordt in de arraylist van keys, deze bevind zich in
 *PlacedItemsMenu gezocht naar de bij behorende deur en wordt de sleutel verwijderd.
 *bij het saven zal een deur worden gesaved als een 2. met behulp van de arraylist keys kan je vinden voor welke deur een sleutel
 *nodig was.
 *
 *je kunt altijd nog implementeren dan je niet kunt saven voordat iedere deur een keyrequired = true heeft.
 *
 *
 *LEVEL:
 *een popup window voor de hoogte
 *een popup window voor sava om een naam in te geven en op te slaan
 *een lijst met levels die je kunt spelen.
 *het inladen van een saved level om verder te bewerken in de editor.
 *je kunt niet saven voordat iedere deur een sleutel heeft en andersom.
 * 
 **/

public class JOGLFrame extends Frame implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth = 800, screenHeight = 600;
	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;
	
	private boolean mapCreated = false;
	private boolean AllGuardsOnOff = false;

	ClickOptions Mode = ClickOptions.doNothing; 

	//layout van de level editor
	private float[] mapCoords = new float[] { 205, 5, 590, 550 };
	
	private float[] itemCoords = new float[] { 5, 5, 195, 200 };
	private float[] itemFloorCoords = new float[] { 5, 5, 48.75f, 100 };
	private float[] itemWallCoords = new float[] { 5+48.75f , 5, 48.75f, 100 };
	private float[] itemDoorCoords = new float[] { 5+2*48.75f , 5, 48.75f, 100 };
	private float[] itemSpotCoords = new float[] { 5+3*48.75f , 5, 48.75f, 100 };
	private float[] itemGuardianCoords = new float[] { 5, 105, 48.75f, 100 };
	private float[] itemKeyCoords = new float[] { 5+48.75f, 105, 48.75f, 100 };
	private float[] itemCameraCoords = new float[] { 5+2*48.75f, 105, 48.75f, 100 };
	
	private float[] placedItemsCoords = new float[] { 5, 235, 195, 200 };
	
	private float[] placedItemsPropertiesCoords = new float[] { 5, 440, 195, 155 };
	private float[] addGuardKeySpotCameraCoords = new float[] { 5, 440, 97.5f, 77.5f };
	private float[] removeGuardKeySpotCameraCoords = new float[] { 102.5f, 440, 97.5f, 77.5f };
	private float[] removeLastPointGuardCoords = new float[] { 5, 517.5f, 97.5f, 77.5f };
	private float[] showAllGuardsCoords = new float[] { 102.5f, 517.5f, 97.5f, 77.5f };

	
	private float[] setStartCoords = new float[] { 5, 210, 75, 20 };
	private float[] setEndCoords = new float[] { 125, 210, 75, 20 };
	private float[] setHeightCoords = new float[] { 205, 565, 75, 20 };
	private float[] setWidthCoords = new float[] { 290, 565, 75, 20 };
	private float[] saveCoords = new float[] { 640, 565, 75, 20 };
	private float[] loadCoords = new float[] { 720, 565, 75, 20 };
	
	//define the windows
	private MapMenu map = new MapMenu(mapCoords, screenWidth, screenHeight);
	private Window items = new Window(itemCoords, screenWidth, screenHeight);
	private PlacedItemsMenu placedItems = new PlacedItemsMenu(placedItemsCoords, screenWidth, screenHeight);
	private Window placedItemsProperties = new Window(placedItemsPropertiesCoords, screenWidth, screenHeight);
	
	//define the buttons
	private Button setStart = new Button(setStartCoords, screenWidth, screenHeight);
	private Button setEnd = new Button(setEndCoords, screenWidth, screenHeight);
	private Button setHeight = new Button(setHeightCoords, screenWidth, screenHeight);
	private Button setWidth = new Button(setWidthCoords, screenWidth, screenHeight);
	private Button save = new Button(saveCoords, screenWidth, screenHeight);
	private Button load = new Button(loadCoords, screenWidth, screenHeight);
	
	private Button itemFloor = new Button(itemFloorCoords, screenWidth, screenHeight);
	private Button itemWall = new Button(itemWallCoords, screenWidth, screenHeight);
	private Button itemDoor = new Button(itemDoorCoords, screenWidth, screenHeight);
	private Button itemSpot = new Button(itemSpotCoords, screenWidth, screenHeight);
	private Button itemGuardian = new Button(itemGuardianCoords, screenWidth, screenHeight);
	private Button itemKey = new Button(itemKeyCoords, screenWidth, screenHeight);
	private Button itemCamera = new Button(itemCameraCoords, screenWidth, screenHeight);
	
	private Button addGuardKeySpotCamera = new Button(addGuardKeySpotCameraCoords, screenWidth, screenHeight);
	private Button removeGuardKeySpotCamera = new Button(removeGuardKeySpotCameraCoords, screenWidth, screenHeight);
	private Button removeLastPointGuard = new Button(removeLastPointGuardCoords, screenWidth, screenHeight);
	private Button showAllGuards = new Button(showAllGuardsCoords, screenWidth, screenHeight);
	
	
	private SaveInput StoreMaze;
	//private GuardianList allGuards = new GuardianList(placedItemsCoords, screenWidth, screenHeight);
	private Guardian guard = new Guardian(itemCoords, screenWidth, screenHeight);
	private Key key = new Key(itemCoords, screenWidth, screenHeight);
	private Spot spot = new Spot();
	private SpotList spotList = new SpotList();
	
	private Camera camera = new Camera();
	private CameraList cameraList = new CameraList();
	
	private StartAndEndPosition StartEnd = new StartAndEndPosition();
		

	
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
		canvas.addMouseMotionListener(this);
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
		
		// check if map can be drawn
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
		gl.glColor3f(0f, 0f, 0f);
		//als de breedte en lengte zijn ingegeven mogen de buildingBlocks worden getekent in de map.
		if(mapCreated){
			map.drawBlocks(gl);
			
			//alle guards zullen worden getekent met blauwe blokjes. 
			//indien je 1 specifieke guard hebt geselecteerd word deze met rode blokjes getekent
			if(AllGuardsOnOff){
				gl.glColor3f(0f, 0f, 0.5f);
				for(Guardian g: placedItems.getAllGuards()){
					for(int i = 0; i <g.routeSize(); i++){
						Point a = g.getRoute(i);
						map.getBuildingBlockByPosition(a).drawGuardianPath(gl);			
					}
				}
			}
			
			//er worden rode kruisjes getekend in de blokjes waar de guard loopt. 
			//dit geld alleen voor deze ene geselecteerde guard.
			if(guard.routeSize()>0 && Mode == ClickOptions.guardian){
				gl.glColor3f(0.5f, 0, 0f);
				for(int i = 0; i <guard.routeSize(); i++){
					Point a = guard.getRoute(i);
					map.getBuildingBlockByPosition(a).drawGuardianPath(gl);			
				}
				
			}
			
			if(cameraList.getCameras().size()>0){
				gl.glColor3f(0.2f, 1f, 0.6f);
				for(Camera s: cameraList.getCameras()){
					Point a = s.getPosition();
					//map.getBuildingBlockByPosition(a).drawSpot(gl);
					map.getBuildingBlockByPosition(a).drawCameras(gl);
				}
			}
			
			if(spotList.getSpots().size()>0){
				gl.glColor3f(1f, 1f, 0f);
				for(Spot s: spotList.getSpots()){
					Point a = s.getPosition();
					//map.getBuildingBlockByPosition(a).drawSpot(gl);
					map.getBuildingBlockByPosition(a).drawSpot(gl);
				}
			}
			
			if(StartEnd.hasStart()){
				gl.glColor3f(0.5f, 0, 0.6f);
				Point a = StartEnd.getStart();
				map.getBuildingBlockByPosition(a).drawBlock(gl);
			}
			
			if(StartEnd.hasEnd()){
				gl.glColor3f(0.5f, 0.5f, 0f);
				Point a = StartEnd.getEnd();
				map.getBuildingBlockByPosition(a).drawBlock(gl);
			}

			
			gl.glColor3f(0f, 0f, 0f);
		}
		else{
			map.draw(gl);
		}

		//het item Menu word getekent met de items waarop geklikt kan worden
		items.draw(gl);
		itemFloor.draw(gl);
		itemWall.draw(gl);
		itemDoor.draw(gl);
		itemSpot.draw(gl);
		itemGuardian.draw(gl);
		itemKey.draw(gl);
		itemCamera.draw(gl);
		

		placedItems.draw(gl);
		placedItems.drawItems(gl);
		
		if(Mode == ClickOptions.guardian){
			placedItemsProperties.draw(gl);
			addGuardKeySpotCamera.draw(gl); 
			removeGuardKeySpotCamera.draw(gl);  
			removeLastPointGuard.draw(gl); 
			showAllGuards.draw(gl); 
		}
		
		if(Mode == ClickOptions.key){
			placedItemsProperties.draw(gl);
			addGuardKeySpotCamera.draw(gl); 
			removeGuardKeySpotCamera.draw(gl);  
		}
		
		if(Mode == ClickOptions.setSpot || Mode == ClickOptions.removeSpot){
			placedItemsProperties.draw(gl);
			addGuardKeySpotCamera.draw(gl); 
			removeGuardKeySpotCamera.draw(gl);  
		}
		
		if(Mode == ClickOptions.setCamera || Mode == ClickOptions.removeCamera){
			placedItemsProperties.draw(gl);
			addGuardKeySpotCamera.draw(gl); 
			removeGuardKeySpotCamera.draw(gl);  
		}
		
		
		//draw the clickable boxes
		setStart.draw(gl);
		setEnd.draw(gl);
		setHeight.draw(gl);
		setWidth.draw(gl);
		save.draw(gl);
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
		placedItems.updateItems(screenWidth, screenHeight);
		placedItemsProperties.update(screenWidth, screenHeight);
		setStart.update(screenWidth, screenHeight);
		setEnd.update(screenWidth, screenHeight);
		setHeight.update(screenWidth, screenHeight);
		setWidth.update(screenWidth, screenHeight);
		save.update(screenWidth, screenHeight);
		load.update(screenWidth, screenHeight);
		itemFloor.update(screenWidth, screenHeight);
		itemWall.update(screenWidth, screenHeight);
		itemDoor.update(screenWidth, screenHeight);
		itemSpot.update(screenWidth, screenHeight);
		itemGuardian.update(screenWidth, screenHeight);
		itemKey.update(screenWidth, screenHeight);
		itemCamera.update(screenWidth, screenHeight);		
		addGuardKeySpotCamera.update(screenWidth, screenHeight);
		removeGuardKeySpotCamera.update(screenWidth, screenHeight); 
		removeLastPointGuard.update(screenWidth, screenHeight);
		showAllGuards.update(screenWidth, screenHeight);
		
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
			mapClickHandler(me);			
			
		}else if(itemFloor.clickedOnIt(me.getX(), me.getY())){
			System.out.println("floor");
			Mode = ClickOptions.floor;
		}else if(itemWall.clickedOnIt(me.getX(), me.getY())){
			System.out.println("wall");
			Mode = ClickOptions.wall;
		}else if(itemDoor.clickedOnIt(me.getX(), me.getY())){
			System.out.println("door");
			Mode = ClickOptions.door;
		}else if(itemSpot.clickedOnIt(me.getX(), me.getY())){
			Mode = ClickOptions.setSpot;
			spot = new Spot();
			System.out.println("spot");
		}else if(itemGuardian.clickedOnIt(me.getX(), me.getY())){
			System.out.println("guardian");
			Mode = ClickOptions.guardian;
			guard = new Guardian(itemCoords, screenWidth, screenHeight);
			
		}else if(itemKey.clickedOnIt(me.getX(), me.getY())){
			System.out.println("key");
			Mode = ClickOptions.key;
			key = new Key(itemCoords, screenWidth, screenHeight);
		}else if(itemCamera.clickedOnIt(me.getX(), me.getY())){
			System.out.println("camera");
			camera = new Camera();
			Mode = ClickOptions.setCamera;
			
		}else if(placedItems.clickedOnIt(me.getX(), me.getY())){
			Mode = ClickOptions.placedItems;
			if(placedItems.typeIsGuardian(me.getX(), me.getY())){
				guard = placedItems.getClickedGuardian(me.getX(), me.getY());
				Mode = ClickOptions.guardian;
			}
			if(placedItems.typeIsKey(me.getX(), me.getY())){
				key = placedItems.getClickedKey(me.getX(), me.getY());
				Mode = ClickOptions.key;
			}
			
		}else if(placedItemsProperties.clickedOnIt(me.getX(), me.getY())){
			PlacedItemsPropertiesClickHandler(me);
			
		}else if(setStart.clickedOnIt(me.getX(), me.getY())){
			Mode = ClickOptions.setStart;
		}else if(setEnd.clickedOnIt(me.getX(), me.getY())){
			Mode = ClickOptions.setEnd;
		}else if(setHeight.clickedOnIt(me.getX(), me.getY())){
			Mode = ClickOptions.setHeight;
		}else if(setWidth.clickedOnIt(me.getX(), me.getY())){
			Mode = ClickOptions.setWidth;
		}else if(save.clickedOnIt(me.getX(), me.getY())){
			//Mode = saveClick;
			StoreMaze = new SaveInput(map, placedItems, StartEnd, spotList, cameraList); //de gegenereerde map wordt opgeslagen
			StoreMaze.floorPlanMaze();	//de map wordt vertaald naar enen en nullen
			StoreMaze.GuardsPlan();		//de bewakers worden weggeschreven
			StoreMaze.StartAndEndPosition();	//begin en eindpositie worden naar string vertaald.
			StoreMaze.SpotsPlan();			//spotjes worden naar string vertaald
			StoreMaze.CamerasPlan();			//camera's worden naar string vertaald
			if(StartEnd.hasStart() && StartEnd.hasEnd()){
				StoreMaze.write("level1"); // de map wordt weggeschereven naar een bestand.
			}
			else
				System.out.println("er zijn nog geen begin en eindpunt geset.");
		}else if(load.clickedOnIt(me.getX(), me.getY())){
			//het level moet ingeladen worden en nog gedisplayd
			Mode = ClickOptions.load;
			LoadLevel newlevel = new LoadLevel("level1");
		}else{
			Mode = ClickOptions.doNothing;
			int a = map.getWidth();
			int b = map.getHeight();
			System.out.println("Breedte: "+a+" Hoogte: "+b);
		}


	}

	private void mapClickHandler(MouseEvent me) {
		if(map.BuildingBlocksExists()){
			if(Mode == ClickOptions.wall){
				//de wall wordt geset, wanneer er geen bewaker/begin punt/eind punt op deze plek staat.
				boolean setWall = posibleToPlaceWallOrDoor(me);
				if(setWall){
					map.getClickedBuildingBlock(me.getX(), me.getY()).setWall();
				}
				else
					System.out.println("er staat een bewaker/begin punt/eind punt op deze plek");
			}
			if(Mode == ClickOptions.floor){
				//de wall wordt geset.
				map.getClickedBuildingBlock(me.getX(), me.getY()).setFloor();
			}
			if(Mode == ClickOptions.door){
				//de door wordt geset, wanneer er geen bewaker/begin punt/eind punt op deze plek staat.
				boolean setDoor = posibleToPlaceWallOrDoor(me);
				if(setDoor){
					map.getClickedBuildingBlock(me.getX(), me.getY()).setDoor();
				}
				else
					System.out.println("er staat een bewaker/begin punt/eind punt op deze plek");
			}
			
			// er kunnen maximaal 8 spotjes geplaatst worden
			if(Mode == ClickOptions.setSpot){
				System.out.println("addSpot");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(!temp.getWall() && !temp.getDoor()){
					Point tempPositie = temp.getPosition();
					spot.setSpot(tempPositie);
					spotList.addSpot(spot);
					spot = new Spot();
				}
			}
			if(Mode == ClickOptions.removeSpot){
				System.out.println("removeSpot");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				Point tempPositie = temp.getPosition();
				spot.setSpot(tempPositie);
				spotList.removeSpot(spot);
				spot = new Spot();
			}
			
			//Cameras
			if(Mode == ClickOptions.setCamera){
				System.out.println("addCamera");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(!temp.getWall() && !temp.getDoor()){
					Point tempPositie = temp.getPosition();
					camera.setCamera(tempPositie);
					cameraList.addCamera(camera);
					camera = new Camera();
				}
			}
			if(Mode == ClickOptions.removeCamera){
				System.out.println("removeCamera");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				Point tempPositie = temp.getPosition();
				camera.setCamera(tempPositie);
				cameraList.removeCamera(camera);
				camera = new Camera();
			}	
			
			if(Mode == ClickOptions.guardian){
				/**
				kijkt of het geklikte vlakje een floor bevat en voegt dit punt toe aan de 
				route lijst van de bewaker. Dit punt moet wel horizontaal of verticaal grenzen
				aan het vorig aangeklikte punt waar de bewaker loopt. de bewaker mag niet schuin
				 lopen
				**/
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					guard.addRoute(tempPositie);
				}
				else
					System.out.println("hier staat een muur er kan geen bewaker worden geplaatst");
			}
			
			if(Mode == ClickOptions.setStart){
				//mag alleen geset worden op een vloer
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					StartEnd.setStart(tempPositie);					
				}
				else
					System.out.println("de start positie kan alleen op een open vlak");
			}
			
			if(Mode == ClickOptions.setEnd){
				//mag alleen geset worden op een vloer
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					StartEnd.setEnd(tempPositie);					
				}
				else
					System.out.println("de eind positie kan alleen op een open vlak");
				
			}
			
			
			//DEBUG
			//hier word de posite van de opgevragen buildingBlock getoont.
//			BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
//			Point tempPositie = temp.getPosition();
//			System.out.println(tempPositie.getX()+", "+tempPositie.getY());
//			System.out.println(tempPositie);
//			System.out.println("wall = "+temp.getWall() + " floor = "+ temp.getFloor());
		}
		else System.out.println("Vul een lengte en breedte in.");
	}

	private boolean posibleToPlaceWallOrDoor(MouseEvent me) {
		boolean possible = true;
		Point a = map.getClickedBuildingBlock(me.getX(), me.getY()).getPosition();
		for(Guardian g: placedItems.getAllGuards()){
			for(Point p: g.getCopyRoutes()){
				if(p.equals(a)){
					possible = false;
					break;
				}
			}
		}
		
		//mag niet wanneer er een spot staat.
		for(Spot s: spotList.getSpots()){
			if(s.getPosition().equals(a)){
				possible = false;
				break;
			}
		}
		
		//mag niet wanneer er een camera staat.
		for(Camera s: cameraList.getCameras()){
			if(s.getPosition().equals(a)){
				possible = false;
				break;
			}
		}
		
		//mag ook niet wanneer er een sleutel staat.
		
		
		//de wall wordt geset, wanneer er geen start of begin punt is
		if((StartEnd.getStart().equals(a) && StartEnd.hasStart()) || (StartEnd.getEnd().equals(a) && StartEnd.hasEnd())){
			possible = false;
		}
		return possible;
	}

	private void PlacedItemsPropertiesClickHandler(MouseEvent me) {
		//Mode = ClickOptions.placedItemsProperties;
		//Guard --> add guard, remove guard, remove last point, show all guards
		//Key --> add key, remove key, (change door)
		
		if(Mode == ClickOptions.guardian){
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){		
				//de Guardian wordt opgeslagen in de Guardian List.
				Guardian copyGuard = new Guardian(itemCoords, screenWidth, screenHeight);	
				copyGuard.setTotalRoute(guard.getCopyRoutes(),guard.geXprevious(), guard.geYprevious());
				placedItems.addGuard(copyGuard);
				//er wordt een lege Guardian aangemaakt.
				guard = new Guardian(itemCoords, screenWidth, screenHeight);
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				placedItems.removeGuard(guard);
				//er wordt een lege Guardian aangemaakt.
				guard = new Guardian(itemCoords, screenWidth, screenHeight);
			}
			if(removeLastPointGuard.clickedOnIt(me.getX(), me.getY())){
				guard.removeLastPoint();
			}
			if(showAllGuards.clickedOnIt(me.getX(), me.getY())){
				if(!AllGuardsOnOff){
					AllGuardsOnOff = true;
				}else{
					AllGuardsOnOff = false;
					}
			}
			 
		} 
		else if(Mode == ClickOptions.key){
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				placedItems.addKey(key);
				key = new Key(itemCoords, screenWidth, screenHeight);
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				System.out.println("Key wordt verwijderd");
			}			 
		}
		
		//zorgt dat de mode wordt geset om een spot te deleten of toe te voegen wanneer op een plek op de map
		// wordt gedrukt.
		else if(Mode == ClickOptions.setSpot || Mode == ClickOptions.removeSpot){
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.setSpot;
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.removeSpot;
			}	
		}
		
		else if(Mode == ClickOptions.setCamera || Mode == ClickOptions.removeCamera){
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.setCamera;
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.removeCamera;
			}	
		}
	}	
	
	@Override
	public void mouseClicked(MouseEvent me) {
		// Not needed.
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// Not needed.

	}

	@Override
	public void mouseExited(MouseEvent me) {
		// Not needed.
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// Not needed.
		

	}
	public void mouseDragged(MouseEvent me)
	{		
		mouseReleased(me);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
		
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
		//System.out.println(key+" = "+keycode);
		switch (Mode) {
			case doNothing:
				break;
			case map:
				break;
			case items:	
				break;
			case placedItems:
				break;
			case placedItemsProperties:
				break;
			case setStart:
				break;
			case setEnd:
				break;
				
			case setHeight:
				if((keycode > 95 && keycode < 106) || (keycode > 47 && keycode < 58)){
					map.setHeight(key);	
				}else if(keycode == 10){ //er is op 'enter' gedrukt
					Mode = ClickOptions.doNothing;
					int a = map.getWidth();
					int b = map.getHeight();
					System.out.println("Breedte: "+a+" Hoogte: "+b);
					map.setTotalBuildingBlocks();
				}else if(keycode == 8){ //er is op 'backspace' gedrukt
					map.removeHeight();
					//nu moet ook de hele maze gereset worden!!!!!! dit moet nog gemaakt worden
				}
				break;
				
			case setWidth:
				if((keycode > 95 && keycode < 106) || (keycode > 47 && keycode < 58)){
					map.setWidth(key);	
				}else if(keycode == 10){ //er is op 'enter' gedrukt
					Mode = ClickOptions.doNothing;
					int a = map.getWidth();
					int b = map.getHeight();
					System.out.println("Breedte: "+a+" Hoogte: "+b);
					map.setTotalBuildingBlocks();
				}else if(keycode == 8){ //er is op 'backspace' gedrukt
					map.removeWidth();
					//nu moet ook de hele maze gereset worden!!!!!! dit moet nog gemaakt worden
				}
				break;
					
			case save:
				break;
			case load:
				break;
		
		}

	}

	@Override
	public void keyTyped(KeyEvent event) {
		// Not needed.
		
	}


}

