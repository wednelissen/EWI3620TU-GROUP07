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
	private boolean zplus = false;
	private boolean zmin = false;
	private boolean xplus = false;
	private boolean xmin = false;

	/*
	 * Elke guard moet een kijkrichting hebben, dat is de kant waarop hij
	 * beweegt, als de speler in de line of sight is dan reageert de gaurd door
	 * op de speler af te lopen. Is de guard binnen een bepaalde regio dan gaat
	 * om de zoveel tijd leven eraf. (timed loops!) Als er een alarm afgaat dan
	 * minpunten, een gaurd gealarmeerd, ook minpunten. Als alarm afgaat, binnen
	 * een bepaalde regio reageren guards door een kortste pad te vinden van
	 * waar ze zijn naar het alarm. Difficulty kan radius zijn waarop gaurds
	 * reageren. Gaurds hebben health en zijn ook dood te schieten.
	 * 
	 * Over de kijkrichting, guards kunnen niet door een muur heenkijken, en
	 * zien maar een bepaalde afstand(zeg aantal SQUARE_SIZES) en de breedte van
	 * een gang.
	 */

	private boolean canMoveForward;

	public Guard(double x, double y, double z, ArrayList<Point> points) {
		super((x * SQUARE_SIZE) + (SQUARE_SIZE), y, (z * SQUARE_SIZE)
				+ (SQUARE_SIZE));
		speed = 0.005;
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

				if (diffX == 0) {
					xmin = false;
					xplus = false;

				}
				if (diffZ == 0) {
					zmin = false;
					zplus = false;
				}
				if (diffX > 0) {
					locationX += speed * deltaTime;
					xplus = true;
				} else if (diffX < 0) {
					locationX -= speed * deltaTime;
					xmin = true;
				} else if (diffZ > 0) {
					locationZ += speed * deltaTime;
					zplus = true;
				} else if (diffZ < 0) {
					locationZ -= speed * deltaTime;
					zmin = true;
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
				locationZ + (SQUARE_SIZE / 2));
		glut.glutSolidCube((float) SQUARE_SIZE / 2);
		gl.glPopMatrix();

	}

	public void playerDetectie(double x, double z) {
		huidigepositie();
		int xx = (int) Math.floor(x / SQUARE_SIZE);
		int zz = (int) Math.floor(z / SQUARE_SIZE);

		Point playerpos = new Point((int) xx, (int) zz);

		if (xplus) {
			if ((int) zz == huidigepositie.getY()) {
				if (xx > huidigepositie.getX()
						&& xx < huidigepositie.getX() + 3) {
					System.out.println("Spotted!");
				}
			}
		} else if (xmin) {
			if ((int) zz == huidigepositie.getY()) {
				if (xx < huidigepositie.getX()
						&& xx > huidigepositie.getX() - 3) {
					System.out.println("Spotted!");
				}
			}
		} else if (zplus) {
			if ((int) xx == huidigepositie.getX()) {
				if (zz > huidigepositie.getY()
						&& zz < huidigepositie.getY() + 7) {
					System.out.println("Spotted!");
				}
			}
		} else if (zmin) {
			if ((int) xx == huidigepositie.getX()) {
				if (zz < huidigepositie.getY()
						&& zz > huidigepositie.getY() - 3) {
					System.out.println("Spotted!");
				}
			}
		}

	}
}
