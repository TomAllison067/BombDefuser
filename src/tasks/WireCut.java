package tasks;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import utils.Bomb;
import utils.MotorContainer;

public class WireCut implements Behavior{

	Bomb bomb;
	EV3ColorSensor colorSensor;
	MotorContainer container;
	
	public WireCut(MotorContainer container, Bomb bomb, EV3ColorSensor colorSensor) {
		this.container = container;
		this.bomb = bomb;
		this.colorSensor = colorSensor;
	}
	
	@Override
	public boolean takeControl() {
		SampleProvider provider = colorSensor.getColorIDMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return bomb.getNextColor() == 'R' && sample[0] == Color.RED;
		
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("WireCut Active", 2, 2);
		
		bomb.startTask();
		
		LCD.drawString("Not implemented", 2, 3);
		Delay.msDelay(2000);
		
		bomb.increment();
		bomb.taskFinished();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
