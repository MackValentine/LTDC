package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.main.FileSave;
import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSound;
import mack.rpg.RPGSystem;
import mack.sprites.Sprite_Clouds;
import mack.window.WindowCommand;

import org.xml.sax.SAXException;

public class Scene_Title extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	public Image title;
	private WindowCommand window_command;
	private Sprite_Clouds[] clouds;

	public Scene_Title(RPGGame f) throws IOException, FontFormatException,
			ParserConfigurationException, SAXException {
		super(f);

		title = Graphics_Loader.load("fond/Titre.png");

		RPGSound sound = new RPGSound("Music/Title", 0, 0, 0);
		Sound.sound_loop(sound);

		window_command = new WindowCommand(8, 160 - 56, 64, new String[] {
				"Nouveau", "Continuer", "Option" }, true);

		clouds = new Sprite_Clouds[10];

		if (FileSave.save_exist())
			window_command.index = 1;
	}

	public void enter_scene() throws IOException {
		title = Graphics_Loader.load("fond/Titre.png");
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		window_command.update();
		if (window_command.valid) {
			Sound.sound_play(RPGSystem.S_CursorValid);
			if (window_command.index == 0) {
				try {
					new_game();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (window_command.index == 1 && (FileSave.save_exist())) {
				try {
					load();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (window_command.index == 2) {
				option();
			}
		}
	}

	private void option() throws IOException, FontFormatException {
		this.frame.call_scene(new Scene_Option(frame, this));
	}

	public void load() throws IOException, ParserConfigurationException,
			SAXException, FontFormatException, CloneNotSupportedException {
		RPGPanel.loading = true;
		frame.repaint();
		//
		String s = System.getenv("APPDATA") + "/Crepuscule/";
		File d = new File(s);
		if (!d.exists()) {
			d.mkdir();
			return;
		}
		File monFichier = new File(s + "Crepuscule.sav");
		if (monFichier.exists()) {
			FileSave.load();
			this.frame.call_scene(RPGPanel.map);
		}

		RPGPanel.loading = false;
		frame.repaint();
		RPGSound sound = new RPGSound("Music/Dungeon", 0, 0, 0);
		Sound.sound_loop(sound);

		Game_Player.actor.reload_items();
	}

	private void new_game() throws IOException, FontFormatException,
			CloneNotSupportedException {
		if (Option.debug)
			Game_Player.floor = 5;
		else
			Game_Player.floor = 0;
		RPGPanel.create_map();
		frame.call_scene(new Scene_Picture(frame, "Intro"));
		RPGSound sound = new RPGSound("Music/Dungeon", 0, 0, 0);
		Sound.sound_loop(sound);
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		g.drawImage(title, 0, 0, null);

		window_command.draw(g);

		for (int i = 0; i < 10; ++i) {
			if (clouds[i] == null) {
				int h = 0;
				if (i > 5)
					h = 160;
				clouds[i] = new Sprite_Clouds(h);
			} else if (clouds[i].erase == true) {
				clouds[i] = new Sprite_Clouds(160);
			} else {
				clouds[i].draw(g);
			}

		}
	}

}
