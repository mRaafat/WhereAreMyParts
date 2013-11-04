public class Part {
	String name;
	int size;
	int[] x;
	int[] y;
	int xCoordinate;
	int yCoordinate;
	boolean alreadyConnected;

	public Part(String name, int size, int[] x, int[] y) {
		this.name = name;
		this.size = size;
		this.x = x;
		this.xCoordinate = x[0];
		this.yCoordinate = y[0];
		this.y = y;
		this.alreadyConnected = false;
	}

	public Part() {
		// TODO Auto-generated constructor stub
	}
}
