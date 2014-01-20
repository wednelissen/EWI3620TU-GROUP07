package test;

import static org.junit.Assert.*;

import org.junit.Test;

import MazeRunner.Objects.Maze;
import MazeRunner.Player.Player;
import MazeRunner.Player.WallChecker;
import MazeRunner.Fundamental.MazeRunner;

/**
 * This JUnit test case checks the functionality of the WallChecker class. The
 * tests are designed to assure proper output to the player, in which booleans
 * are set indicating whether the player can move in certain directions.
 * 
 * @author Wiebe
 * 
 */
public class WallCheckerTest {

	private int[][] mazeCase;
	private Maze maze;
	private Player player = new Player((1.5*Maze.SQUARE_SIZE),0.5*Maze.SQUARE_SIZE,(1.5*Maze.SQUARE_SIZE),0,0); // Player is located in the center of the test maze
	private WallChecker wallchecker;
	

	
	/**
	 * Tests the case in which walls should be detected in all directions.
	 */
	@Test
	public void allBlockedTest() {
		mazeCase = new int[][] {{1,1,1},
								{1,0,1},
								{1,1,1}	};
		maze = new Maze(mazeCase);
		wallchecker = new WallChecker(player,3,maze);
		wallchecker.check();
		boolean[] expected = new boolean[]{false, false, false, false, true, true };
		boolean[] values = player.getDirectionBooleans();
		for(int i=0;i<values.length;i++){
		assertEquals(expected[i], values[i]);
		}
	}
	
	/**
	 * Tests the case in which no wall should be detected in any direction.
	 */
	@Test
	public void allOpenTest() {
		mazeCase = new int[][] {{0,0,0},
								{0,0,0},
								{0,0,0}	};
		maze = new Maze(mazeCase);
		wallchecker = new WallChecker(player,3,maze);
		wallchecker.check();
		boolean[] expected = new boolean[]{true, true, true, true, false, false };
		boolean[] values = player.getDirectionBooleans();
		for(int i=0;i<values.length;i++){
		assertEquals(expected[i], values[i]);
		}
	}
	
	/**
	 * Tests the diagonal wall checks for consistency in case there are no walls
	 * in orthogonal directions. Also tests L/R/F/B wall checks detect no wall
	 * this case.
	 */
	@Test
	public void onlyDiagonalTest() {
		mazeCase = new int[][] {{1,0,0},
								{0,0,0},
								{1,0,0}	};
		maze = new Maze(mazeCase);
		wallchecker = new WallChecker(player, 3, maze);
		wallchecker.check();
		boolean[] expected = new boolean[] { true, true, true, true, true, true };
		boolean[] values = player.getDirectionBooleans();
		for (int i = 0; i < values.length; i++) {
			assertEquals(expected[i], values[i]);
		}
	}

	/**
	 * Rotates the player by 90 degrees (CCW) for 4 times. Tests whether the wall is
	 * detected in the correct orthogonal direction every time.
	 */
	@Test
	public void rotatingPlayerTestOrhtogonal(){
		double horAngle = 0;
		
		mazeCase = new int[][] {{0,0,0},
								{1,0,0},
								{0,0,0}	};
		boolean[] expected = new boolean[] { true, true, true, true, false, false };
		
		for(int i=0;i<4;i++){
			
			player = new Player((1.5*Maze.SQUARE_SIZE),0.5*Maze.SQUARE_SIZE,(1.5*Maze.SQUARE_SIZE),horAngle,0);
			maze = new Maze(mazeCase);
			wallchecker = new WallChecker(player, 3, maze);
			wallchecker.check();
			boolean[] values = player.getDirectionBooleans();
			
			for(int k=0;k<4;k++){
				if(k==i){
					expected[k] = false;
				}
				else{
					expected[k] = true;
				}
			}

			for (int j = 0; j < values.length; j++) {
				assertEquals(expected[j], values[j]);
			}
			horAngle+=90;
		}
	}
	
	/**
	 * Rotates the player by 90 degrees (CCW) for 4 times. Tests whether the
	 * wall is detected in the correct diagonal direction, or not detected at
	 * all in case the player is looking away from it.
	 */
	@Test
	public void rotatingPlayerTestDiagonal(){
		double horAngle = 0;
		
		mazeCase = new int[][] {{1,0,0},
								{0,0,0},
								{0,0,0}	};
		boolean[] expected = new boolean[] { true, true, true, true, true, false };
		
		for(int i=0;i<4;i++){
			if(i==1){
				expected[4] = false;
				expected[5] = true;
			}
			if(i==2){
				expected[5] = false;
			}
			player = new Player((1.5*Maze.SQUARE_SIZE),0.5*Maze.SQUARE_SIZE,(1.5*Maze.SQUARE_SIZE),horAngle,0);
			maze = new Maze(mazeCase);
			wallchecker = new WallChecker(player, 3, maze);
			wallchecker.check();
			boolean[] values = player.getDirectionBooleans();
		
		
			for (int j = 0; j < values.length; j++) {
				assertEquals(expected[j], values[j]);
			}
			horAngle+=90;
		}
	}
	
	/**
	 * Tests god mode functionality. Even though there are walls in all checked
	 * directions, the wallchecker should detect none.
	 */
	@Test
	public void godModeTest() {
		MazeRunner.GOD_MODE = true;
			mazeCase = new int[][] {{1,1,1},
									{1,0,1},
									{1,1,1}	};
		maze = new Maze(mazeCase);
		wallchecker = new WallChecker(player, 3, maze);
		wallchecker.check();
		boolean[] expected = new boolean[] { true, true, true, true, false,
				false };
		boolean[] values = player.getDirectionBooleans();
		for (int i = 0; i < values.length; i++) {
			assertEquals(expected[i], values[i]);
		}
		MazeRunner.GOD_MODE = false;
	}
}
