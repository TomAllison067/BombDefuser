import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import music.MusicThread;

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
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
				
		LCD.clear();
		LCD.drawString("Press Enter", 2, 2);
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		
		AndroidSensor phone = new AndroidSensor();
		phone.startThread();
		
		int bombType;
		String qrInfo;
		
		while(true) {
			qrInfo = phone.getMessage();
			if(qrInfo != null) {
				bombType = Integer.parseInt(qrInfo);
				break;
			}
			
		}
		Bomb bomb = new Bomb(bombType);
		bomb.startBomb();
		
		Arbitrator arb = new Arbitrator(new Behavior[] {new TurnLeft(container, distanceSensor, bomb),
														new TurnRight(container, distanceSensor, bomb),
														new ForwardTest(container, distanceSensor, bomb),
														new Flipper(container, bomb, colorSensor)
														}
										);
		arb.go();
		
		distanceSensor.close();
	}

}
