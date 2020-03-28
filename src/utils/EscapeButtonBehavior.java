package utils;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import music.MusicPlayer;

/**
 * Behavior to end the robot's program when the grey interrupt button is pressed.
 * As specified in the project requirements.
 * @author Tom Allison
 */
public class EscapeButtonBehavior implements Behavior {
	private MotorContainer motorContainer;
	private Bomb bomb;
	private EV3UltrasonicSensor us;
	private EV3ColorSensor cs;
	
	public EscapeButtonBehavior(MotorContainer motorContainer, Bomb bomb, EV3UltrasonicSensor us, EV3ColorSensor cs) {
		this.motorContainer = motorContainer;
		this.bomb = bomb;
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
		
		// Stop the bomb, all motors and music
		bomb.stopCountdown();
		motorContainer.stop();
		MusicPlayer.putMusicOn(null);
		
		// Close sensors
		us.close();
		cs.close();
		
		// Play exit tones and exit
		MusicPlayer.playExitSound();
		System.exit(0);
	}
	
	// Not required
	public void suppress() {
	}

}
