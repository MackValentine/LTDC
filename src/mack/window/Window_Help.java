package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

public class Window_Help extends Window {

	public Window_Help() throws IOException, FontFormatException {
		super(0, 0, 160, 16);
	}

	public void draw(Graphics g) {
		super.draw(g);
		draw_back(g);
		draw_border(g);
	}

	public void set_text(String s, Graphics g) {
		
		String[] text = s.split("\n");
		int max = text.length;
		for (int i = 0; i < max; ++i) {
			draw_text(4, 4 + i * 12, text[i], g);
		}
		draw_border(g);
	}

}
