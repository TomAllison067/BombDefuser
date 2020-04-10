package music;

import java.io.File;

import lejos.hardware.Sound;

/**
 * MusicPlayer can start a thread that plays audio files, or nothing. It can
 * also play the exit program tones with playExitSound(). To use it, call
 * putMusicOn(Music music). If no music thread is running then a thread will
 * start and music will be played, otherwise if the argument is different to
 * what is currently playing then the music will change. To silence it, call
 * putMusicOn(null).
 * 
 * @author Tom Allison
 *
 */
public class MusicPlayer implements Runnable {
	private static Music musicToPlay;
	private static Thread myThread;

	/**
	 * Calls playTune(fileName). If musicToPlay is null, simply do nothing.
	 */
	public void run() {
		while (true) {
			try {
				if (musicToPlay != null) {
					playTune(musicToPlay.getFileName());
				} else {
					Thread.yield();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	/**
	 * Called by run() to actually play the audio file.
	 * 
	 * @param fileName the filename of the audio file
	 * @throws InterruptedException
	 */
	private void playTune(String fileName) throws InterruptedException {
		int time = Sound.playSample(new File(fileName));
		Thread.sleep(time);
	}

	/**
	 * Starts a thread that plays the music given as an argument (or nothing if the
	 * argument is null).
	 * 
	 * If a thread is already running, the music is changed AFTER the current sample
	 * finishes playing.
	 * 
	 * Notes: The original idea was to interrupt the samples and play the new one
	 * straight away (to change music quickly), but I couldn't seem to implement
	 * this. Every attempt to implement this lead to:
	 * "java.lang.NullPointerException at
	 * lejos.internal.ev3.EV3Audio.playSample(EV3Audio.java:341)"
	 * 
	 * You CAN stop the music early by passing null as an argument, but you can no
	 * longer play any more samples afterwards.
	 * 
	 * @param music the music to play
	 */
	public static void putMusicOn(Music music) {
		if (music == null) {
			myThread.interrupt();
		} else {
			musicToPlay = music;
			if (myThread == null) {
				myThread = new Thread(new MusicPlayer());
				myThread.setDaemon(true);
				myThread.start();
			}
		}

	}

	// Here is the broken version - should work, but leads to NullPointerException
	// in lejos.internal.ev3.EV3AUDIO.playSample

// public static void putMusicOn(Music music) {
//		musicToPlay = music;
//		if (myThread != null) {
//			myThread.interrupt();
//			myThread = new Thread(new MusicPlayer());
//			myThread.setDaemon(true);
//			myThread.start();
//		} else {
//			myThread = new Thread(new MusicPlayer());
//			myThread.setDaemon(true);
//			myThread.start();
//		}
//	}

}
