import java.io.*;
import java.net.URL;
import java.util.Scanner;

import javax.sound.sampled.*;

/**
 * This enum encapsulates all the sound effects of a game, so as to separate the
 * sound playing codes from the game codes. 1. Define all your sound effect
 * names and the associated wave file. 2. To play a specific sound, simply
 * invoke SoundEffect.SOUND_NAME.play(). 3. You might optionally invoke the
 * static method SoundEffect.init() to pre-load all the sound files, so that the
 * play is not paused while loading the file for the first time. 4. You can use
 * the static variable SoundEffect.volume to mute the sound.
 */

public enum SoundEffect {
	Background("src\\PlatForm_Resources//Sound/backgroundTech.wav"), // background music
	Jump("src\\PlatForm_Resources//Sound/jumpSound.wav"), // Jump sound
	Death("src\\PlatForm_Resources//Sound/deathSound.wav"); //death noise
	
	  private Clip audioClip;
	  public void startMusic() {
	        
	        File f = new File("src\\PlatForm_Resources//Sound/backgroundTech.wav");
	        try {
	                AudioInputStream audioStream = AudioSystem.getAudioInputStream(f);
	                AudioFormat format = audioStream.getFormat();
	                DataLine.Info info = new DataLine.Info(Clip.class, format);
	                audioClip = (Clip) AudioSystem.getLine(info);
	                audioClip.open(audioStream);
	        }
	        catch (IOException exception) {
	            System.err.println(exception);
	        } catch (UnsupportedAudioFileException exception) {
	            System.err.println(exception);
	        } catch (LineUnavailableException exception) {
	            System.err.println(exception);
	        }
	        audioClip.start();
	    }
	
	public static void setPermVolume() {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("src\\PlatForm_Resources//Sound/backgroundTech.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-100.0f); // Reduce volume by 10 decibels.
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Nested class for specifying volume
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}

	public static Volume volume = Volume.LOW;

	// Each sound effect has its own clip, loaded with its own sound file.
	private Clip clip;

	// Constructor to construct each element of the enum with its own sound file.
	SoundEffect(String soundFileName) {
		try {
			// Use URL (instead of File) to read from disk and JAR.
			// URL url = this.getClass().getClassLoader().getResource(soundFileName);
			File url = new File(soundFileName);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
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

	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void play() {
		if (volume != Volume.MUTE) {
			if (clip.isRunning())
				clip.stop(); // Stop the player if it is still running
			clip.setFramePosition(0); // rewind to the beginning
			clip.start(); // Start playing
		}
	}

	// Optional static method to pre-load all the sound files.
	static void init() {
		values(); // calls the constructor for all the elements
	}
	
}