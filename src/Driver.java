import battery.BatteryBehavior;
import drivearound.MoveForward;
import drivearound.TurnLeft;
import drivearound.TurnRight;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;
import qr.AndroidSensor;
import tasks.ButtonPress;
import tasks.DefusalComplete;
import tasks.Flipper;
import tasks.WireCut;
import utils.Bomb;
import utils.EscapeButtonBehavior;
import utils.MotorContainer;

public class Driver {
	private static final float DISTANCE_DIFFERENCE = 0.03f;
	
	public static void main(String[] args) {
		
		/*
		 * Initialise motors and sensors
		 */
		LCD.drawString("Initialising...", 2, 2);
		MotorContainer motorContainer = initMotorContainer();
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		LCD.drawString("Press ENTER", 2, 3);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		
		/*
		 * Calibrate distance sensor
		 */
		LCD.drawString("Calibration..", 2, 2);
		LCD.drawString("Press ENTER", 2, 3);
		Button.ENTER.waitForPressAndRelease();
		float[] distances = calibrateDistance(distanceSensor);
		float minDistance = distances[0];
		float maxDistance = distances[1];
		LCD.clear();
		LCD.drawString("Calibration complete", 2, 2);
		LCD.drawString("Press ENTER", 2, 3);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		
		/*
		 * Connect to the phone and scan in the QR code in order to get the bombType to defuse (and thus defusal order)
		 */
		AndroidSensor phone = new AndroidSensor();
		phone.startThread();
		String bombType;
		String qrInfo;
		while(true) { // Can we make this bit all one method to make it neater or is it more effort than it's worth?
			qrInfo = phone.getMessage();
			if(!qrInfo.equals("")) {
				bombType = qrInfo;
				break;
			}	
		}
		
		
		/*
		 * Initialise the abstraction of the bomb given the bombType, and initialise the music container
		 */
		MusicContainer musicContainer = new MusicContainer();
		Bomb bomb = new Bomb(bombType, musicContainer);
		bomb.startBomb();
		
		
		/*
		 * Now we're ready to go!
		 */
		Arbitrator arb = new Arbitrator(new Behavior[] {new TurnLeft(motorContainer, distanceSensor, maxDistance, bomb),
														new TurnRight(motorContainer, distanceSensor, minDistance, bomb),
														new MoveForward(motorContainer, distanceSensor, minDistance, maxDistance, bomb),
														new Flipper(motorContainer, bomb, colorSensor), 
														new ButtonPress(motorContainer, bomb, colorSensor),
														new WireCut(motorContainer, bomb, colorSensor),
														new DefusalComplete(motorContainer, musicContainer, bomb, distanceSensor, colorSensor),
														new BatteryBehavior(motorContainer, musicContainer, distanceSensor, colorSensor),
														new EscapeButtonBehavior(motorContainer, musicContainer, distanceSensor, colorSensor),

		});
		arb.go();
	}
	
	/**
	 * Initialises a new MotorContainer
	 * @return the new MotorContainer
	 */
	private static MotorContainer initMotorContainer() {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		return new MotorContainer(mLeft, mRight);
	}
	
	/**
	 * Calibrates the distance sensor, allowing the robot to slowly turn around the bomb.
	 * @param distanceSensor the sensor to calibrate.
	 * @return an array of two distances - [0] the minimum distance the robot should be from the box, and [1] the maximum.
	 */
	private static float[] calibrateDistance(EV3UltrasonicSensor distanceSensor) {
		LCD.clear();
		LCD.drawString("Place the robot...", 2, 2);
		float[] sample = new float[1];
		do {
			SampleProvider provider = distanceSensor.getDistanceMode();
			provider.fetchSample(sample, 0);
		} while (Button.ENTER.isUp());
		float[] distances = {sample[0] - (DISTANCE_DIFFERENCE / 2),
							sample[0] + (DISTANCE_DIFFERENCE / 2)};
		return distances;
	}

}
