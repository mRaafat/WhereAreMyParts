import java.util.ArrayList;

//import java.lang.reflect.Array;

public class WhereAreMyParts extends SearchTree {

	Part[][] grid;
	// String obstacle;
	// String robotPart;
	int gridSize;
	int nparts;

	public WhereAreMyParts() {
		// representation of the robot part and obstacle
		// this.obstacle = "*";
		// this.robotPart = "p";
	}

	public Part[][] GenGrid() {
		// int randomRows,randomColumns = 0;
		// at least one
		int randomRows = 2 + (int) (Math.random() * 15);
		int randomColumns = 2 + (int) (Math.random() * 15);
		int numberOfRobotParts = 2 + (int) (Math.random() * (11));
		gridSize = randomRows * randomColumns;
		while (numberOfRobotParts == 0
				|| numberOfRobotParts >= gridSize) {
			numberOfRobotParts = (int) (Math.random() * (11));
		}
		
		int[] robotPartsPositions = new int[numberOfRobotParts];
		grid = new Part[randomRows][randomColumns];
		
		// Number of robot parts are minimum 6 and maximum 16 "An assumption"

		
		
		
		// Generating positions for the robot parts and placing them in the grid
		for (int i = 0; i < robotPartsPositions.length; i++) {
			
			robotPartsPositions[i] = (int) (Math.random() * gridSize);
			int x = (int) (robotPartsPositions[i] / randomColumns);
			int y = robotPartsPositions[i] - (x * randomColumns);
			if(grid[x][y] == null){
				grid[x][y] = new Part("part", 1, new int[] { x }, new int[] { y });	
			}else{
				numberOfRobotParts--;
			}
		}
		System.out.println(numberOfRobotParts);
		this.nparts = numberOfRobotParts;
		// Generating number of Obstacles
		int numberOfObstacles = (int) (Math.random() * gridSize);
		
		while (numberOfObstacles == 0
				|| numberOfObstacles >= gridSize - numberOfRobotParts) {
			numberOfObstacles = (int) (Math.random() * gridSize);
		}
		

		// Generating obstacles positions and placing them in the grid
		int[] obstaclesPositions = new int[numberOfObstacles];
		for (int i = 0; i < numberOfObstacles; i++) {
			obstaclesPositions[i] = (int) (Math.random() * gridSize);
			int x = (int) (obstaclesPositions[i] / randomColumns);
			int y = obstaclesPositions[i] - (x * randomColumns);
			if (grid[x][y] != null && grid[x][y].name.equals("part"))
				continue;
			else
				grid[x][y] = new Part("*", 0, new int[] { x }, new int[] { y });
		}

		return grid;
		// Inserting the parts in the right position

	}

	public void search(Part[][] grid, String Strategy, boolean visualize) {
		SearchTree st = new SearchTree();
		switch (Strategy) {
		case "BF":
			st.printGrid(grid);
			st.bfs(grid, this.nparts);
			st.reset();
			break;
		case "DF":
			;
			break;
		case "ID":
			st.printGrid(grid);			
			st.ids(grid, this.nparts);
			st.reset();
			break;
		case "GR1":
			;
			break;
		case "GR2":
			;
			break;
		case "AS1":
			st.printGrid(grid);
			st.aStar1(grid, this.nparts);
			st.reset();
			break;
		case "AS2":
			st.printGrid(grid);
			st.aStar2(grid, this.nparts);
			st.reset();
			break;
		default:
			System.out.println("Invalid");
		}
	}

	public static void main(String[] args) {
		WhereAreMyParts myObject = new WhereAreMyParts();
		Part[][] p = myObject.GenGrid();
		myObject.search(p, "AS1", true);
		myObject.search(p, "AS2", true);
		myObject.search(p, "BF", true);
		myObject.search(p, "ID", true);
		// myObject.search(p, "AS2", true);

	}
}
