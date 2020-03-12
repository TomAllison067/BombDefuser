package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class TurnRight implements Behavior {

	private MotorContainer container;
	private EV3UltrasonicSensor sensor;
	private float minDistance;
	
	public TurnRight(MotorContainer container, EV3UltrasonicSensor sensor, float distance) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.minDistance = distance; 
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return sample[0] < minDistance;
	}

	@Override
	public void action() {
		float[] sample = new float[1];
		SampleProvider provider = sensor.getDistanceMode();
		provider.fetchSample(sample, 0);
		container.correctRight(sample[0], minDistance);
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
