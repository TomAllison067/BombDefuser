package battery;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;
import utils.MotorContainer;

/**
 * Behaviour to gracefully shut down the robot if the battery voltage drops below a certain level
 * @author Tom
 */
public class BatteryBehavior implements Behavior {
	final float SHUTDOWN_VOLTAGE = 6.1f;
	private MotorContainer container;
	private MusicContainer musicContainer;
	private EV3UltrasonicSensor us;
	private EV3ColorSensor cs;
	
	public BatteryBehavior(MotorContainer container, MusicContainer musicContainer, EV3UltrasonicSensor us, EV3ColorSensor cs) {
		this.container = container;
		this.musicContainer = musicContainer;
		this.us = us;
		this.cs = cs;
	}
	
	@Override
	public boolean takeControl() {
		float currentVoltage = Battery.getVoltage();
		return currentVoltage <= SHUTDOWN_VOLTAGE;
	}
	
	// Not needed - the robot simply shuts down
	@Override
	public void suppress() {
	}
	
	@Override
	public void action() {
		// Silence any music and stop any motors
		musicContainer.stopMusic();
		container.stop();
		
		// Emit warning beeps every 2 seconds
		Thread thread = new Thread(new Beeper());
		thread.setDaemon(true);
		thread.start();
		
		// Display warning message
		LCD.clear();
		LCD.drawString("Battery low", 0, 0);
		LCD.drawString("Please charge", 0, 1);
		LCD.drawString("and restart", 0, 2);
		
		LCD.drawString("Press ENTER to quit", 0, 4);
		Button.ENTER.waitForPressAndRelease();
		thread.interrupt();
		
		// Close sensors
		us.close();
		cs.close();
		
		// Play exit tones and exit
		musicContainer.exitSound();
		System.exit(0);
	}
}

/**
 * Simple thread to emit warning beeps every 2 seconds
 * @author Tom
 *
 */
class Beeper implements Runnable {
	final long SECOND = 1000;
	public void run() {
		while(true) {
			try {
				Sound.beep();
				Thread.sleep(2 * SECOND);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}