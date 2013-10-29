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
		// minimum possible of grid squares is 20 and maximum 100
		int randomRows = 5 + (int) Math.ceil(Math.random() * 10);
		int randomColumns = 5 + (int) Math.ceil(Math.random() * 10);
		// Number of robot parts are minimum 6 and maximum 16 "An assumption"
		int numberOfRobotParts = 6 + (int) (Math.random() * (11));
		// Number of Obstacles is equal half of the total number of squares in
		// the grid minus number of grids occupied by the robot parts
		int randomObstacleNumber = (int) (Math.random() * ((randomRows * randomColumns) - numberOfRobotParts));
		int[] randomPartPosition = new int[numberOfRobotParts];
		int[] randomObstaclePosition = new int[randomObstacleNumber];

		grid = new String[randomRows][randomColumns];

		// Creating random positions in the grid to put the parts of robots in
		for (int k = 0; k < randomPartPosition.length; k++) {
			randomPartPosition[k] = (int) (Math.random() * (grid.length + 1));
		}
		// Creating random positions in the grid to put the obstacles in
		for (int j = 0; j < randomObstacleNumber; j++) {
			int random = (int) ((Math.random() * (grid.length) + 1));
			
		}

		for (int i = 0; i < numberOfRobotParts; i++) {

		}

	}

	public ArrayList<String> Search() {
		return null;
	}
}