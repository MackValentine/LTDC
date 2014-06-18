package mack.main;

import java.applet.*;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class RPGApplet extends Applet implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6936741349138282548L;
	private Thread monThread;
	private Image backBuffer;
	private Graphics backContext;
	private RPGGame frame;
	public int fps;

	public void init()
	{
		
		backBuffer = this.createImage(160,160);
		backContext = backBuffer.getGraphics();
		this.requestFocus();
		setBackground(Color.lightGray);
		
		
		try {
			frame = new RPGGame(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.addKeyListener(RPGGame.keyListener);

		monThread = new Thread(this);
		monThread.start();
	}
	
	public void paint(Graphics g) {
		
		int x=0;
		int y=0;
		
		if (RPGGame.change_size==true)
		{
			g.fillRect(0, 0, 480, 480);
			g.clearRect(0, 0, 480,480);
			RPGGame.change_size=false;
		}
		if (RPGPanel.option.SizeScreen!=3)
		{
			x=(480-RPGPanel.option.SizeScreen*160)/2;
			y=(480-RPGPanel.option.SizeScreen*160)/2;
		}
		
		g.drawImage( backBuffer, x, y,(int) RPGPanel.option.SizeScreen*160, (int) RPGPanel.option.SizeScreen*160, null);
		
	}

	public void run() {
		long n=0;
		long new_time;
		long old_time;
		fps = 0;
		int f = 0;
		while (true) {
			old_time = System.currentTimeMillis();
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
			}

			update(backContext);
			Graphics g = this.getGraphics();
			if (g != null)
				paint(g);

			new_time = System.currentTimeMillis();
			
			n += new_time-old_time;
			++f;
			if (n>1000)
			{
				n=0;
				fps=f;
				f=0;
			}
		}
	}
	
	public void update(Graphics g) {
		super.update(g);
		
		frame.panel.paint(g);
		try {
			frame.panel.update();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//this.backBuffer = this.backContext.
	}
	
}
