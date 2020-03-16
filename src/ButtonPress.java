import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import utils.Bomb;
import utils.MotorContainer;

public class ButtonPress implements Behavior{

	Bomb bomb;
	EV3ColorSensor colorSensor;
	MotorContainer container;
	
	public ButtonPress(MotorContainer container, Bomb bomb, EV3ColorSensor colorSensor) {
		this.container = container;
		this.bomb = bomb;
		this.colorSensor = colorSensor;
	}
	
	@Override
	public boolean takeControl() {
		SampleProvider provider = colorSensor.getColorIDMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return bomb.getNextColor() == 'B' && sample[0] == Color.BLUE;
		
	}

	@Override
	public void action() {
		
		bomb.startTask();
		
		
		bomb.increment();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
