package MazeRunner;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;

public class Spotlight extends GameObject implements VisibleObject {

	private Texture spotTexture;
	private int spotNumber;

	public Spotlight(GL gl, double size, Texture myTexture, double xCoord,
			double yCoord, double zCoord, int number) {
		super((xCoord * size) + (size / 2), yCoord, (zCoord * size)
				+ (size / 2));
		spotTexture = myTexture;
		spotNumber = number;
		initLights(gl);
	}

	@Override
	public void display(GL gl) {
		// Updating location of the lights
		float updatedLightPosition[] = { (float) this.locationX,
				(float) this.locationY, (float) this.locationZ, 1.0f };
		float updatedLightDirection[] = { 0.0f, -1.0f, 0.0f, 0.0f };
		
		gl.glLightfv(GL.GL_LIGHT0 + spotNumber, GL.GL_POSITION, updatedLightPosition, 0);
		gl.glLightfv(GL.GL_LIGHT0 + spotNumber, GL.GL_SPOT_DIRECTION,
				updatedLightDirection, 0);

		GLUT glut = new GLUT();

		double lightBulbRadius = 0.1;
		double lightBulbSize = 0.2;

		float spotColor[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, spotColor, 0);
		// Vormgeving
		gl.glPushMatrix();
		gl.glTranslated(locationX, locationY - 1, locationZ);
		gl.glRotated(90, 0, 1, 0);
		glut.glutSolidCylinder(lightBulbRadius, lightBulbSize, 20, 20);
		gl.glPopMatrix();
	}

	public void initLights(GL gl) {

		float lightPosition[] = { (float) locationX, (float) (locationY),
				(float) locationZ, 1.0f };
		float lightColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float lightDirection[] = { 0.0f, -1.0f, 0.0f, 0.0f };

		System.out.println("Switch activatie: " + spotNumber);
		// Licht weergeven
		gl.glLightfv(GL.GL_LIGHT0 + spotNumber, GL.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL.GL_LIGHT0 + spotNumber, GL.GL_DIFFUSE, lightColor, 0);
		gl.glLightfv(GL.GL_LIGHT0 + spotNumber, GL.GL_SPOT_DIRECTION, lightDirection, 0);
		gl.glLightf(GL.GL_LIGHT0 + spotNumber, GL.GL_SPOT_CUTOFF, 50);
		
		gl.glEnable(GL.GL_LIGHT0 + spotNumber);
		gl.glEnable(GL.GL_LIGHTING);
	}
}
