import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchTree {
	public static void main(String[] args) {

	}

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
}
