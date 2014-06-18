package mack.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import mack.pathfinding.Node;
import mack.pathfinding.Path;
import mack.tiles.Tiles;

public class CopyOfGame_Dungeon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8685704828414762034L;

	private Random rand;

	// Id des différentes tiles.
	final private int tileDirtWall2 = 1;
	final private int tileDirtWall = 2;
	final private int tileDirtFloor = 6;
	final private int tileDirtFloor2 = 5;
	final private int tileDoor = 7;
	final private int tileUpStairs = 8;
	final private int tileDownStairs = 9;
	final private int tileDirtFloor3 = 15;
	final private int tileTorch = 16;

	// Longueur et Largeur exist du donjon
	public int max_y;
	public int max_x;

	// Etage. Influe sur la création du donjon.
	private int floor;

	// Nombre de salles Max, et actuelle
	public int rooms_max;
	private int nbr_room;

	// Tableau permettant de savoir à quelle salle appartient chaque cases.
	int[][] room_id;
	// Tableau contenant l'id de chaque Tile pour chaque cases.
	public int[][] map;

	private int[] link_per_rooms;

	private int[][][] rooms;

	private ArrayList<int[]>[] walls_room;

	public int[] stairs_up;

	public ArrayList<ArrayList<Integer>> room_linked;

	@SuppressWarnings("unchecked")
	public CopyOfGame_Dungeon(int i, int j, int k, int l) {
		max_x = i;
		max_y = j;

		rooms_max = k;

		floor = l;

		rand = new Random();

		map = new int[max_x][max_y];

		rooms = new int[max_x][max_y][rooms_max];

		walls_room = new ArrayList[rooms_max + 1];

		room_linked = new ArrayList<ArrayList<Integer>>();

		nbr_room = 1;

		room_id = new int[max_x][max_y];

		// Je remplis mes tableau, celui des tiles avec des murs, celui des id
		// des salles avec un 0 ( Pas de salles donc )
		for (int q = 0; q < max_x; ++q) {
			for (int s = 0; s < max_y; ++s) {
				map[q][s] = tileDirtWall;
				room_id[q][s] = 0;
			}
		}

		int x = 0;
		int y = 0;

		if (floor == 4) {
			int h = new Random().nextInt(2) * 2 + 4;
			int w = 3;

			System.out.println(w);

			x = max_x / 2 - 1;
			y = max_y / 2 + h;

			force_make_room(w, h, x, y);

			force_make_room(7, 7, x, y - h - 3);

			showDungeon();

		} else {

			// Si c'est le premier étage, je créer la première salle au milieu
			// du
			// donjon.
			if (floor == 0) {
				x = max_x / 2 - 1;
				y = max_y - 6;

				make_room(5, 5, x, y);

				map[x - 1][y + 3] = 6;
				map[x][y + 3] = 6;
				map[x + 1][y + 3] = 6;

				map[x][y + 4] = 21;
				map[x - 1][y + 4] = 22;
				map[x + 1][y + 4] = 24;
				map[x - 1][y + 5] = 23;
				map[x + 1][y + 5] = 25;
				map[x][y + 5] = 20;
			}

			boolean b = false;
			while (b == false) {
				// Je créer un certain nombre de salle, avec un taille et un
				// emplacement aléatoire.
				make_room(rand.nextInt(2) + 3, rand.nextInt(2) + 3,
						rand.nextInt(max_x - 2) + 1,
						rand.nextInt(max_y - 2) + 1);

				// Quand il y a assez de salles, je sors de la boucle.
				if (nbr_room > this.rooms_max)
					b = true;
			}
		}
		link_per_rooms = new int[rooms_max];
		for (int r = 0; r < rooms_max; ++r) {
			link_per_rooms[r] = 0;
			room_linked.add(new ArrayList<Integer>());
		}

		for (int r = 0; r < rooms_max; ++r) {
			link_room(r);
		}

		for (int q = 0; q < max_x; ++q) {
			for (int s = 0; s < max_y; ++s) {

				if (map[q][s] == tileDirtWall2)
					map[q][s] = tileDirtWall;

				if (map[q][s] == tileDirtWall) {
					int r = rand.nextInt(3);
					if (!tiles_in_range(tileTorch, 3, q, s) && r == 0
							&& can_make_torch(q, s)) {
						map[q][s] = tileTorch;
					}

				}
				if (map[q][s] == tileDirtFloor) {
					int r = rand.nextInt(3);
					if (!tiles_in_range(tileDirtFloor2, 3, q, s) && r == 0) {
						map[q][s] = tileDirtFloor2;
					}

					r = rand.nextInt(6);
					if (!tiles_in_range(tileDirtFloor3, 9, q, s) && r == 0) {
						map[q][s] = tileDirtFloor3;
					}
				}
			}
		}

		showDungeon();
		System.out.println("--");

	}

	private void force_make_room(int sx, int sy, int x, int y) {
		// J'augmente la taille de la salle de 2 pour y placer mes murs.
		sx += 2;
		sy += 2;
		int[][] room = new int[sx][sy];

		for (int r = 0; r < sx; ++r) {
			for (int t = 0; t < sy; ++t) {
				room[r][t] = tileDirtFloor;
				if (nbr_room == 1) {
					if (r == sx / 2 && t == sy - 2) {
						room[r][t] = tileDownStairs;
					} else if (t % 2 == 1 && (r == sx - 1 || r == 0)) {
						System.out.println("AZE");
						room[r][t] = 26;
					}
				}
			}
		}

		for (int i = 0; i < sx; ++i) {
			for (int j = 0; j < sy; ++j) {

				int i2 = x + i - sx / 2;
				int j2 = y + j - sy / 2;
				if (i2 > 0 && i2 < max_x - 1 && j2 > 0 && j2 < max_y - 1) {
					map[i2][j2] = room[i][j];
					room_id[i2][j2] = nbr_room;
					if (map[i2][j2] == tileUpStairs) {
						stairs_up = new int[] { i2, j2 };
					}
				}

			}

			rooms[nbr_room] = room;

			++nbr_room;
		}
	}

	private boolean can_make_torch(int q, int s) {
		boolean b = false;
		for (int l = 0; l < 4; ++l) {
			int i = q;
			int j = s;
			if (l == 0)
				j += 1;
			else if (l == 1)
				i -= 1;
			else if (l == 2)
				i += 1;
			else
				j -= 1;
			if (exist(i, j)) {
				if (Tiles.is_floor(map[i][j])) {
					b = true;
				}
			}
		}

		return b;
	}

	private boolean tiles_in_range(int id, int l, int x, int y) {
		boolean b = false;
		for (int i = -l; i < l; ++i) {
			for (int j = -l; j < l; ++j) {
				if (exist(x + i, y + j)) {
					if (map[x + i][y + j] == id) {
						b = true;
					}
				}
			}
		}
		return b;
	}

	private void link_room(int rl) {

		ArrayList<int[]> wall = walls_room[rl];
		int[] d1 = null;
		int[] d2 = null;

		if (wall != null) {
			boolean skip = false;

			while (skip == false) {
				if (wall.size() < 1) {
					skip = true;
					break;
				}
				int r = rand.nextInt(100);
				int r2 = r * wall.size() / 100;
				int i = wall.get(r2)[0];
				int j = wall.get(r2)[1];

				if (can_make_door(i, j)) {
					this.map[i][j] = tileDirtFloor;
					d1 = new int[] { i, j };

					boolean brk = false;
					int rayon = 1;
					while (brk == false) {
						int i2 = 0;
						int j2 = 0;
						for (int k = 0; k < rayon * 2; ++k) {
							for (int l = 0; l < rayon * 2; ++l) {
								i2 = i + k - (rayon / 2);
								j2 = j + l - (rayon / 2);
								if (Math.pow(i - i2, 2) + Math.pow(j - j2, 2) <= Math
										.pow(rayon, 2)) {
									if (i2 > 0 && i2 < max_x && j2 > 0
											&& j2 < max_y) {
										if (this.room_id[i2][j2] != rl
												&& getCell(i2, j2) == tileDirtWall2
												&& can_make_door(i2, j2)) {
											if ((room_linked.get(
													room_id[i2][j2] - 1).size() < 1)) {
												this.map[i2][j2] = tileDirtFloor2;
												d2 = new int[] { i2, j2 };
												brk = true;
												skip = true;
												k = rayon * 2 + 1;
												l = rayon * 2 + 1;

												room_linked.get(rl - 1).add(
														room_id[i2][j2]);
											}
										}
									}
								}
							}

						}

						++rayon;
						if (rayon > this.max_x)
							skip = true;
					}
				}
				wall.remove(r2);
			}

			if (d1 != null && d2 != null) {

				Node s = new Node(d1[0], d1[1]);
				s.x = d1[0];
				s.y = d1[1];
				Node e = new Node(d2[0], d2[1]);
				e.x = d2[0];
				e.y = d2[1];
				Path p = new Path(s, e, map);

				ArrayList<Node> list = p.search();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); ++i) {
						if (list.get(i) != null) {
							int x = list.get(i).x;
							int y = list.get(i).y;
							if (exist(x, y)) {
								map[x][y] = tileDirtFloor;
							}
						}
					}
					map[e.x][e.y] = 27;
					map[s.x][s.y] = 27;

				}
			}

		}
	}

	private void make_room(int sx, int sy, int x, int y) {
		// J'augmente la taille de la salle de 2 pour y placer mes murs.
		sx += 2;
		sy += 2;
		int[][] room = new int[sx][sy];

		ArrayList<int[]> wall = new ArrayList<int[]>();

		for (int r = 0; r < sx; ++r) {
			for (int t = 0; t < sy; ++t) {
				if (r != 0 && r != sx - 1 && t != 0 && t != sy - 1) {
					// Je place le sol si la case n'est pas un bord.
					room[r][t] = tileDirtFloor;
					// Si c'est la première ou la dernière salle, et que c'est
					// son centre, je place un escalier.
					if (floor != 0) {
						if (nbr_room == 1) {
							if (r == sx / 2 && t == sy / 2) {
								room[r][t] = tileDownStairs;
							}
						}
					}
					if (nbr_room == this.rooms_max) {
						if (r == sx / 2 && t == sy / 2) {
							room[r][t] = tileUpStairs;
						}
					}

					if (sx >= 3 + 2) {
						if (t % 2 == 1 && (r == 1 || r == sx - 2)) {
							if (can_make_pillar(room, r, t))
								room[r][t] = 26;
						}
					}
				} else {
					// Sinon je place mes murs autours du sol.
					if (floor != 0) {
						if (r + x > 0 && r + x <= max_x && t + y > 0
								&& t + y <= max_y) {
							room[r][t] = tileDirtWall2;
							wall.add(new int[] { x + r - sx / 2,
									y + t - sy / 2, r, t });
						}
					} else if (nbr_room > 1) {
						if (r + x > 0 && r + x <= max_x && t + y > 0
								&& t + y <= max_y) {
							room[r][t] = tileDirtWall2;
							wall.add(new int[] { x + r - sx / 2,
									y + t - sy / 2, r, t });
						}
					} else if (t != sy - 1) {
						if (r + x > 0 && r + x <= max_x && t + y > 0
								&& t + y <= max_y) {
							room[r][t] = tileDirtWall2;
							wall.add(new int[] { x + r - sx / 2,
									y + t - sy / 2, r, t });
						}
					} else {
						if (r == 0 || r == sx - 1)
							room[r][t] = tileDirtWall;
						else
							room[r][t] = tileDirtFloor;
					}

				}
			}
		}

		// Si je peux placer ma salle à ces coordonnées, je remplis mon tableau
		// map avec les coordonnées
		// et les id de ma salle, et je remplis mon tableau des id de la même
		// façon.
		if (can_make_room(x, y, sx, sy)) {
			for (int i = 0; i < sx; ++i) {
				for (int j = 0; j < sy; ++j) {

					int i2 = x + i - sx / 2;
					int j2 = y + j - sy / 2;
					if (i2 > 0 && i2 < max_x - 1 && j2 > 0 && j2 < max_y - 1) {
						map[i2][j2] = room[i][j];
						room_id[i2][j2] = nbr_room;
						if (map[i2][j2] == tileUpStairs) {
							stairs_up = new int[] { i2, j2 };
						}
					}
				}
			}

			rooms[nbr_room] = room;
			walls_room[nbr_room] = wall;

			++nbr_room;
		}

		// show_build(room, sx, sy);
	}

	private boolean can_make_pillar(int[][] room, int r, int t) {
		boolean b = true;

		for (int i = 0; i < 5; ++i) {
			ArrayList<Integer[]> f = new ArrayList<Integer[]>();
			for (int j = 0; j < 3; ++j) {
				int dx = 0;
				int dy = 0;
				if (i == 0) {
					if (j == 0) {
						dx = r;
						dy = t - 1;
					} else if (j == 1) {
						dx = r + 1;
						dy = t - 1;
					} else if (j == 2) {
						dx = r + 1;
						dy = t;
					} else if (j == 3) {
						dx = r + 1;
						dy = t + 1;
					} else if (j == 4) {
						dx = r;
						dy = t + 1;
					}

				} else if (i == 1) {
					if (j == 0) {
						dx = r + 1;
						dy = t;
					} else if (j == 1) {
						dx = r + 1;
						dy = t - 1;
					} else if (j == 2) {
						dx = r;
						dy = t - 1;
					} else if (j == 3) {
						dx = r - 1;
						dy = t - 1;
					} else if (j == 4) {
						dx = r - 1;
						dy = t;
					}

				} else if (i == 2) {
					if (j == 0) {
						dx = r;
						dy = t + 1;
					} else if (j == 1) {
						dx = r - 1;
						dy = t + 1;
					} else if (j == 2) {
						dx = r - 1;
						dy = t;
					} else if (j == 3) {
						dx = r - 1;
						dy = t - 1;
					} else if (j == 4) {
						dx = r;
						dy = t - 1;
					}
				} else if (i == 3) {
					if (j == 0) {
						dx = r - 1;
						dy = t;
					} else if (j == 1) {
						dx = r - 1;
						dy = t + 1;
					} else if (j == 2) {
						dx = r;
						dy = t + 1;
					} else if (j == 3) {
						dx = r + 1;
						dy = t + 1;
					} else if (j == 4) {
						dx = r + 1;
						dy = t;
					}
				}

				// room[dx][dy] = -1;

				if (Tiles.is_floor(room[dx][dy])) {
					f.add(new Integer[] { dx, dy });
				}

			}
			if (f.size() >= 3)
				b = false;

		}
		return b;
	}

	public boolean can_make_room(int x, int y, int sx, int sy) {
		// Vérifie si la salle peut être créer :
		// Si elle n'empiète pas sur une autre salle
		// Ou qu'elle sorte du donjon
		// Ou qu'elle soit trop près du bord.
		for (int i = 0; i < sx + 2; ++i) {
			for (int j = 0; j < sy + 2; ++j) {
				int x2 = x + i - sx / 2 - 1;
				int y2 = y + j - sy / 2 - 1;
				if (x2 > 0 && y2 > 0 && x2 < max_x - 1 && y2 < max_y - 1) {
					if (map[x2][y2] != tileDirtWall) {
						return false;
					}
				} else
					return false;
			}
		}
		return true;
	}

	private boolean can_make_door(int x, int y) {
		if (exist(x + 1) && exist(y + 1)) {
			if (getCell(x + 1, y) == tileDirtWall2
					&& getCell(x, y + 1) == tileDirtWall2) {
				return false;
			}
		}
		if (exist(x - 1) && exist(y + 1)) {
			if (getCell(x - 1, y) == tileDirtWall2
					&& getCell(x, y + 1) == tileDirtWall2) {
				return false;
			}
		}
		if (exist(x + 1) && exist(y - 1)) {
			if (getCell(x + 1, y) == tileDirtWall2
					&& getCell(x, y - 1) == tileDirtWall2) {
				return false;
			}
		}
		if (exist(x - 1) && exist(y - 1)) {
			if (getCell(x - 1, y) == tileDirtWall2
					&& getCell(x, y - 1) == tileDirtWall2) {
				return false;
			}
		}

		return true;
	}

	private boolean exist(int x) {
		if (x >= 0 && x < max_x)
			return true;
		return false;
	}

	private boolean exist(int x, int y) {
		if (x >= 0 && x < max_x && y >= 0 && y < max_y)
			return true;
		return false;
	}

	public boolean can_make_door(int x, int y, int size_x, int size_y) {
		boolean b = true;
		if ((x == 0 && y == 0) || (x == size_x - 1 && y == size_y - 1)
				|| (x == 0 && y == size_y - 1) || (y == 0 && x == size_x - 1))
			b = false;

		return b;
	}

	public int getCell(int x, int y) {
		return map[x][y];
	}

	// Permet d'afficher une salle
	public void show_build(int[][] room, int x, int y) {
		for (int i = 0; i < x; ++i) {
			System.out.println();
			for (int j = 0; j < y; ++j) {
				switch (room[i][j]) {
				case tileDirtWall:
					System.out.print("#");
					break;
				case tileDirtFloor:
					System.out.print(".");
					break;
				case tileDirtFloor2:
					System.out.print("o");
					break;
				case tileDoor:
					System.out.print("D");
					break;
				case tileUpStairs:
					System.out.print("<");
					break;
				case tileDownStairs:
					System.out.print(">");
					break;
				case 20:
					System.out.print("v");
					break;
				}
			}
		}
	}

	// Affiche le donjon
	public void showDungeon() {
		for (int y = 0; y < max_x; y++) {
			for (int x = 0; x < max_y; x++) {
				// System.out.print(getCell(x, y));
				switch (getCell(x, y)) {
				case tileDirtFloor3:
					System.out.print("X");
					break;
				case tileDirtWall2:
					System.out.print("-");
					break;
				case tileDirtWall:
					System.out.print("#");
					break;
				case tileDirtFloor:
					System.out.print(".");
					break;
				case tileDirtFloor2:
					System.out.print("o");
					break;
				case tileDoor:
					System.out.print("D");
					break;
				case tileUpStairs:
					System.out.print("<");
					break;
				case tileDownStairs:
					System.out.print(">");
					break;
				case tileTorch:
					System.out.print("*");
					break;
				case 20:
					System.out.print("v");
					break;
				case 21:
					System.out.print(",");
					break;
				case 22:
					System.out.print(",");
					break;
				case 23:
					System.out.print(",");
					break;
				case 24:
					System.out.print(",");
					break;
				case 25:
					System.out.print(",");
					break;
				case 26:
					System.out.print("P");
					break;
				case -1:
					System.out.print("H");
					break;
				case 27:
					System.out.print("D");
					break;
				case -3:
					System.out.print("U");
					break;
				}
				;
			}
			System.out.println();
		}
	}

	// Affiche la carte des id des salles
	public void showRoomId() {
		for (int y = 0; y < max_x; y++) {
			for (int x = 0; x < max_y; x++) {
				int id = room_id[x][y];
				if (id > 9)
					System.out.print(id);
				else
					System.out.print(" " + id);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		System.out.println("START");
		new CopyOfGame_Dungeon(40, 40, 15, 0);
		// dungeon.showDungeon();
		// dungeon.showRoomId();
	}

}
