package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.Sound;
import mack.rpg.RPGSystem;

import org.xml.sax.SAXException;

public class Scene_Gameover extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	public Image title;

	public Scene_Gameover(RPGGame f) throws IOException {
		super(f);
		title = Graphics_Loader.load("fond/Gameover.png");
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		if (Option.triggerA()) {
			Sound.sound_play(RPGSystem.S_CursorValid);
			frame.call_scene(new Scene_Title(frame));
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		g.drawImage(title, 0, 0, null);
	}

}
