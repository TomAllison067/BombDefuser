package tasks;
import lejos.hardware.Button;
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

public class Flipper implements Behavior{

	BaseRegulatedMotor mFront = new EV3LargeRegulatedMotor(MotorPort.C);

	Bomb bomb;
	EV3ColorSensor colorSensor;
	MotorContainer container;
	
	public Flipper(MotorContainer container, Bomb bomb, EV3ColorSensor colorSensor) {
		this.container = container;
		this.bomb = bomb;
		this.colorSensor = colorSensor;
	}
	
	@Override
	public boolean takeControl() {
		SampleProvider provider = colorSensor.getColorIDMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return bomb.getNextColor() == 'G' && sample[0] == Color.GREEN;
		
	}

	@Override
	public void action() {
		
		bomb.startTask();
		
		mFront.setSpeed (1300);
		
		LCD.drawString("Press ENTER to go", 1,2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();


		container.turnLeft(90);

		container.forward();
		Delay.msDelay(2000);
		container.stop();

		mFront.rotateTo(-90);
		mFront.waitComplete();
		mFront.rotateTo(0);
		
		mFront.close();
		
		bomb.increment();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
