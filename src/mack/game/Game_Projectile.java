package mack.game;

import java.io.IOException;

import mack.scene.Scene_Map;

public class Game_Projectile extends Game_Character {

	public int move_tick;
	public Game_Character owner;
	private int range;
	private boolean die;
	private int wait;

	public Game_Projectile(Scene_Map m, int k, int i, int j, Game_Character o,
			int p, String s) throws IOException {
		super(m, s, i, j);
		range = p;
		move_tick = 0;
		direction = k;
		owner = o;
	}

	public boolean trought() {
		return true;
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

	public void update() throws IOException {
		if (die == true) {
			wait++;
			if (wait == 5)
				if (owner instanceof Game_Player)
					Game_Player.can_move = true;
			if (wait > 15)
				this.set_dead();
		} else {
			if (!this.move) {
				this.move_direction(direction);
				++move_tick;
				if (owner instanceof Game_Player) {
					for (int i = 0; i < map.characters.length; ++i) {
						Game_Character event = map.characters[i];
						if (collide(event)) {
							if (event instanceof Game_Enemy) {
								Game_Enemy e = (Game_Enemy) event;
								e.enemy.take_damage(Game_Player.actor
										.total_force());
							}
						}
					}
				} else {
					Game_Character event = map.player();
					if (collide(event)) {
						if (event instanceof Game_Player) {
							Game_Enemy o = (Game_Enemy) owner;
							if (o.enemy != null)
								Game_Player.actor.take_damage(o.enemy.force);
						}
					}
				}
			}

			if (move_tick >= range) {
				die = true;
				wait = 0;
			}
		}
	}
}
