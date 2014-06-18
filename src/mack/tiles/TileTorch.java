package mack.tiles;

import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Dungeon;
import mack.game.Game_Map;
import mack.sprites.SpriteSheet;

public class TileTorch extends TileWall {

	private static Game_Dungeon map;
	private static int wait;
	private SpriteSheet tileset;
	private static int wait_a;

	public TileTorch(int i) throws IOException {
		super(i, "wall");
		tileset = new SpriteSheet("tiles/" + Game_Map.etage_name + "/torch.png", 16, 16);
		wait = 0;
		wait_a = 0;

	}

	public void set_direction(int d) {
		direction = d;
	}

	public static void update() {
		++wait;
		if (wait > 20) {
			wait = 0;
			++wait_a;
			if (wait_a > 1)
				wait_a = 0;
		}
	}

	public void render(int x1, int y1, int mx, int my, Graphics g) {
		super.render(x1, y1, mx, my, g);
		if (this.visible() == true) {
			for (int l = 0; l < 4; ++l) {
				int i = x1;
				int j = y1;
				if (l == 0)
					j += 1;
				else if (l == 1)
					i -= 1;
				else if (l == 2)
					i += 1;
				else
					j -= 1;
				
				if (exist(i, j)) {
					if (Tiles.is_floor(map.map[i][j])) {
						direction = l;
					}
				}
			}

			g.drawImage(tileset.getSprite(wait_a, direction), x1 * 16 - mx, y1
					* 16 - my, null);
		}
	}

	private boolean exist(int x, int y) {
		if (x > 0 && x < map.max_x - 1 && y > 0 && y < map.max_y - 1)
			return true;
		return false;
	}

	public static void set_map(Game_Dungeon m) {
		map = m;
	}
}
