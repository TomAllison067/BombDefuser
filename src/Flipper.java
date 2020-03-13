import drivearound.MotorContainer;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Flipper {

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		BaseRegulatedMotor mFront = new EV3LargeRegulatedMotor(MotorPort.C);

		MotorContainer container = new MotorContainer(mLeft, mRight);
		
		mLeft.synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		
		mFront.setSpeed (1300);
		
		LCD.drawString("Press ENTER to go", 1,2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		container.turnLeft(90);
		
		container.forward();
		Delay.msDelay(1000);
		container.stop();
		
		mFront.rotateTo(-90);
		mFront.waitComplete();
		mFront.rotateTo(0);
		
		mLeft.close();
		mRight.close();
		mFront.close();
		
		

	}
		
}
