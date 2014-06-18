package mack.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSound;
import mack.scene.Scene_Map;

public class Game_Weapon extends Game_Character {

	public int wait;
	private Game_Character owner;

	public Game_Weapon(Scene_Map m, String string, int i, int j, int k,
			Game_Character o) throws IOException {
		super(m, string, i, j);
		move_direction(k);
		wait = 0;
		movespeed = 4;
		RPGSound s = new RPGSound("Sound/Weapon", 1, 1, 0);
		Sound.sound_play(s);

		owner = o;
	}

	public boolean trought() {
		return true;
	}

	public void update() throws IOException, FontFormatException {
		super.update();
		wait += 1;

		System.out.println(wait);
		if (wait == 1) {
			if (owner instanceof Game_Player) {
				for (int a = 0; a < map.characters.length; ++a) {
					Game_Character event = map.characters[a];
					if (collide(event)) {
						if (event instanceof Game_Enemy) {
							Game_Enemy e = (Game_Enemy) event;
							e.enemy.take_damage(Game_Player.actor.total_force());
						}
					}
				}
			} else {
				Game_Character event = map.player();

				if (collide(event)) {
					Game_Player.actor
							.take_damage(((Game_Enemy) owner).enemy.force);
				}
			}
		} else if (wait == 15) {
			if (owner instanceof Game_Player)
				Game_Character.movable = true;
		} else if (wait > 20) {
			System.out.println("AZEQSDWXC");

			if (owner instanceof Game_Player) {
				map.player().change_character(RPGPanel.map.player().sprite,
						true);
				Game_Player.can_move = true;
			} else {
				owner.change_character(owner.sprite, false);
			}
			set_dead();
		}
	}

	public void render(Graphics g) {
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
		if ((direction != old_dir) || (old_frame != character_frame)) {
			old_dir = direction;
			old_frame = character_frame;
			refresh_sprite();
		}
		int i = 0;
		int j = 0;
		if (direction == 2)
			i = 2;
		else if (direction == 4 || direction == 6)
			j = 4;
		else if (direction == 8)
			i = -4;
		if (RPGPanel.game_map.has_visit[Game_Player.floor][x][y])
			g.drawImage(character_sprite, (screen_x) - map.spriteset.display_x
					+ i, (screen_y) - map.spriteset.display_y + j, null);

		for (int l = 0; l < particles.length; ++l) {
			if (particles[l] != null) {
				particles[l].render(g);
			}
		}
	}

}
