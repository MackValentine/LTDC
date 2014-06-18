package mack.items;

import java.io.IOException;
import java.io.Serializable;

import mack.game.Game_Player;
import mack.scene.Scene_Map;

public class Item implements Comparable<Item>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5919978713842237158L;

	public int id;
	public int icon_index_x = 0;
	public int icon_index_y = 0;
	public int force = 0;
	public int endurance = 0;
	public int sagesse = 0;
	public int esprit = 0;
	public int price = 0;
	public int heal = 0;

	public boolean usable = true;

	public int type;

	public String character_name = "Sword";

	public String name = "";

	public String description = "";

	public Item(int i) {
		id = i;
	}

	public int type() {
		return 1;
	}

	public void use_item(Scene_Map m, Game_Player player) throws IOException {
		if (heal > 0) {
			Game_Player.actor.heal(heal);
			if (usable == true)
				Game_Player.actor.remove_item(this, 1);
		}
	}

	public void use_item(Scene_Map map, Game_Player player, int i) throws IOException {
		if (heal > 0) {
			Game_Player.actor.heal(heal);
			if (usable == true)
				Game_Player.actor.remove_item(i);
		}
	}

	public Item set_type(int i) {
		type = i;
		return this;
	}

	public Item set_usable(boolean b) {
		usable = b;
		return this;
	}

	public Item set_sagesse(int i) {
		sagesse = i;
		return this;
	}

	public Item set_esprit(int i) {
		esprit = i;
		return this;
	}

	public Item set_endurance(int i) {
		endurance = i;
		return this;
	}

	public Item set_price(int i) {
		price = i;
		return this;
	}

	public Item set_force(int i) {
		force = i;
		return this;
	}

	public Item set_description(String s) {
		description = s;
		return this;
	}

	public Item set_icon(int i, int j) {
		icon_index_x = i;
		icon_index_y = j;
		return this;
	}

	public Item set_name(String string) {
		name = string;
		return this;
	}

	public Item set_heal(int i) {
		heal = i;
		return this;
	}

	public Item set_character(String name) {
		character_name = name;
		return this;
	}

	public int compareTo(Item it) {
		int i = 99999;
		if (it != null) {
			i = Integer.compare(id, it.id);

			if (i == 0) {
				return new Integer(id).compareTo(new Integer(it.id));
			}
		}

		return i;
	}

	

}
