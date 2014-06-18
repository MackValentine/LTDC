package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.main.FileSave;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_HUD;
import mack.window.Window_Menu;

import org.xml.sax.SAXException;

public class Scene_Menu extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	private WindowCommand window_command;
	private Window_Menu window_menu;
	private Window_HUD HUD;

	public Scene_Menu(RPGGame f) throws IOException, FontFormatException {
		super(f);
		window_menu = new Window_Menu();

		window_command = new WindowCommand(0, 0, 80, new String[] { "Objets",
				"Equipements", "Status","Sauvegarder", "Options", "Retour" },
				true);

		HUD = new Window_HUD(0, 144, 0, 0);

	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		window_command.update();
		if (window_command.valid) {
			if (window_command.index == 0){
				Scene_Item s = new Scene_Item(frame);
				s.window_command.reload_items();
				frame.call_scene(s);
			}
			else if (window_command.index == 1) {
				Scene_Equip s = new Scene_Equip(frame);
				Scene_Equip.background.reload_items();
				frame.call_scene(s);
			}
			else if (window_command.index == 2) {
				Scene_Status s = new Scene_Status(frame);
				frame.call_scene(s);
			}
			else if (window_command.index == 3) {
				FileSave.save();
				frame.call_scene(RPGPanel.map);
			}else if (window_command.index == 4){
				Scene_Option s = new Scene_Option(frame, this);
				frame.call_scene(s);
			}
			else if (window_command.index == 5){
				frame.call_scene(RPGPanel.map);
			}
		}

		if (Option.triggerB() || Option.triggerSelect()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(RPGPanel.map);
		}
	}

	public void paint(Graphics g) throws IOException  {
		super.paint(g);
		window_menu.draw(g);
		window_command.draw(g);
		HUD.draw(g);
	}

}
