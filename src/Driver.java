import battery.BatteryBehavior;
import drivearound.MoveForward;
import drivearound.TurnLeft;
import drivearound.TurnRight;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import qr.AndroidSensor;
import tasks.ButtonPress;
import tasks.DefusalComplete;
import tasks.Flipper;
import tasks.Retreat;
import tasks.WireCut;
import utils.Bomb;
import utils.EscapeButtonBehavior;
import utils.MotorContainer;

public class Driver {
	private static final float DISTANCE_DIFFERENCE = 0.025f;
	private static MotorContainer motorContainer;
	private static EV3UltrasonicSensor distanceSensor;
	private static EV3ColorSensor colorSensor;
	private static float minDistance;
	private static float maxDistance;
	private static String bombType;
	private static Bomb bomb;
	
	public static void main(String[] args) {
		

		/*
		 * Initialise motors and sensors
		 */
		LCD.drawString("Defuser v53", 0, 0);
		initialise();
		
		
		/*
		 * Now we're ready to go!
		 */
		Arbitrator arb = new Arbitrator(new Behavior[] {new TurnLeft(motorContainer, distanceSensor, maxDistance, bomb),
														new TurnRight(motorContainer, distanceSensor, minDistance, bomb),
														new MoveForward(motorContainer, distanceSensor, minDistance, maxDistance, bomb),
														new Flipper(motorContainer, bomb, colorSensor), 
														new ButtonPress(motorContainer, bomb, colorSensor),
														new WireCut(motorContainer, bomb, colorSensor),
														new DefusalComplete(motorContainer, bomb, distanceSensor, colorSensor),
														new Retreat(motorContainer, bomb, distanceSensor, colorSensor),
														new BatteryBehavior(motorContainer, bomb, distanceSensor, colorSensor),
														new EscapeButtonBehavior(motorContainer, bomb, distanceSensor, colorSensor),

		});
		System.out.println("\n\n\n\n\n\n\n\n"); // Clear the 'Arbitrator created' text from the Arbitrator's constructor
		arb.go();
		
	}
	
	/**
	 * Method to initialise and calibrate the motors & sensors.
	 */
	private static void initialise() {
		LCD.drawString("Initialising...", 0, 2);
		motorContainer = new MotorContainer(new EV3LargeRegulatedMotor(MotorPort.A), new EV3LargeRegulatedMotor(MotorPort.B));
		distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
		colorSensor = new EV3ColorSensor(SensorPort.S2);
		
		/*
		 * Welcome screen
		 */
		LCD.clear(2);
		LCD.drawString("Press ENTER", 0, 3);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		
		/*
		 * Calibrate distance sensor
		 */
		LCD.drawString("Calibration..", 0, 2);
		LCD.drawString("Press ENTER", 0, 3);
		Button.ENTER.waitForPressAndRelease();
		
		calibrateDistance(distanceSensor);
		
		LCD.drawString("Calibration complete", 0, 2);
		LCD.drawString("Press ENTER", 0, 3);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		/*
		 * Connect to the phone and scan in the QR code in order to get the bombType to defuse (and thus defusal order)
		 */
		AndroidSensor phone = new AndroidSensor();
		phone.startThread();
		String qrInfo;
		while(true) { // Can we make this bit all one method to make it neater or is it more effort than it's worth?
			qrInfo = phone.getMessage();
			if(!qrInfo.equals("")) {
				bombType = qrInfo;
				break;
			}	
		}
		LCD.clear();
		
		/*
		 * Initialise the abstraction of the bomb given the bombType
		 */
		bomb = new Bomb(bombType);
		bomb.startBomb();
	}
	
	
	/**
	 * Calibrates the distance sensor, allowing the robot to slowly turn around the bomb.
	 * @param distanceSensor the sensor to calibrate.
	 * @return an array of two distances - [0] the minimum distance the robot should be from the box, and [1] the maximum.
	 */
	private static void calibrateDistance(EV3UltrasonicSensor distanceSensor) {
		float[] sample = new float[1];
		float currentDistance;
		SampleProvider sp = distanceSensor.getDistanceMode();
		
		LCD.clear();
		LCD.drawString("Place the robot...", 0, 0);
		LCD.drawString("ENTER to calibrate", 0, 1);
		LCD.drawString("ESCAPE to finish", 0, 5);
		
		while(true) {
			sp.fetchSample(sample, 0);
			currentDistance = sample[0];
			if (Button.ENTER.isDown()) {
				minDistance = currentDistance - (DISTANCE_DIFFERENCE / 2);
				maxDistance = currentDistance + (DISTANCE_DIFFERENCE / 2);
				LCD.clear();
				LCD.drawString("ENTER to calibrate", 0, 1);
				LCD.drawString("Min: " + minDistance, 0, 2);
				LCD.drawString("Cur: " + currentDistance, 0, 3);
				LCD.drawString("Max: " + maxDistance, 0, 4);
				LCD.drawString("ESCAPE to finish", 0, 5);
			}
			if (Button.ESCAPE.isDown()) {
				LCD.clear();
				break;
			}
		}
	}
}
