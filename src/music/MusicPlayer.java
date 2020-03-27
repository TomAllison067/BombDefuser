package music;
import java.io.File;

import lejos.hardware.Sound;

/**
 * Thread to play audio files given a filename.
 * @author Tom Allison
 *
 */
public class MusicPlayer extends Thread {
	private String fileName;
	
	/**
	 * Construct a new thread given a certain Music setting
	 * @param music the Music enum given
	 */
	public MusicPlayer(Music music) {
		this.fileName = music.getFileName();
	}
	
	/**
	 * Calls playTune(fileName) to actually play the music.
	 * Stops the music by being interrupted by the MusicContainer class.
	 */
	public void run() {
		try {
			while(true) {
				playTune(fileName);
			}
		} catch (InterruptedException e) {
			return;
		}
	}
	
	/**
	 * Method to actually play the music.
	 * @param fileName the filename of the audio file
	 * @throws InterruptedException when interrupted - allowing us to stop the music
	 */
	public void playTune(String fileName) throws InterruptedException {
		int time = Sound.playSample(new File(fileName));
		Thread.sleep(time);
	}
}

