package MazeRunner.Objects;

import javax.media.opengl.GL;

import MazeRunner.Fundamental.LoadTexturesMaze;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;



public class FinishPoint extends GameObject implements VisibleObject {

	private static final double SQUARE_SIZE = 5;
	private GLUT glut = new GLUT();
	private Texture myTexture;
	public FinishPoint(double xFinish, double yFinish, double zFinish) {
		super(xFinish * SQUARE_SIZE + (0.5 * SQUARE_SIZE), yFinish, zFinish
				* SQUARE_SIZE + (0.5 * SQUARE_SIZE));
		myTexture = LoadTexturesMaze.getTexture("mazeFlag");
	}

	@Override
	public void display(GL gl) {
		gl.glDisable(GL.GL_CULL_FACE);// zorgt dat de achterkant zichtbaar is

		float cubeColor[] = { 0f, 1f, 0f, 0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		// Draw paal
		gl.glTranslated(locationX, 0, locationZ);
		gl.glRotatef(-90, 1, 0, 0);
		glut.glutSolidCylinder(0.1, 3, 40, 10);
		gl.glRotatef(90, 1, 0, 0);
		
		// Draw vlag
		float flagColor[] = { 1.0f, 1.0f, 1.0f, 0f };
		if (myTexture != null) {
			// Eerst de texture aanzetten
			myTexture.enable();
			// Dan de texture binden aan het volgende object
			myTexture.bind();
		}
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, flagColor, 0);
		gl.glBegin(GL.GL_QUADS);
		// Voorzijde muur ERROR
		gl.glNormal3d(0, 0, -1);
		// Eerste texture coordinaat
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0, 2, 0);
		// Tweede texture coordinaat
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0, 3, 0);
		// Derde texture coordinaat
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0 + 2, 3, 0);
		// Vierde texture coordinaat
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0 + 2, 2, 0);
		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}
		gl.glPopMatrix();
		gl.glEnable(GL.GL_CULL_FACE); // zet de instellingen weer terug zoals ze
										// stonden

	}

}
