public class Node {

	// State ???
	private Node parentNode;
	private String operator;
	private int depth;
	private int pathCost;

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public Node(Node parentNode, String operator, int depth, int pathCost) {
		setParentNode(parentNode);
		setOperator(operator);
		setDepth(depth);
		setPathCost(pathCost);

	}
}