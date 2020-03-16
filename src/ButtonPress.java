import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class ButtonPress implements Behavior{

	BaseRegulatedMotor mFront = new EV3LargeRegulatedMotor(MotorPort.C);

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
		
	s
		
		bomb.increment();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
