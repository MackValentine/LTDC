package mack.pathfinding;

public class Node {

	
	public Node parent;
	public int G=0;
	public int H=0;
	public int F;

	public int x;
	public int y;
	

	public Node(int i, int j) {
		x = i;
		y = j;
	}
}
