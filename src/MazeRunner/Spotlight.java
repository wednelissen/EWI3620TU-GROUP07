package MazeRunner;

import java.awt.Point;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;

public class Spotlight implements VisibleObject{
	
	private double size;
	private Texture spotTexture;
	private Point coord;

	public Spotlight(double squareSize, Texture myTexture, Point coordinates)  {
		size = squareSize;
		spotTexture = myTexture;
		coord = coordinates;
	}

	@Override
	public void display(GL gl) {
		float spotColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, spotColour, 0);

		GLUT glut = new GLUT();
		double lightRadius = 0.1;
		double lightSize = 0.2;

		float lightPosition[] = { (float) (size / 2),
				(float) (size - lightSize - lightSize), (float) (size / 2),
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
		gl.glTranslated(size / 2, size, size / 2);
		gl.glRotated(90, 0, 1, 0);
		glut.glutSolidCylinder(lightRadius, lightSize, 20, 20);
		// glut.glutSolidCube((float)lightSize);
		gl.glPopMatrix();
	}
}
