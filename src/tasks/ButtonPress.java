package tasks;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import utils.Bomb;
import utils.MotorContainer;

public class ButtonPress implements Behavior{
	
	EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S3);
	SensorMode touch = touchSensor.getTouchMode();
	float[] sample = new float[1];

	Bomb bomb;
	EV3ColorSensor colorSensor;
	MotorContainer container;
	
	public ButtonPress(MotorContainer container, Bomb bomb, EV3ColorSensor colorSensor) {
		this.container = container;
		this.bomb = bomb;
		this.colorSensor = colorSensor;
	}
	
	/**
	 * For the behaviour to take control and run the robot detect the same colour as the current colour in the defuse order
	 * 
	 * @author harry
	 */
	@Override
	public boolean takeControl() {
		SampleProvider provider = colorSensor.getColorIDMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return bomb.getNextColor() == 'B' && sample[0] == Color.BLUE;
		
	}

	@Override
	public void action() {
		
		LCD.drawString("Flipper Active", 2, 2);
		LCD.clear();
		
		bomb.startTask();
		
		container.turnRight(90);
		
		container.backward();
		
		while(true) {
			touch.fetchSample(sample, 0);
			if(sample[0]==1) {
				container.stop();
				break;
				
			}
		}
		touchSensor.close();
		
		container.forward();
		Delay.msDelay(2000);
		container.stop();
		
		container.turnRight(90);
		
		bomb.increment();
		bomb.taskFinished();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
