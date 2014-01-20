package MazeRunner.Opponents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MazeRunner.Objects.Maze;

public class RouteAlgoritme {

	private Map<Point, Point> predecessor = new HashMap<Point, Point>();
	private ArrayList<Point> openList = new ArrayList<Point>();
	private ArrayList<Point> closedList = new ArrayList<Point>();
	private List<Point> nodes = new ArrayList<Point>();
	private Map<Point, Integer> distance = new HashMap<Point, Integer>();
	private Maze maze;
	private Point guardLocation;
	private Point camera;
	private int[][] mazeCoord;
	private ArrayList<Point> shortestPath = new ArrayList<Point>();

	public RouteAlgoritme(Maze maz) {
		this.maze = maz;
		this.mazeCoord = maze.getMaze();
	}

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

	private Point selectSmallestValue() {
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
		}
		else{
			return null;
		}
		
	}
}
