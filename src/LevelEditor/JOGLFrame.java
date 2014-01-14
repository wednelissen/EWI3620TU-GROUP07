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
import MazeRunner.GameDriver;
import com.sun.opengl.util.Animator;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * HEEL ALGEMEEN VERHAAL OVER DE OPZET VAN DE LEVEL EDITOR:
 * 
 * om te beginnen is verder gewerkt aan de in assignment 1 gekregen opdracht.
 * hier wordt een JOGL frame geopent waarin kleine rechthoeken worden getekent waarop kan worden geklikt.
 * deze kleine rechthoeken zijn allemaal al van te voren gedefinieerd in het formaat van 800 bij 600. het scherm zal
 * automatisch in deze verhouding worden geschaald. omdat alle rechthoeken vast liggen is er een lange lijst met vooraf gedefinieerde
 * coordinaten. Deze worden aangegeven door middel van 'Coords' aan het einde van de naam. de classe Window en button zorgen voor
 * het maken van de klikbare rechthoeken. De classe 'mapMenu' is de classe die zorgt dat er kleine vierkantjes(BuildingBlocks) in
 * het middden van het scherm worden getekent waar een eigenschap aan kan worden toegekent zoals een muur of een deur.
 * de Classe 'PlacedItemsMenu' is het linker middelste grote rechthoek waarin geplaatste items verschijnen zoals een bewaker of
 * een sleutel. wanneer op een sleutel of bewaker wordt geklikt zal deze in de map zichtbaar worden. de mogelijkheid bestaat om
 * alle bewakers en/of sleutels zichbaar te maken door in de rechthoek links onder aan te klikken op show all.
 * het onderste rechthoek verschijnt alleen wanneer een item extra opties heeft zoals bij sleutel het geval is indien hier een deur
 * aan moet worden gekopppelt. een deur die geplaatst is zal rood van kleur zijn. wanneer hier een sleutel aan is gekoppeld zal de
 * deur groen kleuren.
 * 
 * @author mvanderreek
 *
 */
public class JOGLFrame extends Frame implements GLEventListener, MouseListener, MouseMotionListener {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth = 800, screenHeight = 600;
	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;
	
	//Classes met een Jframe die wel aangemaakt worden maar nog niet zichtbaar zijn.
	private MazeSizeFrame sizes = new MazeSizeFrame();
	private OpenLevelFrame loadFile = new OpenLevelFrame();
	private SaveLevelFrame saveFile = new SaveLevelFrame();
	
	private boolean mapCreated = false;
	private boolean AllGuardsOnOff = false;
	private boolean AllKeysOnOff = false;
	private boolean texLoaded = false;

	ClickOptions Mode = ClickOptions.doNothing; 

	//layout coordinaten van de level editor
	private float[] mapCoords = new float[] { 205, 5, 590, 550 };
	
	private float[] itemCoords = new float[] { 5, 5, 195, 200 };
	private float[] itemFloorCoords = new float[] { 5, 5, 48.75f, 100 };
	private float[] itemWallCoords = new float[] { 5+48.75f , 5, 48.75f, 100 };
	private float[] itemDoorCoords = new float[] { 5+2*48.75f , 5, 48.75f, 100 };
	private float[] itemSpotCoords = new float[] { 5+3*48.75f , 5, 48.75f, 100 };
	private float[] itemGuardianCoords = new float[] { 5, 105, 48.75f, 100 };
	private float[] itemKeyCoords = new float[] { 5+48.75f, 105, 48.75f, 100 };
	private float[] itemCameraCoords = new float[] { 5+2*48.75f, 105, 48.75f, 100 };
	private float[] itemControlCenterCoords = new float[] { 5+3*48.75f, 105, 48.75f, 100 };
	
	private float[] placedItemsCoords = new float[] { 5, 235, 195, 200 };
	
	private float[] placedItemsPropertiesCoords = new float[] { 5, 440, 195, 155 };
	private float[] addGuardKeySpotCameraCoords = new float[] { 5, 440, 97.5f, 77.5f };
	private float[] removeGuardKeySpotCameraCoords = new float[] { 102.5f, 440, 97.5f, 77.5f };
	private float[] removeLastPointGuardOrSetDoorKeyCoords = new float[] { 5, 517.5f, 97.5f, 77.5f };
	private float[] showAllGuardsKeysCoords = new float[] { 102.5f, 517.5f, 97.5f, 77.5f };

	
	private float[] setStartCoords = new float[] { 5, 210, 75, 20 };
	private float[] setEndCoords = new float[] { 125, 210, 75, 20 };
	private float[] setSizesCoords = new float[] { 205, 565, 75, 20 };
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
	private Button setSizes = new Button(setSizesCoords, screenWidth, screenHeight);
	private Button save = new Button(saveCoords, screenWidth, screenHeight);
	private Button load = new Button(loadCoords, screenWidth, screenHeight);
	
	private Button itemFloor = new Button(itemFloorCoords, screenWidth, screenHeight);
	private Button itemWall = new Button(itemWallCoords, screenWidth, screenHeight);
	private Button itemDoor = new Button(itemDoorCoords, screenWidth, screenHeight);
	private Button itemSpot = new Button(itemSpotCoords, screenWidth, screenHeight);
	private Button itemGuardian = new Button(itemGuardianCoords, screenWidth, screenHeight);
	private Button itemKey = new Button(itemKeyCoords, screenWidth, screenHeight);
	private Button itemCamera = new Button(itemCameraCoords, screenWidth, screenHeight);
	private Button itemControlCenter = new Button(itemControlCenterCoords, screenWidth, screenHeight);
	
	
	private Button addGuardKeySpotCamera = new Button(addGuardKeySpotCameraCoords, screenWidth, screenHeight);
	private Button removeGuardKeySpotCamera = new Button(removeGuardKeySpotCameraCoords, screenWidth, screenHeight);
	private Button removeLastPointGuardOrSetDoorKey = new Button(removeLastPointGuardOrSetDoorKeyCoords, screenWidth, screenHeight);
	private Button showAllGuardsKeys = new Button(showAllGuardsKeysCoords, screenWidth, screenHeight);
	
	//tijdelijke objecten die later in een arraylist worden geplaatst.
	private SaveInput StoreMaze;
	private Guardian guard = new Guardian(itemCoords, screenWidth, screenHeight);
	private Key key = new Key(itemCoords, screenWidth, screenHeight);
	private Spot spot = new Spot();
	private Camera camera = new Camera();
	private ControlCenterEditor controlCenter = new ControlCenterEditor();
	
	//Arraylists, de Key en Guard worden in placedItems opgeslagen, op deze wijze kun je ze nog selecteren en veranderen.
	private SpotList spotList = new SpotList();
	private CameraList cameraList = new CameraList();
	private ControlCenterList controlCenterList = new ControlCenterList();
	
	private StartAndEndPosition StartEnd = new StartAndEndPosition();
	private LoadTexturesEditor loadedTexturesEditor;

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
				dispose();
				GameDriver.main(new String[] {});
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
	 * Een functie gedefineerd in GLEventListener. deze functie wordt meerdere malen per seconden aangeroepen en zorgt dat de 
	 * knoppen worden getekend. ook wordt er iedere keer gecheckt of de inhoud van de map ookwel de BuildingBlocks kunnen worden
	 * getekend, dit mag alleen wanneer er een lengte en breedte voor het aantal blokken is ingegeven.
	 */
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		// Set the clear color and clear the screen.
		gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		if (texLoaded == false)
		{
			System.out.println("texLoaded = false");
			// Init Textures
			System.out.println("Textures loading");
			loadedTexturesEditor = new LoadTexturesEditor();
			texLoaded = true;
		}
		
		// Draw the buttons.
		drawWindows(gl);
		
		// check if map can be drawn
		mapDrawCheck();
		

		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();
	}

	/**
	 * in deze methode worden alle teken functies aangeroepen om de layout te tekenen.
	 * afhankelijk van de ingedrukte button zal er meer of minder schermen zichtbaar zijn in de layout.
	 * zo verschijnt er links onderaan een extra menu met opties wanneer er op Guard is gedrukt.
	 *  
	 * @param gl
	 */
	private void drawWindows(GL gl) {
		//als de breedte en lengte zijn ingegeven mogen de buildingBlocks worden getekent in de map.
		//alle items in de map worden ook getekent indien aan de if is voldaan.
		if(mapCreated){
			map.drawBlocks(gl);
			
			//startpunt wordt getekent
			if(StartEnd.hasStart()){
				Point a = StartEnd.getStart();
				map.getBuildingBlockByPosition(a).drawBlock(gl, LoadTexturesEditor.getTexture("editorStartPos"));
			}
			
			//eindpunt wordt getekent
			if(StartEnd.hasEnd()){
				Point a = StartEnd.getEnd();
				map.getBuildingBlockByPosition(a).drawBlock(gl, LoadTexturesEditor.getTexture("editorEndPos"));
			}
			
			//alle guards zullen worden getekent met blauwe blokjes. 
			//indien je 1 specifieke guard hebt geselecteerd word deze met rode blokjes getekent
			if(AllGuardsOnOff){
//				gl.glColor3f(0f, 0f, 0.5f);
				for(Guardian g: placedItems.getAllGuards()){
					for(int i = 0; i <g.routeSize(); i++){
						Point a = g.getRoute(i);
						map.getBuildingBlockByPosition(a).drawGuardianPath(gl, LoadTexturesEditor.getTexture("editorGuardianStepsBlue"));			
					}
				}
			}
			
			//alle sleutels die zijn geplaatst worden getekent.
			if(AllKeysOnOff){
//				gl.glColor3f(0.1f, 0.3f, 0.5f);
				for(Key k: placedItems.getAllKeys()){
					Point a = k.getKey();
					map.getBuildingBlockByPosition(a).drawKey(gl);			
				}
			}
		
			
			//er worden rode kruisjes getekend in de blokjes waar de guard loopt. 
			//dit geld alleen voor deze ene geselecteerde guard.
			if(guard.routeSize()>0 && Mode == ClickOptions.guardian){
//				gl.glColor3f(0.5f, 0, 0f);
				for(int i = 0; i <guard.routeSize(); i++){
					Point a = guard.getRoute(i);
					map.getBuildingBlockByPosition(a).drawGuardianPath(gl, LoadTexturesEditor.getTexture("editorGuardianStepsRed"));			
				}
				
			}
			
			//tijdelijke sleutel die is geselecteerd wordt getekent
			if(key.hasPosition() && (Mode == ClickOptions.key || Mode == ClickOptions.setKeyDoor) ){
//				gl.glColor3f(0.1f, 0.3f, 0.5f);
				Point a = key.getKey();
				map.getBuildingBlockByPosition(a).drawKey(gl);	
			}
			
			//alle geplaatste camara's worden getekent
			if(cameraList.getCameras().size()>0){
//				gl.glColor3f(0.2f, 1f, 0.6f);
				for(Camera s: cameraList.getCameras()){
					Point a = s.getPosition();
					map.getBuildingBlockByPosition(a).drawCameras(gl, LoadTexturesEditor.getTexture("editorCamera"));
				}
			}
			
			//alle geplaatste control centers worden getekent
			if(controlCenterList.getControlCenters().size()>0){
//				gl.glColor3f(0.2f, 1f, 0.6f);
				for(ControlCenterEditor s: controlCenterList.getControlCenters()){
					Point a = s.getPosition();
					map.getBuildingBlockByPosition(a).drawControlCenter(gl, LoadTexturesEditor.getTexture("controlCenterEditor"));
				}
			}
			
			//alle geplaatste spots, maximaal 8, worden getekent
			if(spotList.getSpots().size()>0){
//				gl.glColor3f(1f, 1f, 0f);
				for(Spot s: spotList.getSpots()){
					Point a = s.getPosition();
					map.getBuildingBlockByPosition(a).drawSpot(gl, LoadTexturesEditor.getTexture("editorSpot"));
				}
			}
			
//			gl.glColor3f(0f, 0f, 0f);
		}
		else{
			map.draw(gl, null);
		}

		//het item Menu word getekent met de items waarop geklikt kan worden
		items.draw(gl, null);

		itemFloor.draw(gl, LoadTexturesEditor.getTexture("editorFloor"));
		itemWall.draw(gl, LoadTexturesEditor.getTexture("editorWall"));
		itemDoor.draw(gl, LoadTexturesEditor.getTexture("editorDoor"));
		itemSpot.draw(gl, LoadTexturesEditor.getTexture("editorSpot"));
		itemGuardian.draw(gl, LoadTexturesEditor.getTexture("editorGuardian"));
		itemKey.draw(gl, LoadTexturesEditor.getTexture("editorKey"));
		itemCamera.draw(gl, LoadTexturesEditor.getTexture("editorCamera"));
		itemControlCenter.draw(gl, LoadTexturesEditor.getTexture("controlCenterEditor"));

		//de items met speciale eigenschappen zoals Key en Guard worden hier getekent.
		//dit zijn de items die al met een positie in de map zijn geplaatst.
		placedItems.draw(gl, null);
		placedItems.drawItems(gl);
		
		//de extra menu's indien op een  speciale item is gedrukt
		if(Mode == ClickOptions.guardian){
			placedItemsProperties.draw(gl, null);
			addGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorAddButton")); 
			removeGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorRemoveButton"));  
			removeLastPointGuardOrSetDoorKey.draw(gl, LoadTexturesEditor.getTexture("editorDeleteLastPoint")); 
			showAllGuardsKeys.draw(gl, LoadTexturesEditor.getTexture("editorShowAll")); 
		}
		
		if(Mode == ClickOptions.key || Mode == ClickOptions.setKeyDoor){
			placedItemsProperties.draw(gl, null);
			addGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorAddButton")); 
			removeGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorRemoveButton"));  
			removeLastPointGuardOrSetDoorKey.draw(gl, LoadTexturesEditor.getTexture("editorDeleteDoor")); 
			showAllGuardsKeys.draw(gl, LoadTexturesEditor.getTexture("editorShowAll")); 
		}
		
		if(Mode == ClickOptions.key){
			placedItemsProperties.draw(gl, null);
			addGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorAddButton")); 
			removeGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorRemoveButton"));  
		}
		
		if(Mode == ClickOptions.setSpot || Mode == ClickOptions.removeSpot){
			placedItemsProperties.draw(gl, null);
			addGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorAddButton")); 
			removeGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorRemoveButton"));  
		}
		
		if(Mode == ClickOptions.setCamera || Mode == ClickOptions.removeCamera){
			placedItemsProperties.draw(gl, null);
			addGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorAddButton")); 
			removeGuardKeySpotCamera.draw(gl, LoadTexturesEditor.getTexture("editorRemoveButton"));  
		}
		
		if(Mode == ClickOptions.setControlCenter || Mode == ClickOptions.removeControlCenter){
			placedItemsProperties.draw(gl, null);
			addGuardKeySpotCamera.draw(gl, loadedTexturesEditor.getTexture("addButton")); 
			removeGuardKeySpotCamera.draw(gl, loadedTexturesEditor.getTexture("removeButton"));  
		}
		
		
		//draw the clickable boxes
		setSizes.draw(gl, LoadTexturesEditor.getTexture("editorSizeButton"));
		setStart.draw(gl, LoadTexturesEditor.getTexture("editorSetStartButton"));
		setEnd.draw(gl, LoadTexturesEditor.getTexture("editorSetEndButton"));
		save.draw(gl, LoadTexturesEditor.getTexture("editorSaveButton"));
		load.draw(gl, LoadTexturesEditor.getTexture("editorLoadButton"));
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
	 * 
	 * alle vensters die getekent zijn krijgen de nieuwe screenWidth en screenHeight mee, zodat ze naar de goede verhouding
	 * worden weergegeven.
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
		setSizes.update(screenWidth, screenHeight);
		save.update(screenWidth, screenHeight);
		load.update(screenWidth, screenHeight);
		itemFloor.update(screenWidth, screenHeight);
		itemWall.update(screenWidth, screenHeight);
		itemDoor.update(screenWidth, screenHeight);
		itemSpot.update(screenWidth, screenHeight);
		itemGuardian.update(screenWidth, screenHeight);
		itemKey.update(screenWidth, screenHeight);
		itemCamera.update(screenWidth, screenHeight);
		itemControlCenter.update(screenWidth, screenHeight);
		addGuardKeySpotCamera.update(screenWidth, screenHeight);
		removeGuardKeySpotCamera.update(screenWidth, screenHeight); 
		removeLastPointGuardOrSetDoorKey.update(screenWidth, screenHeight);
		showAllGuardsKeys.update(screenWidth, screenHeight);
				

		// Update the projection to an orthogonal projection using the new
		// screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

	}

	@Override
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 * 
	 * Er wordt gecontrolleerd of op 1 van de windows of buttons is gedrukt. indien op deze button gedrukt zal er een actie 
	 * plaats vinden. dit kan het setten van bijvoorbeeld een wall of het aanpassen van de Mode. Mode is een ennum die de 
	 * waardes aan kan nemen van het window of button waar op is gedrukt. als er dus op wall is gedrukt zal de mode 'wall' worden.
	 * wanneer vervolgens op de map op een buildingblock wordt gedrukt zal wordden gekeken naar de mode en zal er een wall worden 
	 * geset in de geklikte BuildingBlock.
	 * 
	 */
	public void mouseReleased(MouseEvent me) {
			
		if(map.clickedOnIt(me.getX(), me.getY())){							//Map
			mapClickHandler(me);				
		}else if(itemFloor.clickedOnIt(me.getX(), me.getY())){				//Floor
			System.out.println("floor");
			Mode = ClickOptions.floor;
		}else if(itemWall.clickedOnIt(me.getX(), me.getY())){ 				//Wall
			System.out.println("wall");	
			Mode = ClickOptions.wall;
		}else if(itemDoor.clickedOnIt(me.getX(), me.getY())){				//Door
			System.out.println("door");
			Mode = ClickOptions.door;
		}else if(itemSpot.clickedOnIt(me.getX(), me.getY())){				//Spot
			Mode = ClickOptions.setSpot;
			spot = new Spot();
			System.out.println("spot");
		}else if(itemGuardian.clickedOnIt(me.getX(), me.getY())){			//Guard
			System.out.println("guardian");
			Mode = ClickOptions.guardian;
			guard = new Guardian(itemCoords, screenWidth, screenHeight);	
		}else if(itemKey.clickedOnIt(me.getX(), me.getY())){				//Key		
			System.out.println("key");
			Mode = ClickOptions.key;
			key = new Key(itemCoords, screenWidth, screenHeight);
		}else if(itemCamera.clickedOnIt(me.getX(), me.getY())){				//Camera
			System.out.println("camera");
			camera = new Camera();
			Mode = ClickOptions.setCamera;	
		}else if(itemControlCenter.clickedOnIt(me.getX(), me.getY())){				//Control Center
			System.out.println("control center");
			controlCenter = new ControlCenterEditor();
			Mode = ClickOptions.setControlCenter;	
		}else if(placedItems.clickedOnIt(me.getX(), me.getY())){			//placed Items (kunnen Guard of Keys zijn)
			Mode = ClickOptions.placedItems;
			if(placedItems.typeIsGuardian(me.getX(), me.getY())){
				guard = placedItems.getClickedGuardian(me.getX(), me.getY());
				Mode = ClickOptions.guardian;
			}
			if(placedItems.typeIsKey(me.getX(), me.getY())){
				key = placedItems.getClickedKey(me.getX(), me.getY());
				Mode = ClickOptions.key;
			}	
		}else if(placedItemsProperties.clickedOnIt(me.getX(), me.getY())){		//extra opties menu
			PlacedItemsPropertiesClickHandler(me);
		}else if(setStart.clickedOnIt(me.getX(), me.getY())){				//Start punt
			Mode = ClickOptions.setStart;
		}else if(setEnd.clickedOnIt(me.getX(), me.getY())){					//Eind punt
			Mode = ClickOptions.setEnd;
		}else if(setSizes.clickedOnIt(me.getX(), me.getY())){				//set nieuwe size van Maze
			sizes.appear();						
		}else if(save.clickedOnIt(me.getX(), me.getY())){					//Save window
			//popup window waar de naam en plek 
			//van de weg te schrijven file kan worden ingegeven.
			String SaveLevel = saveFile.getFilePath();
			System.out.println(SaveLevel);
			if(SaveLevel != null){
				//de gegenereerde map wordt opgeslagen
				StoreMaze = new SaveInput(map, placedItems, StartEnd, spotList, cameraList, controlCenterList, SaveLevel); 
			}	
		}else if(load.clickedOnIt(me.getX(), me.getY())){					//Load Window
			//het level moet ingeladen worden en nog gedisplayd
			String LoadedLevel = loadFile.getFilePath();
			if(LoadedLevel != null){
				LoadLevel newlevel = new LoadLevel(LoadedLevel);
				if(newlevel.getValid()){
					initialiseObjects(newlevel);
				}
			}
		}
	}

	/**
	 * wanneer het nieuwe level wordt ingeladen zal alles tot nu toe worden gereset en daarna worden
	 * alle objecten uit newlevel ingeladen, zodat er verder bewerkt kan worden
	 *  
	 * @param newlevel
	 */
	private void initialiseObjects(LoadLevel newlevel) {
		this.resetObjects();
		map.loadBuildingBlocks(newlevel.getFloorPlan(), newlevel.getWidth(), newlevel.getHeight());
		map.loadKeysAndDoors(newlevel.getKeys());
		placedItems.loadGardians(newlevel.getGuardians());
		placedItems.loadKeys(newlevel.getKeys());
		StartEnd.setStart(newlevel.getStartPosition());
		StartEnd.setEnd(newlevel.getEndPosition());
		cameraList.loadCameras(newlevel.getCameras());
		controlCenterList.loadControlCenters(newlevel.getControlCenters());
		spotList.loadSpots(newlevel.getSpots());
	}

	/**
	 * wanneer op de map is geklikt zal worden gekeken welke Mode is geset en zal dit afgehandeld worden
	 * @param me
	 */
	private void mapClickHandler(MouseEvent me) {
		if(map.BuildingBlocksExists()){
			if(Mode == ClickOptions.wall){														//Wall
				//de wall wordt geset, wanneer er 
				//geen bewaker/begin punt/eind punt 
				//op deze plek staat.
				boolean setWall = posibleToPlaceWallOrDoor(me);
				if(setWall){
					removeKeyAndDoor(me);	//als op deze plek een deur stond word de deur met bijbehorende sleutel verwijderd
					map.getClickedBuildingBlock(me.getX(), me.getY()).setWall();
				}
				else
					System.out.println("er staat een bewaker/begin punt/eind punt op deze plek");
			}
			if(Mode == ClickOptions.floor){														//Floor
				//de floor wordt geset.
				removeKeyAndDoor(me);
				map.getClickedBuildingBlock(me.getX(), me.getY()).setFloor();
			}
			if(Mode == ClickOptions.door){														//Door
				//de door wordt geset, wanneer er geen bewaker/begin punt/eind punt op deze plek staat.
				boolean setDoor = posibleToPlaceWallOrDoor(me);
				if(setDoor){
					removeKeyAndDoor(me);
					map.getClickedBuildingBlock(me.getX(), me.getY()).setDoor();
				}
				else
					System.out.println("er staat een bewaker/begin punt/eind punt op deze plek");
			}
			
			// er kunnen maximaal 8 spotjes geplaatst worden
			if(Mode == ClickOptions.setSpot){													//place Spot
				System.out.println("addSpot");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(!temp.getWall() && !temp.getDoor()){
					Point tempPositie = temp.getPosition();
					spot.setSpot(tempPositie);
					spotList.addSpot(spot);
					spot = new Spot();
				}
			}
			if(Mode == ClickOptions.removeSpot){												//Remove Spot
				System.out.println("removeSpot");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				Point tempPositie = temp.getPosition();
				spot.setSpot(tempPositie);
				spotList.removeSpot(spot);
				spot = new Spot();
			}
			
			//Cameras
			if(Mode == ClickOptions.setCamera){													//place Camera
				System.out.println("addCamera");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(!temp.getWall() && !temp.getDoor()){
					Point tempPositie = temp.getPosition();
					camera.setCamera(tempPositie);
					cameraList.addCamera(camera);
					camera = new Camera();
				}
			}
			if(Mode == ClickOptions.removeCamera){												//Remove Camera
				System.out.println("removeCamera");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				Point tempPositie = temp.getPosition();
				camera.setCamera(tempPositie);
				cameraList.removeCamera(camera);
				camera = new Camera();
			}	
			
			//Control Centers
			if(Mode == ClickOptions.setControlCenter){											//place ControlCenter
				System.out.println("addControlCenter");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(!temp.getWall() && !temp.getDoor()){
					Point tempPositie = temp.getPosition();
					controlCenter.setControlCenter(tempPositie);
					controlCenterList.addControlCenter(controlCenter);
					controlCenter = new ControlCenterEditor();
				}
			}
			if(Mode == ClickOptions.removeControlCenter){										//Remove ControlCenter
				System.out.println("removeControlCenter");
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				Point tempPositie = temp.getPosition();
				controlCenter.setControlCenter(tempPositie);
				controlCenterList.removeControlCenter(controlCenter);
				controlCenter = new ControlCenterEditor();
			}	
			
			if(Mode == ClickOptions.guardian){													//Guard
				/*
				kijkt of het geklikte vlakje een floor bevat en voegt dit punt toe aan de 
				route lijst van de bewaker. Dit punt moet wel horizontaal of verticaal grenzen
				aan het vorig aangeklikte punt waar de bewaker loopt. de bewaker mag niet schuin
				 lopen
				*/
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					guard.addRoute(tempPositie);
				}
				else
					System.out.println("dit is geen open plek, dus er kan geen bewaker worden geplaatst");
			}
			
			if(Mode == ClickOptions.key){														//Key
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					key.setKey(tempPositie);
				}
				else
					System.out.println("dit is geen open plek, dus er kan geen key worden geplaatst");
			}
			if(Mode == ClickOptions.setKeyDoor){												//Connect Key to Door
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getDoor() && !temp.getKeyRequired()){
					//eerst wordt de oude deur opgevraagd indien deze
					//er is wordt hij verwijderd en word de keyRequired uit gezet.
					if(key.hasDoor()){
						Point a = key.getDoor();
						map.getBuildingBlockByPosition(a).removeKeyRequired();
					}
					
					//nu wordt bij de nieuwe deur de key geset.
					Point tempPositie = temp.getPosition();
					key.setDoor(tempPositie);
					temp.setKeyRequired();
				}
				else
					System.out.println("geen deur of deur heeft al een sleutel");
			}
			
			if(Mode == ClickOptions.setStart){													//Set Start
				//mag alleen geset worden op een vloer
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					StartEnd.setStart(tempPositie);					
				}
				else
					System.out.println("de start positie kan alleen op een open vlak");
			}
			
			if(Mode == ClickOptions.setEnd){													//Set End
				//mag alleen geset worden op een vloer
				BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
				if(temp.getFloor()){
					Point tempPositie = temp.getPosition();
					StartEnd.setEnd(tempPositie);					
				}
				else
					System.out.println("de eind positie kan alleen op een open vlak");
				
			}
		}
		else System.out.println("Vul een lengte en breedte in.");
	}

	/**
	 * wanneer dit BuildingBlock een deur is die een sleutel nodig heeft, zal de deur met de bijbehorende sleutel worden
	 * verwijderd. als er geen sleutel bij de deur hoort gebeurt er niets. 
	 * @param me
	 */
	private void removeKeyAndDoor(MouseEvent me) {
		BuildingBlock temp = map.getClickedBuildingBlock(me.getX(), me.getY());
		if(temp.getKeyRequired()){
			Point a = temp.getPosition();
			for(Key k: placedItems.getAllKeys()){
				if(k.getDoor().equals(a)){
					placedItems.removeKey(k);
					break;
				}
			}
		}
	}

	/**
	 * kijkt of het mogelijk is om een muur of deur te plaatsen. dit is niet mogelijk wanneer er een spot staat,
	 * of een camera, of een sleutel, of een bewaker route of een begin of eindpunt. indien het wel mogelijk is returnt
	 * de funtie true. 
	 * @param me
	 * @return
	 */
	private boolean posibleToPlaceWallOrDoor(MouseEvent me) {
		boolean possible = true;
		
		//mag niet wanneer er een Guardian path loopt.
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
		
		//mag niet wanneer er een controlCenter staat.
		for(ControlCenterEditor s: controlCenterList.getControlCenters()){
			if(s.getPosition().equals(a)){
				possible = false;
				break;
			}
		}
		
		//mag ook niet wanneer er een sleutel staat.
		for(Key k: placedItems.getAllKeys()){
			Point p = k.getKey();
			if(p.equals(a)){
				possible = false;
				break;
			}	
		}
		
		
		//de wall wordt geset, wanneer er geen start of begin punt is
		if((StartEnd.getStart().equals(a) && StartEnd.hasStart()) || (StartEnd.getEnd().equals(a) && StartEnd.hasEnd())){
			possible = false;
		}
		return possible;
	}

	/**
	 * wanneer het extra menu zichtbaar is en er is op geklikt zal hier het bijbehoordende event worden afgehandeld.
	 * voor een Guard geld:
	 * - het toevoegen van een guard
	 * - het verwijderen van de guard
	 * - een route punt 1 stap terug nemen
	 * - alle geplaatste guards zichtbaar maken
	 * 
	 * voor Key:
	 * - het toevoegen van een key
	 * - het verwijderen van een key
	 * - deur aan een key koppelen
	 * - alle keys zichtbaar maken
	 * 
	 * voor Spot:
	 * - toevoegen van een Spot
	 * - verwijderen van een Spot
	 * 
	 * voor Camera:
	 * - toevoegen van een Camera
	 * - verwijderen van een Camera 
	 * 
	 * voor Control Center:
	 * - toevoegen van een Control Center
	 * - verwijderen van een Control Center 
	 * 
	 * @param me
	 */
	private void PlacedItemsPropertiesClickHandler(MouseEvent me) {
		if(Mode == ClickOptions.guardian){															//Guard
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
			if(removeLastPointGuardOrSetDoorKey.clickedOnIt(me.getX(), me.getY())){
				guard.removeLastPoint();
			}
			if(showAllGuardsKeys.clickedOnIt(me.getX(), me.getY())){
				if(!AllGuardsOnOff){
					AllGuardsOnOff = true;
				}else{
					AllGuardsOnOff = false;
					}
			}
			 
		} 
		else if(Mode == ClickOptions.key || Mode == ClickOptions.setKeyDoor){ 						//Key
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				placedItems.addKey(key);
				System.out.println("Key is toegevoegd");
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				if(key.hasDoor()){
					Point a = key.getDoor();
					map.getBuildingBlockByPosition(a).removeKeyRequired();
					System.out.println(map.getBuildingBlockByPosition(a).getKeyRequired());
				}
				
				placedItems.removeKey(key);
				//er wordt een lege key aangemaakt.
				key = new Key(itemCoords, screenWidth, screenHeight);
				System.out.println("Key is verwijderd");
			}		
			if(removeLastPointGuardOrSetDoorKey.clickedOnIt(me.getX(), me.getY())){
				System.out.println("Door wordt geset");
				Mode = ClickOptions.setKeyDoor;
			}
			if(showAllGuardsKeys.clickedOnIt(me.getX(), me.getY())){
				if(!AllKeysOnOff){
					AllKeysOnOff = true;
				}else{
					AllKeysOnOff = false;
					}
			}
		}
		
		// zorgt dat de mode wordt geset om een spot te deleten
		// of toe te voegen wanneer op een plek op de map wordt gedrukt.
		else if(Mode == ClickOptions.setSpot || Mode == ClickOptions.removeSpot){				//Spot
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.setSpot;
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.removeSpot;
			}	
		}
		
		else if(Mode == ClickOptions.setCamera || Mode == ClickOptions.removeCamera){			//Camera
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){	
				Mode = ClickOptions.setCamera;
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.removeCamera;
			}	
		}
		
		else if(Mode == ClickOptions.setControlCenter || Mode == ClickOptions.removeControlCenter){			//Control Center
			if(addGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){	
				Mode = ClickOptions.setControlCenter;
			}
			if(removeGuardKeySpotCamera.clickedOnIt(me.getX(), me.getY())){
				Mode = ClickOptions.removeControlCenter;
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

	/**
	 * alleen wanneer een goede waarde is opgeslagen zal de if statement 1 maal worden uitgevoerd
	 * totdat opnieuw een nieuwe geldige waarde is ingevuld in het SizeWindows.
	 */
	public void mapDrawCheck(){
		mapCreated = map.hasHeightAndWidth();
		
		if(sizes.getMapdrawCheck()){ 
			resetObjects();
			
			map.setHeight(sizes.getHeightField());
			map.setWidth(sizes.getWidthField());

			int a = map.getWidth();
			int b = map.getHeight();
			System.out.println("Breedte: "+a+" Hoogte: "+b);
			
			map.setTotalBuildingBlocks();

			sizes.resetMapdrawCheck();			
		}
		
	}

	/**
	 * zorgt dat alle objecten die in de map zijn geplaatst worden gereset.
	 */
	private void resetObjects() {
		guard = new Guardian(itemCoords, screenWidth, screenHeight);
		key = new Key(itemCoords, screenWidth, screenHeight);
		spot = new Spot();
		spotList = new SpotList();
		camera = new Camera();
		cameraList = new CameraList();
		controlCenter = new ControlCenterEditor();
		controlCenterList = new ControlCenterList();
		StartEnd = new StartAndEndPosition();
		
		placedItems = new PlacedItemsMenu(placedItemsCoords, screenWidth, screenHeight);
		map = new MapMenu(mapCoords, screenWidth, screenHeight);
	}

}

