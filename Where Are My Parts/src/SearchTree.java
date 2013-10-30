import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchTree {


	/*
	 * public boolean dfSearch(String[][] grid) { int numberOfParts = 0;
	 * Stack<Part> robotParts = new Stack<Part>(); for(int i = 0;
	 * i<grid.length;i++){ for(int j = 0;j<grid[i].length;j++){
	 * if(grid[i][j].equals("part")){
	 * 
	 * Part myPart = new Part(); robotParts.addElement(grid[i][j]); } } }
	 * 
	 * return false; }
	 */
	/*
	 * The following method works as follows. It has a switch statment that
	 * works according to the direction that I am moving in.
	 * "This method is currently working assuming i moving only one part" If I
	 * am moving in the north direction, then I save to a variable the x
	 * coordinate to the next cell that i should move to, because the Y
	 * coordinate is unchangable in the north direction, if i hit part, i create
	 * new part with x coordinate list containing the x of the cell that part
	 * has hit, and x+1, and return this part. Repeat these steps for the rest
	 * of parts, considering the position i am moving in.
	 */
	public Part raafatSearch(Part p, String[][] grid, String direction) {
		boolean hitSth = false;
		Part newPart = new Part();
		switch (direction) {
		case ("North"):
			// That will be in case of only one part, I will be handling more
			// than one part later

			for (int i = p.x[0] - 1; i >= 0; i--) {
				if (grid[i].equals("part")) {

					newPart = new Part("part", 2, new int[] { i, i + 1 },
							new int[] { p.y[0] });
					hitSth = true;

				} else {
					if (grid[i].equals("*")) {
						hitSth = true;
						newPart = p;
					}
				}

			}
			if (!hitSth)
				break;

		case ("East"):

			for (int j = p.y[0] + 1; j <= grid[0].length; j++) {
				if (grid[p.x[0]][j] != null && grid[p.x[0]][j].equals("part")) {

					newPart = new Part("part", 2, new int[] { p.x[0] },
							new int[] { j - 1, j });
					hitSth = true;
					break;

				} else {
					if (grid[p.x[0]][j]!=null&&grid[p.x[0]][j].equals("*")) {
						hitSth = true;
						newPart = p;
						break;
					}
				}

			}
			//if (!hitSth)
				break;
		case ("West"):

			for (int k = p.y[0] - 1; k >= 0; k--) {
				if (grid[p.x[0]][k].equals("part")) {

					newPart = new Part("part", 2, new int[] { p.x[0] },
							new int[] { k, k + 1 });
					hitSth = true;
					// return newPart;
				} else {
					if (grid[p.x[0]][k].equals("*")) {
						hitSth = true;
						newPart = p;
					}
				}

			}
			if (!hitSth)
				break;
		case ("South"):

			for (int l = p.x[0] + 1; l <= grid.length; l++) {
				if (grid[p.x[l]][0].equals("part")) {

					newPart = new Part("part", 2, new int[] { l - 1, l },
							new int[] { p.y[0] });
					hitSth = true;
					return newPart;
				} else {
					if (grid[p.x[l]][0].equals("*")) {
						hitSth = true;
						newPart = p;
					}
				}

			}
			if (!hitSth)
				break;
		}
		return newPart;
	}


	public static void main(String[] args) {
		String[][] grid = new String[4][3];
		Part part1 = new Part("part1", 1, new int[] { 0 }, new int[] { 0 });
		Part part2 = new Part("part2", 1, new int[] { 0 }, new int[] { 2 });
		Part part3 = new Part("part3", 1, new int[] { 3 }, new int[] { 1 });
		grid[0][0] = "part";
		grid[0][2] = "part";
		grid[1][0] = "*";
		grid[3][1] = "part";
		SearchTree k = new SearchTree();
		Part returnedPart = k.raafatSearch(part1, grid, "East");
		System.out.println(returnedPart.name);
	}

}
