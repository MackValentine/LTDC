package mack.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import mack.game.Game_Player;
import mack.items.Item;
import mack.sprites.SpriteSheet;

public class Window {

	public int x;
	public int y;
	public int width;
	public int height;

	public boolean active = true;

	private SpriteSheet windowskin;

	public Font font;
	private SpriteSheet numbers;
	protected SpriteSheet iconset;
	private SpriteSheet numbers_red;

	public Window(int i, int j, int k, int l) throws IOException, FontFormatException {
		
		InputStream is = this.getClass().getResourceAsStream("/pics/04B_03_.TTF");
		Font uniFont = Font.createFont(Font.TRUETYPE_FONT,is);
		font = uniFont.deriveFont(8f);
		//font = new Font("04b03", 0, 8);

		x = i;
		y = j;
		width = k;
		height = l;
		if (width < 16)
			width = 16;
		if (height < 16)
			height = 16;

		// font = new FontSheet("res/pics/Font/font.png");

		numbers = new SpriteSheet("window/number.png",6,6); 
		numbers_red = new SpriteSheet("window/number_red.png",6,6); 
		windowskin = new SpriteSheet("window/base.png", 8, 8); // Image("pics/window/base.png");
		iconset = new SpriteSheet("window/icons.png",16,16);

	}

	public void draw_back(Graphics g) {

		for (int i = 0; i < (width / 8); ++i) {
			for (int j = 0; j < (height / 8); ++j) {
				g.drawImage(windowskin.getSprite(1, 1), x + (i * 8), y
						+ (j * 8), null);
			}
		}
	}

	public void draw_border(Graphics g) {
		for (int i = 0; i < (width / 8); ++i) {
			for (int j = 0; j < (height / 8); ++j) {
				if (i == 0) {
					if (j == 0) {
						g.drawImage(windowskin.getSprite(0, 0), x, y, null);
						// windowskin.getSprite(0, 0).draw(x, y);
					} else if (j == height / 8 - 1) {
						g.drawImage(windowskin.getSprite(0, 2), x, y + (j * 8),
								null);
						// windowskin.getSprite(0, 2).draw(x, y+(j*8));
					} else {
						g.drawImage(windowskin.getSprite(0, 1), x, y + (j * 8),
								null);
						// windowskin.getSprite(0, 1).draw(x, y+(j*8));
					}
				} else if (i == width / 8 - 1) {
					if (j == 0) {
						g.drawImage(windowskin.getSprite(2, 0),
								x + (width - 8), y, null);
						// windowskin.getSprite(2, 0).draw(x+(width-8), y);
					} else if (j == height / 8 - 1) {
						g.drawImage(windowskin.getSprite(2, 2),
								x + (width - 8), y + (j * 8), null);
						// windowskin.getSprite(2, 2).draw(x+(width-8),
						// y+(j*8));
					} else {
						g.drawImage(windowskin.getSprite(2, 1),
								x + (width - 8), y + (j * 8), null);
						// windowskin.getSprite(2, 1).draw(x+(width-8),
						// y+(j*8));
					}
				} else if (j == 0) {
					g.drawImage(windowskin.getSprite(1, 0), x + (i * 8), y
							+ (j * 8), null);
					// windowskin.getSprite(1, 0).draw(x+(i*8), y+(j*8));
				} else if (j == height / 8 - 1) {
					g.drawImage(windowskin.getSprite(1, 2), x + (i * 8), y
							+ (j * 8), null);
					// windowskin.getSprite(1, 2).draw(x+(i*8), y+(j*8));
				}
			}
		}
	}

	public void draw_text(int i, int j, String text, Graphics g) {

		Color c = g.getColor();
		Color c2 = new Color(255,255,255);
		g.setColor(c2);
		g.setFont(font);
		

		g.drawString(text, i+2, j+6);
		g.setColor(c);
	}
	public void draw_text_black(int i, int j, String text, Graphics g) {

		Color c = g.getColor();
		Color c2 = new Color(0,0,0);
		g.setColor(c2);
		g.setFont(font);
		

		g.drawString(text, i+2, j+6);
		g.setColor(c);
	}

	public void draw_number(int i, int j, int k, Graphics g)
	{
		String s= String.valueOf(k);
		for (int a=0; a<s.length(); ++a)
		{
			String ss = String.valueOf(s.charAt(a));
			int c=Integer.valueOf(ss);
			g.drawImage(numbers.getSprite(c, 0),i,j,null);
			i+=7;
		}
	}
	public void draw_number_red(int i, int j, int k, Graphics g)
	{
		String s= String.valueOf(k);
		for (int a=0; a<s.length(); ++a)
		{
			String ss = String.valueOf(s.charAt(a));
			int c=Integer.valueOf(ss);
			g.drawImage(numbers_red.getSprite(c, 0),i,j,null);
			i+=7;
		}
	}
	public void draw_item(int i, int j, Item item, Graphics g) {
		if (item != null) {
			g.drawImage(
					iconset.getSprite(item.icon_index_x, item.icon_index_y), i,
					j, null);
			draw_text(
					i + 16,
					j + 4,
					item.name
							+ " :"
							+ String.valueOf(Game_Player.actor
									.item_number(item)), g);
		}
	}
	public void draw_icon(int i, int j, Item item, Graphics g) {
		if (item!=null)
		{
			g.drawImage(iconset.getSprite(item.icon_index_x, item.icon_index_y),i,j,null);
		}
	}
	
	public void draw(Graphics g) {
	}

}
