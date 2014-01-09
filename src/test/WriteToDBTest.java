package test;
import MazeRunner.HighScore;

public class WriteToDBTest {

	private static HighScore highscore = new HighScore("Arie", 2345, "level1");

	public static void main(String[] args) {
		highscore.writeToDB();
	}

}
