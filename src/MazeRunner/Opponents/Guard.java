package MazeRunner.Opponents;

import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL;

import MazeRunner.Fundamental.LoadTexturesMaze;
import MazeRunner.Fundamental.MazeRunner;
import MazeRunner.Objects.GameObject;
import MazeRunner.Objects.Maze;
import MazeRunner.Objects.Model;
import MazeRunner.Objects.VisibleObject;

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
	private GuardCamera guardCamera = null;
	private double speed;
	private boolean richting = true;

	private Point startpositie;

	private Point eindpositie;
	private Point huidigepositie;

	private Point finishpositie;

	private int teller = 1;

	private boolean zplus = false; // loopt naar beneden
	private boolean zmin = false; // loopt naar boven
	private boolean xplus = false; // loopt naar rechts
	private boolean xmin = false; // loopt naar links
	private boolean attack = false;
	private double horAngle = 0;
	private double attackAngle = 0;
	private boolean startCheck = true;
	private boolean finishCheck = false;
	private boolean busted = false;
	private boolean alarmed = false;
	private boolean resettingPatrol = false;
	//private boolean patrol = true;
	
	private ArrayList<Point> patrolCoordinaten;

	private boolean canMoveForward;
	private boolean canMoveLeft;
	private boolean canMoveRight;

	private Model modelGuard;

	private boolean overRuleLeft;
	private boolean overRuleRight;
	private int deltaTimeSum = 0;
	private Point patrolStartPositie;
	

	public Guard(double x, double y, double z, ArrayList<Point> points) {

		super(x * SQUARE_SIZE + (0.5 * SQUARE_SIZE), y, z * SQUARE_SIZE
				+ (0.5 * SQUARE_SIZE));
		modelGuard = LoadTexturesMaze.getModel("modelGuard");

		speed = 0.005;
		coordinaten = points;
		patrolCoordinaten = points;

		startpositie = coordinaten.get(0);
		patrolStartPositie = coordinaten.get(0);
		finishpositie = coordinaten.get(coordinaten.size() - 1);
		huidigepositie = startpositie;
	}

	public void run(int deltaTime, double xPlayer, double zPlayer ){
		if(!attack){
			update(deltaTime);	
			
			if(alarmed){
				if(huidigepositie.equals(finishpositie)){
					resettingPatrol = true;
					alarmed = false;
					guardCamera.resetAlarm();
					guardCamera = null;
					setPatrolPathToBegin();
				}
			}
			if(resettingPatrol){
				if(huidigepositie.equals(finishpositie)){
					resettingPatrol = false;
					setDefaultPatrolPath();
				}
			}
		}
		
		if(!MazeRunner.GOD_MODE){
			playerDetectie(xPlayer, zPlayer); 		//if attack==false zit in de functie.
			aanvallen(xPlayer, zPlayer, deltaTime); //if attack==true  zit in de functie.
		}
		
	}
	
	private void setPatrolPathToBegin(){
		ArrayList<Point> resetRoute = new RouteAlgoritme(maze).algorithm(patrolStartPositie,huidigepositie);
		setNewPatrolPath(resetRoute);
	}
	
	private void setDefaultPatrolPath(){
		setNewPatrolPath(patrolCoordinaten);
	}
	
	
	public void setNewPatrolPath(ArrayList<Point> newRoute){
		startCheck = true;
		finishCheck = false;
		teller = 1;
		richting = true;
		this.coordinaten = newRoute;
		startpositie = coordinaten.get(0); 
		finishpositie = coordinaten.get(coordinaten.size() - 1);
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
			startCheck = false;
			finishCheck = true;
		} else if (huidigepositie.equals(startpositie) && !startCheck) {
			richting = true;
			startCheck = true;
			finishCheck = false;
		}
		if (!attack) {
			resetWalkingDirection();
			if (richting) {

				eindpositie = coordinaten.get(teller);

				if (!(eindpositie.equals(huidigepositie))) {
					int diffX = (int) (eindpositie.getX() - huidigepositie
							.getX());
					int diffZ = (int) (eindpositie.getY() - huidigepositie
							.getY());
					
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
					int diffX = (int) (eindpositie.getX() - huidigepositie
							.getX());
					int diffZ = (int) (eindpositie.getY() - huidigepositie
							.getY());

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
					teller--;
				}
			}
			richtingDraaier();
		}

	}

	private void resetWalkingDirection() {
		xmin = false;
		xplus = false;
		zmin = false;
		zplus = false;
	}

	/**
	 * Deze methode controleert de patrouillerichting. Aan de hand van deze richting
	 * wordt de hoek van de guard aangepast. Als de Guard geen richting heeft behoud hij
	 * zijn oude hoek.
	 */
	private void richtingDraaier() {
		if (zplus) {
			horAngle = 0;
		}
		else if (zmin) {
			horAngle = 180;
		}
		else if (xmin) {
			horAngle = -90;
		}
		else if (xplus) {
			horAngle = 90;
		}
	}

	/**
	 * Geeft de huidige positie van het object Guard.
	 */
	public void huidigepositie() {
		int xTemp = (int) Math.floor(locationX / SQUARE_SIZE);
		int zTemp = (int) Math.floor(locationZ / SQUARE_SIZE);

		double diffx = Math.abs(xTemp * SQUARE_SIZE + 0.5 * SQUARE_SIZE
				- locationX);
		double diffz = Math.abs(zTemp * SQUARE_SIZE + 0.5 * SQUARE_SIZE
				- locationZ);

		if (diffx < 0.1 && diffz < 0.1) {
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

		//attackAngle zorgt dat de guard altijd met zijn gezicht naar de player toe is gericht.
		gl.glRotatef((float) (attackAngle + horAngle), 0f, 1f, 0f);
		gl.glScaled(0.15, 0.15, 0.15);

		gl.glDisable(GL.GL_CULL_FACE);// zorgt dat de achterkant zichtbaar is
		modelGuard.draw(gl, LoadTexturesMaze.getTexture("modelGuard"));
		gl.glPopMatrix();

		gl.glEnable(GL.GL_CULL_FACE); // zet de instellingen weer terug zoals ze
										// stonden
	}

	/**
	 * Als een speler binnen een bepaalde afstand van de guard komt wordt hij
	 * gespot en gaat er een boolean op true.
	 * 
	 * @param x
	 * @param z
	 */
	public void playerDetectie(double xPlayer, double zPlayer) {
		if (!attack) {
			double afstandzicht = 13;
			double zijzicht = 13;

			double diffX = locationX - xPlayer;
			double diffZ = locationZ - zPlayer;

			if (xplus) {
				if (Math.abs(diffZ) < zijzicht && diffX > -afstandzicht
						&& diffX < 0) {
					if (!wallInBetween(diffX, diffZ)) {
						setAttack(true);
					}
				}
			} else if (xmin) {
				if (Math.abs(diffZ) < zijzicht && diffX < afstandzicht
						&& diffX > 0) {
					if (!wallInBetween(diffX, diffZ)) {
						setAttack(true);
					}
				}
			} else if (zmin) {
				if (Math.abs(diffX) < zijzicht && diffZ < afstandzicht
						&& diffZ > 0) {
					if (!wallInBetween(diffX, diffZ)) {
						setAttack(true);
					}
				}
			} else if (zplus) {
				if (Math.abs(diffX) < zijzicht && diffZ > -afstandzicht
						&& diffZ < 0) {
					if (!wallInBetween(diffX, diffZ)) {
						setAttack(true);
					}
				}
			}
		}
	}

	/**
	 * trekt een lijn tussen de player en de guard en geeft true wanneer hier
	 * een wall tussen zit.
	 * 
	 * @param diffX
	 * @param diffZ
	 * @return
	 */
	private boolean wallInBetween(double diffX, double diffZ) {
		boolean wall = false;
		if (Math.abs(diffX) > Math.abs(diffZ)) {
			for (double i = 0; i < Math.abs(diffX); i += 0.5) {
				int teken = 1;
				if (diffX > 0) {
					teken = -1;
				}
				double lijnFormuleZ = diffZ / diffX * teken * i + locationZ;
				double lijnFormuleX = teken * i + locationX;
				// DEBUG
				// int x = (int) Math.floor(lijnFormuleX / SQUARE_SIZE);
				// int z = (int) Math.floor(lijnFormuleZ / SQUARE_SIZE);
				// System.out.println(x+","+z);
				if (maze.isWall(lijnFormuleX, lijnFormuleZ)) {
					wall = true;
					break;
				}
			}
		} else {
			for (double i = 0; i < Math.abs(diffZ); i += 0.5) {
				int teken = 1;
				if (diffZ > 0) {
					teken = -1;
				}
				double lijnFormuleX = diffX / diffZ * teken * i + locationX;
				double lijnFormuleZ = teken * i + locationZ;
				// DEBUG
				// int x = (int) Math.floor(lijnFormuleX / SQUARE_SIZE);
				// int z = (int) Math.floor(lijnFormuleZ / SQUARE_SIZE);
				// System.out.println(x+","+z);
				if (maze.isWall(lijnFormuleX, lijnFormuleZ)) {
					wall = true;
					break;
				}
			}
		}

		if (wall) {
			System.out.println("er staat een muur of deur tussen");
		}
		return wall;
	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
		if (attack) {
			attackAngle = -90;
		}
		else{
			attackAngle = 0;
		}
	}

	/**
	 * Loopt naar de positie van de player als attack = true.
	 * Wanneer de guard in een gebied van 2.5 om de player heen is zal busted op true worden geset.
	 * 
	 * @param x
	 * @param z
	 * @param deltatime
	 */
	public void aanvallen(double xPlayer, double zPlayer, int deltaTime) {
		huidigepositie();
		if (attack) {
			double diffX = xPlayer - locationX;
			double diffZ = zPlayer - locationZ;
			horAngle = -Math.toDegrees(Math.atan2(diffZ, diffX)) + 180;
			// DIT IS STANDAARD, MAAR DAN ZOU DE GUARD DOOR MUREN KUNNEN LOPEN.
			// locationX -= Math.cos(Math.PI*horAngle/180) * speed * deltaTime;
			// locationZ += Math.sin(Math.PI*horAngle/180) * speed * deltaTime;

			wallChecker();
			updateAttackPosition(deltaTime);

			if (Math.abs(diffX) < 2.5 && Math.abs(diffZ) < 2.5) {
				busted = true;
			}

		}

	}

	private void updateAttackPosition(int deltaTime) {
		if (!canMoveForward && !canMoveRight) {
			overRuleLeft = true;
		}

		if (!canMoveForward && !canMoveLeft) {
			overRuleRight = true;
		}

		if (canMoveForward) {
			stepForward(deltaTime);
		}

		if (canMoveLeft) {
			stepLeft(deltaTime);
		}

		if (canMoveRight) {
			stepRight(deltaTime);
		}

		if (overRuleLeft) {
			stepLeft(deltaTime); // Math.min(absCos,absSin) * speed
			deltaTimeSum = deltaTimeSum + deltaTime;
			if (deltaTimeSum >= 100) {
				overRuleLeft = false;
				deltaTimeSum = 0;
			}
		}

		if (overRuleRight) {
			stepRight(deltaTime); // , Math.min(absCos, absSin) * speed
			deltaTimeSum = deltaTimeSum + deltaTime;
			if (deltaTimeSum >= 100) {
				overRuleRight = false;
				deltaTimeSum = 0;
			}
		}
		// Reset collision detectors
		canMoveForward = true;
		canMoveLeft = true;
		canMoveRight = true;
	}

	private void wallChecker() {
		double checkdistance = SQUARE_SIZE / 2;
		// check forward
		if (maze.isWall(
				locationX - checkdistance / 2
						* Math.cos(Math.PI * horAngle / 180),
				locationZ + checkdistance / 2
						* Math.sin(Math.PI * horAngle / 180))) {
			canMoveForward = false;
		}
		// Check left direction for obstacles
		if (maze.isWall(
				locationX - checkdistance
						* Math.cos(Math.PI * (horAngle + 90) / 180),
				locationZ + checkdistance
						* Math.sin(Math.PI * (horAngle + 90) / 180))) {
			canMoveLeft = false;
		}
		// Check right direction for obstacles
		if (maze.isWall(
				locationX - checkdistance
						* Math.cos(Math.PI * (horAngle - 90) / 180),
				locationZ + checkdistance
						* Math.sin(Math.PI * (horAngle - 90) / 180))) {
			canMoveRight = false;
		}
	}

	private void stepForward(int deltatime) {
		locationX -= Math.cos(Math.PI * horAngle / 180) * speed * deltatime;
		locationZ += Math.sin(Math.PI * horAngle / 180) * speed * deltatime;
	}

	private void stepLeft(int deltatime) {
		locationX -= Math.cos(Math.PI * horAngle / 180 + Math.PI * 0.5) * speed
				* deltatime;
		locationZ += Math.sin(Math.PI * horAngle / 180 + Math.PI * 0.5) * speed
				* deltatime;
	}

	private void stepRight(int deltatime) {
		locationX -= Math.cos(Math.PI * horAngle / 180 - Math.PI * 0.5) * speed
				* deltatime;
		locationZ += Math.sin(Math.PI * horAngle / 180 - Math.PI * 0.5) * speed
				* deltatime;
	}

	public void setCanMoveRight(boolean b) {
		this.canMoveRight = b;
	}

	public void setCanMoveLeft(boolean b) {
		this.canMoveLeft = b;

	}

	public ArrayList<Point> getCoordinaten() {
		return coordinaten;
	}

	public void setCoordinaten(ArrayList<Point> coordinaten) {
		this.coordinaten = coordinaten;
	}

	public Point getStartpositie() {
		return startpositie;
	}

	public void setStartpositie(Point startpositie) {
		this.startpositie = startpositie;
	}

	public Point getEindpositie() {
		return eindpositie;
	}

	public void setEindpositie(Point eindpositie) {
		this.eindpositie = eindpositie;
	}

	public Point getHuidigepositie() {
		return huidigepositie;
	}

	public Point getFinishpositie() {
		return finishpositie;
	}

	public void setFinishpositie(Point finishpositie) {
		this.finishpositie = finishpositie;
	}

	public Point getPatrolStartPositie() {
		return patrolStartPositie;
	}

	public void setPatrolStartPositie(Point patrolStartPositie) {
		this.patrolStartPositie = patrolStartPositie;
	}

	public boolean isResettingPatrol() {
		return resettingPatrol;
	}

	public void setResettingPatrol(boolean resettingPatrol) {
		this.resettingPatrol = resettingPatrol;
	}

	public ArrayList<Point> getPatrolCoordinaten() {
		return patrolCoordinaten;
	}

	public void setPatrolCoordinaten(ArrayList<Point> patrolCoordinaten) {
		this.patrolCoordinaten = patrolCoordinaten;
	}

	public int getTeller() {
		return teller;
	}

	public void setTeller(int teller) {
		this.teller = teller;
	}

	public boolean isRichting() {
		return richting;
	}

	public void setRichting(boolean richting) {
		this.richting = richting;
	}

	public void setCanMoveForward(boolean b) {
		this.canMoveForward = b;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isBusted() {
		return busted;
	}

	public boolean isAlarmed() {
		return alarmed;
	}

	public void setAlarmed(boolean alarmed) {
		this.alarmed = alarmed;
	}


	public Point getEindPositie() {
		return eindpositie;
	}
	
	public void setGuardCamera(GuardCamera cam){
		guardCamera = cam;
	}
	
}
