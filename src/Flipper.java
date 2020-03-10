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

		mLeft.synchronizeWith ( new BaseRegulatedMotor [] { mRight });
		
		mLeft.setSpeed (720); // 2 Revolutions Per Second ( RPS )
		mRight.setSpeed (720);
		mFront.setSpeed (1300);
		
		LCD.drawString("Press ENTER to go", 1,2);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.backward();
		mLeft.endSynchronization();
		Delay.msDelay(1000);
		mLeft.stop();
		mRight.stop();
		
		mLeft.startSynchronization();
		mLeft.rotate(200);
		mRight.rotate(200);
		mLeft.endSynchronization();
		
		mFront.backward();
		Delay.msDelay(350);
		mFront.forward();
		Delay.msDelay(350);
		mFront.stop();
		
		mLeft.close();
		mRight.close();
		mFront.close();
		
		

	}
		
}
