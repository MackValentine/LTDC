package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Option;

public class Window_ShopNumber extends Window {

	public boolean sell = false;
	public int index = 0;
	public int max = 99;
	public int wait_move = 0;

	public Item item;

	public Window_ShopNumber(int i, int j) throws IOException,
			FontFormatException {
		super(i, j, 48, 48);
		active = false;

	}

	public void draw(Graphics g) {
		super.draw(g);
		draw_back(g);

		int p = item.price;
		if (sell) {
			p /= 2;
		}

		for (int a = 0; a < 3; ++a) {
			if (Game_Player.actor.equipements[a].id == item.id) {
				draw_text(x + 36, y + 8, "E", g);
			}
		}

		draw_text(x + 8, y + 8, index + "/" + max, g);
		draw_text(x + 8, y + 18, (p * index) + " G", g);

		draw_text(x + 8, y + 32, Game_Player.actor.gold + " G", g);

		draw_border(g);
	}

	public void update() {
		if (Option.pressLeft()) {
			wait_move += 1;
			if (wait_move > 5) {
				if (index > 0) {
					--index;
				}
				wait_move = 0;
			}

		} else if (Option.pressRight()) {
			wait_move += 1;
			if (wait_move > 5) {
				if (index < max) {
					++index;
				}
				wait_move = 0;
			}
		} else {
			wait_move = 5;
		}

		if (Option.triggerA()) {
			active = false;

			if (index > 0) {
				if (Game_Player.actor.gold >= (item.price) * index) {
					Game_Player.actor.add_item(item, index);
					Game_Player.actor.gold -= (item.price) * index;
				}

			}
		}
	}

	public void set_item(Item i) {
		index = 0;
		item = i;
		if (sell) {
			max = Game_Player.actor.item_number(i);
			for (int a = 0; a < 3; ++a) {
				if (Game_Player.actor.equipements[a].id == i.id) {
					--max;
				}
			}
		} else {
			max = Game_Player.actor.max_items();
		}
	}
}
