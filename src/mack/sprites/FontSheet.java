package mack.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import mack.main.Graphics_Loader;

public class FontSheet {

	private BufferedImage bigImg;

	public FontSheet(String s) throws IOException {
		bigImg = (BufferedImage) Graphics_Loader.load(s);
	}

	public Image getSprite(int x, int y, int w, int h) {
		return bigImg.getSubimage(x, y, w, h);
	}

}
