package mack.pathfinding;

import java.util.ArrayList;

import mack.game.Game_Character;
import mack.tiles.Tiles;

public class Path {

	public static final int NODE_VALUE = 10;
	private Node start;
	private Node end;
	private int[][] map;
	private ArrayList<Node> open;
	private ArrayList<Node> close;
	private ArrayList<Node> final_path;
	private boolean is_character;
	private Game_Character[] characters;
	private Game_Character character;

	public Path(Node s, Node e, int[][] m) {
		start = s;
		end = e;

		map = m;

		close = new ArrayList<Node>();
		open = new ArrayList<Node>();

		addToOpen(start);
	}

	public ArrayList<Node> search() {
		while (open.size() > 0) {
			Node current = getCurrent();
			if (is_goal(current)) {
				end = current;
				break;
			}

			addToClose(current);

			ArrayList<Node> neighbours = getNeighbours(current);

			for (int i = 0; i < neighbours.size(); ++i) {
				Node n = neighbours.get(i);
				if (isOnClose(n) || !passable(n.x, n.y)) {
					continue;
				}

				int newG = n.parent.G + NODE_VALUE;
				int newH = Math.abs(end.y - n.y) + Math.abs(end.x - n.x)
						* NODE_VALUE;
				int dir = 0;
				if (chang_dir(n, current)) {
					dir = NODE_VALUE;
				}
				int newF = newG + newH + dir;

				if (isOnOpen(n)) {
					if (newG < n.G) {
						n.parent = current;
						n.G = newG;
						n.F = newF;
						n.H = newH;
					}
				} else {
					n.parent = current;
					n.G = newG;
					n.F = newF;
					n.H = newH;
					addToOpen(n);
				}

			}

		}

		if (open.size() == 0)
			return final_path;

		return get_path();
	}

	private boolean chang_dir(Node n, Node current) {
		Node n3 = current.parent;
		if (n3 == null)
			n3 = n;

		if (Math.toDegrees(find_angle(n, current, n3)) == 0) {
			return false;
		}

		return true;
	}

	public double find_angle(Node p0, Node p1, Node c) {
		double p0c = Math.sqrt(Math.pow(c.x - p0.x, 2)
				+ Math.pow(c.y - p0.y, 2));

		double p1c = Math.sqrt(Math.pow(c.x - p1.x, 2)
				+ Math.pow(c.y - p1.y, 2));

		double p0p1 = Math.sqrt(Math.pow(p1.x - p0.x, 2)
				+ Math.pow(p1.y - p0.y, 2));
		return Math.acos((p1c * p1c + p0c * p0c - p0p1 * p0p1)
				/ (2 * p1c * p0c));
	}

	private boolean isOnOpen(Node n) {
		for (int i = 0; i < open.size(); ++i) {
			if (open.get(i).x == n.x && open.get(i).y == n.y)
				return true;
		}
		return false;
	}

	private boolean isOnClose(Node n) {
		for (int i = 0; i < close.size(); ++i) {
			if (close.get(i).x == n.x && close.get(i).y == n.y)
				return true;
		}
		return false;
	}

	private ArrayList<Node> getNeighbours(Node n) {
		ArrayList<Node> list = new ArrayList<Node>();
		for (int i = 0; i < 4; ++i) {
			int modX = 0;
			int modY = 0;
			if (i == 0)
				modX = -1;
			else if (i == 1)
				modX = 1;
			else if (i == 2)
				modY = -1;
			else if (i == 3)
				modY = 1;

			Node n2 = new Node(n.x + modX, n.y + modY);

			n2.parent = n;
			list.add(n2);
		}
		return list;
	}

	private void addToClose(Node current) {
		if (open.contains(current)) {
			open.remove(current);
		}
		close.add(current);
	}

	private void addToOpen(Node current) {
		if (close.contains(current)) {
			close.remove(current);
		}
		open.add(current);
	}

	private Node getCurrent() {
		int minF = 99999999;
		Node current = null;

		for (int i = 0; i < open.size(); ++i) {
			if (open.get(i).F < minF) {
				minF = open.get(i).F;
				current = open.get(i);
			}
		}

		return current;
	}

	public ArrayList<Node> get_path() {
		ArrayList<Node> list = new ArrayList<Node>();
		Node n = end;
		boolean b = true;
		while (b == true) {
			n = n.parent;
			list.add(n);
			if (n == start || n == null) {
				b = false;
			}
		}
		return list;
	}

	private boolean is_goal(Node current) {
		if (current.x == end.x && current.y == end.y) {
			return true;
		}
		return false;
	}

	// Permet de savoir si je peux créer un chemin.
	public boolean passable(int i, int j) {
		if (this.is_character == true) {
			boolean b = true;
			if (i >= 0 && j >= 0 && i < map.length && j < map[0].length) {
				if (Tiles.is_floor(map[i][j])) {
					for (int l = 0; l < characters.length; ++l) {
						if (characters[l] != null) {
							if ((characters[l].x == i && characters[l].y == j)) {
								b = false;
							}
							if ((!characters[l].exist() || characters[l]
									.trought())) {
								b = true;
							}
						}
					}
				} else {
					b = false;
				}
			} else
				b = false;

			if (i == start.x && j == start.y)
				b = true;
			if (i == end.x && j == end.y)
				b = true;
			return b;
		} else {
			// TODO BUG chemin passe dans les salles.
			if (i >= 0 && j >= 0 && i < map.length && j < map[0].length)
				if (map[i][j] == 1
						|| (map[i][j] >= 22 && map[i][j] <= 25))
					return false;
			return true;
		}
	}

	public boolean a(int i, int j) {
		for (int l = 0; l < characters.length; ++l) {
			if (characters[l] != null) {
				if (Tiles.is_floor(map[i][j]) && character != characters[l])
					if ((characters[l].x == i && characters[l].y == j)) {
						return false;
					}
			}
		}
		return true;
	}

	public static void show_build(int[][] room, int x, int y) {
		for (int i = 0; i < x; ++i) {
			System.out.println();
			for (int j = 0; j < y; ++j) {
				switch (room[i][j]) {
				case 0:
					System.out.print(".");
					break;
				case 1:
					System.out.print("S");
					break;
				case 3:
					System.out.print("E");
					break;
				case 2:
					System.out.print("#");
					break;
				case 5:
					System.out.print("H");
					break;
				case 15:
					System.out.print("J");
					break;
				}
			}
		}
	}

	public void set_character(boolean b, Game_Character[] c, Game_Character c2) {
		characters = c;
		is_character = b;
		character = c2;
	}

}
