package mack.scene;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import mack.main.FileSave;
import mack.main.RPGGame;
import mack.main.Option;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.Window_Option;

public class Scene_Option extends Scene_Base {

	public int scene_id() {
		return 2;
	}

	private Window_Option window_command;
	private int wait_move;
	public Scene_Base scene;
	private boolean call_name;
	private ArrayList<String> name;
	private int wait_name;

	public Scene_Option(RPGGame f, Scene_Base s) throws IOException,
			FontFormatException {
		super(f);

		window_command = new Window_Option(0, 0, 96, new String[] { "Son",
				"Musique", "Bouton A", "Bouton B", "Bouton Select",
				"Bouton Start", "Taille fenetre", "Interface", "Texture Pack",
				"Nom", "Retour" }, false);
		scene = s;

		if (RPGPanel.option.name == null)
			RPGPanel.option.name = "Nathan";

		convert_name(RPGPanel.option.name);
	}

	private void convert_name(String s) {
		name = new ArrayList<String>();
		for (int i = 0; i < RPGPanel.option.name.length(); ++i) {
			name.add("" + RPGPanel.option.name.charAt(i));
		}
	}

	private void unconvert_name(ArrayList<String> n) {
		String s = "";
		for (int i = 0; i < n.size(); ++i) {
			s += n.get(i);
			System.out.print(n.get(i));
		}
		System.out.println(n.size());
		System.out.println("");

		RPGPanel.option.name = s;
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		if (call_name) {
			update_name();

			return;
		}
		window_command.update();
		if (window_command.index == 10) {
			if (window_command.valid) {
				FileSave.save_option();
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(scene);
			}
		}

		if (window_command.index == 9) {
			if (window_command.valid) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				call_name = true;
				RPGPanel.option.name = "";
				convert_name(RPGPanel.option.name);
			}
		}

		if (window_command.index == 8) {
			if (window_command.valid) {
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(new Scene_TexturePack(frame, this));
			}
		}

		if (window_command.index < 2 || window_command.index > 5) {
			if (Option.triggerB() || Option.triggerStart()
					|| Option.triggerSelect()) {
				FileSave.save_option();
				Sound.sound_play(RPGSystem.S_CursorValid);
				frame.call_scene(scene);
			}
		}

		update_volume();
		update_input();
		update_size();
		update_HUD();
	}

	private void update_name() {
		if (RPGGame.key_pressed > 0)
			wait_name++;
		else
			wait_name = -1;
		if (wait_name % 10 == 0) {
			if ((RPGGame.key_pressed > 64 && RPGGame.key_pressed < 91)
					|| (RPGGame.key_pressed > 96 && RPGGame.key_pressed < 123)) {
				if (name.size() < 8) {
					if (Toolkit.getDefaultToolkit().getLockingKeyState(
							KeyEvent.VK_CAPS_LOCK)) {
						name.add(KeyEvent.getKeyText(RPGGame.key_pressed));
					} else {
						name.add(KeyEvent.getKeyText(RPGGame.key_pressed)
								.toLowerCase());
					}

					System.out.println(RPGGame.key_pressed);

					unconvert_name(name);
				}
			} else if (RPGGame.key_pressed == KeyEvent.VK_SPACE) {
				name.add(" ");
				unconvert_name(name);
			} else if (RPGGame.key_pressed == 8) {
				if (name.size() - 1 >= 0)
					name.remove(name.size() - 1);
				unconvert_name(name);
			} else

			if (RPGGame.key_pressed == 27 || RPGGame.key_pressed == 10
					|| RPGGame.key_pressed == 13) {
				call_name = false;
			}
		}

	}

	private void update_HUD() {
		if (window_command.index == 7) {
			if (window_command.valid) {
				RPGPanel.option.HUD += 1;
				if (RPGPanel.option.HUD > 1)
					RPGPanel.option.HUD = 0;
			}
		}
	}

	private void update_size() {
		if (window_command.index == 6) {
			if (window_command.valid) {
				RPGGame.change_size = true;
				RPGPanel.option.SizeScreen += 1;
				if (RPGPanel.option.SizeScreen > 3)
					RPGPanel.option.SizeScreen = 1;
			}
		}
	}

	private void update_input() {
		if (window_command.index == 2) {
			int i = Option.key_pressed();
			if (i != RPGPanel.option.Button_B
					&& i != RPGPanel.option.Button_Select
					&& i != RPGPanel.option.Button_Start
					&& i != KeyEvent.VK_DOWN && i != KeyEvent.VK_UP
					&& i != KeyEvent.VK_LEFT && i != KeyEvent.VK_RIGHT
					&& i != -1)
				RPGPanel.option.Button_A = i;
		} else if (window_command.index == 3) {
			int i = Option.key_pressed();
			if (i != RPGPanel.option.Button_A
					&& i != RPGPanel.option.Button_Select
					&& i != RPGPanel.option.Button_Start
					&& i != KeyEvent.VK_DOWN && i != KeyEvent.VK_UP
					&& i != KeyEvent.VK_LEFT && i != KeyEvent.VK_RIGHT
					&& i != -1)
				RPGPanel.option.Button_B = i;
		} else if (window_command.index == 4) {
			int i = Option.key_pressed();
			if (i != RPGPanel.option.Button_B && i != RPGPanel.option.Button_A
					&& i != RPGPanel.option.Button_Start
					&& i != KeyEvent.VK_DOWN && i != KeyEvent.VK_UP
					&& i != KeyEvent.VK_LEFT && i != KeyEvent.VK_RIGHT
					&& i != -1)
				RPGPanel.option.Button_Select = i;
		} else if (window_command.index == 5) {
			int i = Option.key_pressed();
			if (i != RPGPanel.option.Button_Select
					&& i != RPGPanel.option.Button_A
					&& i != RPGPanel.option.Button_B && i != KeyEvent.VK_DOWN
					&& i != KeyEvent.VK_UP && i != KeyEvent.VK_LEFT
					&& i != KeyEvent.VK_RIGHT && i != -1)
				RPGPanel.option.Button_Start = i;
		}

	}

	public void update_volume() {
		if (window_command.index == 0) {
			if (Option.pressLeft()) {
				wait_move += 1;
				if (wait_move > 5) {
					if (RPGPanel.option.volume_sound > 0F)
						RPGPanel.option.volume_sound -= 0.05F;
					wait_move = 0;
					if (RPGPanel.option.volume_sound < 0F)
						RPGPanel.option.volume_sound = 0F;
				}
			} else if (Option.pressRight()) {
				wait_move += 1;
				if (wait_move > 5) {
					if (RPGPanel.option.volume_sound < 1F)
						RPGPanel.option.volume_sound += 0.05F;
					wait_move = 0;
					if (RPGPanel.option.volume_sound > 2F)
						RPGPanel.option.volume_sound = 2F;
				}
			} else
				wait_move = 4;
		} else if (window_command.index == 1) {
			if (Option.pressLeft()) {
				wait_move += 1;
				if (wait_move > 5) {
					if (RPGPanel.option.volume_music > 0F)
						RPGPanel.option.volume_music -= 0.05F;
					wait_move = 0;
					if (RPGPanel.option.volume_music < 0F)
						RPGPanel.option.volume_music = 0F;
				}
				Sound.change_volume(Sound.loop_volume,
						RPGPanel.option.volume_music);
			} else if (Option.pressRight()) {
				wait_move += 1;
				if (wait_move > 5) {
					if (RPGPanel.option.volume_music < 1F)
						RPGPanel.option.volume_music += 0.05F;
					wait_move = 0;
					if (RPGPanel.option.volume_music > 2F)
						RPGPanel.option.volume_music = 2F;
				}
				Sound.change_volume(Sound.loop_volume,
						RPGPanel.option.volume_music);
			} else
				wait_move = 4;
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
