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
	public static final float DISTANCE_DIFFERENCE = 0.03f;
	public static final float SECONDS_1_DEGREE = 0.062644444f;
	public static final long MILLISECONDS_1_DEGREE = 63;
	/****************************************/
	
	public static void main(String[] args) {
		float minDistance;
		float maxDistance;
		
		LCD.drawString("Initialising...", 2, 2);
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
	
		MotorContainer container = new MotorContainer(mLeft, mRight);
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
		
		LCD.clear();
		LCD.drawString("Rotate", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		
		int angle = 360;
		
		mLeft.setSpeed(Math.round(angle * 0.5));
		mRight.setSpeed(angle);
		
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		
		Delay.msDelay(MILLISECONDS_1_DEGREE * 180);
		container.stop();
		
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString("Calibrate", 2, 1);
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString("Place the robot", 1, 2);
		
		float sample[] = new float[1];

		do {
			SampleProvider provider = distanceSensor.getDistanceMode();
			provider.fetchSample(sample, 0);
			
			LCD.clear();
			LCD.drawString("" + 1/sample[0], 2, 2);
			LCD.drawString("" + sample[0], 2, 3);
			
			Delay.msDelay(500);
			
		} while (Button.ENTER.isUp());
		
		minDistance = sample[0] - (DISTANCE_DIFFERENCE / 2);
		maxDistance = sample[0] + (DISTANCE_DIFFERENCE / 2);
		
		LCD.clear();
		LCD.drawString("Press Enter", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		
		Arbitrator arb = new Arbitrator(new Behavior[] {new TurnLeft(container, distanceSensor, maxDistance),
														new TurnRight(container, distanceSensor, minDistance),
														new ForwardTest(container, distanceSensor, minDistance, maxDistance)}
										);
		arb.go();
		
		distanceSensor.close();
	}

}
