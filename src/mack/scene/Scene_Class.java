package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.main.RPGGame;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_Help;
import mack.window.Window_Menu;

import org.xml.sax.SAXException;

public class Scene_Class extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	private WindowCommand window_command;
	private Window_Help window_help;
	private Window_Menu window_status;

	public Scene_Class(RPGGame f) throws IOException, FontFormatException {
		super(f);

		window_command = new WindowCommand(0, 16, 80, new String[] {
				"Ranger", "Paladin", "Barbare", "Clerc", "Mage" }, true);
		window_help = new Window_Help();

		Game_Player.actor.reset_stat();
		window_status = new Window_Menu(1);
		window_status.y=16;

	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		window_command.update();
		Game_Player.actor.reset_stat();
		Game_Player.actor.set_class(window_command.index);
		if (window_command.valid) {
			Sound.sound_play(RPGSystem.S_CursorValid);
			frame.call_scene(new Scene_Difficulty(frame));
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		window_status.draw(g);
		window_help.draw(g);
		window_command.draw(g);

		String s = "";
		switch (window_command.index) {
		case 0:
			s = "Attaque et endurance equilibre.";
			break;
		case 1:
			s = "Endurance haute.";
			break;
		case 2:
			s = "Force haute.";
			break;
		case 3:
			s = "Force et Magie equilibre.";
			break;
		case 4:
			s = "Magie et Esprit haute.";
			break;
		}
		window_help.set_text(s, g);
	}

}
