package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.RPGGame;

public class Scene_Picture extends Scene_Base {

	public int scene_id() {
		return 3;
	}

	public String title;
	private BufferedImage picture;
	private int pattern = 1;

	public Scene_Picture(RPGGame f, String fond) throws IOException {
		super(f);
		pattern = 1;
		title = fond;

		picture = (BufferedImage) Graphics_Loader.load("fond/" + fond + "/" + pattern + ".png");
		
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		if (Option.triggerA()) {
			pattern++;
			if (file_exist(title + "/" + pattern + ".png")) {
				picture = (BufferedImage) Graphics_Loader.load("fond/" + title + "/" + pattern + ".png");
			} else {
				end_scene();
			}
		}
		if (Option.triggerSelect()) {
			end_scene();
		}
	}

	private void end_scene() throws IOException, FontFormatException,
			ParserConfigurationException, SAXException {
		if (title == "Tutorial")
			frame.call_scene(new Scene_Class(frame));
		else if (title == "Intro")
			frame.call_scene(new Scene_Picture(frame, "Tutorial"));
		else
			frame.call_scene(new Scene_Title(frame));
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		g.drawImage(picture, 0, 0, null);
	}

	public boolean file_exist(String n) throws IOException,
			ParserConfigurationException, SAXException {

		String s = "/pics/fond/" + n;

		InputStream is = getClass().getResourceAsStream(s);
		if (is != null) {
			return true;
		}

		return false;
	}

}
