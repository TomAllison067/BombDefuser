import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Callibration {
	
	public static void main(String[] args) {
		LCD.drawString("Initialising", 2, 2);
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		LCD.clear();
		LCD.drawString("Press Enter", 2, 2);
		
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString("Press when 20cm", 2, 2);
		
		long time = System.currentTimeMillis();
		do {
			
		} while (Button.ENTER.isUp());
		
		time = System.currentTimeMillis() - time;
		
		Button.ENTER.waitForPressAndRelease();
		Button.ENTER.waitForPressAndRelease();
		
		LCD.clear();
		LCD.drawString(Long.toString(time), 2, 2);
		
		mLeft.close();
		mRight.close();
		
	}

}
