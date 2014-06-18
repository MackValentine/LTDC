package mack.window;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import mack.main.Option;
import mack.main.RPGPanel;

public class Window_Option extends WindowCommand {

	public Window_Option(int i, int j, int l, String[] s, boolean b)
			throws IOException, FontFormatException {
		super(i, j, l, s, b);
	}

	public void draw(Graphics g) {
		super.draw(g);

		g.setColor(new Color(255, 255, 255));

		g.fillRect(48 + 8, 10, (int) (48 * RPGPanel.option.volume_sound), 2);
		draw_text(144 - 32, 8, (Math.round(RPGPanel.option.volume_sound * 100))
				+ "%", g);
		g.fillRect(48 + 8, 22, (int) (48 * RPGPanel.option.volume_music), 2);
		draw_text(144 - 32, 20,
				(Math.round(RPGPanel.option.volume_music * 100)) + "%", g);

		draw_text(144 - 32, 32, KeyEvent.getKeyText(RPGPanel.option.Button_A),
				g);
		draw_text(144 - 32, 44, KeyEvent.getKeyText(RPGPanel.option.Button_B),
				g);
		draw_text(144 - 32, 56,
				KeyEvent.getKeyText(RPGPanel.option.Button_Select), g);

		draw_text(144 - 32, 56 + 12,
				KeyEvent.getKeyText(RPGPanel.option.Button_Start), g);

		draw_text(144 - 32, 68 + 12, "x " + RPGPanel.option.SizeScreen, g);

		draw_text(144 - 32, 68 + 24, "Type " + RPGPanel.option.HUD, g);

		draw_text(144 - 32, 68 + 36, Option.texture_pack().replace("/", ""), g);

		draw_text(144 - 32, 68 + 48, RPGPanel.option.name, g);
	}

}
