package mack.scene;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;

public class Scene_TexturePack extends Scene_Base {

	private WindowCommand window_command;
	private Scene_Base scene;
	private String[] list;

	public Scene_TexturePack(RPGGame f, Scene_Base s) throws IOException,
			FontFormatException {
		super(f);
		scene = s;
		ArrayList<String> t = new ArrayList<String>();

		String s3 = System.getenv("APPDATA").replace("\\", "/");
		String p = s3 + "/Crepuscule/TexturePacks/";

		File r = new File(p);

		if (!r.exists())
			r.mkdir();

		String[] l = r.list();
		t.add("Ouvrir le dossier.");
		t.add("Aucun");
		for (int i = 0; i < l.length; ++i) {
			File f2 = new File(p + "/" + l[i]);
			if (f2.isDirectory()) {
				t.add(l[i]);
			}
		}
		t.add("Retour");

		list = new String[t.size()];
		for (int i = 0; i < t.size(); ++i) {
			list[i] = t.get(i);
		}

		window_command = new WindowCommand(0, 0, 160, 160,list, false);
	}

	public void update() throws IOException {
		window_command.update();
		
		if (Option.triggerB() || Option.triggerStart()){
			Sound.sound_play(RPGSystem.S_CursorValid);
			frame.call_scene(scene);
		}
		if (window_command.index == 0) {
			if (window_command.valid) {
				String s3 = System.getenv("APPDATA").replace("\\", "/");
				String p = s3 + "/Crepuscule/TexturePacks/";
				Desktop.getDesktop().open(new File(p));
			}
		} else if (window_command.index == 1) {
			if (window_command.valid) {
				RPGPanel.option.texture_pack = "";
				Sound.sound_play(RPGSystem.S_CursorValid);
			}
		} else if (window_command.index == list.length - 1) {
			if (window_command.valid) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(scene);
			}
		} else {
			if (window_command.valid) {
				RPGPanel.option.texture_pack = list[window_command.index];
				Sound.sound_play(RPGSystem.S_CursorValid);
			}
		}

	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		Color c = new Color(0, 0, 0);
		g.setColor(c);
		g.fillRect(0, 0, 160, 160);

		window_command.draw(g);
	}

}
