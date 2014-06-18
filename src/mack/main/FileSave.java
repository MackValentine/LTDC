package mack.main;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import mack.game.Game_Map;
import mack.game.Game_Player;
import mack.scene.Scene_Map;

public class FileSave implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277155473195042055L;

	public static void save() throws IOException {
		String s = System.getenv("APPDATA") + "/Crepuscule/";
		File d = new File(s);
		if (!d.exists()) {
			d.mkdir();
			save();
			return;
		}

		RPGPanel.file_test.player_floor = Game_Player.floor;
		RPGPanel.file_test.player_difficulty = Game_Player.difficulty;
		RPGPanel.file_test.actor = Game_Player.actor;
		RPGPanel.file_test.map = RPGPanel.game_map;
		Save file = RPGPanel.file_test;
		try {
			FileOutputStream fileOut = new FileOutputStream(s
					+ "Crepuscule.sav");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(file);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public static void load() throws IOException, FontFormatException, CloneNotSupportedException {
		String s = System.getenv("APPDATA") + "/Crepuscule/";
		File d = new File(s);
		if (!d.exists()) {
			d.mkdir();
			save();
			return;
		}
		Save f = null;
		try {
			FileInputStream fileIn = new FileInputStream(s + "Crepuscule.sav");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			f = (Save) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Save class not found");
			c.printStackTrace();
			return;
		}

		Game_Map mapp = f.map;
		mapp.create_enemy();
		RPGPanel.game_map = mapp;
		RPGPanel.map = new Scene_Map(RPGPanel.frame);
		Game_Player.actor = f.actor;
		Game_Player.floor = f.player_floor;
		Game_Player.difficulty = f.player_difficulty;

		RPGPanel.map.change_map(1);
	}

	public static void save_option() throws IOException {
		RPGPanel.file_option.sound = RPGPanel.option.volume_sound;
		RPGPanel.file_option.music = RPGPanel.option.volume_music;
		RPGPanel.file_option.Button_A = RPGPanel.option.Button_A;
		RPGPanel.file_option.Button_B = RPGPanel.option.Button_B;
		RPGPanel.file_option.Button_Select = RPGPanel.option.Button_Select;
		RPGPanel.file_option.Button_Start = RPGPanel.option.Button_Start;
		RPGPanel.file_option.SizeScreen = RPGPanel.option.SizeScreen;
		RPGPanel.file_option.HUD = RPGPanel.option.HUD;
		RPGPanel.file_option.texture_pack = RPGPanel.option.texture_pack;

		RPGPanel.file_option.name = RPGPanel.option.name;
		
		Option file = RPGPanel.file_option;

		String s = System.getenv("APPDATA") + "/Crepuscule/";
		File d = new File(s);
		if (!d.exists()) {
			d.mkdir();
			save();
			return;
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(s + "Option.sav");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(file);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public static void load_option() throws IOException {
		Option f = null;
		String s = System.getenv("APPDATA") + "/Crepuscule/";
		File d = new File(s);
		if (!d.exists()) {
			d.mkdir();
			save();
			return;
		}
		try {
			FileInputStream fileIn = new FileInputStream(s + "Option.sav");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			f = (Option) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Option class not found");
			c.printStackTrace();
			return;
		}

		RPGPanel.option.Button_A = f.Button_A;
		RPGPanel.option.Button_B = f.Button_B;
		RPGPanel.option.Button_Select = f.Button_Select;
		RPGPanel.option.Button_Start = f.Button_Start;
		RPGPanel.option.volume_sound = f.sound;
		RPGPanel.option.volume_music = f.music;
		RPGPanel.option.SizeScreen = f.SizeScreen;
		RPGPanel.option.HUD = f.HUD;

		RPGPanel.option.name = f.name;

		RPGPanel.option.texture_pack = f.texture_pack;
		if (f.SizeScreen == 0)
			RPGPanel.option.SizeScreen = 1;
	}

	public static boolean save_exist() throws IOException,
			ParserConfigurationException, SAXException {

		String s = System.getenv("APPDATA") + "/Crepuscule/";
		try {
			FileInputStream fileIn = new FileInputStream(s + "Crepuscule.sav");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			in.close();
			fileIn.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
