package mack.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mack.main.Graphics_Loader;
import mack.main.Option;
import mack.main.RPGPanel;
import mack.pathfinding.Line;
import mack.scene.Scene_Map;
import mack.scene.Scene_Village;
import mack.sprites.SpritesetMap;
import mack.tiles.Tiles;

public class Game_Player extends Game_Character {

	public static Game_Actor actor;
	public static boolean can_move;

	public static int difficulty = 1;

	public static int floor = 0;

	public boolean has_moved;

	public static boolean debug_mode = false;

	public int w_move = 0;

	public int time;

	private int wait_move;
	private int wait_stairs;
	private ArrayList<Game_Enemy> door_trap;
	private ArrayList<Game_Door> doors;
	private int wait_change_chara;
	private int new_dir;
	public BufferedImage sprite_action;
	public BufferedImage sprite;
	public static Game_LBoss l;
	public static Game_Weapon weapon;
	public static boolean use_shield;

	public Game_Player(Scene_Map m, String string, int i, int j)
			throws IOException, CloneNotSupportedException {
		super(m, string, i, j);
		actor = new Game_Actor();
		wait_move = 0;
		can_move = true;
		time = 0;
		draw_particle = false;
		door_trap = new ArrayList<Game_Enemy>();
		doors = new ArrayList<Game_Door>();

		l = new Game_LBoss();

		sprite_action = (BufferedImage) Graphics_Loader
				.load("characters/Guy_atk.png");
		sprite = (BufferedImage) Graphics_Loader.load("characters/Guy.png");

		change_character(sprite, false);

	}

	public int max_time() {
		return 15;
	}

	public void update() throws IOException, FontFormatException {
		super.update();

		if (!door_trap.isEmpty()) {
			for (int l = 0; l < door_trap.size(); l++) {
				if (door_trap.get(l) == null || door_trap.get(l).dead == true) {
					door_trap.remove(l);
				}
			}
		} else if (!doors.isEmpty()) {
			for (int l = 0; l < doors.size(); l++) {
				map.remove_event(doors.get(l));
				doors.remove(l);
			}
		}

		if (debug_mode) {
			actor.heal(999);
		}
		if (has_moved == true) {
			++w_move;
			if (w_move > 2) {
				w_move = 0;
				Game_Character.movable = true;
				has_moved = false;
				if (this.getCell() == 8 || this.getCell() == 9) {
					if (Game_Player.floor == 4) {
						System.out.println("WXC");
						if (Game_Map.boss_dead[0] == true)
							++wait_stairs;
					} else
						++wait_stairs;
				} else if (this.getCell() == 20) {
					wait_stairs = 8;
				} else if (this.getCell() == 28) {
					try {
						lock_door();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			Game_Character.movable = false;
		}

		if (wait_stairs > 0) {
			++wait_stairs;
			if (wait_stairs > 16) {
				wait_stairs = 0;
				if (this.getCell() == 8) {
					// STAIRS UP
					if (Game_Player.floor >= 10) {
						System.out.print("Fin");
						Game_Player.floor = 10;
					} else {
						System.out.print("Monte d'un étage");
						Game_Player.floor += 1;
						RPGPanel.map.change_map(1);
						if (Game_Player.floor > 4)
							Game_Map.etage_name = "landing2";
					}
				} else if (this.getCell() == 9) {
					// STAIRS DOWN
					if (Game_Player.floor == 0) {
						System.out.print("Sortie");
						RPGPanel.map.frame.call_scene(new Scene_Village(
								RPGPanel.map.frame));
					} else {
						System.out.print("Descend d'un étage");
						Game_Player.floor -= 1;
						RPGPanel.map.change_map(-1);
						if (Game_Player.floor < 5)
							Game_Map.etage_name = "landing1";
					}
				} else if (this.getCell() == 20) {
					// STAIRS DOWN
					System.out.print("Sortie");
					RPGPanel.map.frame.call_scene(new Scene_Village(
							RPGPanel.map.frame));

				}
			}
			map.spriteset.display_x = (screen_x) - SpritesetMap.width() * 4
					/ ((SpritesetMap.width() * 16 / 160)) * 2;
			map.spriteset.display_y = (screen_y) - 16 - SpritesetMap.height()
					* 4 / ((SpritesetMap.height() * 16 / 160));
			return;
		}

		if (Option.triggerA()) {
			if (can_move) {
				if (event_actionnable() && (actor.equipements[0] != null)) {
					can_move = false;
					actor.equipements[0].use_item(map, this);
					add_time();
				}
			}

		}

		if (Option.triggerB()) {
			if (actor.equipements[1] != null) {
				if (actor.item_number(actor.equipements[1]) > 0) {
					actor.equipements[1].use_item(map, this);
					add_time();
					has_moved = true;
				}
			}
		}

		if (weapon != null && weapon.exist()) {
			weapon.update();
		}

		if (can_move) {
			if (new_dir != 0) {
				direction = new_dir;
				new_dir = 0;
			}
			if (Option.pressUp()) {
				wait_move += 1;
				if (wait_move > 15) {
					if (direction == 8) {
						move_up();
						add_time();
						has_moved = true;
						wait_move = 0;
					} else {
						direction = 8;
						wait_move = 10;
					}
					// Game_Character.movable=true;
				}
			} else if (Option.pressDown()) {
				wait_move += 1;
				if (wait_move > 15) {
					if (direction == 2) {
						move_down();
						add_time();
						has_moved = true;
						wait_move = 0;
					} else {
						direction = 2;
						wait_move = 10;
					}
					// Game_Character.movable=true;

				}
			}

			else if (Option.pressLeft()) {
				wait_move += 1;
				if (wait_move > 15) {
					if (direction == 4) {
						move_left();
						add_time();
						has_moved = true;
						wait_move = 0;
					} else {
						direction = 4;
						wait_move = 10;
					}
					// Game_Character.movable=true;
				}
			} else if (Option.pressRight()) {
				wait_move += 1;
				if (wait_move > 15) {
					if (direction == 6) {
						move_right();
						add_time();
						has_moved = true;
						wait_move = 0;
					} else {
						direction = 6;
						wait_move = 10;
					}

					// Game_Character.movable=true;
				}

			} else {
				wait_move = 15;
			}
		} else {
			if (Option.pressUp())
				new_dir = 8;
			else if (Option.pressLeft())
				new_dir = 4;
			else if (Option.pressRight())
				new_dir = 6;
			else if (Option.pressDown())
				new_dir = 2;
		}

		set_tile_visible(x, y);
		for (int a = -3; a < 4; ++a) {
			for (int b = -3; b < 4; ++b) {
				if ((Math.abs(a) + Math.abs(b)) <= 4) {
					Line l = new Line(new Point(x, y), new Point(x + a, y + b));

					int g = 0;
					if (l.points.size() != 0)
						while (true) {
							++g;
							Point p = l.points.get(g);
							set_tile_visible(p.x, p.y);
							if (has_wall(p.x, p.y)) {
								break;
							}
							if (g >= l.points.size() - 1) {
								break;
							}

						}

				}
			}
		}

		map.spriteset.display_x = (screen_x) - SpritesetMap.width() * 4
				/ ((SpritesetMap.width() * 16 / 160)) * 2;
		map.spriteset.display_y = (screen_y) - 16 - SpritesetMap.height() * 4
				/ ((SpritesetMap.height() * 16 / 160));

	}

	private void lock_door() throws IOException, CloneNotSupportedException {
		int id = SpritesetMap.map.room_id[x][y];
		int l = SpritesetMap.map.doors_room[id].size();
		if (SpritesetMap.map.room_visited[id] == false) {
			SpritesetMap.map.room_visited[id] = true;
			if (id > 1) {
				for (int i = 0; i < l; ++i) {
					int x1 = SpritesetMap.map.doors_room[id].get(i)[0];
					int y1 = SpritesetMap.map.doors_room[id].get(i)[1];
					Game_Door t = new Game_Door(map, "door", x1, y1);

					doors.add(t);
					map.add_new_event(t);
				}

				int[][] r = SpritesetMap.map.rooms[id];

				int w = r.length;
				int h = r[0].length;
				int m = w * h / 2;

				for (int i = 0; i < m; ++i) {
					int[] list = Game_Map.enemy_list[Game_Player.floor];
					int r2 = new Random().nextInt(list.length);
					Game_Enemy e = new Game_Enemy(list[r2], map, 0, 0);
					boolean b = e.set_pos_in_room(id);

					if (b) {
						door_trap.add(e);
						map.add_new_event(e);
					}

				}
			}
		}

	}

	public boolean has_wall(int ax, int ay) {
		if (SpritesetMap.exist(((ax)), ((ay)))) {
			if (Tiles.tiles_list[SpritesetMap.tiles[((ax))][((ay))]].passable()) {
				return false;
			}
		}
		return true;
	}

	private int getCell() {
		int i = SpritesetMap.tiles[x][y];
		return i;
	}

	private void add_time() throws IOException {
		if (time > this.max_time()) {
			++Game_Player.actor.hour;
			time = 0;
		} else
			++time;

		if (Game_Player.actor.hour > 12 * 10) {
			Random rand = new Random();
			int i = Game_Player.actor.mHp / 50;
			if (i <= 0)
				i = 1;
			Game_Player.actor.damage(rand.nextInt(i) + 1);
		}

	}

	public void set_tile_visible(int i, int j) {
		if (SpritesetMap.exist(i, j)) {
			RPGPanel.game_map.has_visit[Game_Player.floor][i][j] = true;
		}
	}

	public boolean event_actionnable() {
		for (int i = 0; i < map.characters.length; ++i) {
			if (map.characters[i] != null) {
				if (map.characters[i].contact_player()) {
					if (map.characters[i].actionnable()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean trought() {
		return trought;
	}

	public void render(Graphics g) throws IOException {
		super.render(g);

		if (wait_change_chara > 0) {
			wait_change_chara--;
			if (wait_change_chara == 0) {
				change_character((BufferedImage) sprite, true);
				// change_character("Guy", true);
			}
		}

		if (weapon != null && weapon.exist()) {
			weapon.render(g);
		}

		for (int i = 0; i < particles.length; ++i) {
			if (particles[i] != null) {
				particles[i].render(g);
			}
		}

	}

	public boolean push_A() {
		if (Option.triggerA()) {
			return true;
		}
		return false;
	}

	public void change_character(String s, boolean b, int i) throws IOException {
		change_character(s, b);

		wait_change_chara = i;

	}

	public void change_character(BufferedImage s, boolean b, int i) {
		change_character(s, b);

		wait_change_chara = i;
	}

}
