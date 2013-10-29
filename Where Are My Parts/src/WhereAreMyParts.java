import java.util.ArrayList;

//import java.lang.reflect.Array;

public class WhereAreMyParts extends SearchTree {

	String[][] grid;
	String obstacle;
	String robotPart;
	int gridSize;

	public WhereAreMyParts() {
		// representation of the robot part and obstacle
		this.obstacle = "*";
		this.robotPart = "p";
	}

	public void GenGrid() {
		// int randomRows,randomColumns = 0;
		// at least one
		int randomRows = 1 + (int) (Math.random() * 100);
		int randomColumns = 1 + (int) (Math.random() * 100);
		int numberOfRobotParts = 6 + (int) (Math.random() * (11));
		int[] robotPartsPositions = new int[numberOfRobotParts];

		grid = new String[randomRows][randomColumns];
		gridSize = randomRows * randomColumns;
		// Number of robot parts are minimum 6 and maximum 16 "An assumption"

		// Generating positions for the robot parts and placing them in the grid
		for (int i = 0; i < robotPartsPositions.length; i++) {
			robotPartsPositions[i] = (int) (Math.random() * gridSize);
			int x = (int) (robotPartsPositions[i] / randomColumns);
			int y = robotPartsPositions[i] - (x * randomColumns);
			grid[x][y] = "part";
			System.out.println(grid[x][y]);
		}

		// Generating number of Obstacles
		int numberOfObstacles = (int) (Math.random() * gridSize);
		while (numberOfObstacles == 0
				|| numberOfObstacles >= gridSize - numberOfRobotParts) {
			numberOfObstacles = (int) (Math.random() * gridSize);
		}

		// Generating obstacles positions and placing them in the grid
		int[] obstaclesPositions = new int[numberOfObstacles];
		for (int i = 0; i < numberOfRobotParts; i++) {
			obstaclesPositions[i] = (int) (Math.random() * gridSize);
			int x = (int) (obstaclesPositions[i] / randomColumns);
			int y = obstaclesPositions[i] - (x * randomColumns);
			if (grid[x][y]!=null && grid [x][y].equals("part"))
				continue;
			else
				grid[x][y] = "*";
		}

		// Inserting the parts in the right position

	}

	public ArrayList<String> Search() {
		return null;
	}

	public static void main(String[] args) {
		WhereAreMyParts myObject = new WhereAreMyParts();
		myObject.GenGrid();
	}
}