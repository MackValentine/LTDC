package mack.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Map;
import mack.game.Game_Player;
import mack.items.Items;
import mack.rpg.Enemy_List;
import mack.scene.Scene_Base;
import mack.scene.Scene_Map;

import org.xml.sax.SAXException;

public class RPGPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static RPGGame frame;
	public Font font;

	public Scene_Base scene;
	public Items items;
	public Enemy_List enemy_list;
	public FileSave file;
	private int alpha;
	public Scene_Base next_scene;
	private Image backBuffer;
	private Graphics backContext;
	private boolean is_Applet;

	public static Scene_Map map;
	public static Game_Map game_map;
	public static Save file_test;
	public static Option file_option;
	public static Option option;
	public static boolean loading;

	public RPGPanel(RPGGame f, boolean a) throws IOException,
			ParserConfigurationException, SAXException, FontFormatException {
		super();
		is_Applet = a;
		repaint();
		RPGPanel.frame = f;

		option = new Option();
		items = new Items();
		enemy_list = new Enemy_List();

		file = new FileSave();

		file_test = new Save();
		file_test.actor = Game_Player.actor;
		file_option = new Option();

		load_option();

		RPGGame.change_size = true;

	}

	private void load_option() throws IOException {
		String s = System.getenv("APPDATA") + "/Crepuscule/";
		File d = new File(s);
		if (!d.exists()) {
			d.mkdir();
			return;
		}
		File monFichier = new File(s + "Option.sav");
		if (monFichier.exists()) {
			FileSave.load_option();
		}
	}

	public static void create_map() throws IOException, FontFormatException, CloneNotSupportedException {
		RPGPanel.loading = true;
		frame.repaint();
		game_map = new Game_Map();
		map = new Scene_Map(frame);
		RPGPanel.loading = false;
		frame.repaint();
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {

		if (Scene_Base.need_transition == true)
			return;
		if (scene != null)
			scene.update();
	}

	public void paint(Graphics g) {

		if (g != null && scene != null) {
			Color c = Color.black;
			g.setColor(c);
			g.fillRect(0, 0, 160, 160);
			try {
				scene.paint(g);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		if (is_Applet == false) {
			if (next_scene != null) {

				--Scene_Base.wait_transition;
				backBuffer = createImage(160, 160);
				backContext = backBuffer.getGraphics();

				try {
					next_scene.paint(backContext);
				} catch (IOException e) {
					e.printStackTrace();
				}
				int t = Scene_Base.wait_transition;
				if (t < 1)
					t = 1;

				alpha = (alpha * (t - 1) + 255) / t;

				// System.out.println(alpha / 255.0f);
				Graphics2D g2d = (Graphics2D) g;
				AlphaComposite newComposite = AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, alpha / 255.0f);
				g2d.setComposite(newComposite);
				g2d.drawImage(backBuffer, 0, 0, null);
				newComposite = AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1f);
				g2d.setComposite(newComposite);

				if (Scene_Base.wait_transition <= 0) {
					scene = next_scene;
					backContext.clearRect(0, 0, 160, 160);
					backContext.dispose();
					next_scene = null;
					Scene_Base.need_transition = false;
					alpha = 0;
				}

			}
		} else {
			if (g != null && next_scene != null){
				try {
					next_scene.paint(g);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				--Scene_Base.wait_transition;
				
				if (Scene_Base.wait_transition <= 0) {
					scene = next_scene;
					next_scene = null;
					Scene_Base.need_transition = false;
					alpha = 0;
				}
				
			}
		}
		
		if (RPGPanel.loading) {

			Image img = null;
			try {
				img = Graphics_Loader.load("fond/Loading.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (img != null)
				g.drawImage(img, 0, 0, (int) RPGPanel.option.SizeScreen * 160,
						(int) RPGPanel.option.SizeScreen * 160, null);
		}

	}

	public void load(RPGGame f) throws IOException,
			ParserConfigurationException, SAXException {

		frame = f;

	}

}
