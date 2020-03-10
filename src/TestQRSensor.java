import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class TestQRSensor {
	public static void main(String[] args) {
		LCD.drawString("TestQRSensor v1", 0, 0);
		LCD.drawString("Connecting...", 0, 6);
		
		AndroidSensor sensor = new AndroidSensor();
		sensor.startThread();
		
		LCD.drawString("Press ENTER for message", 1, 1);
		Button.ENTER.waitForPressAndRelease();
		LCD.drawString("Message:" + sensor.getMessage(), 0, 1);
		Button.ENTER.waitForPressAndRelease();
	}
}
