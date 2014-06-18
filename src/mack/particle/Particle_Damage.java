package mack.particle;

import java.awt.Graphics;
import java.io.IOException;

import mack.game.Game_Player;
import mack.main.RPGPanel;
import mack.scene.Scene_Map;
import mack.sprites.SpriteSheet;

public class Particle_Damage extends Particle {

	public int wait;
	public int x;
	public int y;

	public Particle_Damage(int i, int j, Scene_Map m) throws IOException {
		x = i;
		y = j;
		map = m;
		character_spritesheet = new SpriteSheet("particles/damage.png",
				16, 16);
		character_sprite = character_spritesheet.getSprite(0, 0);

		wait = 0;
	}

	public void render(Graphics g) {
		if (RPGPanel.game_map.has_visit[Game_Player.floor][x/16][y/16] == true) {
			g.drawImage(character_sprite, x - map.spriteset.display_x, y
					- map.spriteset.display_y, null);
		}

	}

	public void update(int i, int j) {
		x = i;
		y = j;

		++wait;
		if (wait > 20) {
			this.exist = false;
		} else if (wait > 10) {
			character_sprite = character_spritesheet.getSprite(1, 0);
		}
	}
}
