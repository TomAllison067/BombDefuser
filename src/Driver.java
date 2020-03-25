import battery.BatteryBehavior;
import drivearound.ForwardTest;
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
		LCD.drawString("Initialising...", 2, 2);
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
	
		MotorContainer motorContainer = new MotorContainer(mLeft, mRight);
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S1);
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
			
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString("Calibrate", 2, 2);
		
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString("Place the robot...", 2, 2);
		
		float[] sample = new float[1];
		do {
			SampleProvider provider = distanceSensor.getDistanceMode();
			provider.fetchSample(sample, 0);
		} while (Button.ENTER.isUp());

		float minDistance = sample[0] - (DISTANCE_DIFFERENCE / 2);
		float maxDistance = sample[0] + (DISTANCE_DIFFERENCE / 2);
		
		LCD.clear();
		LCD.drawString("Press Enter", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		
		AndroidSensor phone = new AndroidSensor();
		phone.startThread();
		
		String bombType;
		String qrInfo;
		
		while(true) {
			qrInfo = phone.getMessage();
			if(!qrInfo.equals("")) {
				bombType = qrInfo;
				break;
			}	
		}
		MusicContainer musicContainer = new MusicContainer();
		Bomb bomb = new Bomb(bombType, musicContainer);
		bomb.startBomb();
		
		// Behaviours are in order of increasing priority
		Arbitrator arb = new Arbitrator(new Behavior[] {new TurnLeft(motorContainer, distanceSensor, maxDistance, bomb),
														new TurnRight(motorContainer, distanceSensor, minDistance, bomb),
														new ForwardTest(motorContainer, distanceSensor, minDistance, maxDistance, bomb),
														new Flipper(motorContainer, bomb, colorSensor), 
														new ButtonPress(motorContainer, bomb, colorSensor),
														new WireCut(motorContainer, bomb, colorSensor),
														new DefusalComplete(motorContainer, bomb),
														new BatteryBehavior(motorContainer, musicContainer),
														new EscapeButtonBehavior(motorContainer, musicContainer),

		});
		arb.go();
		
		distanceSensor.close();
	}

}
