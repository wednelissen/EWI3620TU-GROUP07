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
	public Maze maze;
	private double speed;
	private boolean richting = true;
	private Point startpositie;
	private Point eindpositie;
	private Point huidigepositie;
	private Point finishpositie;
	private int teller = 1;
	private boolean zplus = false;	//loopt naar beneden
	private boolean zmin = false;	//loopt naar boven
	private boolean xplus = false; 	//loopt naar rechts
	private boolean xmin = false;	// loopt naar links
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
	
	private Model modelGuard;

	private boolean overRuleLeft;
	private boolean overRuleRight;
	private int deltaTimeSum = 0;

	public Guard(double x, double y, double z, ArrayList<Point> points) {
		super(x * SQUARE_SIZE+(0.5*SQUARE_SIZE), y, z * SQUARE_SIZE+(0.5*SQUARE_SIZE));
		modelGuard = LoadTexturesMaze.getModel("modelGuard");
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
			resetRichtingDraaier(false);
		} else if (huidigepositie.equals(startpositie) && !startCheck) {
			richting = true;
			startCheck = true;
			finishCheck = false;
			startAngle -= 180;
			resetRichtingDraaier(true);
		}
		if (attack == false) {
			if (richting) {

				eindpositie = coordinaten.get(teller);

				if (!(eindpositie.equals(huidigepositie))) {
					int diffX = (int) (eindpositie.getX() - huidigepositie
							.getX());
					int diffZ = (int) (eindpositie.getY() - huidigepositie
							.getY());

					resetWalkingDirection();
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

					resetWalkingDirection();

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
					teller--;
				}
			}
		}

	}

	private void resetWalkingDirection() {
			xmin = false;
			xplus = false;
			zmin = false;
			zplus = false;	
	}


	/**
	 * afhankelijk of je van het begin of het einde komt zorgt deze functie dat de richting waar je vandaag komt wanneer
	 * de guard zich omdraaid, goed wordt geset.
	 * @param fromBegin
	 */
	private void resetRichtingDraaier(boolean fromBegin){
		int diffX;
		int diffZ;
		
		xplusPrev = false;
		xminPrev = false;
		zplusPrev = false;
		zminPrev = false;
		
		if(fromBegin){
			Point secondPosition = coordinaten.get(1);
			diffX = (int) (secondPosition.getX() - startpositie.getX());
			diffZ = (int) (secondPosition.getY() - startpositie.getY());
		}else{			
			Point secondPosition = coordinaten.get(coordinaten.size() - 2);
			diffX = (int) (secondPosition.getX() - finishpositie.getX());
			diffZ = (int) (secondPosition.getY() - finishpositie.getY());
		}
		
		if (diffX > 0) {
			xplusPrev = true;
		} else if (diffX < 0) {
			xminPrev = true;
		} else if (diffZ > 0) {
			zplusPrev = true;
		} else if (diffZ < 0) {
			zminPrev = true;
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
		int xTemp = (int) Math.floor(locationX / SQUARE_SIZE);
		int zTemp = (int) Math.floor(locationZ / SQUARE_SIZE);
		
		double diffx = Math.abs(xTemp*SQUARE_SIZE+0.5*SQUARE_SIZE - locationX);
		double diffz = Math.abs(zTemp*SQUARE_SIZE+0.5*SQUARE_SIZE - locationZ);
		
		if(diffx<0.1 && diffz<0.1){
			huidigepositie = new Point(xTemp, zTemp);
		}
	}

	/**
	 * Display het object dat de guard is
	 */
	public void display(GL gl) {
		float cubeColor[] = { 1f, 0.5f, 0.5f, 0.7f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, cubeColor, 0);
		gl.glPushMatrix();

		gl.glTranslated(locationX, 0, locationZ);
		
		gl.glRotatef((float) (startAngle + horAngle), 0f, 1f, 0f);
		gl.glScaled(0.50, 0.50, 0.50);
		
		gl.glDisable(GL.GL_CULL_FACE);//zorgt dat de achterkant zichtbaar is
		modelGuard.draw(gl);
		gl.glPopMatrix();

		gl.glEnable(GL.GL_CULL_FACE); // zet de instellingen weer terug zoals ze stonden
	}

	/**
	 * Als een speler binnen een bepaalde afstand van de guard komt wordt hij
	 * gespot en gaat er een boolean op true.
	 * 
	 * @param x
	 * @param z
	 */
	public void playerDetectie(double xPlayer, double zPlayer) {
		if(!attack){
			double afstandzicht = 13;
			double zijzicht = 13;
			
			double diffX = locationX - xPlayer;
			double diffZ = locationZ - zPlayer;		
			
			if (xplus) {
				if(Math.abs(diffZ) < zijzicht && diffX > -afstandzicht && diffX < 0){			
					if(!wallInBetween(diffX, diffZ)){
						setAttack(true);
					}
				}
			} else if (xmin) {
				if(Math.abs(diffZ) < zijzicht && diffX < afstandzicht && diffX > 0){
					if(!wallInBetween(diffX, diffZ)){
						setAttack(true);
					}
				}
			} else if (zmin) {
				if(Math.abs(diffX) < zijzicht && diffZ < afstandzicht && diffZ > 0){
					if(!wallInBetween(diffX, diffZ)){
						setAttack(true);
					}
				}
			} else if (zplus) {
				if(Math.abs(diffX) < zijzicht && diffZ > -afstandzicht && diffZ < 0){
					if(!wallInBetween(diffX, diffZ)){
						setAttack(true);
					}
				}
			} 
		}
	}

	/**
	 * trekt een lijn tussen de player en de guard en geeft true wanneer hier een wall tussen zit. 
	 * 
	 * @param diffX
	 * @param diffZ
	 * @return
	 */
	private boolean wallInBetween(double diffX, double diffZ) {
		boolean wall = false;
		if(Math.abs(diffX)>Math.abs(diffZ)){
			for(double i = 0;i<Math.abs(diffX);i+=0.5){
				int teken = 1;
				if(diffX>0){
					teken = -1;
				}
				double lijnFormuleZ = diffZ/diffX*teken*i+locationZ;
				double lijnFormuleX = teken*i + locationX;
				//DEBUG
//				int x = (int) Math.floor(lijnFormuleX / SQUARE_SIZE);
//				int z = (int) Math.floor(lijnFormuleZ / SQUARE_SIZE);
				//System.out.println(x+","+z);
					if(maze.isWall(lijnFormuleX, lijnFormuleZ)){
						wall = true;
						break;
					}
			}
		}else{
			for(double i = 0;i<Math.abs(diffZ);i+=0.5){
				int teken = 1;
				if(diffZ>0){
					teken = -1;
				}
				double lijnFormuleX = diffX/diffZ*teken*i+locationX;
				double lijnFormuleZ = teken*i + locationZ;
				//DEBUG
//				int x = (int) Math.floor(lijnFormuleX / SQUARE_SIZE);
//				int z = (int) Math.floor(lijnFormuleZ / SQUARE_SIZE);
				//System.out.println(x+","+z);
				if(maze.isWall(lijnFormuleX, lijnFormuleZ)){
					wall = true;
					break;
				}
			}
		}
		
		if(wall){
			System.out.println("er staat een muur of deur tussen");
		}
		return wall;
	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
		if(attack){
			startAngle = -90;
		}
	}

	/**
	 * Loopt naar de positie van de player als attack = true
	 * 
	 * @param x
	 * @param z
	 * @param deltatime
	 */
	public void aanvallen(double xPlayer, double zPlayer, int deltaTime) {
		huidigepositie();
		if (attack == true) {
			double diffX = xPlayer - locationX;
			double diffZ = zPlayer - locationZ;
			horAngle = -Math.toDegrees(Math.atan2(diffZ, diffX))+180;
			//DIT IS STANDAARD
//			locationX -= Math.cos(Math.PI*horAngle/180) * speed * deltaTime;
//			locationZ += Math.sin(Math.PI*horAngle/180) * speed * deltaTime;
				
			wallChecker();
			updateAttackPosition(deltaTime);

						
			
			if (Math.abs(diffX) < 2.5 && Math.abs(diffZ) < 2.5) {
				System.out.println("busted!!!");
			}
			
		}

	}

	private void updateAttackPosition(int deltaTime) {
		if(!canMoveForward && !canMoveRight){
			overRuleLeft = true;
		}
		
		if(!canMoveForward && !canMoveLeft){
			overRuleRight = true;
		}
		
		if(canMoveForward){
			stepForward(deltaTime);
		}
		
		if(canMoveLeft){
			stepLeft(deltaTime);
		}
		
		if(canMoveRight){
			stepRight(deltaTime);
		}
		
		if(overRuleLeft){
			stepLeft(deltaTime); // Math.min(absCos,absSin) * speed
			deltaTimeSum  = deltaTimeSum + deltaTime;
//				System.out.println(deltaTimeSum);
			if(deltaTimeSum >= 100){
				overRuleLeft = false;
				deltaTimeSum = 0;
			}
		}
		
		if(overRuleRight){
			stepRight(deltaTime); // , Math.min(absCos, absSin) * speed
			deltaTimeSum = deltaTimeSum + deltaTime;
//				System.out.println(deltaTimeSum);
			if(deltaTimeSum >= 100 ){
				overRuleRight = false;
				deltaTimeSum = 0;
			}
		}
		//Reset collision detectors
		canMoveForward = true;
		canMoveLeft = true;
		canMoveRight = true;
	}

	private void wallChecker() {
		double checkdistance = SQUARE_SIZE/2;
		//check forward
		if (maze.isWall(
				locationX - checkdistance/2 * Math.cos(Math.PI * horAngle / 180),
				locationZ + checkdistance/2 *  Math.sin(Math.PI* horAngle / 180))){ 
					canMoveForward = false;
		}
		// Check left direction for obstacles
		if (maze.isWall(
				locationX - checkdistance * Math.cos(Math.PI * (horAngle+90) / 180),
				locationZ + checkdistance *  Math.sin(Math.PI* (horAngle+90) / 180))){ 
					canMoveLeft = false;
		}
		// Check right direction for obstacles
		if (maze.isWall(
				locationX - checkdistance * Math.cos(Math.PI * (horAngle-90) / 180),
				locationZ + checkdistance *  Math.sin(Math.PI* (horAngle-90) / 180))){ 
					canMoveRight = false;
		}
	}

	private void stepForward(int deltatime) {
		locationX -= Math.cos(Math.PI*horAngle/180) * speed * deltatime;
		locationZ += Math.sin(Math.PI*horAngle/180) * speed * deltatime;
	}
	
	private void stepLeft(int deltatime) {
		locationX -= Math.cos(Math.PI*horAngle/180 + Math.PI * 0.5) * speed * deltatime;
		locationZ += Math.sin(Math.PI*horAngle/180 + Math.PI * 0.5) * speed * deltatime;
	}
	
	private void stepRight(int deltatime) {
		locationX -= Math.cos(Math.PI*horAngle/180 - Math.PI * 0.5) * speed * deltatime;
		locationZ += Math.sin(Math.PI*horAngle/180 - Math.PI * 0.5) * speed * deltatime;
	}

	public void setCanMoveRight(boolean b) {
		this.canMoveRight = b;
	}

	public void setCanMoveLeft(boolean b) {
		this.canMoveLeft = b;

	}

}
