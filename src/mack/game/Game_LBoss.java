package mack.game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mack.items.Item;
import mack.items.ItemArmor;
import mack.items.ItemWeapon;
import mack.items.Items;
import mack.main.Graphics_Loader;
import mack.main.RPGPanel;
import mack.rpg.RPG_Enemy;

public class Game_LBoss {

	public BufferedImage sprite_web_attack;
	public BufferedImage sprite_web;

	public ArrayList<String> list;

	public Item item;
	public ItemArmor armor;
	public ItemWeapon weapon;
	public RPG_Enemy enemy;
	public String name;

	public void load_web() throws IOException, CloneNotSupportedException {
		String link = "http://tourducrepusucle.olympe.in/files/boss/Guy.png";
		String link_a = "http://tourducrepusucle.olympe.in/files/boss/Guy_atk.png";

		sprite_web = (BufferedImage) Graphics_Loader.load_URL(link);
		sprite_web_attack = (BufferedImage) Graphics_Loader.load_URL(link_a);

		list = new ArrayList<String>();

		URL u = new URL(
				"http://tourducrepusucle.olympe.in/files/boss/upload/boss.txt");

		// Lire le flux d'entrée (InputStream) lié au fichier
		InputStream is = u.openStream();

		InputStreamReader ipsr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		String[] t = new String[10];
		int i = 0;
		while ((ligne = br.readLine()) != null) {
			System.out.println(ligne);

			String s = "";
			if (ligne.contains("name=")) {
				s = ligne.replace("name=", "");
			}
			if (ligne.contains("hp=")) {
				s = ligne.replace("hp=", "");
			}
			if (ligne.contains("level=")) {
				s = ligne.replace("level=", "");
			}
			if (ligne.contains("gold=")) {
				s = ligne.replace("gold=", "");
			}
			if (ligne.contains("force=")) {
				s = ligne.replace("force=", "");
			}
			if (ligne.contains("endurance=")) {
				s = ligne.replace("endurance=", "");
			}
			if (ligne.contains("sagesse=")) {
				s = ligne.replace("sagesse=", "");
			}
			if (ligne.contains("esprit=")) {
				s = ligne.replace("esprit=", "");
			}

			t[i] = s;
			i++;
		}

		name = t[0];

		RPG_Enemy e = new RPG_Enemy(0);
		e.mHp = Integer.valueOf(t[2]);
		e.Hp = e.mHp;
		e.exp = Integer.valueOf(t[3]);
		e.set_gold(Integer.valueOf(t[4]), Integer.valueOf(t[4]));
		e.force = Integer.valueOf(t[5]);
		e.endurance = Integer.valueOf(t[6]);
		e.sagesse = Integer.valueOf(t[7]);
		e.esprit = Integer.valueOf(t[8]);

		enemy = e;

		u = new URL(
				"http://tourducrepusucle.olympe.in/files/boss/upload/weapon.txt");

		// Lire le flux d'entrée (InputStream) lié au fichier
		is = u.openStream();

		ipsr = new InputStreamReader(is);
		br = new BufferedReader(ipsr);
		int[] t2 = new int[10];
		i = 0;
		while ((ligne = br.readLine()) != null) {
			System.out.println(ligne);
			if (ligne.contains("id="))
				t2[i] = Integer.valueOf(ligne.replace("id=", ""));
			if (ligne.contains("level="))
				t2[i] = Integer.valueOf(ligne.replace("level=", ""));
			i++;
		}

		System.out.println(t2[0]);

		weapon = (ItemWeapon) Items.items_list[t2[0]];
		weapon = (ItemWeapon) weapon.randomize(t2[3]);

		u = new URL(
				"http://tourducrepusucle.olympe.in/files/boss/upload/armor.txt");

		// Lire le flux d'entrée (InputStream) lié au fichier
		is = u.openStream();

		ipsr = new InputStreamReader(is);
		br = new BufferedReader(ipsr);
		t2 = new int[10];
		i = 0;
		while ((ligne = br.readLine()) != null) {
			System.out.println(ligne);
			if (ligne.contains("id="))
				t2[i] = Integer.valueOf(ligne.replace("id=", ""));
			if (ligne.contains("level="))
				t2[i] = Integer.valueOf(ligne.replace("level=", ""));
			i++;
		}

		System.out.println(t2[0]);

		armor = (ItemArmor) Items.items_list[t2[0]];
		armor = (ItemArmor) armor.randomize(t2[3]);

		u = new URL("http://tourducrepusucle.olympe.in/files/boss/item.txt");

		// Lire le flux d'entrée (InputStream) lié au fichier
		is = u.openStream();

		ipsr = new InputStreamReader(is);
		br = new BufferedReader(ipsr);
		t2 = new int[2];
		i = 0;
		while ((ligne = br.readLine()) != null) {
			System.out.println(ligne);
			if (ligne.contains("id="))
				t2[i] = Integer.valueOf(ligne.replace("id=", ""));
			if (ligne.contains("level="))
				t2[i] = Integer.valueOf(ligne.replace("level=", ""));
			i++;
		}
		System.out.println(t2[0]);

		item = Items.items_list[t2[0]];

	}

	public void save_web() throws IOException {
		String u = "http://tourducrepusucle.olympe.in/files/boss/upload.php";
		String b = "file=boss&i=0&n=" + RPGPanel.option.name + "&h="
				+ Game_Player.actor.mHp + "&l=" + Game_Player.actor.level
				+ "&g=" + Game_Player.actor.gold + "&f="
				+ Game_Player.actor.force + "&e=" + Game_Player.actor.endurance
				+ "&s=" + Game_Player.actor.sagesse + "&ee="
				+ Game_Player.actor.esprit;

		String a = "file=armor&i=" + 0 + "&n=" + "" + "&h=" + "0" + "&l=" + 0
				+ "&g=" + "0" + "&f=" + "0" + "&e=" + "0" + "&s=" + "0"
				+ "&ee=0";

		if (Game_Player.actor.equipements[2] != null)
			a = "file=armor&i=" + Game_Player.actor.equipements[2].id + "&n="
					+ "" + "&h=" + "0" + "&l="
					+ ((ItemArmor) Game_Player.actor.equipements[2]).level
					+ "&g=" + "0" + "&f=" + "0" + "&e=" + "0" + "&s=" + "0"
					+ "&ee=0";

		String w = "file=weapon&i=" + 0 + "&n=" + "" + "&h=" + "0" + "&l=" + 0
				+ "&g=" + "0" + "&f=" + "0" + "&e=" + "0" + "&s=" + "0"
				+ "&ee=0";

		if (Game_Player.actor.equipements[0] != null)
			w = "file=weapon&i=" + Game_Player.actor.equipements[0].id + "&n="
					+ "" + "&h=" + "0" + "&l="
					+ ((ItemWeapon) Game_Player.actor.equipements[0]).level
					+ "&g=" + "0" + "&f=" + "0" + "&e=" + "0" + "&s=" + "0"
					+ "&ee=0";

		String i = "file=item&i=" + 0 + "&n=" + "" + "&h=" + "0" + "&l=" + 0
				+ "&g=" + "0" + "&f=" + "0" + "&e=" + "0" + "&s=" + "0"
				+ "&ee=0";
		
		if (Game_Player.actor.equipements[1] != null)
			i = "file=item&i=" + Game_Player.actor.equipements[1].id + "&n="
					+ "" + "&h=" + "0" + "&l=" + "0" + "&g=" + "0" + "&f="
					+ "0" + "&e=" + "0" + "&s=" + "0" + "&ee=0";

		System.out.println("QSD");

		upload_file(u + "?" + b);
		upload_file(u + "?" + a);
		upload_file(u + "?" + i);
		upload_file(u + "?" + w);

	}

	@SuppressWarnings("unused")
	public void upload_file(String s) throws IOException {
		URL url = new URL(s);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream is = conn.getInputStream();
			// do something with the data here
			System.out.println("Reussi");
		} else {
			InputStream err = conn.getErrorStream();
			System.out.println("Raté");
			// err may have useful information.. but could be null see javadocs
			// for more information
		}
	}
}
