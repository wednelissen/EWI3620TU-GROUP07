package MazeRunner;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

/**
 * Deze klasse maakt een guard object aan, deze heeft een functie voor
 * patrouille en gebruikt het A* algoritme voor het lopen naar een kortste
 * locatie waar een player is gespot door een camera. Verder valt een guard de
 * player aan en heeft hij health.
 * 
 * @author Stijn
 * 
 */

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
	private boolean zplusPrev = false;
	private boolean zminPrev = false;
	private boolean xplusPrev = false;
	private boolean xminPrev = false;
	private boolean attack = false;
	private double horAngle = 0;
	private double startAngle;
	private boolean startCheck = true;
	private boolean finishCheck = false;

	private boolean canMoveForward;
	private boolean canMoveLeft;
	private boolean canMoveRight;
	private boolean canMoveBack;
	private boolean rightForward;
	private boolean leftForward;
	
	private Model modelGuard;

	public Guard(double x, double y, double z, ArrayList<Point> points) {
//		super((x * SQUARE_SIZE) + (2 * SQUARE_SIZE), y, (z * SQUARE_SIZE)
//				- (SQUARE_SIZE / 2));
		

		//PROBEERSEL VAN MENNO
		super(x * SQUARE_SIZE+(0.5*SQUARE_SIZE), y, z * SQUARE_SIZE+(0.5*SQUARE_SIZE));
		
		try {
			modelGuard = OBJLoader.loadModel("src/modelGuard.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}

		speed = 0.005;
		coordinaten = points;

		startpositie = coordinaten.get(0);
		finishpositie = coordinaten.get(coordinaten.size() - 1);
		huidigepositie = startpositie;
		startHoek();
	}

	private void startHoek() {
		Point secondPosition = coordinaten.get(1);
		int diffX = (int) (secondPosition.getX() - startpositie.getX());
		int diffZ = (int) (secondPosition.getY() - startpositie.getY());
		if (diffX > 0) {
			startAngle = 90;
			xplusPrev = true;
		} else if (diffX < 0) {
			startAngle = -90;
			xminPrev = true;
		} else if (diffZ > 0) {
			startAngle = 0;
			zplusPrev = true;
			System.out.println("zplusPrev " + zplusPrev);
		} else if (diffZ < 0) {
			startAngle = 180;
			zminPrev = true;
		}
	}

	public void setCanMoveForward(boolean b) {
		this.canMoveForward = b;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Deze functie laat de guard patrouilleren over een route die meegegeven
	 * wordt uit de leveleditor.
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {
		huidigepositie();
		if (huidigepositie.equals(finishpositie) && !finishCheck) {
			richting = false;
			startAngle += 180;
			startCheck = false;
			finishCheck = true;
		} else if (huidigepositie.equals(startpositie) && !startCheck) {
			richting = true;
			startCheck = true;
			finishCheck = false;
			startAngle -= 180;
		}
		if (attack == false) {
			if (richting) {

				eindpositie = coordinaten.get(teller);

				if (!(eindpositie.equals(huidigepositie))) {
					int diffX = (int) (eindpositie.getX() - huidigepositie
							.getX());
					int diffZ = (int) (eindpositie.getY() - huidigepositie
							.getY());

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

					richtingDraaier();

				} else if (huidigepositie.equals(eindpositie)) {
					teller++;
				}
			} else if (!richting) {

				eindpositie = coordinaten.get(teller);

				if (!(eindpositie.equals(huidigepositie))) {
					int diffX = (int) (eindpositie.getX() - huidigepositie
							.getX());
					int diffZ = (int) (eindpositie.getY() - huidigepositie
							.getY());

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
						xmin = true;

					} else if (diffX < 0) {
						locationX -= speed * deltaTime;
						xplus = true;
					} else if (diffZ > 0) {
						locationZ += speed * deltaTime;
						zmin = true;
					} else if (diffZ < 0) {
						locationZ -= speed * deltaTime;
						zplus = true;
					} else {
						System.out.println("Fucking grote error biatch");
					}

					richtingDraaier();
				} else if (huidigepositie.equals(eindpositie)) {
					teller--;
				}
			}
		}

	}

	/**
	 * Deze methode controleert de patrouillerichting aan de hand daarvan de
	 * angle van de guard.
	 */

	private void richtingDraaier() {
		if (zplus != zplusPrev) {
			if (xmin) {
				horAngle = horAngle - 90;
				xminPrev = true;
			}
			if (xplus) {
				horAngle = horAngle + 90;
				xplusPrev = true;
			}
			zplusPrev = false;
		}
		if (zmin != zminPrev) {
			if (xmin) {
				horAngle = horAngle + 90;
				xminPrev = true;
			}
			if (xplus) {
				horAngle = horAngle - 90;
				xplusPrev = true;
			}
			zminPrev = false;
		}
		if (xmin != xminPrev) {
			if (zmin) {
				horAngle = horAngle - 90;
				zminPrev = true;
			}
			if (zplus) {
				horAngle = horAngle + 90;
				zplusPrev = true;
			}
			xminPrev = false;
		}
		if (xplus != xplusPrev) {
			if (zmin) {
				horAngle = horAngle + 90;
				zminPrev = true;
			}
			if (zplus) {
				horAngle = horAngle - 90;
				zplusPrev = true;
			}
			xplusPrev = false;
		}
	}

	/**
	 * Geeft de huidige positie van het object Guard.
	 */
	public void huidigepositie() {
		
//		int x = (int) Math.floor(locationX / SQUARE_SIZE);
//		int z = (int) Math.floor(locationZ / SQUARE_SIZE);
		
		//PROBEERSEL VAN MENNO
//		int x = (int) Math.floor((locationX-(SQUARE_SIZE/2)) / SQUARE_SIZE);
//		int z = (int) Math.floor((locationZ-(SQUARE_SIZE/2)) / SQUARE_SIZE);
		
		//ANDER PROBEERSEL
		
		int xTemp = (int) Math.floor(locationX / SQUARE_SIZE);
		int zTemp = (int) Math.floor(locationZ / SQUARE_SIZE);
		
		double diffx = Math.abs(xTemp*SQUARE_SIZE+0.5*SQUARE_SIZE - locationX);
		double diffz = Math.abs(zTemp*SQUARE_SIZE+0.5*SQUARE_SIZE - locationZ);
		
		if(diffx<0.1 && diffz<0.1){
			huidigepositie = new Point(xTemp, zTemp);
		}
		

	//	huidigepositie = new Point(x, z);
		//System.out.println(huidigepositie);

	}

	/**
	 * Display het object dat de guard is
	 */
	public void display(GL gl) {
		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();

//		gl.glTranslated(locationX - (SQUARE_SIZE / 2), SQUARE_SIZE / 4,
//				locationZ + (SQUARE_SIZE / 2));
		
		//PROBEERSEL VAN MENNO
		gl.glTranslated(locationX, SQUARE_SIZE/4, locationZ);
		
		
		gl.glRotatef((float) (startAngle + horAngle), 0f, 1f, 0f);
		gl.glScaled(0.60, 0.60, 0.60);
		
		gl.glDisable(GL.GL_CULL_FACE);//zorgt dat de achterkant zichtbaar is
		modelGuard.draw(gl);
		gl.glPopMatrix();
	}

	/**
	 * Als een speler binnen een bepaalde afstand van de guard komt wordt hij
	 * gespot en gaat er een boolean op true.
	 * 
	 * @param x
	 * @param z
	 */
	public void playerDetectie(double x, double z) {
		huidigepositie();
		int xx = (int) Math.floor(x / SQUARE_SIZE);
		int zz = (int) Math.floor(z / SQUARE_SIZE);

		Point playerpos = new Point((int) xx, (int) zz);

		if (xplus) {
			if ((int) zz == huidigepositie.getY()) {
				if (xx > huidigepositie.getX()
						&& xx < huidigepositie.getX() + 7) {
					this.attack = true;

				}
			}
		} else if (xmin) {
			if ((int) zz == huidigepositie.getY()) {
				if (xx < huidigepositie.getX()
						&& xx > huidigepositie.getX() - 7) {
					this.attack = true;

				}
			}
		} else if (zplus) {
			if ((int) xx == huidigepositie.getX()) {
				if (zz > huidigepositie.getY()
						&& zz < huidigepositie.getY() + 7) {
					this.attack = true;

				}
			}
		} else if (zmin) {
			if ((int) xx == huidigepositie.getX()) {
				if (zz < huidigepositie.getY()
						&& zz > huidigepositie.getY() - 7) {
					this.attack = true;

				}
			}
		}

	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
	}

	/**
	 * Loopt naar de positie van de player als attack = true
	 * 
	 * @param x
	 * @param z
	 * @param deltatime
	 */
	public void aanvallen(double x, double z, int deltatime) {
		huidigepositie();

		if (attack == true) {

			double diffX = x - locationX;
			double diffZ = z - locationZ;
			horAngle = -Math.toDegrees(Math.atan2(diffZ, diffX)) + 90;

			if (diffX > 0 && diffZ > 0) {
				locationX -= Math.cos(horAngle) * speed * deltatime;
				locationZ += Math.sin(horAngle) * speed * deltatime;
			}
			if (diffX < 0 && diffZ > 0) {
				locationX -= Math.cos(horAngle) * speed * deltatime;
				locationZ += Math.sin(horAngle) * speed * deltatime;
			}
			if (diffX > 0 && diffZ < 0) {
				locationX += Math.cos(horAngle) * speed * deltatime;
				locationZ -= Math.sin(horAngle) * speed * deltatime;
			}
			if (diffX < 0 && diffZ < 0) {
				locationX += Math.cos(horAngle) * speed * deltatime;
				locationZ -= Math.sin(horAngle) * speed * deltatime;
			}
			if (diffX < 0.1 && diffZ < 0.1) {
				System.out.println("busted!!!");
			}
		}

	}

	public void setCanMoveRight(boolean b) {
		this.canMoveRight = b;
	}

	public void setCanMoveLeft(boolean b) {
		this.canMoveLeft = b;

	}

	public void setCanMoveBack(boolean b) {
		this.canMoveBack = b;

	}

	public void setLeftForwardWall(boolean b) {
		this.leftForward = true;
	}

	public void setRightForwardWall(boolean b) {
		this.rightForward = true;
	}

}
