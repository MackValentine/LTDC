package mack.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import mack.main.Graphics_Loader;

public class SpriteSheet {

	public BufferedImage[][] sprites;
	private int width;
	private int height;
	private int rows;
	private int cols;

	public SpriteSheet(String s, int w, int h) throws IOException {
		BufferedImage bigImg = (BufferedImage) Graphics_Loader.load(s);// ImageIO.read(new File(s));
		// The above line throws an checked IOException which must be caught.
		int r = bigImg.getWidth() / w;
		int c = bigImg.getHeight() / h;
		width = w;
		height = h;
		rows = r;
		cols = c;

		sprites = new BufferedImage[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[i][j] = bigImg.getSubimage(i * width, j * height,
						width, height);
			}
		}
	}

	public SpriteSheet() {
	}

	public SpriteSheet(BufferedImage bigImg, int w, int h) {
		// The above line throws an checked IOException which must be caught.
		int r = bigImg.getWidth() / w;
		int c = bigImg.getHeight() / h;
		width = w;
		height = h;
		rows = r;
		cols = c;

		sprites = new BufferedImage[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[i][j] = bigImg.getSubimage(i * width, j * height,
						width, height);
			}
		}
	}

	public void load_character(String s) throws IOException {
		BufferedImage bigImg = (BufferedImage) Graphics_Loader.load(s);
		int w = bigImg.getWidth() / 4;
		int h = bigImg.getHeight() / 4;
		int r = bigImg.getWidth() / w;
		int c = bigImg.getHeight() / h;
		width = w;
		height = h;
		rows = r;
		cols = c;

		sprites = new BufferedImage[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[i][j] = bigImg.getSubimage(i * width, j * height,
						width, height);
			}
		}
	}

	public int getSWidth() {
		return width;
	}

	public int getSHeight() {
		return height;
	}

	public int getWidth() {
		return rows;
	}

	public int getHeight() {
		return cols;
	}

	public Image getSprite(int i, int j) {
		return sprites[i][j];
	}

	public Image getSprite(int k) {
		k = k - 384;
		if (k < 0)
			return null;
		int i = k % (rows);
		int j = k / (rows);

		return sprites[i][j];
	}

	public Image getSpriteAnim(int k) {
		k = k - 1;
		if (k < 0)
			return null;

		int i = k % (5);
		int j = k / (5);

		if (i >= rows || j >= cols)
			return null;

		return sprites[i][j];
	}

}
