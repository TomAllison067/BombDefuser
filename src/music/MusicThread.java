package music;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import lejos.hardware.Button;
import lejos.hardware.Sound;

public class MusicThread extends Thread {
	final long MINUTE = 60000;
	
	@Override
	public void run() {
		MusicPlayer player = new MusicPlayer("trumpet.wav");
		MusicTimer mt = new MusicTimer(player, 1 * MINUTE);
		
		player.setDaemon(true);
		mt.setDaemon(true);
		
		player.start();
		mt.start();
		
		Button.ENTER.waitForPressAndRelease();
	}
}

class MusicPlayer extends Thread {
	private String fileName;
	
	public MusicPlayer(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		while (true) {
			boolean playing = playTune(fileName);
			if (!playing) return;
		}
	}
		
	private boolean playTune(String fileName) {
		int time = Sound.playSample(new File(fileName));
		try {
			Thread.sleep(time);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
}

class MusicTimer extends Thread {
	private MusicPlayer player;
	private long time;
	
	public MusicTimer(MusicPlayer player, long time) {
		this.player = player;
		this.time = time;
	}
	
	@Override
	public void run() {
		Timer t = new Timer();
		t.schedule(new MusicStopper(player), time);
	}
}

class MusicStopper extends TimerTask {
	private MusicPlayer player;
	
	public MusicStopper(MusicPlayer player) {
		this.player = player;
	}
	
	public void run() {
		player.interrupt();
	}
}