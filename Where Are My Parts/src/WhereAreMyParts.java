import java.util.ArrayList;

//import java.lang.reflect.Array;

public class WhereAreMyParts extends SearchTree {

	String[][] grid;
	String obstacle;
	String robotPart;

	public WhereAreMyParts() {
		this.obstacle = "*";
		this.robotPart = "p";
	}

	public void GenGrid() {
		//int randomRows,randomColumns = 0;
		//minimum possible of grid squares is 20 and maximum 100
		int randomRows = 20+(int)Math.ceil(Math.random()*80),randomColumns =20 + (int)Math.ceil(Math.random()*80);
		//Number of robot parts are minimum 6 and maximum 16 "An assumption"
		int numberOfRobotParts = 6 + (int)(Math.random()*(11));
		//Number of Obstacles is equal half of the total number of squares in the grid minus number of grids occupied by the robot parts
		int randomObstacleNumber = (int)(0.5*((randomRows*randomColumns)-numberOfRobotParts));
		grid = new String[randomRows][randomColumns];
		for (int i = 0; i < numberOfRobotParts ; i++){
			
		}

	}

	public ArrayList<String> Search() {
		return null;
	}
}