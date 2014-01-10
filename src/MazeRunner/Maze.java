package MazeRunner;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL;

import LevelEditor.LoadLevel;

import com.sun.opengl.util.GLUT;
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

	private LoadLevel newMaze = new LoadLevel("level1");
	// public final double MAZE_SIZE = 10;
	public final double MAZE_SIZE_X = newMaze.getWidth();
	public final double MAZE_SIZE_Z = newMaze.getHeight();
	public final double SQUARE_SIZE = 5;
	public final Point startPoint = newMaze.getStartPosition();
	private ArrayList<Keys> doorKeys;

	private LoadTexturesMaze loadedTexturesMaze;

	// private Texture wallTexture;
	// private Texture floorTexture;
	// private Texture roofTexture;
	// private Texture spotTexture;
	// private Texture doorTexture, doorBottomTexture;
	// private boolean texLoaded = false;

	public Maze(LoadTexturesMaze temp) {
		loadedTexturesMaze = temp;
	}

	// private int[][] maze =
	// { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
	// { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	// { 1, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
	// { 1, 0, 1, 0, 0, 0, 1, 0, 0, 1 },
	// { 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
	// { 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
	// { 1, 0, 0, 0, 1, 0, 1, 0, 0, 1 },
	// { 1, 0, 0, 0, 1, 1, 1, 0, 0, 1 },
	// { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
	// { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	private int[][] maze = newMaze.outputForMazeRunner();

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
		return (isWall(gX, gZ) || isClosedDoor(gX, gZ) || isOpeningDoor(gX, gZ));
	}

	public int[][] getMaze() {
		return maze;
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
							loadedTexturesMaze.getTexture("wallTexture"));
				}
				if (!isWall(i, j)) {
					drawFloor(gl, SQUARE_SIZE,
							loadedTexturesMaze.getTexture("floorTexture"));
					drawRoof(gl, SQUARE_SIZE,
							loadedTexturesMaze.getTexture("floorTexture"));
				}
				if (isClosedDoor(i, j)) {
					for (Keys k : doorKeys) {
						if (k.getDoor().equals(new Point(i, j))) {
							drawClosedDoor(gl, SQUARE_SIZE,
									loadedTexturesMaze
											.getTexture("doorTexture"),
									loadedTexturesMaze
											.getTexture("doorBottomTexture"),
									k.getKeyColor());
						}
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
		// gl.glEnable(GL.GL_TEXTURE_2D);
		// Sets the current normal vector
		gl.glNormal3d(0, 1, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(size, 0, 0);
		gl.glEnd();
		myTexture.disable();

	}

	private void drawWall(GL gl, double size, Texture myTexture) {
		// set the colour for the wall
		float wallColour[] = { 1.0f, 1.0f, 1.0f, 0f };
		// sets the material and lighting for the wall
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);

		// Wanneer er geen texture gevonden is, teken toch maar een vierkant
		if (myTexture != null) {
			// Eerst de texture aanzetten
			myTexture.enable();
			// Dan de texture binden aan het volgende object
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		// Voorzijde muur
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

		// Rechterzijde muur (bovenkant, hoeft niet per se)
		// gl.glTexCoord2d(0, 0);
		// gl.glVertex3d(0, size, 0);
		// gl.glTexCoord2d(1, 0);
		// gl.glVertex3d(0, size, size);
		// gl.glTexCoord2d(1, 1);
		// gl.glVertex3d(size, size, size);
		// gl.glTexCoord2d(0, 1);
		// gl.glVertex3d(size, size, 0);

		// Achterzijde muur
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, size);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, 0, size);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, 0, size);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, size);

		// Linkerzijde muur (onderzijde, hoeft niet per se)
		// gl.glTexCoord2d(0, 0);
		// gl.glVertex3d(0, 0, size);
		// gl.glTexCoord2d(1, 0);
		// gl.glVertex3d(0, 0, 0);
		// gl.glTexCoord2d(1, 1);
		// gl.glVertex3d(size, 0, 0);
		// gl.glTexCoord2d(0, 1);
		// gl.glVertex3d(size, 0, size);

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
		myTexture.disable();
	}

	private void drawRoof(GL gl, double size, Texture myTexture) {
		// Setting the floor color and material.
		float roofColour[] = { 1.0f, 1.0f, 1.0f, 0.0f }; // The floor is blue.
		// Set the materials used by the floor.
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, roofColour, 0);

		// Sets the current normal vector
		gl.glNormal3d(0, 1, 0);
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0, size, 0);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(size, size, 0);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(size, size, size);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0, size, size);
		gl.glEnd();
		myTexture.disable();
	}

	private void drawClosedDoor(GL gl, double size, Texture myTexture,
			Texture myTexture2, float[] keyCardHoleColor) {
		// set the colour for the wall
		float DoorColor[] = { 1.0f, 0.0f, 0.0f, 0f };
		// sets the material and lighting for the wall
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, DoorColor, 0);

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
		myTexture.disable();

		// Sleutel gat

		// DIT MOET WEG
		gl.glColor3f(keyCardHoleColor[0], keyCardHoleColor[1],
				keyCardHoleColor[2]);

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
		myTexture2.disable();

	}

	private void drawOpeningDoor(GL gl, double size, double opening,
			float[] keyCardHoleColor) {
		gl.glTranslated(0, opening, 0);
		drawClosedDoor(gl, SQUARE_SIZE,
				loadedTexturesMaze.getTexture("doorTexture"),
				loadedTexturesMaze.getTexture("doorBottomTexture"),
				keyCardHoleColor);
	}

	// ////////////////////////////////////////////////////////////////////////
	// //////////////////////////WORDT NIET MEER GEBRUIKT????//////////////////
	// ////////////////////////////////////////////////////////////////////////
	private void drawBars(GL gl, double size) {
		GLUT glut = new GLUT();
		float barColour[] = { 0.5f, 0.5f, 0.5f, 0.5f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, barColour, 0);
		glut.glutSolidCylinder(1, 1, 1, 1);
	}

	public void drawSpot(GL gl, double size, Texture myTexture) {
		float spotColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, spotColour, 0);
		GLUT glut = new GLUT();
		double lightRadius = 0.1;
		double lightSize = 0.2;

		// Licht weergeven
		float lightPosition[] = { (float) (size / 2),
				(float) (size - lightSize - lightSize), (float) (size / 2),
				1.0f };
		float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		float lightDirection[] = { 0.0f, -1.0f, 0.0f, 0.0f };

		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightColour, 0);
		// gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, (float) 30.0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, lightDirection, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

		// Vormgeving
		gl.glTranslated(size / 2, size, size / 2);
		gl.glRotated(90, 0, 1, 0);
		glut.glutSolidCylinder(lightRadius, lightSize, 20, 20);
		// glut.glutSolidCube((float)lightSize);

		// ////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////

	}
}
