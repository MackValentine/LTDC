package mack.main;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Option implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4837211274415063099L;

	public static final int WIDTH = 160;

	public static final int HEIGHT = 160;

	public int HUD = 0;

	public int SizeScreen = 3;

	public static boolean debug = false;

	public int Button_A = KeyEvent.VK_SPACE;
	public int Button_B = KeyEvent.VK_X;
	public int Button_Select = KeyEvent.VK_W;
	public int Button_Start = KeyEvent.VK_Q;

	public int Button_Power = KeyEvent.VK_F12;

	public float sound = 1.0F;
	public float music = 1.0F;

	private static int triggerUp;
	private static int triggerDown;
	private static int triggerLeft;
	private static int triggerRight;

	private static int triggerB;
	private static int triggerA;
	private static int triggerStart;

	private static int triggerPower;

	private static int triggerSelect;

	public float volume_sound = 1.0F;

	public float volume_music = 1.0F;

	public String texture_pack = "";

	public String name;

	public static boolean pressRight() {
		return RPGGame.pressRight;
	}

	public static boolean pressDown() {
		return RPGGame.pressDown;
	}

	public static boolean pressUp() {

		return RPGGame.pressUp;
	}

	public static boolean pressLeft() {
		return RPGGame.pressLeft;
	}

	public static boolean pressB() {
		return RPGGame.pressB;
	}

	public static boolean triggerA() {
		if (RPGGame.pressA)
			triggerA += 1;
		else
			triggerA = 0;
		return triggerA == 1;
	}

	public static boolean triggerPower() {
		if (RPGGame.pressPower)
			triggerPower += 1;
		else
			triggerPower = 0;
		return triggerPower == 1;
	}

	public static boolean triggerSelect() {
		if (RPGGame.pressSelect)
			triggerStart += 1;
		else
			triggerStart = 0;
		return triggerStart == 1;
	}

	public static boolean repeatUp() {
		if (RPGGame.pressUp)
			triggerUp += 1;
		else
			triggerUp = 0;
		return triggerUp % 10 == 1 && RPGGame.pressUp;
	}

	public static boolean repeatDown() {
		if (RPGGame.pressDown)
			triggerDown += 1;
		else
			triggerDown = 0;
		return triggerDown % 10 == 1 && RPGGame.pressDown;
	}

	public static boolean repeatLeft() {
		if (RPGGame.pressLeft)
			triggerLeft += 1;
		else
			triggerLeft = 0;
		return triggerLeft % 10 == 1 && RPGGame.pressLeft;
	}

	public static boolean repeatRight() {
		if (RPGGame.pressRight)
			triggerRight += 1;
		else
			triggerRight = 0;
		return triggerRight % 10 == 1 && RPGGame.pressRight;
	}

	public static boolean triggerB() {
		if (RPGGame.pressB)
			triggerB += 1;
		else
			triggerB = 0;
		return triggerB == 1;
	}

	public static boolean triggerStart() {
		if (RPGGame.pressStart)
			triggerSelect += 1;
		else
			triggerSelect = 0;
		return triggerSelect == 1;
	}

	public static int key_pressed() {
		return RPGGame.key_pressed;
	}

	public static String texture_pack() {
		String s = RPGPanel.option.texture_pack;
		if (s != "") {
			s += "/";
		} else {
			s = "pics/" + s;
		}

		return s;
	}

}
