package MazeRunner.Opponents;

import java.awt.Point;
import java.util.Calendar;

import javax.media.opengl.GL;

import MazeRunner.Fundamental.MazeRunner;
import MazeRunner.Objects.GameObject;
import MazeRunner.Objects.VisibleObject;

import com.sun.opengl.util.GLUT;

/**
 * Deze klasse maakt een bewakingscameraoject aan. Deze gaat om de zoveel
 * seconden aan en uit en vormt dus een obstakel. Mocht de speler gezien worden
 * door de camera dan alarmeert dit guards binnen een bepaalde radius die dan
 * naar dat punt toe komen.
 * 
 * @author Stijn
 * 
 */
public class GuardCamera extends GameObject implements VisibleObject {

	private boolean alarm = false;
	private boolean needGuardSend = false;
	public final double MAZE_SIZE = 10;
	public final static double SQUARE_SIZE = 5;
	double playerLocatieX;
	double playerLocatieZ;
	private Point huidigepositie;
	private long alarmOnTime;
	private long currentTime;
	private long resetTime = 60; // in seconden wordt het alarm automatisch uit gezet.

	public Point getHuidigepositie() {
		return huidigepositie;
	}

	private Point playerPositie;
	private ThreadLoop thread = new ThreadLoop();
	private MazeRunner mazerunner;

	public GuardCamera(double x, int y, double z, MazeRunner mazerunner) {
		super((x * SQUARE_SIZE) + (SQUARE_SIZE / 2), y, (z * SQUARE_SIZE)
				+ (SQUARE_SIZE / 2));
		this.mazerunner = mazerunner;
		huidigepositie();

		thread.start();
	}

	/**
	 * De functie die registreert of de speler gezien wordt door de camera
	 * wanneer deze aan staat.
	 */
	public boolean alarm() {
		if (thread.visible && playerPositie.equals(huidigepositie) && !alarm) {
			System.out.println("Camera ALARM");
			alarm = true;
			alarmOnTime = Calendar.getInstance().getTimeInMillis();
			thread.setAlarmOn();
			mazerunner.updateHighScoreCamera();
			return true;
		}
		return false;
	}

	public void resetAlarm() {
		alarm = false;

		thread.setAlarmOff();
	}

	public boolean getNeedGuard() {
		return needGuardSend;
	}

	public void setNeedGuard(boolean bool) {
		this.needGuardSend = bool;
	}

	/**
	 * Dit weergeeft de camera in het spel om de zoveel seconden
	 */
	public void display(final GL gl) {
		if(alarm){
			currentTime = Calendar.getInstance().getTimeInMillis();
			if(currentTime - alarmOnTime > resetTime*1000){ //na 30 seconden gaat het alarm uit.
				resetAlarm();
				setNeedGuard(false);
			}
		}
		GLUT glut = new GLUT();

		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();
		gl.glTranslated(locationX, SQUARE_SIZE, locationZ);
		glut.glutSolidCube((float) SQUARE_SIZE / 12);
		gl.glPopMatrix();

		if (thread.visible) {
			gl.glPushMatrix();
			gl.glTranslated(locationX, -SQUARE_SIZE * 3, locationZ);
			
			gl.glRotatef(-90, 1, 0, 0);
			glut.glutWireCone(SQUARE_SIZE * 2, SQUARE_SIZE * 4, 50, 50);
			gl.glPopMatrix();
		}
	}

	/**
	 * De positie van de speler wordt geupdate
	 * 
	 * @param x
	 * @param z
	 */
	public void updatePositie(double x, double z) {
		int xx = (int) Math.floor(x / SQUARE_SIZE);
		int zz = (int) Math.floor(z / SQUARE_SIZE);

		playerPositie = new Point(xx, zz);
	}

	/**
	 * De positie van de camera.
	 */
	public void huidigepositie() {
		int x = (int) Math.floor(locationX / SQUARE_SIZE);
		int z = (int) Math.floor(locationZ / SQUARE_SIZE);

		huidigepositie = new Point(x, z);
		System.out.println(huidigepositie);

	}
	
	public Point getPositie(){
		return huidigepositie;
	}
}