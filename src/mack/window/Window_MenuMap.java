package mack.window;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Character;
import mack.game.Game_Dungeon;
import mack.game.Game_Enemy;
import mack.game.Game_Map;
import mack.game.Game_Player;
import mack.game.Game_Spike;
import mack.main.Option;
import mack.main.RPGPanel;

public class Window_MenuMap extends Window {

	public int index;
	public int wait_move;

	public Window_MenuMap() throws IOException, FontFormatException {
		super(0, 0, 160, 144);
		index = Game_Player.floor;
	}

	public void draw(Graphics g) {
		super.draw(g);

		draw_map(g);

		draw_text_s(8, 8, "Etage :" + (index + 1), g);

		draw_border(g);
	}

	private void draw_text_s(int i, int j, String string, Graphics g) {
		draw_text_black(i - 1, j, string, g);
		draw_text_black(i + 1, j, string, g);
		draw_text_black(i, j - 1, string, g);
		draw_text_black(i, j + 1, string, g);
		draw_text(i, j, string, g);
	}

	public void update() {
		if (Option.repeatUp()) {
			wait_move += 1;
			if (wait_move > 5) {
				index += 1;
				wait_move = 0;
			}
		} else if (Option.repeatDown()) {
			wait_move += 1;
			if (wait_move > 5) {
				index -= 1;
				wait_move = 0;
			}
		} else {
			wait_move = 5;
		}
		if (index < 0) {
			index = 0;
		}
		if (index > Game_Map.m_floor - 1) {
			index = Game_Map.m_floor - 1;
		}
	}

	public void draw_map(Graphics g) {
		this.draw_back(g);
		g.setColor(new Color(0, 0, 0));
		// g.fillRect(0, 0, 160, 160);
		Game_Dungeon map = RPGPanel.game_map.dungeons[index];// SpritesetMap.map;
		int i = 40 - 16;// 70;
		int j = 40 - 32;// 35;

		for (int y = 0; y < map.max_x; y++) {
			for (int x = 0; x < map.max_y; x++) {
				int c = 0xE6E8FA;
				if (map.getCell(x, y) == 5 || map.getCell(x, y) == 6
						|| map.getCell(x, y) == 15 || map.getCell(x, y) == 21)
					c = 0xE6E8FA;
				if (map.getCell(x, y) == 7)
					c = 0xE1E010;
				if (map.getCell(x, y) == 8 || map.getCell(x, y) == 9
						|| map.getCell(x, y) == 20)
					c = 0x80E010;
				if (map.getCell(x, y) == 2 || map.getCell(x, y) == 16
						|| (map.getCell(x, y) >= 22 && map.getCell(x, y) <= 26))
					c = 0;
				Color color = new Color(c);
				g.setColor(color);

				RPGPanel.map
						.player();
				// if ((map.getCell(x, y) != 2 && map.getCell(x, y) != 16)
				if (((RPGPanel.game_map.has_visit[index][x][y]) || Game_Player.debug_mode))
					g.fillRect(x * 2 + i, y * 2 + j, 2, 2);
			}
		}
		if (index == Game_Player.floor)
			for (int k = 0; k < RPGPanel.map.characters.length; ++k) {
				Game_Character event = RPGPanel.map.characters[k];
				if (event != null && event.exist()) {
					int c = 0;
					if (event instanceof Game_Enemy)
						c = 0xE80218;
					if (event instanceof Game_Player)
						c = 0x20B0FF;
					if (event instanceof Game_Spike)
						c = 0xE80218;

					Color color = new Color(c);
					g.setColor(color);
					if (c != 0) {
						RPGPanel.map.player();
						if (RPGPanel.game_map.has_visit[index][event.x][event.y]
								|| Game_Player.debug_mode)
							g.fillRect(event.x * 2 + i, event.y * 2 + j, 2, 2);
					}
				}
			}
	}

}
