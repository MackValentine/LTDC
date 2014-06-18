package mack.tiles;

import java.awt.Graphics;
import java.io.IOException;
import mack.main.Graphics_Loader;
import mack.sprites.SpriteSheet;



public class TileDoor extends Tile {

	public boolean open;
	public SpriteSheet tileset;
	
	
	public TileDoor(int i,String s) throws IOException {
		super(i,"floor");
		open=false;
	}
	
	
	public void render(int x1, int y1, int mx, int my, Graphics g) {
		if (this.visible() == true) {
			if (open==false){
				try {
					sprite = Graphics_Loader.load("tiles/door.png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			g.drawImage(sprite, x1 * 16 - mx, y1 * 16 - my, null);
		}
	}
	
	public boolean passable()
	{
		return true;
	}

}
