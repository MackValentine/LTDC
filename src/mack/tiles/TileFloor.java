package mack.tiles;

import java.io.IOException;
import mack.game.Game_Map;
import mack.main.Graphics_Loader;

public class TileFloor extends Tile {

	public TileFloor(int i, String s) throws IOException {
		super(i, "floor");
		 sprite = Graphics_Loader.load("tiles/" + Game_Map.etage_name + "/" + s + ".png");
	}
}
