package mack.window;

import java.awt.Color;
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

public class Window_Item extends Window {

	public int[] items_number;
	public Item[] items;
	public Item[] commands;

	public int index = 0;
	public int wait_move = 0;

	public int display_y = -1;

	public Image cursor;

	public Window_Item() throws IOException, FontFormatException {
		super(0, 32, 160, 112);
		items = Game_Player.actor.items;
		commands = new Item[items.length];

		cursor = Graphics_Loader.load("window/Cursor_Item.png");

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

		if (Option.repeatUp()) {
			if (index > 0) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index -= 9;
		} else if (Option.repeatDown()) {
			if (index < commands.length - 1) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index += 9;
		} else if (Option.repeatRight()) {
			if (index < commands.length - 1) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index += 1;
		} else if (Option.repeatLeft()) {
			if (index < commands.length - 1) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index -= 1;
		}

		if (index < 0) {
			index = 0;
		}
		if (index > 9 * 6 - 1) {
			index = 9 * 6 - 1;
		}
	}

	public void draw(Graphics g) {
		draw_back(g);

		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 6; ++j) {

				Color c = new Color(51, 51, 51);
				if ((i + j) % 2 == 0)
					c = new Color(115, 115, 115);

				g.setColor(c);

				g.fillRect(x + 8 + i * 16, y + 8 + j * 16, 16, 16);
			}
		}

		for (int i = 0; i < Game_Player.actor.items.length; ++i) {
			int ix = i % 9 * 16 + 8;
			int iy = 8 + i / 9 * 16;

			Item item = Game_Player.actor.items[i];

			draw_icon(x + ix, y + iy, item, g);
		}

		int i = index % 9 * 16 + 8 + x;
		int j = index / 9 * 16 + 8 + y;
		g.drawImage(cursor, i, j, null);

		draw_border(g);
	}

	public void reload_items() {
		items = Game_Player.actor.items;
		if (items.length == 0) {
			commands = new Item[1];
			commands[0] = null;
		} else {
			commands = new Item[items.length];
			for (int i = 0; i < items.length; ++i) {
				if (items[i] != null) {
					if (Game_Player.actor.item_number(items[i]) > 0) {
						commands[i] = items[i];
					}
				}
			}
		}
	}
}
