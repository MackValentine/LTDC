package mack.main;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.scene.Scene_Base;
import mack.scene.Scene_Start;

import org.xml.sax.SAXException;

public class RPGGame {

	public static int panel_id;

	public RPGPanel panel;

	public static KeyListener keyListener;

	public static boolean pressRight;

	protected static boolean pressLeft;

	protected static boolean pressUp;

	protected static boolean pressDown;

	public static Sound sound;

	public static boolean change_size;

	protected static boolean pressSelect;

	protected static boolean pressA;

	protected static boolean pressB;

	public static int key_pressed;

	protected static boolean pressStart;

	public static boolean pressPower;

	public RPGGame(boolean a) throws IOException, ParserConfigurationException,
			SAXException, FontFormatException {

		sound = new Sound(this);

		panel = new RPGPanel(this, a);

		Scene_Start title = new Scene_Start(this);
		call_scene(title, 30);

		keyListener = new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == RPGPanel.option.Button_Power) {
					RPGGame.pressPower = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					RPGGame.pressRight = false;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					RPGGame.pressLeft = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					RPGGame.pressUp = false;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					RPGGame.pressDown = false;
				}

				if (e.getKeyCode() == RPGPanel.option.Button_A) {
					RPGGame.pressA = false;
				}
				if (e.getKeyCode() == RPGPanel.option.Button_B) {
					RPGGame.pressB = false;
				}
				if (e.getKeyCode() == RPGPanel.option.Button_Select) {
					RPGGame.pressSelect = false;
				}
				if (e.getKeyCode() == RPGPanel.option.Button_Start) {
					RPGGame.pressStart = false;
				}

				RPGGame.key_pressed = -1;
			}

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == RPGPanel.option.Button_Power) {
					RPGGame.pressPower = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					RPGGame.pressRight = true;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					RPGGame.pressLeft = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					RPGGame.pressUp = true;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					RPGGame.pressDown = true;
				}

				if (e.getKeyCode() == RPGPanel.option.Button_A) {
					RPGGame.pressA = true;
				}
				if (e.getKeyCode() == RPGPanel.option.Button_B) {
					RPGGame.pressB = true;
				}
				if (e.getKeyCode() == RPGPanel.option.Button_Select) {
					RPGGame.pressSelect = true;
				}

				if (e.getKeyCode() == RPGPanel.option.Button_Start) {
					RPGGame.pressStart = true;
				}

				RPGGame.key_pressed = e.getKeyCode();
			}
		};
	}

	public void call_scene(Scene_Base p) throws IOException {
		call_scene(p, 5);
	}

	public void call_scene(Scene_Base p, int i) throws IOException {
		p.enter_scene();
		Scene_Base.wait_transition = i;
		Scene_Base.need_transition = true;
		panel.next_scene = p;
		panel_id = p.scene_id();
		// if (p instanceof Scene_Map)
		// Sprite_Map.animation = true;
	}

	public void repaint() {
		panel.repaint();
	}

}