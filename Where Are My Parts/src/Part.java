public class Part {
	String name;
	int size;
	int[] x;
	int[] y;
	int pathCost;
	int heuristicCost;
	int hitPartX;
	int hitPartY;
	Object [][] parent;
	
	public Part(String name, int size, int[] x, int[] y) {
		this.name = name;
		this.size = size;
		this.x = x;
		this.y = y;
		this.pathCost = 1;
		this.heuristicCost = 0;
		this.parent = null;
		int hitPartX = 0;
		int hitPartY = 0;
	}

	public Part() {
		// TODO Auto-generated constructor stub
	}
}
