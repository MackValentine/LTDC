package mack.tiles;

import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Map;
import mack.sprites.SpriteSheet;

public class TileDoorExit extends Tile {

	private SpriteSheet tileset;

	public TileDoorExit(int i, String string) throws IOException {
		super(i, string);

		tileset = new SpriteSheet("tiles/" + Game_Map.etage_name + "/wall_exit.png", 16, 16);
	}

	public void render(int x1, int y1, int mx, int my, Graphics g) {
		g.drawImage(tileset.getSprite(1, 1), x1 * 16 - mx, y1 * 16 - my, null);
	}
}
