package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.items.Item;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.Window_Loot;
import mack.window.Window_HUD;
import mack.window.Window_Help;

import org.xml.sax.SAXException;

public class Scene_Loot extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	public Image title;
	private Window_Loot window_command;
	private Window_Help window_help;
	private Window_HUD window_hud;
	private ArrayList<Item> loots;

	public Scene_Loot(RPGGame f, ArrayList<Item> d) throws IOException,
			FontFormatException {
		super(f);

		loots = d;
		window_command = new Window_Loot();
		window_command.reload_items(loots);
		window_help = new Window_Help();

		window_hud = new Window_HUD(0, 144, 0, 0);
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {

		super.update();

		window_command.update();
		if (Option.triggerA()) {
			Sound.sound_play(RPGSystem.S_CursorValid);
			if (window_command.index > 0) {
				int i = window_command.index - 1;
				Item it = loots.get(i);
				boolean b = false;
				if (it != null)
					b = Game_Player.actor.add_item(it, 1);

				if (b == false) {
					Scene_Drop s = new Scene_Drop(frame, this);
					s.window_command.reload_items();
					frame.call_scene(s);
					return;
				}

				loots.remove(i);
				window_command.reload_items(loots);
				if (loots.size() == 0)
					frame.call_scene(RPGPanel.map);
			} else {

				for (int i = 0; i < loots.size(); ++i) {
					Item it = loots.get(i);
					boolean b = false;
					if (it != null){
						b = Game_Player.actor.add_item(it, 1);
					}

					if (b == false) {
						window_command.reload_items(loots);
						Scene_Drop s = new Scene_Drop(frame, this);
						s.window_command.reload_items();
						frame.call_scene(s);
						return;
					}
					loots.set(i, null);

				}
				int items = 0;
				for (int i = 0; i < loots.size(); ++i) {
					if (loots.get(i) != null)
						items++;
				}

				window_command.reload_items(loots);
				if (items == 0)
					frame.call_scene(RPGPanel.map);

			}
		}
		if (Option.triggerB()) {
			frame.call_scene(RPGPanel.map);
			Sound.sound_play(RPGSystem.S_CursorAnulation);
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);

		g.fillRect(0, 0, 160, 160);
		window_hud.draw(g);
		g.drawImage(title, 0, 0, null);

		window_command.draw(g);

		window_help.draw(g);
		int i = window_command.index;
		if (i < 0)
			i = 0;
		if (window_command.commands[i] != null) {
			window_help.set_text(window_command.commands[i].description, g);
		}
	}

}
