package music;
import java.io.File;

import lejos.hardware.Sound;
import synchronize.SynchronizedContainer;

public class MusicPlayer extends Thread {
	private String fileName;
	private SynchronizedContainer sync;
	
	public MusicPlayer(Music music, SynchronizedContainer sync, long time) {
		this.fileName = music.getFileName();
		this.sync = sync;
	}
	
	@Override
	public void run() {
		while (true) {
			playTune(fileName);
		}
	}
		
	public void playTune(String fileName) {
		int time = Sound.playSample(new File(fileName));
		try {
			sync.setMusicFlag(true);
			Thread.sleep(time);
		} catch (InterruptedException e) {
			sync.setMusicFlag(false);
		}
	}
}

