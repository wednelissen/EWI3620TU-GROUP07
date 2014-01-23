package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class CameraList {

	private ArrayList<Camera> cameras;

	/**
	 * Maakt een ArrayList van camera's.
	 */
	public CameraList() {
		cameras = new ArrayList<Camera>();
	}

	/**
	 * Voegt een camera aan de arraylist van camera's toe. een camera die al in
	 * de arraylist zit wordt niet toegevoegt.
	 * 
	 * @param s
	 *            De camera die toegevoegd moet worden aan de camera lijst
	 */
	public void addCamera(Camera s) {
		boolean duplicated = false;
		Point a = s.getPosition();
		for (Camera temp : cameras) {
			if (temp.getPosition().equals(a)) {
				duplicated = true;
				break;
			}
		}
		if (!duplicated)
			cameras.add(s);
		else {
			System.out.println("deze camera bestaat al");
		}
	}

	/**
	 * Genereert een arraylist van camera's en vervangt de oude arraylist
	 * 
	 * @param loadedCameraPoints
	 *            Een lijst met alle punten waar camera's moeten komen te staan
	 */
	public void loadCameras(ArrayList<Point> loadedCameraPoints) {

		for (Point a : loadedCameraPoints) {
			Camera temp = new Camera();
			temp.setCamera(a);
			this.addCamera(temp);
		}
	}

	/**
	 * De get functie die de hoeveelheid camera's terug geeft
	 * 
	 * @return Een int waarde die aangeeft hoeveel camera's er zijn
	 */
	public int size() {
		return cameras.size();
	}

	/**
	 * Verwijderd een camera indien deze in de lijst zit. anders gebeurd er
	 * niets.
	 * 
	 * @param s
	 *            De camera die verwijderd moet worden uit de lijst met camera's
	 */
	public void removeCamera(Camera s) {
		Point a = s.getPosition();
		for (Camera temp : cameras) {
			if (temp.getPosition().equals(a)) {
				cameras.remove(temp);
				break;
			}
		}
	}

	/**
	 * De get functie die de alle camera's van het level heeft
	 * 
	 * @return een ArrayList van opgeslagen camera's
	 */
	public ArrayList<Camera> getCameras() {
		return cameras;
	}

}
