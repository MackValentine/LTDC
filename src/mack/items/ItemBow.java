package mack.items;

import java.io.IOException;

import mack.game.Game_Character;
import mack.game.Game_Player;
import mack.game.Game_Projectile;
import mack.main.RPGPanel;
import mack.scene.Scene_Map;

public class ItemBow extends ItemWeapon {


	/**
	 * 
	 */
	private static final long serialVersionUID = 506650671821582481L;

	public ItemBow(int i) {
		super(i);

	}

	public void use_item(Scene_Map m, Game_Player player) throws IOException {
		Game_Character.movable = true;
		Game_Character event = new Game_Projectile(m, player.direction, player.x,
				player.y, player, 3, "Arrow");
		m.add_new_event(event);
		player.change_character(RPGPanel.map.player().sprite_action, true,15);
		//player.change_character("Guy_atk", true, 15);
	}

	public void use_item(Scene_Map m, Game_Player player, int id)
			throws IOException {
		Game_Character.movable = true;
		Game_Character event = new Game_Projectile(m, player.direction, player.x,
				player.y, player, 3, "Arrow");
		m.add_new_event(event);
		player.change_character(RPGPanel.map.player().sprite_action, true,15);
		//player.change_character("Guy_atk", true, 15);
	}

}
