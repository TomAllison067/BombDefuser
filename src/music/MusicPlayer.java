package music;
import java.io.File;

import lejos.hardware.Sound;

public class MusicPlayer extends Thread {
	private String fileName;
	
	public MusicPlayer(Music music, long time) {
		this.fileName = music.getFileName();
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
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
}

