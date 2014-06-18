package mack.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import mack.main.RPGPanel;
import mack.particle.Particle;
import mack.rpg.RPG_Enemy;
import mack.scene.Scene_Map;
import mack.sprites.SpriteSheet;
import mack.sprites.SpritesetMap;
import mack.tiles.TileDoorExit;
import mack.tiles.TileStairs;
import mack.tiles.Tiles;

public class Game_Character {

	public RPG_Enemy enemy;

	public static boolean movable;
	public SpriteSheet character_spritesheet;
	public Image character_sprite;
	public String character_name;

	public int x;
	public int y;
	public int screen_x;
	public int screen_y;

	public boolean trought = false;

	public int direction;
	public int character_frame;
	public int character_wframe = 0;
	public int movespeed;

	public int old_dir;
	public int old_frame;
	public boolean move;
	public boolean want_move;

	public boolean exist = true;

	public Scene_Map map;
	public int type = 0;

	public Particle[] particles;

	public boolean draw_particle = true;

	public boolean use_direction = true;

	public BufferedImage sprite;

	public Game_Character(Scene_Map m, String string, int i, int j)
			throws IOException {
		map = m;

		movespeed = 1;
		direction = 2;
		character_name = string;
		set_pos(i, j);
		character_frame = 0;
		move = false;

		particles = new Particle[100];
		if (string != "") {
			character_spritesheet = new SpriteSheet("characters/" + string
					+ ".png", 16, 16);
			character_sprite = character_spritesheet.getSprite(0, 0);
		}
	}

	public boolean actionnable() {
		return false;
	}

	public void change_dir(int i) {
		direction = i;
		old_dir = i;
		refresh_sprite();
	}

	public boolean trought() {
		return trought;
	}

	public boolean exist() {
		return exist;
	}

	public boolean event_in_range(Game_Character player, int r) {
		if (Math.abs(player.x - x) <= r) {
			if (Math.abs(player.y - y) <= r) {
				return true;
			}
		}
		return false;
	}

	public boolean passable(Game_Character event, int ax, int ay) {
		if (SpritesetMap.exist((x + (ax)), (y + (ay)))) {
			if (trought()) {
				return true;
			}
			if (event != this) {
				if (!event.trought()) {
					if (event.x == (x + (ax)) && event.y == (y + (ay))) {
						return false;
					} else {
						if (Tiles.tiles_list[SpritesetMap.tiles[(x + (ax))][(y + (ay))]]
								.passable()) {
							return true;
						}
					}
				}

				else {
					if (Tiles.tiles_list[SpritesetMap.tiles[(x + (ax))][(y + (ay))]]
							.passable()) {
						return true;
					}
				}
			} else {
				if (Tiles.tiles_list[SpritesetMap.tiles[(x + (ax))][(y + (ay))]]
						.passable()) {
					return true;
				}
			}
		}
		return false;
	}

	public void change_character(BufferedImage s, boolean b) {
		character_spritesheet = new SpriteSheet(s, 16, 16);
		int i = 0;
		if (direction == 4)
			i = 1;
		if (direction == 6)
			i = 2;
		if (direction == 8)
			i = 3;

		if (use_direction == false || i > 3) {
			i = 0;
		}

		character_sprite = character_spritesheet.getSprite(character_frame, i);

	}
	
	protected void change_character(String string) throws IOException {
		character_spritesheet = new SpriteSheet("characters/" + string
				+ ".png", 16, 16);
		character_sprite = character_spritesheet.getSprite(0, 0);
	}

	public void change_character(String string, boolean b) throws IOException {
		character_spritesheet = new SpriteSheet("characters/" + string
				+ ".png", 16, 16);
		int i = 0;
		if (direction == 4)
			i = 1;
		if (direction == 6)
			i = 2;
		if (direction == 8)
			i = 3;

		if (use_direction == false || i > 3) {
			i = 0;
		}

		character_sprite = character_spritesheet.getSprite(character_frame, i);
	}

	public void update() throws IOException, FontFormatException {
		for (int i = 0; i < particles.length; ++i) {
			if (particles[i] != null) {
				if (particles[i].exist)
					particles[i].update(screen_x, screen_y);
				else {
					particles[i] = null;
				}
			}
		}
	}

	public void refresh_sprite() {
		int i = 0;
		if (direction == 4)
			i = 1;
		if (direction == 6)
			i = 2;
		if (direction == 8)
			i = 3;

		if (use_direction == false) {
			i = 0;
		}

		if (character_name != "")
			character_sprite = character_spritesheet.getSprite(character_frame,
					i);
	}

	public void render(Graphics g) throws IOException {
		if (screen_x != x * 16 || screen_y != y * 16) {
			move = true;
		} else {
			move = false;
		}

		if (screen_x < x * 16) {
			screen_x += movespeed;
		} else if (screen_x > x * 16) {
			screen_x -= movespeed;
		}

		if (screen_y < y * 16) {
			screen_y += movespeed;
		} else if (screen_y > y * 16) {
			screen_y -= movespeed;
		}

		if (move) {
			character_wframe += 1;
			if (character_wframe > 8) {
				character_wframe = 0;
				character_frame += 1;
				if (character_frame > character_spritesheet.getWidth() - 1) {
					character_frame = 0;
				}
			}
		} else {
			character_frame = 0;
			character_wframe = 0;
		}
		if ((direction != old_dir) || (old_frame != character_frame)) {
			old_dir = direction;
			old_frame = character_frame;
			refresh_sprite();
		}
		if (RPGPanel.game_map.has_visit[Game_Player.floor][x][y]
				&& character_name != "")
			g.drawImage(character_sprite, (screen_x) - map.spriteset.display_x,
					(screen_y) - map.spriteset.display_y, null);

		if (draw_particle)
			for (int i = 0; i < particles.length; ++i) {
				if (particles[i] != null) {
					particles[i].render(g);
				}
			}
	}

	public void move_toward(Game_Character player) {

		if (Math.abs(x - player.x) > Math.abs(y - player.y)) {
			if (x > player.x) {
				move_direction(4);
			} else if (x < player.x) {
				move_direction(6);
			}
		} else {
			if (y > player.y) {
				move_direction(8);
			} else if (y < player.y) {
				move_direction(2);
			}
		}
	}

	public void look_at(Game_Player player) {
		if (Math.abs(x - player.x) > Math.abs(y - player.y)) {
			if (x > player.x) {
				direction = 4;
			} else if (x < player.x) {
				direction = 6;
			}
		} else {
			if (y > player.y) {
				direction = 8;
			} else if (y < player.y) {
				direction = 2;
			}
		}
	}

	public void move_direction(int i) {
		if (i == 2) {
			move_down();
		} else if (i == 4) {
			move_left();
		} else if (i == 6) {
			move_right();
		} else if (i == 8) {
			move_up();
		}
	}

	public void set_dead() throws IOException {
		exist = false;
		trought = true;
	}

	public void move_up() {
		boolean b = false;
		direction = 8;
		for (int i = 0; i < map.characters.length; ++i) {
			if (map.characters[i] != null) {
				Game_Character event = map.characters[i];
				if (passable(event, 0, -1)) {
					b = true;
				} else {
					b = false;
					break;
				}
			}
		}
		if (b) {
			y -= 1;
		}

		move = true;
	}

	public void move_down() {
		boolean b = false;
		direction = 2;
		for (int i = 0; i < map.characters.length; i++) {
			if (map.characters[i] != null) {
				Game_Character event = map.characters[i];
				if (passable(event, 0, 1)) {
					b = true;
				} else {
					b = false;
					break;
				}
			}
		}
		if (b) {
			y += 1;
		}

		move = true;
	}

	public void move_left() {
		direction = 4;
		boolean b = false;
		for (int i = 0; i < map.characters.length; i++) {
			if (map.characters[i] != null) {
				Game_Character event = map.characters[i];
				if (passable(event, -1, 0)) {
					b = true;
				} else {
					b = false;
					break;
				}
			}
		}
		if (b) {
			x -= 1;
		}

		move = true;
	}

	public void move_right() {
		boolean b = false;
		direction = 6;
		for (int i = 0; i < map.characters.length; i++) {
			if (map.characters[i] != null) {
				Game_Character event = map.characters[i];
				if (passable(event, 1, 0)) {
					b = true;
				} else {
					b = false;
					break;
				}
			}
		}
		if (b) {
			x += 1;
		}

		move = true;
	}

	public void move_rand() {
		Random rand = new Random();
		if (rand.nextInt(3) == 0) {
			move_down();
		}
		if (rand.nextInt(3) == 1) {
			move_up();
		}
		if (rand.nextInt(3) == 2) {
			move_left();
		}
		if (rand.nextInt(3) == 3) {
			move_right();
		}
	}

	public void set_pos(int a, int b) {
		x = a;
		y = b;
		screen_x = x * 16;
		screen_y = y * 16;
	}

	public boolean collide(Game_Character event) {
		if (event != null && event != this) {
			if (event.exist) {
				if (event.x == x && event.y == y) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean contact_player() {
		Game_Player player = (Game_Player) map.player();
		int i = player.direction == 4 ? player.x - 1
				: player.direction == 6 ? player.x + 1 : player.x;
		int j = player.direction == 8 ? player.y - 1
				: player.direction == 2 ? player.y + 1 : player.y;
		if (i == x && j == y) {
			return true;
		}
		return false;
	}

	public boolean contact_event() {
		Game_Player player = (Game_Player) map.player();
		int i = direction == 4 ? x - 1 : direction == 6 ? x + 1 : x;
		int j = direction == 8 ? y - 1 : direction == 2 ? y + 1 : y;
		if (i == player.x && j == player.y) {
			return true;
		}
		return false;
	}

	public boolean set_pos_rand() {
		boolean c = false;
		int n = 0;
		while (c == false) {
			Random rand = new Random();
			int a = rand.nextInt(SpritesetMap.width());
			int b = rand.nextInt(SpritesetMap.height());
			if (SpritesetMap.passable(a, b)
					&& (Tiles.is_floor(SpritesetMap.tiles[a][b]))
					&& event_pos(a, b)) {
				set_pos(a, b);
				c = true;
			}
			++n;
			if (n > 100) {
				c = true;
				return false;
			}

		}

		return true;

	}

	public boolean set_pos_in_room(int id) {
		boolean c = false;
		int n = 0;
		while (c == false) {
			Random rand = new Random();
			int a = rand.nextInt(SpritesetMap.width());
			int b = rand.nextInt(SpritesetMap.height());
			if (SpritesetMap.passable(a, b)
					&& (Tiles.is_floor(SpritesetMap.tiles[a][b]))
					&& event_pos(a, b) && SpritesetMap.map.room_id[a][b] == id) {
				set_pos(a, b);
				c = true;
			}
			++n;
			if (n > 200) {
				c = true;
				return false;
			}

		}

		return true;
	}

	public boolean event_pos(int a, int b) {
		for (int i = 0; i < map.characters.length; i++) {
			if (map.characters[i] != null) {
				if (map.characters[i].x == a && map.characters[i].y == b) {
					return false;
				}
			}
		}
		return true;
	}

	public void seton_stairs(int i) {
		for (int a = 0; a < SpritesetMap.width(); ++a) {
			for (int b = 0; b < SpritesetMap.height(); ++b) {
				if (Game_Player.floor > 0) {
					if (Tiles.tiles_list[SpritesetMap.tiles[a][b]] instanceof TileStairs) {
						TileStairs s = (TileStairs) Tiles.tiles_list[SpritesetMap.tiles[a][b]];
						if (i == 1) {
							if (s.stairs_up == false)
								set_pos(a, b);
						} else {
							if (s.stairs_up == true)
								set_pos(a, b);
						}
					}
				} else {
					if (i != 1) {
						if (Tiles.tiles_list[SpritesetMap.tiles[a][b]] instanceof TileStairs) {
							TileStairs s = (TileStairs) Tiles.tiles_list[SpritesetMap.tiles[a][b]];
							if (s.stairs_up == true)
								set_pos(a, b);
						}
					} else {

						if (Tiles.tiles_list[SpritesetMap.tiles[a][b]] instanceof TileDoorExit) {
							set_pos(a, b - 1);
						}
					}
				}
			}
		}
	}

	public boolean push_A() {
		return false;
	}

	public void add_particle(Particle particle) {
		for (int i = 0; i < particles.length; ++i) {
			if (particles[i] == null) {
				particles[i] = particle;
				break;
			}
		}
	}
}