package mack.rpg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mack.game.Game_Enemy;
import mack.game.Game_Player;
import mack.items.Item;
import mack.items.Items;
import mack.main.Sound;
import mack.particle.Particle_Damage;

public class RPG_Enemy implements Cloneable {

	public int id;
	public int mHp;
	public int Hp;
	public int force = 0;
	public int endurance = 0;
	public int sagesse = 0;
	public int esprit = 0;
	public int exp = 0;
	public int gold = 0;
	public int gold_m = 0;

	public int type = 0;

	public int invincible;

	public String character_name = "";

	public Random rand;

	public ArrayList<Item> drops;
	public Game_Enemy enemy;
	public boolean use_direction = true;
	public String weapon_name = "Sword";

	public RPG_Enemy(int i) {
		drops = new ArrayList<Item>();
		set_gold(0, 0);
		id = i;
		rand = new Random();
	}

	public Object clone(Game_Enemy e) throws CloneNotSupportedException {
		enemy = e;
		return super.clone();
	}

	public RPG_Enemy set_Hp(int i) {
		mHp = i;
		Hp = mHp;
		return this;
	}

	public RPG_Enemy set_mana(int i) {
		sagesse = i;
		return this;
	}

	public RPG_Enemy set_type(int i) {
		type = i;
		return this;
	}

	public RPG_Enemy set_character(String s) {
		character_name = s;
		return this;
	}

	public RPG_Enemy set_gold(int i, int j) {
		gold = i;
		gold_m = j;
		return this;
	}

	public RPG_Enemy set_force(int i) {
		force = i;
		return this;
	}

	public RPG_Enemy set_endurance(int i) {
		endurance = i;
		return this;
	}

	public RPG_Enemy set_esprit(int i) {
		esprit = i;
		return this;
	}

	public RPG_Enemy set_exp(int i) {
		exp = i;
		return this;
	}

	public RPG_Enemy set_drops(ArrayList<Item> d) {
		drops = d;
		return this;
	}

	public RPG_Enemy set_drop(Item item) {
		drops = new ArrayList<Item>();
		drops.add(item);
		return this;
	}

	public RPG_Enemy set_invincible(int b) {
		invincible = b;
		return this;
	}

	public RPG_Enemy set_drops(Item[] items) {
		drops = new ArrayList<Item>();
		for (int i = 0; i < items.length; ++i) {
			Item item = items[i];
			if (item != null) {
				drops.add(item);
			}
		}
		return this;
	}

	public RPG_Enemy set_drops(int[] id) {
		drops = new ArrayList<Item>();
		for (int i = 0; i < id.length; ++i) {
			Item item = Items.items_list[id[i]];
			if (item != null) {
				drops.add(item);
			}
		}
		return this;
	}

	public void take_damage(int i) throws IOException {
		System.out.println(invincible);

		int d = Math.max(1, i - endurance / 2);

		if (Game_Player.difficulty == 0)
			d *= 2;
		else if (Game_Player.difficulty == 2)
			d /= 2;

		if (d <= 0)
			d = 1;

		System.out.println(d);
		damage(d);
	}

	public void take_magic_damage(int i) throws IOException {
		int d = Math.max(0, i - esprit / 2);

		if (Game_Player.difficulty == 0)
			d *= 2;
		else if (Game_Player.difficulty == 2)
			d /= 2;

		if (d <= 0)
			d = 1;

		System.out.println(d);

		damage(d);
	}

	public void heal(int i) {
		Hp += i;
		if (Hp > mHp) {
			Hp = mHp;
		}
	}

	public void damage(int i) throws IOException {
		if (enemy.invincible == false) {
			Hp -= i;
			if (Hp < 0) {
				Hp = 0;
			}

			RPGSound s = new RPGSound("Sound/Enemy_Hit", 0, 0, 1);
			Sound.sound_play(s);

			Particle_Damage particle = new Particle_Damage(enemy.screen_x,
					enemy.screen_y, enemy.map);
			enemy.add_particle(particle);
		}
	}

	public void set_dead() {
		Game_Player.actor.add_exp(exp);
		if (gold == gold_m)
			Game_Player.actor.gold += gold;
		else
			Game_Player.actor.gold += (rand.nextInt(gold_m - gold) + (gold));
	}

	public RPG_Enemy set_direction(boolean b) {
		use_direction = b;
		return this;
	}

}
