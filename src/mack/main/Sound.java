package mack.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import mack.rpg.RPGSound;

public class Sound {
	public static RPGSound sound_loop;

	public Sound(RPGGame f) {
		frame = f;
	}

	private static RPGGame frame;
	private static Clip loop_clip;
	public static FloatControl loop_volume;

	public static Clip loadSound(URL path) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(path);
			clip.open(inputStream);
			// FloatControl gainControl = (FloatControl)
			// clip.getControl(FloatControl.Type.MASTER_GAIN);
			// gainControl.setValue(-10.0f); // Reduce volume by 10
			inputStream.close();
			return clip;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static Clip loadSound(URL path, int v) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(path);
			clip.open(inputStream);
			// FloatControl gainControl = (FloatControl) clip
			// .getControl(FloatControl.Type.MASTER_GAIN);
			// gainControl.setValue(v);
			inputStream.close();
			return clip;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static void sound_play(final URL path) {
		Clip clip = loadSound(path);
		clip.start();
	}

	public static void sound_play(String path) {
		if (frame != null) {
			if (frame.getClass().getResource("/res/Sound/" + path) != null) {
				Clip clip = loadSound(frame.getClass().getResource(
						"/res/Sounds/" + path));
				clip.start();
			}
		}
	}

	public static void sound_play(RPGSound sound) {
		if (frame != null && sound != null) {
			if (frame.getClass().getResource("/res/" + sound.name + ".wav") != null) {
				Clip clip = loadSound(
						frame.getClass().getResource(
								"/res/" + sound.name + ".wav"), sound.volume);

				FloatControl gainControl = (FloatControl) clip
						.getControl(FloatControl.Type.MASTER_GAIN);
				change_volume(gainControl, RPGPanel.option.volume_sound);

				if (clip != null)
					clip.start();
			}
		}
	}

	public static void sound_loop(RPGSound sound) {
		if (sound_loop == null) {
			if (frame.getClass().getResource("/res/" + sound.name + ".wav") != null) {
				sound_loop = sound;
				if (loop_clip != null)
					loop_clip.stop();
				loop_clip = loadSound(
						frame.getClass().getResource(
								"/res/" + sound.name + ".wav"), sound.volume);
				if (loop_clip != null) {
					loop_volume = (FloatControl) loop_clip
							.getControl(FloatControl.Type.MASTER_GAIN);
					change_volume(loop_volume, RPGPanel.option.volume_music);
					loop_clip.loop(-1);
				}
			}
		} else if (frame != null && sound != null
				&& sound.name != sound_loop.name) {
			if (frame.getClass().getResource("/res/" + sound.name + ".wav") != null) {
				sound_loop = sound;
				if (loop_clip != null)
					loop_clip.stop();
				loop_clip = loadSound(
						frame.getClass().getResource(
								"/res/" + sound.name + ".wav"), sound.volume);
				if (loop_clip != null) {
					loop_volume = (FloatControl) loop_clip
							.getControl(FloatControl.Type.MASTER_GAIN);

					change_volume(loop_volume, RPGPanel.option.volume_music);

					loop_clip.loop(-1);
				}
			}
		}
	}

	public static void change_volume(FloatControl g, float f) {
		double gain = f;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		if (dB<-80.0)
			dB=-80.0F;
		g.setValue(dB);
	}

}
