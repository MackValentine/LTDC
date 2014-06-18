package mack.tiles;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import mack.game.Game_Character;
import mack.game.Game_Map;
import mack.game.Game_Player;
import mack.main.Graphics_Loader;
import mack.main.RPGPanel;
import mack.sprites.SpritesetMap;

public class Tile implements Cloneable {

	public int x = 0;
	public int y = 0;

	public String tile_name;
	public Image sprite;
	public int id;
	public boolean passable;
	public int direction;

	public Tile(int i, String string) throws IOException {
		passable = true;
		id = i;
		tile_name = string;
		if (string != "") {
			sprite = Graphics_Loader.load("tiles/"
					+ Game_Map.etage_name + "/" + tile_name + ".png");
		}
	}

	public int priority() {
		return 0;
	}

	public boolean visible() {
		return true;
	}

	public int tile_id() {
		return id;
	}

	public boolean collide(Game_Character event) {
		if (event.x == x && event.y == y) {
			return true;
		}
		return false;
	}

	public boolean contact_player() {
		Game_Player player = (Game_Player) RPGPanel.map.player();
		int i = player.direction == 4 ? player.x - 1
				: player.direction == 6 ? player.x + 1 : player.x;
		int j = player.direction == 8 ? player.y - 1
				: player.direction == 2 ? player.y + 1 : player.y;
		if (i == x && j == y) {
			return true;
		}
		return false;
	}

	public void render(int x1, int y1, int mx, int my, Graphics g) {
		if (this.visible() == true) {
			g.drawImage(sprite, x1 * 16 - mx, y1 * 16 - my, null);
		}
	}

	public boolean is_free(int i, int j) {
		boolean b = true;
		return b;
	}

	public int size() {
		return 1;
	}

	public boolean passable() {
		return passable;
	}

	public Tile set_passable(boolean b) {
		this.passable = b;
		return this;
	}

	public Tile clone() {
		Tile t = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la
			// méthode super.clone()
			t = (Tile) super.clone();
		} catch (CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}

		// On clone l'attribut de type Patronyme qui n'est pas immuable.

		// on renvoie le clone
		return t;
	}

	public boolean in_range(int s) {
		int a = 0;
		for (int i = 0; i > s; ++i)
			for (int j = 0; j > s; ++j)
				if (SpritesetMap.map.getCell(i, j) == this.id) {
					++a;
				}
		if (a > 2) {
			return false;
		}
		return true;
	}

	public Tile set_pos(int i, int j) {
		x = i;
		y = j;
		return this;
	}

	public static void update() throws IOException, FontFormatException {
	}

}
