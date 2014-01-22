package MazeRunner.Objects;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL;

import LevelEditor.LoadLevel;
import LevelEditor.OpenLevelFrame;
import MazeRunner.Fundamental.LoadTexturesMaze;

import com.sun.opengl.util.texture.Texture;

/**
 * Maze represents the maze used by MazeRunner.
 * <p>
 * The Maze is defined as an array of integers, where 0 equals nothing and 1
 * equals a wall. Note that the array is square and that MAZE_SIZE contains the
 * exact length of one side. This is to perform various checks to ensure that
 * there will be no ArrayOutOfBounds exceptions and to perform the calculations
 * needed by not only the display(GL) function, but also by functions in the
 * MazeRunner class itself.<br />
 * Therefore it is of the utmost importance that MAZE_SIZE is correct.
 * <p>
 * SQUARE_SIZE is used by both MazeRunner and Maze itself for calculations of
 * the display(GL) method and other functions. The larger this value, the larger
 * the world of MazeRunner will be.
 * <p>
 * This class implements VisibleObject to force the developer to implement the
 * display(GL) method, so the Maze can be displayed.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */
public class Maze implements VisibleObject {
	private String levelName = "level1";
	private OpenLevelFrame levelPicker = new OpenLevelFrame();
	private LoadLevel newMaze;
	// public final double MAZE_SIZE = 10;
	public double MAZE_SIZE_X;
	public double MAZE_SIZE_Z;
	public static final double SQUARE_SIZE = 5;
	public Point startPoint;
	public Point endPoint;
	private ArrayList<Keys> doorKeys;
	private ArrayList<ControlCenter> controlCenters = new ArrayList<ControlCenter>();

	private int[][] maze;
	private int[][] oldMaze; //deze wordt gebruikt om te kijken of de maze is veranderd in de loop van het spel.

	public Maze() {
		levelName = levelPicker.getFilePath();
		levelPicker.requestFocus();
		if (levelName == null) {
			levelName = "level1";
		}
		newMaze = new LoadLevel(levelName);
		MAZE_SIZE_X = newMaze.getWidth();
		MAZE_SIZE_Z = newMaze.getHeight();
		startPoint = newMaze.getStartPosition();
		endPoint = newMaze.getEndPosition();

		maze = newMaze.outputForMazeRunner();
		oldMaze = new int[(int)MAZE_SIZE_X][(int)MAZE_SIZE_Z]; 
		copyMazeToOldMaze();
	}
	
	/**
	 * Constructor with predefined maze. Used for JUnit testing.
	 * 
	 * @param maze
	 *            : The predefined maze. <b>Must be 3x3!</b>
	 */
	public Maze(int[][] maze) {
		MAZE_SIZE_X = 3;
		MAZE_SIZE_Z = 3;

		this.maze = maze;
	}

	/**
	 * isWall(int x, int z) checks for a wall.
	 * <p>
	 * It returns whether maze[x][z] contains a 1.
	 * 
	 * @param x
	 *            the x-coordinate of the location to check
	 * @param z
	 *            the z-coordinate of the location to check
	 * @return whether there is a wall at maze[x][z]
	 */
	public boolean isWall(int x, int z) {
		if (x >= 0 && x < MAZE_SIZE_X && z >= 0 && z < MAZE_SIZE_Z)
			return maze[x][z] == 1;
		else
			return false;
	}

	public boolean isClosedDoor(int x, int z) {
		if (x >= 0 && x < MAZE_SIZE_X && z >= 0 && z < MAZE_SIZE_Z)
			return maze[x][z] == 2;
		else
			return false;
	}

	public boolean isOpeningDoor(int x, int z) {
		if (x >= 0 && x < MAZE_SIZE_X && z >= 0 && z < MAZE_SIZE_Z)
			return maze[x][z] == 3;
		else
			return false;
	}

	/**
	 * isWall(double x, double z) checks for a wall by converting the double
	 * values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the
	 * grid defined by maze[][]. Then it calls upon isWall(int, int) to check
	 * for a wall.
	 * 
	 * @param x
	 *            the x-coordinate of the location to check
	 * @param z
	 *            the z-coordinate of the location to check
	 * @return whether there is a wall at maze[x][z]
	 */
	public boolean isWall(double x, double z) {
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return (isWall(gX, gZ) || isClosedDoor(gX, gZ) || isOpeningDoor(gX, gZ) || isControlCenter(x,z));
	}

	public boolean isWallOrClosedDoor(double x, double z) {
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return (isWall(gX, gZ) || isClosedDoor(gX, gZ) || isOpeningDoor(gX, gZ));
	}

	public boolean isClosedDoor(double x, double z) {
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return isClosedDoor(gX, gZ);
	}

	public boolean isOpeningDoor(double x, double z) {
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return isOpeningDoor(gX, gZ);
	}
	
	public boolean isControlCenter(double x, double z) {	
		for (ControlCenter c : controlCenters) {
			if (Math.abs(x - c.locationX) < c.getSizeX()
					&& Math.abs(z - c.locationZ) < c.getSizeZ()) {
				return true;
			}
		}
		return false;
	}

	public void openDoor(Point a) {
		int x = (int) a.getX();
		int z = (int) a.getY();
		maze[x][z] = 3;
	}

	public void removeDoor(Point a) {
		int x = (int) a.getX();
		int z = (int) a.getY();
		maze[x][z] = 0;
	}

	/**
	 * Converts the double x-coordinate to its correspondent integer coordinate.
	 * 
	 * @param x
	 *            the double x-coordinate
	 * @return the integer x-coordinate
	 */
	private int convertToGridX(double x) {
		return (int) Math.floor(x / SQUARE_SIZE);
	}

	/**
	 * Converts the double z-coordinate to its correspondent integer coordinate.
	 * 
	 * @param z
	 *            the double z-coordinate
	 * @return the integer z-coordinate
	 */
	private int convertToGridZ(double z) {
		return (int) Math.floor(z / SQUARE_SIZE);
	}

	public void setKeys(ArrayList<Keys> keys) {
		doorKeys = keys;
	}

	public void display(GL gl) {

		for (int i = 0; i < MAZE_SIZE_X; i++) {
			for (int j = 0; j < MAZE_SIZE_Z; j++) {
				gl.glPushMatrix();
				gl.glTranslated(i * SQUARE_SIZE, 0, j * SQUARE_SIZE);
				if (isWall(i, j)) {
					drawWall(gl, SQUARE_SIZE,
							LoadTexturesMaze.getTexture("wallTexture"));
				}
				if (!isWall(i, j)) {
					drawFloor(gl, SQUARE_SIZE,
							LoadTexturesMaze.getTexture("floorTexture"));
					drawRoof(gl, SQUARE_SIZE,
							LoadTexturesMaze.getTexture("floorTexture"));
				}
				if (isClosedDoor(i, j)) {
					boolean possibleKey = false;
					for (Keys k : doorKeys) {
						if (k.getDoor().equals(new Point(i, j))) {
							possibleKey = true;
							drawClosedDoor(gl, SQUARE_SIZE, k.getKeyColor());
						}
					}
					if(!possibleKey){
						drawClosedDoor(gl, SQUARE_SIZE, null);
					}
				}
				if (isOpeningDoor(i, j)) {
					for (Keys k : doorKeys) {
						if (k.getDoor().equals(new Point(i, j))) {
							double translateValue = k.openingDoor();
							if (translateValue > SQUARE_SIZE) {
								removeDoor(new Point(i, j));
								doorKeys.remove(k);
							}
							drawOpeningDoor(gl, SQUARE_SIZE, translateValue,
									k.getKeyColor());
							break;
						}
					}
				}
				gl.glPopMatrix();
			}
		}
	}

	/**
	 * paintSingleFloorTile(GL, double) paints a single floor tile, to represent
	 * the floor of the entire maze.
	 * 
	 * @param gl
	 *            the GL context in which should be drawn
	 * @param size
	 *            the size of the tile
	 */
	private void drawFloor(GL gl, double size, Texture myTexture) {
		// Setting the floor color and material.
		float floorColour[] = { 1.0f, 1.0f, 1.0f, 0f };
		// Set the materials used by the floor.
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, floorColour, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		// Sets the current normal vector
		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, 0, 0);
		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}
	}

	private void drawWall(GL gl, double size, Texture myTexture) {
		// set the color for the wall
		float wallColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		// sets the material and lighting for the wall

		// Wanneer er geen texture gevonden is, teken toch maar een vierkant
		if (myTexture != null) {
			// Eerst de texture aanzetten
			myTexture.enable();
			// Dan de texture binden aan het volgende object
			myTexture.bind();
		}
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColor, 0);
		gl.glBegin(GL.GL_QUADS);
		// Voorzijde muur ERROR
		gl.glNormal3d(0, 0, -1);
		// Eerste texture coordinaat
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, 0);
		// Tweede texture coordinaat
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0, size, 0);
		// Derde texture coordinaat
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, size, 0);
		// Vierde texture coordinaat
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(size, 0, 0);

		// Achterzijde muur
		gl.glNormal3d(0, 0, 1);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, size);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, size);

		// Bovenzijde muur
		gl.glNormal3d(1, 0, 0);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, 0, 0);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, size, size);

		// Onderzijde muur
		gl.glNormal3d(-1, 0, 0);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0, size, size);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, 0);
		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}
	}

	private void drawRoof(GL gl, double size, Texture myTexture) {
		// Setting the floor color and material.
		float roofColor[] = { 1.0f, 1.0f, 1.0f, 0.0f }; // The floor is blue.
		// Set the materials used by the floor.
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, roofColor, 0);

		// Sets the current normal vector
		gl.glNormal3d(0, 1, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, -1, 0);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, size, 0);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, size, 0);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, size);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, size);
		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}
	}

	private void drawClosedDoor(GL gl, double size, float[] keyCardHoleColor) {
		// set the colour for the wall
		float DoorColor[] = { 1.0f, 0.0f, 0.0f, 0f };
		// sets the material and lighting for the wall
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, DoorColor, 0);
		Texture myTexture = LoadTexturesMaze.getTexture("doorTexture");
		Texture myTexture2 = LoadTexturesMaze.getTexture("doorBottomTexture");

		// Wanneer er geen texture gevonden is, teken toch maar een vierkant
		if (myTexture != null) {
			// Eerst de texture aanzetten
			myTexture.enable();
			// Dan de texture binden aan het volgende object
			myTexture.bind();
		}

		gl.glBegin(GL.GL_QUADS);
		// Voorzijde muur
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0, size, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, size, 0);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(size, 0, 0);

		// Achterzijde muur
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, size);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, size);

		// Bovenzijde muur
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, 0, 0);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, size, size);

		// Onderzijde muur
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0, size, size);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, 0);

		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}

		// Sleutel gat

		if(keyCardHoleColor != null){
			// DIT MOET WEG
			gl.glColor3f(keyCardHoleColor[0], keyCardHoleColor[1],
					keyCardHoleColor[2]);
			gl.glEnable(GL.GL_COLOR_MATERIAL);
			gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, keyCardHoleColor,0);
			gl.glBegin(GL.GL_QUADS);
	
			// HIER KUNNEN DE MATEN WORDEN OPGEGEVEN. GEDEELD DOOR WORDT NIET
			// ONDERSTEUND
			// JE MOET HET DUS ZELF EVEN UITREKENEN bedacht vanuit links boven aan
			// is 0,0
			// double x_links = 239/298, x_rechts = 267/298, y_laag = 211/424,
			// y_hoog = 240/424;
			double x_links = 0.8, x_rechts = 0.895, y_laag = 0.428, y_hoog = 0.497;// 0.566;
	
			gl.glVertex3d((1 - x_links) * size, y_hoog * size, -0.01); // rechtsboven
			gl.glVertex3d((1 - x_links) * size, y_laag * size, -0.01); // links
																		// onder
			gl.glVertex3d((1 - x_rechts) * size, y_laag * size, -0.01); // rechts
																		// onder
			gl.glVertex3d((1 - x_rechts) * size, y_hoog * size, -0.01); // linksboven
	
			// Achterzijde muur
			gl.glVertex3d(x_links * size, y_hoog * size, size + 0.01); // linksboven
			gl.glVertex3d(x_links * size, y_laag * size, size + 0.01); // linksonder
			gl.glVertex3d(x_rechts * size, y_laag * size, size + 0.01); // rechtsonder
			gl.glVertex3d(x_rechts * size, y_hoog * size, size + 0.01); // rechtsboven
	
			// Bovenzijde muur, y,x
			gl.glVertex3d(size + 0.01, y_laag * size, (1 - x_links) * size); // linksonder
			gl.glVertex3d(size + 0.01, y_laag * size, (1 - x_rechts) * size); // rechtonder
			gl.glVertex3d(size + 0.01, y_hoog * size, (1 - x_rechts) * size); // rechtsboven
			gl.glVertex3d(size + 0.01, y_hoog * size, (1 - x_links) * size); // linksboven
	
			// Onderzijde muur y,x
			gl.glVertex3d(0 - 0.01, y_laag * size, x_links * size); // linksonder
			gl.glVertex3d(0 - 0.01, y_laag * size, x_rechts * size);
			gl.glVertex3d(0 - 0.01, y_hoog * size, x_rechts * size);
			gl.glVertex3d(0 - 0.01, y_hoog * size, x_links * size); // linksboven
			
			gl.glEnd();
			
			// DIT MOET WEG
			gl.glColor3f(1, 1, 1);
			gl.glDisable(GL.GL_COLOR_MATERIAL);
		}
		
		if (myTexture2 != null) {
			// Eerst de texture aanzetten
			myTexture2.enable();
			// Dan de texture binden aan het volgende object
			myTexture2.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		// onderzijde DEZE MOET EEN ANDERE TEXTURE
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, 0, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, 0, size);

		gl.glEnd();
		if (myTexture2 != null) {
			myTexture2.disable();
		}
		
		
	}

	private void drawOpeningDoor(GL gl, double size, double opening,
			float[] keyCardHoleColor) {
		gl.glTranslated(0, opening, 0);
		drawClosedDoor(gl, SQUARE_SIZE, keyCardHoleColor);
	}

	public String getLevelName() {
		return levelName;
	}

	/**
	 * Returns the LoadLevel-representation of the Maze. Used to import all
	 * objects such as cameras into MazeRunner.
	 * 
	 * @return
	 */
	public LoadLevel getLoadLevel() {
		return this.newMaze;
	}

	public int[][] getMaze() {
		return maze;
	}

	public void setMaze(int[][] maze) {
		this.maze = maze;
	}
	
	public void setControlCenters(ArrayList<ControlCenter> controlCentersMazerunner){
		controlCenters = controlCentersMazerunner;
	}
	
	private void copyMazeToOldMaze(){
		for (int i = 0; i < MAZE_SIZE_X; i++) {
			for (int j = 0; j < MAZE_SIZE_Z; j++) {
				oldMaze[i][j] = maze[i][j];
			}
		}
	}
	
	
	/**
	 * 
	 * @return of de maze is veranderd na de vorige aanroep van deze functie.
	 */
	public boolean mazeChanged() {
		boolean change = false;
		outerloop:
		for (int i = 0; i < MAZE_SIZE_X; i++) {
			for (int j = 0; j < MAZE_SIZE_Z; j++) {
				if(oldMaze[i][j] != maze[i][j]){
					change = true;
					copyMazeToOldMaze();
					break outerloop;					
				}
			}
		}
		return change;
	}

}
