package mack.tiles;

import java.io.IOException;

public class TilePillar extends Tile{

	public TilePillar(int i) throws IOException {
		super(i, "pillar");
		this.set_passable(false);
	}

}
