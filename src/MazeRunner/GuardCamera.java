package MazeRunner;

import java.awt.Point;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

public class GuardCamera extends GameObject implements VisibleObject {

	private Point positie = new Point(60, 60);
	private boolean status = true;
	public final double MAZE_SIZE = 10;
	public final double SQUARE_SIZE = 5;
	double playerLocatieX;
	double playerLocatieZ;
	double deltatime;

	private final int timeOn = 12000;

	public GuardCamera(double x, double y, double z, double playerx,
			double playerz, double time) {
		super(x, y, z);
		deltatime = time;
		// alarm();
		updatePositie(playerx, playerz);

	}

	public void alarm() {
		if ((playerLocatieX < positie.getX() + SQUARE_SIZE && playerLocatieX > positie
				.getX() - SQUARE_SIZE)
				|| (playerLocatieZ < positie.getY() + SQUARE_SIZE && playerLocatieZ > positie
						.getY() - SQUARE_SIZE)) {

			System.out.println("To close");

			status = false;
		} else {
			status = true;
		}

	}

	public void display(final GL gl) {
		GLU glu = new GLU();
		final GLUT glut = new GLUT();

		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		gl.glTranslated(positie.getX() / SQUARE_SIZE, SQUARE_SIZE,
				positie.getY() / SQUARE_SIZE);
		glut.glutSolidCube((float) SQUARE_SIZE / 12);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(positie.getX() / SQUARE_SIZE, -SQUARE_SIZE * 3,
				positie.getY() / SQUARE_SIZE);
		gl.glColor3f(1, 0, 0);
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(0, 0, 1, 0);
		gl.glRotatef(0, 0, 0, 1);
		glut.glutWireCone(SQUARE_SIZE * 2, SQUARE_SIZE * 4, 50, 50);
		gl.glPopMatrix();

	}

	public void updatePositie(double x, double z) {
		playerLocatieX = x;
		playerLocatieZ = z;

	}

}
