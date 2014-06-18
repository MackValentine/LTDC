package mack.game;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mack.items.Item;
import mack.items.ItemArmor;
import mack.items.ItemWeapon;
import mack.items.Items;
import mack.main.Option;
import mack.main.RPGPanel;
import mack.scene.Scene_Loot;
import mack.scene.Scene_Map;

public class Game_Chest extends Game_Character {

	public boolean open;
	private Item[][] floor_drops;

	public Game_Chest(Scene_Map m, int i, int j) throws IOException {
		super(m, "Chest", i, j);
		open = false;

		floor_drops = new Item[Game_Map.m_floor][0];
		floor_drops[0] = new Item[] { Items.corde, Items.potion, Items.dague,
				Items.canne, Items.arccourt, Items.gantdecuir, Items.glaive,
				Items.veste, Items.brigandine, Items.corde, Items.pomme,
				Items.couvercle, Items.forciseur, Items.soin };

		floor_drops[1] = new Item[] { Items.daguemagik, Items.hachette, Items.flamberge,
				Items.sceptre, Items.gantdacier, Items.bardiche,
				Items.cuirasse, Items.fireball, Items.rondache, Items.pique };

		floor_drops[2] = new Item[] { Items.masse, Items.arsenal, Items.sagittaire };

		floor_drops[3] = new Item[] { Items.ecu, Items.assassine, Items.tomahawk,
				Items.chasseur, Items.cottedacier, Items.potion2, Items.foudre };

		floor_drops[4] = new Item[] { Items.masse, Items.kukri, Items.sanguine };

		floor_drops[5] = new Item[] { Items.fendeuse, Items.organix, Items.togemagus,
				Items.pavois };

		floor_drops[6] = new Item[] { Items.frapeterre, Items.chasseur, Items.hallebarde };

		floor_drops[7] = new Item[] { Items.guardian, Items.plaque, Items.sevensky,
				Items.obelisque, Items.monarch, Items.potionx, Items.templier };

		floor_drops[8] = new Item[] { Items.peytral, Items.francisque, Items.mandragore,
				Items.ragnarock, Items.angelarme };

		floor_drops[9] = new Item[] {};

	}

	public boolean actionnable() {
		if (open) {
			return false;
		} else {
			return true;
		}
	}

	public void update() throws IOException, FontFormatException {
		// super.update();
		if (open == false) {
			if (contact_player() && Option.triggerA()) {
				open = true;
				ArrayList<Item> drops = new ArrayList<Item>();
				Random rand = new Random();
				int r = rand.nextInt(3) + 1;
				for (int a = 0; a < r; ++a) {
					boolean s = false;
					while (s == false) {
						int i = rand.nextInt(Items.items_list.length);
						if (Items.items_list[i] != null) {
							if (contains(floor_drops[Game_Player.floor], i))
								if (i > 0 && i < 999) {
									// Game_Player.actor.add_item(Items.items_list[i],n);
									Item item = Items.items_list[i];
									if (item instanceof ItemWeapon) {
										try {
											item = ((ItemWeapon) item)
													.randomize();
										} catch (CloneNotSupportedException e) {
											e.printStackTrace();
										}
									} else if (item instanceof ItemArmor) {
										try {
											item = ((ItemArmor) item)
													.randomize();
										} catch (CloneNotSupportedException e) {
											e.printStackTrace();
										}
									}
									drops.add(item);
									s = true;
								}
						}
					}
				}
				map.frame.call_scene(new Scene_Loot(RPGPanel.map.frame, drops));
				set_dead();
			}
		}
	}

	private boolean contains(Item[] is, int i) {
		for (int j = 0; j < is.length; ++j) {
			if (is[j] == Items.items_list[i])
				return true;
		}
		return false;
	}

}
