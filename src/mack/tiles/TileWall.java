package mack.tiles;

import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Map;
import mack.sprites.SpriteSheet;
import mack.sprites.SpritesetMap;

public class TileWall extends Tile {
	public SpriteSheet tileset;
	public SpriteSheet tilesetangle;

	public TileWall(int i, String string) throws IOException {
		super(i, string);
		tileset = new SpriteSheet("tiles/" + Game_Map.etage_name + "/" + tile_name + ".png", 16, 16);
		tilesetangle = new SpriteSheet("tiles/" + Game_Map.etage_name + "/" + tile_name + ".png", 8, 8);
		set_passable(false);
	}

	public void render(int i, int j, int mx, int my, Graphics g) {
		int x = 1;
		int y = 1;

		g.drawImage(tileset.getSprite(1, 3), i * 16 - mx, j * 16 - my, null);
		if (SpritesetMap.exist(i + 1, j + 1)) {
			if (is_wall(i + 1, j + 1)) {
				g.drawImage(tilesetangle.getSprite(1, 7), i * 16 - mx + 8, j
						* 16 - my + 8, null);
				// OKAY
			}
		}
		if (SpritesetMap.exist(i - 1, j + 1)) {
			if (is_wall(i - 1, j + 1)) {
				// x=2;
				// y=3;
				g.drawImage(tilesetangle.getSprite(0, 7), i * 16 - mx, j * 16
						- my + 8, null);
				// OKAY
			}
		}
		if (SpritesetMap.exist(i - 1, j - 1)) {
			if (is_wall(i - 1, j - 1)) {
				// x=1;
				// y=3;
				g.drawImage(tilesetangle.getSprite(0, 6), i * 16 - mx, j * 16
						- my, null);
				// OKAY
			}
		}
		if (SpritesetMap.exist(i + 1, j - 1)) {
			if (is_wall(i + 1, j - 1)) {
				g.drawImage(tilesetangle.getSprite(1, 6), i * 16 - mx + 8, j
						* 16 - my, null);
				// OKAY
			}
		}

		if (SpritesetMap.exist(i - 1, j)) {

			if (is_wall(i - 1, j)) {
				x -= 1;
			}
		}
		if (SpritesetMap.exist(i + 1, j)) {

			if (is_wall(i + 1, j)) {
				x += 1;
			}
		}
		if (SpritesetMap.exist(i, j - 1)) {

			if (is_wall(i, j - 1)) {
				y -= 1;
			}
		}
		if (SpritesetMap.exist(i, j + 1)) {

			if (is_wall(i, j + 1)) {
				y += 1;
			}
		}

		if ((SpritesetMap.exist(i, j + 1)) && (SpritesetMap.exist(i, j - 1))) {

			if (is_wall(i, j + 1) && is_wall(i, j - 1)) {
				x = 4;
				y = 0;
			}
		}
		if ((SpritesetMap.exist(i + 1, j)) && (SpritesetMap.exist(i - 1, j))) {

			if (is_wall(i + 1, j) && is_wall(i - 1, j)) {
				x = 3;
				y = 0;
			}
		}

		if ((SpritesetMap.exist(i + 1, j)) && (SpritesetMap.exist(i - 1, j))
				&& (SpritesetMap.exist(i, j + 1))) {

			if (is_wall(i, j + 1) && is_wall(i + 1, j) && is_wall(i - 1, j)) {
				x = 3;
				y = 2;
			}
		}
		if ((SpritesetMap.exist(i + 1, j)) && (SpritesetMap.exist(i - 1, j))
				&& (SpritesetMap.exist(i, j - 1))) {

			if (is_wall(i, j - 1) && is_wall(i + 1, j) && is_wall(i - 1, j)) {
				x = 3;
				y = 1;
			}
		}

		if ((SpritesetMap.exist(i + 1, j)) && (SpritesetMap.exist(i, j - 1))
				&& (SpritesetMap.exist(i, j + 1))) {

			if (is_wall(i, j + 1) && is_wall(i + 1, j) && is_wall(i, j - 1)) {
				x = 4;
				y = 1;
			}
		}
		if ((SpritesetMap.exist(i - 1, j)) && (SpritesetMap.exist(i, j + 1))
				&& (SpritesetMap.exist(i, j - 1))) {

			if (is_wall(i, j - 1) && is_wall(i - 1, j) && is_wall(i, j + 1)) {
				x = 4;
				y = 2;
			}
		}
		if ((SpritesetMap.exist(i - 1, j))
				&& (SpritesetMap.exist(i, j + 1))
				&& (SpritesetMap.exist(i, j - 1) && (SpritesetMap.exist(i + 1,
						j)))) {

			if (is_wall(i + 1, j) && is_wall(i, j - 1) && is_wall(i - 1, j)
					&& is_wall(i, j + 1)) {
				x = 5;
				y = 0;
			}
		}

		sprite = tileset.getSprite(x, y);
		super.render(i, j, mx, my, g);

	}

	private boolean is_wall(int i, int j) {
		boolean b = true;
		if (SpritesetMap.tiles[i][j] == 2
				|| SpritesetMap.tiles[i][j] == 16
				|| (SpritesetMap.tiles[i][j] >= 22 && SpritesetMap.tiles[i][j] <= 25))
			b = false;
		return b;
	}
}
