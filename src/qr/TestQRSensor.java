package qr;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

/**
 * A simple class to test the Android phone/connection/QR reader implementation.
 * 
 * @author Tom
 *
 */
public class TestQRSensor {
	public static void main(String[] args) {
		LCD.drawString("TestQRSensor v2", 0, 0);

		LCD.drawString("Enter to connect", 1, 1);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		AndroidSensor sensor = new AndroidSensor();
		sensor.startThread();

		while (!Button.ESCAPE.isDown()) {
			if (Button.ENTER.isDown()) {
				LCD.clear();
				LCD.drawString(sensor.getMessage(), 3, 1);
			}

		}
		System.exit(0);

	}
}
