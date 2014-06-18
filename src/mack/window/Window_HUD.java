package mack.window;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import mack.game.Game_Player;
import mack.main.Graphics_Loader;
import mack.main.RPGPanel;
import mack.sprites.SpriteSheet;

public class Window_HUD extends Window {

	private Image back;
	private Image exp_bar;
	private Image life_bar;
	private SpriteSheet clock_spritesheet;

	public Window_HUD(int i, int j, int k, int l) throws IOException,
			FontFormatException {
		super(i, j, k, l);
		back = Graphics_Loader.load("window/HUD.png");
		exp_bar = Graphics_Loader.load("window/HUD/Exp_Bar.png");
		life_bar = Graphics_Loader.load("window/HUD/Life_Bar.png");

		clock_spritesheet = new SpriteSheet("window/Clock.png", 160, 16);
	}

	public void draw(Graphics g) {
		g.drawImage(back, x, y, null);

		int hw = Game_Player.actor.Hp * 38 / Game_Player.actor.mHp;
		g.drawImage(life_bar, x + 28, y + 2, hw, 4, null);

		g.drawImage(
				clock_spritesheet.getSprite(0, Game_Player.actor.hour % 12),
				x - 2, y, null);
		if (Game_Player.actor.days > 10)
			draw_number_red(x + 119, y + 6, Game_Player.actor.hour / 12, g);
		else
			draw_number(x + 119, y + 6, Game_Player.actor.hour / 12, g);

		int i;
		if (RPGPanel.option.HUD == 0)
			i = Game_Player.actor.Hp * 100 / Game_Player.actor.mHp;
		else
			i = Game_Player.actor.Hp;

		draw_number(x + 4, y + 1, i, g);

		int ew = Game_Player.actor.exp * 38 / Game_Player.actor.need_up;

		g.drawImage(exp_bar, x + 28, y + 10, ew, 4, null);

		int j = Game_Player.actor.exp * 100 / Game_Player.actor.need_up;
		if (RPGPanel.option.HUD == 1)
			j = Game_Player.actor.exp;
		draw_number(x + 4, y + 9, j, g);

		// Level
		draw_number(x + 70, y + 8, Game_Player.actor.level, g);

		if (Game_Player.actor.equipements[0] != null) {
			draw_icon(x + 86, y, Game_Player.actor.equipements[0], g);
		}

		if (Game_Player.actor.equipements[1] != null) {
			draw_icon(x + 131 + 5, y, Game_Player.actor.equipements[1], g);
			if (Game_Player.actor.equipements[1].usable)
				draw_text_black(
						x + 146,
						y + 8,
						String.valueOf(Game_Player.actor
								.item_number(Game_Player.actor.equipements[1])),
						g);
			;
		}
	}

}
