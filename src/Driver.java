import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Driver {
	final static float WHEEL_DIAMETER = 35; // Diameter (mm) of the wheels
	final static float AXLE_LENGTH = 212; // Distance (mm) between two wheels
	final static float ANGULAR_SPEED = 70; // How fast around corners (degrees/sec)
	final static float LINEAR_SPEED = 300;// How fast in a straight line (mm/sec)
	
	public static void main(String[] args) {
		// Initialise objects
		MovePilot plt = getPilot(MotorPort.A, MotorPort.B, WHEEL_DIAMETER, AXLE_LENGTH / 2);
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		
		plt.setLinearSpeed(LINEAR_SPEED);
		
		float boxDistance = Calibration.calibrateUs(us);
		
		LCD.clear();
		LCD.drawString("Calibration completed!", 0, 2);
		LCD.drawString("boxDistance: " + boxDistance, 0, 3);
		
		
		LCD.drawString("TrackBot v1", 1, 1);
		Button.ENTER.waitForPressAndRelease();
		
		for (int i = 0; i < 4; i++) {
			boolean move=true;
			if(!plt.isMoving() & move) {
				plt.forward();
				Delay.msDelay(1500);
				move=false;
			}
			plt.rotate(90);
			
		}
	}
	
	private static MovePilot getPilot(Port left, Port right, float diam, float offset){
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(left);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(right);
		
		Wheel wL = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-1 * offset);
		Wheel wR = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(offset);
		
		Wheel[] wS = new Wheel[] {wL, wR};
		
		Chassis c = new WheeledChassis(wS, WheeledChassis.TYPE_DIFFERENTIAL);
		
		return new MovePilot(c);
		
	}
}
