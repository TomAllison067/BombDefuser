package music;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class TestMusic {
	public static void main(String[] args) {
		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		MusicPlayer.putMusicOn(Music.TENSE_MUSIC);
		Button.ENTER.waitForPressAndRelease();
		MusicPlayer.putMusicOn(Music.THEME_MUSIC);
		Button.ENTER.waitForPressAndRelease();
		MusicPlayer.putMusicOn(Music.BOOM);
		Button.ENTER.waitForPressAndRelease();
		MusicPlayer.putMusicOn(Music.TENSE_MUSIC);
		Button.ENTER.waitForPressAndRelease();
	}
}
