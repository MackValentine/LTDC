package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.RPGGame;

import org.xml.sax.SAXException;

public class Scene_Start extends Scene_Base {

	public int scene_id() {
		return 1;
	}

	public Image title;
	private int wait;

	public Scene_Start(RPGGame f) throws IOException, FontFormatException,
			ParserConfigurationException, SAXException {
		super(f);
		title = Graphics_Loader.load("fond/Start.png");
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		++wait;
		if (Option.triggerA() || Option.triggerB() || Option.triggerStart()
				|| Option.triggerSelect() || wait > 160) {
			frame.call_scene(new Scene_Title(frame));
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		g.drawImage(title, 0, 0, null);
	}

}
