package mack.sprites;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class Sprite_Clouds {
	public int x;
	public int y;
	public Image sprite;
	public boolean erase;
	private int wait;

	public Sprite_Clouds(int i) throws IOException {
		x = new Random().nextInt(160) + i;
		y = new Random().nextInt(80);
		int j = new Random().nextInt(4);

		InputStream is;

		if (j == 0)
			is = getClass()
					.getResourceAsStream("/pics/fond/Titles/clouds1.png");
		else if (j == 1)
			is = getClass()
					.getResourceAsStream("/pics/fond/Titles/clouds2.png");
		else if (j == 2)
			is = getClass()
					.getResourceAsStream("/pics/fond/Titles/clouds3.png");
		else
			is = getClass()
					.getResourceAsStream("/pics/fond/Titles/clouds4.png");

		sprite = ImageIO.read(is);
	}

	public void draw(Graphics g) {
		++wait;
		if (wait % 4 == 0)
			x -= 1;
		g.drawImage(sprite, x, y, null);
		if (x < -160)
			erase = true;
	}

}
