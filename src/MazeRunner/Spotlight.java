package MazeRunner;

import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;

public class Spotlight extends GameObject implements VisibleObject{
	
	private Texture spotTexture;

	public Spotlight(double size, Texture myTexture, double xCoord, double yCoord, double zCoord) {
		super((xCoord * size) + (size / 2), yCoord, (zCoord * size)
				+ (size / 2));
		spotTexture = myTexture;
	}

	@Override
	public void display(GL gl) {
		GLUT glut = new GLUT();
		
		float spotColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, spotColour, 0);

		double lightBulbRadius = 0.1;
		double lightBulbSize = 0.2;

		float lightPosition[] = { (float) locationX,
				(float) (locationY - lightBulbSize - lightBulbSize), (float) locationZ,
				1.0f };
		float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		float lightDirection[] = { 0.0f, -1.0f, 0.0f, 0.0f };

		// Licht weergeven
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightColour, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, lightDirection, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

		// Vormgeving
		gl.glPushMatrix();
		gl.glTranslated(locationX, locationY, locationZ);
		gl.glRotated(90, 0, 1, 0);
		glut.glutSolidCylinder(lightBulbRadius, lightBulbSize, 20, 20);
		gl.glPopMatrix();
	}
}
