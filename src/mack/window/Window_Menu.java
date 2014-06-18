package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Player;
import mack.items.Item;

public class Window_Menu extends Window {

	private int type;

	public Window_Menu() throws IOException, FontFormatException {
		super(0, 0, 160, 144);

	}

	public Window_Menu(int i) throws IOException, FontFormatException {
		super(0, 0, 160, 144);
		type = 1;
	}

	public void draw(Graphics g) {
		super.draw(g);
		draw_back(g);

		int i = 18 + y;
		int j = -12;
		if (type == 0)
			j = -4;

		draw_text(85, 8 + i, "Nathan Hugues", g);

		draw_text(85, 16 + i,
				"Niveau : " + String.valueOf(Game_Player.actor.level), g);

		draw_text(85, 24 + i, "HP : " + String.valueOf(Game_Player.actor.Hp)
				+ "/" + String.valueOf(Game_Player.actor.mHp), g);

		draw_text(85, 32 + i, "Exp : " + String.valueOf(Game_Player.actor.exp)
				+ "/" + String.valueOf(Game_Player.actor.need_up), g);

		draw_text(85, 88 + i, "G : " + String.valueOf(Game_Player.actor.gold),
				g);
		draw_text(85, 96 + i,
				"PC : " + String.valueOf(Game_Player.actor.lv_up_pts), g);

		draw_text(85, 44 + i,
				"Force : " + String.valueOf(Game_Player.actor.total_force()), g);

		draw_text(
				85,
				54 + i,
				"Endurance : "
						+ String.valueOf(Game_Player.actor.total_endurance()),
				g);

		draw_text(85, 64 + i,
				"Sagesse : " + String.valueOf(Game_Player.actor.total_sagesse()),
				g);

		draw_text(85, 74 + i,
				"Esprit : " + String.valueOf(Game_Player.actor.total_esprit()),
				g);

		if (Game_Player.actor.equipements[0] != null) {
			draw_item(8, 80 + i - 4 + j, Game_Player.actor.equipements[0], g);
		}

		if (Game_Player.actor.equipements[1] != null) {
			draw_item(8, 96 + i - 4 + j, Game_Player.actor.equipements[1], g);
		}
		if (Game_Player.actor.equipements[2] != null) {
			draw_item(8, 112 + i - 4 + j, Game_Player.actor.equipements[2], g);
		}
		draw_border(g);
	}

	public void draw_item(int i, int j, Item item, Graphics g) {
		if (item != null) {
			g.drawImage(
					iconset.getSprite(item.icon_index_x, item.icon_index_y), i,
					j, null);
			draw_text(i + 16, j + 4, item.name, g);
		}
	}

}
