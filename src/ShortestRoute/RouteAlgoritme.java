package ShortestRoute;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MazeRunner.Maze;

public class RouteAlgoritme {

	private Map<Point, Point> predecessor = new HashMap<Point, Point>();
	private ArrayList<Point> openList = new ArrayList<Point>();
	private ArrayList<Point> closedList = new ArrayList<Point>();
	private List<Point> nodes = new ArrayList<Point>();
	private Maze maze;
	private Point guardLocation;

	private Point camera;

	private int[][] mazeCoord;

	public RouteAlgoritme(Maze maz) {
		this.maze = maz;
		this.mazeCoord = maze.getMaze();
	}

	public void mapConversion() {
		for (int i = 0; i < mazeCoord.length; i++) {
			for (int j = 0; j < mazeCoord[i].length; j++) {
				if (mazeCoord[i][j] == 0) {
					Point temp = new Point(i, j);
					openList.add(temp);
				}
			}
		}

	}

	public void checkAdjecencies() {
		for (Point node : nodes) {
			Point top = new Point((int) node.getX(), (int) node.getY() + 1);
			Point bottom = new Point((int) node.getX(), (int) node.getY() - 1);
			Point right = new Point((int) node.getX() + 1, (int) node.getY());
			Point left = new Point((int) node.getX() - 1, (int) node.getY());
			closedList.add(node);

			if (openList.contains(top)) {
				nodes.add(top);
				predecessor.put(node, top);

			}
			if (openList.contains(bottom)) {
				nodes.add(bottom);
				predecessor.put(node, bottom);

			}
			if (openList.contains(right)) {
				nodes.add(right);
				predecessor.put(node, right);

			}
			if (openList.contains(left)) {
				nodes.add(left);
				predecessor.put(node, left);

			}
			if (nodes.contains(camera)) {
				// returnShortestRoute();
				System.out.println("found");
			}
			openList.remove(node);
		}

	}

	// private ArrayList<Point> returnShortestRoute() {
	//
	// }

	public void algorithm(Point cam, Point guard) {
		this.camera = cam;
		this.guardLocation = guard;
		mapConversion();
		nodes.add(guardLocation);
		for (Point punts : nodes) {
			System.out.println(punts);
		}
		while (openList.size() > 0) {
			checkAdjecencies();

		}

	}
}
