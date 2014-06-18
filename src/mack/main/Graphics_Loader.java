package mack.main;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Graphics_Loader {
	private static final Graphics_Loader AZE = new Graphics_Loader();

	public static Image load(String s) throws IOException {
		Image sprite = null;
		if (Option.texture_pack() != "") {

			String s3 = System.getenv("APPDATA").replace("\\", "/");
			String p = s3 + "/Crepuscule/TexturePacks/" + Option.texture_pack()
					+ s;
			File d = new File(p);
			if (!d.exists()) {
				InputStream is = AZE.getClass().getResourceAsStream("/pics/" + s);
				sprite = ImageIO.read(is);
			} else {
				InputStream is = new FileInputStream(p);
				sprite = ImageIO.read(is);
			}
		} else {
			InputStream is = AZE.getClass().getResourceAsStream("/" + s);

			sprite = ImageIO.read(is);
		}

		return sprite;
	}

	public static Image load_URL(String s) throws IOException {
		Image sprite = null;

		URL imageUrl = new URL(s);
		InputStream in = imageUrl.openStream();
		sprite = ImageIO.read(in);

		return sprite;
	}

	public static boolean exist(String string) {
		// TODO Auto-generated method stub
		return false;
	}
}
