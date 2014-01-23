package LevelEditor;

import java.awt.Point;
import java.util.ArrayList;

public class SpotList {

	private ArrayList<Spot> spots;

	/**
	 * maakt een ArrayList van Spot
	 */
	public SpotList() {
		spots = new ArrayList<Spot>();
	}

	/**
	 * Voegt een spot toe indien de huidige lijst van spots kleiner is dan 8 en
	 * deze spot nog niet in de lijst is toegevoegd. er mogen maar maximaal 8
	 * spots in een level worden geplaatst.
	 * 
	 * @param s
	 *            Spot
	 */
	public void addSpot(Spot s) {
		if (spots.size() < 8) {
			boolean duplicated = false;
			Point a = s.getPosition();
			for (Spot temp : spots) {
				if (temp.getPosition().equals(a)) {
					duplicated = true;
					break;
				}
			}
			if (!duplicated)
				spots.add(s);
			else {
				System.out.println("deze spot bestaat al");
			}
		} else {
			System.out.println("er mogen maar maximaal 8 spots in een level");
		}
	}

	/**
	 * Vult de ArrayList met Spot wanneer deze zijn geladen uit een opgeslagen
	 * level. de ArrayList met Spots is op het moment van aanroepen leeg indien
	 * bij het laden van een level alle objecten worden gereset.
	 * 
	 * @param loadedSpotPoints
	 *            arrayList van Point
	 */
	public void loadSpots(ArrayList<Point> loadedSpotPoints) {

		for (Point a : loadedSpotPoints) {
			Spot temp = new Spot();
			temp.setSpot(a);
			this.addSpot(temp);
		}
	}

	/**
	 * 
	 * @return aantal Spots in de lijst.
	 */
	public int size() {
		return spots.size();
	}

	/**
	 * verwijderd de spot indien deze in de lijst zit. wanneer deze niet in de
	 * lijst zit gebeurd er niets.
	 * 
	 * @param s
	 *            Spot
	 */
	public void removeSpot(Spot s) {
		Point a = s.getPosition();
		for (Spot temp : spots) {
			if (temp.getPosition().equals(a)) {
				spots.remove(temp);
				break;
			}
		}
	}

	/**
	 * 
	 * @return de ArrayList van de opgeslagen Spots.
	 */
	public ArrayList<Spot> getSpots() {
		return spots;
	}

}
