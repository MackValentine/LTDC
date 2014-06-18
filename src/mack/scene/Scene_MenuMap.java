package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.Window_HUD;
import mack.window.Window_MenuMap;

import org.xml.sax.SAXException;

public class Scene_MenuMap extends Scene_Base {

	private Window_HUD HUD;
	private Window_MenuMap background;


	public int scene_id() {
		return 1;
	}

	public Scene_MenuMap(RPGGame f) throws IOException, FontFormatException {
		super(f);
		background = new Window_MenuMap();
		HUD=new Window_HUD(0,144,0,0);
		
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		background.update();
		if (Option.triggerB()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(new Scene_Menu(frame));
		} else if (Option.triggerSelect()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(RPGPanel.map);
		}
	}


	public void paint(Graphics g) throws IOException {
		super.paint(g);
		background.draw(g);
		HUD.draw(g);
	}

}
