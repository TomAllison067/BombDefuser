package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class TurnLeft implements Behavior {

	private EV3UltrasonicSensor sensor;
	private MotorContainer container;
	private float maxDistance;
	
	public TurnLeft(MotorContainer container, EV3UltrasonicSensor sensor, float distance) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.maxDistance = distance;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return sample[0] > maxDistance;
		
	}

	@Override
	public void action() {
		float[] sample = new float[1];
		SampleProvider provider = sensor.getDistanceMode();
		provider.fetchSample(sample, 0);
		container.correctLeft(sample[0], maxDistance);
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
