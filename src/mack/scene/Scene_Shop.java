package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_HUD;
import mack.window.Window_Help;
import mack.window.Window_Menu;
import mack.window.Window_Shop;
import mack.window.Window_ShopNumber;

public class Scene_Shop extends Scene_Base {

	public Window_HUD HUD;

	public Item[] items;

	public static Window_Shop buy_window;
	public static Window_Shop sell_window;

	public Window_Help window_help;

	public WindowCommand window_command;

	public Window_ShopNumber shop_number;

	private Window_Menu window_menu;

	public Scene_Shop(RPGGame f, String s) throws IOException, FontFormatException {
		super(f);
		String[] tab = { "Acheter", "Vendre", "Retour" };
		window_command = new WindowCommand(0, 0, 80, tab, true);
		window_command.active = true;

		window_help = new Window_Help();

		buy_window = new Window_Shop(false);
		buy_window.active = false;

		sell_window = new Window_Shop(true);
		sell_window.active = false;

		HUD = new Window_HUD(0, 144, 0, 0);

		shop_number = new Window_ShopNumber(0, 16);
		window_menu = new Window_Menu();
		
	}

	public void paint(Graphics g) {
		window_menu.draw(g);

		if (window_command.index == 0)
			buy_window.draw(g);
		else if (window_command.index == 1)
			sell_window.draw(g);

		HUD.draw(g);
		window_help.draw(g);
		if (!window_command.active) {
			int i;
			if (window_command.index == 0) {
				i = buy_window.index;
				if (buy_window.commands[i] != null) {
					window_help.set_text(buy_window.commands[i].description,g);
				}
			} else {
				i = sell_window.index;
				if (sell_window.commands[i] != null) {
					window_help.set_text(sell_window.commands[i].description,g);
				}
			}
		}

		if (window_command.active)
			window_command.draw(g);

		if (shop_number.active) {
			shop_number.draw(g);
		}

	}

	public void update() throws IOException, FontFormatException {
		if (shop_number.active) {
			shop_number.update();
		}
		if (!window_command.active) {
			if (!shop_number.active) {
				if (window_command.index == 0) {
					buy_window.update();

					if (Option.triggerA()) {
						Sound.sound_play(RPGSystem.S_CursorValid);
						int i = buy_window.index;
						if (buy_window.commands[i] != null) {
							shop_number.sell = false;
							shop_number.set_item(buy_window.commands[i]);
							shop_number.active = true;
						}
					}
				} else if (window_command.index == 1) {
					sell_window.update();

					if (Option.triggerA()) {
						Sound.sound_play(RPGSystem.S_CursorValid);
						int i = sell_window.index;
						if (sell_window.commands[i] != null) {
							Game_Player.actor.remove_item(i);
							Game_Player.actor.gold+=(sell_window.commands[i].price/2);
							Scene_Shop.sell_window.reload_items();
						}
					}
				}
			} else {

			}
		} else {
			window_command.update();
			if (window_command.valid) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				if (window_command.index == 0 || window_command.index == 1) {
					window_command.active = false;
				} else {
					frame.call_scene(new Scene_Village(frame));
				}
			}
		}
		if (Option.triggerB()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			if (!window_command.active)
				if (shop_number.active)
					shop_number.active = false;
				else
					window_command.active = true;
			else
				frame.call_scene(new Scene_Village(frame));
		}
	}

	public void reload() {
		window_command.active = true;
		buy_window.reload_items(items);
		sell_window.reload_items();
	}
}
