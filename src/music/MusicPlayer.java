package music;
import java.io.File;

import lejos.hardware.Sound;

public class MusicPlayer extends Thread {
	private String fileName;
	
	public MusicPlayer(Music music) {
		this.fileName = music.getFileName();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				playTune(fileName);
			}
		} catch (InterruptedException e) {
		}
	}
		
	public void playTune(String fileName) throws InterruptedException {
		int time = Sound.playSample(new File(fileName));
		Thread.sleep(time);
	}
}

