package mack.game;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import mack.items.Item;
import mack.main.Option;
import mack.main.RPGPanel;
import mack.scene.Scene_Loot;
import mack.scene.Scene_Map;

public class Game_Drop extends Game_Character {

	public boolean open;
	public ArrayList<Item> items;

	public Game_Drop(Scene_Map m, ArrayList<Item> drops, int i, int j)
			throws IOException {
		super(m, "Chest", i, j);
		open = false;

		items = drops;
	}

	public boolean actionnable() {
		if (open) {
			return false;
		} else {
			return true;
		}
	}

	public void update() throws IOException, FontFormatException {
		if (open == false) {
			if (contact_player() && Option.triggerA()) {
				open = true;
				map.frame.call_scene(new Scene_Loot(RPGPanel.map.frame, items));
				set_dead();
			}
		}
	}

}
