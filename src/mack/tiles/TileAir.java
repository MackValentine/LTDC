package mack.tiles;

import java.io.IOException;


public class TileAir extends Tile {

	public TileAir() throws IOException {
		super(0,"");
		
	}
	public boolean visible()
	{
		return false;
	}

}
