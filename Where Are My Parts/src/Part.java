public class Part {
	String name;
	int size;
	int[] x;
	int[] y;
	int xCoordinate;
	int yCoordinate;
	boolean alreadyConnected;
	int pathCost;
	int heuristicCost;
	
	
	public Part(String name, int size, int[] x, int[] y) {
		this.name = name;
		this.size = size;
		this.x = x;
		this.xCoordinate = x[0];
		this.yCoordinate = y[0];
		this.y = y;
		this.alreadyConnected = false;
		this.pathCost = 1;
		this.heuristicCost = 0;
	}

	public Part() {
		// TODO Auto-generated constructor stub
	}
}
