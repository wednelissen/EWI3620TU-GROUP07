package MazeRunner.Fundamental;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Represents a high score. Contains the player name, the player's score and the
 * name of the map on which the score was attained. Can be written to a
 * database.
 * 
 * @author Wiebe
 * 
 */
public class HighScore {

	public String playerName;
	public int score;
	public String levelName;
	private static final String url = "jdbc:mysql://sql.ewi.tudelft.nl/minorprojectSOT";
	private static final String username = "admin";
	private static final String password = "fietskopen";
	private static Statement stmt;
	private static Connection connection = null;
	private static ResultSet rs;

	/**
	 * Constructor
	 * 
	 * @param playername
	 * @param score
	 * @param levelname
	 */
	public HighScore(String playername, int score, String levelname) {
		this.playerName = playername;
		this.score = score;
		File tempfile = new File(levelname);
		this.levelName = tempfile.getName();
	}

	/**
	 * Updates the high score according to the passed time since the previous
	 * update.
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {
		this.score += deltaTime;
	}

	public void alarmedCameraUpdate() {
		this.score += 10000;
	}

	public int getScore() {
		return this.score;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public String getLevelName() {
		return this.levelName;
	}

	/**
	 * Connects to the database and writes the high score to it.
	 */
	public void writeToDB() {
		try {
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"Cannot find the driver in the classpath!", e);
		}
		try {
			System.out.println("Connecting database...");
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			try {
				java.util.Date utilDate = new java.util.Date();
				java.sql.Timestamp unformattedTimestamp = new java.sql.Timestamp(
						utilDate.getTime());
				String timestamp = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss")
						.format(unformattedTimestamp);
				stmt = connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery("SELECT COUNT(*)" + "FROM highScores ");
				stmt.executeUpdate("INSERT INTO highScores(playerName, score, levelName, timestamp) VALUES(\""
						+ this.playerName
						+ "\", "
						+ this.score
						+ ", \""
						+ this.levelName + "\", \"" + timestamp + "\");");
				System.out.println("High score saved succesfully!");
				// System.out
				// .println("INSERT INTO minorprojectSOT.highScores(playerName, score, levelName, timestamp) VALUES(\""
				// + this.playerName
				// + "\", "
				// + this.score
				// + ", \""
				// + this.levelName
				// + "\", \""
				// + timestamp + "\");");

			}

			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			// throw new RuntimeException("Cannot connect the database!", e);
			System.out
					.println("CANNOT CONNECT TO THE DATABASE, high score was not saved.");
		} finally {
			// System.out.println("Closing the connection.");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
	}

	/**
	 * Connects to the database to retrieve the saved high scores. Returns a
	 * String representation of the 10 best high scores.
	 * 
	 * @return
	 */
	public static String retrieveFromDB() {
		String res = "";
		try {
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"Cannot find the driver in the classpath!", e);
		}
		try {
			System.out.println("Connecting database...");
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			try {
				stmt = connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				rs = stmt.executeQuery("SELECT * "
						+ "from highScores ORDER BY score");
				int i = 0;
				while (rs.next() && i < 10) {
					i += 1;
					String playerDB = rs.getString("playerName");
					int scoreDB = rs.getInt("score");
					String levelDB = rs.getString("levelName");
					res += playerDB + "\t" + scoreDB + " " + levelDB + "\n";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// throw new RuntimeException("Cannot connect the database!", e);
			System.out.println("Cannot connect to database");
		} finally {
			System.out.println("Closing the connection.");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
		return res;
	}

	/**
	 * Connects to the database to retrieve the saved high scores. Returns an
	 * ArrayList containing the j best high scores
	 * 
	 * Used source:
	 * http://www.developer.com/java/data/jdbc-and-mysql-discussion-
	 * and-sample-code-for-jdbc-programs.html
	 * 
	 * @param j
	 *            amount of high scores to be retrieved
	 * @return
	 */
	public static ArrayList<HighScore> getHighScores(int j) {
		ArrayList<HighScore> res = new ArrayList<HighScore>();
		try {
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"Cannot find the driver in the classpath!", e);
		}
		try {
			System.out.println("Connecting database...");
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");

			try {
				stmt = connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				rs = stmt.executeQuery("SELECT * "
						+ "from highScores ORDER BY score");
				int i = 0;
				while (rs.next() && i < j) {
					i += 1;
					String playerDB = rs.getString("playerName");
					int scoreDB = rs.getInt("score");
					String levelDB = rs.getString("levelName");
					res.add(new HighScore(playerDB, scoreDB, levelDB));
				}
				System.out.println("Highscores retrieved succesfully!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// throw new RuntimeException("Cannot connect the database!", e);
			System.out.println("Cannot connect to database");
		} finally {
			System.out.println("Closing the connection");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
		return res;
	}
}
