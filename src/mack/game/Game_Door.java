package mack.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import mack.main.Graphics_Loader;
import mack.main.RPGPanel;
import mack.scene.Scene_Map;

public class Game_Door extends Game_Character {

	private BufferedImage sprite;
	public boolean open;

	public Game_Door(Scene_Map m, String s, int i, int j)
			throws CloneNotSupportedException, IOException {
		super(m, "", i, j);

		sprite = (BufferedImage) Graphics_Loader.load("tiles/" + Game_Map.etage_name
				+ "/" + s + ".png");
	}

	public void update() throws IOException, FontFormatException {
		super.update();

	}

	public void render(Graphics g) throws IOException {
		if (RPGPanel.game_map.has_visit[Game_Player.floor][x][y])
			g.drawImage(sprite, (screen_x) - map.spriteset.display_x,
					(screen_y) - map.spriteset.display_y, null);
	}

	public boolean trought() {
		return false;
	}
}
