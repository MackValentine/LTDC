package mack.game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import mack.main.RPGPanel;
import mack.scene.Scene_Map;


public class Game_Spike extends Game_Character {
	
	public int wait;
	public Game_Spike(Scene_Map m, int i, int j) throws IOException  {
		super(m, "Spike", i, j);
		wait=0;
	}
	
	public boolean trought()
	{
		return true;
	}
	
	public void update() throws IOException
	{
		++wait;
		if (wait>20)
		{
			wait=0;
			++character_frame;
			if (character_frame>=2)
				character_frame=0;
			character_sprite = character_spritesheet.getSprite(character_frame,0);
		}
		if (contact() && Game_Character.movable)
		{
			Random rand = new Random();
			int i = Game_Player.actor.mHp/20;
			Game_Player.actor.damage(rand.nextInt(i)+2);
		}

		for (int i=0;i<RPGPanel.map.characters.length;++i)
		{
			Game_Character event = RPGPanel.map.characters[i];
			if (event!=null && event.exist)
			if (contact(event) && event instanceof Game_Enemy && Game_Character.movable )
			{
				if (in_range(RPGPanel.map.player()))
				{
					Game_Enemy e = (Game_Enemy) event;
					if (e.enemy!=null)
					{
						Random rand = new Random();
						e.enemy.damage(rand.nextInt(10)+1);
					}
				}
			}
		}
	}
	private boolean in_range(Game_Character player) {
		if (Math.abs(player.x-x)<6 && Math.abs(player.y-y)<6)
		{
			return true;
		}
		return false;
	}

	public void render(Graphics g)
	{

		if (RPGPanel.game_map.has_visit[Game_Player.floor][x][y])
			g.drawImage(character_sprite,(screen_x)-map.spriteset.display_x, (screen_y)-map.spriteset.display_y,null);
		
		for (int i=0; i<particles.length;++i)
		{
			if (particles[i]!=null)
			{
				particles[i].render(g);
			}
		}
	}
	
	public boolean contact()
	{
		Game_Player player = (Game_Player) map.player();
		if (x==player.x && y==player.y)
			return true;
		return false;
	}
	public boolean contact(Game_Character event)
	{
		if (x==event.x && y==event.y)
			return true;
		return false;
	}

}
