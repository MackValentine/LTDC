package mack.game;

import java.io.Serializable;

public class Game_Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2292501545835608712L;

	public Game_Dungeon[] dungeons;
	public static int m_floor = 10;
	public static int[][] enemy_list;

	public boolean[][][] has_visit;
	
	public static String etage_name = "landing1";

	public static boolean[] boss_dead;

	public long seed;

	public Game_Map() {
		
		boss_dead = new boolean[] {false,false};
		
		seed = System.currentTimeMillis();

		has_visit = new boolean[m_floor][999][999];

		dungeons = new Game_Dungeon[m_floor];
		create_enemy();
		for (int i = 0; i < m_floor; ++i) {

			if (boss(i)){
				System.out.println("QSD");
				dungeons[i] = new Game_Dungeon(60,60, 2, i);
			}
			else
				dungeons[i] = new Game_Dungeon(60, 60, 20, i);
		}
		
		if (Game_Player.floor<=4)
			Game_Map.etage_name="landing1";
		else
			Game_Map.etage_name="landing2";
		
		System.out.println("Fin Création Maps");
	}

	public void create_enemy() {
		enemy_list = new int[m_floor][100];
		enemy_list[0] = new int[] { 1, 2 };
		enemy_list[1] = new int[] { 3, 2, 1 };
		enemy_list[2] = new int[] { 3, 2, 4 };
		enemy_list[3] = new int[] { 3, 2, 4, 5, 6 };
		enemy_list[4] = new int[] { 10 };
		
		enemy_list[5] = new int[] { 21, 22 };
		enemy_list[6] = new int[] { 23, 22, 21 };
		enemy_list[7] = new int[] { 23, 22, 24 };
		enemy_list[8] = new int[] { 23, 22, 24, 25, 26 };
		enemy_list[9] = new int[] { 1, 2 };
	}

	public static boolean boss(int i) {
		return i == 4 || i == 9;
	}
}
