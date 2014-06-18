package mack.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mack.items.Item;
import mack.items.ItemArmor;
import mack.items.ItemWeapon;
import mack.main.Graphics_Loader;
import mack.main.RPGPanel;
import mack.pathfinding.Node;
import mack.pathfinding.Path;
import mack.rpg.Enemy_List;
import mack.rpg.RPG_Enemy;
import mack.scene.Scene_Map;

public class Game_Enemy extends Game_Character {

	public RPG_Enemy enemy;
	public int type = 1;

	public Game_Weapon weapon;
	public boolean dead = false;
	public int wait_d = 0;
	public boolean invincible;
	public BufferedImage sprite_attack;

	public Game_Enemy(int k, Scene_Map m, int i, int j)
			throws CloneNotSupportedException, IOException {
		super(m, "vide", i, j);
		if (k > 0) {
			enemy = (RPG_Enemy) Enemy_List.enemy_list[k].clone(this);
			type = enemy.type;

			use_direction = enemy.use_direction;

			if (enemy.character_name != null && enemy.character_name != "")
				sprite = (BufferedImage) Graphics_Loader
						.load("characters/"+enemy.character_name+".png");

			change_character(sprite, false);
		}
	}

	public void update() throws IOException, FontFormatException {
		super.update();
		
		if (weapon != null && dead == false)
			weapon.update();
		
		if (type != 11) {
			if (dead == true) {
				++wait_d;
				if (wait_d > 20) {
					set_dead();
					// exist=false;
				}
			} else if (enemy != null) {
				if (Game_Character.movable == true) {
					if (type != 10) {
						if (type == 0) {
							if (Game_Character.movable == true) {
								move_rand();
							}
						} else if (type == 1) {
							if (event_in_range(map.player(), 5)) {
								if (this.event_lined(map.player())
										&& event_in_range(map.player(), 1)) {
									if (contact_event()) {
										// Game_Player.actor
										// .take_damage(enemy.force);
										weapon = new Game_Weapon(map,
												enemy.weapon_name, this.x,
												this.y, this.direction, this);
										if (this.sprite_attack != null) {
											change_character(sprite_attack,
													false);
										}
									} else {
										look_at(map.player());
									}
								} else {
									move_toward(map.player());
								}
							} else {
								move_rand();
							}
						} else if (type == 2) {
							if (event_in_range(map.player(), 5)) {
								if (this.event_lined(map.player())
										&& event_in_range(map.player(), 3)) {
									look_at(map.player());
									Game_Character event = new Game_Fireball(
											map, this.direction, this.x,
											this.y, this);
									map.add_new_event(event);
								} else {
									move_toward(map.player());
								}
							} else {
								move_rand();
							}
						} else if (type == 3) {
							if (event_in_range(map.player(), 5)) {
								if (this.event_lined(map.player())
										&& event_in_range(map.player(), 3)) {
									look_at(map.player());
									Game_Character event = new Game_Projectile(
											map, this.direction, this.x,
											this.y, this, 3, "Arrow");
									map.add_new_event(event);
								} else {
									move_toward(map.player());
								}
							} else {
								move_rand();
							}
						} else if (type == 4) {
							if (event_in_range(map.player(), 4)) {
								if (this.event_lined(map.player())
										&& event_in_range(map.player(), 2)) {
									look_at(map.player());
									Game_Character event = new Game_Projectile(
											map, this.direction, this.x,
											this.y, this, 2, "Spear");
									map.add_new_event(event);
								} else {
									move_toward(map.player());
								}
							} else {
								move_rand();
							}
						}
					}

				}
			}
			if (enemy.Hp <= 0) {
				dead = true;
				// set_dead();
			}
		}
	}

	public void move_toward(Game_Character player) {
		int dx = player.x;
		int dy = player.y;

		Node s = new Node(x, y);
		s.x = x;
		s.y = y;

		Node e = new Node(dx, dy);
		e.x = dx;
		e.y = dy;

		Path p = new Path(s, e, map.map());
		p.set_character(true, map.characters, this);

		ArrayList<Node> list = p.search();
		if (list != null) {
			boolean b = true;
			int id = list.size() - 2;
			for (int i = 0; i < map.characters.length; ++i) {
				if (map.characters[i] != null) {
					Game_Character event = map.characters[i];
					if (!passable(event, list.get(id).x - x, list.get(id).y - y)) {
						b = false;
					}
				}
			}
			if (b == true)
				moveto(list.get(id).x - x, list.get(id).y - y);
		}

	}

	private void moveto(int i, int j) {
		x += i;
		y += j;

		if (Math.abs(i) > Math.abs(j)) {
			if (i < 0) {
				direction = 4;
			} else if (i > 0) {
				direction = 6;
			}
		} else {
			if (j < 0) {
				direction = 8;
			} else if (j > 0) {
				direction = 2;
			}
		}
	}

	public boolean event_lined(Game_Character player) {
		if (x == player.x || y == player.y) {
			return true;
		}
		return false;
	}

	public void set_dead() throws IOException {
		super.set_dead();
		enemy.set_dead();
		int i = RPGPanel.map.new_character();
		if (enemy != null)
			if (!enemy.drops.isEmpty()) {
				ArrayList<Item> drops = new ArrayList<Item>();
				for (int j = 0; j < enemy.drops.size(); ++j) {
					Random rand = new Random();
					int r = rand.nextInt(10);
					if (r > 7) {
						Item item = enemy.drops.get(j);
						if (item instanceof ItemWeapon) {
							try {
								item = ((ItemWeapon) item).randomize();
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						} else if (item instanceof ItemArmor) {
							try {
								item = ((ItemArmor) item).randomize();
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						}
						drops.add(item);
					}
				}
				if (!drops.isEmpty())
					RPGPanel.map.characters[i] = new Game_Drop(map, drops, x, y);
			}
	}

	public void render(Graphics g) throws IOException {
		super.render(g);
		if (weapon != null && weapon.exist()) {
			weapon.render(g);
		}
	}
}
