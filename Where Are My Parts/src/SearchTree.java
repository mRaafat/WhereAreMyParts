import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchTree {

	public boolean bfSearch(String[][] grid) {
		/*
		 * Assume there is a parent node. Explored to find the rest of the
		 * nodes. These nodes are the parts of the robot. The Robot parts are
		 * Queued into the Queue to be explored.
		 */
		int numberOfParts = 0;
		Queue<Part> searchParts = new LinkedList<Part>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j].equals("part")) {
					int[] posX = { i };
					int[] posY = { j };
					Part newPart = new Part("part" + numberOfParts, 1, posX,
							posY);
					searchParts.add(newPart);
					numberOfParts++;
				}
			}
		}

		while (!searchParts.isEmpty()) {
			Part northPart = search((Part) searchParts.poll(), grid, "North",
					numberOfParts);
			Part eastPart = search((Part) searchParts.poll(), grid, "east",
					numberOfParts);
			Part southPart = search((Part) searchParts.poll(), grid, "south",
					numberOfParts);
			Part westPart = search((Part) searchParts.poll(), grid, "west",
					numberOfParts);
			if (northPart.size != 0) {
				searchParts.add(northPart);
			}
			if (eastPart.size != 0) {
				searchParts.add(eastPart);
			}
			if (southPart.size != 0) {
				searchParts.add(southPart);
			}
			if (westPart.size != 0) {
				searchParts.add(westPart);
			}

		}
		if (numberOfParts == 0) {
			// solution
			// we have to restrict from the beginning that there is at least one
			// part
			return true;
		} else {
			// failure
			return false;
		}

	}

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

	public Part search(Part p, String[][] grid, String direction, int nOfParts) {
		/*
		 * This method -takes a part/node to be explored in the 4 directions.
		 * -if hit another part then a new part/node is created on the next
		 * level with the 2 parts. -if hit an obstacle it's considered to be a
		 * new part,removed from the queue and added to the new level -if hit
		 * fence then this branch fails. it returns the new part created, if the
		 * new part has size = 0, then there was a fence and dead. when 2 parts
		 * are merged, the number of parts variable should be decreased by 1
		 */
		Part resultedPart;
		switch (direction) {
		case "North":
			boolean flag = true;
			while (flag) {// while not returned, keep moving up
				for (int i = 0; i < p.size; i++) {
					int x = p.x[i];
					int y = p.y[i];
					if (x - 1 < 0) { // this means we are out the grid for this
										// x
						flag = false;
						return new Part("", 0, null, null);// return a zero
															// sized
															// part
					} else {
						if (grid[x - 1][y].equals("*")) { // there is an
															// obstacle up
															// so i can't move.
							flag = false;
							return p;// return the same part, no changes
						} else {
							if (grid[x - 1][y].equals("part")) {// there is a
																// part
																// above, stop
																// and
																// merge
								boolean samePart = false;
								for (int f = 0; f < p.size; f++) {
									if (p.x[f] == x - 1 && p.y[f] == y) {
										samePart = true;
									}
								}
								if (!samePart) {
									int[] newPlaceX = new int[p.x.length + 1];
									int[] newPlaceY = new int[p.y.length + 1];
									for (int k = 0; k < newPlaceX.length - 1; k++) {
										newPlaceX[k] = p.x[k];
										newPlaceY[k] = p.y[k];
									}
									newPlaceX[newPlaceX.length - 1] = x - 1;
									newPlaceY[newPlaceY.length - 1] = y;
									resultedPart = new Part("PR1", p.size + 1,
											newPlaceX, newPlaceY);
									nOfParts--;
									flag = false;
									return resultedPart;// the new part created
														// of
														// the 2 parts merged
								}
							}
						}
					}
				}
				// update grid with one move up
				// update part x and y with one move up
				for (int a = 0; a < p.size; a++) {
					grid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.x[a]--;
				}
				for (int a = 0; a < p.size; a++) {
					grid[p.x[a]][p.y[a]] = "part";
				}
			}
			;
			break;
		case "east":
			;
			break;
		case "south":
			;
			break;
		case "west":
			;
			break;
		}

		return p;
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
