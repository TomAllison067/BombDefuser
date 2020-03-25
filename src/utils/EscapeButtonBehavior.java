package utils;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;

/**
 * Behavior to end the robot's program when the grey interrupt button is pressed.
 * As specified in the project requirements.
 * @author Tom
 * TODO: Close sensors .etc
 */
public class EscapeButtonBehavior implements Behavior {
	private MotorContainer motorContainer;
	private MusicContainer musicContainer;
	
	public EscapeButtonBehavior(MotorContainer motorContainer, MusicContainer musicContainer) {
		this.motorContainer = motorContainer;
		this.musicContainer = musicContainer;
	}
	
	// Take control if ESCAPE is pressed
	public boolean takeControl() {
		return Button.ESCAPE.isDown();
	}
	
	public void action() {
		// Notify user
		LCD.clear();
		LCD.drawString("Quitting...", 0, 0);
		
		// Stop all motors and music
		motorContainer.stop();
		musicContainer.stopMusic();
		
		// Play exit tones
		musicContainer.exitSound();
		
		System.exit(0);
	}
	
	// Not required
	public void suppress() {
	}

}
