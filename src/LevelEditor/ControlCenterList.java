package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class ControlCenterList {
	private ArrayList<ControlCenterEditor> controlCenters;

	/**
	 * Maakt een ArrayList van controlCenters.
	 */
	public ControlCenterList() {
		controlCenters = new ArrayList<ControlCenterEditor>();
	}

	/**
	 * Voegt een ControlCenter aan de arraylist van ControlCenters toe. een
	 * ControlCenter die al in de arraylist zit wordt niet toegevoegt.
	 * 
	 * @param s
	 *            De control center die toegevoegd moet worden aan de camera
	 *            lijst
	 */
	public void addControlCenter(ControlCenterEditor s) {
		boolean duplicated = false;
		Point a = s.getPosition();
		for (ControlCenterEditor temp : controlCenters) {
			if (temp.getPosition().equals(a)) {
				duplicated = true;
				break;
			}
		}
		if (!duplicated)
			controlCenters.add(s);
		else {
			System.out.println("deze controlCenter bestaat al");
		}
	}

	/**
	 * Genereerd een arraylist van controlCenters en vervangt de oude arraylist
	 * 
	 * @param loadedControlCentersPoints
	 *            Een lijst met alle punten waar control centers moeten komen te
	 *            staan
	 */
	public void loadControlCenters(ArrayList<Point> loadedControlCenterPoints) {

		for (Point a : loadedControlCenterPoints) {
			ControlCenterEditor temp = new ControlCenterEditor();
			temp.setControlCenter(a);
			this.addControlCenter(temp);
		}
	}

	/**
	 * De get functie die de hoeveelheid control centers terug geeft
	 * 
	 * @return Een int waarde die aangeeft hoeveel camera's er zijn
	 */
	public int size() {
		return controlCenters.size();
	}

	/**
	 * verwijderd een controlCenter indien deze in de lijst zit, anders gebeurd
	 * er niets.
	 * 
	 * @param s
	 *            De control center die verwijderd moet worden uit de lijst met
	 *            control centers
	 */
	public void removeControlCenter(ControlCenterEditor s) {
		Point a = s.getPosition();
		for (ControlCenterEditor temp : controlCenters) {
			if (temp.getPosition().equals(a)) {
				controlCenters.remove(temp);
				break;
			}
		}
	}

	/**
	 * De get functie die de alle camera's van het level heeft
	 * 
	 * @return een ArrayList van opgeslagen controlCenters
	 */
	public ArrayList<ControlCenterEditor> getControlCenters() {
		return controlCenters;
	}

}