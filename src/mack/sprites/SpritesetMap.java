package mack.sprites;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Dungeon;
import mack.game.Game_Player;
import mack.main.RPGPanel;
import mack.tiles.Tiles;

public class SpritesetMap {
	public static int[][] tiles;
	public int display_x = 0;
	public int display_y = 0;
	public static Game_Dungeon map;

	public SpritesetMap() {
	}

	public void load_map() throws IOException {

		map = RPGPanel.game_map.dungeons[Game_Player.floor];

		tiles = new int[width()][height()];

		if (Tiles.tiles_list != null) {
			for (int i = 0; i < width(); ++i) {
				for (int j = 0; j < height(); ++j) {
					// System.out.println("AZE");
					tiles[i][j] = map.getCell(i, j);// Tiles.tiles_list[];//.clone().set_pos(i,
													// j).reload();
				}
			}
		}

	}

	public void update() throws IOException, FontFormatException {

	}

	public boolean player_in_range(int i, int j, int r) {
		Game_Player player = RPGPanel.map.player();
		if (Math.abs(player.x - i) < r) {
			if (Math.abs(player.y - j) < r) {
				return true;
			}
		}
		return false;
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 160, 160);

		for (int i = 0; i < width(); ++i) {
			for (int j = 0; j < height(); ++j) {
				if (tiles[i][j] != 0) {
					if (visible(i, j) && (player_in_range(i, j, 11))) {
						Tiles.tiles_list[tiles[i][j]].render(i, j, display_x,
								display_y, g);
					}
				}
			}
		}

		for (int i = 0; i < width(); ++i) {
			for (int j = 0; j < height(); ++j) {
				if (tiles[i][j] != 0) {
					if (visible(i, j)
							&& (player_in_range(i, j, 11) && Tiles.tiles_list[tiles[i][j]]
									.priority() > 0)) {
						// System.out.println(tiles[i][j]);
						// Tiles.tiles_list[tiles[i][j]].render(i,j,display_x,
						// display_y, g);
					}
				}
			}
		}
	}

	public boolean visible(int i, int j) {
		boolean a = false;
		if (RPGPanel.game_map.has_visit[Game_Player.floor][i][j] == true) {
			a = true;
		}
		return a;
	}

	public static boolean exist(int i, int j) {
		boolean b = false;
		if ((i >= 0 && i < width()) && (j >= 0 && j < height())) {
			b = true;
		}

		return b;
	}

	public static int width() {
		return map.max_y;
	}

	public static int height() {
		return map.max_x;
	}

	public static boolean passable(int i, int j) {
		if (exist(i, j))
			if (Tiles.tiles_list[tiles[i][j]].passable) {
				return true;
			}
		return false;
	}
}
