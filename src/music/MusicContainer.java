package music;

import java.util.Timer;
import java.util.TimerTask;

import lejos.hardware.Sound;
import lejos.utility.Delay;

/**
 * Container class to stop and start music. Runs an internal MusicPlayer thread (which has the method to play the music).
 * To stop the music, the MusicPlayer thread is interrupted by an anonymous TimerTask after the given time.
 * @author Tom
 *
 */
public class MusicContainer {
	// Frequencies for tones played in exitSound(), called by other behaviors when robot program ends
	final int F5 = 698;
	final int D5 = 587;
	final int A4 = 440;
	final int NOTE_LENGTH = 250;
	
	private Music music;
	private MusicPlayer player;
	
	/**
	 * Set the music to play
	 * @param music the music to play
	 */
	public void setMusic(Music music) {
		this.music = music;
	}
	
	/**
	 * Starts a new MusicPlayer thread and runs that thread for the given time - stops the music by interrupting the thread.
	 * @param time the time to play the music for
	 */
	public void playMusic(long time) {
		player = new MusicPlayer(music);
		player.setDaemon(true);
		player.start();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				player.interrupt();
				player = null;
			}
		}, time);
	}
	
	/**
	 * Stop music early by interrupting the MusicPlayer
	 */
	public void stopMusic() {
		player.interrupt();
		player = null;
	}
	
	// Plays some pleasant exit tones - called in EscapeButtonBehavior and BatteryBehavior
	public void exitSound() {
		Sound.playTone(F5, NOTE_LENGTH);
		Delay.msDelay(NOTE_LENGTH);
		Sound.playTone(D5, NOTE_LENGTH);
		Delay.msDelay(NOTE_LENGTH);
		Sound.playTone(A4, NOTE_LENGTH);
	}
	
}

	
