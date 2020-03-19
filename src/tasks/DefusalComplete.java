package tasks;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import utils.Bomb;
import utils.MotorContainer;

/**
 * This behavior takes place when all three interactions with a bomb have been successfully completed (ie, if the Bomb's array is incremented three times)
 * @author Tom
 *
 */
public class DefusalComplete implements Behavior {
	Bomb bomb;
	MotorContainer container;

	public DefusalComplete(MotorContainer container, Bomb bomb) {
		this.bomb = bomb;
		this.container = container;
	}
	@Override
	public boolean takeControl() {
		return bomb.getNextColor() == 'F';
	}

	@Override
	public void action() {
		bomb.stopCountdown();
		container.stop();
		container.turnLeft(180);
		container.forward();
		Delay.msDelay(2000);
		container.stop();
		LCD.clear();
		LCD.drawString("Bomb Defused", 1, 1);
		Button.ENTER.waitForPressAndRelease();
		System.exit(0);
		
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
