package mack.tiles;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import mack.sprites.SpriteSheet;
import mack.sprites.SpritesetMap;



public class CopyOfTileDoor extends Tile {

	public boolean open;
	public SpriteSheet tileset;
	
	
	public CopyOfTileDoor(int i) throws IOException {
		super(i,"door");
		open=false;
		
		tileset=new SpriteSheet("tiles/door.png",24,24);
		
		InputStream is = getClass().getResourceAsStream(
				"/pics/tiles/floor_2.png");
		sprite = ImageIO.read(is);
		
	}
	public boolean passable()
	{
		return open;
	}
/**
	public static void update()
	{
		if (open==false)
		if (contact_player())
		{
			if ( RPGPanel.map.player() != null )
			{
				if (RPGPanel.map.player().push_A())
				{
					open=true;
				}
			}
		}
	}
**/
	public Tile set_direction(int i)
	{
		direction=i;
		return this;
	}
	public Tile reload() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(2);
		a.add(4);
		if (SpritesetMap.map!=null)
		{
			int[][] map = SpritesetMap.map.map;
			int max_x=SpritesetMap.map.max_x;
			int max_y=SpritesetMap.map.max_y;
			
	    	if ((x-1>0 && x-1<max_x-1) && (y-1>0 && y-1<max_y-1))
	    	{
		    	if ((a.contains(map[x-1][y])) && a.contains(map[x+1][y]))
		    	{
		    		if (map[x][y+1]==1)
		    		{
		    			direction=0;
		    		}
		    		else 
		    		{
		    			direction=3;
		    		}
		    	}
		    	if ((a.contains(map[x][y-1])) && a.contains(map[x][y+1]))
		    	{
		    		if (map[x+1][y]==1)
		    		{
		    			direction=1;
		    		}
		    		else
		    		{
		    			direction=2;
		    		}
		    	}
	    	}
		}
		return this;
	}
	public int priority()
	{
		return 1;
	}
	public void render(int x, int y, int mx, int my, Graphics g)
	{
		int i = 0;
		int j = 0;
		if (direction==1 || direction==2)
		{
			j=4;
		}
		else
		{
			i=4;
		}
		g.drawImage(sprite,x*16-mx,y*16-my,null);
		if (open==false)
		{
			g.drawImage(tileset.getSprite(0, direction),x*16-mx-i,y*16-my-j,null);
		}
	}
}
