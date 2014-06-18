package mack.game;

import java.io.IOException;

import mack.items.Item;
import mack.items.ItemArmor;
import mack.items.ItemBow;
import mack.items.ItemSpear;
import mack.items.ItemWeapon;
import mack.items.Items;
import mack.scene.Scene_Map;

public class Game_BossWeb extends Game_Enemy {

	private ItemWeapon weapon_i;
	private ItemArmor armor;
	private Item item;
	

	public Game_BossWeb(int k, Scene_Map m, int i, int j)
			throws CloneNotSupportedException, IOException {
		super(k, m, i, j);
		Game_Player.l.load_web();
		enemy = Game_Player.l.enemy;
		enemy.enemy = this;

		armor = Game_Player.l.armor;
		item = Game_Player.l.item;
		weapon_i = Game_Player.l.weapon;

		if (item.id == Items.fireball.id) {
			type = 2;
		} else if (weapon_i instanceof ItemBow) {
			type = 3;
		} else if (weapon_i instanceof ItemSpear) {
			type = 4;
		} else {
			type = 1;
		}
		
		enemy.force += armor.force;
		enemy.force += item.force;
		enemy.force += weapon_i.force;
		
		enemy.endurance += armor.endurance;
		enemy.endurance += item.endurance;
		enemy.endurance += weapon_i.endurance;
		
		enemy.sagesse += armor.sagesse;
		enemy.sagesse += item.sagesse;
		enemy.sagesse += weapon_i.sagesse;
		
		enemy.esprit += armor.esprit;
		enemy.esprit += item.esprit;
		enemy.esprit += weapon_i.esprit;
		
		enemy.weapon_name = Items.items_list[weapon_i.id].character_name;
		
		this.sprite_attack = Game_Player.l.sprite_web_attack;
		this.sprite = Game_Player.l.sprite_web;
		change_character(Game_Player.l.sprite_web, false);

	}

	public void set_dead() throws IOException {
		Game_Player.l.save_web();
		super.set_dead();
	}

}
