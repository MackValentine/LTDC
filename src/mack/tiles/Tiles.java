package mack.tiles;

import java.io.IOException;

public class Tiles {

	public static Tile[] tiles_list;

	public Tiles() throws IOException {
		tiles_list = new Tile[100];
		add_tile(new TileAir());
		add_tile(new TileWall(2, "wall"));
		add_tile(new Tile(4, "wall_turn"));
		add_tile(new TileFloor(6, "floor"));
		add_tile(new TileFloor(5, "floor_2"));
		add_tile(new TileFloor(15, "floor_3"));
		// add_tile(new TileDoor(7));
		add_tile(new TileStairs(8, "stairsup", true));
		add_tile(new TileStairs(9, "stairsdown", false));

		add_tile(new TileDoorExit(20, "floor"));
		add_tile(new TileFloor(21, "floor_exit"));
		add_tile(new TileWallExit(22, "wall", 0, 0));
		add_tile(new TileWallExit(23, "wall", 0, 1));
		add_tile(new TileWallExit(24, "wall", 2, 0));
		add_tile(new TileWallExit(25, "wall", 2, 1));

		add_tile(new TilePillar(26));

		add_tile(new Tile(27, "floor"));
		add_tile(new Tile(28, "floor"));

		add_tile(new TileTorch(16));
	}

	public void add_tile(Tile t) {
		int id = t.id;
		if (tiles_list[id] == null) {
			tiles_list[id] = t;
		}
	}

	public static boolean is_floor(int i) {

		boolean b = false;
		if (i == 5 || i == 6 || i == 15 || i == 8 || i == 9 || i == 0
				|| i == 20 || i == 21 || i == 27 || i == 28)
			b = true;
		return b;
	}
}
