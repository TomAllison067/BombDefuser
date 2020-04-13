package tasks;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import music.MusicPlayer;
import utils.Bomb;
import utils.MotorContainer;

/**
 * This behavior takes place when all three interactions with a bomb have been
 * successfully completed
 * 
 * @author Tom Allison
 *
 */
public class DefusalComplete implements Behavior {
	private Bomb bomb;
	private MotorContainer motorContainer;
	private EV3UltrasonicSensor us;
	private EV3ColorSensor cs;

	public DefusalComplete(MotorContainer motorContainer, Bomb bomb, EV3UltrasonicSensor us, EV3ColorSensor cs) {
		this.bomb = bomb;
		this.motorContainer = motorContainer;
		this.us = us;
		this.cs = cs;
	}

	/**
	 * Takes control if the bomb has been successfully defused, that is, if the
	 * Bomb's defuseOrder[] index is 3, where defuseOrder[3] == 'F' for finished.
	 */
	public boolean takeControl() {
		return bomb.getNextColor() == 'F';
	}

	/**
	 * Notifies user of success, stops the Bomb class's internal countdown, moves
	 * away from the bomb and exits the program.
	 */
	public void action() {
		// Notify user
		LCD.clear();
		LCD.drawString("Bomb Defused", 0, 0);

		// Stop the bomb countdown
		bomb.stopCountdown();

		// Stop the music and any motors
		MusicPlayer.putMusicOn(null);
		motorContainer.stop();

		// Attempt to move away from bomb, back to the user
		motorContainer.turnLeft(180);
		motorContainer.forward();
		Delay.msDelay(2000);
		motorContainer.stop();

		LCD.drawString("Press enter to quit", 0, 2);
		Button.ENTER.waitForPressAndRelease();

		// Close sensors
		us.close();
		cs.close();

		// Exit
		System.exit(0);
	}

	// Not required - we've completed our task!
	public void suppress() {
	}

}
