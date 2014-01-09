package test;

import java.text.SimpleDateFormat;
import MazeRunner.HighScore;
public class GetHighScoreTest {

	public static void main(String[] args) {
		System.out.println(HighScore.retrieveFromDB());

//		java.util.Date utilDate = new java.util.Date();
//		System.out.println(utilDate);
//		java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());
//		System.out.println(timestamp);
//		String s = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(timestamp);
//		System.out.println(s);
	}

}
