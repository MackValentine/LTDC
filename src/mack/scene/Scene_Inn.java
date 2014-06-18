package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_HUD;
import mack.window.Window_Help;
import mack.window.Window_Menu;
import mack.game.Game_Player;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.Sound;

public class Scene_Inn extends Scene_Base {

	public Window_HUD HUD;
	public Window_Help help;

	private WindowCommand window_command;
	private Window_Menu window_menu;

	public Scene_Inn(RPGGame f) throws IOException, FontFormatException {
		super(f);

		HUD = new Window_HUD(0, 144, 0, 0);

		help = new Window_Help();

		String[] tab = { "Dormir", "Sortir" };
		window_command = new WindowCommand(0, 16, 80, tab, true);
		window_menu = new Window_Menu();
		
	}

	public void paint(Graphics g) {
		window_menu.draw(g);
    	
    	HUD.draw(g);
    	help.draw(g);
    	window_command.draw(g);
    	help.set_text("10G / "+Game_Player.actor.gold+" G",g);
	}

	public void update() throws IOException, FontFormatException {
		window_command.update();
    	if (window_command.valid)
    	{
    		//new Sound("Sound/select_okay.wav").play(1.0F,Crepusucle.volume_sound);
    		if (window_command.index==0)
    		{
    		if (Game_Player.actor.gold>=10)
    		{
    			Game_Player.actor.gold-=10;
    			Game_Player.actor.heal(999);
    		}
    		}
      		if (window_command.index==1)
    		{
      			//Retour
      			Sound.sound_play(RPGSystem.S_CursorAnulation);
    			frame.call_scene(new Scene_Village(frame));
    		}
   		}
    	
    	if (Option.triggerB() || Option.triggerSelect()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(new Scene_Village(frame));
		}
	}
}