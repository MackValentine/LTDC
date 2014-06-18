package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import mack.game.Game_Player;
import mack.items.Item;
import mack.items.Items;
import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.Sound;
import mack.rpg.RPGSystem;

public class Window_Equip extends Window {

	public Item[] items;
	public Item[] commands;

	public int index = 0;
	public int wait_move = 0;

	public Image cursor;
	public Window_EquipItem background;

	public Window_Equip() throws IOException, FontFormatException {
		super(0, 16, 160, 56);

		items = Game_Player.actor.equipements;
		commands = new Item[items.length];
		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null)
				commands[i] = Items.items_list[items[i].id];// items.get(i);
		}
		cursor = Graphics_Loader.load("window/Cursor.png");
	}

	public void update() {
		if (Option.repeatUp()) {
			if (index > 0) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index -= 1;
		} else if (Option.repeatDown()) {
			if (index < commands.length - 1) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index += 1;
		}

		if (index < 0) {
			index = 0;
		}
		if (index > commands.length - 1) {
			index = commands.length - 1;
		}

	}

	public void draw(Graphics g) {
		super.draw(g);
		draw_back(g);

		for (int i = 0; i < commands.length; ++i) {
			draw_item(x + 16, y + 4 + (i) * 16, commands[i], g);
		}

		g.drawImage(cursor, x + 8, y + 9 + index * 16, null);

		draw_border(g);
	}

	public void reload_items() {
		items = Game_Player.actor.equipements;
		commands = new Item[items.length];
		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null)
				commands[i] = Items.items_list[items[i].id];// items.get(i);
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
