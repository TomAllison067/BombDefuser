package music;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Container class to stop and start music. Runs an internal MusicPlayer thread (which has the method to play the music).
 * To stop the music, the MusicPlayer thread is interrupted by an anonymous TimerTask after the given time.
 * @author Tom
 *
 */
public class MusicContainer {
	private Music music;
	private MusicPlayer player;
	
	public MusicContainer() {
	}
	
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
			}
		}, time);
		
	}
	
	/**
	 * Stop music early by interrupting the MusicPlayer
	 */
	public void stopMusic() {
		player.interrupt();
	}
	
}

	
