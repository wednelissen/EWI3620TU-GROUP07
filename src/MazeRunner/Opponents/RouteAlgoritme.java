package MazeRunner.Opponents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MazeRunner.Objects.Maze;

/**
 * Deze klasse vindt de korste route van een bewaker zijn huidige positie naar
 * een gealarmeerde camera. Er wordt daarvoor gebruik gemaakt van Dijkstra's
 * algoritme.
 * 
 * @author Stijn
 * 
 */

public class RouteAlgoritme {

	public Map<Point, Point> predecessor = new HashMap<Point, Point>();
	private ArrayList<Point> openList = new ArrayList<Point>();

	private ArrayList<Point> closedList = new ArrayList<Point>();
	private List<Point> nodes = new ArrayList<Point>();
	public Map<Point, Integer> distance = new HashMap<Point, Integer>();

	private Maze maze;
	private Point guardLocation;
	private Point camera;
	private int[][] mazeCoord;
	public ArrayList<Point> shortestPath = new ArrayList<Point>();

	public RouteAlgoritme(Maze maz) {
		this.maze = maz;
		this.mazeCoord = maze.getMaze();
	}

	/**
	 * Deze functie maakt van de array van maze een lijst met punten, als er op
	 * die punten gelopen kan worden. Muren worden dus al uit de beloopbare
	 * plekken gehaald
	 */

	public void mapConversion() {
		for (int i = 0; i < mazeCoord.length; i++) {
			for (int j = 0; j < mazeCoord[i].length; j++) {
				if (mazeCoord[i][j] == 0) {
					Point temp = new Point(i, j);
					distance.put(temp, Integer.MAX_VALUE);
					openList.add(temp);
				}
			}
		}

	}

	/**
	 * Deze methode controleert de aanliggende punten rond een punt en voegt die
	 * toe aan de Map predecessor mocht zo'n punt bestaan.
	 * 
	 * @return - een boolean die aangeeft of er een kortste route gevonden kan
	 *         worden
	 */
	public boolean checkAdjecencies() {
		Point node = selectSmallestValue();
		if (node == null) {
			return false;
		}
		Point top = new Point((int) node.getX(), (int) node.getY() + 1);
		Point bottom = new Point((int) node.getX(), (int) node.getY() - 1);
		Point right = new Point((int) node.getX() + 1, (int) node.getY());
		Point left = new Point((int) node.getX() - 1, (int) node.getY());
		closedList.add(node);

		int distanceNode = distance.get(node);

		if (openList.contains(top) && !closedList.contains(top)) {
			if ((distanceNode + 1) < distance.get(top)) {
				distance.put(top, distanceNode + 1);
				predecessor.put(top, node);
			}

		}
		if (openList.contains(bottom) && !closedList.contains(bottom)) {
			if ((distanceNode + 1) < distance.get(bottom)) {
				distance.put(bottom, distanceNode + 1);
				predecessor.put(bottom, node);
			}

		}
		if (openList.contains(right) && !closedList.contains(right)) {
			if ((distanceNode + 1) < distance.get(right)) {
				distance.put(right, distanceNode + 1);
				predecessor.put(right, node);
			}

		}
		if (openList.contains(left) && !closedList.contains(left)) {
			if ((distanceNode + 1) < distance.get(left)) {
				distance.put(left, distanceNode + 1);
				predecessor.put(left, node);
			}

		}
		if (node.equals(camera)) {

		}
		return true;
	}

	/**
	 * Deze methode vindt het punt het dichstbijzijnd bij de node en niet al
	 * gecontroleerd. Aaangezien hier alle afstand hetzelfde is, wordt er een
	 * standaard afstand meegegeven
	 * 
	 * @return het punt met de kleinste afstand
	 */
	public Point selectSmallestValue() {
		int minimalValue = Integer.MAX_VALUE;
		Point res = new Point();
		res = null; // zodat hij null returnt indien er geen minimale waarde is
					// gevonden. dit komt voor wanneer een guard niet bij een
					// camera kan komen.
		for (Point p : openList) {
			if (!closedList.contains(p)) {
				int dist = distance.get(p);
				if (dist < minimalValue) {
					minimalValue = dist;
					res = p;
				}
			}
		}
		return res;
	}

	/**
	 * 
	 * @param end
	 *            - het eindpunt wat de guard moet bereiken
	 */
	private void backtracking(Point end) {
		if (end.equals(guardLocation)) {
			shortestPath.add(end);
			Collections.reverse(shortestPath);
		} else {
			Point a = predecessor.get(end);

			shortestPath.add(end);
			backtracking(a);
		}
	}

	/**
	 * Deze methode bevat Dijkstra's algoritme
	 * 
	 * @param cam
	 *            - het punt van de camera
	 * @param guard
	 *            - het beginpunt van de guard
	 * @return een korstste route
	 */
	public ArrayList<Point> algorithm(Point cam, Point guard) {
		ArrayList<Point> route = new ArrayList<Point>();
		this.camera = cam;
		this.guardLocation = guard;
		mapConversion();
		distance.put(guardLocation, 0);

		boolean found = true;

		while (!closedList.contains(camera)) {
			if (!checkAdjecencies()) {
				found = false;
				break;
			}

		}

		if (found) {

			backtracking(camera);
			for (Point p : shortestPath) {

				route.add(p);

			}
			return route;
		} else {
			return null;
		}

	}

	public ArrayList<Point> getOpenList() {
		return openList;
	}

	public void setOpenList(ArrayList<Point> openList) {
		this.openList = openList;
	}

	public ArrayList<Point> getClosedList() {
		return closedList;
	}

	public void setClosedList(ArrayList<Point> closedList) {
		this.closedList = closedList;
	}

	public Map<Point, Integer> getDistance() {
		return distance;
	}
}
