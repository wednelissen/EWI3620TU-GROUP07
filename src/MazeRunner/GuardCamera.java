package MazeRunner;

import java.awt.Point;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

public class GuardCamera extends GameObject implements VisibleObject {

	private Point positie = new Point(60, 60);
	private boolean status = true;
	public final double MAZE_SIZE = 10;
	public final static double SQUARE_SIZE = 5;
	double playerLocatieX;
	double playerLocatieZ;
	double deltatime;
	private Point huidigepositie;
	private Point playerPositie;
	private ThreadLoop thread = new ThreadLoop();

	public GuardCamera(double x, double y, double z, double playerx,
			double playerz, double time) {
		super((x * SQUARE_SIZE) + (SQUARE_SIZE / 2), y, (z * SQUARE_SIZE)
				+ (SQUARE_SIZE / 2));
		deltatime = time;

		updatePositie(playerx, playerz);
		huidigepositie();
		alarm();
		thread.start();
	}

	public void alarm() {
		// if ((playerLocatieX < locationX + SQUARE_SIZE && playerLocatieX >
		// locationX
		// - SQUARE_SIZE)
		// || (playerLocatieZ < positie.getY() + SQUARE_SIZE && playerLocatieZ >
		// positie
		// .getY() - SQUARE_SIZE)) {
		if (playerPositie.equals(huidigepositie)) {
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
		gl.glTranslated(locationX, SQUARE_SIZE, locationZ);
		glut.glutSolidCube((float) SQUARE_SIZE / 12);
		gl.glPopMatrix();

		if (thread.visible) {
			gl.glPushMatrix();
			gl.glTranslated(locationX, -SQUARE_SIZE * 3, locationZ);
			gl.glColor3f(1, 0, 0);
			gl.glRotatef(-90, 1, 0, 0);
			glut.glutWireCone(SQUARE_SIZE * 2, SQUARE_SIZE * 4, 50, 50);
			gl.glPopMatrix();
		}

	}

	public void updatePositie(double x, double z) {
		int xx = (int) Math.floor(x / SQUARE_SIZE);
		int zz = (int) Math.floor(z / SQUARE_SIZE);

		playerPositie = new Point(xx, zz);
	}

	public void huidigepositie() {
		int x = (int) Math.floor(locationX / SQUARE_SIZE);
		int z = (int) Math.floor(locationZ / SQUARE_SIZE);

		huidigepositie = new Point(x, z);
		System.out.println(huidigepositie);

	}
}
