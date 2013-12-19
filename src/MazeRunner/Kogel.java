package MazeRunner;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Kogel extends GameObject implements VisibleObject {

	private final static double SQUARE_SIZE = 5;
	private final static double speed = 0.00007;
	private double horAngle;
	private double verAngle;
	private double deltaTime;

	public Kogel(double x, double y, double z) {
		super(x, y, z);
	}

	@Override
	public void display(GL gl) {
		locationX -= Math.sin(Math.toRadians(horAngle)) * speed * deltaTime;
		locationZ -= Math.cos(Math.toRadians(horAngle)) * speed * deltaTime;
		locationY += Math.tan(Math.toRadians(verAngle)) * speed * deltaTime;

		GLUT glut = new GLUT();
		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		gl.glTranslated(locationX, locationY, locationZ);
		glut.glutSolidCube((float) SQUARE_SIZE / 14);
		gl.glPopMatrix();

	}

	public void updateShoot(double hor, double ver, int deltatime) {
		horAngle = hor;
		verAngle = ver;
		deltaTime = deltatime;

	}
}
