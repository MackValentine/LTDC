package mack.items;

import java.io.IOException;

import mack.game.Game_Player;
import mack.scene.Scene_Map;

public class ItemFood extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5019184980936830404L;
	private int food_level;

	public ItemFood(int i, int l) {
		super(i);
		food_level = l;
	}

	public void use_item(Scene_Map m, Game_Player player, int id)
			throws IOException {
		if (food_level > 0) {
			Game_Player.actor.hour -= food_level;
			if (Game_Player.actor.hour < 0) {
				Game_Player.actor.hour = 0;
			}
			Game_Player.actor.remove_item(id);
		}
	}

	public void use_item(Scene_Map m, Game_Player player) throws IOException {
		if (food_level > 0) {
			Game_Player.actor.hour -= food_level;
			if (Game_Player.actor.hour < 0) {
				Game_Player.actor.hour = 0;
			}
			Game_Player.actor.remove_item(this, 1);
		}
	}

	public void create_descr(ItemFood i) {

		String s = "";
		if (i.food_level > 0) {
			s += "Jours-" + i.food_level;
		}
		i.description = s;
	}

}
