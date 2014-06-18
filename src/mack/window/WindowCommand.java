package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.Sound;
import mack.rpg.RPGSystem;

public class WindowCommand extends Window {

	public String[] commands;
	public Image cursor;
	public int index;
	private boolean back;
	public boolean valid;
	private int display_y;

	public WindowCommand(int i, int j, int l, String[] s, boolean b)
			throws IOException, FontFormatException {
		super(i, j, l, s.length * 12 + 16);
		back = b;
		commands = s;
		cursor = Graphics_Loader.load("window/Cursor.png");

	}

	public WindowCommand(int i, int j, int l, int h, String[] s, boolean b)
			throws IOException, FontFormatException {
		super(i, j, l, s.length * 12 + 16);
		back = b;
		commands = s;
		cursor = Graphics_Loader.load("window/Cursor.png");
		height = h;
	}

	public void update() {

		if (index > display_y + 11) {
			if (index < commands.length) {
				display_y += 1;
			}
		}

		if (index <= display_y) {
			if (index > 0) {
				display_y -= 1;
			}
		}

		if (Option.repeatDown()) {
			if (index < commands.length - 1) {
				Sound.sound_play(RPGSystem.S_CursorMove);
				index += 1;
			}
		} else if (Option.repeatUp()) {
			if (index > 0) {
				Sound.sound_play(RPGSystem.S_CursorMove);
				index -= 1;
			}
		}

		if (Option.triggerA()) {
			Sound.sound_play(RPGSystem.S_CursorValid);
			valid = true;
		} else
			valid = false;
	}

	public void draw(Graphics g) {
		if (back == true)
			this.draw_back(g);
		for (int i = 0; i < commands.length; ++i) {
			if (i == index)
				draw_text(6 + x, y + i * 12 + 10 - (display_y * 12),
						commands[i], g);
			else
				draw_text(4 + x, y + i * 12 + 10 - (display_y * 12),
						commands[i], g);
		}
		if (back == true)
			this.draw_border(g);

		g.drawImage(cursor, x, y + index * 12 + 10 - (display_y * 12), null);
	}

}
