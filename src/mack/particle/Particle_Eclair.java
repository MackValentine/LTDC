package mack.particle;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import mack.scene.Scene_Map;
import mack.sprites.SpriteSheet;



public class Particle_Eclair extends Particle{
	
	public int wait;
	public int max;
	public int y_plus;
	public int x_plus;
	
	public Particle_Eclair(int i, int j, Scene_Map m) throws IOException
	{
		x_plus=i;
		y_plus=j;
		map = m;
		character_spritesheet=new SpriteSheet("particles/eclair.png",16,72);
		character_sprite = character_spritesheet.getSprite(0, 0);
		
		wait=0;
		Random rand = new Random();
		max=20+rand.nextInt(10);
	}
	public void render(Graphics g)
	{
		g.drawImage(character_sprite,x-map.spriteset.display_x, y-map.spriteset.display_y,null);
	}
	
	public void update(int i, int j)
	{
		x=x_plus+i;
		y=y_plus+j-64;
		
		++wait;
		if (wait>max)
		{
			this.exist=false;
		}
		else if (wait>10)
		{
			character_sprite = character_spritesheet.getSprite(1, 0);
		}
	}
}
