package mack.game;


import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import mack.main.Sound;
import mack.rpg.Enemy_List;
import mack.rpg.RPGSound;
import mack.rpg.RPG_Enemy;
import mack.scene.Scene_Map;
import mack.sprites.SpritesetMap;
import mack.tiles.Tiles;

public class Game_Torch extends Game_Enemy {
	
	public Game_Torch(int k,Scene_Map m, int i, int j) throws  CloneNotSupportedException, IOException {
		super(k,m, i, j);
		enemy=(RPG_Enemy) Enemy_List.enemy_list[k].clone(this);
		this.change_character(enemy.character_name, false);
		type=enemy.type;
	}


	public void update() throws IOException, FontFormatException 
	{
		super.update();
		
		if (enemy.Hp<=0 && dead==false)
		{
			dead=true;
		}
		if (dead==true)
		{
			++wait_d;
			if (wait_d>300)
			{
				RPGSound s = new RPGSound("Sound/Switch",0,0,1);
				Sound.sound_play(s);
				set_pos_rand();
				wait_d=0;
				enemy.heal(999);
				dead=false;
			}
		}
	}
	
	public boolean set_pos_rand() {
		boolean c=false;
		int n=0;
		while (c==false)
		{
			Random rand = new Random();
			int a=rand.nextInt(SpritesetMap.width());
			int b=rand.nextInt(SpritesetMap.height());
			if (SpritesetMap.passable(a, b) && Tiles.is_floor(SpritesetMap.tiles[a][b]) && event_pos(a,b))
			{
				if (SpritesetMap.map.room_id[a][b]==SpritesetMap.map.rooms_max-1)
				{
					set_pos(a,b);
					c=true;
				}
			}
			++n;
			if (n > 100) {
				c = true;
				return false;
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) throws IOException
	{
		if (dead==false)
			super.render(g);
	}
}
