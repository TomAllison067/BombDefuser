package music;
import java.io.File;

import lejos.hardware.Sound;
import lejos.utility.Delay;

/**
 * MusicPlayer can start a thread that plays audio files, or nothing. It can also play the exit program tones with playExitSound().
 * To use it, call putMusicOn(Music music). If no music thread is running then a thread will start and music will be played, 
 * otherwise if the argument is different to what is currently playing then the music will change.
 * To silence it, call putMusicOn(null).
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
	 * Calls playTune(fileName).
	 * If musicToPlay is null, simply sleeps the thread for 1/10th second.
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
			// Do nothing - simply keep looping
		}
	}
	
	/**
	 * Called by run() to actually play the audio file.
	 * @param fileName the filename of the audio file
	 * @throws InterruptedException when interrupted - allowing us to stop the music
	 */
	private void playTune(String fileName) throws InterruptedException {
		int time = Sound.playSample(new File(fileName));
		Thread.sleep(time);
	}
	
	/**
	 * Starts a thread that plays the music given as an argument (or nothing if the argument is null).
	 * If a thread is already running and this method is called with a different argument, the thread is interrupted to switch to the new music.
	 * @param music the music to play
	 */
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
	
	/**
	 * Simply plays some tones. Called by behaviors which deal with exiting the program (BatteryBehavior, DefusalComplete, EscapeButtonBehavior, Retreat)
	 */
	public static void playExitSound() {
		Sound.playTone(F5, NOTE_LENGTH);
		Delay.msDelay(NOTE_LENGTH);
		Sound.playTone(D5, NOTE_LENGTH);
		Delay.msDelay(NOTE_LENGTH);
		Sound.playTone(A4, NOTE_LENGTH);
	}
}

