package mack.game;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import mack.items.Item;
import mack.items.Items;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.particle.Particle_Damage;
import mack.rpg.RPGSound;

public class Game_Actor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 344745034814133962L;
	public int mHp;
	public int Hp;
	public int level;
	public int exp;
	public int need_up;
	public int gold;

	public int force;
	public int endurance;
	public int sagesse;
	public int esprit;
	public int lv_up_pts;

	public int hour;
	public int days;

	public Item[] items;
	public int[] items_number;

	public Item[] equipements;

	public Game_Actor() {
		reload();
	}

	public void equip(Item item, int i) {
		equipements[i] = item;

	}

	public int total_force() {
		int i = force;
		if (equipements[0] != null) {
			i += equipements[0].force;
		}
		if (equipements[1] != null) {
			i += equipements[1].force;
		}
		if (equipements[2] != null) {
			i += equipements[2].force;
		}
		return i;
	}

	public int total_esprit() {
		int i = esprit;
		if (equipements[0] != null) {
			i += equipements[0].esprit;
		}
		if (equipements[1] != null) {
			i += equipements[1].esprit;
		}
		if (equipements[2] != null) {
			i += equipements[2].esprit;
		}

		return i;
	}

	public int total_endurance() {
		int i = endurance;
		if (equipements[0] != null) {
			i += equipements[0].endurance;
		}
		if (equipements[1] != null) {
			i += equipements[1].endurance;
		}
		if (equipements[2] != null) {
			i += equipements[2].endurance;
		}

		return i;
	}

	public int total_sagesse() {
		int i = sagesse;
		if (equipements[0] != null) {
			i += equipements[0].sagesse;
		}
		if (equipements[1] != null) {
			i += equipements[1].sagesse;
		}
		if (equipements[2] != null) {
			i += equipements[2].sagesse;
		}
		return i;
	}

	public boolean add_item(Item item, int i) {
		boolean b = true;
		for (int j = 0; j < i; ++j) {
			int k = new_id();
			if (k < max_item() && k >= 0) {
				items[k] = item;
			} else
				b = false;
		}
		return b;
	}

	public int max_item() {
		return 9 * 6;
	}

	public int max_items() {
		int j = 0;
		for (int i = 0; i < max_item(); ++i) {
			if (items[i] == null)
				j++;
		}
		return j;
	}

	public void remove_item(Item item, int i) {
		// items.add(item);

		if (contains(items, item)) {
			int k = item_id(item);
			items[k] = null;
		} else {
			for (int j = 0; j < 3; ++j) {
				if (item.id == equipements[j].id)
					equipements[j] = null;
			}
		}
	}

	public void remove_item(int id) {
		// items.add(item);
		items[id] = null;
	}

	private int item_id(Item item) {
		int j = 0;
		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null)
				if (items[i].id == item.id)
					j = i;
		}
		return j;
	}

	private int new_id() {
		int j = -1;
		for (int i = items.length - 1; i >= 0; --i) {
			if (items[i] == null)
				j = i;
		}

		return j;
	}

	private boolean contains(Item[] is, Item item) {
		boolean b = false;
		for (int i = 0; i < is.length; ++i) {
			if (is[i] != null)
				if (is[i].id == item.id) {
					b = true;
				}
		}
		return b;
	}

	public void reload_items() {
		/**
		 * items = new ArrayList<Item>(); for (int i = 0; i <
		 * items_number.length; ++i) { if (items_number[i] > 0)
		 * items.add(Items.items_list[i]); }
		 **/

		for (int i = 0; i < items.length; ++i) {
			if (items[i] != null)
				items[i] = Items.items_list[items[i].id];
		}

		for (int j = 0; j < equipements.length; ++j) {
			if (equipements[j] != null) {
				equipements[j] = Items.items_list[equipements[j].id];
			}
		}

	}

	public void heal(int i) {
		Hp += i;
		if (Hp > mHp) {
			Hp = mHp;
		}
	}

	public void damage(int i) throws IOException {
		Hp -= i;
		if (Hp < 0) {
			Hp = 0;
		}
		RPGSound s = new RPGSound("Sound/Player_Hit", 0, 0, 1);
		Sound.sound_play(s);
		// new Sound("Sound/Player_Hit.wav").play(1.0F,Crepusucle.volume_sound);
		Game_Player player = RPGPanel.map.player();
		Particle_Damage particle = new Particle_Damage(player.screen_x,
				player.screen_y, player.map);
		player.add_particle(particle);
	}

	public void add_exp(int i) {
		exp += i;
		if (exp >= need_up) {
			exp = 0;
			add_level();
		}
	}

	public void add_level() {
		level += 1;
		if (level > 99) {
			level = 99;
			return;
		}

		mHp += 1;

		++lv_up_pts;

		Random rand = new Random();
		need_up = 15 + level * 5 + rand.nextInt(9);
	}

	public int item_number(Item item) {
		int i = 0;
		for (int j = 0; j < items.length; ++j) {
			if (items[j] != null)
				if (item.id == items[j].id)
					i++;
		}

		for (int j = 0; j < 3; ++j) {
			if (equipements[j] != null)
				if (item.id == equipements[j].id)
					i++;
		}

		return i;
	}

	public void take_damage(int i) throws IOException {
		int d = Math.max(1, i - total_endurance() / 2);
		if (Game_Player.difficulty == 0)
			d /= 2;
		else if (Game_Player.difficulty == 2)
			d *= 2;

		if (d <= 0)
			d = 1;

		System.out.println(d);
		damage(d);
	}

	public void take_magic_damage(int i) throws IOException {
		int d = Math.max(0, i - total_esprit() / 2);

		if (Game_Player.difficulty == 0)
			d /= 2;
		else if (Game_Player.difficulty == 2)
			d *= 2;

		if (d <= 0)
			d = 1;

		System.out.println(d);
		damage(d);
	}

	public void reload() {
		mHp = 48;//48
		Hp = mHp;
		level = 1;
		exp = 0;
		need_up = 15;
		gold = 10;
		force = 0;
		endurance = 0;
		esprit = 0;
		sagesse = 0;

		items_number = new int[Items.items_list.length];
		items = new Item[9 * 6];
		// items.add(null);

		add_item(Items.corde, 1);

		add_item(Items.pomme, 1);

		if (Game_Player.debug_mode)
			for (int i = 0; i < Items.items_list.length; ++i) {
				// if (Items.items_list[i] != null)
				// add_item(Items.items_list[i], 99);
			}

		equipements = new Item[3];
	}

	public void set_class(int index) {
		reload();
		Item weapon;
		Item armor;
		Item objet;
		switch (index) {
		case 0:
			force = 2;
			endurance = 2;
			weapon = Items.arccourt;
			equipements[0] = weapon;
			armor = Items.veste;
			equipements[2] = armor;

			objet = Items.couvercle;
			equipements[1] = objet;

			objet = Items.potion;
			add_item(objet, 3);

			break;
		case 1:
			endurance = 4;
			weapon = Items.glaive;
			equipements[0] = weapon;

			armor = Items.brigandine;
			equipements[2] = armor;

			objet = Items.rondache;
			equipements[1] = objet;

			objet = Items.potion;
			add_item(objet, 2);
			break;
		case 2:
			force = 4;
			weapon = Items.hachette;
			equipements[0] = weapon;

			objet = Items.forciseur;
			equipements[1] = objet;
			break;
		case 3:
			force = 2;
			sagesse = 2;

			weapon = Items.dague;
			equipements[0] = weapon;

			objet = Items.potion;
			equipements[1] = objet;

			objet = Items.fireball;
			add_item(objet, 2);

			armor = Items.soierie;
			equipements[2] = armor;
			break;
		case 4:
			weapon = Items.canne;
			equipements[0] = weapon;

			objet = Items.fireball;
			add_item(objet, 5);

			objet = Items.potion;
			equipements[1] = objet;

			armor = Items.soierie;
			equipements[2] = armor;

			sagesse = 3;
			esprit = 3;
			break;
		}
		

	}

	public void reset_stat() {
		force = 0;
		sagesse = 0;
		endurance = 0;
		esprit = 0;
	}

}
