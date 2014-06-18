package mack.scene;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mack.game.Game_Player;
import mack.main.Option;
import mack.main.RPGGame;
import mack.main.RPGPanel;
import mack.main.Sound;
import mack.rpg.RPGSystem;
import mack.window.WindowCommand;
import mack.window.Window_HUD;
import mack.window.Window_Help;
import mack.window.Window_Menu;

import org.xml.sax.SAXException;

public class Scene_Status extends Scene_Base {

	private Window_Help window_help;
	private Window_HUD HUD;
	private Window_Menu background;
	private WindowCommand window_command;
	public int[] bonus={5,1,2,1,2};
	
	public int scene_id() {
		return 1;
	}

	public Scene_Status(RPGGame f) throws IOException, FontFormatException {
		super(f);
		String[] tab = {"Vie","Force", "Endurance", "Sagesse", "Esprit", "Retour"};
    	window_command = new WindowCommand(0,16,80,tab, true);
    	
    	background = new Window_Menu();
    	
    	HUD=new Window_HUD(0,144,0,0);
    	
    	window_help = new Window_Help();
	}

	public void update() throws ParserConfigurationException, SAXException,
			IOException, FontFormatException {
		super.update();
		window_command.update();
		
		if (window_command.valid)
    	{
      		if (window_command.index==5)
    		{
      			//new Sound("Sound/select_okay.wav").play(1.0F,Crepusucle.volume_sound);
    			frame.call_scene(new Scene_Menu(frame));
    		}
      		else
      		{
      			if (Game_Player.actor.lv_up_pts>=1)
      			{
      				Game_Player.actor.lv_up_pts-=1;
      				if (window_command.index==0)
	      			{
	      				Game_Player.actor.mHp+=bonus[window_command.index];
	      			}
      				else if (window_command.index==1)
	      			{
      					Game_Player.actor.force+=bonus[window_command.index];
      				}
      				else if (window_command.index==2)
	   				{
	   					Game_Player.actor.endurance+=bonus[window_command.index];
	      			}
      				else if (window_command.index==3)
	      			{
	      				Game_Player.actor.sagesse+=bonus[window_command.index];
	      			}
      				else if (window_command.index==4)
	      			{
	      				Game_Player.actor.esprit+=bonus[window_command.index];
	      			}
      			}
      		}
   		}
		if (Option.triggerB()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(new Scene_Menu(frame));
		} else if (Option.triggerSelect()) {
			Sound.sound_play(RPGSystem.S_CursorAnulation);
			frame.call_scene(RPGPanel.map);
		}
	}

	public void paint(Graphics g) throws IOException {
		super.paint(g);
		background.draw(g);
    	HUD.draw(g);
    	window_command.draw(g);
    	
    	window_help.draw(g);
    	int i = window_command.index;
    	if (i<0)
    		i=0;
    	if (i<5)
    	{
	    	if (window_command.commands[i]!=null)
	    	{
	    		window_help.set_text(window_command.commands[i]+". 1 pour "+String.valueOf(bonus[i]),g);
	    	}
    	}
    	else
    	{
    		window_help.set_text("Retourne au menu.",g);
    	}
	}

}
