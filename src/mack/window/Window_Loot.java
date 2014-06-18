package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import mack.items.Item;
import mack.items.Items;
import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.Sound;
import mack.rpg.RPGSystem;

public class Window_Loot extends Window {

	public int[] items_number;
	public ArrayList<Item> items;
	public Item[] commands;

	public int index = 0;
	public int wait_move = 0;

	public int display_y = -1;

	public Image cursor;

	public Window_Loot() throws IOException, FontFormatException {
		super(0, 16, 160, 128);

		cursor = Graphics_Loader.load("window/Cursor.png");
	}

	public void update() {
		if (index > display_y + 6) {
			if (index < items.size() - 1) {
				display_y += 1;
			}
		}

		if (index < display_y + 2) {
			if (index > 0) {
				display_y -= 1;
			}
		}

		if (Option.repeatUp()){
			if (index > 0) {
				Sound.sound_play(RPGSystem.S_CursorMove);
			}
			index -= 1;
		}
		else if (Option.repeatDown()){
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
			draw_item(x + 16, y - 6 + (i) * 16 - (display_y * 16), commands[i],
					g);
		}

		g.drawImage(cursor, x + 8, y + index * 16 - (display_y * 16), null);
		draw_border(g);
	}

	public void reload_items(ArrayList<Item> drops) {
		items = null;
		index = 0;
		display_y = -1;
		items = drops;
		commands = new Item[items.size() + 1];
		for (int i = 0; i < commands.length; ++i) {
			if (i == 0) {
				commands[i] = Items.toutprendre;
			} else if (items.get(i-1) != null) {
				commands[i] = items.get(i-1);
			}
		}
	}
	
	public void draw_item(int i, int j, Item item, Graphics g)
	{
		if (item!=null)
		{
			g.drawImage(iconset.getSprite(item.icon_index_x, item.icon_index_y),i,j,null);
			draw_text(i+16,j+4,item.name, g);
		}
	}
}
