package tasks;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import music.MusicContainer;
import utils.Bomb;
import utils.MotorContainer;

/**
 * This behavior takes place when all three interactions with a bomb have been successfully completed
 * @author Tom
 *
 */
public class DefusalComplete implements Behavior {
	private Bomb bomb;
	private MotorContainer motorContainer;
	private MusicContainer musicContainer;

	public DefusalComplete(MotorContainer motorContainer, MusicContainer musicContainer, Bomb bomb) {
		this.bomb = bomb;
		this.motorContainer = motorContainer;
		this.musicContainer = musicContainer;
	}
	
	/**
	 * Takes control if the bomb has been successfully defused,
	 * that is, if the Bomb's defuseOrder[] index is 3, where defuseOrder[3] == 'F' for finished.
	 */
	public boolean takeControl() {
		return bomb.getNextColor() == 'F';
	}

	@Override
	public void action() {
		// Notify user
		LCD.clear();
		LCD.drawString("Bomb Defused", 0, 0);
		
		// Stop the bomb countdown
		bomb.stopCountdown();
		
		// Stop the music and any motors
		musicContainer.stopMusic();
		motorContainer.stop();
		
		// Attempt to move away from bomb, back to the user
		motorContainer.turnLeft(180);
		motorContainer.forward();
		Delay.msDelay(2000);
		motorContainer.stop();
		
		LCD.drawString("Press enter to quit", 0, 2);
		Button.ENTER.waitForPressAndRelease();
		
		musicContainer.exitSound();
		System.exit(0);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
