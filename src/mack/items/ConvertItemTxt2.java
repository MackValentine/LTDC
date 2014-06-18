package mack.items;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConvertItemTxt2 {
	public ConvertItemTxt2() {

		String chaine = "";
		String fichier = "/res/items2.txt";

		// lecture du fichier texte
		try {
			InputStream is = getClass().getResourceAsStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {

				ligne = ligne.replace("add_item(", "");

				String[] s = ligne.split("#");

				int id = 0;
				String n = "name";
				String d = "";

				int ii = 0;
				int iii = 0;

				int p = 0;
				int fo = 0;
				int en = 0;
				int sa = 0;
				int es = 0;

				String t = "-1";
				int ts = -1;
				int h = 0;

				for (int i = 0; i < s.length; ++i) {
					String ss = s[i];
					if (ss.contains("Item(")) {
						id = Integer.valueOf(ss.replace("new Item(", "")
								.replace(")", ""));
						t = "Item";
					} else if (ss.contains("ItemWeapon")) {
						id = Integer.valueOf(ss.replace("new ItemWeapon(", "")
								.replace(")", ""));
						t = "Sword";
					} else if (ss.contains("ItemBow")) {
						id = Integer.valueOf(ss.replace("new ItemBow(", "")
								.replace(")", ""));
						t = "Bow";
					} else if (ss.contains("ItemSpear(")) {
						id = Integer.valueOf(ss.replace("new ItemSpear(", "")
								.replace(")", ""));
						t = "Spear";
					} else if (ss.contains("ItemArmor")) {
						id = Integer.valueOf(ss.replace("new ItemArmor(", "")
								.replace(")", ""));
						t = "Armor";
					} else if (ss.contains("ItemCorde")) {
						id = Integer.valueOf(ss.replace("new ItemCorde(", "")
								.replace(")", ""));
						t = "Corde";
						n = "Corde";

						ii = 5;

						iii = 0;
						
						d= "Fait sortir du donjon";
					} else if (ss.contains("ItemSpell")) {
						String[] s2 = ss.split(",");
						id = Integer.valueOf(s2[0]
								.replace("new ItemSpell(", ""));
						t = "Spell";
						ts = Integer.valueOf(s2[1].replace(")", "").replace(
								" ", ""));
					} else if (ss.contains("ItemFood")) {
						String[] s2 = ss.split(",");
						id = Integer
								.valueOf(s2[0].replace("new ItemFood(", ""));

						ts = Integer.valueOf(s2[1].replace(")", "").replace(
								" ", ""));
						t = "Food";
					} else if (ss.contains("ItemUnusable")) {
						String[] s2 = ss.split(",");
						id = Integer.valueOf(s2[0].replace("new ItemUnusable(",
								""));
						t = "Unusable";

					}

					if (ss.contains("set_character")) {
						t = ss.replace("set_character(\"", "")
								.replace("\")", "").replace(")", "");
					}

					if (ss.contains("set_icon")) {
						String[] s2 = ss.split(",");
						ii = Integer.valueOf(s2[0].replace("set_icon(", ""));

						iii = Integer.valueOf(s2[1].replace(")", "").replace(
								" ", ""));
					}

					if (ss.contains("set_name")) {
						n = ss.replace("set_name(\"", "").replace("\")", "")
								.replace(")", "");
					}

					if (ss.contains("set_description")) {
						d = ss.replace("set_description(\"", "")
								.replace("\")", "").replace(")", "");
					}

					if (ss.contains("set_heal")) {
						h = Integer.valueOf(ss.replace("set_heal(", "")
								.replace(")", ""));
					}

					if (ss.contains("set_force")) {
						fo = Integer.valueOf(ss.replace("set_force(", "")
								.replace(")", ""));
					}
					if (ss.contains("set_endurance")) {
						en = Integer.valueOf(ss.replace("set_endurance(", "")
								.replace(")", ""));
					}
					if (ss.contains("set_sagesse")) {
						sa = Integer.valueOf(ss.replace("set_sagesse(", "")
								.replace(")", ""));
					}
					if (ss.contains("set_esprit")) {
						es = Integer.valueOf(ss.replace("set_esprit(", "")
								.replace(")", ""));
					}

					if (ss.contains("set_price")) {
						p = Integer.valueOf(ss.replace("set_price(", "")
								.replace(")", ""));
					}

				}

				chaine += id + " # " + n + " # " + d + " # " + ii + " # " + iii
						+ " # " + fo + " # " + en + " # " + sa + " # " + es
						+ " # " + p + " # " + h + " # " + ts + " # " + t + "\n";

				// System.out.println(ligne);
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		int l = chaine.length();
		for (int i = 0; i < l; ++i) {
			char c = chaine.charAt(i);
			if (c == '\n')
				System.out.println("");
			else if (c == '#') {
				System.out.print("#");
			} else
				System.out.print(c);
		}

	}

	public static void main(String[] args) {
		new ConvertItemTxt2();
	}
}
