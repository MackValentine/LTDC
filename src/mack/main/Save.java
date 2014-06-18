package mack.main;

import java.io.Serializable;

import mack.game.Game_Actor;
import mack.game.Game_Map;

public class Save implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4973293329755567871L;

	public Game_Map map;
	public Game_Actor actor;
	public int player_floor;

	public int player_difficulty;
}
