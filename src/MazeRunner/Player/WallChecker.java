package MazeRunner.Player;

import MazeRunner.Objects.Maze;
import MazeRunner.Fundamental.MazeRunner;

public class WallChecker {

	private Player player;
	private int checkdistance;
	private Maze maze;

	public WallChecker(Player player, int checkdistance, Maze maze) {
		this.player = player;
		this.checkdistance = checkdistance;
		this.maze = maze;
	}

	public void check() {
		if (!MazeRunner.GOD_MODE) {
			if (maze.isWall(
					player.getLocationX() + checkdistance
							* -Math.sin(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ() + checkdistance
							* -Math.cos(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveForward(false);
			} else {
				player.setCanMoveForward(true);
			}
			// Check backward direction for obstacles
			if (maze.isWall(
					player.getLocationX() - checkdistance
							* -Math.sin(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ() - checkdistance
							* -Math.cos(Math.PI * player.getHorAngle() / 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveBack(false);
			} else {
				player.setCanMoveBack(true);
			}
			// Check left direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							+ checkdistance
							* -Math.sin(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							+ checkdistance
							* -Math.cos(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveLeft(false);
			} else {
				player.setCanMoveLeft(true);
			}
			// check right direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							- checkdistance
							* -Math.sin(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							- checkdistance
							* -Math.cos(Math.PI * (player.getHorAngle() + 90)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setCanMoveRight(false);

			} else {
				player.setCanMoveRight(true);
			}

			// Check left-forward direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							+ checkdistance
							* 2
							* -Math.sin(Math.PI * (player.getHorAngle() + 45)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							+ checkdistance
							* 2
							* -Math.cos(Math.PI * (player.getHorAngle() + 45)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setLeftForwardWall(true);
			} else {
				player.setLeftForwardWall(false);
			}

			// Check right-forward direction for obstacles
			if (maze.isWall(
					player.getLocationX()
							- checkdistance
							* 2
							* -Math.sin(Math.PI * (player.getHorAngle() + 135)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180),
					player.getLocationZ()
							- checkdistance
							* 2
							* -Math.cos(Math.PI * (player.getHorAngle() + 135)
									/ 180)
							* Math.cos(Math.PI * player.getVerAngle() / 180))) {
				player.setRightForwardWall(true);
			} else {
				player.setRightForwardWall(false);
			}
		}
		else{
			player.setCanMoveForward(true);
			player.setCanMoveBack(true);
			player.setCanMoveLeft(true);
			player.setCanMoveRight(true);
			player.setLeftForwardWall(false);
			player.setRightForwardWall(false);
		}
	}
}