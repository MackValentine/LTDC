package mack.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import mack.main.RPGPanel;
import mack.particle.Particle_Eclair;
import mack.rpg.Enemy_List;
import mack.rpg.RPG_Enemy;
import mack.scene.Scene_Map;

public class Game_Boss extends Game_Enemy {

	public int type = 1;

	public Game_Weapon weapon;
	public boolean dead = false;
	public int wait_d = 0;
	private ArrayList<Game_Enemy> sbires;
	private int wait_create;
	private int combo;

	private int old_hp = -1;

	public Game_Boss(int k, Scene_Map m, int i, int j)
			throws CloneNotSupportedException, IOException {
		super(k, m, i, j);
		enemy = (RPG_Enemy) Enemy_List.enemy_list[k].clone(this);
		this.change_character(enemy.character_name, false);
		type = enemy.type;
		sbires = new ArrayList<Game_Enemy>();
		wait_create = 1;
		old_hp = enemy.Hp;
	}

	public void update() throws IOException, FontFormatException {
		super.update();
		if (enemy != null) {
			if (Game_Character.movable == true) {
				if (!sbires.isEmpty()) {
					for (int l = 0; l < sbires.size(); l++) {
						if (sbires.get(l) == null || sbires.get(l).dead == true) {
							sbires.remove(l);
						}
					}
				} else if (wait_create == 0) {
					this.set_pos_in_room(2);
					wait_create = 20;
					invincible = false;
				}

				if (wait_create > 0) {
					--wait_create;
					if (enemy.Hp < old_hp) {
						old_hp = enemy.Hp;
						combo++;
					}
					if (wait_create == 0 || combo >= 5) {
						combo = 0;
						wait_create = 0;
						invincible = true;
						boss_attack();
						try {
							create_ennemy();
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (enemy.Hp <= 0) {
				dead = true;
			}
		}
	}

	private void boss_attack() throws IOException {
		Particle_Eclair p = new Particle_Eclair(this.x, this.y, map);
		this.add_particle(p);

		int r = 3;

		Game_Player player = RPGPanel.map.player();
		if (Math.abs(player.x - x) < r) {
			if (Math.abs(player.y - y) < r) {
				Game_Player.actor.take_magic_damage(enemy.sagesse);
			}
		}

	}

	public void create_ennemy() throws CloneNotSupportedException, IOException {
		int id = 2;

		int m = 5;

		int idm = 2;
		if (Game_Player.difficulty == 1)
			idm = 3;
		if (Game_Player.difficulty == 2)
			idm = 6;

		for (int i = 0; i < m; ++i) {
			Game_Enemy e = new Game_Enemy(idm, map, 0, 0);
			boolean b = e.set_pos_in_room(id);

			if (b) {
				sbires.add(e);
				map.add_new_event(e);
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
		Game_Map.boss_dead[0] = true;
		/*
		 * RPGPanel.map.frame.call_scene(new Scene_Picture(RPGPanel.map.frame,
		 * "End"));
		 */
		// Scene_Picture.show_picture("Fin");
	}

	public void render(Graphics g) throws IOException {
		super.render(g);
	}
}
