package music;
import java.io.File;

import lejos.hardware.Sound;
import lejos.utility.Delay;

/**
 * Thread to play audio files given a filename.
 * @author Tom Allison
 *
 */
public class MusicPlayer implements Runnable {
	private static Music musicToPlay = null;
	private static Thread myThread = null;
	
	final static int F5 = 698;
	final static int D5 = 587;
	final static int A4 = 440;
	final static int NOTE_LENGTH = 250;
	
	/**
	 * Calls playTune(fileName) to actually play the music.
	 * Stops the music by being interrupted by the MusicContainer class.
	 */
	public void run() {
		try {
			while(true) {
				if (musicToPlay != null) {
					playTune(musicToPlay.getFileName());
				} else {
					Thread.sleep(100);
				}
				
			}
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Method to actually play the music.
	 * @param fileName the filename of the audio file
	 * @throws InterruptedException when interrupted - allowing us to stop the music
	 */
	private void playTune(String fileName) throws InterruptedException {
		int time = Sound.playSample(new File(fileName));
		Thread.sleep(time);
	}
	
	
	public static void putMusicOn(Music music) {
		if (myThread == null) {
			musicToPlay = music;
			myThread = new Thread(new MusicPlayer());
			myThread.setDaemon(true);
			myThread.start();
		}
		if (musicToPlay != music) {
			myThread.interrupt();
			music = musicToPlay;
		}
	}
	
	public static void playExitSound() {
		Sound.playTone(F5, NOTE_LENGTH);
		Delay.msDelay(NOTE_LENGTH);
		Sound.playTone(D5, NOTE_LENGTH);
		Delay.msDelay(NOTE_LENGTH);
		Sound.playTone(A4, NOTE_LENGTH);
	}
}

