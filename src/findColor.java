package robotLabWorksheets;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class findColor {

	public static void main(String[] args) {
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S2);
		SampleProvider color = cs.getColorIdMode();

		LCD.drawString("Press ENTER to go", 3, 3);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		float[] sample = new float[1]; 
		color.fetchSample(sample, 0);
		
		
		if(sample[0] == 0) {
			LCD.drawString("NONE", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("BLACK", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("BLUE", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("GREEN", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("YELLOW", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("RED", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("WHITE", 2, 2);
		}
		else if(sample[0] == ) {
			LCD.drawString("BROWN", 2, 2);
		}
		
		cs.close();
	}

}
