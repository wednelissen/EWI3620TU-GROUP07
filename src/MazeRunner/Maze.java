package MazeRunner;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import LevelEditor.LoadLevel;
import LevelEditor.Key;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

import LevelEditor.LoadLevel;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.ImageUtil;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

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
 * SQUARE_SIZE is used by both MazeRunner and Maze itself for calculations of the 
 * display(GL) method and other functions. The larger this value, the larger the world of
 * MazeRunner will be.
 * <p>
 * This class implements VisibleObject to force the developer to implement the display(GL)
 * method, so the Maze can be displayed.
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
	public final Point startPoint =  newMaze.getStartPosition();
	private LoadTexturesMaze loadedTexturesMaze;
	
	public Maze (LoadTexturesMaze temp){
		loadedTexturesMaze = temp;
	}

	private int[][] maze = newMaze.outputForMazeRunner();
	private boolean test = true;
	
	/**
	 * isWall(int x, int z) checks for a wall.
	 * <p>
	 * It returns whether maze[x][z] contains a 1.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean isWall( int x, int z )
	{	
		//DEBUG VAN Keys
//		if(test){
//			ArrayList<Key> temp = newMaze.getKeys();
//			for(int i=0; i<temp.size(); i++){	
//				System.out.println(temp.get(i));
//			}
//			test = false;
//		}
		if( x >= 0 && x < MAZE_SIZE_X && z >= 0 && z < MAZE_SIZE_Z )
			return maze[x][z] == 1;
		else
			return false;
	}

	/**
	 * isWall(double x, double z) checks for a wall by converting the double values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the grid 
	 * defined by maze[][]. Then it calls upon isWall(int, int) to check for a wall.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean isWall( double x, double z )
	{
		int gX = convertToGridX( x );
		int gZ = convertToGridZ( z );
		return isWall( gX, gZ );
	}
	 
	/**
	 * Converts the double x-coordinate to its correspondent integer coordinate.
	 * @param x		the double x-coordinate
	 * @return		the integer x-coordinate
	 */
	private int convertToGridX( double x )
	{
		return (int)Math.floor( x / SQUARE_SIZE );
	}

	/**
	 * Converts the double z-coordinate to its correspondent integer coordinate.
	 * @param z		the double z-coordinate
	 * @return		the integer z-coordinate
	 */
	private int convertToGridZ( double z )
	{
		return (int)Math.floor( z / SQUARE_SIZE );
	}
	
	public void display(GL gl) {

		for (int i = 0; i < MAZE_SIZE_X; i++) {
			for (int j = 0; j < MAZE_SIZE_Z; j++) {
				gl.glPushMatrix();
				gl.glTranslated(i * SQUARE_SIZE, 0, j * SQUARE_SIZE);
				if (isWall(i, j)) {
					drawWall(gl, SQUARE_SIZE, loadedTexturesMaze.getTexture("wallTexture"));
				}
				if (!isWall(i, j)) {
					drawFloor(gl, SQUARE_SIZE, loadedTexturesMaze.getTexture("floorTexture"));
					drawRoof(gl, SQUARE_SIZE, loadedTexturesMaze.getTexture("floorTexture"));
				}
				gl.glPopMatrix();
			}
		}
	}
	
	/**
	 * paintSingleFloorTile(GL, double) paints a single floor tile, to represent the floor of the entire maze.
	 * 
	 * @param gl	the GL context in which should be drawn
	 * @param size	the size of the tile
	 */
	private void drawFloor(GL gl, double size, Texture myTexture) {
		// Setting the floor color and material.
		float floorColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		// Sets the current normal vector
		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		// Set the materials used by the floor.
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, floorColor, 0);
		gl.glBegin(GL.GL_QUADS);
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
		myTexture.disable();
		gl.glDisable(GL.GL_COLOR_MATERIAL);
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

		// Bovenzijde muur ERROR
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
		myTexture.disable();
		gl.glDisable(GL.GL_COLOR_MATERIAL);
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
		myTexture.disable();
		gl.glDisable(GL.GL_COLOR_MATERIAL);
	}
}
