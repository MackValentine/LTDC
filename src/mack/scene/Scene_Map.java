package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import mack.sprites.SpritesetMap;
import mack.tiles.TileTorch;
import mack.tiles.Tiles;
import mack.window.Window_HUD;
import mack.game.Game_Boss;
import mack.game.Game_BossWeb;
import mack.game.Game_Character;
import mack.game.Game_Chest;
import mack.game.Game_Enemy;
import mack.game.Game_Map;
import mack.game.Game_Player;
import mack.game.Game_Spike;
import mack.main.Option;
import mack.main.RPGGame;

public class Scene_Map extends Scene_Base {

	public Tiles tiles;
	public SpritesetMap spriteset;
	public Window_HUD HUD;
	public Game_Character[] characters;

	public Scene_Map(RPGGame f) throws IOException, FontFormatException, CloneNotSupportedException {
		super(f);

		HUD = new Window_HUD(0, 144, 0, 0);
		characters = new Game_Character[999];

		characters[998] = new Game_Player(this, "Guy", 0, 0);
		change_map(1);

	}
	
	public void enter_scene() throws IOException {
		tiles = new Tiles();
		spriteset = new SpritesetMap();

		this.spriteset.load_map();

		TileTorch.set_map(SpritesetMap.map);
	}

	public Game_Player player() {
		return (Game_Player) characters[998];
	}

	public void add_new_event(Game_Character event) {
		int e = characters.length;
		for (int z = 0; z < e; ++z) {
			if (characters[z] == null) {
				characters[z] = event;
				break;
			}
		}
	}

	public void paint(Graphics g) throws IOException {
		spriteset.render(g);
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] != null) {
				if (characters[i].exist())
					characters[i].render(g);
			}
		}
		HUD.draw(g);
	}

	public void update() throws IOException, ParserConfigurationException,
			SAXException, FontFormatException {
		super.update();
		spriteset.update();
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] != null) {
				if (characters[i].exist())
					characters[i].update();
			}
		}
		TileTorch.update();
		if (Game_Player.actor.Hp <= 0) {
			frame.call_scene(new Scene_Gameover(frame));
		}

		if (Option.triggerSelect()) {
			frame.call_scene(new Scene_MenuMap(frame));
		} else if (Option.triggerStart()) {
			frame.call_scene(new Scene_Menu(frame));
		}
	}

	public void change_map(int j) throws IOException {

		if (Game_Player.floor <= 4)
			Game_Map.etage_name = "landing1";
		else
			Game_Map.etage_name = "landing2";

		tiles = new Tiles();
		spriteset = new SpritesetMap();

		this.spriteset.load_map();

		TileTorch.set_map(SpritesetMap.map);

		player().seton_stairs(j);

		player().change_dir(8);

		Random rand = new Random();
		for (int i = 0; i < 998; ++i) {
			characters[i] = null;
		}

		int[] list = Game_Map.enemy_list[Game_Player.floor];

		int y = rand.nextInt(5) + 4 * ((Game_Player.floor));
		if (y < 0)
			y = 0;

		if (Game_Player.difficulty == 0)
			y /= 2;
		else if (Game_Player.difficulty == 2)
			y *= 2;

		if (!Game_Map.boss(Game_Player.floor)) {
			for (int z = 0; z < y; ++z) {
				characters[z] = new Game_Spike(this, 0, 0);
				characters[z].set_pos_rand();
			}
		}

		int e = rand.nextInt(10) + 50;

		int i = 0;
		if (Game_Player.difficulty == 0)
			i = -e / 4;
		else if (Game_Player.difficulty == 2)
			i = e / 4;

		e += i;

		if (Game_Player.floor == 4) {
			
			Game_Map.boss_dead = new boolean[] {false,false};
			if (Game_Map.boss_dead[0] == false) {
				try {
					
					/*AZE*/
					
					characters[y] = new Game_Boss(10, this, 0, 0);
					//characters[y] = new Game_BossWeb(0, this, 0, 0);
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				characters[y].set_pos_in_room(2);
			}
		} else if (Game_Player.floor == 9) {
			try {
				//characters[y] = new Game_Boss(10, this, 0, 0);
				characters[y] = new Game_BossWeb(0, this, 0, 0);
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
			characters[y].set_pos_in_room(2);
		} else {
			for (int z = 0; z < e; ++z) {
				try {
					int r = rand.nextInt(list.length);
					int p = z + y;
					if (p < 1)
						p = 0;
					characters[p] = new Game_Enemy(list[r], this, 0, 0);
					characters[p].set_pos_rand();

				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (!Game_Map.boss(Game_Player.floor)) {
			int t = rand.nextInt(5) + 10 - (Game_Player.floor);
			for (int z = 0; z < t; ++z) {
				characters[e + z + y] = new Game_Chest(this, 0, 0);// Game_Enemy(list[r],this,0,0);
				boolean b = characters[e + z + y].set_pos_rand();
				if (b == false) {
					characters[e + z + y] = null;
				}
			}
		}

	}

	public int new_character() {
		int i = 0;
		while (characters[i] != null) {
			++i;
		}
		return i;
	}

	public void remove_event(Game_Character g) {
		for (int i = 0; i < characters.length; ++i) {
			if (characters[i] == g) {
				characters[i] = null;
				break;
			}
		}
	}

	public Game_Boss boss() {
		Game_Boss event = null;
		for (int z = 0; z < 999; ++z) {
			if (characters[z] instanceof Game_Boss) {
				event = (Game_Boss) characters[z];
				break;
			}
		}
		return event;
	}

	public int[][] map() {
		return SpritesetMap.map.map;
	}

}