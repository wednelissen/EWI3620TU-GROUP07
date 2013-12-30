package ShortestRoute;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import MazeRunner.Maze;

public class RouteAlgoritme {

	private Point cam;
	private Point guardLocation;
	private Maze maze;
	private int[][] mazeCoord = maze.getMaze();
	private List<Point> openList;
	private List<Point> closedList;
	private Point node;
	private Point currentSquare;
	private int heuristicDist;
	private int nodeDist = 1;
	ArrayList<Point> tempList;
	private int x;
	private int y;
	private int totalWeight;

	/*
	 * Elke aanliggende node van de locatie in de map moet gecheckt worden. Er
	 * moeten funcites komen voor de lengte van de totale route tot nog toe en
	 * de geschatte lengte van de route die nog gaat komen. Elke node die
	 * bezocht die bezcoht is wordt dan toegevoegd aan een lijst en daar wordt
	 * de beste lijst uit gehaald. Die lijst wordt de input voor een guard net
	 * zoals een patrouille. Als een cam een guard spot dan guards binnen een
	 * bepaald gebied stoppen met lopen, lopen naar de cam, dan gaan ze weer
	 * terug naar orginele route en weer patrouille lopen.
	 */

	public RouteAlgoritme(Maze maz) {
		this.maze = maz;
	}

	public void update(double x, double y) {
		Point guardLocation = new Point((int) x, (int) y);
	}

	public void startNode() {
		openList.add(guardLocation);
	}

	public void tempListAdd(int x, int z) {
		tempList.add(node(x, y + 1));
		tempList.add(node[x][z - 1]);
		tempList.add(node[x + 1][z]);
		tempList.add(node[x - 1][z]);
	}

	public void distCheck() {
		for (Point temp : tempList)
			totalWeight = heuristicWeight(temp) + nodeDist;
	}

	private int heuristicWeight(Point x) {
		heuristicDist = ((int) (Math.abs(x.getX() - cam.getX()) + Math.abs(cam
				.getY() - x.getY())));
		return heuristicDist;
	}

}
