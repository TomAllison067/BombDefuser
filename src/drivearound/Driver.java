package drivearound;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {

	/************/
	public static float DISTANCE_MAX = 0.085f;
	public static float DISTANCE_MIN = 0.055f;
	/************/
	
	public static void main(String[] args) {
		LCD.drawString("Initialising...", 2, 2);
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
	
		MotorContainer container = new MotorContainer(mLeft, mRight);
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
				
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
