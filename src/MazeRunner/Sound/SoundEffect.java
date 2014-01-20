package MazeRunner.Sound;

import java.io.*;
import javax.sound.sampled.*;

/**
 * 
 * This class is a slightly edited version of the class found at:
 * http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html,
 * written by Chua Hock-Chuan.
 * 
 * The edited version was found in the source code of a game which was made in 
 * a previous edition of this project. 
 * 
 * This enum encapsulates all the sound effects of a game, so as to separate the
 * sound playing codes from the game codes. 1. Define all your sound effect
 * names and the associated wave file. 2. To play a specific sound, simply
 * invoke SoundEffect.SOUND_NAME.play(). 3. You might optionally invoke the
 * static method SoundEffect.init() to pre-load all the sound files, so that the
 * play is not paused while loading the file for the first time. 4. You can use
 * the static variable SoundEffect.volume to mute the sound.
 */
public enum SoundEffect {
	CLICK("sounds/Speech On.wav"), 
	BACK("sounds/Speech Off.wav"), 
	WALK("sounds/footsteps(1).wav"), 
	DOOR("sounds/door.wav"), 
	INGAME("sounds/ingame.wav"), 
	SHOT("sounds/ns37(1).wav"), 
	MURPHYLAUGH("sounds/murphy_laugh.wav"), 
	DOORSLIDE("sounds/door-slide.wav");

	/**
	 * Nested class for specifying volume
	 * 
	 * @author Chua Hock-Chuan
	 * 
	 */
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;

	/**
	 * Each sound effect has its own clip, loaded with its own sound file.
	 */
	private Clip clip;

	/**
	 * Constructor to construct each element of the enum with its own sound
	 * file.
	 * 
	 * @param soundFileName
	 */
	SoundEffect(String soundFileName) {
		try {
			// Use URL (instead of File) to read from disk and JAR.
			File file = new File(soundFileName);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(file);
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Play or Re-play the sound effect from the beginning, by rewinding.
	 */
	public void play() {
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
	}

	/**
	 * Loops the clip continuously, starting from the beginning.
	 */
	public void loop() {
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Loops the clip continuously, without starting from the beginning in case
	 * the clip is already running. Primarily used for walking sounds.
	 */
	public void walk() {
		if (volume != Volume.MUTE) {
			if (!clip.isRunning())
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

/**
 * Stops the playing of a clip. Resets the clip to the beginning.
 */
	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
	}

	/**
	 * Static method to pre-load all the sound files.
	 */
	public static void init() {
		values(); // calls the constructor for all the elements
	}
}