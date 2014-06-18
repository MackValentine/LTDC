package mack.items;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConvertItemTxt {
	public ConvertItemTxt() {

		String chaine = "";
		String fichier = "/res/items.txt";

		try {
			InputStream is = getClass().getResourceAsStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			boolean b = false;
			while ((ligne = br.readLine()) != null) {
				if (b == false) {
					if (ligne.contains("<#>")) {
						b = true;
					}
					continue;
				}
				String[] s = ligne.split(" # ");
				int id = Integer.valueOf(s[0]);
				String name = s[1];
				String descr = s[2];
				int ii = Integer.valueOf(s[3]);
				int iii = Integer.valueOf(s[4]);
				int fo = Integer.valueOf(s[5]);
				int en = Integer.valueOf(s[6]);
				int sa = Integer.valueOf(s[7]);
				int es = Integer.valueOf(s[8]);
				int p = Integer.valueOf(s[9]);
				int h = Integer.valueOf(s[10]);
				int ts = Integer.valueOf(s[11]);
				String type = s[12].replace(" ", "");

				String aze = "";
				
				if (type.equals("Bow"))
					aze += "add_item(new ItemBow(" + id + ").set_icon(" + ii
							+ ", " + iii + ").set_name(\"" + name
							+ "\").set_price(" + p + ")";
				else if (type.equals("Spear"))
					aze += "add_item(new ItemSpear(" + id + ").set_icon(" + ii
							+ ", " + iii + ").set_name(\"" + name
							+ "\").set_price(" + p + ")";
				else if (type.equals("Sword") || type.equals("Axe")
						|| type.equals("Staff") || type.equals("Poing")
						|| type.equals("Dagger"))
					aze += "add_item(new ItemWeapon(" + id + ").set_icon(" + ii
							+ ", " + iii + ").set_name(\"" + name
							+ "\").set_character(\"" + type + "\").set_price("
							+ p + ")";
				else if (type.equals("Armor"))
					aze += "add_item(new ItemArmor(" + id + ").set_icon(" + ii
							+ ", " + iii + ").set_name(\"" + name
							+ "\").set_price(" + p + ")";
				else if (type.equals("Spell"))
					aze += "add_item(new ItemSpell(" + id + ","
							+ Integer.valueOf(ts) + ").set_icon(" + ii + ", "
							+ iii + ").set_name(\"" + name + "\").set_price("
							+ p + ")";
				else if (type.equals("Food"))
					aze += "add_item(new ItemFood(" + id + ","
							+ Integer.valueOf(ts) + ").set_icon(" + ii + ", "
							+ iii + ").set_name(\"" + name + "\").set_price("
							+ p + ")";
				else if (type.equals("Unusable"))
					aze += "add_item(new ItemUnusable(" + id + ").set_icon("
							+ ii + ", " + iii + ").set_name(\"" + name
							+ "\").set_price(" + p + ")";
				else if (type.equals("Corde"))
					aze += "add_item(new ItemCorde(" + id + ").set_icon(" + ii
							+ ", " + iii + ").set_name(\"" + name
							+ "\").set_price(" + p + ")";
				else
					aze += "add_item(new Item(" + id + ").set_icon(" + ii
							+ ", " + iii + ").set_name(\"" + name
							+ "\").set_price(" + p + ")";
				if (fo > 0)
					aze += ".set_force(" + fo + ")";
				if (en > 0)
					aze += ".set_endurance(" + en + ")";
				if (sa > 0)
					aze += ".set_sagesse(" + sa + ")";
				if (es > 0)
					aze += ".set_esprit(" + es + ")";
				if (h > 0)
					aze += ".set_heal(" + h + ")";

				if (type.equals("Item"))
					aze += ".set_description(\"" + descr + "\")";

				String n = name.replace(" ", "").replace("+", "2")
						.replace("'", "").toLowerCase();
				chaine += "public static final Item " + n + " = " + aze
						+ ");\n";
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		for (int i = 0; i < chaine.length(); ++i) {
			char c = chaine.charAt(i);
			if (c == '\n')
				System.out.println("");
			else
				System.out.print(c);
		}

	}

	public static void main(String[] args) {
		new ConvertItemTxt();
	}
}
