import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SearchTree {
	int numberOfParts;
	int expandedNodes = 0;
	public void printGrid(Part[][] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {
					System.out.print("null | ");
				} else {
					if (grid[i][j].name == "*") {
						System.out.print("**** | ");
					} else {
						System.out.print(grid[i][j].name + " | ");
					}

				}
			}
			System.out.println();
		}
	}

	public Part copyPart(Part original) {
		String name = original.name;
		int size = original.size;
		int[] x = new int[original.x.length];
		int[] y = new int[original.y.length];
		for (int i = 0; i < x.length; i++) {
			x[i] = original.x[i];
			y[i] = original.y[i];
		}
		Part copy = new Part(name, size, x, y);
		copy.pathCost = original.pathCost;
		copy.heuristicCost = original.heuristicCost;
		copy.parent = original.parent;
		copy.hitPartX = original.hitPartX;
		copy.hitPartY = original.hitPartY;
		return copy;
	}

	/*
	 * A* Heuristic 2
	 */
	public void aStar2(Part[][] grid, int nparts) {
		this.numberOfParts = nparts;
		Queue<Object> grids = new LinkedList<>();
		Queue<Integer> gridsCost = new LinkedList<>();
		grids.add(grid);
		gridsCost.add(0);
		Part[] gridParts = getParts(grid);
		for (int i = 0; i < gridParts.length; i++) {
			gridParts[i].pathCost = 0;
			gridParts[i].heuristicCost = distinctRows(grid);// number of rows
		}
		boolean flag = false;
		while (!grids.isEmpty()) {
			Part[][] polledGridd = (Part[][]) grids.poll();
			int polledCost = gridsCost.poll();
			if (isSolution((Part[][]) polledGridd)) {
				System.out.println("Solution to A* 2");
				System.out.println("Cost: " + getPart(polledGridd).pathCost);
				System.out.println("Expanded Nodes: " + expandedNodes);
				flag = true;
				// printGrid(polledGridd);
				printTrace(polledGridd);
				break;
			} else {
				// boolean cont =
				expandedNodes++;
				aStar2heuristic(grids, polledGridd, gridsCost, polledCost);
				// if(!cont){
				// break;
				// }
			}
		}
		if (!flag) {
			System.out.println("A* 2 failed to find solution");
		}
	}

	public int distinctRows(Part[][] grid) {
		int rows = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != null) {
					if (grid[i][j].name == "part") {
						rows++;
						break;
					}
				}
			}
			continue;
		}
		return rows;
	}

	public void calculateCostHeuristic2(Part[][] grid, Part[] gridParts) {
		int hn = distinctRows(grid);
		for (int i = 0; i < gridParts.length; i++) {
			gridParts[i].heuristicCost = hn;
		}
	}

	public void aStar2heuristic(Queue grids, Part[][] polledGridd,
			Queue gridsCost, int polledCost) {
		/*
		 * this method takes the polled grid and takes the parts out of it
		 * -calculates f(n) for them and enqueue them into the queue from least
		 * to maximum This Heuristic is about expanding the grid with less
		 * number of rows that parts place before expanding the rest.
		 */
		Part[] gridParts = getParts(polledGridd);
		calculateCostHeuristic2(polledGridd, gridParts);
		for (int i = 0; i < gridParts.length; i++) {// bubble sort array to f(n)
			for (int x = 1; x < gridParts.length - i; x++) {
				if (gridParts[x - 1].pathCost + gridParts[x - 1].heuristicCost > gridParts[x].pathCost
						+ gridParts[x].heuristicCost) {
					Part temp = gridParts[x - 1];
					gridParts[x - 1] = gridParts[x];
					gridParts[x] = temp;
				}
			}
		}
		expandParts(grids, polledGridd, gridParts, gridsCost, polledCost);
	}

	/*
	 * A* Heuristic 1
	 */

	public void aStar1(Part[][] grid, int nparts) {
		this.numberOfParts = nparts;
		Queue<Object> grids = new LinkedList<>();
		Queue<Integer> gridsCost = new LinkedList<>();
		grids.add(grid);
		gridsCost.add(0);
		Part[] gridParts = getParts(grid);
		for (int i = 0; i < gridParts.length; i++) {
			gridParts[i].pathCost = 0;
			gridParts[i].heuristicCost = numberOfParts;
		}
		boolean flag = false;
		while (!grids.isEmpty()) {
			Part[][] polledGridd = (Part[][]) grids.poll();
			int polledCost = gridsCost.poll();
			if (isSolution((Part[][]) polledGridd)) {
				System.out.println("Solution to A*");
				System.out.println("Cost: " + getPart(polledGridd).pathCost);
				System.out.println("Expanded Nodes: " + expandedNodes);
				flag = true;
				// printGrid(polledGridd);
				printTrace(polledGridd);
				break;
			} else {
				// boolean cont =
				expandedNodes++;
				aStar1heuristic(grids, polledGridd, gridsCost, polledCost);
				// if(!cont){
				// break;
				// }
			}
		}
		if (!flag) {
			System.out.println("A* failed to find sol.");
		}
	}

	public int distinctParts(Part[] gridParts) {
		LinkedList p = new LinkedList<>();
		for (int i = 0; i < gridParts.length; i++) {
			if (!p.contains(gridParts[i])) {
				p.add(gridParts[i]);
			}
		}
		return p.size();
	}

	public void calculateCostHeuristic1(Part[][] grid, Part[] gridParts) {
		int hn = distinctParts(gridParts);
		for (int i = 0; i < gridParts.length; i++) {
			// int gn = 0; //grid.length * grid[0].length * gridParts[i].size;
			
			// gridParts[i].pathCost = gridParts[i].pathCost + gn;
			gridParts[i].heuristicCost = hn;
		}
	}

	public void expandParts(Queue grids, Part[][] polledGridd,
			Part[] gridParts, Queue gridsCost, int polledCost) {
		Queue<Part> parts = new LinkedList<Part>();
		for (int k = 0; k < gridParts.length; k++) {// true bec we
													// will expand
													// then search
			parts.add(gridParts[k]);
		}
		while (!parts.isEmpty()) {
			Part polledPartN = copyPart((Part) parts.peek());
			Part polledPartE = copyPart((Part) parts.peek());
			Part polledPartS = copyPart((Part) parts.peek());
			Part polledPartW = copyPart((Part) parts.poll());
			// west
			Part[][] westGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartW, "West");
			if (westGrid.length != 0) {
				grids.add(westGrid);
				gridsCost.add(polledPartW.pathCost + polledPartW.heuristicCost);
			}
			// south
			Part[][] southGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartS, "South");
			if (southGrid.length != 0) {
				grids.add(southGrid);
				gridsCost.add(polledPartS.pathCost + polledPartS.heuristicCost);
			}
			// east
			Part[][] eastGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartE, "East");
			if (eastGrid.length != 0) {
				grids.add(eastGrid);
				gridsCost.add(polledPartE.pathCost + polledPartE.heuristicCost);
			}
			// north
			Part[][] northGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartN, "North");
			if (northGrid.length != 0) {
				grids.add(northGrid);
				gridsCost.add(polledPartN.pathCost + polledPartN.heuristicCost);
			}
		}
		sortGrids(grids, gridsCost);
	}

	private void sortGrids(Queue grids, Queue<Integer> gridsCost) {
		Object[] sortGrids = new Object[grids.size()];
		int[] sortCost = new int[gridsCost.size()];
		int sizeOfGrids = grids.size();
		for (int i = 0; i < sizeOfGrids; i++) {
			sortGrids[i] = grids.poll();
			sortCost[i] = gridsCost.poll();
		}
		for (int i = 0; i < sortCost.length; i++) {// bubble sort array to f(n)
			for (int x = 1; x < sortCost.length - i; x++) {
				if (sortCost[x - 1] > sortCost[x]) {
					int temp = sortCost[x - 1];
					Part[][] tp = (Part[][]) sortGrids[x - 1];
					sortCost[x - 1] = sortCost[x];
					sortGrids[x - 1] = sortGrids[x];
					sortCost[x] = temp;
					sortGrids[x] = tp;
				}
			}
		}
		grids.clear();
		gridsCost.clear();
		for (int i = 0; i < sortGrids.length; i++) {
			grids.add(sortGrids[i]);
			gridsCost.add(sortCost[i]);
		}
	}

	public void aStar1heuristic(Queue grids, Part[][] polledGridd,
			Queue gridsCost, int polledCost) {
		/*
		 * this method takes the polled grid and takes the parts out of it
		 * -calculates f(n) for them and enqueue them into the queue from least
		 * to maximum This Heuristic is about expanding the grid with less
		 * number of parts before expanding the rest.
		 */
		Part[] gridParts = getParts(polledGridd);
		calculateCostHeuristic1(polledGridd, gridParts);
		for (int i = 0; i < gridParts.length; i++) {// bubble sort array to f(n)
			for (int x = 1; x < gridParts.length - i; x++) {
				if (gridParts[x - 1].pathCost + gridParts[x - 1].heuristicCost > gridParts[x].pathCost
						+ gridParts[x].heuristicCost) {
					Part temp = gridParts[x - 1];
					gridParts[x - 1] = gridParts[x];
					gridParts[x] = temp;
				}
			}
		}
		expandParts(grids, polledGridd, gridParts, gridsCost, polledCost);
	}

	/*
	 * Iterative DEEPING SEARCH
	 */
	public void ids(Part[][] grid, int nparts) {
		this.numberOfParts = nparts;
		boolean flag = true;
		if (isSolution(grid)) {
			System.out.println("Main Grid is solution");
		} else {
			for (int d = 1; flag; d++) {// condition stopping ids
				flag = idsHelp(grid, nparts, d);
			}
		}
	}

	public boolean idsHelp(Part[][] grid, int nparts, int depth) {
		Stack<Object> grids = new Stack<>();
		Stack<Integer> gridsDepth = new Stack<>();
		grids.push(grid);
		gridsDepth.push(1);
		boolean moreLevel = false;
		while (!grids.isEmpty()) {
			Part[][] polledGridd = (Part[][]) grids.pop();
			expandedNodes++;
			int polledLevel = gridsDepth.pop();
			if (isSolution((Part[][]) polledGridd)) {
				System.out.println("Solution to IDS");
				System.out.println("Cost: " + getPart(polledGridd).pathCost);
				System.out.println("Expanded Nodes: " + expandedNodes);
				// printGrid(polledGridd);
				printTrace(polledGridd);
				return false;
			} else {
				if (polledLevel <= depth) {
					pushLevel(grids, polledGridd, gridsDepth, polledLevel);
				} else {
					Stack<Object> testMoreLevel = new Stack();
					Stack<Object> testMoreDepth = new Stack();
					pushLevel(testMoreLevel, polledGridd, testMoreDepth,
							polledLevel);
					if (testMoreLevel.size() > 0) {
						moreLevel = true;
					}
				}
			}
		}
		if (!moreLevel) {
			System.out.println("No Solution to IDS");
		}
		return moreLevel;// if true then depth next
							// if false then no more levels
	}

	public void pushLevel(Stack<Object> grids, Part[][] polledGridd,
			Stack gridsDepth, int polledLevel) {
		Part[] gridParts = getParts((Part[][]) polledGridd);
		Stack<Part> parts = new Stack<Part>();
		for (int k = 0; k < gridParts.length; k++) {// true bec we
													// will expand
													// then search
			parts.push(gridParts[k]);
		}
		while (!parts.isEmpty()) {
			Part polledPartN = copyPart((Part) parts.peek());
			Part polledPartE = copyPart((Part) parts.peek());
			Part polledPartS = copyPart((Part) parts.peek());
			Part polledPartW = copyPart((Part) parts.pop());
			// west
			Part[][] westGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartW, "West");
			if (westGrid.length != 0) {
				grids.push(westGrid);
				gridsDepth.push(polledLevel + 1);
			}
			// south
			Part[][] southGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartS, "South");
			if (southGrid.length != 0) {
				grids.push(southGrid);
				gridsDepth.push(polledLevel + 1);
			}
			// east
			Part[][] eastGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartE, "East");
			if (eastGrid.length != 0) {
				grids.push(eastGrid);
				gridsDepth.push(polledLevel + 1);
			}
			// north
			Part[][] northGrid = doSearch((Part[][]) polledGridd,
					(Part) polledPartN, "North");
			if (northGrid.length != 0) {
				grids.push(northGrid);
				gridsDepth.push(polledLevel + 1);
			}
		}
	}

	/*
	 * BREADTH FIRST SEARCH
	 */
	public void bfs(Part[][] grid, int nparts) {
		this.numberOfParts = nparts;
		Queue<Object> grids = new LinkedList<>();
		grids.add(grid);
		Part[] setParent = getParts(grid);
		for (int t = 0; t < setParent.length; t++) {
			setParent[t].parent = null;
		}
		boolean flag = false;
		while (!flag) {
			if (!grids.isEmpty()) {
				Part[] gridParts = getParts((Part[][]) grids.peek());
				Queue<Object> parts = new LinkedList<>();
				for (int k = 0; k < gridParts.length; k++) {
					parts.add(gridParts[k]);
				}
				Part[][] polledGridd = (Part[][]) grids.poll();
				expandedNodes++;
				while (!parts.isEmpty()) {
					Part polledPartN = copyPart((Part) parts.peek());
					Part polledPartE = copyPart((Part) parts.peek());
					Part polledPartS = copyPart((Part) parts.peek());
					Part polledPartW = copyPart((Part) parts.poll());
					Part[][] northGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartN, "North");
					if (northGrid.length != 0) {
						boolean northSolution = isSolution(northGrid);
						if (!northSolution) {
							grids.add(northGrid);
						} else {
							System.out.println("North Solution");
							System.out.println("Cost: " + getPart(polledGridd).pathCost);
							System.out.println("Expanded Nodes: " + expandedNodes);
							printTrace(northGrid);
							flag = true;
							break;
						}
					}
					Part[][] eastGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartE, "East");
					if (eastGrid.length != 0) {
						boolean eastSolution = isSolution(eastGrid);
						if (!eastSolution) {
							grids.add(eastGrid);
						} else {
							System.out.println("East Solution");
							System.out.println("Cost: " + getPart(polledGridd).pathCost);
							System.out.println("Expanded Nodes: " + expandedNodes);
							printTrace(eastGrid);
							flag = true;
							break;
						}
					}
					Part[][] southGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartS, "South");
					if (southGrid.length != 0) {
						boolean southSolution = isSolution(southGrid);
						if (!southSolution) {
							grids.add(southGrid);
						} else {
							System.out.println("south Solution");
							System.out.println("Cost: " + getPart(polledGridd).pathCost);
							System.out.println("Expanded Nodes: " + expandedNodes);
							printTrace(southGrid);
							flag = true;
							break;
						}
					}
					Part[][] westGrid = doSearch((Part[][]) polledGridd,
							(Part) polledPartW, "West");
					if (westGrid.length != 0) {
						boolean westSolution = isSolution(westGrid);
						if (!westSolution) {
							grids.add(westGrid);
						} else {
							System.out.println("west Solution");
							System.out.println("Cost: " + getPart(polledGridd).pathCost);
							System.out.println("Expanded Nodes: " + expandedNodes);
							printTrace(westGrid);
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

							if (parts.contains(grid[i][j])) {

							} else {
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
		int countMoves = 1;
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
								if (sameGrid) {
									return new Part[0][0];
								} else {
									p.pathCost = p.pathCost
											+ (p.size * countMoves);
									p.parent = grid;// trying to trace;
									for (int ii = 0; ii < tempGrid.length; ii++) {
										for (int jj = 0; jj < tempGrid[ii].length; jj++) {
											if (tempGrid[ii][jj] == null) {

											} else {
												if (tempGrid[ii][jj].name == "*") {

												} else {
													Part f = copyPart(tempGrid[ii][jj]);
													tempGrid[ii][jj] = f;
												}
											}
										}
									}
									Part[] setParent = getParts(tempGrid);
									for (int t = 0; t < setParent.length; t++) {
										setParent[t].parent = grid;
									}
									
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
										int[] newPlaceX = new int[p.size
												+ tempGrid[x - 1][y].size];
										int[] newPlaceY = new int[p.size
												+ tempGrid[x - 1][y].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk = 0;
										for (int k = p.size; k < newPlaceX.length; k++) {
											newPlaceX[k] = tempGrid[x - 1][y].x[kk];
											newPlaceY[k] = tempGrid[x - 1][y].y[kk];
											kk++;
										}
										// newPlaceX[newPlaceX.length - 1] = x -
										// 1;
										// newPlaceY[newPlaceY.length - 1] = y;
										Part resultedPart = new Part(
												"part",
												p.size
														+ tempGrid[x - 1][y].size,
												newPlaceX, newPlaceY);

										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}
										resultedPart.pathCost = p.pathCost
												+ (p.size * countMoves);
										//resultedPart.heuristicCost = p.heuristicCost
											//	- tempGrid[x - 1][y].size;
										resultedPart.hitPartX = p.hitPartX;
										resultedPart.hitPartY = p.hitPartY;
										resultedPart.parent = p.parent;
										// tempGrid[x][y] = resultedPart;
										// tempGrid[x - 1][y] = resultedPart;
										// numberOfParts++;
										// printGrid(tempGrid);
										resultedPart.parent = grid;
										for (int ii = 0; ii < tempGrid.length; ii++) {
											for (int jj = 0; jj < tempGrid[ii].length; jj++) {
												if (tempGrid[ii][jj] == null) {

												} else {
													if (tempGrid[ii][jj].name == "*") {

													} else {
														Part f = copyPart(tempGrid[ii][jj]);
														tempGrid[ii][jj] = f;
													}
												}
											}
										}
										Part[] setParent = getParts(tempGrid);
										for (int t = 0; t < setParent.length; t++) {
											setParent[t].parent = grid;
										}
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
					tempGrid[x][y] = p;// tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.x[a]--;
				}
				countMoves++;
				sameGrid = false;
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
								if (sameGrid) {
									return new Part[0][0];
								} else {
									// printGrid(tempGrid);
									p.pathCost = p.pathCost
											+ (p.size * countMoves);
									p.parent = grid;
									for (int ii = 0; ii < tempGrid.length; ii++) {
										for (int jj = 0; jj < tempGrid[ii].length; jj++) {
											if (tempGrid[ii][jj] == null) {

											} else {
												if (tempGrid[ii][jj].name == "*") {

												} else {
													Part f = copyPart(tempGrid[ii][jj]);
													tempGrid[ii][jj] = f;
												}
											}
										}
									}
									Part[] setParent = getParts(tempGrid);
									for (int t = 0; t < setParent.length; t++) {
										setParent[t].parent = grid;
									}
									return tempGrid;// causes infinite loop
								}
								// return new Part[0][0];
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
										int[] newPlaceX = new int[p.size
												+ tempGrid[x][y + 1].size];
										int[] newPlaceY = new int[p.size
												+ tempGrid[x][y + 1].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk = 0;
										for (int k = p.size; k < newPlaceX.length; k++) {
											newPlaceX[k] = tempGrid[x][y + 1].x[kk];
											newPlaceY[k] = tempGrid[x][y + 1].y[kk];
											kk++;
										}
										// newPlaceX[newPlaceX.length - 1] = x;
										// newPlaceY[newPlaceY.length - 1] = y +
										// 1;
										Part resultedPart = new Part(
												"part",
												p.size
														+ tempGrid[x][y + 1].size,
												newPlaceX, newPlaceY);

										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}
										resultedPart.pathCost = p.pathCost
												+ p.size * countMoves;
										//resultedPart.heuristicCost = p.heuristicCost
											//	- tempGrid[x][y + 1].size;// tempGrid[x][y]
																			// =
																			// resultedPart;
										resultedPart.parent = p.parent;
										resultedPart.hitPartX = p.hitPartX;
										resultedPart.hitPartY = p.hitPartY;
										// tempGrid[x - 1][y] = resultedPart;
										// numberOfParts++;
										// printGrid(tempGrid);
										resultedPart.parent = grid;
										for (int ii = 0; ii < tempGrid.length; ii++) {
											for (int jj = 0; jj < tempGrid[ii].length; jj++) {
												if (tempGrid[ii][jj] == null) {

												} else {
													if (tempGrid[ii][jj].name == "*") {

													} else {
														Part f = copyPart(tempGrid[ii][jj]);
														tempGrid[ii][jj] = f;
													}
												}
											}
										}
										Part[] setParent = getParts(tempGrid);
										for (int t = 0; t < setParent.length; t++) {
											setParent[t].parent = grid;
										}
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
					tempGrid[x][y] = p;// tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.y[a]++;
				}
				countMoves++;
				sameGrid = false;
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
								if (sameGrid) {
									return new Part[0][0];
								} else {
									// printGrid(tempGrid);
									p.pathCost = p.pathCost
											+ (p.size * countMoves);
									p.parent = grid;
									for (int ii = 0; ii < tempGrid.length; ii++) {
										for (int jj = 0; jj < tempGrid[ii].length; jj++) {
											if (tempGrid[ii][jj] == null) {

											} else {
												if (tempGrid[ii][jj].name == "*") {

												} else {
													Part f = copyPart(tempGrid[ii][jj]);
													tempGrid[ii][jj] = f;
												}
											}
										}
									}
									Part[] setParent = getParts(tempGrid);
									for (int t = 0; t < setParent.length; t++) {
										setParent[t].parent = grid;
									}
									return tempGrid;// causes infinite loop
								}
								// return new Part[0][0];
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
										int[] newPlaceX = new int[p.size
												+ tempGrid[x + 1][y].size];
										int[] newPlaceY = new int[p.size
												+ tempGrid[x + 1][y].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk = 0;
										for (int k = p.size; k < newPlaceX.length; k++) {
											newPlaceX[k] = tempGrid[x + 1][y].x[kk];
											newPlaceY[k] = tempGrid[x + 1][y].y[kk];
											kk++;
										}
										// newPlaceX[newPlaceX.length - 1] = x +
										// 1;
										// newPlaceY[newPlaceY.length - 1] = y;
										Part resultedPart = new Part(
												"part",
												p.size
														+ tempGrid[x + 1][y].size,
												newPlaceX, newPlaceY);
										// tempGrid[x][y] = resultedPart;
										// tempGrid[x + 1][y] = resultedPart;
										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}
										resultedPart.pathCost = p.pathCost
												+ p.size * countMoves;
										//resultedPart.heuristicCost = p.heuristicCost
											//	- tempGrid[x + 1][y].size;
										resultedPart.parent = p.parent;
										resultedPart.hitPartX = p.hitPartX;
										resultedPart.hitPartY = p.hitPartY;
										// numberOfParts++;
										// printGrid(tempGrid);
										resultedPart.parent = grid;
										for (int ii = 0; ii < tempGrid.length; ii++) {
											for (int jj = 0; jj < tempGrid[ii].length; jj++) {
												if (tempGrid[ii][jj] == null) {

												} else {
													if (tempGrid[ii][jj].name == "*") {

													} else {
														Part f = copyPart(tempGrid[ii][jj]);
														tempGrid[ii][jj] = f;
													}
												}
											}
										}
										Part[] setParent = getParts(tempGrid);
										for (int t = 0; t < setParent.length; t++) {
											setParent[t].parent = grid;
										}
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
					tempGrid[x][y] = p;// tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.x[a]++;
				}
				countMoves++;
				sameGrid = false;
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
								if (sameGrid) {
									return new Part[0][0];
								} else {
									// printGrid(tempGrid);
									p.pathCost = p.pathCost
											+ (p.size * countMoves);
									p.parent = grid;
									for (int ii = 0; ii < tempGrid.length; ii++) {
										for (int jj = 0; jj < tempGrid[ii].length; jj++) {
											if (tempGrid[ii][jj] == null) {

											} else {
												if (tempGrid[ii][jj].name == "*") {

												} else {
													Part f = copyPart(tempGrid[ii][jj]);
													tempGrid[ii][jj] = f;
												}
											}
										}
									}
									Part[] setParent = getParts(tempGrid);
									for (int t = 0; t < setParent.length; t++) {
										setParent[t].parent = grid;
									}
									return tempGrid;// causes infinite loop
								}

								// return new Part[0][0];
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
										int[] newPlaceX = new int[p.size
												+ tempGrid[x][y - 1].size];
										int[] newPlaceY = new int[p.size
												+ tempGrid[x][y - 1].size];
										for (int k = 0; k < p.size; k++) {
											newPlaceX[k] = p.x[k];
											newPlaceY[k] = p.y[k];
										}
										int kk = 0;
										for (int k = p.size; k < newPlaceX.length; k++) {
											newPlaceX[k] = tempGrid[x][y - 1].x[kk];
											newPlaceY[k] = tempGrid[x][y - 1].y[kk];
											kk++;
										}
										// newPlaceX[newPlaceX.length - 1] = x;
										// newPlaceY[newPlaceY.length - 1] = y -
										// 1;
										Part resultedPart = new Part(
												"part",
												p.size
														+ tempGrid[x][y - 1].size,
												newPlaceX, newPlaceY);

										for (int l = 0; l < resultedPart.size; l++) {
											tempGrid[resultedPart.x[l]][resultedPart.y[l]] = resultedPart;
										}
										resultedPart.pathCost = p.pathCost
												+ p.size * countMoves;
										//resultedPart.heuristicCost = p.heuristicCost
											//	- tempGrid[x][y - 1].size;
										resultedPart.parent = p.parent;
										resultedPart.hitPartX = p.hitPartX;
										resultedPart.hitPartY = p.hitPartY;
										// tempGrid[x][y] = resultedPart;
										// tempGrid[x - 1][y] = resultedPart;
										// numberOfParts++;
										// printGrid(tempGrid);
										resultedPart.parent = grid;
										for (int ii = 0; ii < tempGrid.length; ii++) {
											for (int jj = 0; jj < tempGrid[ii].length; jj++) {
												if (tempGrid[ii][jj] == null) {

												} else {
													if (tempGrid[ii][jj].name == "*") {

													} else {
														Part f = copyPart(tempGrid[ii][jj]);
														tempGrid[ii][jj] = f;
													}
												}
											}
										}
										Part[] setParent = getParts(tempGrid);
										for (int t = 0; t < setParent.length; t++) {
											setParent[t].parent = grid;
										}
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
					tempGrid[x][y] = p;// tempGrid[p.x[a]][p.y[a]];
					tempGrid[p.x[a]][p.y[a]] = null;
				}
				for (int a = 0; a < p.size; a++) {
					p.y[a]--;
				}
				countMoves++;
				sameGrid = false;
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

	public boolean dfs(Part[][] grid) {
		boolean answer = false;
		int numberOfParts = 0;
		Stack<Part[][]> grids = new Stack<>();
		grids.add(grid);
		Part[][] northGrid = new Part[grid.length][grid[0].length];
		Part[][] eastGrid = new Part[grid.length][grid[0].length];
		Part[][] southGrid = new Part[grid.length][grid[0].length];
		Part[][] westGrid = new Part[grid.length][grid[0].length];
		Part[][] tempGrid = new Part[grid.length][grid[0].length];
		Part[][] workingGrid = new Part[grid.length][grid[0].length];
		Stack<Part> part = new Stack<>();
		Part workingPart = new Part();

		while (!grids.isEmpty()) {
			numberOfParts = 0;
			workingGrid = grids.pop();

			for (int i = 0; i < workingGrid.length; i++) {
				for (int j = 0; j < workingGrid[0].length; j++) {
					// To add a part in the stack, the part should be not null,
					// and name is "Part" and size == 1
					// Or not null, and name is part and size is more than one,
					// but not already in the Stack
					if (!((workingGrid[i][j].equals(null))
							&& workingGrid[i][j].name.equals("part") && workingGrid[i][j].size == 1)
							|| !(workingGrid[i][j].equals(null))
							&& workingGrid[i][j].name.equals("part")
							&& (workingGrid[i][j].size > 1)
							&& !(part.contains((workingGrid)[i][j]))) {
						part.push(workingGrid[i][j]);
						numberOfParts++;

					}
				}
			}
			if (numberOfParts == 1) {
				answer = true;
				break;
			}
			while (!part.isEmpty()) {
				workingPart = part.pop();

				westGrid = doSearch(workingGrid, workingPart, "West");
				if (westGrid.length > 0)
					grids.add(westGrid);
				southGrid = doSearch(workingGrid, workingPart, "South");
				if (southGrid.length > 0)
					grids.add(westGrid);
				eastGrid = doSearch(workingGrid, workingPart, "East");
				if (eastGrid.length > 0)
					grids.add(westGrid);
				northGrid = doSearch(workingGrid, workingPart, "North");
				if (northGrid.length > 0)
					grids.add(westGrid);

			}
		}
		return answer;
	}

	// default;

	public Part getPart(Part[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == null) {

				} else {
					if (grid[i][j].name == "*") {

					} else {
						return grid[i][j];
					}
				}
			}
		}
		return null;
	}

	public void printTrace(Part[][] grid) {
		Stack<Object[][]> s = new Stack<Object[][]>();
		s.push(grid);
		while (getPart((Part[][]) s.peek()).parent != null) {
			s.push(getPart((Part[][]) s.peek()).parent);
		}
		while (!s.isEmpty()) {
			printGrid((Part[][]) s.pop());
		}

	}

	
	/*
	 * Greedy Search
	 */

	public boolean greedyH1(Part[][] grid) {
		boolean result = false;
		boolean stoppingCondition = false;

		Part[][] workingGrid = new Part[grid.length][grid[0].length];
		Queue<Integer> gridCost = new LinkedList<Integer>();
		Queue<Part[][]> grids = new LinkedList<Part[][]>();
		Part copyNorth = new Part();
		Part copyEast = new Part();
		Part copySouth = new Part();
		Part copyWest = new Part();

		grids.add(grid);
		gridCost.add(0);

		while (!grids.isEmpty()) {

			workingGrid = grids.poll();
			gridCost.poll();
			Part[] parts = getParts(workingGrid);
			if (parts.length == 1) {
				result = true;
				break;
			}
			for (int i = 0; i < parts.length; i++) {
				copyNorth = copyPart(parts[i]);
				copyEast = copyPart(parts[i]);
				copyWest = copyPart(parts[i]);
				copySouth = copyPart(parts[i]);
				Part[][] northGrid = doSearch(workingGrid, copyNorth, "North");
				if (northGrid.length != 0) {
					grids.add(northGrid);
					gridCost.add(copyNorth.pathCost);
				}

				Part[][] eastGrid = doSearch(workingGrid, copyEast, "East");
				if (eastGrid.length != 0) {
					grids.add(eastGrid);
					gridCost.add(copyEast.pathCost);
				}

				Part[][] southGrid = doSearch(workingGrid, copySouth, "South");
				if (southGrid.length != 0) {
					grids.add(southGrid);
					gridCost.add(copySouth.pathCost);
				}

				Part[][] westGrid = doSearch(workingGrid, copyWest, "West");
				if (westGrid.length != 0) {
					grids.add(westGrid);
					gridCost.add(copyWest.pathCost);
				}

			}
			sortGrids(grids, gridCost);

		}
		if (result == true)
			return true;
		else
			return false;
	}
	/*
	 * Heurestic two, Manhaten distance
	 */
	public int getMax(int[] array) {
		int maximum = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] >= maximum) {
				maximum = array[i];
			}
		}
		return maximum;
	}

	public int getMin(int[] array) {
		int minimum = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] <= minimum) {
				minimum = array[i];
			}
		}
		return minimum;
	}

	public int getPosition(int[] array, int number) {
		int position = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == number) {
				position = i;
			}
		}
		return position;

	}

	public int manhatenCost(Part part, String direction) {
		int cost = 0;
		switch (direction) {
		case "North":
			cost = (Math.abs(getMax(part.x) - part.hitPartX) + (Math
					.abs(part.y[getPosition(part.x, getMax(part.x))]
							- part.hitPartY)));
			break;

		case "East":
			cost = (Math.abs(getMax(part.y) - part.hitPartY) + (Math
					.abs(part.x[getPosition(part.y, getMax(part.y))]
							- part.hitPartX)));
			break;
		case "South":
			cost = (Math.abs(getMin(part.y) - part.hitPartY) + (Math
					.abs(part.x[getPosition(part.y, getMin(part.y))]
							- part.hitPartX)));
			break;
		case "West":
			cost = (Math.abs(getMin(part.y) - part.hitPartY) + (Math
					.abs(part.x[getPosition(part.y, getMin(part.y))]
							- part.hitPartX)));
			break;

		}
		return cost;
	}

	public boolean greedyH2(Part[][] grid) {
		boolean result = false;

		Part[][] workingGrid = new Part[grid.length][grid[0].length];
		Queue<Integer> gridCost = new LinkedList<Integer>();
		Queue<Part[][]> grids = new LinkedList<Part[][]>();
		Part copyNorth = new Part();
		Part copyEast = new Part();
		Part copySouth = new Part();
		Part copyWest = new Part();

		grids.add(grid);
		gridCost.add(0);

		while (!grids.isEmpty()) {
			workingGrid = grids.poll();
			gridCost.poll();
			Part[] parts = getParts(workingGrid);
			if (parts.length == 1) {
				result = true;
				break;
			}
			for (int i = 0; i < parts.length; i++) {
				copyNorth = copyPart(parts[i]);
				copyEast = copyPart(parts[i]);
				copyWest = copyPart(parts[i]);
				copySouth = copyPart(parts[i]);
				Part[][] northGrid = doSearch(workingGrid, copyNorth, "North");
				if (northGrid.length != 0) {
					grids.add(northGrid);
					gridCost.add(manhatenCost(copyNorth, "North"));
				}

				Part[][] eastGrid = doSearch(workingGrid, copyEast, "East");
				if (eastGrid.length != 0) {
					grids.add(eastGrid);
					gridCost.add(manhatenCost(copyEast, "East"));
				}

				Part[][] southGrid = doSearch(workingGrid, copySouth, "South");
				if (southGrid.length != 0) {
					grids.add(southGrid);
					gridCost.add(manhatenCost(copySouth, "South"));
				}

				Part[][] westGrid = doSearch(workingGrid, copyWest, "West");
				if (westGrid.length != 0) {
					grids.add(westGrid);
					gridCost.add(manhatenCost(copyWest, "West"));
				}

			}
			sortGrids(grids, gridCost);

		}
		if (result == true)
			return true;
		else
			return false;
	}

	
	public static void main(String[] args) {
		
		Part[][] grid = new Part[4][4];
		  Part part11 = new Part("part", 1, new int[] { 0 }, new int[] { 0 });
		  Part part22 = new Part("part", 1, new int[] { 0 }, new int[] { 1 });
		  Part part33 = new Part("part", 1, new int[] { 2 }, new int[] { 1 });
		  Part part44 = new Part("part", 1, new int[] { 2 }, new int[] { 3 });
		  grid[0][0] = part11;
		  grid[0][1] = part22;
		  grid[2][1] = part33;
		  grid[2][3] = part44;
		  
		
		
		Part part1 = new Part("part", 1, new int[] { 0 }, new int[] { 0 });
		Part part2 = new Part("part", 1, new int[] { 0 }, new int[] { 2 });
		Part part3 = new Part("part", 1, new int[] { 2 }, new int[] { 0 });
		Part part4 = new Part("part", 1, new int[] { 2 }, new int[] { 2 });
		Part part5 = new Part("part", 1, new int[] { 1 }, new int[] { 2 });
		Part part6 = new Part("*", 0, new int[] { 1 }, new int[] { 1 });
		Part part7 = new Part("part", 1, new int[] { 1 }, new int[] { 3 });
		Part part8 = new Part("*", 0, new int[] { 3 }, new int[] { 3 });
		SearchTree k = new SearchTree();
		// Part returnedPart = k.raafatSearch(part1, grid, "East");
		// System.out.println(returnedPart.name);
		Part[][] testGrid = new Part[3][4];
		testGrid[0][0] = part1;
		testGrid[0][2] = part2;
		testGrid[2][0] = part3;
		testGrid[2][2] = part4;
		testGrid[1][2] = part5;
		// testGrid[1][1] = part6;
		testGrid[1][3] = part7;
		// testGrid[3][3] = part8;
		// k.printGrid(testGrid);
		 //k.bfs(testGrid, 6);
		 //k.ids(testGrid, 6);
		 //k.aStar1(testGrid, 6);
		 //k.aStar2(testGrid, 6);
		// k.printGrid(testGrid);
		 //k.bfs(grid, 4);
		 //k.ids(grid, 4);
		 //k.aStar1(grid, 4);
		 k.aStar2(grid, 4);
	}

}
