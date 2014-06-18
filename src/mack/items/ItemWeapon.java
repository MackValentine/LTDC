package mack.items;

import java.io.IOException;
import java.util.Random;

import mack.game.Game_Player;
import mack.game.Game_Weapon;
import mack.main.RPGPanel;
import mack.scene.Scene_Map;

public class ItemWeapon extends Item implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8644407462750872148L;
	public int level;

	public ItemWeapon(int i) {
		super(i);

		create_descr(this);
	}

	public ItemWeapon(int i, int j) {
		super(i);
	}

	public int type() {
		return 0;
	}

	public void use_item(Scene_Map m, Game_Player player) throws IOException {
		Game_Player.weapon = new Game_Weapon(m, character_name, player.x,
				player.y, player.direction, RPGPanel.map.player());
		player.change_character(RPGPanel.map.player().sprite_action, true);

	}

	public void use_item(Scene_Map m, Game_Player player, int id)
			throws IOException {
		Game_Player.weapon = new Game_Weapon(m, character_name, player.x,
				player.y, player.direction, RPGPanel.map.player());
		player.change_character(RPGPanel.map.player().sprite_action, true);
	}

	public Item randomize() throws CloneNotSupportedException {
		Item i = (Item) this.clone();
		int r = new Random().nextInt(30) / 10;
		int j = 0;
		if (r == 0) {
			j = -(new Random().nextInt(30) / 10 + 1);
		} else if (r == 1) {
			j = new Random().nextInt(30) / 10 - 1;
		} else {
			j = new Random().nextInt(30) / 10 + 1;
		}

		level = j;
		if (j > 0)
			i.name += "+" + j;
		else if (j < 0)
			i.name += j;

		if (i.force > 0)
			i.force += j;
		if (i.endurance > 0)
			i.endurance += j;
		if (i.sagesse > 0)
			i.sagesse += j;
		if (i.esprit > 0)
			i.esprit += j;

		create_descr(i);

		return i;
	}

	public Item randomize(int j) throws CloneNotSupportedException {
		Item i = (Item) this.clone();

		level = j;
		if (j > 0)
			i.name += "+" + j;
		else if (j < 0)
			i.name += j;

		if (i.force > 0)
			i.force += j;
		if (i.endurance > 0)
			i.endurance += j;
		if (i.sagesse > 0)
			i.sagesse += j;
		if (i.esprit > 0)
			i.esprit += j;

		create_descr(i);
		return i;
	}

	public void create_descr(Item i) {

		String s = "";
		if (i.force > 0)
			s += "Force+" + i.force + " ";
		if (i.endurance > 0)
			s += "Endurance+" + i.endurance + " ";
		if (i.sagesse > 0)
			s += "Sagesse+" + i.sagesse + " ";
		if (i.esprit > 0)
			s += "Esprit+" + i.esprit + " ";

		i.description = s;
	}
}
