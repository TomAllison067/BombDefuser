package battery;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;
import utils.MotorContainer;

/**
 * Behaviour to gracefully shut down the robot if the battery voltage drops below a certain level
 * @author Tom
 * TODO: Close all sensors .etc
 */
public class BatteryBehavior implements Behavior {
	final float SHUTDOWN_VOLTAGE = 6.1f;
	private MotorContainer container;
	private MusicContainer musicContainer;
	
	public BatteryBehavior(MotorContainer container, MusicContainer musicContainer) {
		this.container = container;
		this.musicContainer = musicContainer;
	}
	
	public boolean takeControl() {
		float currentVoltage = Battery.getVoltage();
		return currentVoltage <= SHUTDOWN_VOLTAGE;
	}
	
	// Not needed - the robot simply shuts down
	public void suppress() {
	}
	
	public void action() {
		// Silence any music and stop any motors
		musicContainer.stopMusic();
		container.stop();
		
		// Emit warning beeps
		Thread thread = new Thread(new Beeper());
		thread.setDaemon(true);
		thread.start();
		
		// Display warning message
		LCD.clear();
		LCD.drawString("Battery low", 0, 0);
		LCD.drawString("Please charge", 0, 1);
		LCD.drawString("and restart", 0, 2);
		
		Button.ENTER.waitForPressAndRelease();
		thread.interrupt();
		
		System.exit(0);
	}
}

class Beeper implements Runnable {
	public void run() {
		while(true) {
			try {
				Sound.beep();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}