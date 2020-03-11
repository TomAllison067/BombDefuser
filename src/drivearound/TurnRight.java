package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class TurnRight implements Behavior {

	MotorContainer container;
	EV3UltrasonicSensor sensor;
	
	public TurnRight(MotorContainer container, EV3UltrasonicSensor sensor) {
		super();
		this.container = container;
		this.sensor = sensor;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return sample[0] < Driver.DISTANCE_MIN;
	}

	@Override
	public void action() {
		container.correctRight();
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
