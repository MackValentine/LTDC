package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_HUD;
import mack.window.Window_Help;
import mack.window.Window_Item;

import org.xml.sax.SAXException;

public class Scene_Item extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	public Window_Item window_command;
	private Window_Help window_help;
	private Window_HUD HUD;
	private WindowCommand window_command_items;
	private boolean swap_item;
	private int swap_index;

	public Scene_Item(RPGGame f) throws IOException, FontFormatException {
		super(f);

		window_command = new Window_Item();
		window_help = new Window_Help();
		window_help.height = 32;
		window_command_items = new WindowCommand(0, 0, 80, new String[] {
				"Utiliser", "Jeter", "Deplacer", "Retour" }, true);
		window_command_items.y = 160 - 16 - window_command_items.height;
		HUD = new Window_HUD(0, 144, 0, 0);
		window_command_items.active = false;
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();

		if (window_command_items.active == true) {
			window_command_items.update();
			if (window_command_items.valid) {
				if (window_command_items.index == 0) {
					int i = window_command.index;
					window_command.commands[i].use_item(RPGPanel.map,
							(Game_Player) RPGPanel.map.player(), i);
					window_command_items.active = false;
					frame.call_scene(RPGPanel.map);
				} else if (window_command_items.index == 1) {
					int i = window_command.index;
					Game_Player.actor.remove_item(i);
					window_command_items.active = false;
					window_command.reload_items();
				} else if (window_command_items.index == 2) {
					swap_index = window_command.index;
					swap_item = true;
					window_command_items.active = false;
				} else if (window_command_items.index == 3) {
					window_command_items.active = false;
				}
			}
			if (Option.triggerB()) {
				window_command_items.active = false;
			}
		} else {
			window_command.update();

			if (window_command.commands.length != Game_Player.actor.items.length) {
				window_command.reload_items();
			}

			if (Option.triggerA()) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				if (swap_item == false) {
					int i = window_command.index;
					if (window_command.commands[i] != null) {
						window_command_items.active = true;
						window_command_items.index = 0;
					}
					if (i % 9 > 4)
						window_command_items.x = 0;
					else
						window_command_items.x = 80;
				} else {
					int id = window_command.index;
					Item i = Game_Player.actor.items[id];

					Game_Player.actor.items[id] = Game_Player.actor.items[swap_index];

					Game_Player.actor.items[swap_index] = i;
					swap_item = false;
				}
			}

			if (Option.triggerB()) {
				if (swap_item == false)
					frame.call_scene(new Scene_Menu(frame));
				else
					swap_item = false;
			} else if (Option.triggerSelect()) {

				Item[] items = new Item[Game_Player.actor.max_item()];
				for (int i = 0; i < Game_Player.actor.max_item(); ++i) {
					items[i] = Game_Player.actor.items[i];
				}

				Comparator<Item> comparator = new Comparator<Item>() {

					public int compare(Item i1, Item i2) {
						int i = 1;
						if (i2 != null) {
							if (i1 != null) {
								i = Integer.compare(i1.id, i2.id);
							} else {
								i = 1;
							}
						} else {
							i = -1;
						}

						return i;
					}

				};
				Arrays.sort(items, comparator);

				// items = tri();

				Game_Player.actor.items = items;

				window_command.reload_items();

			}
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		window_command.draw(g);
		window_help.draw(g);
		HUD.draw(g);

		if (window_command_items.active)
			window_command_items.draw(g);

		int i = window_command.index;
		if (i < 0)
			i = 0;

		if (i < window_command.commands.length)
			if (window_command.commands[i] != null) {
				window_help.set_text(window_command.commands[i].name + "\n"
						+ window_command.commands[i].description, g);
			}
	}

}
