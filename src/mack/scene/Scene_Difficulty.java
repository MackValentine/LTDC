package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.window.WindowCommand;
import mack.window.Window_Help;

import org.xml.sax.SAXException;

public class Scene_Difficulty extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	private WindowCommand window_command;
	private Window_Help window_help;

	public Scene_Difficulty(RPGGame f) throws IOException, FontFormatException {
		super(f);

		window_command = new WindowCommand(40, 80, 80, new String[] { "Facile",
				"Normal", "Difficile" }, true);

		window_help = new Window_Help();

	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		window_command.update();
		if (window_command.valid) {
			if (window_command.index == 0) {
				Game_Player.difficulty = 0;
			} else if (window_command.index == 1) {
				Game_Player.difficulty = 1;
			} else if (window_command.index == 2) {
				Game_Player.difficulty = 2;
			}
			if (Option.debug)
				for (int i = 0; i < 49; ++i)
					Game_Player.actor.add_level();
			frame.call_scene(RPGPanel.map);
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		window_command.draw(g);
		window_help.draw(g);

		if (window_command.index == 0) {
			window_help.set_text("Stats des monstres / 2.", g);
		} else if (window_command.index == 1) {
			window_help.set_text("Stats des monstres normales.", g);
		} else if (window_command.index == 2) {
			window_help.set_text("Stats des monstres * 2.", g);
		}
	}

}
