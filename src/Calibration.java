import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Calibration {
	public static float calibrateUs(EV3UltrasonicSensor us) {
		float[] distances = new float[1];
		float min = 0.8f;
		float max = 0.1f;
		float current = 0;
		
		SampleProvider sp = us.getDistanceMode();
				
		LCD.clear();
		LCD.drawString("Calibrating distance from box", 0, 3);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		LCD.drawString("Calibrating...", 2, 2);
		LCD.drawString("Place sensor at desired distance from box", 0, 3);
		
		while(true) {
			sp.fetchSample(distances, 0);
			current = distances[0];
			if (current < min) {
				min = current;
			}
			if (current > max) {
				max = current;
			}
			if (Button.ENTER.isDown()) {
				LCD.drawString("min: " + min, 1, 1);
				LCD.drawString("current: " + current, 1, 2);
				LCD.drawString("max: " + max, 1, 3);
			}
			if (Button.ESCAPE.isDown()) {
				break;
			}
		}
		return current;		
	}
}
