public class Part {
	String name;
	int size;
	int[] x;
	int[] y;
	boolean alreadyConnected;

	public Part(String name, int size, int[] x, int[] y) {
		this.name = name;
		this.size = size;
		this.x = x;
		this.y = y;
		this.alreadyConnected = false;
	}

	public Part() {
		// TODO Auto-generated constructor stub
	}
}
