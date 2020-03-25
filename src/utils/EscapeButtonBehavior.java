package utils;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;

/**
 * Behavior to end the robot's program when the grey interrupt button is pressed.
 * As specified in the project requirements.
 * @author Tom Allison
 * TODO: Close sensors .etc
 */
public class EscapeButtonBehavior implements Behavior {
	private MotorContainer motorContainer;
	private MusicContainer musicContainer;
	private EV3UltrasonicSensor us;
	private EV3ColorSensor cs;
	
	public EscapeButtonBehavior(MotorContainer motorContainer, MusicContainer musicContainer, EV3UltrasonicSensor us, EV3ColorSensor cs) {
		this.motorContainer = motorContainer;
		this.musicContainer = musicContainer;
		this.us = us;
		this.cs = cs;
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
		
		// Close sensors
		us.close();
		cs.close();
		
		// Play exit tones and exit
		musicContainer.exitSound();
		System.exit(0);
	}
	
	// Not required
	public void suppress() {
	}

}
