package MazeRunner;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Guard extends GameObject implements VisibleObject {

	private ArrayList<Point> coordinaten;

	public final double MAZE_SIZE = 10;
	public final static double SQUARE_SIZE = 5;
	private double speed;
	private boolean richting = true;
	private Point startpositie;
	private Point eindpositie;
	private Point huidigepositie;
	private Point finishpositie;
	private int teller = 1;

	private boolean canMoveForward;

	public Guard(double x, double y, double z, ArrayList<Point> points) {
		super(x * SQUARE_SIZE, y, z * SQUARE_SIZE);
		speed = 0.01;
		coordinaten = points;
		startpositie = coordinaten.get(0);
		finishpositie = coordinaten.get(coordinaten.size() - 1);

	}

	public void setCanMoveForward(boolean canMoveForward) {
		this.canMoveForward = canMoveForward;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void update(int deltaTime) {
		huidigepositie();
		if (huidigepositie.equals(finishpositie)) {
			richting = false;
		} else if (huidigepositie.equals(startpositie)) {
			richting = true;
		}

		if (richting) {

			eindpositie = coordinaten.get(teller);

			if (!(eindpositie.equals(huidigepositie))) {
				int diffX = (int) (eindpositie.getX() - huidigepositie.getX());
				int diffZ = (int) (eindpositie.getY() - huidigepositie.getY());

				if (diffX > 0) {
					locationX += speed * deltaTime;

				} else if (diffX < 0) {
					locationX -= speed * deltaTime;
				} else if (diffZ > 0) {
					locationZ += speed * deltaTime;
				} else if (diffZ < 0) {
					locationZ -= speed * deltaTime;
				} else {
					System.out.println("Fucking grote error biatch");
				}
			} else if (huidigepositie.equals(eindpositie)) {
				teller++;
			}
		} else if (!richting) {

			eindpositie = coordinaten.get(teller);

			if (!(eindpositie.equals(huidigepositie))) {
				int diffX = (int) (eindpositie.getX() - huidigepositie.getX());
				int diffZ = (int) (eindpositie.getY() - huidigepositie.getY());
				System.out.println(huidigepositie);
				// System.out.println(diffZ);
				// System.out.println(diffX);
				if (diffX > 0) {
					locationX += speed * deltaTime;

				} else if (diffX < 0) {
					locationX -= speed * deltaTime;
				} else if (diffZ > 0) {
					locationZ += speed * deltaTime;
				} else if (diffZ < 0) {
					locationZ -= speed * deltaTime;
				} else {
					System.out.println("Fucking grote error biatch");
				}
			} else if (huidigepositie.equals(eindpositie)) {
				teller--;
			}
		}

	}

	public void huidigepositie() {
		int x = (int) Math.floor(locationX / SQUARE_SIZE);
		int z = (int) Math.floor(locationZ / SQUARE_SIZE);

		huidigepositie = new Point(x, z);

	}

	public void display(GL gl) {
		GLUT glut = new GLUT();

		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		gl.glTranslated(locationX - (SQUARE_SIZE / 2), SQUARE_SIZE / 4,
				locationZ - (SQUARE_SIZE / 2));
		glut.glutSolidCube((float) SQUARE_SIZE / 2);
		gl.glPopMatrix();

	}

}
