package tasks;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import utils.Bomb;
import utils.MotorContainer;

public class Flipper implements Behavior {

	BaseRegulatedMotor mFront = new EV3LargeRegulatedMotor(MotorPort.C);

	Bomb bomb;
	EV3ColorSensor colorSensor;
	MotorContainer container;

	public Flipper(MotorContainer container, Bomb bomb, EV3ColorSensor colorSensor) {
		this.container = container;
		this.bomb = bomb;
		this.colorSensor = colorSensor;
	}

	/**
	 * For the behaviour to take control and run the robot detect the same colour as
	 * the current colour in the defuse order
	 * 
	 * @author harry
	 */
	@Override
	public boolean takeControl() {
		SampleProvider provider = colorSensor.getColorIDMode();
		float[] sample = new float[1];

		provider.fetchSample(sample, 0);

		return bomb.getNextColor() == 'R' && sample[0] == Color.RED;

	}

	@Override
	public void action() {

		LCD.clear();
		LCD.drawString("Flipper Active", 0, 6);

		// sets a flag in the bomb class to prevent other behaviours from talking
		// control whilst this behaviour runs.
		bomb.startTask();

		/**
		 * a set of parameters are sent to methods within the motorContainer class to
		 * control the robots movement.
		 */

		mFront.setSpeed(1300);

		container.turnLeft(90);

		container.forward();
		Delay.msDelay(2000);
		container.stop();

		mFront.rotateTo(-90);
		mFront.waitComplete();
		mFront.rotateTo(0);

		container.backward();
		Delay.msDelay(2000);
		container.stop();

		container.turnRight(90);

		mFront.close();

		bomb.increment();
		bomb.taskFinished();

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
