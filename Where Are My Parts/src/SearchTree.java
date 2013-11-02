import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchTree {
	int numberOfParts;

	public void printGrid(Part[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {
					System.out.print("null | ");
				} else {
					System.out.print(grid[i][j].name + " | ");
				}
			}
			System.out.println();
		}
	}

	public Part copyPart(Part original){
		String name = original.name;
		int size = original.size;
		int [] x = new int[original.x.length];
		int [] y = new int[original.y.length];
		for(int i=0;i<x.length;i++){
			x[i] = original.x[i];
			y[i] = original.y[i];
		}
		Part copy = new Part(name,size,x,y);
		return copy;
	}
	
	public void bfs(Part[][] grid, int nparts) {
		this.numberOfParts = nparts;
		Queue<Object> grids = new LinkedList<>();
		grids.add(grid);
		boolean flag = false;
		while (!flag) {
			if (!grids.isEmpty()) {
				Part[] gridParts = getParts((Part[][]) grids.peek());
				Queue<Object> parts = new LinkedList<>();
				for (int k = 0; k < gridParts.length; k++) {
					parts.add(gridParts[k]);
				}
				Part[][] polledGridd = (Part[][]) grids.poll();
				while (!parts.isEmpty()) {
					Part polledPartN = copyPart( (Part) parts.peek());
					Part polledPartE = copyPart( (Part) parts.peek());
					Part polledPartS = copyPart( (Part) parts.peek());
					Part polledPartW = copyPart( (Part) parts.poll());
					System.out.println("Go North");
					Part[][] northGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartN, "North");
					if (northGrid.length != 0) {
						boolean northSolution = isSolution(northGrid);
						if (!northSolution) {
							grids.add(northGrid);
						} else {
							System.out.println("North Solution");
							flag = true;
							break;
						}
					}
					System.out.println("Go East");
					Part[][] eastGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartE, "East");
					if (eastGrid.length != 0) {
						boolean eastSolution = isSolution(eastGrid);
						if (!eastSolution) {
							grids.add(eastGrid);
						} else {
							System.out.println("East Solution");
							flag = true;
							break;
						}
					}
					System.out.println("Go South");
					Part[][] southGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartS, "South");
					if (southGrid.length != 0) {
						boolean southSolution = isSolution(southGrid);
						if (!southSolution) {
							grids.add(southGrid);
						} else {
							System.out.println("south Solution");
							flag = true;
							break;
						}
					}
					System.out.println("Go West");
					Part[][] westGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartW, "West");
					if (westGrid.length != 0) {
						boolean westSolution = isSolution(westGrid);
						if (!westSolution) {
							grids.add(westGrid);
						} else {
							System.out.println("west Solution");
							flag = true;
							break;
						}
					}
				}

			} else {
				System.out.println("No Solution");
				flag = true;
			}
		}
	}

	public Part[] getParts(Part[][] grid) {
		Queue<Object> parts = new LinkedList<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {
					// null so nothing here
				} else {
					if (grid[i][j].name.equals("*")) {
						// the grid has an obstacle
						// nothing to do here
					} else {
						// the grid has a part
						if (grid[i][j].size > 1) {
							// it has neighbors but i don't care for now
							// just same as if doesn't
							// i care in their movement
							
							if(parts.contains(grid[i][j])){
								
							}else{
								parts.add(grid[i][j]);
							}
												
						} else {
							// it doens't have neighbors
							parts.add(grid[i][j]);
						}
					}
				}
			}
		}
		Part[] p = new Part[parts.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = (Part) parts.poll();
		}
		// Part[] p = (Part[]) parts.toArray();
		return p;
	}

	public Part[][] doSearch(Part[][] grid, Part p, String direction) {
		Part[][] tempGrid = new Part[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {

				} else {
					tempGrid[i][j] = grid[i][j];
				}
			}
		}
		boolean sameGrid = true;
		switch (direction) {
		case "North":
			while (true) {
				// for(int jj = 0 ; jj < 5 ; jj++){
				for (int i = 0; i < p.size; i++) {
					int x = p.x[i];
					int y = p.y[i];
					if (x - 1 < 0) { // this means we are out the grid for this
										// x
						return new Part[0][0];// return zero sized grid
					} else {
						if (tempGrid[x - 1][y] != null) {
							if (tempGrid[x - 1][y].name.equals("*")) { // there
																		// is
																		// an
																		// obstacle
																		// up
																		// so i
																		// can't
																		// move.
								if(sameGrid){
									return new Part[0][0];
								}else{
									return tempGrid;// causes infinite loop	
								}							
								// return p;// return the same part, no changes
							} else {
								if (tempGrid[x - 1][y].name.equals("part")) {
									// there
									// is
									// a
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
										int[] newPlaceX = new int[p.size + tempGrid[x - 1][y].size];
										int[] newPlaceY = new int[p.size + tempGrid[x - 1][y].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk=0;
										for(int k=p.size;k<newPlaceX.length;k++){
											newPlaceX[k] = tempGrid[x-1][y].x[kk];
											newPlaceY[k] = tempGrid[x-1][y].y[kk];
											kk++;
										}
										//newPlaceX[newPlaceX.length - 1] = x - 1;
										//newPlaceY[newPlaceY.length - 1] = y;
										Part resultedPart = new Part("part",
												p.size + tempGrid[x - 1][y].size, newPlaceX,
												newPlaceY);

										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}

										// tempGrid[x][y] = resultedPart;
										// tempGrid[x - 1][y] = resultedPart;
										// numberOfParts++;
										return tempGrid;
										// return resultedPart;// the new part
										// created
										// of
										// the 2 parts merged
									}
								}
							}
						}
					}
				}
				// update grid with one move up
				// update part x and y with one move up
				for (int a = 0; a < p.size; a++) {
					int x = p.x[a];
					int y = p.y[a];
					x--;
					tempGrid[x][y] = tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.x[a]--;
				}
				sameGrid = false;
				System.out.println();
				printGrid(tempGrid);
				System.out.println();
			}
			// break;
		case "East":
			while (true) {
				// for(int jj = 0 ; jj < 5 ; jj++){
				for (int i = 0; i < p.size; i++) {
					int x = p.x[i];
					int y = p.y[i];
					if (y + 1 >= tempGrid[0].length) { // this means we are out
														// the grid for this
						// x
						return new Part[0][0];// return zero sized grid
					} else {
						if (tempGrid[x][y + 1] != null) {
							if (tempGrid[x][y + 1].name.equals("*")) { // there
																		// is
																		// an
																		// obstacle
																		// up
																		// so i
																		// can't
																		// move.
								if(sameGrid){
									return new Part[0][0];
								}else{
									return tempGrid;// causes infinite loop	
								}
								//return new Part[0][0];
								// return p;// return the same part, no changes
							} else {
								if (tempGrid[x][y + 1].name.equals("part")) {
									// there
									// is
									// a
									// part
									// above, stop
									// and
									// merge
									boolean samePart = false;
									for (int f = 0; f < p.size; f++) {
										if (p.x[f] == x && p.y[f] == y + 1) {
											samePart = true;
										}
									}
									if (!samePart) {
										int[] newPlaceX = new int[p.size + tempGrid[x][y+1].size];
										int[] newPlaceY = new int[p.size + tempGrid[x][y+1].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk=0;
										for(int k=p.size;k<newPlaceX.length;k++){
											newPlaceX[k] = tempGrid[x][y+1].x[kk];
											newPlaceY[k] = tempGrid[x][y+1].y[kk];
											kk++;
										}
										//newPlaceX[newPlaceX.length - 1] = x;
										//newPlaceY[newPlaceY.length - 1] = y + 1;
										Part resultedPart = new Part("part",
												p.size + tempGrid[x][y + 1].size, newPlaceX,
												newPlaceY);

										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}

										// tempGrid[x][y] = resultedPart;
										// tempGrid[x - 1][y] = resultedPart;
										// numberOfParts++;
										return tempGrid;
										// return resultedPart;// the new part
										// created
										// of
										// the 2 parts merged
									}
								}
							}
						}
					}
				}
				// update grid with one move up
				// update part x and y with one move up
				for (int a = 0; a < p.size; a++) {
					int x = p.x[a];
					int y = p.y[a];
					// x--;
					y++;
					tempGrid[x][y] = tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.y[a]++;
				}
				sameGrid = false;
				System.out.println();
				printGrid(tempGrid);
				System.out.println();
			}
			// break;
		case "South":
			while (true) {
				// for(int jj = 0 ; jj < 5 ; jj++){

				for (int i = 0; i < p.size; i++) {
					int x = p.x[i];
					int y = p.y[i];
					if ((x + 1) >= tempGrid.length) { // this means we are out
														// the
														// grid for this
						// x
						return new Part[0][0];// return zero sized grid
					} else {
						if (tempGrid[x + 1][y] != null) {
							if (tempGrid[x + 1][y].name.equals("*")) { // there
																		// is
																		// an
																		// obstacle
																		// up
																		// so i
																		// can't
																		// move.
								if(sameGrid){
									return new Part[0][0];
								}else{
									return tempGrid;// causes infinite loop	
								}
								//return new Part[0][0];
								// return p;// return the same part, no changes
							} else {
								if (tempGrid[x + 1][y].name.equals("part")) {
									// there
									// is
									// a
									// part
									// above, stop
									// and
									// merge
									boolean samePart = false;
									for (int f = 0; f < p.size; f++) {
										if (p.x[f] == x + 1 && p.y[f] == y) {
											samePart = true;
										}
									}
									if (!samePart) {
										int[] newPlaceX = new int[p.size + tempGrid[x+1][y].size];
										int[] newPlaceY = new int[p.size + tempGrid[x+1][y].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk=0;
										for(int k=p.size;k<newPlaceX.length;k++){
											newPlaceX[k] = tempGrid[x+1][y].x[kk];
											newPlaceY[k] = tempGrid[x+1][y].y[kk];
											kk++;
										}
										//newPlaceX[newPlaceX.length - 1] = x + 1;
										//newPlaceY[newPlaceY.length - 1] = y;
										Part resultedPart = new Part("part",
												p.size + tempGrid[x+1][y].size, newPlaceX,
												newPlaceY);
										// tempGrid[x][y] = resultedPart;
										// tempGrid[x + 1][y] = resultedPart;
										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}
										// numberOfParts++;
										return tempGrid;
										// return resultedPart;// the new part
										// created
										// of
										// the 2 parts merged
									}
								}
							}
						}
					}
				}
				// update grid with one move up
				// update part x and y with one move up
				for (int a = 0; a < p.size; a++) {
					int x = p.x[a];
					int y = p.y[a];
					x++;
					tempGrid[x][y] = tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.x[a]++;
				}
				sameGrid = false;
				System.out.println();
				printGrid(tempGrid);
				System.out.println();
			}
		case "West":
			while (true) {
				// for(int jj = 0 ; jj < 5 ; jj++){
				for (int i = 0; i < p.size; i++) {
					int x = p.x[i];
					int y = p.y[i];
					if (y - 1 < 0) { // this means we are out the grid for this
										// x
						return new Part[0][0];// return zero sized grid
					} else {
						if (tempGrid[x][y - 1] != null) {
							if (tempGrid[x][y - 1].name.equals("*")) { // there
																		// is
																		// an
																		// obstacle
																		// up
																		// so i
																		// can't
																		// move.
								if(sameGrid){
									return new Part[0][0];
								}else{
									return tempGrid;// causes infinite loop	
								}
								
								//return new Part[0][0];
								// return p;// return the same part, no changes
							} else {
								if (tempGrid[x][y - 1].name.equals("part")) {
									// there
									// is
									// a
									// part
									// above, stop
									// and
									// merge
									boolean samePart = false;
									for (int f = 0; f < p.size; f++) {
										if (p.x[f] == x && p.y[f] == (y - 1)) {
											samePart = true;
										}
									}
									if (!samePart) {
										int[] newPlaceX = new int[p.size + tempGrid[x][y-1].size];
										int[] newPlaceY = new int[p.size + tempGrid[x][y-1].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk=0;
										for(int k=p.size;k<newPlaceX.length;k++){
											newPlaceX[k] = tempGrid[x][y-1].x[kk];
											newPlaceY[k] = tempGrid[x][y-1].y[kk];
											kk++;
										}
										//newPlaceX[newPlaceX.length - 1] = x;
										//newPlaceY[newPlaceY.length - 1] = y - 1;
										Part resultedPart = new Part("part",
												p.size + tempGrid[x][y - 1].size, newPlaceX,
												newPlaceY);

										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}

										// tempGrid[x][y] = resultedPart;
										// tempGrid[x - 1][y] = resultedPart;
										// numberOfParts++;
										return tempGrid;
										// return resultedPart;// the new part
										// created
										// of
										// the 2 parts merged
									}
								}
							}
						}
					}
				}
				// update grid with one move up
				// update part x and y with one move up
				for (int a = 0; a < p.size; a++) {
					int x = p.x[a];
					int y = p.y[a];
					// x--;
					y--;
					tempGrid[x][y] = tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.y[a]--;
				}
				sameGrid = false;
				System.out.println();
				printGrid(tempGrid);
				System.out.println();
			}
			// break;
		}

		return null;
	}

	public boolean isSolution(Part[][] grid) {
		// either solution then return true
		// if not but can proceed then return false
		// solution when all the parts have the size of int numberofparts

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {
					// null here
				} else {
					if (grid[i][j].name.equals("*")) {
						// obstacle here
					} else {
						if (grid[i][j].size != numberOfParts) {
							return false;
						}
					}
				}
			}
		}
		return true;
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
					if (grid[p.x[0]][j] != null && grid[p.x[0]][j].equals("*")) {
						hitSth = true;
						newPart = p;
						break;
					}
				}

			}
			// if (!hitSth)
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
		Part part1 = new Part("part", 1, new int[] { 0 }, new int[] { 0 });
		Part part2 = new Part("part", 1, new int[] { 0 }, new int[] { 2 });
		Part part3 = new Part("part", 1, new int[] { 2 }, new int[] { 0 });
		Part part4 = new Part("*", 0, new int[] { 1 }, new int[] { 1 });
		Part part5 = new Part("part", 1, new int[] { 1 }, new int[] { 2 });
		Part part6 = new Part("*", 0, new int[] { 2 }, new int[] { 2 });
		grid[0][0] = "part";
		grid[0][2] = "part";
		grid[1][0] = "*";
		grid[3][1] = "part";
		SearchTree k = new SearchTree();
		// Part returnedPart = k.raafatSearch(part1, grid, "East");
		// System.out.println(returnedPart.name);

		Part[][] testGrid = new Part[3][3];
		testGrid[0][0] = part1;
		testGrid[0][2] = part2;
		testGrid[2][0] = part3;
		testGrid[1][1] = part4;
		//testGrid[1][2] = part5;
		//testGrid[2][2] = part6;
		k.printGrid(testGrid);
		k.bfs(testGrid, 3);
		// k.printGrid(testGrid);

	}

}
