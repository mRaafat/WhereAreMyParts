import java.util.ArrayList;

//import java.lang.reflect.Array;

public class WhereAreMyParts extends SearchTree {

	String[][] grid;
	String obstacle;
	String robotPart;

	public WhereAreMyParts() {
		// representation of the robot part and obstacle
		this.obstacle = "*";
		this.robotPart = "p";
	}

	public void GenGrid() {
		// int randomRows,randomColumns = 0;
		// at least one
		int randomRows = 1 + (int) (Math.random());
		int randomColumns = 1 + (int) (Math.random());
		int numberOfRobotParts = 6 + (int) (Math.random() * (11));
		int[] robotPartsPositions = new int[numberOfRobotParts];

		grid = new String[randomRows][randomColumns];
		// Number of robot parts are minimum 6 and maximum 16 "An assumption"

		// Generating positions for the robot parts
		for (int i = 0; i < robotPartsPositions.length; i++) {
			robotPartsPositions[i] = (int) (Math.random() * grid.length);
		}
		// Generating number of Obstacles
		int numberOfObstacles = (int) (Math.random() * grid.length);
		while (numberOfObstacles == 0
				|| numberOfObstacles >= grid.length - numberOfRobotParts) {
			numberOfObstacles = (int) (Math.random() * grid.length);
		}

		// Generating obstacles positions
		int[] obstaclesPositions = new int[numberOfObstacles];
		for (int i = 0; i < numberOfRobotParts; i++) {
			obstaclesPositions[i] = (int) (Math.random() * grid.length);
		}

	}

	public ArrayList<String> Search() {
		return null;
	}
}