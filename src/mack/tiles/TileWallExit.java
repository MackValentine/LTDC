package mack.tiles;

import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Map;
import mack.sprites.SpriteSheet;

public class TileWallExit extends TileWall {

	private SpriteSheet tileset2;
	int xid;
	int yid;

	public TileWallExit(int i, String string, int j, int l) throws IOException {
		super(i, string);

		tileset2 = new SpriteSheet("tiles/" + Game_Map.etage_name + "/wall_exit.png", 16, 16);

		xid = j;
		yid = l;
	}

	public void render(int x1, int y1, int mx, int my, Graphics g) {
		super.render(x1, y1, mx, my, g);

		g.drawImage(tileset2.getSprite(xid, yid), x1 * 16 - mx, y1 * 16 - my,
				null);
	}

}
