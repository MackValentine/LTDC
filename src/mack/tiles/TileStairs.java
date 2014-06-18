package mack.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import mack.game.Game_Map;
import mack.game.Game_Player;
import mack.main.Graphics_Loader;

public class TileStairs extends Tile {

	public boolean stairs_up;
	private BufferedImage sprite_f;

	public TileStairs(int i, String string, boolean b) throws IOException {
		super(i, string);
		stairs_up = b;

		sprite_f =  (BufferedImage) Graphics_Loader.load(
				"tiles/" + Game_Map.etage_name + "/floor.png");
	}

	public void render(int x1, int y1, int mx, int my, Graphics g) {
		if (Game_Map.boss(Game_Player.floor) && Game_Map.boss_dead[0] == false) {
			if (this.visible() == true)
				g.drawImage(sprite_f, x1 * 16 - mx, y1 * 16 - my, null);
		} else
			super.render(x1, y1, mx, my, g);
	}
}
