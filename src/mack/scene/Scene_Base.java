package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.main.RPGGame;

import org.xml.sax.SAXException;

public class Scene_Base {

	public int scene_id() {
		return 0;
	}

	public RPGGame frame;

	public static boolean need_transition;
	public static int wait_transition;
	public static float transition_time = 5.0F;

	public Scene_Base(RPGGame f) throws IOException {
		frame = f;
	}

	public void enter_scene() throws IOException {
		
	}

	public void paint(Graphics g) throws IOException {
		g.fillRect(0, 0, 160, 160);

	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {

	}



}
