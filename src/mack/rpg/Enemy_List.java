package mack.rpg;

import mack.items.Item;
import mack.items.Items;

public class Enemy_List {

	public static RPG_Enemy[] enemy_list;

	public Enemy_List() {
		enemy_list = new RPG_Enemy[999];
		add_enemy(new RPG_Enemy(1).set_Hp(1).set_gold(1, 5)
				.set_character("Blob").set_force(8).set_exp(3)
				.set_drops(new Item[] { Items.potion }).set_direction(false));
		add_enemy(new RPG_Skeleton(2,1));
		add_enemy(new RPG_Enemy(3).set_type(1).set_Hp(10).set_gold(3, 8)
				.set_exp(5).set_character("Snake").set_endurance(11)
				.set_force(13).set_direction(false));
		add_enemy(new RPG_Mage(4,1));
		add_enemy(new RPG_Enemy(5).set_type(1).set_Hp(16).set_gold(1, 12)
				.set_character("Fungus").set_endurance(6).set_esprit(5)
				.set_force(16).set_exp(7)
				.set_drops(new Item[] { Items.togemagus }).set_direction(false));
		add_enemy(new RPG_Enemy(6)
				.set_type(1)
				.set_gold(8, 10)
				.set_character("Kobold")
				.set_force(21)
				.set_endurance(16)
				.set_Hp(22)
				.set_exp(9)
				.set_drops(
						new Item[] { Items.fendeuse, Items.bardiche,
								Items.cuirasse }));

		add_enemy(new RPG_Enemy(10).set_character("Boss1").set_force(30)
				.set_endurance(16).set_mana(18).set_Hp(100).set_type(10)//HP=100
				.set_gold(1, 2).set_invincible(1));
		add_enemy(new RPG_Enemy(11).set_character("Blob").set_Hp(10)
				.set_gold(1, 2).set_type(11).set_direction(false));
		
		
		
		add_enemy(new RPG_Enemy(21).set_Hp(8).set_gold(3, 8)
				.set_character("Blob2").set_force(8).set_exp(5)
				.set_drops(new Item[] { Items.potion }).set_direction(false));
		add_enemy(new RPG_Skeleton(22,2));
		add_enemy(new RPG_Enemy(23).set_type(1).set_Hp(25).set_gold(8, 14)
				.set_exp(9).set_character("Snake2").set_endurance(21)
				.set_force(23).set_direction(false));
		add_enemy(new RPG_Mage(24,2));
		add_enemy(new RPG_Enemy(25).set_type(1).set_Hp(26).set_gold(1, 20)
				.set_character("Ghost").set_endurance(11).set_esprit(9)
				.set_force(20).set_exp(15)
				.set_drops(new Item[] { Items.togemagus }).set_direction(false));
		add_enemy(new RPG_Enemy(26)
				.set_type(1)
				.set_gold(16, 17)
				.set_character("Orc")
				.set_force(40)
				.set_endurance(35)
				.set_Hp(50)
				.set_exp(20)
				.set_drops(
						new Item[] { Items.fendeuse, Items.bardiche,
								Items.cuirasse }));
		// add_enemy(new RPG_Mage(5));
	}

	public void add_enemy(RPG_Enemy enemy) {
		enemy_list[enemy.id] = enemy;
	}
}
