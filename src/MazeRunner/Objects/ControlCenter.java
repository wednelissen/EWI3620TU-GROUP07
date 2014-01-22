package MazeRunner.Objects;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;

import MazeRunner.Fundamental.LoadTexturesMaze;

public class ControlCenter extends GameObject implements VisibleObject {

	Model modelTable;
	private double sizeX = 2; // geef hier de maten van de tafel in
	private double sizeZ = 1; // geef hier de maten van de tafel in
	private GLUT glut;
	private Texture myTexture;

	public ControlCenter(double xPosition, double yPosition, double zPosition) {
		super(xPosition * Maze.SQUARE_SIZE + (0.5 * Maze.SQUARE_SIZE),
				yPosition, zPosition * Maze.SQUARE_SIZE
						+ (0.5 * Maze.SQUARE_SIZE));
		modelTable = LoadTexturesMaze.getModel("modelTable");
		glut = new GLUT();
		myTexture = LoadTexturesMaze.getTexture("modelComputerScreen");
	}

	public double getSizeX() {
		return sizeX;
	}

	public double getSizeZ() {
		return sizeZ;
	}

	@Override
	public void display(GL gl) {
		float cubeColor[] = { 0f, 0f, 0f, 0f };
		gl.glDisable(GL.GL_CULL_FACE);// zorgt dat de achterkant zichtbaar is
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		// Draw tafel
		gl.glTranslated(locationX, 1.24, locationZ);
		gl.glScaled(2, 2, 2);
		drawComputer(gl);
		// Draw computer
		gl.glTranslated(0, -0.49, 0);
		modelTable.draw(gl, LoadTexturesMaze.getTexture("modelTable"));
		gl.glEnable(GL.GL_CULL_FACE);// zorgt dat de achterkant zichtbaar is
		gl.glPopMatrix();
	}

	private void drawComputer(GL gl) {
		// Onderkant computerkast
		float pcColor[] = { 1f, 1f, 1f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, pcColor, 0);
		gl.glBegin(GL.GL_QUADS);
		// onderzijde computer
		gl.glNormal3d(0, -1, 0);
		gl.glVertex3d(-0.5, 0, -0.4);
		gl.glVertex3d(0.5, 0, -0.4);
		gl.glVertex3d(0.5, 0, 0.4);
		gl.glVertex3d(-0.5, 0, 0.4);

		// bovenkant computerkast
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(-0.5, 0.15, -0.4);
		gl.glVertex3d(0.5, 0.15, -0.4);
		gl.glVertex3d(0.5, 0.15, 0.4);
		gl.glVertex3d(-0.5, 0.15, 0.4);

		// voorzijde
		gl.glNormal3d(0, 0, -1);
		gl.glVertex3d(-0.5, 0.0, -0.4);
		gl.glVertex3d(-0.5, 0.15, -0.4);
		gl.glVertex3d(0.5, 0.15, -0.4);
		gl.glVertex3d(0.5, 0.0, -0.4);

		// achterzijde
		gl.glNormal3d(0, 0, 1);
		gl.glVertex3d(-0.5, 0.0, 0.4);
		gl.glVertex3d(-0.5, 0.15, 0.4);
		gl.glVertex3d(0.5, 0.15, 0.4);
		gl.glVertex3d(0.5, 0.0, 0.4);

		gl.glNormal3d(-1, 0, 0);
		gl.glVertex3d(-0.5, 0.0, -0.4);
		gl.glVertex3d(-0.5, 0.0, 0.4);
		gl.glVertex3d(-0.5, 0.15, 0.4);
		gl.glVertex3d(-0.5, 0.15, -0.4);

		gl.glNormal3d(1, 0, 0);
		gl.glVertex3d(0.5, 0.0, -0.4);
		gl.glVertex3d(0.5, 0.0, 0.4);
		gl.glVertex3d(0.5, 0.15, 0.4);
		gl.glVertex3d(0.5, 0.15, -0.4);

		// beeldscherm
		float monitorColor[] = { 0f, 0f, 0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, monitorColor, 0);
		// onder
		gl.glNormal3d(0, -1, 0);
		gl.glVertex3d(-0.35, 0.15, -0.2);
		gl.glVertex3d(0.35, 0.15, -0.2);
		gl.glVertex3d(0.35, 0.15, 0);
		gl.glVertex3d(-0.35, 0.15, 0);

		// boven
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(-0.35, 0.6, -0.2);
		gl.glVertex3d(0.35, 0.6, -0.2);
		gl.glVertex3d(0.35, 0.6, 0);
		gl.glVertex3d(-0.35, 0.6, 0);

		// zijkant
		gl.glNormal3d(-1, 0, 0);
		gl.glVertex3d(-0.35, 0.15, -0.20);
		gl.glVertex3d(-0.35, 0.15, 0);
		gl.glVertex3d(-0.35, 0.6, 0);
		gl.glVertex3d(-0.35, 0.6, -0.20);

		// zijkant
		gl.glNormal3d(1, 0, 0);
		gl.glVertex3d(0.35, 0.15, -0.20);
		gl.glVertex3d(0.35, 0.15, 0);
		gl.glVertex3d(0.35, 0.6, 0);
		gl.glVertex3d(0.35, 0.6, -0.20);

		// voorzijde (bestaande uit 4 stukken die om het scherm (dat onderaan
		// staat) zitten
		gl.glNormal3d(0, 0, -1);
		gl.glVertex3d(-0.35, 0.15, -0.20);
		gl.glVertex3d(-0.35, 0.25, -0.20);
		gl.glVertex3d(0.35, 0.25, -0.20);
		gl.glVertex3d(0.35, 0.15, -0.20);

		gl.glVertex3d(-0.35, 0.50, -0.20);
		gl.glVertex3d(-0.35, 0.6, -0.20);
		gl.glVertex3d(0.35, 0.6, -0.20);
		gl.glVertex3d(0.35, 0.50, -0.20);

		gl.glVertex3d(-0.35, 0.24, -0.20);
		gl.glVertex3d(-0.35, 0.51, -0.20);
		gl.glVertex3d(-0.30, 0.51, -0.20);
		gl.glVertex3d(-0.30, 0.24, -0.20);

		gl.glVertex3d(0.35, 0.24, -0.20);
		gl.glVertex3d(0.35, 0.51, -0.20);
		gl.glVertex3d(0.30, 0.51, -0.20);
		gl.glVertex3d(0.30, 0.24, -0.20);

		// zijkanten bolle bolle stuk
		gl.glNormal3d(-1, 0, 0);
		gl.glVertex3d(-0.35, 0.15, 0);
		gl.glVertex3d(-0.35, 0.30, 0.3);
		gl.glVertex3d(-0.35, 0.45, 0.3);
		gl.glVertex3d(-0.35, 0.6, 0);

		gl.glNormal3d(1, 0, 0);
		gl.glVertex3d(0.35, 0.30, 0.3);
		gl.glVertex3d(0.35, 0.15, 0);
		gl.glVertex3d(0.35, 0.6, 0);
		gl.glVertex3d(0.35, 0.45, 0.3);

		// achterzijde
		gl.glNormal3d(0, 0, 1);
		gl.glVertex3d(-0.35, 0.30, 0.3);
		gl.glVertex3d(-0.35, 0.45, 0.3);
		gl.glVertex3d(0.35, 0.45, 0.3);
		gl.glVertex3d(0.35, 0.30, 0.3);

		// boven & onderzijde bolle stuk
		gl.glNormal3d(0, 0.832, 0.555);
		gl.glVertex3d(-0.35, 0.15, 0);
		gl.glVertex3d(0.35, 0.15, 0);
		gl.glVertex3d(0.35, 0.30, 0.3);
		gl.glVertex3d(-0.35, 0.30, 0.3);

		gl.glNormal3d(0, -0.832, -0.555);
		gl.glVertex3d(-0.35, 0.45, 0.3);
		gl.glVertex3d(0.35, 0.45, 0.3);
		gl.glVertex3d(0.35, 0.6, 0);
		gl.glVertex3d(-0.35, 0.6, 0);
		gl.glEnd();

		if (myTexture != null) {
			myTexture.enable();
			myTexture.bind();
		}
		float screenColor[] = { 1f, 1f, 1f, 1f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, screenColor, 0);
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 0, -1);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(-0.30, 0.24, -0.15);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(-0.30, 0.51, -0.15);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0.30, 0.51, -0.15);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0.30, 0.24, -0.15);
		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}
	}

}
