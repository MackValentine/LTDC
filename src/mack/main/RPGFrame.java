package mack.main;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import mack.scene.Scene_Title;

import org.xml.sax.SAXException;

public class RPGFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7244025052949037200L;

	private static final long TIME_INT = 1000000L;
	private static final long TIME_LONG = 1000000000L;

	private Graphics backContext;
	private Image backBuffer;
	private RPGGame frame;
	private int old_SizeScreen;
	private int fps;
	private long wait;
	private long frameDelay;
	private long milli;
	private int nano;

	public RPGFrame() throws IOException, ParserConfigurationException,
			SAXException, FontFormatException {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		InputStream is = getClass().getResourceAsStream(
				"/pics/Icons/icon16.png");
		this.setIconImage(ImageIO.read(is));

		this.setTitle("Loading");
		
		frame = new RPGGame(false);

		this.setSize(Option.WIDTH * RPGPanel.option.SizeScreen + 6,
				Option.HEIGHT * RPGPanel.option.SizeScreen + 29);

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		this.setResizable(false);

		this.setContentPane(frame.panel);

		this.setTitle("");

		this.addKeyListener(RPGGame.keyListener);

		backBuffer = this.createImage(160, 160);
		backContext = backBuffer.getGraphics();

		Color c = new Color(0, 0, 0);
		getGraphics().setColor(c);
		backContext.setColor(c);
		if (getDesiredRate() == 0) {
			this.frameDelay = 0;
		} else {
			this.frameDelay = TIME_LONG / getDesiredRate();
		}

		try {
			Graphics g = getGraphics();

			g.setColor(Color.black);

			int i = (int) RPGPanel.option.SizeScreen * 160;
			int j = (int) RPGPanel.option.SizeScreen * 160;

			int x = 0;
			int y = 0;

			backContext.fillRect(x, y, i, j);

			paint(g);

			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		go();
	}

	public void paint(Graphics g) {

		int i = 160;
		int j = 160;
		if (RPGPanel.option != null) {
			if (old_SizeScreen != RPGPanel.option.SizeScreen) {

				if (RPGPanel.option.SizeScreen > 1) {
					setSize((Option.WIDTH) * RPGPanel.option.SizeScreen + 6,
							(Option.HEIGHT) * RPGPanel.option.SizeScreen + 29);

				} else {
					setSize(Option.WIDTH + 6, Option.HEIGHT + 29);

				}

				if (old_SizeScreen > RPGPanel.option.SizeScreen) {
					this.setLocation(this.getLocation().x + Option.WIDTH / 1,
							this.getLocation().y + Option.HEIGHT / 1);
				} else {
					this.setLocation(this.getLocation().x - Option.WIDTH / 2,
							this.getLocation().y - Option.HEIGHT / 2);
				}
				old_SizeScreen = RPGPanel.option.SizeScreen;
			}

			i = (int) RPGPanel.option.SizeScreen * 160;
			j = (int) RPGPanel.option.SizeScreen * 160;
		}

		int x = 3;
		int y = 26;

		if (g != null)
			g.drawImage(backBuffer, x, y, i, j, null);

	}

	private void go() throws IOException, ParserConfigurationException,
			SAXException {
		long n = 0L;
		fps = 0;
		int f = 0;
		do {
			do {
				long old_time = System.currentTimeMillis();

				long lastTime = System.nanoTime();

				update(backContext);
				Graphics g = getGraphics();
				if (g != null)
					paint(g);

				this.sync(System.nanoTime() - lastTime);

				long new_time = System.currentTimeMillis();
				n += new_time - old_time;
				f++;
			} while (n <= 1000L);
			n = 0L;
			fps = f;
			f = 0;
			setTitle((new StringBuilder("Crepuscule")).append(" : ")
					.append(fps).toString());
		} while (true);
	}

	private void sync(long time) {
		if (getDesiredRate() != 0) {
			try {
				this.wait = this.frameDelay - time;
				if (this.wait > 0) {
					this.milli = this.wait / TIME_INT;
					this.nano = (int) (this.wait % TIME_INT);
					Thread.sleep(this.milli, this.nano);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	private int getDesiredRate() {
		return 60;
	}

	public void update(Graphics g) {
		super.update(g);
		frame.panel.paint(g);

		if (Option.triggerPower()) {
			try {
				frame.call_scene(new Scene_Title(frame));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		try {
			frame.panel.update();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException,
			ParserConfigurationException, SAXException, FontFormatException {
		@SuppressWarnings("unused")
		RPGFrame fenetre = new RPGFrame();
	}
}
