package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_HUD;
import mack.window.Window_Menu;
import mack.game.Game_Player;
import mack.items.Item;
import mack.items.Items;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;

public class Scene_Village extends Scene_Base {

	public Window_HUD HUD;
	private WindowCommand window_command;
	private Window_Menu fond;

	public Scene_Village(RPGGame f) throws IOException, FontFormatException {
		super(f);

		HUD = new Window_HUD(0, 144, 0, 0);

		String[] tab = { "Apothicaire", "Auberge", "Forge", "Tour" };
		window_command = new WindowCommand(0, 0, 80, tab, true);

		fond = new Window_Menu();

		Game_Player.actor.days = 0;
		Game_Player.actor.hour = 0;
	}

	public void paint(Graphics g) {
		fond.draw(g);

		HUD.draw(g);
		window_command.draw(g);
	}

	public void update() throws IOException, FontFormatException {
		window_command.update();
		if (window_command.valid) {
			if (window_command.index == 0) {
				// Apothicaire
				Item[] items = new Item[] { Items.items_list[99],
						Items.items_list[100], Items.items_list[101],
						Items.items_list[102], Items.items_list[103],
						Items.items_list[110], Items.items_list[111],
						Items.items_list[112] };
				Scene_Shop s = new Scene_Shop(frame, "Magasin");
				s.items = items;
				s.reload();
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(s);
			}
			if (window_command.index == 1) {
				// Auberge
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(new Scene_Inn(frame));
			}
			if (window_command.index == 2) {
				// Forge
				Item[] items = new Item[] { Items.items_list[5],
						Items.items_list[7], Items.items_list[9],
						Items.items_list[51], Items.items_list[52],
						Items.items_list[56] };

				Scene_Shop s = new Scene_Shop(frame, "Forge");
				s.items = items;
				s.reload();
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(s);

				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(s);
			}
			if (window_command.index == 3) {
				Sound.sound_play(RPGSystem.S_CursorAnulation);
				RPGPanel.map.player().change_dir(8);
				frame.call_scene(RPGPanel.map);
			}
		}
		if (Option.triggerB() || Option.triggerSelect()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(RPGPanel.map);
		}
	}
}