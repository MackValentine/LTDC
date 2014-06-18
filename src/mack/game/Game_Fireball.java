package mack.game;

import java.io.IOException;

import mack.scene.Scene_Map;


public class Game_Fireball extends Game_Character {

	public int move_tick;
	public Game_Character owner;
	public Game_Fireball(Scene_Map m, int k, int i, int j, Game_Character o) throws IOException{
		super(m, "Fireball", i, j);
		move_tick=0;
		direction=k;
		owner = o;
	}
	public boolean trought()
	{
		return true;
	}
	public void refresh_sprite()
	{
		int i=0;
		character_sprite = character_spritesheet.getSprite(character_frame,i);
	}
	
	public void update() throws IOException
	{
		if (!this.move)
		{
			this.move_direction(direction);
			++move_tick;
			if (owner instanceof Game_Player)
			{
				for (int i=0;i<map.characters.length;++i)
				{
					Game_Character event=map.characters[i];
					if (collide(event))
					{
						if (event instanceof Game_Enemy)
						{
							Game_Enemy e=(Game_Enemy) event;
							e.enemy.take_magic_damage(Game_Player.actor.total_sagesse());
						}
					}
				}
			}
			else
			{
				Game_Character event=map.player();
				if (collide(event))
				{
					if (event instanceof Game_Player)
					{
						Game_Enemy o = (Game_Enemy) owner;
						if (o.enemy != null)
							Game_Player.actor.take_magic_damage(o.enemy.sagesse);
					}
				}
			}
		}
		
		
		if (move_tick>=4)
		{
			this.set_dead();
		}
	}
}
