package mack.rpg;

import mack.items.Item;
import mack.items.Items;

public class RPG_Skeleton extends RPG_Enemy {

	public RPG_Skeleton(int i, int j) {
		super(i);
		if (j == 2) {
			Item[] items = new Item[] { Items.hachette, Items.glaive,
					Items.brigandine, Items.potion, Items.rondache, Items.forciseur };
			this.set_Hp(25).set_gold(12, 15).set_drops(items)
					.set_character("Skeleton").set_endurance(17).set_force(18)
					.set_exp(9).set_type(1);
		} else {
			Item[] items = new Item[] { Items.hachette, Items.glaive,
					Items.brigandine, Items.potion, Items.rondache,
					Items.forciseur };
			this.set_Hp(8).set_gold(3, 8).set_drops(items)
					.set_character("Skeleton").set_endurance(9).set_force(10)
					.set_exp(5).set_type(1);
		}
	}

}
