package MazeRunner;

public class HighScore {

	private String playerName;
	private int score;
	private String levelName;
	
	public HighScore(String playername, int score, String levelname) {
		this.playerName = playername;
		this.score = score;
		this.levelName = levelname; 
	}

	public void update(int deltaTime){
		this.score += deltaTime;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public String getPlayerName(){
		return this.playerName;
	}
	
	public String getLevelName(){
		return this.levelName;
	}
}
