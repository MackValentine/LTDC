package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.Sound;
import mack.rpg.RPGSystem;

public class Window_EquipItem extends Window {

	public int[] items_number;
	public ArrayList<Item> items;

	public int index = 0;
	public int wait_move = 0;

	public int display_y = 0;

	public Window_Equip window_command;

	public Image cursor;

	public Window_EquipItem(Window_Equip window) throws IOException,
			FontFormatException {
		super(0, 72, 160, 72);
		// items = Game_Player.actor.items;
		// items.clear();
		items = new ArrayList<Item>();
		items.add(null);
		// commands=new Item[items.size()];
		cursor = Graphics_Loader.load("window/Cursor.png");

		window_command = window;
	}

	public void update() {

		if (index > display_y + 2) {
			if (index < items.size() - 1) {
				display_y += 1;
			}
		}

		if (index < display_y + 1) {
			if (index > 0) {
				display_y -= 1;
			}
		}

		if (Option.repeatUp()) {
			if (index > 0) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index -= 1;
		} else if (Option.repeatDown()) {
			if (index < items.size() - 1) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index += 1;
		}

		if (index < 0) {
			index = 0;
		}
		if (index > items.size() - 1) {
			index = items.size() - 1;
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		draw_back(g);

		for (int i = 0; i < items.size(); ++i)// commands.length;++i)
		{
			draw_item(x + 16, y + 4 + (i) * 16 - (display_y * 16),
					items.get(i), g);// commands[i]);
		}

		if (!window_command.active) {
			g.drawImage(cursor, x + 8, y + 9 + index * 16 - (display_y * 16),
					null);
		}

		draw_border(g);
	}

	public void reload_items() {
		// items = Game_Player.actor.items;
		index = 0;
		display_y = 0;
		items.clear();
		items.add(null);
		for (int i = 0; i < Game_Player.actor.items.length; ++i) {
			if (Game_Player.actor.items[i] != null) {
				if (Game_Player.actor.items[i].type() == window_command.index) {
					items.add(Game_Player.actor.items[i]);
				}
			}
		}
	}

	public void draw_item(int i, int j, Item item, Graphics g) {
		if (item != null) {
			g.drawImage(
					iconset.getSprite(item.icon_index_x, item.icon_index_y), i,
					j, null);
			draw_text(i + 16, j + 4, item.name, g);
		}
	}

}
