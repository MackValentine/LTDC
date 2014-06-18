package mack.rpg;

import mack.items.Item;
import mack.items.Items;

public class RPG_Mage extends RPG_Enemy {

	public RPG_Mage(int i, int j) {
		super(i);
		Item[] items = new Item[] { Items.potion, Items.soin, Items.fireball,
				Items.canne, Items.sceptre, Items.soierie };
		if (j == 2) {
			this.set_Hp(24).set_gold(9, 16).set_drops(items)
					.set_character("Mage").set_force(1).set_esprit(10)
					.set_endurance(7).set_exp(9).set_type(2);
			this.set_mana(9);
		} else {
			this.set_Hp(12).set_gold(5, 9).set_drops(items)
					.set_character("Mage").set_force(1).set_esprit(6)
					.set_endurance(4).set_exp(6).set_type(2);
			this.set_mana(6);
		}
	}

}
