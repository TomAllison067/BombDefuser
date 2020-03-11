package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class TurnLeft implements Behavior {

	MotorContainer container;
	EV3UltrasonicSensor sensor;
	
	public TurnLeft(MotorContainer container, EV3UltrasonicSensor sensor) {
		super();
		this.container = container;
		this.sensor = sensor;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return sample[0] > Driver.DISTANCE_MAX;
		
	}

	@Override
	public void action() {
		container.correctLeft();
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
