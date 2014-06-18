package mack.items;

import java.io.IOException;

import mack.game.Game_Player;
import mack.scene.Scene_Map;

public class ItemCorde extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2863396020290938688L;

	public ItemCorde(int i) {
		super(i);
	}

	public int type() {
		return 1;
	}

	public void use_item(Scene_Map m, Game_Player player, int id)
			throws IOException {
		Game_Player.floor = 0;
		m.change_map(0);
		m.player().seton_stairs(1);
		Game_Player.actor.remove_item(id);
	}

	public void use_item(Scene_Map m, Game_Player player) throws IOException {
		Game_Player.floor = 0;
		m.change_map(0);
		m.player().seton_stairs(1);
		Game_Player.actor.remove_item(this, 1);
	}

}
