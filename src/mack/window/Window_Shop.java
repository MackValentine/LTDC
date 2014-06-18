package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.Sound;
import mack.rpg.RPGSystem;

public class Window_Shop extends Window {

	public int[] items_number;
	public Item[] items;
	public Item[] commands;

	public int index = 0;
	public int wait_move = 0;

	public int display_y = -1;

	public Image cursor;

	public boolean sell_window = false;

	public Window_Shop(boolean b) throws IOException, FontFormatException {
		super(0, 16, 160, 128);
		items = Game_Player.actor.items;
		commands = new Item[items.length];
		cursor = Graphics_Loader.load("window/Cursor.png");
		sell_window = b;
	}

	public Window_Shop(Item[] i) throws IOException, FontFormatException {
		super(0, 16, 160, 128);
		items = i;
		commands = new Item[items.length];
		cursor = Graphics_Loader.load("window/Cursor.png");

	}

	public void update() {
		if (index > display_y + 6) {
			if (index < items.length - 1) {
				display_y += 1;
			}
		}

		if (index < display_y + 2) {
			if (index > 0) {
				display_y -= 1;
			}
		}

		if (Option.pressUp()) {
			wait_move += 1;
			if (wait_move > 5) {
				if (index > 0)
					Sound.sound_play(RPGSystem.S_CursorMove);
				index -= 1;
				wait_move = 0;
			}
		} else if (Option.pressDown()) {
			wait_move += 1;
			if (wait_move > 5) {
				if (index < commands.length - 1)
					Sound.sound_play(RPGSystem.S_CursorMove);
				index += 1;
				wait_move = 0;
			}
		} else {
			wait_move = 5;
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
			draw_item(x + 16, y - 6 + (i) * 16 - (display_y * 16), commands[i],
					g);
		}

		g.drawImage(cursor, x + 8, y + index * 16 - (display_y * 16), null);
		draw_border(g);
	}

	public void draw_item(int i, int j, Item item, Graphics g) {
		if (item != null) {
			g.drawImage(
					iconset.getSprite(item.icon_index_x, item.icon_index_y), i,
					j, null);
			if (this.sell_window)
				draw_text(i + 16, j + 4, item.name + "  - " + item.price / 2
						+ "G", g);
			else
				draw_text(i + 16, j + 4, item.name + "  - " + item.price + "G",
						g);

		}
	}

	public void reload_items() {
		items = Game_Player.actor.items;
		commands = new Item[items.length];
		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null) {
				commands[i] = items[i];
			}
		}
	}

	public void reload_items(Item[] it) {
		items = it;
		commands = new Item[items.length];
		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null) {
				commands[i] = items[i];
			}
		}
	}
}
