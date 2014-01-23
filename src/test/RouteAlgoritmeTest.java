package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import LevelEditor.LoadLevel;
import MazeRunner.Objects.ControlCenter;
import MazeRunner.Objects.Keys;
import MazeRunner.Objects.Maze;
import MazeRunner.Opponents.RouteAlgoritme;

public class RouteAlgoritmeTest {

	private String levelName = "level1";
	public double MAZE_SIZE_X;
	public double MAZE_SIZE_Z;
	private LoadLevel newMaze;
	public static final double SQUARE_SIZE = 5;
	public Point startPoint;
	public Point endPoint;
	private ArrayList<Keys> doorKeys;
	private ArrayList<ControlCenter> controlCenters = new ArrayList<ControlCenter>();
	private int[][] maze;
	private int[][] oldMaze;

	Maze testmaze = new Maze();
	RouteAlgoritme testroute = new RouteAlgoritme(testmaze);
	private Point cam = new Point(1, 1);
	private Point guard = new Point(2, 5);

	private ArrayList<Point> openlist = testroute.getOpenList();
	private ArrayList<Point> closedlist = testroute.getClosedList();
	private Map<Point, Integer> distance = testroute.getDistance();

	@Test
	public void testMapConversionSetDistance() {
		testroute.mapConversion();
		for (Integer value : testroute.getDistance().values()) {
			assertEquals((int) value, Integer.MAX_VALUE);
		}

	}

	@Test
	public void testMapConversionSize() {
		int k = 0;
		testroute.mapConversion();

		for (int i = 0; i < testmaze.getMaze().length; i++) {
			for (int j = 0; j < testmaze.getMaze()[i].length; j++) {
				if (testmaze.getMaze()[i][j] == 0) {
					k++;
				}

			}

		}
		assertEquals(k, testroute.getOpenList().size());
	}

	@Test
	public void testSelectSmallestValue() {
		testroute.mapConversion();
		testroute.distance.put(guard, 0);
		Point res = testroute.selectSmallestValue();
		assertEquals(res, guard);

	}

	@Test
	public void testNoSmallestValue() {
		testroute.mapConversion();
		Point res = testroute.selectSmallestValue();
		assertEquals(null, res);
	}

	@Test
	public void testNoCheckAdjecensies() {
		testroute.mapConversion();
		boolean res = testroute.checkAdjecencies();
		assertEquals(false, res);
	}

	@Test
	public void testCheckAdjecensies() {
		testroute.mapConversion();
		testroute.distance.put(guard, 0);
		boolean res = testroute.checkAdjecencies();
		Point res1 = new Point(1, 5);
		Point res2 = new Point(3, 5);
		Point res3 = new Point(2, 6);
		Point res4 = new Point(2, 4);
		assertTrue(testroute.predecessor.containsKey(res1));
		assertTrue(testroute.predecessor.containsKey(res2));
		assertTrue(testroute.predecessor.containsKey(res3));
		assertTrue(testroute.predecessor.containsKey(res4));
		assertTrue(testroute.getClosedList().contains(guard));
		assertTrue(testroute.distance.containsValue(1));
		assertEquals(true, res);

	}

	@Test
	public void testAlgorithmHasARoute() {
		ArrayList<Point> res = testroute.algorithm(cam, guard);
		assertNotEquals(res, null);
	}

	@Test
	public void testAlgorithmHasNoRoute() {
		ArrayList<Point> res = testroute.algorithm(cam, null);
		assertEquals(res, null);
	}

	@Test
	public void testAlgorithmFindsShortestRoute() {
		ArrayList<Point> res = testroute.algorithm(cam, guard);
		assertEquals(res.size(), 6);
	}

}
