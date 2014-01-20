package MazeRunner.Objects;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class FinishPoint extends GameObject implements VisibleObject {

	private static final double SQUARE_SIZE = 5;
	private float rotate;
	private float deltaTime;
	private float rotateSpeed = 0.1f;

	public FinishPoint(double xFinish, double yFinish, double zFinish) {
		super(xFinish * SQUARE_SIZE + (0.5 * SQUARE_SIZE), yFinish, zFinish
				* SQUARE_SIZE + (0.5 * SQUARE_SIZE));
		rotate = 0;
		deltaTime = 0;

	}

	@Override
	public void display(GL gl) {
		GLUT glut = new GLUT();

		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		gl.glRotatef(rotate, 0, 1, 0);
		gl.glTranslated(locationX, 4, locationZ);

		gl.glDisable(GL.GL_CULL_FACE);// zorgt dat de achterkant zichtbaar is
		glut.glutSolidCube(2);
		gl.glPopMatrix();
		rotate += rotateSpeed * deltaTime;
		if (rotate > 360) {
			rotate -= 360;
		}

		gl.glEnable(GL.GL_CULL_FACE); // zet de instellingen weer terug zoals ze
										// stonden

	}

}
