package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.Window_Equip;
import mack.window.Window_EquipItem;
import mack.window.Window_HUD;
import mack.window.Window_Help;

import org.xml.sax.SAXException;

public class Scene_Equip extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	private Window_Equip window_command;
	private Window_HUD HUD;
	private Window_Help window_help;
	private int old_index;
	public static Window_EquipItem background;

	public Scene_Equip(RPGGame f) throws IOException, FontFormatException {
		super(f);
		window_command = new Window_Equip();

		background = new Window_EquipItem(window_command);

		window_help = new Window_Help();

		HUD = new Window_HUD(0, 144, 0, 0);

	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		if (window_command.active) {
			window_command.update();
			if (Option.triggerA()) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				window_command.active = false;
			}

			if (Option.triggerB()) {
				Sound.sound_play(RPGSystem.S_CursorAnulation);
				frame.call_scene(new Scene_Menu(frame));
			}
		} else {
			background.update();
			if (Option.triggerA()) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				Item item = background.items.get(background.index);
				if (item != null) {
					int id = window_command.index;
					Game_Player.actor.remove_item(item, 1);
					if (Game_Player.actor.equipements[id] != null)
						Game_Player.actor.add_item(
								Game_Player.actor.equipements[id], 1);
					Game_Player.actor.equip(item, window_command.index);
					window_command.reload_items();
					background.reload_items();
				} else if (background.index == 0) {
					if (Game_Player.actor.equipements[window_command.index] != null)
						Game_Player.actor
								.add_item(
										Game_Player.actor.equipements[window_command.index],
										1);

					Game_Player.actor.equip(null, window_command.index);
					
					window_command.reload_items();
					background.reload_items();
				}
				window_command.active = true;
			}

			if (Option.triggerB()) {
				Sound.sound_play(RPGSystem.S_CursorAnulation);
				// new
				// Sound("Sound/select_cancel").play(1.0F,Crepusucle.volume_sound);
				window_command.active = true;

			}
		}

	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		background.draw(g);
		window_command.draw(g);
		window_help.draw(g);

		HUD.draw(g);

		int i = window_command.index;
		if (window_command.active) {
			if (window_command.index != old_index) {
				old_index = window_command.index;
				background.reload_items();
			}

			if (window_command.commands[i] != null) {
				window_help.set_text(window_command.commands[i].description, g);
			}
		} else {
			if (background.items.get(background.index) != null) {
				window_help.set_text(
						background.items.get(background.index).description, g);
			}
		}
	}

}
