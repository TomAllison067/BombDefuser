package battery;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import music.MusicPlayer;
import utils.Bomb;
import utils.MotorContainer;

/**
 * Behaviour to gracefully shut down the robot if the battery voltage drops below a certain level
 * @author Tom
 */
public class BatteryBehavior implements Behavior {
	final float SHUTDOWN_VOLTAGE = 6.1f;
	private MotorContainer container;
	private Bomb bomb;
	private EV3UltrasonicSensor us;
	private EV3ColorSensor cs;

	/**
	 * Constructs the BatteryBehaviour object
	 * @param container the MotorContainer containing the motors used
	 * @param bomb the Bomb currently active
	 * @param us the EV3UltrasonicSensor in use
	 * @param cs the EV3ColorSensor in use
	 */
	public BatteryBehavior(MotorContainer container, Bomb bomb, EV3UltrasonicSensor us,
			EV3ColorSensor cs) {
		this.container = container;
		this.bomb = bomb;
		this.us = us;
		this.cs = cs;
	}

	// This behaviour takes control if the battery voltage falls below a certain level.
	@Override
	public boolean takeControl() {
		float currentVoltage = Battery.getVoltage();
		return currentVoltage <= SHUTDOWN_VOLTAGE;
	}

	// The action taken when the battery voltage is low
	public void action() {
		// Stop the countdown, silence any music and stop any motors
		bomb.stopCountdown();
		MusicPlayer.putMusicOn(null);
		container.stop();

		// Emit warning beeps every 2 seconds
		Thread thread = new Thread(new Beeper());
		thread.setDaemon(true);
		thread.start();

		// Display warning message
		LCD.clear();
		LCD.drawString("Battery low", 0, 0);
		LCD.drawString("Press ENTER to quit", 0, 2);
		Button.ENTER.waitForPressAndRelease();
		thread.interrupt();

		// Close sensors
		us.close();
		cs.close();

		// Exit
		System.exit(0);
	}

	// Not needed - the robot simply shuts down
	public void suppress() {
	}
}

/**
 * Simple thread to emit warning beeps every 2 seconds.
 * Perhaps the user is not paying attention and wondering why the robot has stopped - this should alert them.
 * @author Tom
 */
class Beeper implements Runnable {
	final long SECOND = 1000;

	public void run() {
		while (true) {
			try {
				Sound.beep();
				Thread.sleep(2 * SECOND);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}