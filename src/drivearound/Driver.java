package drivearound;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Driver {

	/****************************************/
	public static final float DISTANCE_DIFFERENCE = 0.2f;
	
	public static float DISTANCE_MAX;
	public static float DISTANCE_MIN;
	/****************************************/
	
	public static void main(String[] args) {
		LCD.drawString("Initialising...", 2, 2);
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
	
		MotorContainer container = new MotorContainer(mLeft, mRight);
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
		
		LCD.clear();
		LCD.drawString("Callibrate", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString("Place the robot", 1, 2);
		
		float sample[] = new float[1];

		do {
			SampleProvider provider = distanceSensor.getDistanceMode();
			provider.fetchSample(sample, 0);
			
			LCD.clear();
			LCD.drawString("" + sample[0], 2, 2);
			Delay.msDelay(500);
			
		} while (Button.ENTER.isUp());		
		
		LCD.clear();
		LCD.drawString("Press Enter", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		
		Arbitrator arb = new Arbitrator(new Behavior[] {new TurnLeft(container, distanceSensor),
														new TurnRight(container, distanceSensor),
														new ForwardTest(container, distanceSensor)}
										);
		arb.go();
		
		distanceSensor.close();
	}

}
