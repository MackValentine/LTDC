package mack.particle;

import java.awt.Graphics;
import java.awt.Image;

import mack.scene.Scene_Map;
import mack.sprites.SpriteSheet;


public class Particle {
	
	public SpriteSheet character_spritesheet;
	public Image character_sprite;
	public String character_name;
	
	public int x;
	public int y;
	public Scene_Map map;
	
	public boolean exist=true;
	
	public Particle()
	{
		
	}
	public void render(Graphics g)
	{
		
	}
	
	public void update(int i, int j)
	{
		x=i;
		y=j;
	}
}
