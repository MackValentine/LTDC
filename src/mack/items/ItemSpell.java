package mack.items;

import java.io.IOException;
import java.util.Random;

import mack.game.Game_Character;
import mack.game.Game_Enemy;
import mack.game.Game_Fireball;
import mack.game.Game_Player;
import mack.particle.Particle_Eclair;
import mack.scene.Scene_Map;

public class ItemSpell extends Item{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8757972733472101428L;
	
	public ItemSpell(int i, int j) {
		super(i);
		type=j;
	}
	public int type_s()
	{
		return type;
	}
	

	public void use_item(Scene_Map m,Game_Player player) throws IOException
	{
		create_spell(m,player,0);
		Game_Player.actor.remove_item(this, 1);
	}
	public void use_item(Scene_Map m,Game_Player player, int id) throws IOException
	{
		create_spell(m,player,id);
		Game_Player.actor.remove_item(id);
	}
	
	private void create_spell(Scene_Map m,Game_Player player, int id) throws IOException {
		Game_Character.movable=true;
		if (type_s()==0)
		{
			Game_Character event = new Game_Fireball(m,player.direction, player.x, player.y, player);
			m.add_new_event(event);
		}
		else if (type_s()==1)
		{
			for (int j=0;j<5;++j)
			{
				Random rand = new Random();
				int r = 64;
				int x1 = rand.nextInt(r)-r/2;
				r = rand.nextInt(64)+1;
				int x2 = rand.nextInt(r)-r/2;
				Particle_Eclair particle = new Particle_Eclair(x1, +x2, m);
				player.add_particle(particle);
			}
			for (int i=0; i<m.characters.length; i++) {
		 		   if (m.characters[i]!=null && m.player().event_in_range(m.characters[i], 4))
		 		   {
		 			   if (m.characters[i] instanceof Game_Enemy)
		 			   {
		 				   Game_Enemy e = (Game_Enemy) m.characters[i];
		 				   if (e.enemy!=null)
		 				   {
		 					   e.enemy.take_magic_damage(Game_Player.actor.sagesse);
		 				   }
		 			   }
		 		   }
			}
		}
		else if (type_s()==2)
		{
			int i = Game_Player.actor.mHp/4;
			Game_Player.actor.heal(i);
		}
	}
	

}
